package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;
/**
 * Estratégia responsável por localizar uma categoria pelo seu ID.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada pelo controlador
 * quando a ação <b>"encontrar"</b> é requisitada.</p>
 *
 * <p>Os dados enviados na {@link Requisicao} são convertidos para um objeto
 * {@link Categoria} apenas para acessar o ID desejado. A busca é então
 * delegada ao {@link CategoriaServico}.</p>
 *
 * <p>A resposta indica se a categoria foi encontrada e, em caso positivo,
 * retorna o objeto correspondente em formato JSON.</p>
 */
public class EncontrarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    /**
     * Construtor padrão.
     *
     * @param categoriaServico serviço responsável pelas operações de busca
     */
    public EncontrarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;

    }

    /**
     * Executa a operação de busca de categoria por ID.
     *
     * <p>Converte os dados da requisição para {@link Categoria}, extrai o ID,
     * realiza a busca e retorna uma {@link Resposta} indicando sucesso ou erro.</p>
     *
     * @param requisicao requisição contendo o ID da categoria a ser buscada
     * @return JSON representando o resultado da operação
     */
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
