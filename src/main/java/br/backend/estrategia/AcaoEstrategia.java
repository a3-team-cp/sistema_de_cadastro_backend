package br.backend.estrategia;

import br.backend.dto.Requisicao;


/**
 * Interface que representa uma estratégia de execução para uma ação específica.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b>, permitindo que cada
 * ação (criar, atualizar, listar, etc.) seja encapsulada em uma classe distinta,
 * promovendo baixo acoplamento e alta extensibilidade.</p>
 *
 * <p>As estratégias recebem uma {@link Requisicao} contendo os dados necessários
 * e devem retornar uma resposta em formato JSON.</p>
 */
public interface AcaoEstrategia {

    /**
     * Executa a ação definida pela estratégia.
     *
     * @param requisicao objeto contendo a ação e os dados enviados pelo cliente
     * @return uma string JSON representando o resultado da operação
     */
    String executar(Requisicao<?> requisicao);
}
