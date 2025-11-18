package br.backend.modelo.enums;

/**
 * Enum que representa o estado de um produto ou o resultado de uma
 * movimentação registrada no sistema.
 *
 * <p>Os valores deste enum são utilizados nos registros de movimentação
 * para indicar se um produto foi adicionado, alterado, removido ou se sua
 * quantidade está dentro, acima ou abaixo dos limites estabelecidos.</p>
 *
 * <p>Essas informações são essenciais para auditoria, relatórios e
 * acompanhamento do comportamento do estoque.</p>
 */
public enum Status {

    /**
     * Quantidade atual está acima do limite máximo permitido.
     */
    ACIMA,

    /**
     * Quantidade atual está abaixo do limite mínimo permitido.
     */
    ABAIXO,

    /**
     * Quantidade atual está dentro dos limites mínimo e máximo.
     */
    DENTRO,

    /**
     * Produto recém-adicionado ao estoque (normalmente após inserção).
     */
    ADICIONADO,

    /**
     * Produto teve seus dados atualizados (ex.: nome, embalagem, categoria).
     */
    NOMEALTERADO,

    /**
     * Produto foi removido do estoque (soft delete).
     */
    DELETADO,

    /**
     * Não houve alteração relevante no status.
     *
     * <p>Utilizado em situações onde a movimentação não se enquadra
     * em nenhuma das categorias acima.</p>
     */
    NENHUM
}
