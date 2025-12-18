/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Models;

/**
 *
 * @author luisfelippe
 */
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PaymentMicroservice {

    private final HttpClient httpClient;
    private final String baseUrl;

    public PaymentMicroservice() {
        this.baseUrl = "https://systemless-ineludibly-sachiko.ngrok-free.dev";
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    // PIX PAYMENT
    public PixPaymentResponse createPixPayment(PixPaymentRequest request) throws Exception {

        validatePixRequest(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/generate_pix"))
                .timeout(Duration.ofSeconds(15))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(request.toJson()))
                .build();

        HttpResponse<String> response =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 400) {
            throw new RuntimeException("Requisição inválida: " + response.body());
        }

        if (response.statusCode() == 500) {
            throw new RuntimeException("Erro interno do servidor: " + response.body());
        }

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Erro inesperado (" + response.statusCode() + "): " + response.body()
            );
        }

        return PixPaymentResponse.fromJson(response.body());
    }

    private void validatePixRequest(PixPaymentRequest r) {
    if (r == null ||
        r.getMerchantName() == null ||
        r.getPixKey() == null ||
        r.getMerchantCity() == null ||
        r.getTransactionId() == null ||
        r.getAmount() <= 0) {
        throw new IllegalArgumentException("Campos obrigatórios do PIX não informados");
    }
}


    public BoletoPaymentResponse createBoletoPayment(BoletoPaymentRequest request) throws Exception {

        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("Campos obrigatórios do boleto não informados");
        }

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/generate_bankslip"))
            .timeout(Duration.ofSeconds(15))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(request.toJson()))
            .build();


        HttpResponse<String> response =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 400) {
        throw new RuntimeException("Requisição inválida: " + response.body());
    }

    if (response.statusCode() == 500) {
        throw new RuntimeException("Erro interno do servidor: " + response.body());
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException(
                "Erro inesperado (" + response.statusCode() + "): " + response.body()
        );
    }


        return BoletoPaymentResponse.fromJson(response.body());
    }
}

