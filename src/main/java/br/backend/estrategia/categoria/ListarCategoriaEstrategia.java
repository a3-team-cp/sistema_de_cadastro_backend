package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;

public class ListarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    public ListarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de categorias", categoriaServico.listarCategorias())
        );
    }
}
