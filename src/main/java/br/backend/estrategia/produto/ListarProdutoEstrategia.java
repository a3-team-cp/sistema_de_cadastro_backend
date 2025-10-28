package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;

public class ListarProdutoEstrategia implements AcaoEstrategia{
    
        private final ProdutoServico produtoServico;

    public ListarProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

         @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de categorias", produtoServico.listarProdutos())
        );
    }
}
