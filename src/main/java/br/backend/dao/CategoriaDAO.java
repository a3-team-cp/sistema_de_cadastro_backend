package br.backend.dao;

import br.backend.modelo.Categoria;

import java.util.List;

public interface CategoriaDAO {

    void inserirCategoria(Categoria cat);

    void atualizarCategoria(Integer id, Categoria cat);

    void deletarPorId(Integer id);

    Categoria buscarPorId(Integer id);

    List<Categoria> buscarTodasCategorias();
}
