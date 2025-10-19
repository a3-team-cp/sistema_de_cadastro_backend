package br.backend.dao;

import br.backend.modelo.Produto;
import java.util.List;

public interface ProdutoDAO {

    void inserirProduto(Produto obj);

    void atualizarProduto(Integer id, Produto novoProduto);

    Produto buscarPorId(Integer id);

    List<Produto> resgatarProdutos();

}
