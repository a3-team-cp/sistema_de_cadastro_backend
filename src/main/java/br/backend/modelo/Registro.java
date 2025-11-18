package br.backend.modelo;

import br.backend.modelo.enums.Movimentacao;
import br.backend.modelo.enums.Status;

import java.util.Date;

/**
 * Representa uma movimentação registrada no estoque.
 *
 * <p>Um registro é criado sempre que um produto é inserido, atualizado
 * ou removido. Cada entrada contém informações sobre a data da operação,
 * a quantidade envolvida, o tipo de movimentação e o status resultante.</p>
 *
 * <p>Essa entidade é fundamental para auditoria, relatórios e rastreabilidade,
 * permitindo acompanhar todo o histórico de alterações do estoque.</p>
 */
public class Registro {

    /** Identificador único do registro no banco de dados. */
    private Integer id;

    /** Data e hora da movimentação realizada. */
    private Date data;

    /** Identificador do produto relacionado a esta movimentação. */
    private Integer produtoId;

    /** Quantidade envolvida na movimentação. */
    private Integer quantidade;

    /** Tipo da movimentação (entrada, saída ou nenhuma). */
    private Movimentacao movimentacao;

    /** Status resultante da operação (adicionado, deletado, etc.). */
    private Status status;

    /**
     * Construtor padrão necessário para serialização e frameworks.
     */
    public Registro() {
    }

    /**
     * Construtor completo.
     *
     * @param id           identificador do registro
     * @param data         data da movimentação
     * @param produtoId    ID do produto relacionado
     * @param quantidade   quantidade movimentada
     * @param movimentacao tipo da movimentação
     * @param status       status resultante
     */
    public Registro(Integer id, Date data, Integer produtoId, Integer quantidade,
                    Movimentacao movimentacao, Status status) {

        this.id = id;
        this.data = data;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.movimentacao = movimentacao;
        this.status = status;
    }

    /** @return identificador do registro */
    public Integer getId() {
        return id;
    }

    /** @param id define o identificador do registro */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return data da movimentação */
    public Date getData() {
        return data;
    }

    /** @param data define a data da movimentação */
    public void setData(Date data) {
        this.data = data;
    }

    /** @return ID do produto relacionado */
    public Integer getProdutoId() {
        return produtoId;
    }

    /** @param produtoId define qual produto foi movimentado */
    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    /** @return quantidade movimentada na operação */
    public Integer getQuantidade() {
        return quantidade;
    }

    /** @param quantidade define a quantidade movimentada */
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /** @return tipo da movimentação */
    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    /** @param movimentacao define o tipo da movimentação */
    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    /** @return status da operação */
    public Status getStatus() {
        return status;
    }

    /** @param status define o status da movimentação */
    public void setStatus(Status status) {
        this.status = status;
    }
}
