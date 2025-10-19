package br.backend.dao.impl;

import br.backend.dao.ProdutoDAO;
import br.backend.database.Database;
import br.backend.modelo.Categoria;
import br.backend.modelo.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private final Database database;

    public ProdutoDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void inserirProduto(Produto obj) {
        String sql = "INSERT INTO produto (nome, preco_unitario, unidade, quantidade, quantidade_minima, quantidade_maxima, categoria_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setDouble(2, obj.getPreco());
            st.setString(3, obj.getUnidade());
            st.setInt(4, obj.getQuantidade());
            st.setInt(5, obj.getQuantidadeMinima());
            st.setInt(6, obj.getQuantidadeMaxima());
            st.setInt(7, obj.getCategoria().getId());

            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizarProduto(Integer id, Produto novoProduto) {
        Produto produtoExistente = buscarPorId(id);
        if (produtoExistente == null) {
            throw new RuntimeException("Produto com ID " + id + " não encontrado.");
        }

        produtoExistente.setNome(novoProduto.getNome());
        produtoExistente.setPreco(novoProduto.getPreco());
        produtoExistente.setUnidade(novoProduto.getUnidade());
        produtoExistente.setQuantidade(novoProduto.getQuantidade());
        produtoExistente.setQuantidadeMinima(novoProduto.getQuantidadeMinima());
        produtoExistente.setQuantidadeMaxima(novoProduto.getQuantidadeMaxima());
        produtoExistente.setCategoria(novoProduto.getCategoria());

        String sql = "UPDATE produto SET "
                   + "nome = ?, "
                   + "preco_unitario = ?, "
                   + "unidade = ?, "
                   + "quantidade = ?, "
                   + "quantidade_minima = ?, "
                   + "quantidade_maxima = ?, "
                   + "categoria_id = ? "
                   + "WHERE id = ?";

        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setString(1, produtoExistente.getNome());
            st.setDouble(2, produtoExistente.getPreco());
            st.setString(3, produtoExistente.getUnidade());
            st.setInt(4, produtoExistente.getQuantidade());
            st.setInt(5, produtoExistente.getQuantidadeMinima());
            st.setInt(6, produtoExistente.getQuantidadeMaxima());
            st.setInt(7, produtoExistente.getCategoria().getId());
            st.setInt(8, produtoExistente.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto buscarPorId(Integer id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapProduto(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> resgatarProdutos() {
        throw new UnsupportedOperationException("Não implementado ainda.");
    }

    private Produto mapProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setPreco(rs.getDouble("preco_unitario"));
        p.setUnidade(rs.getString("unidade"));
        p.setQuantidade(rs.getInt("quantidade"));
        p.setQuantidadeMinima(rs.getInt("quantidade_minima"));
        p.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));

        Categoria cat = new Categoria();
        cat.setId(rs.getInt("categoria_id"));
        p.setCategoria(cat);

        return p;
    }
}
