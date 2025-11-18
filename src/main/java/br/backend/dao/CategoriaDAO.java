package br.backend.dao;

import br.backend.modelo.Categoria;

import java.util.List;


/**
 * Interface responsável por definir as operações de acesso a dados
 * relacionadas à entidade {@link Categoria}.
 *
 * <p>Fornece métodos para inserção, atualização, exclusão lógica (soft delete),
 * busca por ID e listagem de todas as categorias ativas.</p>
 *
 * <p>As implementações desta interface devem garantir o correto tratamento das
 * operações CRUD e seguir as regras de domínio associadas à entidade.</p>
 */
public interface CategoriaDAO {

    /**
     * Insere uma nova categoria no banco de dados.
     *
     * @param cat categoria a ser inserida
     */
    void inserirCategoria(Categoria cat);

    /**
     * Atualiza uma categoria existente com base no seu ID.
     *
     * @param id  identificador da categoria a ser atualizada
     * @param cat objeto contendo os novos dados da categoria
     */
    void atualizarCategoria(Integer id, Categoria cat);

    /**
     * Realiza a exclusão lógica (soft delete) de uma categoria.
     *
     * <p>A categoria não é removida fisicamente, mas marcada como inativa.</p>
     *
     * @param id identificador da categoria a ser desativada
     */
    void deletarPorId(Integer id);


    /**
     * Busca uma categoria pelo seu ID, retornando apenas categorias ativas.
     *
     * @param id identificador da categoria
     * @return a categoria correspondente ou {@code null} caso não exista
     */
    Categoria buscarPorId(Integer id);

    /**
     * Retorna a lista de todas as categorias ativas no sistema.
     *
     * @return lista de categorias
     */
    List<Categoria> buscarTodasCategorias();
}
