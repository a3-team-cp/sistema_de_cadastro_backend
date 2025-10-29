package br.backend.estrategia.registro;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.RegistroServico;
import br.backend.util.JsonUtil;

public class ListarRegistroEstrategia implements AcaoEstrategia {

    private final RegistroServico registroServico;

    public ListarRegistroEstrategia(RegistroServico registroServico) {
        this.registroServico = registroServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de registros", registroServico.listarRegistros())
        );
    }
}
