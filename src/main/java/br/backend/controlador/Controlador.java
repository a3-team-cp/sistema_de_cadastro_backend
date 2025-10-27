package br.backend.controlador;

import br.backend.dto.Requisicao;

public interface Controlador {

    public String processarRequisicao(Requisicao<?> requisicao);
}
