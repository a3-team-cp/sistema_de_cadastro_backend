package br.backend.servico;

import br.backend.dao.RegistroDAO;
import br.backend.modelo.Registro;

public class RegistroService {

    private final RegistroDAO registroDAO;

    public RegistroService(RegistroDAO registroDAO) {
        this.registroDAO = registroDAO;
    }

    public void inserirRegistro(Registro r){
        registroDAO.inserirRegistro(r);
    }
}
