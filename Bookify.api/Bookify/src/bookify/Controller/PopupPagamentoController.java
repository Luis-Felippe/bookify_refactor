/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Controller;

/**
 *
 * @author luisfelippe
 */

import bookify.Models.BoletoPaymentRequest;
import bookify.Models.BoletoPaymentResponse;
import bookify.Models.PaymentMicroservice;
import bookify.Models.PixPaymentRequest;
import bookify.Models.PixPaymentResponse;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Map;
import javafx.concurrent.Task;

public class PopupPagamentoController {

    private final PaymentMicroservice paymentService = new PaymentMicroservice();
    private Map<String, String> values;
    private Runnable fecharManipulador;


    @FXML
    private VBox conteudoBox;

    @FXML
    private ImageView qrCodeImage;

    @FXML
    private Text boletoInfoText;

    @FXML
    private Button baixarBoletoBtn;

    @FXML
    private Text errorText;

    @FXML
    private Pane mainContainer;

    // ==========================
    // RECEBE DADOS DO EMPRÉSTIMO
    // ==========================
    public void setDados(Map<String, String> values) {
        this.values = values;
    }

    // ==========================
    // PIX
    // ==========================
    @FXML
    private void selecionarPix() {

        qrCodeImage.setVisible(true);
        boletoInfoText.setVisible(false);
        baixarBoletoBtn.setVisible(false);

        try {
            PixPaymentRequest request = new PixPaymentRequest(
            values.get("nome_usuario"),        // merchantName
            "chave-pix@exemplo.com",            // pixKey
            10.00,                              // amount
            "Minha Cidade",                     // merchantCity
            values.get("id_emprestimo")         // transactionId
        );


            PixPaymentResponse response =
                paymentService.createPixPayment(request);

            String base64 = response.getQrcodeBase64();

            // Remove prefixo data:image/... se existir
            if (base64.contains(",")) {
                base64 = base64.substring(base64.indexOf(",") + 1);
            }

            // Remove aspas, espaços e quebras de linha
            base64 = base64
                .replace("\"", "")
                .replace("\n", "")
                .replace("\r", "")
                .replace(" ", "")
                .trim();

            // (opcional, mas recomendado) valida caracteres
            base64 = base64.replaceAll("[^A-Za-z0-9+/=]", "");

            byte[] imageBytes = Base64.getDecoder().decode(base64);

            Image qrImage = new Image(new ByteArrayInputStream(imageBytes));
            qrCodeImage.setImage(qrImage);



        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }
    }

    // ==========================
    // BOLETO
    // ==========================
    @FXML
    private void selecionarBoleto() {

        qrCodeImage.setVisible(false);
        boletoInfoText.setVisible(true);
        baixarBoletoBtn.setVisible(true);
    }

    @FXML
    private void baixarBoleto() {

        errorText.setText("Gerando boleto, aguarde...");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                BoletoPaymentRequest request = new BoletoPaymentRequest(
                    values.get("nome_usuario"),
                    values.get("id_usuario"),
                    10.00,
                    "email@exemplo.com",
                    "11999999999",
                    "Rua Exemplo, 123",
                    "Minha Cidade",
                    values.get("id_emprestimo")
                );

                BoletoPaymentResponse response =
                    paymentService.createBoletoPayment(request);

                String base64 = response.getBankSlipBase64();

                if (base64.contains(",")) {
                    base64 = base64.substring(base64.indexOf(",") + 1);
                }

                base64 = base64
                    .replace("\"", "")
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace(" ", "")
                    .trim()
                    .replaceAll("[^A-Za-z0-9+/=]", "");

                byte[] pdfBytes = Base64.getDecoder().decode(base64);

                File boleto = new File("boleto_pagamento.pdf");
                try (FileOutputStream fos = new FileOutputStream(boleto)) {
                    fos.write(pdfBytes);
                }

                Desktop.getDesktop().open(boleto);

                return null;
            }
        };

        task.setOnSucceeded(e ->
            errorText.setText("Boleto gerado com sucesso.")
        );

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            errorText.setText(
                ex != null ? ex.getMessage() : "Erro ao gerar boleto"
            );
        });

        new Thread(task).start();
    }

    public void setFecharManipulador(Runnable fecharManipulador) {
        this.fecharManipulador = fecharManipulador;
    }


    // ==========================
    // FECHAR MODAL
    // ==========================
    @FXML
    private void fechar() {
        if (fecharManipulador != null) {
            fecharManipulador.run();
        }
    }




}


