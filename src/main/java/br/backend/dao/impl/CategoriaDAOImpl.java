package br.backend.dao.impl;

import br.backend.dao.CategoriaDAO;
import br.backend.database.Database;
import br.backend.modelo.Categoria;
import br.backend.modelo.enums.Embalagem;
import br.backend.modelo.enums.Tamanho;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    Database database;

    public CategoriaDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void inserirCategoria(Categoria cat) {
        String sql = "INSERT INTO categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, cat.getNome());
            st.setString(2, cat.getTamanho().name());
            st.setString(3, cat.getEmbalagem().name());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    cat.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir categoria: " + e.getMessage());
        }
    }

    @Override
    public void atualizarCategoria(Integer id, Categoria novaCategoria) {

        Categoria categoriaExistente = buscarPorId(id);
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria com ID " + id + " não encontrada.");
        }

        categoriaExistente.setNome(novaCategoria.getNome());
        categoriaExistente.setTamanho(novaCategoria.getTamanho());
        categoriaExistente.setEmbalagem(novaCategoria.getEmbalagem());

        String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ?";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setString(1, categoriaExistente.getNome());
            st.setString(2, categoriaExistente.getTamanho().name());
            st.setString(3, categoriaExistente.getEmbalagem().name());
            st.setInt(4, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    @Override
    public void deletarPorId(Integer id) {
        String sql = "UPDATE categoria SET deleted = TRUE WHERE id = ?";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar categoria como deletada: " + e.getMessage(), e);
        }
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        String sql = "SELECT * FROM categoria WHERE id = ? AND deleted = FALSE";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapCategoria(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Categoria> buscarTodasCategorias() {
        String sql = "SELECT * FROM categoria WHERE deleted = FALSE";
        List<Categoria> lista = new ArrayList<>();
        try (PreparedStatement st = database.getConnection().prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(mapCategoria(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas categorias: " + e.getMessage(), e);
        }
        return lista;
    }

    private Categoria mapCategoria(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria();
        cat.setId(rs.getInt("id"));
        cat.setNome(rs.getString("nome"));
        cat.setTamanho(Tamanho.valueOf(rs.getString("tamanho")));
        cat.setEmbalagem(Embalagem.valueOf(rs.getString("embalagem")));
        return cat;
    }
}
