/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Strategy;

import bookify.Controller.AlunosCadastroController;
import bookify.Interface.IValidadorCadastro;

/**
 *
 * @author luisfelippe
 */
public class ValidadorAluno implements IValidadorCadastro {
    private AlunosCadastroController controller;

    public ValidadorAluno(AlunosCadastroController controller) {
        this.controller = controller;
    }

    @Override
    public String validar() {
        if (controller.aluTextCurso.getText().isEmpty()
                || controller.aluTextEmail.getText().isEmpty()
                || controller.aluTextMatricula.getText().isEmpty()
                || controller.aluTextNome.getText().isEmpty()
                || controller.Turma.getValue() == null
                || controller.aluTextTelefone.getText().isEmpty()) {
            return "Preencha todos os campos!";
        }
        return null;
    }
}

