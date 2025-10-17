package br.backend;

import br.backend.database.Database;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Database database = new Database();

        Connection conn = database.getConnection();

        if(conn != null){
            System.out.println("Deu certo");
        }

    }
}