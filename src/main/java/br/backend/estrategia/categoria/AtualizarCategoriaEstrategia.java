package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

public class AtualizarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    public AtualizarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;

    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        Categoria catAtualizacao = Util.fromObject(requisicao.getDados(), Categoria.class);
        Categoria catAtualizada = categoriaServico.atualizarCategoria(catAtualizacao.getId(), catAtualizacao);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Categoria atualizada", catAtualizada));
    }
}
