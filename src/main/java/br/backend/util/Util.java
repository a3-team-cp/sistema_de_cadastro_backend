package br.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Classe utilitária para conversões genéricas de objetos utilizando
 * o {@link ObjectMapper} do Jackson.
 *
 * <p>Fornece métodos estáticos que permitem converter um objeto de um tipo
 * para outro sem necessidade de serialização explícita, através do método
 * {@code convertValue()}.</p>
 *
 * <p>Essa abordagem é especialmente útil quando os dados chegam em forma
 * de {@code Map}, {@code LinkedHashMap} ou estruturas genéricas provenientes
 * de desserialização parcial.</p>
 *
 * <p>Em caso de falha na conversão, uma {@link RuntimeException} é lançada
 * contendo a causa original.</p>
 */
public class Util {


    /**
     * Instância compartilhada do ObjectMapper usada nas conversões.
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converte um objeto genérico para uma classe específica utilizando
     * a funcionalidade de mapeamento interno do Jackson.
     *
     * <p>Suporta conversões entre mapas, listas e objetos simples, desde
     * que os campos sejam compatíveis com o tipo alvo.</p>
     *
     * @param obj   objeto de origem a ser convertido
     * @param clazz tipo de destino desejado
     * @param <T>   tipo resultante da conversão
     * @return objeto convertido para o tipo especificado
     * @throws RuntimeException caso ocorra erro durante a conversão
     */
    public static <T> T fromObject(Object obj, Class<T> clazz) {
        try {
            return mapper.convertValue(obj, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter objeto para classe " + clazz.getSimpleName(), e);
        }
    }

}
