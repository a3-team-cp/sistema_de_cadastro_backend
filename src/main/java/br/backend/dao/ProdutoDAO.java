package br.backend.dao;

import br.backend.modelo.Produto;
import java.util.List;

public interface ProdutoDAO {

    void inserirProduto(Produto obj);

    Produto atualizarProduto(Integer id, Produto novoProduto);

    Produto buscarPorId(Integer id);

    void deletarPorId(Integer id);

    List<Produto> resgatarTodosProdutos();

    void aumentarValorProduto(Double percentual);

    void diminuirValorProduto(Double percentual);
}
