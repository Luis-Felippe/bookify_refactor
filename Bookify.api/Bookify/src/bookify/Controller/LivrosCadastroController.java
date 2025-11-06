package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Factory.FabricaValidadorCadastro;
import bookify.Interface.ICadastrar;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Interface.IValidadorCadastro;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LivrosCadastroController extends TelasLivrosController implements ICadastrar{
    
    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    public  Pane mainContainer;
    
    @FXML
    public  Text erroText;
    
    @FXML
    public  TextField livroTextNumReg;
    
    @FXML
    public  TextField livroTextTitulo;
    
    @FXML
    public  TextField livroTextAutor;
    
    @FXML
    public  TextField livroTextVolume;
    
    @FXML
    public  TextField livroTextCategoria;
    
    @FXML
    public  TextField livroTextExemplar;
    
    @FXML
    public  TextField livroTextLocal;
    
    @FXML
    public  DatePicker livroTextData;
    
    @FXML
    public  TextField livroTextEditora;
    
    @FXML
    public  TextField livroTextAnoPublicacao;
    
    @FXML
    public  TextField livroTextFormaAquisicao;
    
    @FXML
    public TextField livroTextObservacao;
    
    // Cadastra um livro no banco de dados
    @FXML
    public void cadastrar(ActionEvent evento) throws IOException{
        IValidadorCadastro validador = FabricaValidadorCadastro.criar("livro", this);
        String erro = validador.validar();
        if (erro != null) {
            this.erroText.setText(erro);   
        } else {
            String [] columns = {
                "num_registro", "titulo", "autor", "volume", "exemplar", "local", "data", "editora", 
                "ano_publicacao", "forma_aquisicao", "observacao", "disponibilidade", "categoria"
            };
            
            String obs = "";
            if(!(this.livroTextObservacao.getText() == null)) obs = this.livroTextObservacao.getText();
            String [] values = {
                this.livroTextNumReg.getText(),
                this.livroTextTitulo.getText(),
                this.livroTextAutor.getText(),
                this.livroTextVolume.getText(),
                this.livroTextExemplar.getText(),
                this.livroTextLocal.getText(),
                this.livroTextData.getEditor().getText(),
                this.livroTextEditora.getText(),
                this.livroTextAnoPublicacao.getText(),
                this.livroTextFormaAquisicao.getText(),
                obs,
                "true",
                this.livroTextCategoria.getText()
           };
            try {
                repositorio.save("livro",columns, values);
            } catch (SQLException ex) {
                erroText.setText("Erro: código do livro já existe");
                return;
            }
            IPopupMsg controller =  MsgFabrica.criaPopupMsg("PopupCadastrarMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            this.erroText.setText("");
        }
    }
}
