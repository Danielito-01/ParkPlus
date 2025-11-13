package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
        private static final String url = "jdbc:sqlserver://Daniel:1433;databaseName=ParkPlus;encrypt=true;trustServerCertificate=True";
        private static final String usuario = "danipark";
        private static final String contra = "Dani01";
        
       public static Connection Conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, usuario, contra);
        } catch (SQLException e) {
            System.out.println("Error en la conexion: " + e.getMessage());
        }
        return conn;
    }

    static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
