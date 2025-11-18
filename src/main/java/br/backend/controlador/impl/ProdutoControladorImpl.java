package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.produto.AtualizarProdutoEstrategia;
import br.backend.estrategia.produto.AumentarPrecoProdutoEstrategia;
import br.backend.estrategia.produto.CriarProdutoEstrategia;
import br.backend.estrategia.produto.DeletarProdutoEstrategia;
import br.backend.estrategia.produto.DiminuirPrecoProdutoEstrategia;
import br.backend.estrategia.produto.EncontrarProdutoEstrategia;
import br.backend.estrategia.produto.ListarProdutoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

/**
 * Controlador responsável por processar requisições relacionadas à entidade Produto.
 *
 * <p>Utiliza o padrão Strategy para delegar dinamicamente a execução das ações
 * (criar, encontrar, atualizar, deletar, listar, aumentar preço, diminuir preço)
 * para classes específicas, promovendo organização e extensibilidade.</p>
 *
 * <p>A comunicação segue o padrão da aplicação:
 * uma {@link Requisicao} genérica é recebida e convertida para uma {@link Resposta}
 * serializada em JSON.</p>
 */
public class ProdutoControladorImpl implements Controlador {

    private final ProdutoServico produtoServico;
    private final ObjectMapper objectMapper;

    /**
     * Construtor padrão.
     *
     * @param produtoServico serviço responsável pelas operações de Produto
     */
    public ProdutoControladorImpl(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Processa uma requisição relacionada a Produto, determinando qual estratégia
     * deve ser utilizada com base no campo {@code acao}.
     *
     * <p>Se a ação não existir no mapa de estratégias, uma resposta de erro é retornada.
     * Caso ocorra uma exceção durante o processamento, uma resposta padronizada de erro
     * também é gerada.</p>
     *
     * @param requisicao objeto contendo a ação desejada e os dados enviados pelo cliente
     * @return um JSON representando o resultado da operação executada
     */
    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            Map<String, AcaoEstrategia> estrategias = Map.of(
                    "criar", new CriarProdutoEstrategia(produtoServico),
                    "encontrar", new EncontrarProdutoEstrategia(produtoServico),
                    "atualizar", new AtualizarProdutoEstrategia(produtoServico),
                    "deletar", new DeletarProdutoEstrategia(produtoServico),
                    "listar", new ListarProdutoEstrategia(produtoServico),
                    "aumentar", new AumentarPrecoProdutoEstrategia(produtoServico),
                    "diminuir", new DiminuirPrecoProdutoEstrategia(produtoServico)
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
