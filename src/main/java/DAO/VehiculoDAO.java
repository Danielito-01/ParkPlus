package DAO;

import Clases.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehiculoDAO {
    
    public boolean insertarV(ArrayList<Vehiculo> vehiculos) {
        String sql = "INSERT INTO vehiculo (placa, color, tipo) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Vehiculo v : vehiculos) {
                ps.setString(1, v.getPlaca());
                ps.setString(2, v.getColor());
                ps.setString(3, v.getTipovehiculo());
                ps.addBatch(); // agrega el INSERT a un lote
            }

            ps.executeBatch(); // ejecuta todos los INSERT juntos
            System.out.println("Se insertaron " + vehiculos.size() + " vehiculos correctamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar vehiculos: " + e.getMessage());
            return false;
        }
    }
    
    public boolean existeVehiculo(String placa) {
    // 1. Sentencia SQL: Cuenta cuántas filas tienen ese carnet
    String sql = "SELECT COUNT(*) FROM vehiculo WHERE placa = ?";
    
    try (Connection conn = Conexion.Conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        // Asignar la placa al placeholder
        ps.setString(1, placa);
        
        // Ejecutar la consulta y obtener el resultado
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1); // Obtiene el valor de COUNT(*)
                // Si count es mayor que 0, significa que ya existe
                return count > 0; 
            }
        }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de placa: " + e.getMessage());
            // En caso de error de conexión/SQL, asumimos que no se pudo verificar (o manejamos el error)
            return false; // Podrías devolver true para detener la inserción por seguridad, o false si el error no es de existencia.
        }
        return false; // Por defecto si no se pudo determinar por alguna razón.
    }
    
    public ArrayList<Integer> obtenerIdsv(ArrayList<Vehiculo> vehiculos) {
    ArrayList<Integer> idsVehiculos = new ArrayList<>();

    String sql = "SELECT id FROM vehiculo WHERE placa = ?";

    try (Connection conn = Conexion.Conectar()) {
        for (Vehiculo v : vehiculos) {
            int id = -1; // valor por defecto si no existe

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, v.getPlaca());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener ID del vehículo con placa " + v.getPlaca() + ": " + e.getMessage());
            }

            // agregar el id (exista o no)
            idsVehiculos.add(id);
        }
    } catch (SQLException e) {
        System.err.println("Error de conexión al obtener IDs de vehículos: " + e.getMessage());
    }

    return idsVehiculos;
    }
}
