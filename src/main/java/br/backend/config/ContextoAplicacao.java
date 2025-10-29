package br.backend.config;

import br.backend.controlador.impl.CategoriaControladorImpl;
import br.backend.controlador.impl.ProdutoControladorImpl;
import br.backend.controlador.impl.RegistroControladorImpl;
import br.backend.dao.RegistroDAO;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.dao.impl.ProdutoDAOImpl;
import br.backend.dao.impl.RegistroDAOImpl;
import br.backend.database.Database;
import br.backend.servico.CategoriaServico;
import br.backend.servico.ProdutoServico;
import br.backend.servico.RegistroServico;

public class ContextoAplicacao {

    private final Database database;
    private final CategoriaControladorImpl categoriaControlador;
    private final ProdutoControladorImpl produtoControlador;
    private final RegistroControladorImpl registroControlador;

    public ContextoAplicacao() {
        this.database = new Database();

        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl(database);
        CategoriaServico categoriaServico = new CategoriaServico(categoriaDAO);

        this.categoriaControlador = new CategoriaControladorImpl(categoriaServico);

        RegistroDAO registroDAO = new RegistroDAOImpl(database);
        ProdutoDAOImpl produtoDAO = new ProdutoDAOImpl(database);
        ProdutoServico produtoServico = new ProdutoServico(produtoDAO, registroDAO);

        this.produtoControlador = new ProdutoControladorImpl(produtoServico);
        
        RegistroServico registroServico = new RegistroServico(registroDAO);
        this.registroControlador = new RegistroControladorImpl(registroServico);
    }

    public CategoriaControladorImpl getCategoriaControlador() {
        return categoriaControlador;
    }

    public ProdutoControladorImpl getProdutoControlador() {
        return produtoControlador;
    }

    public RegistroControladorImpl getRegistroControlador() {
        return registroControlador;
    }

    public Database getDatabase() {
        // Útil para fechar a conexão de forma centralizada no Main
        return database;
    }
}
