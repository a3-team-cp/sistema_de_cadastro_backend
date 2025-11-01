# 🛠️ Sistema de Cadastro Backend

## 👥 Integrantes

| Nome Completo               | Usuário GitHub           | RA          |
|-----------------------------|--------------------------|--------------|
| Diego Wobeto Maglia Muller  | diegowmmuller            | 10724265729  |
| Lorenzo Bruscato            | Lorenzo, LorenzoBruscato  | 10724262961  |
| Murilo Vieira Moura         | Murilo, ivaxs            | 10724269339  |
| Henrique Bernardes Rosa     | INTEL, HenriqueBrosa     | 10724263295  |

## 🧱 Estrutura do Banco de Dados

### **Tabela: categoria**

| Campo     | Tipo                                        | Descrição                      |
|------------|---------------------------------------------|--------------------------------|
| id         | INT AUTO_INCREMENT PRIMARY KEY              | Identificador único            |
| nome       | VARCHAR(100) NOT NULL                       | Nome da categoria              |
| tamanho    | ENUM('PEQUENO', 'MEDIO', 'GRANDE') NOT NULL | Tamanho da categoria           |
| embalagem  | ENUM('PLASTICO', 'LATA', 'VIDRO') NOT NULL  | Tipo de embalagem da categoria |
| ativo      | BOOLEAN NOT NULL DEFAULT TRUE               | Indica se a categoria está ativa |

### **Tabela: produto**

| Campo             | Tipo                          | Descrição                                  |
|-------------------|--------------------------------|--------------------------------------------|
| id                | INT AUTO_INCREMENT PRIMARY KEY | Identificador único                        |
| nome              | VARCHAR(100) NOT NULL          | Nome do produto                            |
| preco_unitario    | DECIMAL(10,2) NOT NULL         | Preço unitário do produto                  |
| unidade           | VARCHAR(100) NOT NULL          | Unidade de medida (kg, L, ml...)           |
| quantidade        | INT NOT NULL                   | Quantidade atual em estoque                |
| quantidade_minima | INT NOT NULL                   | Quantidade mínima permitida                |
| quantidade_maxima | INT NOT NULL                   | Quantidade máxima permitida                |
| categoria_id      | INT                            | Categoria do produto                       |
| ativo             | BOOLEAN NOT NULL DEFAULT TRUE  | Indica se o produto está ativo (soft delete) |
| **FK**            | categoria_id → categoria(id)   | Relação com a tabela `categoria`           |

### **Tabela: registro**

| Campo        | Tipo                                                                                            | Descrição                                 |
|---------------|--------------------------------------------------------------------------------------------------|--------------------------------------------|
| id            | INT AUTO_INCREMENT PRIMARY KEY                                                                  | Identificador único                       |
| data          | TIMESTAMP NOT NULL                                                                              | Data da movimentação                      |
| produto_id    | INT NOT NULL                                                                                    | Produto relacionado à movimentação        |
| quantidade    | INT NOT NULL                                                                                    | Quantidade movimentada                    |
| movimentacao  | ENUM('NENHUM', 'ENTRADA', 'SAIDA') NOT NULL                                                     | Tipo da movimentação                      |
| status        | ENUM('ACIMA', 'ABAIXO', 'DENTRO', 'ADICIONADO', 'NOMEALTERADO', 'DELETADO', 'NENHUM') NOT NULL | Status atual da movimentação              |
| **FK**        | produto_id → produto(id)                                                                        | Relação com a tabela `produto`            |

## ⚙️ Tecnologias Utilizadas

- Java 21  
- JDBC 9.2.0 (Java Database Connectivity)  
- MySQL 8.0.42  
- Maven 3.9.9  
- IDE NetBeans 25  

## ⚙️ Configurando o Banco de Dados MySQL

- Para rodar o programa é necessário fazer o download do **MySQL Workbench 8.0**  
- Acesse o arquivo [`banco.sql`](banco.sql) para criar o banco de dados  
- **Usuário:** `root`  
- **Senha:** `1234567`

 ## 🔗 Link do frontend

- [Sistema de cadastro fronted](https://github.com/a3-team-cp/sistema_de_cadastro_frontend)
