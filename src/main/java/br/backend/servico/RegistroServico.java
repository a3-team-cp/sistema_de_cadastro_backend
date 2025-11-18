package br.backend.servico;

import br.backend.dao.RegistroDAO;
import br.backend.modelo.Registro;
import java.util.List;


/**
 * Serviço responsável por encapsular as operações relacionadas aos registros
 * de movimentação no sistema.
 *
 * <p>Atua como camada intermediária entre controladores/estratégias e o
 * {@link RegistroDAO}, garantindo que a lógica de acesso a dados permaneça
 * isolada.</p>
 *
 * <p>No estado atual, o serviço apenas delega chamadas ao DAO, mas esta camada
 * permite facilmente adicionar validações, regras de negócio e transformações
 * no futuro.</p>
 */
public class RegistroServico {


    private final RegistroDAO registroDAO;

    /**
     * Construtor padrão.
     *
     * @param registroDAO implementação utilizada para persistência dos registros
     */
    public RegistroServico(RegistroDAO registroDAO) {
        this.registroDAO = registroDAO;
    }

    /**
     * Insere um novo registro de movimentação no sistema.
     *
     * @param r objeto contendo as informações da movimentação
     */
    public void inserirRegistro(Registro r) {
        registroDAO.inserirRegistro(r);
    }

    /**
     * Retorna todos os registros armazenados, ordenados conforme a implementação do DAO.
     *
     * @return lista de registros
     */
    public List<Registro> listarRegistros() {
        return registroDAO.listarRegistros();
    }
}
