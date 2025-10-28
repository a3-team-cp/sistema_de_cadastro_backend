package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

public class DeletarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    public DeletarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        Categoria cat = Util.fromObject(requisicao.getDados(), Categoria.class);
        Integer id = cat.getId();

        boolean excluido = categoriaServico.deletarCategoria(id);

        if (excluido) {
            return JsonUtil.toJson(new Resposta<>("sucesso", "Categoria deletada", null));
        } else {
            return JsonUtil.toJson(new Resposta<>("erro", "Categoria não encontrada", null));
        }
    }
}
