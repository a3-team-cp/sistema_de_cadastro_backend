package br.backend.controlador.impl;

import br.backend.controlador.Controlador;
import br.backend.modelo.Categoria;
import br.backend.dto.Requisicao;
import br.backend.dto.Resposta;
import br.backend.servico.CategoriaServico;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoriaControladorImpl implements Controlador {

    private final CategoriaServico categoriaServico;
    private final ObjectMapper objectMapper;

    public CategoriaControladorImpl(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String processarRequisicao(Requisicao<?> requisicao) {
        try {
            String acao = requisicao.getAcao().toLowerCase();

            switch (acao) {
                case "criar": {
                    Categoria cat = objectMapper.convertValue(requisicao.getDados(), Categoria.class);
                    Categoria catCriada = categoriaServico.inserirCategoria(cat.getNome(), cat.getTamanho(), cat.getEmbalagem());
                    return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Categoria criada", catCriada));
                }

                case "encontrar": {
                    Integer id = objectMapper.convertValue(requisicao.getDados(), Categoria.class).getId();
                    Categoria encontrada = categoriaServico.buscarPorId(id);
                    if (encontrada != null) {
                        return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Categoria encontrada", encontrada));
                    } else {
                        return objectMapper.writeValueAsString(new Resposta<>("erro", "Categoria não encontrada", null));
                    }
                }

                case "atualizar": {
                    Categoria catAtualizacao = objectMapper.convertValue(requisicao.getDados(), Categoria.class);
                    Categoria catAtualizada = categoriaServico.atualizarCategoria(catAtualizacao.getId(), catAtualizacao);
                    return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Categoria atualizada", catAtualizada));
                }

                case "deletar": {
                    Integer id = objectMapper.convertValue(requisicao.getDados(), Categoria.class).getId();
                    boolean excluido = categoriaServico.deletarCategoria(id);
                    if (excluido) {
                        return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Categoria deletada", null));
                    } else {
                        return objectMapper.writeValueAsString(new Resposta<>("erro", "Categoria não encontrada", null));
                    }
                }

                case "listar": {
                    return objectMapper.writeValueAsString(new Resposta<>("sucesso", "Lista de categorias", categoriaServico.listarCategorias()));
                }

                default:
                    return objectMapper.writeValueAsString(new Resposta<>("erro", "Ação desconhecida: " + acao, null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                return objectMapper.writeValueAsString(new Resposta<>("erro", "Erro ao processar requisição: " + e.getMessage(), null));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
