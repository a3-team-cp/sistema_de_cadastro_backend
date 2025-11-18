package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.estrategia.AcaoEstrategia;
import br.backend.estrategia.registro.CriarRegistroEstrategia;
import br.backend.estrategia.registro.ListarRegistroEstrategia;
import br.backend.servico.RegistroServico;
import br.backend.util.JsonUtil;

import java.util.Map;

/**
 * Controlador responsável por processar requisições referentes aos registros do sistema.
 *
 * <p>Utiliza o padrão Strategy para delegar as ações de criação e listagem
 * a classes especializadas. Esse padrão facilita a extensão futura, caso novas
 * operações sobre registros sejam adicionadas.</p>
 *
 * <p>A comunicação segue o padrão da aplicação, recebendo um objeto
 * {@link Requisicao} e retornando um JSON representando uma {@link Resposta}.</p>
 */
public class RegistroControladorImpl implements Controlador {

    private final RegistroServico registroServico;
    private final CriarRegistroEstrategia criarEstrategia;
    private final ListarRegistroEstrategia listarEstrategia;

    /**
     * Construtor padrão.
     *
     * @param registroServico serviço que fornece as operações relacionadas a registros
     */
    public RegistroControladorImpl(RegistroServico registroServico) {
        this.registroServico = registroServico;
        this.criarEstrategia = new CriarRegistroEstrategia(registroServico);
        this.listarEstrategia = new ListarRegistroEstrategia(registroServico);
    }

    /**
     * Processa uma requisição de registro com base na ação informada.
     *
     * <p>As ações suportadas são:
     * <ul>
     *     <li><b>criar</b>: cria um novo registro;</li>
     *     <li><b>listar</b>: retorna todos os registros existentes.</li>
     * </ul>
     * </p>
     *
     * <p>Se a ação fornecida não corresponder a nenhuma estratégia conhecida,
     * é retornada uma resposta padronizada de erro. Qualquer exceção durante
     * a execução também gera uma resposta de erro.</p>
     *
     * @param requisicao objeto contendo a ação desejada e os dados da operação
     * @return JSON representando o sucesso ou erro da operação executada
     */
    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            Map<String, AcaoEstrategia> estrategias = Map.of(
                    "criar", new CriarRegistroEstrategia(registroServico),
                    "listar", new ListarRegistroEstrategia(registroServico)
            );

            AcaoEstrategia estrategia = estrategias.get(acao);

            if (estrategia == null) {
                return JsonUtil.toJson(
                        new Resposta<>("erro", "Ação desconhecida: " + acao, null)
                );
            }

            return estrategia.executar(requisicao);

        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(
                    new Resposta<>("erro", "Erro ao processar requisição: " + e.getMessage(), null)
            );
        }
    }
}
