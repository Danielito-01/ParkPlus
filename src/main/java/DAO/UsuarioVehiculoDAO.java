package DAO;

import Clases.UsuarioVehiculo;
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
    String sql = "INSERT INTO usuario_vehiculo (idUsuario, idVehiculo, rol) VALUES (?, ?, ?)";

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
    
    public boolean asociarUV(ArrayList<UsuarioVehiculo> relaciones) {
    String sqlSelectUsuario = "SELECT id FROM usuario WHERE carnet = ?";
    String sqlSelectVehiculo = "SELECT id FROM vehiculo WHERE placa = ?";
    String sqlInsert = "INSERT INTO usuario_vehiculo (idUsuario, idVehiculo, rol) VALUES (?, ?, ?)";

    try (Connection conn = Conexion.Conectar();
         PreparedStatement psSelectUsuario = conn.prepareStatement(sqlSelectUsuario);
         PreparedStatement psSelectVehiculo = conn.prepareStatement(sqlSelectVehiculo);
         PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {

        for (UsuarioVehiculo rel : relaciones) {
            String carnet = rel.getCarnet();
            String placa = rel.getPlaca();
            String rol = rel.getRol();

            // 🔹 Buscar idUsuario
            int idUsuario = -1;
            psSelectUsuario.setString(1, carnet);
            try (ResultSet rsU = psSelectUsuario.executeQuery()) {
                if (rsU.next()) {
                    idUsuario = rsU.getInt("id");
                } else {
                    System.out.println("Usuario no encontrado: " + carnet);
                    continue; // si no existe, saltamos
                }
            }

            // 🔹 Buscar idVehiculo
            int idVehiculo = -1;
            psSelectVehiculo.setString(1, placa);
            try (ResultSet rsV = psSelectVehiculo.executeQuery()) {
                if (rsV.next()) {
                    idVehiculo = rsV.getInt("id");
                } else {
                    System.out.println("Vehículo no encontrado: " + placa);
                    continue; // si no existe, saltamos
                }
            }

            // 🔹 Insertar la relación
            psInsert.setInt(1, idUsuario);
            psInsert.setInt(2, idVehiculo);
            psInsert.setString(3, rol);
            psInsert.addBatch();
        }

        // Ejecutar todos los INSERT juntos
        psInsert.executeBatch();
        System.out.println("Se asociaron correctamente " + relaciones.size() + " usuario(s) y vehiculo(s).");
        return true;

        } catch (SQLException e) {
            System.err.println("Error al asociar usuarios y vehiculos: " + e.getMessage());
            return false;
        }
    }
}
