package br.backend.dto;

/**
 * DTO responsável por representar uma requisição enviada ao servidor.
 *
 * <p>Este objeto é transmitido em formato JSON via socket e convertido no
 * servidor para determinar qual entidade e qual ação devem ser executadas.
 * O campo {@code dados} contém as informações específicas necessárias
 * para a execução da operação.</p>
 *
 * <p>O formato esperado corresponde a:</p>
 *
 * <pre>
 * {
 *   "acao": "criar",
 *   "entidade": "produto",
 *   "dados": { ... }
 * }
 * </pre>
 *
 * @param <T> tipo do objeto contido no campo {@code dados}
 */
public class Requisicao<T> {

    /** Ação a ser realizada (ex.: criar, atualizar, listar). */
    private String acao;

    /** Entidade alvo da operação (ex.: categoria, produto, registro, relatorio). */
    private String entidade;

    /** Dados específicos da operação, cujo tipo varia conforme a entidade e a ação. */
    private T dados;

    /**
     * Construtor padrão, necessário para serialização/deserialização JSON.
     */
    public Requisicao() {
    }

    /**
     * Construtor completo para criação manual de requisições.
     *
     * @param acao     ação desejada
     * @param entidade entidade alvo
     * @param dados    dados da operação
     */
    public Requisicao(String acao, String entidade, T dados) {
        this.acao = acao;
        this.entidade = entidade;
        this.dados = dados;
    }

    /** @return ação solicitada na requisição */
    public String getAcao() {
        return acao;
    }

    /** @param acao define a ação da requisição */
    public void setAcao(String acao) {
        this.acao = acao;
    }

    /** @return entidade alvo da operação */
    public String getEntidade() {
        return entidade;
    }

    /** @param entidade define qual entidade será manipulada */
    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    /** @return dados específicos que complementam a requisição */
    public T getDados() {
        return dados;
    }

    /** @param dados define os dados da operação */
    public void setDados(T dados) {
        this.dados = dados;
    }
}
