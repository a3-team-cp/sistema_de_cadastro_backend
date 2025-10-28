package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

public class EncontrarProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    public EncontrarProdutoEstrategia(ProdutoServico ProdutoServico) {
        this.produtoServico = ProdutoServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        // Converte os dados da requisição em Categoria para extrair o ID
        Produto pro = Util.fromObject(requisicao.getDados(), Produto.class);
        Integer id = pro.getId();

        Produto encontrado = produtoServico.buscarPorId(id);

        if (encontrado != null) {
            return JsonUtil.toJson(new Resposta<>("sucesso", "Categoria encontrada", encontrado));
        } else {
            return JsonUtil.toJson(new Resposta<>("erro", "Categoria não encontrada", null));
        }
    }
}
