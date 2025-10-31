package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 *
 * @author loren
 */
public class AumentarPrecoProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    public AumentarPrecoProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    @Override
     public String executar(Requisicao<?> requisicao) {
        Double percentual = Util.fromObject(requisicao.getDados(), Double.class);
        produtoServico.aumentarValorProduto(percentual);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Preços aumentados com sucesso", null));
    }
}
