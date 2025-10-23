package br.backend.dao.impl;

import br.backend.dao.RegistroDAO;
import br.backend.database.Database;
import br.backend.modelo.Registro;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RegistroDAOImpl implements RegistroDAO {

    Database database;

    public RegistroDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void inserirRegistro(Registro r) {
        String sql = "INSERT INTO registro (data, produto_id, quantidade, movimentacao, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = database.getConnection().prepareStatement(sql)) {
            st.setTimestamp(1, new Timestamp(r.getData().getTime()));
            st.setInt(2, r.getTipoDoProduto().getId());
            st.setInt(3, r.getQuantidade());
            st.setString(4, r.getMovimentacao().name());
            st.setString(5, r.getStatus().name());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir registro: " + e.getMessage(), e);
        }
    }
}
