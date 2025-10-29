package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.registro.CriarRegistroEstrategia;
import br.backend.estrategia.registro.ListarRegistroEstrategia;
import br.backend.servico.RegistroServico;
import br.backend.util.JsonUtil;

import java.util.Map;

public class RegistroControladorImpl implements Controlador {

    private final RegistroServico registroServico;
    private final CriarRegistroEstrategia criarEstrategia;
    private final ListarRegistroEstrategia listarEstrategia;

    public RegistroControladorImpl(RegistroServico registroServico) {
        this.registroServico = registroServico;
        this.criarEstrategia = new CriarRegistroEstrategia(registroServico);
        this.listarEstrategia = new ListarRegistroEstrategia(registroServico);
    }

    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            Map<String, AcaoEstrategia> estrategias = Map.of(
                    "criar", new CriarRegistroEstrategia(registroServico),
                    "listar", new ListarRegistroEstrategia(registroServico)
            );

            AcaoEstrategia estrategia = estrategias.get(acao);

            if (estrategia == null) {
                return JsonUtil.toJson(
                        new Resposta<>("erro", "Ação desconhecida: " + acao, null)
                );
            }

            return estrategia.executar(requisicao);

        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(
                    new Resposta<>("erro", "Erro ao processar requisição: " + e.getMessage(), null)
            );
        }
    }
}
