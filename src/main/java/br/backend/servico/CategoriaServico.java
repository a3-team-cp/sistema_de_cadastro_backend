package br.backend.servico;

import br.backend.dao.CategoriaDAO;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.modelo.Categoria;

import java.util.List;

public class CategoriaServico {

    private final CategoriaDAO categoriaDAO;

    public CategoriaServico(CategoriaDAOImpl categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    // Criar categoria
    public Categoria inserirCategoria(Categoria categoria) {
        categoriaDAO.inserirCategoria(categoria);
        return categoria; // retornará com ID preenchido após o DAO inserir
    }

    public Categoria atualizarCategoria(Integer id, Categoria novaCategoria) {
        categoriaDAO.atualizarCategoria(id, novaCategoria); // atualiza
        return categoriaDAO.buscarPorId(id); // retorna o objeto atualizado
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaDAO.buscarPorId(id);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.buscarTodasCategorias();
    }

    public boolean deletarCategoria(Integer id) {
        Categoria existente = categoriaDAO.buscarPorId(id);
        if (existente == null) return false;
        categoriaDAO.deletarPorId(id);
        return true;
    }




}
