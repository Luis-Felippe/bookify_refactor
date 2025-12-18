/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Models;

/**
 *
 * @author luisfelippe
 */
public class PixPaymentRequest {

    private final String merchantName;
    private final String pixKey;
    private final double amount;
    private final String merchantCity;
    private final String transactionId;

    public PixPaymentRequest(
            String merchantName,
            String pixKey,
            double amount,
            String merchantCity,
            String transactionId
    ) {
        this.merchantName = merchantName;
        this.pixKey = pixKey;
        this.amount = amount;
        this.merchantCity = merchantCity;
        this.transactionId = transactionId;
    }

    public String toJson() {
        return "{"
                + "\"merchant_name\":\"" + merchantName + "\","
                + "\"pix_key\":\"" + pixKey + "\","
                + "\"amount\":" + amount + ","
                + "\"merchant_city\":\"" + merchantCity + "\","
                + "\"transaction_id\":\"" + transactionId + "\""
                + "}";
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getPixKey() {
        return pixKey;
    }

    public double getAmount() {
        return amount;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public String getTransactionId() {
        return transactionId;
    }
}

