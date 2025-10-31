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
 *
 * @author loren
 */
public class DiminuirPrecoProdutoEstrategia implements AcaoEstrategia {

    private final ProdutoServico produtoServico;

    public DiminuirPrecoProdutoEstrategia(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
    }

    @Override
    public String executar(Requisicao<?> requisicao) {
        Double percentual = Util.fromObject(requisicao.getDados(), Double.class);
        produtoServico.diminuirValorProduto(percentual);
        return JsonUtil.toJson(new Resposta<>("sucesso", "Produto atualizado", null));
    }
}
