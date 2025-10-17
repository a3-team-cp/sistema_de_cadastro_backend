package br.backend.modelo;

public class Resposta {

    private String status;
    private String mensagem;
    private Object dados;

    public Resposta(String status, String mensagem, Object dados) {
        this.status = status;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public String getStatus() { return status; }
    public String getMensagem() { return mensagem; }
    public Object getDados() { return dados; }
}
