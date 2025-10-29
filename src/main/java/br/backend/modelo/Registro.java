package br.backend.modelo;
import br.backend.modelo.enums.Movimentacao;
import br.backend.modelo.enums.Status;

import java.util.Date;

public class Registro {

    private Integer id;
    private Date data;
    private Integer produtoId;
    private Integer quantidade;
    private Movimentacao movimentacao;
    private Status status;

    public Registro() {
    }

    public Registro(Integer id, Date data, Integer produtoId, Integer quantidade, Movimentacao movimentacao, Status status) {
        this.id = id;
        this.data = data;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.movimentacao = movimentacao;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}