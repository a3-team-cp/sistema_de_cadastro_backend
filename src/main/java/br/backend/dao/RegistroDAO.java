package br.backend.dao;

import br.backend.modelo.Registro;
import java.util.List;


/**
 * Interface responsável por definir as operações de acesso a dados
 * relacionadas à entidade {@link Registro}.
 *
 * <p>Um registro representa uma movimentação de estoque (entrada ou saída)
 * vinculada a um produto, incluindo quantidade, status e data.</p>
 *
 * <p>As implementações desta interface devem garantir o correto tratamento das
 * operações de persistência, respeitando as regras de negócio associadas
 * às movimentações.</p>
 */
public interface RegistroDAO {


    /**
     * Insere um novo registro de movimentação no banco de dados.
     *
     * @param registro objeto contendo os dados da movimentação a ser salva
     */
    void inserirRegistro(Registro registro);

    /**
     * Retorna todos os registros cadastrados, ordenados conforme a implementação.
     *
     * @return lista de registros de movimentação
     */
    List<Registro> listarRegistros();
}