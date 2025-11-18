package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.modelo.Produto;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;


/**
 * Estratégia responsável por criar um novo produto no sistema.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é executada quando
 * o controlador recebe a ação <b>"criar"</b> para produtos.</p>
 *
 * <p>Os dados enviados na {@link Requisicao} são convertidos para um objeto
 * {@link Produto}, que é então persistido por meio do {@link ProdutoServico}.
 * A resposta retornada contém o produto criado serializado em JSON.</p>
 */
public class CriarProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    /**
     * Construtor padrão.
     *
     * @param produtoServico serviço responsável pelas operações de criação de produtos
     */
    public CriarProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    /**
     * Executa a criação de um novo produto.
     *
     * <p>Converte os dados da requisição para {@link Produto}, delega ao serviço
     * a persistência da nova entidade e retorna uma {@link Resposta} contendo o
     * produto recém-criado.</p>
     *
     * @param requisicao requisição contendo os dados do produto a ser criado
     * @return JSON representando o sucesso da operação e o produto criado
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        Produto pro = Util.fromObject(requisicao.getDados(), Produto.class);
        Produto criado = produtoServico.inserirProduto(pro);
        return JsonUtil.toJson(new Resposta<>("Sucesso", "Produto criado", criado));
    }
}
