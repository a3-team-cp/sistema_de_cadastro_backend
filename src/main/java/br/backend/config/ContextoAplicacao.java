package br.backend.config;

import br.backend.controlador.impl.CategoriaControladorImpl;
import br.backend.controlador.impl.ProdutoControladorImpl;
import br.backend.controlador.impl.RegistroControladorImpl;
import br.backend.controlador.impl.RelatorioControladorImpl;
import br.backend.dao.RegistroDAO;
import br.backend.dao.RelatorioDAO;
import br.backend.dao.impl.CategoriaDAOImpl;
import br.backend.dao.impl.ProdutoDAOImpl;
import br.backend.dao.impl.RegistroDAOImpl;
import br.backend.dao.impl.RelatorioDAOImpl;
import br.backend.database.Database;
import br.backend.servico.CategoriaServico;
import br.backend.servico.ProdutoServico;
import br.backend.servico.RegistroServico;
import br.backend.servico.RelatorioServico;

/**
 * Classe responsável por montar e disponibilizar todas as dependências
 * necessárias para o funcionamento da aplicação.
 *
 * <p>Funciona como um container de injeção de dependência simples,
 * instanciando e conectando DAOs, serviços e controladores de forma
 * centralizada. Isso evita a criação dispersa de objetos e garante
 * que cada componente receba suas dependências adequadamente.</p>
 *
 * <p>Este padrão é conhecido como <strong>Composition Root</strong>,
 * ponto onde toda a árvore de objetos da aplicação é construída.</p>
 */
public class ContextoAplicacao {

    private final Database database;
    private final CategoriaControladorImpl categoriaControlador;
    private final ProdutoControladorImpl produtoControlador;
    private final RegistroControladorImpl registroControlador;
    private final RelatorioControladorImpl relatorioControlador;

    /**
     * Construtor padrão que realiza toda a composição dos objetos
     * necessários, da camada de banco até os controladores.
     *
     * <p>Sequência de montagem:</p>
     * <ol>
     *     <li>Instancia o banco de dados ({@link Database})</li>
     *     <li>Cria DAOs</li>
     *     <li>Cria Services que dependem desses DAOs</li>
     *     <li>Cria os Controladores que utilizam os Services</li>
     * </ol>
     *
     * <p>Esse fluxo garante que todas as dependências sejam satisfeitas
     * antes do servidor ser iniciado.</p>
     */
    public ContextoAplicacao() {
        this.database = new Database();

        // Categoria
        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl(database);
        CategoriaServico categoriaServico = new CategoriaServico(categoriaDAO);
        this.categoriaControlador = new CategoriaControladorImpl(categoriaServico);

        // Produto + Registro
        RegistroDAO registroDAO = new RegistroDAOImpl(database);
        ProdutoDAOImpl produtoDAO = new ProdutoDAOImpl(database);
        ProdutoServico produtoServico = new ProdutoServico(produtoDAO, registroDAO);
        this.produtoControlador = new ProdutoControladorImpl(produtoServico);

        // Registro
        RegistroServico registroServico = new RegistroServico(registroDAO);
        this.registroControlador = new RegistroControladorImpl(registroServico);

        // Relatório
        RelatorioDAO relatorioDAO = new RelatorioDAOImpl(database);
        RelatorioServico relatorioServico = new RelatorioServico(relatorioDAO);
        this.relatorioControlador = new RelatorioControladorImpl(relatorioServico);
    }

    /**
     * @return controlador responsável pelas operações de categorias
     */
    public CategoriaControladorImpl getCategoriaControlador() {
        return categoriaControlador;
    }

    /**
     * @return controlador responsável pelas operações de produtos
     */
    public ProdutoControladorImpl getProdutoControlador() {
        return produtoControlador;
    }

    /**
     * @return controlador responsável pelas operações de registros (movimentação)
     */
    public RegistroControladorImpl getRegistroControlador() {
        return registroControlador;
    }

    /**
     * @return controlador responsável pelas operações de relatório
     */
    public RelatorioControladorImpl getRelatorioControlador() {
        return relatorioControlador;
    }

    /**
     * Retorna a instância centralizada do banco de dados.
     *
     * <p>Este método é útil para permitir que o servidor (ou classe Main)
     * feche explicitamente a conexão ao encerrar a aplicação.</p>
     *
     * @return instância de {@link Database}
     */
    public Database getDatabase() {
        // Útil para fechar a conexão de forma centralizada no Main
        return database;
    }
}
