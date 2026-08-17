package com.kurukshetra.view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.sound.sampled.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class NativeCallScreen extends VBox {

    private static final int VIDEO_PORT = 55555;
    private static final int AUDIO_PORT = 55556;

    private final String remoteIp;
    private final Runnable onCallEnded;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private ImageView remoteVideoView;
    private ImageView localVideoView;

    private Webcam webcam;
    private DatagramSocket videoSendSocket;
    private DatagramSocket videoReceiveSocket;
    private DatagramSocket audioSendSocket;
    private DatagramSocket audioReceiveSocket;

    private TargetDataLine micLine;
    private SourceDataLine speakerLine;

    public NativeCallScreen(String remoteIp, Runnable onCallEnded) {
        this.remoteIp = remoteIp;
        this.onCallEnded = onCallEnded;

        setStyle("-fx-background-color: #121222;");
        setPadding(new Insets(15));
        setSpacing(10);
        VBox.setVgrow(this, Priority.ALWAYS);

        // Header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setStyle("-fx-background-color: rgba(30, 30, 47, 0.9); -fx-background-radius: 12px;");

        Label title = new Label("Live Consultation with: " + remoteIp);
        title.setFont(Font.font("System", FontWeight.BOLD, 15));
        title.setTextFill(Color.WHITE);

        header.getChildren().add(title);
        getChildren().add(header);

        // Video View Area (Picture-in-Picture)
        StackPane videoArea = new StackPane();
        videoArea.setStyle("-fx-background-color: #0b0b14; -fx-background-radius: 12px;");
        VBox.setVgrow(videoArea, Priority.ALWAYS);

        remoteVideoView = new ImageView();
        remoteVideoView.setPreserveRatio(true);
        remoteVideoView.fitWidthProperty().bind(videoArea.widthProperty().subtract(20));
        remoteVideoView.fitHeightProperty().bind(videoArea.heightProperty().subtract(20));

        localVideoView = new ImageView();
        localVideoView.setFitWidth(180);
        localVideoView.setFitHeight(135);
        localVideoView.setPreserveRatio(true);
        localVideoView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0, 0, 2);");
        StackPane.setAlignment(localVideoView, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(localVideoView, new Insets(15));

        videoArea.getChildren().addAll(remoteVideoView, localVideoView);
        getChildren().add(videoArea);

        // Controls
        HBox footer = new HBox(20);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));

        Button endBtn = new Button("End Call");
        endBtn.setStyle("-fx-background-color: #f5365c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20px; -fx-padding: 8px 24px; -fx-cursor: hand;");
        endBtn.setOnAction(e -> stopCall());

        footer.getChildren().add(endBtn);
        getChildren().add(footer);

        startStreaming();
    }

    private void startStreaming() {
        isRunning.set(true);

        // 1. Video Sending Thread (Camera -> Remote IP)
        new Thread(() -> {
            try {
                webcam = Webcam.getDefault();
                if (webcam != null) {
                    webcam.setViewSize(WebcamResolution.QVGA.getSize()); // 320x240 for fluid streaming
                    webcam.open();
                    videoSendSocket = new DatagramSocket();
                    InetAddress dest = InetAddress.getByName(remoteIp);

                    while (isRunning.get()) {
                        BufferedImage img = webcam.getImage();
                        if (img != null) {
                            Image fxImg = SwingFXUtils.toFXImage(img, null);
                            Platform.runLater(() -> localVideoView.setImage(fxImg));

                            byte[] jpgBytes = compressToJpg(img, 0.5f);
                            if (jpgBytes.length < 65000) {
                                DatagramPacket packet = new DatagramPacket(jpgBytes, jpgBytes.length, dest, VIDEO_PORT);
                                videoSendSocket.send(packet);
                            }
                        }
                        Thread.sleep(40); // ~25 FPS
                    }
                }
            } catch (Exception e) {
                System.err.println("Video send error: " + e.getMessage());
            }
        }).start();

        // 2. Video Receiving Thread (Remote Frames -> remoteVideoView)
        new Thread(() -> {
            try {
                videoReceiveSocket = new DatagramSocket(VIDEO_PORT);
                byte[] buffer = new byte[65535];

                while (isRunning.get()) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    videoReceiveSocket.receive(packet);

                    ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                    BufferedImage remoteImg = ImageIO.read(bais);
                    if (remoteImg != null) {
                        Image fxRemote = SwingFXUtils.toFXImage(remoteImg, null);
                        Platform.runLater(() -> remoteVideoView.setImage(fxRemote));
                    }
                }
            } catch (Exception e) {
                if (isRunning.get()) System.err.println("Video receive error: " + e.getMessage());
            }
        }).start();

        // 3. Audio / Mic Streaming Thread (Mic -> Remote IP)
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, false);
                DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
                micLine = (TargetDataLine) AudioSystem.getLine(micInfo);
                micLine.open(format);
                micLine.start();

                audioSendSocket = new DatagramSocket();
                InetAddress dest = InetAddress.getByName(remoteIp);
                byte[] audioBuffer = new byte[1024];

                while (isRunning.get()) {
                    int read = micLine.read(audioBuffer, 0, audioBuffer.length);
                    if (read > 0) {
                        DatagramPacket packet = new DatagramPacket(audioBuffer, read, dest, AUDIO_PORT);
                        audioSendSocket.send(packet);
                    }
                }
            } catch (Exception e) {
                System.err.println("Mic stream error: " + e.getMessage());
            }
        }).start();

        // 4. Audio Playback Thread (Receives Remote Voice -> Plays to Speakers)
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, false);
                DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
                speakerLine = (SourceDataLine) AudioSystem.getLine(speakerInfo);
                speakerLine.open(format);
                speakerLine.start();

                audioReceiveSocket = new DatagramSocket(AUDIO_PORT);
                byte[] audioBuffer = new byte[1024];

                while (isRunning.get()) {
                    DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
                    audioReceiveSocket.receive(packet);
                    speakerLine.write(packet.getData(), 0, packet.getLength());
                }
            } catch (Exception e) {
                if (isRunning.get()) System.err.println("Audio receive error: " + e.getMessage());
            }
        }).start();
    }

    public void stopCall() {
        if (!isRunning.get()) return;
        isRunning.set(false);

        try {
            if (webcam != null && webcam.isOpen()) webcam.close();
            if (micLine != null) { micLine.stop(); micLine.close(); }
            if (speakerLine != null) { speakerLine.stop(); speakerLine.close(); }
            if (videoSendSocket != null && !videoSendSocket.isClosed()) videoSendSocket.close();
            if (videoReceiveSocket != null && !videoReceiveSocket.isClosed()) videoReceiveSocket.close();
            if (audioSendSocket != null && !audioSendSocket.isClosed()) audioSendSocket.close();
            if (audioReceiveSocket != null && !audioReceiveSocket.isClosed()) audioReceiveSocket.close();
        } catch (Exception ignored) {}

        if (onCallEnded != null) Platform.runLater(onCallEnded);
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
}