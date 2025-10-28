package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

public class CriarProdutoEstrategia implements AcaoEstrategia{
 
    private final ProdutoServico produtoServico;

    public CriarProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }


    @Override
    public String executar(Requisicao<?> requisicao) {
        Produto pro =  Util.fromObject(requisicao.getDados(), Produto.class);
        Produto criado = produtoServico.inserirProduto(pro);
        return JsonUtil.toJson(new Resposta<>("Sucesso", "Categoria criada", criado));
    }
}
