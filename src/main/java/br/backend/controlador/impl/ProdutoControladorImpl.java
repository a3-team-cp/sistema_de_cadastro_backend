package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.modelo.Categoria;
import br.backend.modelo.Produto;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.produto.AtualizarProdutoEstrategia;
import br.backend.estrategia.produto.CriarProdutoEstrategia;
import br.backend.estrategia.produto.DeletarProdutoEstrategia;
import br.backend.estrategia.produto.EncontrarProdutoEstrategia;
import br.backend.estrategia.produto.ListarProdutoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class ProdutoControladorImpl implements Controlador {

    private final ProdutoServico produtoServico;
    private final ObjectMapper objectMapper;

    public ProdutoControladorImpl(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            Map<String, AcaoEstrategia> estrategias = Map.of(
                    "criar", new CriarProdutoEstrategia(produtoServico),
                    "encontrar", new EncontrarProdutoEstrategia(produtoServico),
                    "atualizar", new AtualizarProdutoEstrategia(produtoServico),
                    "deletar", new DeletarProdutoEstrategia(produtoServico),
                    "listar", new ListarProdutoEstrategia(produtoServico)
            );
            AcaoEstrategia estrategia = estrategias.get(acao);

            if (estrategia == null) {
                return JsonUtil.toJson(
                        new Resposta<>("erro", "Ação desconhecida: " + acao, null)
                );
            }

            // Executa a estratégia correspondente
            return estrategia.executar(requisicao);

        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(
                    new Resposta<>("erro", "Erro ao processar requisição: " + e.getMessage(), null)
            );
        }
    }
}
