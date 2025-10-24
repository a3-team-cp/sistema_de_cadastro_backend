package br.backend;

import br.backend.config.ContextoAplicacao;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.database.Database;
import br.backend.modelo.Categoria;
import br.backend.modelo.enums.Embalagem;
import br.backend.modelo.enums.Tamanho;
import br.backend.servidor.Servidor;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("Iniciando api");
        ContextoAplicacao contexto = new ContextoAplicacao();

        Servidor servidor = new Servidor(
                3001,
                contexto.getCategoriaControlador(),
                contexto.getProdutoControlador());
        servidor.iniciar();
    }
}