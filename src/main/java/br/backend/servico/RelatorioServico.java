package br.backend.servico;

import br.backend.dao.RelatorioDAO;
import br.backend.modelo.Relatorio;
import java.util.List;

public class RelatorioServico {

    private final RelatorioDAO relatorioDAO;

    public RelatorioServico(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    public List<Relatorio> listarRelatorio() {
        return relatorioDAO.listarRelatorio();
    }
}
