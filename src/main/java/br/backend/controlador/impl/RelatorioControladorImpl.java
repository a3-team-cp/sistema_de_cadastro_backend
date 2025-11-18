package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.relatorio.ListarRelatorioEstrategia;
import br.backend.servico.RelatorioServico;
import br.backend.util.JsonUtil;
import java.util.Map;


/**
 * Controlador responsável por processar requisições relacionadas aos relatórios do sistema.
 *
 * <p>Atualmente suporta apenas a ação de listagem, delegando sua execução
 * para a {@link ListarRelatorioEstrategia}. O uso do padrão Strategy permite
 * adicionar outras ações futuramente sem alterar a estrutura principal do controlador.</p>
 *
 * <p>A comunicação segue o padrão da aplicação, recebendo um objeto {@link Requisicao}
 * e retornando uma {@link Resposta} serializada em JSON.</p>
 */
public class RelatorioControladorImpl implements Controlador {

    private final RelatorioServico relatorioServico;
    private final ListarRelatorioEstrategia listarEstrategia;

    /**
     * Construtor padrão.
     *
     * @param relatorioServico serviço responsável pelas operações de relatório
     */
    public RelatorioControladorImpl(RelatorioServico relatorioServico) {
        this.relatorioServico = relatorioServico;
        this.listarEstrategia = new ListarRelatorioEstrategia(relatorioServico);
    }

    /**
     * Processa uma requisição referente a relatórios.
     *
     * <p>Atualmente suporta apenas a ação:
     * <ul>
     *     <li><b>listar</b>: retorna os relatórios gerados.</li>
     * </ul>
     * </p>
     *
     * <p>Caso a ação recebida não corresponda a nenhuma estratégia conhecida,
     * uma resposta padronizada de erro é retornada. Qualquer exceção inesperada
     * também resulta em uma resposta de erro.</p>
     *
     * @param requisicao objeto contendo a ação desejada e dados adicionais
     * @return JSON representando o resultado (sucesso ou erro) da operação
     */
    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            Map<String, AcaoEstrategia> estrategias = Map.of(
                    "listar", new ListarRelatorioEstrategia(relatorioServico)
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
