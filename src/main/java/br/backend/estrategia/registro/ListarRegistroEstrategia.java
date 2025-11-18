package br.backend.estrategia.registro;

import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.servico.RegistroServico;
import br.backend.util.JsonUtil;

/**
 * Estratégia responsável por listar todos os registros cadastrados no sistema.
 *
 * <p>Integra o padrão <b>Strategy</b> utilizado pelos controladores, sendo
 * acionada quando a ação <b>"listar"</b> é solicitada para registros.</p>
 *
 * <p>Esta estratégia não depende de dados adicionais contidos na
 * {@link Requisicao}, servindo apenas para recuperar todos os registros
 * existentes por meio do {@link RegistroServico}.</p>
 *
 * <p>O resultado é retornado como um JSON contendo uma lista de registros e uma
 * mensagem de sucesso.</p>
 */
public class ListarRegistroEstrategia implements AcaoEstrategia {

    private final RegistroServico registroServico;


    /**
     * Construtor padrão.
     *
     * @param registroServico serviço responsável por fornecer a lista de registros
     */
    public ListarRegistroEstrategia(RegistroServico registroServico) {
        this.registroServico = registroServico;
    }


    /**
     * Executa a listagem de todos os registros.
     *
     * <p>Não utiliza dados da requisição. Apenas delega a recuperação dos registros
     * ao {@link RegistroServico} e encapsula o resultado em uma {@link Resposta}.</p>
     *
     * @param requisicao requisição recebida (não utilizada nesta operação)
     * @return JSON contendo a lista de registros
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        return JsonUtil.toJson(
                new Resposta<>("sucesso", "Lista de registros", registroServico.listarRegistros())
        );
    }
}
