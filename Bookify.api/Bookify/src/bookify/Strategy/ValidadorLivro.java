/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Strategy;

import bookify.Controller.LivrosCadastroController;
import bookify.Interface.IValidadorCadastro;

/**
 *
 * @author luisfelippe
 */
public class ValidadorLivro implements IValidadorCadastro {
    private LivrosCadastroController controller;

    public ValidadorLivro(LivrosCadastroController controller) {
        this.controller = controller;
    }

    @Override
    public String validar() {
        if (controller.livroTextNumReg.getText().isEmpty()
                || controller.livroTextTitulo.getText().isEmpty()
                || controller.livroTextAutor.getText().isEmpty()
                || controller.livroTextVolume.getText().isEmpty()
                || controller.livroTextExemplar.getText().isEmpty()
                || controller.livroTextLocal.getText().isEmpty()
                || controller.livroTextData.getEditor().getText().isEmpty()
                || controller.livroTextEditora.getText().isEmpty()
                || controller.livroTextAnoPublicacao.getText().isEmpty()
                || controller.livroTextFormaAquisicao.getText().isEmpty()
                || controller.livroTextCategoria.getText().isEmpty()) {
            return "Preencha todos os campos!";
        }
        return null;
    }
}

