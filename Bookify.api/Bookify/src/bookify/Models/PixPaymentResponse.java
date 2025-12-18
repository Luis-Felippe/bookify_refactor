/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Models;

/**
 *
 * @author luisfelippe
 */
public class PixPaymentResponse {

    private final String message;
    private final String merchantName;
    private final double amount;
    private final String payload;
    private final String qrcodeBase64;
    private final String qrcodeMime;

    public PixPaymentResponse(
            String message,
            String merchantName,
            double amount,
            String payload,
            String qrcodeBase64,
            String qrcodeMime
    ) {
        this.message = message;
        this.merchantName = merchantName;
        this.amount = amount;
        this.payload = payload;
        this.qrcodeBase64 = qrcodeBase64;
        this.qrcodeMime = qrcodeMime;
    }

    public static PixPaymentResponse fromJson(String json) {
        return new PixPaymentResponse(
                extract(json, "message"),
                extract(json, "merchant_name"),
                Double.parseDouble(extract(json, "amount")),
                extract(json, "payload"),
                extract(json, "qrcode_base64"),
                extract(json, "qrcode_mime")
        );
    }

    private static String extract(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start == -1) return null;

        start += key.length();

        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public double getAmount() {
        return amount;
    }

    public String getPayload() {
        return payload;
    }

    public String getQrcodeBase64() {
        return qrcodeBase64;
    }

    public String getQrcodeMime() {
        return qrcodeMime;
    }
}

