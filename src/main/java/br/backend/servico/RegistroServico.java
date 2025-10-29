package br.backend.servico;

import br.backend.dao.RegistroDAO;
import br.backend.modelo.Registro;
import java.util.List;

public class RegistroServico {

    private final RegistroDAO registroDAO;

    public RegistroServico(RegistroDAO registroDAO) {
        this.registroDAO = registroDAO;
    }

    public void inserirRegistro(Registro r) {
        registroDAO.inserirRegistro(r);
    }

    public List<Registro> listarRegistros() {
        return registroDAO.listarRegistros();
    }
}
