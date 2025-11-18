package br.backend.modelo;

/**
 * Representa um produto armazenado no sistema de estoque.
 *
 * <p>Esta entidade contém informações essenciais para o controle de inventário,
 * incluindo preço, quantidade disponível, unidade de medida e limites mínimo
 * e máximo para monitoramento. Também referencia a categoria à qual o produto pertence.</p>
 *
 * <p>Os valores desta classe são persistidos no banco de dados e utilizados
 * em operações de cadastro, atualização, movimentações e relatórios.</p>
 */
public class Produto {

    /** Identificador único do produto no banco de dados. */
    private Integer id;

    /** Nome descritivo do produto (ex.: Arroz, Feijão, Café). */
    private String nome;

    /** Preço unitário do produto. */
    private Double preco;

    /** Unidade de medida (ex.: "kg", "L", "un"). */
    private String unidade;

    /** ID da categoria à qual este produto pertence. */
    private Integer categoriaId;

    /** Quantidade atual disponível no estoque. */
    private Integer quantidade;

    /** Quantidade mínima recomendada para controle de estoque. */
    private Integer quantidadeMinima;

    /** Quantidade máxima permitida ou ideal para o estoque. */
    private Integer quantidadeMaxima;

    /**
     * Construtor padrão necessário para frameworks e serialização JSON.
     */
    public Produto() {
    }

    /**
     * Construtor completo.
     *
     * @param id               identificador único
     * @param nome             nome do produto
     * @param preco            preço unitário
     * @param unidade          unidade de medida
     * @param categoriaId      vínculo com categoria
     * @param quantidade       quantidade atual em estoque
     * @param quantidadeMinima quantidade mínima permitida
     * @param quantidadeMaxima quantidade máxima permitida
     */
    public Produto(Integer id, String nome, Double preco, String unidade, Integer categoriaId,
                   Integer quantidade, Integer quantidadeMinima, Integer quantidadeMaxima) {

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.unidade = unidade;
        this.categoriaId = categoriaId;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
    }

    /** @return identificador do produto */
    public Integer getId() {
        return id;
    }

    /** @param id define o identificador do produto */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return nome do produto */
    public String getNome() {
        return nome;
    }

    /** @param nome define o nome do produto */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return preço unitário */
    public Double getPreco() {
        return preco;
    }

    /** @param preco define o preço unitário */
    public void setPreco(Double preco) {
        this.preco = preco;
    }

    /** @return unidade de medida */
    public String getUnidade() {
        return unidade;
    }

    /** @param unidade define a unidade de medida */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /** @return ID da categoria do produto */
    public Integer getCategoriaId() {
        return categoriaId;
    }

    /** @param categoriaId define a categoria do produto */
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    /** @return quantidade atual do produto */
    public Integer getQuantidade() {
        return quantidade;
    }

    /** @param quantidade define a quantidade do produto */
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /** @return quantidade mínima recomendada */
    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    /** @param quantidadeMinima define a quantidade mínima recomendada */
    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    /** @return quantidade máxima permitida no estoque */
    public Integer getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    /** @param quantidadeMaxima define a quantidade máxima permitida */
    public void setQuantidadeMaxima(Integer quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }
}
