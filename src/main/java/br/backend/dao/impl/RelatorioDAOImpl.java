package br.backend.dao.impl;

import br.backend.dao.RelatorioDAO;
import br.backend.database.Database;
import br.backend.modelo.Relatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAOImpl implements RelatorioDAO {

    private final Database database;

    public RelatorioDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Relatorio> listarRelatorio() {
        String sql = """
        SELECT r.id,
               r.data,
               p.id AS produtoId,
               p.nome AS nomeProduto,
               r.quantidade,
               r.movimentacao,
               r.status
        FROM registro r
        INNER JOIN produto p ON r.produto_id = p.id
        ORDER BY r.data DESC
    """;

        List<Relatorio> relatorios = new ArrayList<>();

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                relatorios.add(mapRelatorio(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registros para o relatório: " + e.getMessage(), e);
        }

        return relatorios;
    }

    private Relatorio mapRelatorio(ResultSet rs) throws SQLException {
        Relatorio relatorio = new Relatorio();

        relatorio.setId(rs.getInt("id"));
        relatorio.setData(new java.util.Date(rs.getTimestamp("data").getTime()));
        relatorio.setProdutoId(rs.getInt("produtoId"));
        relatorio.setNomeProduto(rs.getString("nomeProduto"));
        relatorio.setQuantidade(rs.getInt("quantidade"));
        relatorio.setMovimentacao(rs.getString("movimentacao"));
        relatorio.setStatus(rs.getString("status"));

        return relatorio;
    }

}
