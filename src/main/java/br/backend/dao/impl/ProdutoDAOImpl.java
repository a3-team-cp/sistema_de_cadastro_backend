package br.backend.dao.impl;

import br.backend.dao.ProdutoDAO;
import br.backend.database.Database;

public class ProdutoDAOImpl implements ProdutoDAO {

    Database database;

    public ProdutoDAOImpl(Database database) {
        this.database = database;
    }


}
