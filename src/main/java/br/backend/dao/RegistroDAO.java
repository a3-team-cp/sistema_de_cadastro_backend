package br.backend.dao;

import br.backend.modelo.Registro;
import java.util.List;

public interface RegistroDAO {

    void inserirRegistro(Registro registro);

    List<Registro> listarRegistros();
}