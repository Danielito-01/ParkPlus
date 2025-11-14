
package DAO;

import Clases.Docente;
import Clases.Estudiante;
import Clases.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuario (carnet, telefono, nombre, apellido, tipoUsuario, carrera, semestre) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.Conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getCarnet());
            ps.setString(2, u.getTelefono());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getApellido());
            ps.setString(5, u.getTipousuario());
            if (u instanceof Clases.Estudiante estudiante) {
            ps.setString(6, estudiante.getCarrera());
            ps.setString(7, estudiante.getSemestre());
            } else {
            ps.setNull(6, java.sql.Types.VARCHAR);
            ps.setNull(7, java.sql.Types.VARCHAR);
            }     
            ps.executeUpdate();

            System.out.println("Usuario insertado correctamente.");
            return true;
            } catch (SQLException e) {
                System.err.println("Error al insertar usuario: " + e.getMessage());
                return false;
            }
    }
    
    public boolean existeCarnet(String carnet) {
    // 1. Sentencia SQL: Cuenta cuántas filas tienen ese carnet
    String sql = "SELECT COUNT(*) FROM usuario WHERE carnet = ?";
    
    try (Connection conn = Conexion.Conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        // Asignar el carnet al placeholder
        ps.setString(1, carnet);
        
        // Ejecutar la consulta y obtener el resultado
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1); // Obtiene el valor de COUNT(*)
                // Si count es mayor que 0, significa que ya existe
                return count > 0; 
            }
        }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de carnet: " + e.getMessage());
            // En caso de error de conexión/SQL, asumimos que no se pudo verificar (o manejamos el error)
            return false; // Podrías devolver true para detener la inserción por seguridad, o false si el error no es de existencia.
        }
        return false; // Por defecto si no se pudo determinar por alguna razón.
    }
    
    public int obtenerId(String carnet) {
    String sql = "SELECT id FROM usuario WHERE carnet = ?";
    try (Connection conn = Conexion.Conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, carnet);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id"); // devuelve el id encontrado
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ID por carnet: " + e.getMessage());
    }
    return -1; // si no se encontró o hubo error
    }
    
     public boolean insertarU(ArrayList<Usuario> usuarios) {
        String sql = "INSERT INTO usuario (carnet, telefono, nombre, apellido, tipoUsuario, carrera, semestre) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Usuario u : usuarios) {
                ps.setString(1, u.getCarnet());
                ps.setString(2, u.getTelefono());
                ps.setString(3, u.getNombre());
                ps.setString(4, u.getApellido());
                ps.setString(5, u.getTipousuario());
                
                if (u instanceof Clases.Estudiante estudiante) {
                ps.setString(6, estudiante.getCarrera());
                ps.setString(7, estudiante.getSemestre());
                
                } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
                ps.setNull(7, java.sql.Types.VARCHAR);
                }     
                ps.addBatch(); // agrega el INSERT a un lote
            }

            ps.executeBatch(); // ejecuta todos los INSERT juntos
            System.out.println("Se insertaron " + usuarios.size() + " usuarios correctamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar usuarios: " + e.getMessage());
            return false;
        }
    }
     
    public String obtenerNA(String carnet){
        String sql = "SELECT nombre, apellido FROM usuario WHERE carnet = ?";
        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, carnet);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String na = rs.getString("nombre") +" "+ rs.getString("apellido");
          
                    return na;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre y apellido " + e.getMessage());
        }
        return "";
    }
    
    public ArrayList<String> obtenerPlacasPorCarnet(String carnet) {
        ArrayList<String> placas = new ArrayList<>();

        String sql = "SELECT v.placa " +
                     "FROM usuario u " +
                     "JOIN usuario_vehiculo uv ON u.id = uv.idUsuario " +
                     "JOIN vehiculo v ON v.id = uv.idVehiculo " +
                     "WHERE u.carnet = ?";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, carnet);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    placas.add(rs.getString("placa"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener placas: " + e.getMessage());
        }

        return placas;
    }
    
    public Usuario obtenerUsuarioPorCarnet(String carnet) {
        String sql = "SELECT carnet, telefono, nombre, apellido, tipoUsuario, carrera, semestre FROM usuario WHERE carnet = ?";
        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, carnet);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipoUsuario");

                    if ("Estudiante".equalsIgnoreCase(tipo)) {
                        Estudiante est = new Estudiante();
                        est.setCarnet(rs.getString("carnet"));
                        est.setTelefono(rs.getString("telefono"));
                        est.setNombre(rs.getString("nombre"));
                        est.setApellido(rs.getString("apellido"));
                        est.setTipousuario(tipo);
                        est.setCarrera(rs.getString("carrera"));
                        est.setSemestre(rs.getString("semestre"));
                        return est;
                    } else if ("Docente".equalsIgnoreCase(tipo)) {
                        Docente doc = new Docente();
                        doc.setCarnet(rs.getString("carnet"));
                        doc.setTelefono(rs.getString("telefono"));
                        doc.setNombre(rs.getString("nombre"));
                        doc.setApellido(rs.getString("apellido"));
                        doc.setTipousuario(tipo);
                        // aquí podrías agregar campo específico de docente si lo tienes
                        return doc;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }
}
