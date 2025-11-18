package br.backend.servidor;

import br.backend.controlador.impl.CategoriaControladorImpl;
import br.backend.controlador.impl.ProdutoControladorImpl;
import br.backend.controlador.impl.RegistroControladorImpl;
import br.backend.controlador.impl.RelatorioControladorImpl;
import br.backend.database.Database;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.util.JsonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Servidor responsável por receber requisições JSON via sockets TCP,
 * processá-las e delegar o tratamento aos controladores específicos
 * (Categoria, Produto, Registro e Relatório).
 *
 * <p>Este servidor segue um modelo simples de comunicação síncrona:
 * cada cliente conectado tem sua própria thread dedicada para processar
 * as requisições recebidas.</p>
 *
 * <p>O formato esperado de mensagem é um JSON correspondente ao DTO
 * {@link Requisicao}, que o servidor converte e encaminha ao controlador
 * apropriado com base no campo <code>entidade</code>.</p>
 */
public class Servidor {

    private final int porta;

    private Database database;

    private CategoriaControladorImpl categoriaControlador;
    
    private ProdutoControladorImpl produtoControlador;
    
    private RegistroControladorImpl registroControlador;
    
    private RelatorioControladorImpl relatorioControlador;

    /**
     * Construtor principal do servidor.
     *
     * @param porta                porta TCP na qual o servidor irá escutar
     * @param categoriaControlador controlador para operações de categoria
     * @param produtoControlador   controlador para operações de produto
     * @param registroControlador  controlador para operações de registro
     * @param relatorioControlador controlador para operações de relatório
     */
    public Servidor(int porta, CategoriaControladorImpl categoriaControlador, ProdutoControladorImpl produtoControlador, RegistroControladorImpl registroControlador, RelatorioControladorImpl relatorioControlador) {
        this.porta = porta;

        this.categoriaControlador = categoriaControlador;
        this.produtoControlador = produtoControlador;
        this.registroControlador = registroControlador;
        this.relatorioControlador = relatorioControlador;
    }

    /**
     * Inicia o servidor e começa a escutar conexões de clientes.
     *
     * <p>A cada cliente conectado, uma nova thread é criada para processamento
     * independente, permitindo múltiplos clientes simultâneos.</p>
     */
    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
                new Thread(() -> processarCliente(cliente)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Processa as requisições de um cliente conectado.
     *
     * <p>Lê mensagens JSON linha a linha, processa a requisição e devolve
     * a resposta para o cliente.</p>
     *
     * @param cliente conexão socket do cliente
     */
    private void processarCliente(Socket cliente) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream())); PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

            String jsonRequisicao;
            while ((jsonRequisicao = in.readLine()) != null) {
                String resposta = processarRequisicao(jsonRequisicao);
                out.println(resposta);
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar cliente " + cliente.getInetAddress() + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cliente.close();
                System.out.println("Cliente desconectado: " + cliente.getInetAddress());
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Converte o JSON recebido para uma {@link Requisicao} e delega o tratamento
     * ao controlador apropriado, baseado no campo <code>entidade</code>.
     *
     * @param json JSON contendo a requisição
     * @return JSON contendo a resposta formatada pelo controlador
     */
    private String processarRequisicao(String json) {
        try {
            // Converte o JSON para Requisicao genérica
            Requisicao<?> req = JsonUtil.fromJson(json, Requisicao.class);
            String entidade = req.getEntidade().toLowerCase();

            return switch (entidade) {
                case "categoria" -> categoriaControlador.processarRequisicao(req);
                case "produto" -> produtoControlador.processarRequisicao(req);
                case "registro" -> registroControlador.processarRequisicao(req);
                case "relatorio" -> relatorioControlador.processarRequisicao(req);
                default -> JsonUtil.toJson(new Resposta("erro", "Entidade '" + entidade + "' não reconhecida", null));
            };

        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(new Resposta("erro", "Erro interno", null));
        }
    }

}
