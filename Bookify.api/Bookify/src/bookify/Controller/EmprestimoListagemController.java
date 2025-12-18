package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class EmprestimoListagemController extends TelasController implements Initializable {

    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();

    @FXML
    private ToggleButton atrasadosBtn;

    @FXML
    private Pane mainContainer;

    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;

    private void adicionarComponente(
        HBox box,
        ResultSet res,
        boolean isEncerrado
    ) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Emprestimo-componente-window.fxml"));
        Pane painel = loader.load();

        EmprestimoComponenteController componente = loader.getController();

        String idEmprestimo = res.getString("id_emprestimo");

        componente.setTexto(
            res.getString("titulo_livro"),
            res.getString("nome_usuario"),
            res.getString("id_usuario"),
            res.getString("id_usuario"),
            res.getString("data_emprestimo"),
            res.getString("data_devolucao")
        );

        Map<String, String> values = new HashMap<>();
        values.put("id_emprestimo", idEmprestimo);
        values.put("id_usuario", res.getString("id_usuario"));
        values.put("id_livro", res.getString("id_livro"));
        values.put("titulo_livro", res.getString("titulo_livro"));
        values.put("autor_livro", res.getString("autor_livro"));
        values.put("nome_usuario", res.getString("nome_usuario"));
        values.put("turma_usuario", res.getString("turma_usuario"));
        values.put("telefone_usuario", res.getString("telefone_usuario"));
        values.put("volume_livro", res.getString("volume_livro"));
        values.put("exemplar_livro", res.getString("exemplar_livro"));
        values.put("data_emprestimo", res.getString("data_emprestimo"));
        values.put("data_devolucao", res.getString("data_devolucao"));

        EmprestimoComponenteController.EmprestimoStatus status;

        if (isEncerrado) {
            status = EmprestimoComponenteController.EmprestimoStatus.ENCERRADO;
        } else if (LocalDate.now().isAfter(LocalDate.parse(res.getString("data_devolucao")))) {
            status = EmprestimoComponenteController.EmprestimoStatus.ATRASADO;
        } else {
            status = EmprestimoComponenteController.EmprestimoStatus.ATIVO;
        }

        componente.setStatus(status);

        componente.setEvento(() -> {
            switch (status) {
                case ATIVO, ATRASADO ->
                    emprestimoManipulador(idEmprestimo, mainContainer, values, status);

                case ENCERRADO ->
                    abrirPagamento(values);
            }
        });
   

        box.getChildren().add(painel);
    }

    private void emprestimoManipulador(
        String id,
        Pane mainContainer,
        Map<String, String> values,
        EmprestimoComponenteController.EmprestimoStatus status
    ) {

        try {
            FXMLLoader loaderPopup = new FXMLLoader();
            loaderPopup.setLocation(getClass().getResource("../View/Popup-emprestimo.fxml"));
            Pane popup = loaderPopup.load();

            mainContainer.getChildren().add(popup);

            PopupEmprestimoController popupController = loaderPopup.getController();

            popupController.setInfo(
                values.getOrDefault("titulo_livro", ""),
                values.getOrDefault("id_livro", ""),
                values.getOrDefault("autor_livro", ""),
                values.getOrDefault("nome_usuario", ""),
                values.getOrDefault("id_usuario", ""),
                values.getOrDefault("nome_usuario", ""),
                values.getOrDefault("data_emprestimo", ""),
                values.getOrDefault("data_devolucao", "")
            );

            if (status == EmprestimoComponenteController.EmprestimoStatus.ATIVO) {

                popupController.setEncerrarManipulador(() ->
                    encerrarManipulador(popup, mainContainer, id, values)
                );

                popupController.setRenovarManipulador(() ->
                    renovarManipulador(popup, mainContainer, id)
                );

            } else if (status == EmprestimoComponenteController.EmprestimoStatus.ATRASADO) {

                popupController.setEncerrarManipulador(() ->
                    encerrarManipulador(popup, mainContainer, id, values)
                );

                popupController.setRenovarManipulador(null); // ou regularizar depois
            }

            popupController.setFecharManipulador(() ->
                mainContainer.getChildren().remove(popup)
            );

        } catch (IOException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
    
    private void abrirPagamento(Map<String, String> values) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("../View/Popup-pagamento.fxml")
            );

            Pane popup = loader.load();
            mainContainer.getChildren().add(popup);

            PopupPagamentoController controller = loader.getController();

            controller.setDados(values);

            controller.setFecharManipulador(() ->
                mainContainer.getChildren().remove(popup)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void encerrarManipulador(
        Pane popup,
        Pane mainContainer,
        String id,
        Map<String, String> values
    ) {

        String[] columns = {
            "data_emprestimo",
            "data_devolucao",
            "id_usuario",
            "id_livro",
            "titulo_livro",
            "volume_livro",
            "exemplar_livro",
            "nome_usuario",
            "turma_usuario",
            "telefone_usuario",
            "autor_livro"
        };

        String[] valuesSave = {
            values.get("data_emprestimo"),
            LocalDate.now().toString(),
            values.get("id_usuario"),
            values.get("id_livro"),
            values.get("titulo_livro"),
            values.get("volume_livro"),
            values.get("exemplar_livro"),
            values.get("nome_usuario"),
            values.get("turma_usuario"),
            values.get("telefone_usuario"),
            values.get("autor_livro")
        };

        try {
            repositorio.save("emprestimos_encerrados", columns, valuesSave);
            repositorio.delete("emprestimo", "id_emprestimo = '" + id + "'");
            mainContainer.getChildren().remove(popup);
            buscar();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    private void renovarManipulador(Pane popup, Pane mainContainer, String id) {
        try {
            repositorio.update(
                "emprestimo",
                new String[]{"data_devolucao"},
                new String[]{LocalDate.now().plusDays(5).toString()},
                "id_emprestimo = '" + id + "'"
            );
            mainContainer.getChildren().remove(popup);
            buscar();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void buscar() {
        render_box_elements.getChildren().clear();

        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format(
            "UPPER(nome_usuario) like '%%%s%%' OR UPPER(titulo_livro) like '%%%s%%' " +
            "ORDER BY data_devolucao asc",
            searchBar, searchBar
        );

        try {
            boolean isEncerrado = atrasadosBtn.isSelected();
            ResultSet response = repositorio.get(
                isEncerrado ? "emprestimos_encerrados" : "emprestimo",
                consult
            );

            HBox box = null;
            boolean novaLinha = true;

            while (response.next()) {
                if (novaLinha) {
                    box = new HBox();
                    render_box_elements.getChildren().add(box);
                }
                adicionarComponente(box, response, isEncerrado);
                novaLinha = !novaLinha;
            }

        } catch (SQLException | IOException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    protected void buscarTeclaPressionada(){
        pesquisarText.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buscar();
    }
}

