package br.backend.dao.impl;

import br.backend.dao.RegistroDAO;
import br.backend.database.Database;
import br.backend.modelo.Produto;
import br.backend.modelo.Registro;
import br.backend.modelo.enums.Movimentacao;
import br.backend.modelo.enums.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAOImpl implements RegistroDAO {

    private final Database database;

    public RegistroDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void inserirRegistro(Registro registro) {
        String sql = "INSERT INTO registro (data, produto_id, quantidade, movimentacao, status) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setTimestamp(1, new Timestamp(registro.getData().getTime()));
            statement.setInt(2, registro.getProdutoId());
            statement.setInt(3, registro.getQuantidade());
            statement.setString(4, registro.getMovimentacao().name());
            statement.setString(5, registro.getStatus().name());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir registro, nenhuma linha afetada.");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir registro: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Registro> listarRegistros() {
        String sql = "SELECT * FROM registro ORDER BY data DESC";
        List<Registro> registros = new ArrayList<>();

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                registros.add(mapRegistro(resultSet));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar registros: " + e.getMessage(), e);
        }
        
        return registros;
    }

    private Registro mapRegistro(ResultSet rs) throws SQLException {
        Registro registro = new Registro();
        
        registro.setId(rs.getInt("id"));
        registro.setData(new java.util.Date(rs.getTimestamp("data").getTime()));
        registro.setProdutoId(rs.getInt("produto_id"));
        registro.setQuantidade(rs.getInt("quantidade"));
        registro.setMovimentacao(Movimentacao.valueOf(rs.getString("movimentacao")));
        registro.setStatus(Status.valueOf(rs.getString("status")));
        
        return registro;
    }
}