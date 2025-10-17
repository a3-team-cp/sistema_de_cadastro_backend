package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.modelo.Requisicao;
import br.backend.servico.CategoriaServico;
import br.backend.servico.ProdutoServico;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProdutoControladorImpl implements Controlador {

    private ProdutoServico produtoServico;

    public ProdutoControladorImpl(ProdutoServico produtoServico){
        this.produtoServico = produtoServico;
    }


    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        return "";
    }
}
