package br.backend.modelo;

public class Resposta<T> {

    private String status;
    private String mensagem;
    private T dados;

    public Resposta(String status, String mensagem, T dados) {
        this.status = status;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public String getStatus() { return status; }
    public String getMensagem() { return mensagem; }
    public T getDados() { return dados; }
}
