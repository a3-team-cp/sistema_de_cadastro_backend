package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por aumentar o preço de todos os produtos com base
 * em um percentual fornecido.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada pelo
 * controlador quando a ação <b>"aumentar"</b> é solicitada.</p>
 *
 * <p>O percentual recebido na {@link Requisicao} é convertido para {@link Double}
 * e passado ao {@link ProdutoServico}, que executa o ajuste de preços.</p>
 *
 * <p>Retorna uma resposta padronizada em formato JSON, indicando o sucesso
 * da operação.</p>
 */
public class AumentarPrecoProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    /**
     * Construtor padrão.
     *
     * @param produtoServico serviço responsável pela lógica de alteração de preços
     */
    public AumentarPrecoProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    /**
     * Executa o aumento percentual de preço de todos os produtos.
     *
     * <p>O método extrai da requisição um valor {@link Double} que representa
     * o percentual de aumento, delega a operação ao {@link ProdutoServico}
     * e retorna uma resposta JSON informando o sucesso da ação.</p>
     *
     * @param requisicao requisição contendo o percentual de aumento
     * @return JSON com a resposta da operação
     */
    @Override
     public String executar(Requisicao<?> requisicao) {
        Double percentual = Util.fromObject(requisicao.getDados(), Double.class);
        produtoServico.aumentarValorProduto(percentual);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Preços aumentados com sucesso", null));
    }
}
