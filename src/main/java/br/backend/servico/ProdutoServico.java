package br.backend.servico;

import br.backend.dao.ProdutoDAO;
import br.backend.modelo.Categoria;
import br.backend.modelo.Produto;

public class ProdutoServico {

    private final ProdutoDAO produtoDAO;

    public ProdutoServico(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public Produto inserirProduto(String nome, Double preco, String unidade, Categoria categoria,
            Integer quantidade, Integer quantidadeMinima, Integer quantidadeMaxima) {

        Produto pro = new Produto(null, nome, preco, unidade, categoria, quantidade, quantidadeMinima, quantidadeMaxima);
        produtoDAO.inserirProduto(pro);
        return pro;
    }

    public Produto atualizarProduto(Integer id, Produto novoProduto) {
        produtoDAO.atualizarProduto(id, novoProduto);
        return produtoDAO.buscarPorId(id);
    }

}
