package br.backend.modelo.enums;

/**
 * Enum que representa os tipos de movimentação possíveis no estoque.
 *
 * <p>É utilizado nos registros de movimentação para indicar se um produto
 * foi adicionado, removido ou apenas atualizado sem impacto na quantidade.</p>
 *
 * <p>Esses valores são fundamentais para o controle de histórico e geração
 * de relatórios no sistema.</p>
 */
public enum Movimentacao {

    /**
     * Nenhuma movimentação física ocorreu.
     *
     * <p>Utilizado, por exemplo, quando um produto é apenas atualizado
     * (nome, embalagem, categoria, etc.) sem alteração de quantidade.</p>
     */
    NENHUM,

    /**
     * Representa a entrada de itens no estoque.
     *
     * <p>Usado quando um novo produto é inserido ou quando há aumento na quantidade.</p>
     */
    ENTRADA,

    /**
     * Representa a saída de itens do estoque.
     *
     * <p>Usado quando um produto é removido (soft delete) ou há redução na quantidade.</p>
     */
    SAIDA
}
