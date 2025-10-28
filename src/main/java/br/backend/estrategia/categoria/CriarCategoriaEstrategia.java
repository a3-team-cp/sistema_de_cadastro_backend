package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

public class CriarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    public CriarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;

    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        Categoria cat = Util.fromObject(requisicao.getDados(), Categoria.class);
        Categoria criada = categoriaServico.inserirCategoria(cat);
        return JsonUtil.toJson(new Resposta<>("Sucesso", "Categoria criada", criada));
    }
}
