package br.backend.modelo;

import br.backend.modelo.enums.Embalagem;
import br.backend.modelo.enums.Tamanho;

/**
 * Representa uma categoria de produto dentro do sistema.
 *
 * <p>Uma categoria define características comuns entre produtos,
 * como nome, tamanho e tipo de embalagem. Essa entidade é persistida
 * no banco de dados e utilizada para agrupar e classificar itens.</p>
 *
 * <p>Os valores {@link Tamanho} e {@link Embalagem} definem
 * atributos padronizados que ajudam na organização e identificação
 * do tipo de produto.</p>
 */
public class Categoria {

    /** Identificador único da categoria no banco de dados. */
    private Integer id;

    /** Nome descritivo da categoria (ex.: Bebidas, Enlatados, Limpeza). */
    private String nome;

    /** Porte ou tamanho característico da categoria. */
    private Tamanho tamanho;

    /** Tipo de embalagem predominante dos produtos da categoria. */
    private Embalagem embalagem;

    /**
     * Construtor padrão necessário para serialização e frameworks.
     */
    public Categoria() {
    }

    /**
     * Construtor completo.
     *
     * @param id        identificador da categoria
     * @param nome      nome da categoria
     * @param tamanho   tamanho associado
     * @param embalagem tipo de embalagem predominante
     */
    public Categoria(Integer id, String nome, Tamanho tamanho, Embalagem embalagem) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    /** @return identificador da categoria */
    public Integer getId() {
        return id;
    }

    /** @param id define o ID da categoria */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return nome da categoria */
    public String getNome() {
        return nome;
    }

    /** @param nome define o nome da categoria */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return o tamanho associado à categoria */
    public Tamanho getTamanho() {
        return tamanho;
    }

    /** @param tamanho define o porte da categoria */
    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    /** @return tipo de embalagem predominante */
    public Embalagem getEmbalagem() {
        return embalagem;
    }

    /** @param embalagem define o tipo de embalagem característico da categoria */
    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }
}
