package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.categoria.*;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;

import java.util.Map;

/**
 * Controlador responsável por processar requisições relacionadas a operações de Categoria.
 *
 * <p>Esta classe utiliza o padrão Strategy, delegando cada ação (criar, encontrar,
 * atualizar, deletar, listar) para uma implementação específica de {@link AcaoEstrategia}.
 *
 * <p>A requisição recebida contém a ação a ser executada e os dados necessários.
 * O controlador identifica a ação solicitada, seleciona a estratégia correta
 * e executa a operação correspondente por meio do {@link CategoriaServico}.
 *
 * <p>Em caso de ação desconhecida ou erro de processamento, uma resposta de erro
 * em formato JSON é retornada.
 */
public class CategoriaControladorImpl implements Controlador {

    private final CategoriaServico categoriaServico;

    /**
     * Cria uma instância do controlador de categoria.
     *
     * @param categoriaServico serviço responsável pelas operações de negócio relacionadas a Categoria
     */
    public CategoriaControladorImpl(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    /**
     * Processa uma requisição de categoria com base na ação informada.
     *
     * @param requisicao objeto contendo a ação e os dados da requisição
     * @return JSON contendo o resultado da operação ou mensagem de erro
     */
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
