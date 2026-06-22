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

    private static final String USUARIO =
            obtenerVariableEntorno("PARKPLUS_DB_USER");

    private static final String CONTRA =
            obtenerVariableEntorno("PARKPLUS_DB_PASSWORD");

    private Conexion() {
    }

    private static String obtenerVariableEntorno(String nombre) {
        String valor = System.getenv(nombre);

        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "Falta configurar la variable de entorno: " + nombre
            );
        }

        return valor;
    }

    public static Connection Conectar() {
        try {
            return DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRA
            );
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo conectar con ParkPlus: "
                    + e.getMessage(),
                    e
            );
        }
    }

    public static Connection getConnection() {
        return Conectar();
    }
}