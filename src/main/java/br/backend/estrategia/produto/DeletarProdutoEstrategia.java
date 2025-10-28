package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

public class DeletarProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    public DeletarProdutoEstrategia(ProdutoServico ProdutoServico) {
        this.produtoServico = ProdutoServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        Produto pro = Util.fromObject(requisicao.getDados(), Produto.class);
        Integer id = pro.getId();

        boolean excluido = produtoServico.deletarProduto(id);

        if (excluido) {
            return JsonUtil.toJson(new Resposta<>("sucesso", "Produto deletado", null));
        } else {
            return JsonUtil.toJson(new Resposta<>("erro", "Produto não encontrado", null));
        }
    }
}
