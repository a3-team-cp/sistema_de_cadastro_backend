package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.modelo.Categoria;
import br.backend.modelo.Produto;
import br.backend.modelo.Requisicao;
import br.backend.modelo.Resposta;
import br.backend.servico.CategoriaServico;
import br.backend.servico.ProdutoServico;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProdutoControladorImpl implements Controlador {

    private final ProdutoServico produtoServico;
    private final ObjectMapper objectMapper;

    public ProdutoControladorImpl(ProdutoServico produtoServico) {
        this.produtoServico = produtoServico;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            switch (acao) {
                case "criar": {
                    Produto obj = objectMapper.convertValue(requisicao.getDados(), Produto.class);
                    Categoria categoria = new Categoria();
                    categoria.setId(obj.getCategoriaId());
                    Produto objCriado = produtoServico.inserirProduto(obj.getNome(), obj.getPreco(), obj.getUnidade(), categoria,
                            obj.getQuantidade(), obj.getQuantidadeMinima(), obj.getQuantidadeMaxima());

                    return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Produto criado com sucesso", objCriado));
                }

                case "encontrar": {
                    Integer id = objectMapper.convertValue(requisicao.getDados(), Produto.class).getId();
                    Produto encontrado = produtoServico.buscarPorId(id);
                    if (encontrado != null) {
                        return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Produto encontrado", encontrado));
                    } else {
                        return objectMapper.writeValueAsString(new Resposta<>("erro", "Produto não encontrado", null));
                    }
                }

                case "atualizar": {
                    Produto objAtualizacao = objectMapper.convertValue(requisicao.getDados(), Produto.class);
                    Produto objAtualizado = produtoServico.atualizarProduto(objAtualizacao.getId(), objAtualizacao);
                    return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Produto atualizado com sucesso", objAtualizado));
                }

                case "deletar": {
                    Integer id = objectMapper.convertValue(requisicao.getDados(), Produto.class).getId();
                    boolean excluido = produtoServico.deletarProduto(id);
                    if (excluido) {
                        return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Produto deletado", null));
                    } else {
                        return objectMapper.writeValueAsString(new Resposta<>("erro", "Produto não encontrado", null));
                    }
                }

                case "listar": {
                    return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Lista de categorias", produtoServico.listarProdutos()));
                }
                default:
                    return objectMapper.writeValueAsString(
                            new Resposta("erro", "Ação desconhecida: " + acao, null)
                    );

            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                return objectMapper.writeValueAsString(new Resposta("erro", "Erro ao processar requisição: " + e.getMessage(), null));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
