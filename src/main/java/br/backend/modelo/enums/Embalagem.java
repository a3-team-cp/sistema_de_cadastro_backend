package br.backend.modelo.enums;

/**
 * Enum que representa os tipos de embalagem disponíveis para os produtos.
 *
 * <p>Este valor é utilizado para classificar e padronizar a forma física
 * como um produto é armazenado ou comercializado.</p>
 *
 * <p>Os valores são persistidos no banco como texto
 * (via {@code enum.name()}) e utilizados na camada de serviço e DAO
 * para compor ou recuperar os dados dos produtos.</p>
 */
public enum Embalagem {

    /** Produto armazenado em lata (ex.: milho, ervilha, refrigerante). */
    LATA,

    /** Produto armazenado em vidro (ex.: conservas, geleias). */
    VIDRO,

    /** Produto armazenado em embalagem plástica (ex.: arroz, açúcar, massas). */
    PLASTICO
}
