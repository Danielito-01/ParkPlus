package com.mycompany.parkplus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
        private static final String url = "jdbc:sqlserver://Daniel:1433;databaseName=ParkPlus;encrypt=true;trustServerCertificate=True";
        private static final String usuario = "danipark";
        private static final String contra = "Dani01";
        
       public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, usuario, contra);
            System.out.println("✅ Conexión establecida con la base de datos");
        } catch (SQLException e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
        }
        return conn;
    }
}
