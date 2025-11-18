package br.backend.estrategia.produto;

import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;
import br.backend.dto.Resposta;
import br.backend.dto.Requisicao;

/**
 * Estratégia responsável por atualizar um produto existente no sistema.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada pelo
 * controlador quando a ação <b>"atualizar"</b> é solicitada.</p>
 *
 * <p>Os dados recebidos na {@link Requisicao} são convertidos para um objeto
 * {@link Produto}, e em seguida o {@link ProdutoServico} é utilizado para
 * realizar a atualização da entidade correspondente.</p>
 *
 * <p>Retorna uma resposta em formato JSON, indicando sucesso e trazendo o produto
 * atualizado no corpo da resposta.</p>
 */
public class AtualizarProdutoEstrategia implements AcaoEstrategia{
    
      private final ProdutoServico produtoServico;

    /**
     * Construtor padrão.
     *
     * @param ProdutoServico serviço responsável pelas operações de atualização de produtos
     */
    public AtualizarProdutoEstrategia(ProdutoServico ProdutoServico) {
        this.produtoServico = ProdutoServico;
    }

    /**
     * Executa a operação de atualização de um produto.
     *
     * <p>Converte os dados da requisição para {@link Produto}, extrai o ID e delega
     * ao serviço a responsabilidade de atualizar o produto no banco de dados.</p>
     *
     * @param requisicao requisição contendo os dados do produto a ser atualizado
     * @return JSON contendo a resposta de sucesso e o produto atualizado
     */
      @Override
   public String executar(Requisicao<?> requisicao) {
        Produto produtoAtualizacao = Util.fromObject(requisicao.getDados(), Produto.class);
        Produto produtoAtualizado = produtoServico.atualizarProduto(produtoAtualizacao.getId(), produtoAtualizacao);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Produto atualizado", produtoAtualizado));
    }
}