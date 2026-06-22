package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
            + "databaseName=ParkPlus;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USUARIO = "danipark";
    private static final String CONTRA = "ParkPlus?0101_";

    private Conexion() {
    }

    public static Connection Conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRA);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo conectar con ParkPlus: " + e.getMessage(),
                    e
            );
        }
    }

    public static Connection getConnection() {
        return Conectar();
    }
}