package br.backend.servidor;


import br.backend.controlador.impl.CategoriaControladorImpl;
import br.backend.controlador.impl.ProdutoControladorImpl;
import br.backend.database.Database;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.util.JsonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    private final int porta;

    private Database database;

    private CategoriaControladorImpl categoriaControlador;
    private ProdutoControladorImpl produtoControlador;

    public Servidor(int porta, CategoriaControladorImpl categoriaControlador, ProdutoControladorImpl produtoControlador) {
        this.porta = porta;

        this.categoriaControlador = categoriaControlador;
        this.produtoControlador = produtoControlador;
    }

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


    private void processarCliente(Socket cliente) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

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

    private void processarClientes(Socket client){

    }
}
