package br.backend.servico;

import br.backend.dao.CategoriaDAO;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.modelo.Categoria;

import java.util.List;


/**
 * Serviço responsável por encapsular as regras de negócio relacionadas
 * à entidade {@link Categoria}.
 *
 * <p>Esta classe atua como intermediária entre os controladores/estratégias
 * e a camada de persistência (DAO), garantindo consistência na manipulação
 * das categorias e centralizando validações simples.</p>
 */
public class CategoriaServico {

    private final CategoriaDAO categoriaDAO;

    /**
     * Construtor padrão.
     *
     * <p>Recebe uma implementação de {@link CategoriaDAO}, permitindo que a camada
     * de serviço seja desacoplada da tecnologia de persistência.</p>
     *
     * @param categoriaDAO implementação de acesso a dados para categorias
     */
    public CategoriaServico(CategoriaDAOImpl categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    /**
     * Insere uma nova categoria no sistema.
     *
     * <p>Após a persistência, a entidade retornada já conterá o ID gerado.</p>
     *
     * @param categoria objeto contendo os dados da nova categoria
     * @return a categoria inserida, incluindo o ID populado pelo banco
     */
    public Categoria inserirCategoria(Categoria categoria) {
        categoriaDAO.inserirCategoria(categoria);
        return categoria; // retornará com ID preenchido após o DAO inserir
    }

    /**
     * Atualiza os dados de uma categoria existente.
     *
     * @param id            ID da categoria a ser atualizada
     * @param novaCategoria objeto contendo os novos dados
     * @return a categoria atualizada, conforme registrada no banco
     */
    public Categoria atualizarCategoria(Integer id, Categoria novaCategoria) {
        categoriaDAO.atualizarCategoria(id, novaCategoria); // atualiza
        return categoriaDAO.buscarPorId(id); // retorna o objeto atualizado
    }

    /**
     * Busca uma categoria pelo seu ID.
     *
     * @param id identificador da categoria
     * @return a categoria encontrada ou {@code null} caso não exista
     */
    public Categoria buscarPorId(Integer id) {
        return categoriaDAO.buscarPorId(id);
    }

    /**
     * Retorna a lista de todas as categorias ativas.
     *
     * @return lista de categorias
     */
    public List<Categoria> listarCategorias() {
        return categoriaDAO.buscarTodasCategorias();
    }

    /**
     * Remove logicamente (soft delete) uma categoria, caso ela exista.
     *
     * <p>Se a categoria não for encontrada, o método retorna {@code false}.
     * Caso contrário, executa o soft delete e retorna {@code true}.</p>
     *
     * @param id identificador da categoria a ser deletada
     * @return {@code true} se a categoria foi deletada; {@code false} se não existir
     */
    public boolean deletarCategoria(Integer id) {
        Categoria existente = categoriaDAO.buscarPorId(id);
        if (existente == null) {
            return false;
        }
        categoriaDAO.deletarPorId(id);
        return true;
    }
}
