package br.backend.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 *
 * <p>A configuração é carregada a partir do arquivo <b>application.properties</b>
 * presente no classpath, contendo as propriedades:</p>
 *
 * <ul>
 *     <li><code>db.datasource.url</code></li>
 *     <li><code>db.username</code></li>
 *     <li><code>db.password</code></li>
 * </ul>
 *
 * <p>A conexão é criada de forma <b>lazy</b>, ou seja, apenas quando
 * {@link #getConnection()} é chamado pela primeira vez.</p>
 *
 * <p>Esta classe mantém uma única instância de {@link Connection} durante toda a
 * execução da aplicação.</p>
 */
public class Database {

    private Connection conn = null;

    /**
     * Carrega e retorna as propriedades de configuração do banco de dados.
     *
     * <p>O arquivo <code>application.properties</code> deve estar localizado no classpath.
     * Caso não seja encontrado, uma exceção é lançada.</p>
     *
     * @return instância de {@link Properties} contendo as configurações de conexão
     * @throws RuntimeException caso o arquivo não seja encontrado ou ocorra erro de leitura
     */
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

    /**
     * Retorna a conexão com o banco de dados, criando-a caso ainda não exista.
     *
     * <p>A conexão é única por instância da classe e permanece aberta até que
     * a aplicação seja finalizada (não há método de fechamento nesta implementação).</p>
     *
     * @return conexão ativa com o banco de dados
     * @throws RuntimeException caso ocorra algum erro ao estabelecer a conexão
     */
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
