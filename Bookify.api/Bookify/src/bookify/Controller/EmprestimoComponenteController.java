package bookify.Controller;

import bookify.Interface.IButtonHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class EmprestimoComponenteController {

    public enum EmprestimoStatus {
        ATIVO,
        ATRASADO,
        ENCERRADO
    }

    private IButtonHandler event;

    @FXML
    private StackPane statusPane;

    @FXML
    private Text statusText;

    @FXML
    private Text dataDevolucaoText;

    @FXML
    private Text dataInicioText;

    @FXML
    private Text idText;

    @FXML
    private Text nomeText;

    @FXML
    private Text tituloText;

    @FXML
    private Text clienteText;

    // =====================================
    // TEXTO DO COMPONENTE
    // =====================================
    protected void setTexto(
        String titulo,
        String nome,
        String matricula,
        String cpf,
        String dataInicio,
        String dataDevolucao
    ) {

        if (titulo != null && titulo.length() > 30) {
            titulo = titulo.substring(0, 30) + "...";
        }

        if (nome != null && nome.length() > 40) {
            nome = nome.substring(0, 40) + "...";
        }

        tituloText.setText(titulo != null ? titulo : "");
        nomeText.setText(nome != null ? nome : "");

        if (matricula == null || matricula.isBlank()) {
            idText.setText(cpf != null ? cpf : "");
            clienteText.setText("Professor:");
        } else {
            idText.setText(matricula);
            clienteText.setText("Aluno:");
        }

        dataInicioText.setText(formataData(dataInicio));
        dataDevolucaoText.setText(formataData(dataDevolucao));
    }

    // =====================================
    // STATUS VISUAL
    // =====================================
    protected void setStatus(EmprestimoStatus status) {

        switch (status) {
            case ATIVO -> {
                statusPane.setStyle(
                    "-fx-background-color: #42f58a; -fx-background-radius: 20px"
                );
                statusText.setText("ATIVO");
            }

            case ATRASADO -> {
                statusPane.setStyle(
                    "-fx-background-color: #eb3434; -fx-background-radius: 20px"
                );
                statusText.setText("ATRASADO");
            }

            case ENCERRADO -> {
                statusPane.setStyle(
                    "-fx-background-color: #9e9e9e; -fx-background-radius: 20px"
                );
                statusText.setText("ENCERRADO");
            }
        }

        statusPane.setAlignment(statusText, javafx.geometry.Pos.CENTER);
    }

    // =====================================
    // EVENTO DE CLIQUE (DELEGADO)
    // =====================================
    public void setEvento(IButtonHandler event) {
        this.event = event;
    }

    @FXML
    protected void ManipuladorEvento() {
        if (event != null) {
            event.handler();
        }
    }

    // =====================================
    // FORMATA DATA
    // =====================================
    private String formataData(String info) {
        if (info == null || info.isBlank()) {
            return "";
        }

        try {
            SimpleDateFormat formatoAtual = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat novoFormato = new SimpleDateFormat("dd/MM/yyyy");
            Date data = formatoAtual.parse(info);
            return novoFormato.format(data);
        } catch (ParseException ex) {
            Logger.getLogger(EmprestimoComponenteController.class.getName())
                  .log(Level.SEVERE, null, ex);
            return "";
        }
    }
}



