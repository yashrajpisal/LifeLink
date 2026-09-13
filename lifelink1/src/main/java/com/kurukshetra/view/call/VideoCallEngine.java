package com.kurukshetra.view.call;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.sound.sampled.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Clean, decoupled engine for point-to-point LAN video and audio calls
 * using Sarxos Webcam, UDP DatagramSockets, and Java Sound API.
 */
public class VideoCallEngine {

    public static final int VIDEO_PORT = 55555;
    public static final int AUDIO_PORT = 55556;

    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final ObjectProperty<Image> localVideoImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> remoteVideoImage = new SimpleObjectProperty<>();

    private Webcam webcam;
    private DatagramSocket videoSendSocket;
    private DatagramSocket videoReceiveSocket;
    private DatagramSocket audioSendSocket;
    private DatagramSocket audioReceiveSocket;

    private TargetDataLine micLine;
    private SourceDataLine speakerLine;

    private String remoteIp;
    private Runnable onRemoteConnected;
    private Runnable onCallTerminated;

    public ObjectProperty<Image> localVideoImageProperty() {
        return localVideoImage;
    }

    public ObjectProperty<Image> remoteVideoImageProperty() {
        return remoteVideoImage;
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public synchronized void startCall(String remoteIp, Runnable onRemoteConnected, Runnable onCallTerminated) {
        if (isRunning.get()) {
            stopCall();
        }

        this.remoteIp = (remoteIp != null) ? remoteIp.trim() : "";
        this.onRemoteConnected = onRemoteConnected;
        this.onCallTerminated = onCallTerminated;
        this.isRunning.set(true);

        startVideoReceiver();
        startAudioReceiver();

        if (!this.remoteIp.isEmpty()) {
            startVideoSender(this.remoteIp);
            startAudioSender(this.remoteIp);
        }
    }

    public synchronized void updateRemoteIp(String newRemoteIp) {
        if (newRemoteIp == null || newRemoteIp.trim().isEmpty()) return;
        String trimmed = newRemoteIp.trim();
        if (trimmed.equalsIgnoreCase(this.remoteIp)) return;

        this.remoteIp = trimmed;
        if (isRunning.get()) {
            startVideoSender(this.remoteIp);
            startAudioSender(this.remoteIp);
        }
    }

    private void startVideoSender(String targetIp) {
        new Thread(() -> {
            try {
                if (webcam == null) {
                    webcam = Webcam.getDefault();
                }
                if (webcam != null) {
                    try {
                        Dimension qvga = WebcamResolution.QVGA.getSize(); // 320x240
                        boolean hasQvga = false;
                        for (Dimension d : webcam.getViewSizes()) {
                            if (d.width == qvga.width && d.height == qvga.height) {
                                hasQvga = true;
                                break;
                            }
                        }
                        if (hasQvga && !webcam.isOpen()) {
                            webcam.setViewSize(qvga);
                        }
                    } catch (Exception ignored) {}

                    if (!webcam.isOpen()) {
                        webcam.open();
                    }

                    if (videoSendSocket == null || videoSendSocket.isClosed()) {
                        videoSendSocket = new DatagramSocket();
                    }

                    InetAddress dest = InetAddress.getByName(targetIp);
                    while (isRunning.get() && webcam != null && webcam.isOpen()) {
                        BufferedImage img = webcam.getImage();
                        if (img != null) {
                            Image fxImg = SwingFXUtils.toFXImage(img, null);
                            Platform.runLater(() -> localVideoImage.set(fxImg));

                            byte[] jpgBytes = compressToJpg(img, 0.45f);
                            if (jpgBytes.length < 65000 && videoSendSocket != null && !videoSendSocket.isClosed()) {
                                DatagramPacket packet = new DatagramPacket(jpgBytes, jpgBytes.length, dest, VIDEO_PORT);
                                videoSendSocket.send(packet);
                            }
                        }
                        Thread.sleep(40); // ~25 FPS
                    }
                }
            } catch (Exception e) {
                if (isRunning.get()) {
                    System.err.println("[VideoCallEngine] Video send warning: " + e.getMessage());
                }
            }
        }, "VideoCall-VideoSender").start();
    }

    private void startVideoReceiver() {
        new Thread(() -> {
            try {
                if (videoReceiveSocket != null && !videoReceiveSocket.isClosed()) {
                    videoReceiveSocket.close();
                }
                videoReceiveSocket = new DatagramSocket(null);
                videoReceiveSocket.setReuseAddress(true);
                videoReceiveSocket.bind(new InetSocketAddress(VIDEO_PORT));

                byte[] buffer = new byte[65535];
                boolean notifiedConnected = false;

                while (isRunning.get()) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    videoReceiveSocket.receive(packet);

                    if (!notifiedConnected && onRemoteConnected != null) {
                        notifiedConnected = true;
                        Platform.runLater(onRemoteConnected);
                    }

                    ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                    BufferedImage remoteImg = ImageIO.read(bais);
                    if (remoteImg != null) {
                        Image fxRemote = SwingFXUtils.toFXImage(remoteImg, null);
                        Platform.runLater(() -> remoteVideoImage.set(fxRemote));
                    }
                }
            } catch (Exception e) {
                if (isRunning.get()) {
                    System.err.println("[VideoCallEngine] Video receive warning: " + e.getMessage());
                }
            }
        }, "VideoCall-VideoReceiver").start();
    }

    private void startAudioSender(String targetIp) {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, false);
                DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(micInfo)) {
                    return;
                }

                micLine = (TargetDataLine) AudioSystem.getLine(micInfo);
                micLine.open(format);
                micLine.start();

                if (audioSendSocket == null || audioSendSocket.isClosed()) {
                    audioSendSocket = new DatagramSocket();
                }

                InetAddress dest = InetAddress.getByName(targetIp);
                byte[] audioBuffer = new byte[1024];

                while (isRunning.get() && micLine != null && micLine.isOpen()) {
                    int read = micLine.read(audioBuffer, 0, audioBuffer.length);
                    if (read > 0 && audioSendSocket != null && !audioSendSocket.isClosed()) {
                        DatagramPacket packet = new DatagramPacket(audioBuffer, read, dest, AUDIO_PORT);
                        audioSendSocket.send(packet);
                    }
                }
            } catch (Exception e) {
                if (isRunning.get()) {
                    System.err.println("[VideoCallEngine] Audio send warning: " + e.getMessage());
                }
            }
        }, "VideoCall-AudioSender").start();
    }

    private void startAudioReceiver() {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, false);
                DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
                if (!AudioSystem.isLineSupported(speakerInfo)) {
                    return;
                }

                speakerLine = (SourceDataLine) AudioSystem.getLine(speakerInfo);
                speakerLine.open(format);
                speakerLine.start();

                if (audioReceiveSocket != null && !audioReceiveSocket.isClosed()) {
                    audioReceiveSocket.close();
                }
                audioReceiveSocket = new DatagramSocket(null);
                audioReceiveSocket.setReuseAddress(true);
                audioReceiveSocket.bind(new InetSocketAddress(AUDIO_PORT));

                byte[] audioBuffer = new byte[1024];

                while (isRunning.get()) {
                    DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
                    audioReceiveSocket.receive(packet);
                    if (speakerLine != null && speakerLine.isOpen()) {
                        speakerLine.write(packet.getData(), 0, packet.getLength());
                    }
                }
            } catch (Exception e) {
                if (isRunning.get()) {
                    System.err.println("[VideoCallEngine] Audio receive warning: " + e.getMessage());
                }
            }
        }, "VideoCall-AudioReceiver").start();
    }

    public synchronized void stopCall() {
        if (!isRunning.get()) return;
        isRunning.set(false);

        new Thread(() -> {
            try {
                if (webcam != null && webcam.isOpen()) {
                    webcam.close();
                }
            } catch (Exception ignored) {}

            try {
                if (micLine != null) {
                    micLine.stop();
                    micLine.close();
                }
            } catch (Exception ignored) {}

            try {
                if (speakerLine != null) {
                    speakerLine.stop();
                    speakerLine.close();
                }
            } catch (Exception ignored) {}

            try {
                if (videoSendSocket != null && !videoSendSocket.isClosed()) videoSendSocket.close();
            } catch (Exception ignored) {}

            try {
                if (videoReceiveSocket != null && !videoReceiveSocket.isClosed()) videoReceiveSocket.close();
            } catch (Exception ignored) {}

            try {
                if (audioSendSocket != null && !audioSendSocket.isClosed()) audioSendSocket.close();
            } catch (Exception ignored) {}

            try {
                if (audioReceiveSocket != null && !audioReceiveSocket.isClosed()) audioReceiveSocket.close();
            } catch (Exception ignored) {}

            Platform.runLater(() -> {
                localVideoImage.set(null);
                remoteVideoImage.set(null);
                if (onCallTerminated != null) {
                    onCallTerminated.run();
                }
            });
        }, "VideoCall-Cleanup").start();
    }

    private byte[] compressToJpg(BufferedImage img, float quality) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) throw new IllegalStateException("No JPEG writer found");
        ImageWriter writer = writers.next();
        writer.setOutput(ImageIO.createImageOutputStream(baos));
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(img, null, null), param);
        writer.dispose();
        return baos.toByteArray();
    }

    public static String getLocalNetworkIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        String ip = addr.getHostAddress();
                        if (!ip.startsWith("169.254.")) {
                            return ip;
                        }
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }
}
