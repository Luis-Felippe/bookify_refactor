/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Factory;

import bookify.Controller.AlunosCadastroController;
import bookify.Controller.LivrosCadastroController;
import bookify.Controller.ProfessorCadastroController;
import bookify.Interface.IValidadorCadastro;
import bookify.Strategy.ValidadorAluno;
import bookify.Strategy.ValidadorLivro;
import bookify.Strategy.ValidadorProfessor;

/**
 *
 * @author luisfelippe
 */
public class FabricaValidadorCadastro {
    
    public static IValidadorCadastro criar(String tipo, Object controller) {
        return switch (tipo.toLowerCase()) {
            case "aluno" -> new ValidadorAluno((AlunosCadastroController) controller);
            case "livro" -> new ValidadorLivro((LivrosCadastroController) controller);
            case "professor" -> new ValidadorProfessor((ProfessorCadastroController) controller);
            default -> throw new IllegalArgumentException("Tipo de validador desconhecido: " + tipo);
        };
    }
}

