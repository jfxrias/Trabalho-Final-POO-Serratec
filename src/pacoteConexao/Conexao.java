package pacoteConexao;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/poo"; 
    private static final String USUARIO = "postgres";
    private static final String SENHA = "PirataDosBanc0$4";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String sql = "SELECT nome FROM clientes";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("--- LISTA DE CLIENTES NO BANCO ---");
            while (rs.next()) {
                System.out.println("Cliente: " + rs.getString("nome"));
            }

        } catch (SQLException e) {
            System.err.println("Erro SQL: " + e.getMessage());
        }
    }
}
