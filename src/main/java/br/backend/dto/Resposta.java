package br.backend.dto;

/**
 * DTO responsável por representar a resposta enviada pelo servidor
 * ao cliente após o processamento de uma requisição.
 *
 * <p>Este objeto é serializado em JSON e enviado de volta ao cliente via socket.
 * Ele contém três informações principais:</p>
 *
 * <ul>
 *     <li><b>status</b> – indica o resultado da operação (ex.: "sucesso", "erro")</li>
 *     <li><b>mensagem</b> – texto explicativo sobre o resultado</li>
 *     <li><b>dados</b> – payload retornado (pode ser um objeto, lista ou null)</li>
 * </ul>
 *
 * <p>Formato esperado pelo cliente:</p>
 *
 * <pre>
 * {
 *   "status": "sucesso",
 *   "mensagem": "Produto criado",
 *   "dados": { ... }
 * }
 * </pre>
 *
 * @param <T> tipo do dado retornado pelo servidor
 */
public class Resposta<T> {

    /** Status da operação ("sucesso", "erro", etc.). */
    private String status;

    /** Mensagem explicativa sobre o resultado da operação. */
    private String mensagem;

    /** Dados retornados pela operação, podendo ser objeto, lista ou null. */
    private T dados;

    /**
     * Construtor principal da resposta.
     *
     * @param status   estado da operação
     * @param mensagem mensagem explicativa
     * @param dados    dados retornados (podem ser nulos)
     */
    public Resposta(String status, String mensagem, T dados) {
        this.status = status;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    /** @return status da operação */
    public String getStatus() { return status; }

    /** @return mensagem informativa sobre o resultado */
    public String getMensagem() { return mensagem; }

    /** @return dados retornados pela requisição */
    public T getDados() { return dados; }
}
