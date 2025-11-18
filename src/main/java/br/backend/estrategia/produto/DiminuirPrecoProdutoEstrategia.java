/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.backend.estrategia.produto;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import br.backend.util.Util;

/**
 * Estratégia responsável por reduzir o preço de todos os produtos com base
 * em um percentual informado.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é executada quando
 * o controlador recebe a ação <b>"diminuir"</b> para produtos.</p>
 *
 * <p>O percentual recebido na {@link Requisicao} é convertido para {@link Double}
 * e passado ao {@link ProdutoServico}, que aplica a diminuição do valor em todos
 * os produtos cadastrados.</p>
 *
 * <p>A resposta JSON indica o sucesso da operação.</p>
 */
public class DiminuirPrecoProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    /**
     * Construtor padrão.
     *
     * @param produtoServico serviço responsável por aplicar a redução de preço
     */
    public DiminuirPrecoProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    /**
     * Executa a redução percentual do preço de todos os produtos.
     *
     * <p>Converte o valor recebido na requisição para {@link Double}, delega a
     * operação ao {@link ProdutoServico} e retorna uma {@link Resposta} em JSON
     * informando o sucesso da atualização.</p>
     *
     * @param requisicao requisição contendo o percentual de diminuição de preço
     * @return JSON com a resposta da operação
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        Double percentual = Util.fromObject(requisicao.getDados(), Double.class);
        produtoServico.diminuirValorProduto(percentual);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Produto atualizado", null));
    }
}
