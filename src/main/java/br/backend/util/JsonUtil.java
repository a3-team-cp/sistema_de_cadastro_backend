package br.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe utilitária responsável por conversão entre objetos Java e JSON,
 * utilizando a biblioteca Jackson.
 *
 * <p>Fornece métodos estáticos para serialização (objeto → JSON)
 * e desserialização (JSON → objeto), centralizando a configuração
 * do {@link ObjectMapper} em um único ponto da aplicação.</p>
 *
 * <p>Caso ocorra algum erro durante o processo de conversão,
 * uma {@link RuntimeException} é lançada contendo a causa original.</p>
 */
public class JsonUtil {


    /**
     * Instância única e thread-safe do ObjectMapper utilizada
     * em todas as operações de conversão.
     */
    private static final ObjectMapper mapper = new ObjectMapper();


    /**
     * Converte uma string JSON em um objeto Java do tipo especificado.
     *
     * @param json  o conteúdo JSON a ser convertido
     * @param clazz classe do tipo desejado para a conversão
     * @param <T>   tipo do objeto retornado
     * @return objeto convertido a partir do JSON
     * @throws RuntimeException se houver falha na desserialização
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para objeto", e);
        }
    }

    /**
     * Converte um objeto Java em sua representação JSON.
     *
     * @param obj objeto a ser convertido
     * @return JSON correspondente ao objeto informado
     * @throws RuntimeException se houver falha na serialização
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter objeto para JSON", e);
        }
    }
}
