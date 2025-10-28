package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EncontrarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    public EncontrarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;

    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        // Converte os dados da requisição em Categoria para extrair o ID
        Categoria cat = Util.fromObject(requisicao.getDados(), Categoria.class);
        Integer id = cat.getId();

        Categoria encontrada = categoriaServico.buscarPorId(id);

        if (encontrada != null) {
            return JsonUtil.toJson(new Resposta<>("sucesso", "Categoria encontrada", encontrada));
        } else {
            return JsonUtil.toJson(new Resposta<>("erro", "Categoria não encontrada", null));
        }
    }
}
