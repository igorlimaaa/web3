package br.com.fuctura.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection getConexao() throws SQLException {
        String url = "jdbc:derby://localhost:1527/webvendas";
        String usuario = "postgres";
        String senha = "postgres";
        return DriverManager.getConnection(url, usuario, senha);
    }
}
