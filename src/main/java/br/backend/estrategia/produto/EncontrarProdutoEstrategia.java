package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por localizar um produto com base em seu ID.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada quando
 * o controlador recebe a ação <b>"encontrar"</b> para produtos.</p>
 *
 * <p>Os dados enviados na {@link Requisicao} são convertidos para um objeto
 * {@link Produto} apenas para extrair o ID desejado. A operação de busca é então
 * delegada ao {@link ProdutoServico}.</p>
 *
 * <p>A resposta JSON informa se o produto foi encontrado e, em caso positivo,
 * retorna o objeto correspondente.</p>
 */
public class EncontrarProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    /**
     * Construtor padrão.
     *
     * @param ProdutoServico serviço responsável pelas operações de busca de produtos
     */
    public EncontrarProdutoEstrategia(ProdutoServico ProdutoServico) {
        this.produtoServico = ProdutoServico;
    }

    /**
     * Executa a busca de um produto pelo ID informado na requisição.
     *
     * <p>Converte os dados recebidos para {@link Produto}, extrai o ID, realiza
     * a busca e retorna uma {@link Resposta} em JSON indicando sucesso ou erro.</p>
     *
     * @param requisicao requisição contendo o ID do produto a ser encontrado
     * @return JSON representando o resultado da operação
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        // Converte os dados da requisição em Categoria para extrair o ID
        Produto pro = Util.fromObject(requisicao.getDados(), Produto.class);
        Integer id = pro.getId();

        Produto encontrado = produtoServico.buscarPorId(id);

        if (encontrado != null) {
            return JsonUtil.toJson(new Resposta<>("sucesso", "Produto encontrado", encontrado));
        } else {
            return JsonUtil.toJson(new Resposta<>("erro", "Produto não encontrado", null));
        }
    }
}
