package br.backend.estrategia.registro;

import br.backend.estrategia.AcaoEstrategia;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.modelo.Registro;
import br.backend.servico.RegistroServico;
import br.backend.util.JsonUtil;

/**
 * Estratégia responsável por criar um novo registro no sistema.
 *
 * <p>Faz parte da implementação do padrão <b>Strategy</b> e é acionada quando
 * o controlador recebe a ação <b>"criar"</b> para registros.</p>
 *
 * <p>Os dados recebidos na {@link Requisicao} são convertidos para JSON
 * e, em seguida, desserializados para um objeto {@link Registro}, que é então
 * persistido via {@link RegistroServico}.</p>
 *
 * <p>Em caso de sucesso, retorna uma resposta JSON com o registro criado.
 * Em caso de erro, retorna uma resposta padronizada contendo a mensagem
 * da exceção.</p>
 */
public class CriarRegistroEstrategia implements AcaoEstrategia {

    private final RegistroServico registroServico;

    /**
     * Construtor padrão.
     *
     * @param registroServico serviço responsável pelas operações de criação de registros
     */
    public CriarRegistroEstrategia(RegistroServico registroServico) {
        this.registroServico = registroServico;
    }

    /**
     * Executa a criação de um novo registro.
     *
     * <p>Converte os dados da requisição utilizando utilitários JSON, delega a
     * persistência ao {@link RegistroServico} e encapsula o resultado em uma
     * {@link Resposta}.</p>
     *
     * @param requisicao requisição contendo os dados do registro a ser criado
     * @return JSON representando o sucesso ou erro da operação
     */
    @Override
    public String executar(Requisicao<?> requisicao) {
        try {
            String dadosJson = JsonUtil.toJson(requisicao.getDados());
            Registro registro = JsonUtil.fromJson(dadosJson, Registro.class);
            
            registroServico.inserirRegistro(registro);
            return JsonUtil.toJson(new Resposta<>("sucesso", "Registro criado com sucesso", registro));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(new Resposta<>("erro", "Erro ao criar registro: " + e.getMessage(), null));
        }
    }
}