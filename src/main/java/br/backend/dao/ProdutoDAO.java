package br.backend.dao;

import br.backend.modelo.Produto;
import java.util.List;

/**
 * Interface responsável por definir as operações de acesso a dados
 * relacionadas à entidade {@link Produto}.
 *
 * <p>Inclui métodos de criação, atualização, busca, exclusão lógica (soft delete)
 * e manipulação de preços — funcionalidades essenciais para o controle de estoque.</p>
 *
 * <p>As implementações devem garantir o correto tratamento das operações
 * utilizando o mecanismo de persistência adotado (ex.: JDBC, JPA, etc.).</p>
 */
public interface ProdutoDAO {

    /**
     * Insere um novo produto no banco de dados.
     *
     * @param obj objeto contendo os dados do produto a ser persistido
     */
    void inserirProduto(Produto obj);

    /**
     * Atualiza os dados de um produto existente.
     *
     * <p>Retorna o produto atualizado após a operação.</p>
     *
     * @param id          identificador do produto a ser atualizado
     * @param novoProduto objeto contendo os novos valores
     * @return o produto atualizado
     */
    Produto atualizarProduto(Integer id, Produto novoProduto);

    /**
     * Busca um produto pelo seu ID.
     *
     * <p>A busca deve retornar apenas produtos ativos (não removidos via soft delete).</p>
     *
     * @param id identificador do produto
     * @return o produto encontrado ou {@code null} caso não exista
     */
    Produto buscarPorId(Integer id);

    /**
     * Realiza o soft delete de um produto, marcando-o como inativo.
     *
     * @param id identificador do produto a ser removido
     */
    void deletarPorId(Integer id);

    /**
     * Retorna todos os produtos ativos no sistema.
     *
     * @return lista de produtos
     */
    List<Produto> resgatarTodosProdutos();

    /**
     * Aumenta o valor unitário de todos os produtos ativos com base em um percentual.
     *
     * @param percentual percentual de aumento (ex.: 10.0 = 10%)
     */
    void aumentarValorProduto(Double percentual);

    /**
     * Reduz o valor unitário de todos os produtos ativos com base em um percentual.
     *
     * @param percentual percentual de redução (ex.: 5.0 = 5%)
     */
    void diminuirValorProduto(Double percentual);
}
