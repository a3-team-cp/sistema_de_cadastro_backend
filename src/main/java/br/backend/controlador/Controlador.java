package br.backend.controlador;

import br.backend.modelo.Requisicao;

public interface Controlador {

    public String processarRequisicao(Requisicao<?> requisicao);
}
