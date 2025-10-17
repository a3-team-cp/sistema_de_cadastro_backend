package br.backend.servico;

import br.backend.dao.CategoriaDAO;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.modelo.Categoria;
import br.backend.modelo.enums.Embalagem;
import br.backend.modelo.enums.Tamanho;

import java.util.List;

public class CategoriaServico {

    private final CategoriaDAO categoriaDAO;

    public CategoriaServico(CategoriaDAOImpl categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    // Criar categoria
    public Categoria inserirCategoria(String nome, Tamanho tamanho, Embalagem embalagem) {
        Categoria cat = new Categoria(null, nome, tamanho, embalagem);
        categoriaDAO.inserirCategoria(cat);
        return cat; // retorna com o ID preenchido
    }

    public Categoria atualizarCategoria(Integer id, Categoria novaCategoria) {
        categoriaDAO.atualizarCategoria(id, novaCategoria);
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
