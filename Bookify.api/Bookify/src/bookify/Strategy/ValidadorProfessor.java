/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Strategy;

import bookify.Controller.ProfessorCadastroController;
import bookify.Interface.IValidadorCadastro;

/**
 *
 * @author luisfelippe
 */
public class ValidadorProfessor implements IValidadorCadastro {

    private final ProfessorCadastroController controller;
    private String mensagemErro = "";

    public ValidadorProfessor(ProfessorCadastroController controller) {
        this.controller = controller;
    }

    @Override
    public String validar() {
        if (controller.profTextNome.getText().isEmpty() ||
            controller.profTextTelefone.getText().isEmpty() ||
            controller.profTextCpf.getText().isEmpty() ||
            controller.profTextDisciplina.getText().isEmpty() ||
            controller.profTextEmail.getText().isEmpty()) {
            
            mensagemErro = "Preencha todos os campos!";
            return mensagemErro;
        }
        return null;
    }
}

