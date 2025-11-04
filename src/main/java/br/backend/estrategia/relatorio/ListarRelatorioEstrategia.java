package br.backend.estrategia.relatorio;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.RelatorioServico;
import br.backend.util.JsonUtil;

public class ListarRelatorioEstrategia implements AcaoEstrategia {

    private final RelatorioServico relatorioServico;

    public ListarRelatorioEstrategia(RelatorioServico relatorioServico) {
        this.relatorioServico = relatorioServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de relatórios", relatorioServico.listarRelatorio())
        );
    }
}
