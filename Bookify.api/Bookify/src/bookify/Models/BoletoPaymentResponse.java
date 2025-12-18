/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Models;

/**
 *
 * @author luisfelippe
 */
public class BoletoPaymentResponse {

    private final String message;
    private final String payerName;
    private final double amount;
    private final String bankSlipBase64;
    private final String bankSlipMime;

    public BoletoPaymentResponse(
            String message,
            String payerName,
            double amount,
            String bankSlipBase64,
            String bankSlipMime
    ) {
        this.message = message;
        this.payerName = payerName;
        this.amount = amount;
        this.bankSlipBase64 = bankSlipBase64;
        this.bankSlipMime = bankSlipMime;
    }

    public static BoletoPaymentResponse fromJson(String json) {
        return new BoletoPaymentResponse(
                extract(json, "message"),
                extract(json, "payer_name"),
                Double.parseDouble(extract(json, "amount")),
                extract(json, "bank_slip"),
                extract(json, "bank_slip_mime")
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

    public String getPayerName() {
        return payerName;
    }

    public double getAmount() {
        return amount;
    }

    public String getBankSlipBase64() {
        return bankSlipBase64;
    }

    public String getBankSlipMime() {
        return bankSlipMime;
    }
}

