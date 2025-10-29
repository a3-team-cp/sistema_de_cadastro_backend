package br.backend.estrategia.registro;

import br.backend.estrategia.AcaoEstrategia;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.modelo.Registro;
import br.backend.servico.RegistroServico;
import br.backend.util.JsonUtil;

public class CriarRegistroEstrategia implements AcaoEstrategia {

    private final RegistroServico registroServico;

    public CriarRegistroEstrategia(RegistroServico registroServico) {
        this.registroServico = registroServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        try {
            String dadosJson = JsonUtil.toJson(requisicao.getDados());
            Registro registro = JsonUtil.fromJson(dadosJson, Registro.class);
            
            registroServico.inserirRegistro(registro);
            return JsonUtil.toJson(new Resposta<>("sucesso", "Registro criado com sucesso", registro));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(new Resposta<>("erro", "Erro ao criar registro: " + e.getMessage(), null));
        }
    }
}