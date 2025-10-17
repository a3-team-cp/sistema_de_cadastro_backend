package br.backend.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private Connection conn = null;

    private Properties getProperties() {
        InputStream inputStream = null;
        Properties props = new Properties();
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            if (inputStream == null) {
                throw new RuntimeException("application.properties não encontrado no classpath!");
            }
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo: " + e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection getConnection() {
        if(conn == null){
            try{
                Properties props = getProperties();
                String url = props.getProperty("db.datasource.url");
                String user = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
}
