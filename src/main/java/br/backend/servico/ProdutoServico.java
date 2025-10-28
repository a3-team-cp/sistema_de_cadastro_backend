package br.backend.servico;

import br.backend.dao.ProdutoDAO;
import br.backend.dao.RegistroDAO;
import br.backend.modelo.Categoria;
import br.backend.modelo.Produto;
import br.backend.modelo.Registro;
import br.backend.modelo.enums.Movimentacao;
import br.backend.modelo.enums.Status;

import java.util.Date;
import java.util.List;

public class ProdutoServico {

    private final ProdutoDAO produtoDAO;
    private final RegistroService registroService;

    public ProdutoServico(ProdutoDAO produtoDAO, RegistroDAO registroDAO) {
        this.produtoDAO = produtoDAO;
        this.registroService = new RegistroService(registroDAO);
    }

    public Produto inserirProduto(Produto produto) {
        // Insere o produto no banco
        produtoDAO.inserirProduto(produto);

        // Cria o registro de movimentação
        Registro r = new Registro();
        r.setData(new Date());
        r.setTipoDoProduto(produto);
        r.setMovimentacao(Movimentacao.ENTRADA);
        r.setQuantidade(produto.getQuantidade());
        r.setStatus(Status.ADICIONADO);

        registroService.inserirRegistro(r);

        return produto;
    }


    public Produto atualizarProduto(Integer id, Produto novoProduto) {
        // Atualiza o produto no banco
        produtoDAO.atualizarProduto(id, novoProduto);

        // Busca o produto atualizado (para registrar o estado final)
        Produto produtoAtualizado = produtoDAO.buscarPorId(id);

        // Cria um novo registro de movimentação
        Registro r = new Registro();
        r.setData(new Date());
        r.setTipoDoProduto(produtoAtualizado);
        r.setMovimentacao(Movimentacao.NENHUM); // se o enum Movimentacao tiver ALTERACAO, senão use NENHUM
        r.setQuantidade(produtoAtualizado.getQuantidade());
        r.setStatus(Status.NOMEALTERADO); // agora usamos um valor existente no enum

        // Insere o registro via serviço
        registroService.inserirRegistro(r);

        return produtoAtualizado;
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.resgatarTodosProdutos();
    }

    public boolean deletarProduto(Integer id) {
        Produto existente = produtoDAO.buscarPorId(id);
        if (existente == null) {
            return false;
        }

        produtoDAO.deletarPorId(id);

        // Cria um registro da deleção
        Registro r = new Registro();
        r.setData(new Date());
        r.setTipoDoProduto(existente);
        r.setMovimentacao(Movimentacao.SAIDA); // ou "EXCLUSAO" se existir no enum
        r.setQuantidade(existente.getQuantidade());
        r.setStatus(Status.DELETADO);

        registroService.inserirRegistro(r);

        return true;
    }

    public Produto buscarPorId(Integer id) {
        return produtoDAO.buscarPorId(id);
    }

}
