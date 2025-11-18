package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por atualizar uma categoria existente no sistema.
 *
 * <p>Implementa a interface {@link AcaoEstrategia} como parte do padrão Strategy,
 * sendo acionada quando o controlador recebe uma ação do tipo <b>"atualizar"</b>.</p>
 *
 * <p>A estratégia realiza a conversão dos dados recebidos na {@link Requisicao}
 * para um objeto {@link Categoria}, delega a atualização ao {@link CategoriaServico}
 * e retorna o resultado serializado em JSON.</p>
 */
public class AtualizarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    /**
     * Construtor que recebe o serviço responsável pelas operações de Categoria.
     *
     * @param categoriaServico serviço utilizado para realizar a atualização
     */
    public AtualizarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;

    }

    /**
     * Executa a ação de atualização da categoria.
     *
     * <p>Os dados recebidos na requisição são convertidos para um objeto
     * {@link Categoria}, e em seguida o serviço é utilizado para atualizar
     * a categoria correspondente ao ID informado.</p>
     *
     * @param requisicao requisição contendo os dados da categoria a ser atualizada
     * @return JSON contendo uma {@link Resposta} de sucesso e a categoria atualizada
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        Categoria catAtualizacao = Util.fromObject(requisicao.getDados(), Categoria.class);
        Categoria catAtualizada = categoriaServico.atualizarCategoria(catAtualizacao.getId(), catAtualizacao);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Categoria atualizada", catAtualizada));
    }
}
