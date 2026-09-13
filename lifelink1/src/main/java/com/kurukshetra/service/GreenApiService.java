package com.kurukshetra.service;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Green-API Telegram Integration Service for LifeLink Emergency Dispatch.
 * Uses GREEN-API (https://green-api.com) Telegram instance to transmit
 * emergency SOS alerts to contacts.
 */
public class GreenApiService {

    // =========================================================================
    // GREEN-API TELEGRAM CREDENTIALS
    // Obtain these from your GREEN-API console (https://console.green-api.com/)
    // =========================================================================
    public static String ID_INSTANCE = System.getenv("GREEN_API_ID_INSTANCE") != null
            ? System.getenv("GREEN_API_ID_INSTANCE")
            : "410022729572";

    public static String API_TOKEN_INSTANCE = System.getenv("GREEN_API_TOKEN_INSTANCE") != null
            ? System.getenv("GREEN_API_TOKEN_INSTANCE")
            : "df99c550cc0640e7bed4c79066c871529edaef1461794157b6";

    public static String API_URL = System.getenv("GREEN_API_URL") != null
            ? System.getenv("GREEN_API_URL")
            : "https://4100.api.green-api.com";

    // Green-API standard instance route prefix (applies to Telegram instances as
    // well)
    public static String INSTANCE_PREFIX = "waInstance";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(12))
            .build();

    /**
     * Formats a raw recipient identifier into a GREEN-API Telegram chatId:
     * - Group / Channel ID (e.g. "-1001234567890"): preserves negative numeric ID.
     * - Telegram Username (e.g. "@username"): preserves username format.
     * - Phone number / User ID (e.g. "+91 98220 12345" or "1234567890"): returns
     * clean numeric string (no @c.us for Telegram).
     */
    public static String formatTelegramChatId(String rawRecipient) {
        if (rawRecipient == null || rawRecipient.trim().isEmpty()) {
            return null;
        }
        String trimmed = rawRecipient.trim();

        // If it's a Telegram group / supergroup (negative ID)
        if (trimmed.startsWith("-")) {
            String digits = trimmed.substring(1).replaceAll("[^0-9]", "");
            return "-" + digits;
        }

        // If it's a Telegram username
        if (trimmed.startsWith("@")) {
            return trimmed;
        }

        // If it's a phone number or numeric user ID
        String cleaned = trimmed.replaceAll("[^0-9]", "");
        if (cleaned.startsWith("0")) {
            cleaned = cleaned.substring(1);
        }
        if (cleaned.length() == 10) {
            // Standard 10-digit number, default country code +91
            cleaned = "91" + cleaned;
        }
        if (!cleaned.endsWith("@c.us")) {
            cleaned = cleaned + "@c.us";
        }
        return cleaned;
    }

    public static String lastErrorMessage = null;
    public static String lastResponseBody = null;

    /**
     * Sends an emergency Telegram message to the recipient using GREEN-API Telegram
     * instance.
     * Endpoint: POST
     * {{apiUrl}}/waInstance{{idInstance}}/sendMessage/{{apiTokenInstance}}
     *
     * @param recipient   The recipient's Telegram chatId, numeric user ID, group
     *                    ID, or mobile number.
     * @param messageText The emergency SOS alert text.
     * @return true if GREEN-API accepted and dispatched the message (HTTP 200),
     *         false otherwise.
     */
    public static boolean sendTelegramMessage(String recipient, String messageText) {
        lastErrorMessage = null;
        lastResponseBody = null;

        String chatId = formatTelegramChatId(recipient);
        if (chatId == null || chatId.isEmpty()) {
            lastErrorMessage = "Invalid recipient: " + recipient;
            System.err.println("[Green-API Telegram] Invalid recipient: " + recipient);
            return false;
        }

        // Check if credentials are placeholders
        if (ID_INSTANCE == null || ID_INSTANCE.contains("YOUR_ID_INSTANCE") ||
                API_TOKEN_INSTANCE == null || API_TOKEN_INSTANCE.contains("YOUR_API_TOKEN")) {
            lastErrorMessage = "Credentials not configured in GreenApiService.java";
            System.out.println("=================================================================");
            System.out.println("[Green-API Telegram SIMULATOR] Green-API credentials not configured yet.");
            System.out.println("[Green-API Telegram SIMULATOR] Would send Telegram alert to: " + chatId);
            System.out.println("[Green-API Telegram SIMULATOR] Message Content:\n" + messageText);
            System.out.println(
                    "[Green-API Telegram SIMULATOR] Please set ID_INSTANCE and API_TOKEN_INSTANCE in GreenApiService.java.");
            System.out.println("=================================================================");
            return false;
        }

        try {
            String baseUrl = API_URL.endsWith("/") ? API_URL.substring(0, API_URL.length() - 1) : API_URL;
            String endpoint = baseUrl + "/" + INSTANCE_PREFIX + ID_INSTANCE.trim() + "/sendMessage/"
                    + API_TOKEN_INSTANCE.trim();

            JSONObject payload = new JSONObject();
            payload.put("chatId", chatId);
            payload.put("message", messageText);
            payload.put("typingTime", 1000);

            String requestBody = payload.toString();

            System.out.println("[Green-API Telegram] Transmitting Telegram alert to " + chatId + "...");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String responseBody = response.body();
            lastResponseBody = responseBody;

            if (statusCode == 200) {
                System.out.println("[Green-API Telegram] \u2713 Telegram alert dispatched successfully to " + chatId
                        + ": " + responseBody);
                return true;
            } else {
                lastErrorMessage = "HTTP " + statusCode + ": " + responseBody;
                try {
                    JSONObject errObj = new JSONObject(responseBody);
                    if (errObj.has("invokeStatus") && errObj.getJSONObject("invokeStatus").has("description")) {
                        lastErrorMessage = errObj.getJSONObject("invokeStatus").getString("description");
                    } else if (errObj.has("correspondentsStatus")
                            && errObj.getJSONObject("correspondentsStatus").has("description")) {
                        lastErrorMessage = errObj.getJSONObject("correspondentsStatus").getString("description");
                    } else if (errObj.has("message")) {
                        lastErrorMessage = errObj.getString("message");
                    }
                } catch (Exception ignored) {
                }

                System.err.println("[Green-API Telegram] \u2715 HTTP " + statusCode + " error sending to " + chatId
                        + ": " + responseBody);
                return false;
            }

        } catch (Exception e) {
            lastErrorMessage = e.getMessage();
            System.err.println("[Green-API Telegram Error] Exception sending to " + chatId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Universal dispatch method.
     */
    public static boolean sendMessage(String recipient, String messageText) {
        return sendTelegramMessage(recipient, messageText);
    }

    /**
     * Alias method for backward compatibility.
     */
    public static boolean sendWhatsAppMessage(String recipient, String messageText) {
        return sendTelegramMessage(recipient, messageText);
    }
}
