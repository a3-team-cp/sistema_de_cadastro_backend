package br.backend.servico;

import br.backend.dao.RelatorioDAO;
import br.backend.modelo.Relatorio;
import java.util.List;

/**
 * Serviço responsável por fornecer acesso aos dados consolidados utilizados
 * na geração de relatórios.
 *
 * <p>Funciona como uma camada intermediária entre controladores/estratégias e o
 * {@link RelatorioDAO}, permitindo centralizar regras de negócio relacionadas
 * à montagem e consulta de relatórios.</p>
 *
 * <p>Atualmente, o serviço atua como um simples delegador, porém sua existência
 * permite evoluções futuras, como aplicação de filtros, ordenações adicionais,
 * validações ou transformação dos dados antes de retorná-los à camada superior.</p>
 */
public class RelatorioServico {

    private final RelatorioDAO relatorioDAO;


    /**
     * Construtor padrão.
     *
     * @param relatorioDAO DAO responsável pelas consultas de relatório
     */
    public RelatorioServico(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    /**
     * Recupera a lista completa de registros consolidados para exibição em relatório.
     *
     * <p>A estrutura e ordenação dos dados retornados dependem da implementação do DAO.</p>
     *
     * @return lista de objetos {@link Relatorio}
     */
    public List<Relatorio> listarRelatorio() {
        return relatorioDAO.listarRelatorio();
    }
}
