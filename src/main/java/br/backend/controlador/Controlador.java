package br.backend.controlador;

import br.backend.dto.Requisicao;


/**
 * Interface base para todos os controladores da aplicação.
 *
 * <p>Define o contrato para o processamento de uma {@link Requisicao}, retornando
 * sempre um JSON que representa o resultado da operação executada pelo controlador
 * concreto.</p>
 *
 * <p>Implementações dessa interface utilizam o padrão Strategy para despachar
 * dinamicamente a ação desejada com base nas informações contidas na requisição.</p>
 */
public interface Controlador {

    /**
     * Processa uma requisição genérica e retorna o resultado em formato JSON.
     *
     * @param requisicao objeto contendo a ação a ser executada e os dados enviados pelo cliente
     * @return uma string JSON representando a resposta (sucesso ou erro)
     */
    public String processarRequisicao(Requisicao<?> requisicao);
}
