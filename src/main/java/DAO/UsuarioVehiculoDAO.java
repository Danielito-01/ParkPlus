package DAO;

import Clases.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioVehiculoDAO {
    
    public boolean tienePropietario(String placa) {
    String sql = """
        SELECT COUNT(*) 
        FROM usuario_vehiculo uv
        JOIN vehiculo v ON uv.idVehiculo = v.id
        WHERE v.placa = ? AND uv.rol = 'Propietario'
    """;

    try (Connection conn = Conexion.Conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, placa);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Si hay al menos 1, ya tiene propietario
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al verificar propietario: " + e.getMessage());
        // Podrías devolver true para prevenir duplicados si hay error de conexión
        return true;
    }

    return false;
    }
    
    public boolean asociarUsuarioVehiculos(int idUsuario, ArrayList<Vehiculo> vehiculos, ArrayList<Integer> idsVehiculos) {
    String sql = "INSERT INTO usuario_vehiculo (id_usuario, id_vehiculo, rol) VALUES (?, ?, ?)";

    try (Connection conn = Conexion.Conectar()) {
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
            int idVehiculo = idsVehiculos.get(i);
            String rol = v.getRol(); // 👈 lo tomás directamente del objeto

            // Si el idVehiculo es válido
            if (idVehiculo > 0) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idUsuario);
                    ps.setInt(2, idVehiculo);
                    ps.setString(3, rol);
                    ps.executeUpdate();
                }
            }
        }
        System.out.println("Asociaciones creadas correctamente.");
        return true;

    } catch (SQLException e) {
        System.err.println("Error al asociar usuario con vehículos: " + e.getMessage());
        return false;
    }
    }
    
    public boolean existeRol(String carnet, String placa) {
    String sql = """
        SELECT COUNT(*) AS total
        FROM usuario_vehiculo uv
        INNER JOIN usuario u ON u.id = uv.idUsuario
        INNER JOIN vehiculo v ON v.id = uv.idVehiculo
        WHERE u.carnet = ? AND v.placa = ?
    """;

    try (Connection conn = Conexion.Conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, carnet);
        ps.setString(2, placa);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0; // Si hay registros, ya existe la relación
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al verificar relación usuario–vehículo: " + e.getMessage());
    }

    return false;
    }  
}
