package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.categoria.*;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;

import java.util.Map;

public class CategoriaControladorImpl implements Controlador {

    private final CategoriaServico categoriaServico;

    public CategoriaControladorImpl(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            // Mapa de ações -> estratégias
            Map<String, AcaoEstrategia> estrategias = Map.of(
                    "criar", new CriarCategoriaEstrategia(categoriaServico),
                    "encontrar", new EncontrarCategoriaEstrategia(categoriaServico),
                    "atualizar", new AtualizarCategoriaEstrategia(categoriaServico),
                    "deletar", new DeletarCategoriaEstrategia(categoriaServico),
                    "listar", new ListarCategoriaEstrategia(categoriaServico)
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
