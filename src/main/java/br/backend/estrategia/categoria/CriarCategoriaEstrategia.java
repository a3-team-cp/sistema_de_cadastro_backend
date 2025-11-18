package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por criar uma nova categoria no sistema.
 *
 * <p>Esta classe faz parte da implementação do padrão <b>Strategy</b> e é utilizada
 * quando o controlador recebe a ação <b>"criar"</b> para categorias.</p>
 *
 * <p>Os dados enviados na {@link Requisicao} são convertidos para um objeto
 * {@link Categoria}, o qual é então persistido através do {@link CategoriaServico}.
 * O resultado é retornado ao cliente em formato JSON.</p>
 */
public class CriarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;


    /**
     * Construtor que recebe o serviço responsável por operações de categoria.
     *
     * @param categoriaServico serviço utilizado para realizar a inserção da categoria
     */
    public CriarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;

    }

    /**
     * Executa a criação de uma nova categoria.
     *
     * <p>O método converte os dados recebidos para um objeto {@link Categoria},
     * delega ao serviço a inserção da nova categoria e retorna uma resposta
     * padronizada com o objeto criado.</p>
     *
     * @param requisicao requisição contendo os dados da categoria a ser criada
     * @return JSON representando o sucesso da operação e a categoria criada
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        Categoria cat = Util.fromObject(requisicao.getDados(), Categoria.class);
        Categoria criada = categoriaServico.inserirCategoria(cat);
        return JsonUtil.toJson(new Resposta<>("Sucesso", "Categoria criada", criada));
    }
}
