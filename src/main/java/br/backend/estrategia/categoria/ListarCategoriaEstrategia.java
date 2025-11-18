package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;

/**
 * Estratégia responsável por listar todas as categorias cadastradas no sistema.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é utilizada quando
 * o controlador recebe a ação <b>"listar"</b> para categorias.</p>
 *
 * <p>Não exige dados adicionais na {@link Requisicao}, sendo apenas um disparo
 * para o serviço recuperar todas as categorias.</p>
 *
 * <p>O resultado é retornado em formato JSON, contendo a lista completa
 * e uma mensagem de sucesso.</p>
 */
public class ListarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    /**
     * Construtor padrão.
     *
     * @param categoriaServico serviço responsável por fornecer a lista de categorias
     */
    public ListarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    /**
     * Executa a listagem de todas as categorias existentes.
     *
     * <p>A requisição não necessita de dados adicionais. A lista é obtida por meio
     * do {@link CategoriaServico} e encapsulada em uma {@link Resposta}.</p>
     *
     * @param requisicao requisição recebida (não utilizada nesta estratégia)
     * @return JSON contendo a lista completa de categorias
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de categorias", categoriaServico.listarCategorias())
        );
    }
}
