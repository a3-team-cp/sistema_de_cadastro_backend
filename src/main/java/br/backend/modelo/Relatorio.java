package br.backend.modelo;

import java.util.Date;

/**
 * Representa um item consolidado do relatório de movimentações.
 *
 * <p>Esta classe não corresponde diretamente a uma tabela única no banco, mas
 * resulta da junção entre as tabelas de <strong>registro</strong> e
 * <strong>produto</strong>. Ela reúne informações essenciais para exibição
 * em relatórios, como o nome do produto, quantidade movimentada,
 * tipo de movimentação e data.</p>
 *
 * <p>É utilizada exclusivamente para leitura e apresentação de dados,
 * não sendo modificada diretamente pelo sistema.</p>
 */
public class Relatorio {

    /** Identificador do registro de movimentação. */
    private int id;

    /** ID do produto relacionado ao registro. */
    private int produtoId;

    /** Nome do produto no momento da movimentação. */
    private String nomeProduto;

    /** Quantidade movimentada. */
    private int quantidade;

    /** Tipo da movimentação (ex.: ENTRADA, SAÍDA). */
    private String movimentacao;

    /** Status resultante da movimentação (ex.: ADICIONADO, DELETADO). */
    private String status;

    /** Data e hora em que a movimentação ocorreu. */
    private Date data;

    /**
     * Construtor padrão necessário para serialização e frameworks.
     */
    public Relatorio() {
    }

    /**
     * Construtor completo.
     *
     * @param id            identificador da movimentação
     * @param produtoId     identificador do produto
     * @param nomeProduto   nome do produto
     * @param quantidade    quantidade movimentada
     * @param movimentacao  tipo da movimentação
     * @param status        status da operação
     * @param data          data da movimentação
     */
    public Relatorio(int id, int produtoId, String nomeProduto, int quantidade,
                     String movimentacao, String status, Date data) {

        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.movimentacao = movimentacao;
        this.status = status;
        this.data = data;
    }

    /** @return identificador da movimentação */
    public int getId() {
        return id;
    }

    /** @param id define o identificador da movimentação */
    public void setId(int id) {
        this.id = id;
    }

    /** @return ID do produto relacionado */
    public int getProdutoId() {
        return produtoId;
    }

    /** @param produtoId define o produto relacionado ao registro */
    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    /** @return nome do produto no momento da movimentação */
    public String getNomeProduto() {
        return nomeProduto;
    }

    /** @param nomeProduto define o nome do produto */
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    /** @return quantidade movimentada */
    public int getQuantidade() {
        return quantidade;
    }

    /** @param quantidade define a quantidade movimentada */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /** @return tipo da movimentação */
    public String getMovimentacao() {
        return movimentacao;
    }

    /** @param movimentacao define o tipo da movimentação */
    public void setMovimentacao(String movimentacao) {
        this.movimentacao = movimentacao;
    }

    /** @return status resultante da movimentação */
    public String getStatus() {
        return status;
    }

    /** @param status define o status resultante da operação */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return data da movimentação */
    public Date getData() {
        return data;
    }

    /** @param data define a data da movimentação */
    public void setData(Date data) {
        this.data = data;
    }
}
