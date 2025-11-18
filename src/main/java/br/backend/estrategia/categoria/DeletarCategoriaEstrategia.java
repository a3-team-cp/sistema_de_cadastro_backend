package br.backend.estrategia.categoria;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Categoria;
import br.backend.servico.CategoriaServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por deletar uma categoria existente.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada quando
 * o controlador recebe a ação <b>"deletar"</b> para categorias.</p>
 *
 * <p>Os dados enviados na {@link Requisicao} são convertidos para um objeto
 * {@link Categoria} apenas para extrair o ID, que é então utilizado pelo
 * {@link CategoriaServico} para realizar a exclusão.</p>
 *
 * <p>A resposta JSON indica se a categoria foi removida com sucesso ou se não
 * foi encontrada.</p>
 */
public class DeletarCategoriaEstrategia implements AcaoEstrategia {

    private final CategoriaServico categoriaServico;

    /**
     * Construtor padrão.
     *
     * @param categoriaServico serviço utilizado para realizar a exclusão
     */
    public DeletarCategoriaEstrategia(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }

    /**
     * Executa a exclusão da categoria.
     *
     * <p>Converte os dados recebidos para um objeto {@link Categoria} a fim de obter
     * seu ID, e delega ao serviço a remoção correspondente. Retorna uma
     * {@link Resposta} indicando sucesso ou erro.</p>
     *
     * @param requisicao requisição contendo o ID da categoria a ser deletada
     * @return JSON representando o resultado da operação
     */
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
