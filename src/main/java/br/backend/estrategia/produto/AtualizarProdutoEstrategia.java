package br.backend.estrategia.produto;

import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;
import br.backend.dto.Resposta;
import br.backend.dto.Requisicao;

public class AtualizarProdutoEstrategia implements AcaoEstrategia{
    
      private final ProdutoServico produtoServico;

    public AtualizarProdutoEstrategia(ProdutoServico ProdutoServico) {
        this.produtoServico = ProdutoServico;
    }
      
      @Override
   public String executar(Requisicao<?> requisicao) {
        Produto produtoAtualizacao = Util.fromObject(requisicao.getDados(), Produto.class);
        Produto produtoAtualizado = produtoServico.atualizarProduto(produtoAtualizacao.getId(), produtoAtualizacao);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Produto atualizado", produtoAtualizado));
    }
}