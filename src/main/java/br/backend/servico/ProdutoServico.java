package br.backend.servico;

import br.backend.dao.ProdutoDAO;
import br.backend.dao.impl.ProdutoDAOImpl;
import br.backend.database.Database;

public class ProdutoServico {

    private ProdutoDAO produtoDAO;

    public ProdutoServico(ProdutoDAOImpl produtoDAO){
        this.produtoDAO = produtoDAO;
    }


}
