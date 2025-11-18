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


/**
 * Implementação de {@link ProdutoDAO} utilizando JDBC puro.
 *
 * <p>Responsável por executar operações de persistência relacionadas à entidade
 * {@link Produto}, incluindo inserção, atualização, busca, exclusão lógica
 * (soft delete) e ajustes de preço.</p>
 *
 * <p>Esta classe acessa o banco de dados utilizando a conexão fornecida por
 * {@link Database}. Todas as consultas tratam o campo <code>ativo</code>, evitando
 * o retorno de produtos marcados como inativos.</p>
 */
public class ProdutoDAOImpl implements ProdutoDAO {

    private final Database database;

    /**
     * Construtor padrão.
     *
     * @param database fonte de conexões JDBC
     */
    public ProdutoDAOImpl(Database database) {
        this.database = database;
    }


    /**
     * Insere um novo produto no banco de dados.
     *
     * <p>Após a execução do comando SQL, o ID gerado automaticamente é atribuído
     * ao objeto fornecido.</p>
     *
     * @param obj produto a ser inserido
     * @throws RuntimeException caso ocorra erro SQL
     */
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

    /**
     * Atualiza os dados de um produto existente, mantendo seu status como ativo.
     *
     * <p>Se nenhum produto ativo for encontrado com o ID fornecido, uma exceção
     * é lançada.</p>
     *
     * @param id          ID do produto a ser atualizado
     * @param novoProduto objeto contendo os novos dados
     * @return o produto atualizado, já recuperado do banco
     * @throws RuntimeException caso o produto não exista ou ocorra erro SQL
     */
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

    /**
     * Realiza o soft delete de um produto, marcando-o como inativo.
     *
     * @param id ID do produto a ser desativado
     * @throws RuntimeException caso ocorra erro SQL
     */
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


    /**
     * Busca um produto pelo seu ID, incluindo informações da categoria associada.
     *
     * <p>Retorna apenas produtos marcados como ativos.</p>
     *
     * @param id ID do produto buscado
     * @return o produto encontrado ou {@code null} caso não exista
     * @throws RuntimeException caso ocorra erro SQL
     */
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

    /**
     * Recupera todos os produtos ativos cadastrados.
     *
     * @return lista contendo todos os produtos ativos
     * @throws RuntimeException caso ocorra erro SQL
     */
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

    /**
     * Aumenta o valor unitário de todos os produtos ativos.
     *
     * @param percentual percentual de aumento (ex.: 10.0 = 10%)
     * @throws RuntimeException caso ocorra erro SQL
     */
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

    /**
     * Reduz o valor unitário de todos os produtos ativos.
     *
     * @param percentual percentual de redução (ex.: 5.0 = 5%)
     * @throws RuntimeException caso ocorra erro SQL
     */
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

    /**
     * Converte uma linha do {@link ResultSet} em um objeto {@link Produto}.
     *
     * <p>Mapeia todos os campos básicos da entidade.</p>
     *
     * @param rs resultado da consulta SQL
     * @return objeto {@link Produto} preenchido com os dados do banco
     * @throws SQLException caso ocorra erro ao ler o ResultSet
     */
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
