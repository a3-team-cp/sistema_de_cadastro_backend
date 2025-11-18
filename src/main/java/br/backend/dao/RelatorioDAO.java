package br.backend.dao;

import br.backend.modelo.Relatorio;
import java.util.List;

/**
 * Interface responsável por definir as operações de acesso a dados
 * relacionadas à composição de relatórios.
 *
 * <p>Um {@link Relatorio} representa a combinação de informações
 * provenientes das tabelas de movimentação (registro) e de produtos,
 * agregadas para fins de visualização e análise.</p>
 *
 * <p>As implementações dessa interface devem realizar consultas que
 * retornem dados consolidados e ordenados conforme regras de negócio
 * associadas à geração de relatórios.</p>
 */
public interface RelatorioDAO {


    /**
     * Retorna uma lista contendo dados consolidados de registros e produtos
     * utilizados para construção do relatório.
     *
     * <p>A ordenação e o formato final dos dados dependem da implementação.</p>
     *
     * @return lista de objetos {@link Relatorio}
     */
    List<Relatorio> listarRelatorio();
}
