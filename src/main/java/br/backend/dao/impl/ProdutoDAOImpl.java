package br.backend.dao.impl;

import br.backend.dao.ProdutoDAO;
import br.backend.database.Database;
import br.backend.modelo.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            st.setInt(7, obj.getCategoriaId());

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
    public Produto atualizarProduto(Integer id, Produto novoProduto) {
        String sql = "UPDATE produto SET "
                + "nome = ?, preco_unitario = ?, unidade = ?, quantidade = ?, quantidade_minima = ?, quantidade_maxima = ?, categoria_id = ?, ativo = true "
                + "WHERE id = ? AND ativo = true";

        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setString(1, novoProduto.getNome());
            st.setDouble(2, novoProduto.getPreco());
            st.setString(3, novoProduto.getUnidade());
            st.setInt(4, novoProduto.getQuantidade());
            st.setInt(5, novoProduto.getQuantidadeMinima());
            st.setInt(6, novoProduto.getQuantidadeMaxima());
            st.setInt(7, novoProduto.getCategoriaId());
            st.setInt(8, id);

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Produto com ID " + id + " não encontrado.");
            }

            return buscarPorId(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletarPorId(Integer id) {
        String sql = "UPDATE produto SET ativo = false WHERE id = ?";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto buscarPorId(Integer id) {
        String sql = "SELECT p.*, c.nome AS c_nome, c.tamanho AS c_tamanho, c.embalagem AS c_embalagem "
                + "FROM produto p "
                + "JOIN categoria c ON p.categoria_id = c.id "
                + "WHERE p.id = ? AND p.ativo = true";
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
    public List<Produto> resgatarTodosProdutos() {
        String sql = "SELECT * FROM produto WHERE ativo = true";
        List<Produto> lista = new ArrayList<>();
        try (PreparedStatement st = database.getConnection().prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(mapProduto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os Produtos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void aumentarValorProduto(Double percentual) {
        String sql = "UPDATE produto SET preco_unitario = preco_unitario * (1 + ?/100) WHERE ativo = true";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setDouble(1, percentual);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao aumentar valor dos produtos: " + e.getMessage(), e);
        }
    }

    @Override
    public void diminuirValorProduto(Double percentual) {
        String sql = "UPDATE produto SET preco_unitario = preco_unitario * (1 - ?/100) WHERE ativo = true";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setDouble(1, percentual);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao diminuir valor dos produtos: " + e.getMessage(), e);
        }
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
        p.setCategoriaId(rs.getInt("categoria_id"));

        return p;
    }
}
