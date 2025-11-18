package br.backend.servico;

import br.backend.dao.ProdutoDAO;
import br.backend.dao.RegistroDAO;
import br.backend.modelo.Produto;
import br.backend.modelo.Registro;
import br.backend.modelo.enums.Movimentacao;
import br.backend.modelo.enums.Status;

import java.util.Date;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio relacionadas à entidade {@link Produto},
 * incluindo criação, atualização, remoção e manipulação de preços.
 *
 * <p>Além das operações básicas com produtos, este serviço registra automaticamente
 * movimentações no estoque utilizando {@link RegistroServico}, garantindo histórico
 * completo das ações realizadas.</p>
 *
 * <p>As movimentações registradas incluem:
 * <ul>
 *   <li><b>ENTRADA</b> — quando um produto é criado;</li>
 *   <li><b>NENHUM</b> — quando o produto é apenas atualizado (ex.: nome ou quantidade);</li>
 *   <li><b>SAIDA</b> — quando um produto é deletado (soft delete);</li>
 * </ul>
 * Em conjunto, o {@link Status} é usado para caracterizar o tipo de alteração.</p>
 */
public class ProdutoServico {

    private final ProdutoDAO produtoDAO;
    private final RegistroServico registroService;

    /**
     * Construtor padrão.
     *
     * <p>Recebe o {@link ProdutoDAO} e o {@link RegistroDAO}, criando internamente
     * o {@link RegistroServico} responsável por registrar as movimentações.</p>
     *
     * @param produtoDAO DAO responsável pelas operações de persistência de produtos
     * @param registroDAO DAO usado para persistir registros de movimentações
     */
    public ProdutoServico(ProdutoDAO produtoDAO, RegistroDAO registroDAO) {
        this.produtoDAO = produtoDAO;
        this.registroService = new RegistroServico(registroDAO);
    }

    /**
     * Insere um novo produto e registra automaticamente uma movimentação de ENTRADA.
     *
     * @param produto objeto a ser inserido no sistema
     * @return o produto inserido, com ID preenchido
     */
    public Produto inserirProduto(Produto produto) {
        // Insere o produto no banco
        produtoDAO.inserirProduto(produto);

        // Cria o registro de movimentação
        Registro r = new Registro();
        r.setData(new Date());
        r.setProdutoId(produto.getId());
        r.setMovimentacao(Movimentacao.ENTRADA);
        r.setQuantidade(produto.getQuantidade());
        r.setStatus(Status.ADICIONADO);

        registroService.inserirRegistro(r);

        return produto;
    }

    /**
     * Atualiza um produto existente e registra a movimentação correspondente.
     *
     * <p>A movimentação registrada usa {@link Movimentacao#NENHUM}, já que não há
     * entrada ou saída de estoque, apenas modificação de dados.</p>
     *
     * @param id ID do produto a ser atualizado
     * @param novoProduto dados atualizados do produto
     * @return o produto atualizado
     */
    public Produto atualizarProduto(Integer id, Produto novoProduto) {
        // Atualiza o produto no banco
        produtoDAO.atualizarProduto(id, novoProduto);

        // Busca o produto atualizado (para registrar o estado final)
        Produto produtoAtualizado = produtoDAO.buscarPorId(id);

        // Cria um novo registro de movimentação
        Registro r = new Registro();
        r.setData(new Date());
        r.setProdutoId(id);
        r.setMovimentacao(Movimentacao.NENHUM); // se o enum Movimentacao tiver ALTERACAO, senão use NENHUM
        r.setQuantidade(produtoAtualizado.getQuantidade());
        r.setStatus(Status.NOMEALTERADO); // agora usamos um valor existente no enum

        // Insere o registro via serviço
        registroService.inserirRegistro(r);

        return produtoAtualizado;
    }


    /**
     * Lista todos os produtos ativos cadastrados.
     *
     * @return lista de produtos
     */
    public List<Produto> listarProdutos() {
        return produtoDAO.resgatarTodosProdutos();
    }

    /**
     * Deleta (soft delete) um produto e registra a movimentação correspondente.
     *
     * <p>A movimentação registrada usa {@link Movimentacao#SAIDA} e o status
     * {@link Status#DELETADO}.</p>
     *
     * @param id ID do produto a ser removido
     * @return {@code true} se o produto foi removido; {@code false} se não existir
     */
    public boolean deletarProduto(Integer id) {
        Produto existente = produtoDAO.buscarPorId(id);
        if (existente == null) {
            return false;
        }

        produtoDAO.deletarPorId(id);

        Registro r = new Registro();
        r.setData(new Date());
        r.setProdutoId(existente.getId());
        r.setMovimentacao(Movimentacao.SAIDA);
        r.setQuantidade(existente.getQuantidade());
        r.setStatus(Status.DELETADO);

        registroService.inserirRegistro(r);

        return true;
    }

    /**
     * Busca um produto pelo seu ID.
     *
     * @param id identificador do produto
     * @return o produto encontrado ou {@code null}
     */
    public Produto buscarPorId(Integer id) {
        return produtoDAO.buscarPorId(id);
    }


    /**
     * Aumenta o valor de todos os produtos ativos com base em um percentual.
     *
     * @param percentual percentual de aumento (ex.: 10.0 = 10%)
     */
     public void aumentarValorProduto(Double percentual) {
        produtoDAO.aumentarValorProduto(percentual);
    }

    /**
     * Reduz o valor de todos os produtos ativos com base em um percentual.
     *
     * @param percentual percentual de redução (ex.: 5.0 = 5%)
     */
    public void diminuirValorProduto(Double percentual) {
        produtoDAO.diminuirValorProduto(percentual);
    }
}
    