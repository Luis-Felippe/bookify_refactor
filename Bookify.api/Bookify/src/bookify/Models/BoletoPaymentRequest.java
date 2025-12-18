package bookify.Models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisfelippe
 */
public class BoletoPaymentRequest {

    private final String payerName;
    private final String payerDocument;
    private final double amount;
    private final String payerAddress;
    private final String payerNeighborhood;
    private final String payerCity;
    private final String payerState;
    private final String payerZip;

    public BoletoPaymentRequest(
            String payerName,
            String payerDocument,
            double amount,
            String payerAddress,
            String payerNeighborhood,
            String payerCity,
            String payerState,
            String payerZip
    ) {
        this.payerName = payerName;
        this.payerDocument = payerDocument;
        this.amount = amount;
        this.payerAddress = payerAddress;
        this.payerNeighborhood = payerNeighborhood;
        this.payerCity = payerCity;
        this.payerState = payerState;
        this.payerZip = payerZip;
    }

    public boolean isValid() {
        return payerName != null &&
               payerDocument != null &&
               payerAddress != null &&
               payerNeighborhood != null &&
               payerCity != null &&
               payerState != null &&
               payerZip != null &&
               amount > 0;
    }

    public String toJson() {
        return "{"
                + "\"payer_name\":\"" + payerName + "\","
                + "\"payer_document\":\"" + payerDocument + "\","
                + "\"amount\":" + amount + ","
                + "\"payer_address\":\"" + payerAddress + "\","
                + "\"payer_neighborhood\":\"" + payerNeighborhood + "\","
                + "\"payer_city\":\"" + payerCity + "\","
                + "\"payer_state\":\"" + payerState + "\","
                + "\"payer_zip\":\"" + payerZip + "\""
                + "}";
    }
}

