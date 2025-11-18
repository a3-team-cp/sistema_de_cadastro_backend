package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;

/**
 * Estratégia responsável por listar todos os produtos cadastrados no sistema.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada quando
 * o controlador recebe a ação <b>"listar"</b> para produtos.</p>
 *
 * <p>Esta estratégia não utiliza dados da {@link Requisicao}, servindo apenas
 * como um disparo para recuperação de todos os produtos através do
 * {@link ProdutoServico}.</p>
 *
 * <p>O resultado é retornado em formato JSON contendo a lista completa de produtos.</p>
 */
public class ListarProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    /**
     * Construtor padrão.
     *
     * @param produtoServico serviço responsável por fornecer a lista de produtos
     */
    public ListarProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    /**
     * Executa a operação de listagem de todos os produtos.
     *
     * <p>A requisição não necessita de dados adicionais. A lista é obtida por meio
     * do {@link ProdutoServico} e encapsulada em uma {@link Resposta}.</p>
     *
     * @param requisicao requisição recebida (não utilizada nesta estratégia)
     * @return JSON contendo a lista de produtos
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de produtos", produtoServico.listarProdutos())
        );
    }
}
