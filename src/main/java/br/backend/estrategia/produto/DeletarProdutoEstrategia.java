package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por deletar um produto com base em seu ID.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é executada quando
 * o controlador recebe a ação <b>"deletar"</b> para produtos.</p>
 *
 * <p>Os dados enviados na {@link Requisicao} são convertidos para um objeto
 * {@link Produto} apenas para extrair o ID. Em seguida, o
 * {@link ProdutoServico} é utilizado para realizar a remoção.</p>
 *
 * <p>A resposta informa se o produto foi deletado com sucesso ou se o ID
 * fornecido não corresponde a nenhum produto existente.</p>
 */
public class DeletarProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;


    /**
     * Construtor padrão.
     *
     * @param ProdutoServico serviço responsável pelas operações de exclusão de produtos
     */
    public DeletarProdutoEstrategia(ProdutoServico ProdutoServico) {
        this.produtoServico = ProdutoServico;
    }

    /**
     * Executa a exclusão do produto com base no ID informado.
     *
     * <p>Converte os dados da requisição para {@link Produto}, extrai o ID,
     * delega a exclusão ao serviço e retorna uma {@link Resposta}
     * indicando sucesso ou erro.</p>
     *
     * @param requisicao requisição contendo o ID do produto a ser excluído
     * @return JSON representando o resultado da operação
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        Produto pro = Util.fromObject(requisicao.getDados(), Produto.class);
        Integer id = pro.getId();

        boolean excluido = produtoServico.deletarProduto(id);

        if (excluido) {
            return JsonUtil.toJson(new Resposta<>("sucesso", "Produto deletado", null));
        } else {
            return JsonUtil.toJson(new Resposta<>("erro", "Produto não encontrado", null));
        }
    }
}
