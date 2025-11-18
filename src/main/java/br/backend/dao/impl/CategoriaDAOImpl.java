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

/**
 * Implementação de {@link CategoriaDAO} utilizando JDBC puro.
 *
 * <p>Responsável por realizar operações CRUD no banco de dados para a entidade
 * {@link Categoria}. Todas as operações consideram o campo <code>ativo</code>
 * para aplicar a regra de <b>soft delete</b>, garantindo que categorias excluídas
 * não sejam retornadas nas consultas.</p>
 *
 * <p>Esta classe utiliza uma instância de {@link Database} para obter conexões
 * e executar comandos SQL diretamente.</p>
 */
public class CategoriaDAOImpl implements CategoriaDAO {

    Database database;

    /**
     * Construtor padrão.
     *
     * @param database objeto de acesso à conexão com o banco de dados
     */
    public CategoriaDAOImpl(Database database) {
        this.database = database;
    }

    /**
     * Insere uma nova categoria no banco de dados.
     *
     * <p>Após a inserção, o ID gerado automaticamente é atribuído ao objeto
     * {@link Categoria} recebido como parâmetro.</p>
     *
     * @param cat categoria a ser inserida
     * @throws RuntimeException em caso de erro SQL
     */
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

    /**
     * Atualiza os dados de uma categoria existente.
     *
     * <p>O método garante que a categoria existe antes de atualizar. Caso não seja
     * encontrada, lança uma exceção informativa.</p>
     *
     * @param id            identificador da categoria a ser atualizada
     * @param novaCategoria dados atualizados da categoria
     * @throws RuntimeException se a categoria não existir ou ocorrer erro SQL
     */
    @Override
    public void atualizarCategoria(Integer id, Categoria novaCategoria) {

        Categoria categoriaExistente = buscarPorId(id);
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria com ID " + id + " não encontrada.");
        }

        categoriaExistente.setNome(novaCategoria.getNome());
        categoriaExistente.setTamanho(novaCategoria.getTamanho());
        categoriaExistente.setEmbalagem(novaCategoria.getEmbalagem());

        String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ? AND ativo = true";

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

    /**
     * Realiza o soft delete da categoria e dos produtos associados.
     *
     * <p>Em vez de remover fisicamente os registros, o método define o campo
     * <code>ativo = false</code> tanto na categoria quanto nos produtos que
     * pertencem a ela.</p>
     *
     * @param id ID da categoria a ser desativada
     * @throws RuntimeException em caso de erro SQL
     */
    @Override
    public void deletarPorId(Integer id) {
       String sql = "UPDATE categoria c " +
                 "LEFT JOIN produto p ON p.categoria_id = c.id " +
                 "SET c.ativo = false, p.ativo = false " +
                 "WHERE c.id = ? AND c.ativo = true";

        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            int linhasAfetadas = st.executeUpdate();
            System.out.println("Linhas afetadas: " + linhasAfetadas);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar categoria e produtos: " + e.getMessage(), e);
        }
    }

    /**
     * Busca uma categoria pelo seu ID.
     *
     * <p>Retorna apenas categorias ativas (soft delete ativo).</p>
     *
     * @param id identificador da categoria a ser buscada
     * @return a categoria encontrada ou {@code null} caso não exista
     * @throws RuntimeException em caso de erro SQL
     */
    @Override
    public Categoria buscarPorId(Integer id) {
        String sql = "SELECT * FROM categoria WHERE id = ? AND ativo = true";
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

    /**
     * Busca todas as categorias ativas no sistema.
     *
     * @return lista de categorias
     * @throws RuntimeException em caso de erro SQL
     */
    @Override
    public List<Categoria> buscarTodasCategorias() {
        String sql = "SELECT * FROM categoria WHERE ativo = true";
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

    /**
     * Converte um {@link ResultSet} em um objeto {@link Categoria}.
     *
     * <p>Mapeia todos os campos relevantes, incluindo os enums
     * {@link Tamanho} e {@link Embalagem}.</p>
     *
     * @param rs resultado da consulta SQL
     * @return objeto {@link Categoria} preenchido com os dados do banco
     * @throws SQLException caso ocorra erro ao ler o ResultSet
     */
    private Categoria mapCategoria(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria();
        cat.setId(rs.getInt("id"));
        cat.setNome(rs.getString("nome"));
        cat.setTamanho(Tamanho.valueOf(rs.getString("tamanho")));
        cat.setEmbalagem(Embalagem.valueOf(rs.getString("embalagem")));
        return cat;
    }
}
