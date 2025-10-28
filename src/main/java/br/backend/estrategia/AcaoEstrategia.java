package br.backend.estrategia;

import br.backend.dto.Requisicao;

public interface AcaoEstrategia {

    String executar(Requisicao<?> requisicao);
}
