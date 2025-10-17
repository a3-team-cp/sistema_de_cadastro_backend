package br.backend.servidor;


import br.backend.controlador.impl.CategoriaControladorImpl;
import br.backend.controlador.impl.ProdutoControladorImpl;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.dao.impl.ProdutoDAOImpl;
import br.backend.database.Database;
import br.backend.modelo.Requisicao;
import br.backend.modelo.Resposta;
import br.backend.servico.CategoriaServico;
import br.backend.servico.ProdutoServico;
import br.backend.util.JsonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    private final int porta;

    private Database database;

    private CategoriaServico categoriaServico;
    private CategoriaDAOImpl categoriaDAO;
    private CategoriaControladorImpl categoriaControlador;

    private ProdutoServico produtoServico;
    private ProdutoDAOImpl produtoDAO;
    private ProdutoControladorImpl produtoControlador;

    public Servidor(int porta) {
        this.porta = porta;
        // Inicializa o Database apenas uma vez
        this.database = new Database();

        this.categoriaDAO = new CategoriaDAOImpl(database);
        this.categoriaServico = new CategoriaServico(categoriaDAO);
        this.categoriaControlador = new CategoriaControladorImpl(categoriaServico);



        this.produtoDAO = new ProdutoDAOImpl(database);
        this.produtoServico = new ProdutoServico(produtoDAO);
        this.produtoControlador = new ProdutoControladorImpl(produtoServico);
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                         PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

                        String jsonRequisicao;
                        while ((jsonRequisicao = in.readLine()) != null) {
                            String resposta = processarRequisicao(jsonRequisicao);
                            out.println(resposta);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String processarRequisicao(String json) {
        try {
            // Converte o JSON para Requisicao genérica
            Requisicao<?> req = JsonUtil.fromJson(json, Requisicao.class);
            String entidade = req.getEntidade().toLowerCase();

            return switch (entidade) {
                case "categoria" -> categoriaControlador.processarRequisicao(req);
                case "produto" -> produtoControlador.processarRequisicao(req);
                default -> JsonUtil.toJson(new Resposta("erro", "Entidade '" + entidade + "' não reconhecida", null));
            };

        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.toJson(new Resposta("erro", "Erro interno", null));
        }
    }
}
