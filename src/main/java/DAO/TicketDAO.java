package DAO;

import Clases.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TicketDAO {
    
    public boolean insertarTicketYMarcarSpot(Ticket t, String codigoSpot) {

        String sqlTicket = "INSERT INTO ticket (placaVehiculo, carnetUsuario, tipoUsuario, tipoVehiculo, "
                + "codigoSpot, codigoArea, fechaHoraIngreso, tarifaAplicada, monto, metodoPago, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlSpot = "UPDATE spot SET estado = 1 WHERE codigo = ?"; // 1 = ocupado

        try (Connection conn = Conexion.Conectar()) {

            conn.setAutoCommit(false); // ← INICIO TRANSACCIÓN

            // 1) INSERTAR TICKET Y OBTENER ID GENERADO
            try (PreparedStatement ps = conn.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, t.getPlacaVehiculo());
                ps.setString(2, t.getCarnetUsuario());
                ps.setString(3, t.getTipoUsuario());
                ps.setString(4, t.getTipoVehiculo());
                ps.setString(5, t.getCodigoSpot());
                ps.setString(6, t.getCodigoArea());
                ps.setTimestamp(7, Timestamp.valueOf(t.getFechaHoraIngreso()));
                ps.setString(8, t.getTarifaAplicada());
                ps.setDouble(9, t.getMonto());
                ps.setString(10, t.getMetodoPago());
                ps.setString(11, t.getEstado());

                ps.executeUpdate();

                // Obtener ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        t.setId(rs.getInt(1));  // ✔ Asignar al ticket
                    }
                }
            }

            // 2) MARCAR SPOT COMO OCUPADO
            try (PreparedStatement ps2 = conn.prepareStatement(sqlSpot)) {
                ps2.setString(1, codigoSpot);
                ps2.executeUpdate();
            }

            conn.commit();  // ← TODO OK
            return true;

        } catch (SQLException e) {
            System.err.println("ERROR insertarTicketYMarcarSpot(): " + e.getMessage());
            return false;
        }
    }
    
    public Ticket getTicketPorId(int id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setPlacaVehiculo(rs.getString("placaVehiculo"));
                t.setCarnetUsuario(rs.getString("carnetUsuario"));
                t.setTipoUsuario(rs.getString("tipoUsuario"));
                t.setTipoVehiculo(rs.getString("tipoVehiculo"));
                t.setCodigoSpot(rs.getString("codigoSpot"));
                t.setCodigoArea(rs.getString("codigoArea"));
                t.setFechaHoraIngreso(rs.getTimestamp("fechaHoraIngreso").toLocalDateTime());
                t.setFechaHoraSalida(rs.getTimestamp("fechaHoraSalida") != null ? 
                rs.getTimestamp("fechaHoraSalida").toLocalDateTime() : null);
                t.setTarifaAplicada(rs.getString("tarifaAplicada"));
                t.setMonto(rs.getDouble("monto"));
                t.setMetodoPago(rs.getString("metodoPago"));
                t.setEstado(rs.getString("estado"));
                return t;
            }

        } catch (SQLException e) {
            System.out.println("ERROR getTicketPorId(): " + e.getMessage());
        }

        return null;
    }
    
    public boolean actualizarTicketSalida(Ticket t) {

        String sqlTicket = "UPDATE ticket SET fechaHoraSalida=?, monto=?, metodoPago=?, estado=? WHERE id=?";

        String sqlLiberarSpot = "UPDATE spot SET estado = 0 WHERE codigo = ?";  

        try (Connection conn = Conexion.Conectar()) {

            conn.setAutoCommit(false); // INICIO TRANSACCIÓN

            // 1. actualizar ticket
            try (PreparedStatement ps = conn.prepareStatement(sqlTicket)) {
                ps.setTimestamp(1, Timestamp.valueOf(t.getFechaHoraSalida()));
                ps.setDouble(2, t.getMonto());
                ps.setString(3, t.getMetodoPago());
                ps.setString(4, t.getEstado());
                ps.setInt(5, t.getId());
                ps.executeUpdate();
            }

            // 2. liberar el spot
            try (PreparedStatement ps2 = conn.prepareStatement(sqlLiberarSpot)) {
                ps2.setString(1, t.getCodigoSpot()); // ← ya deberías tenerlo en el Ticket
                ps2.executeUpdate();
            }

            conn.commit(); // TODO OK

            return true;

        } catch (SQLException e) {
            System.err.println("ERROR completarSalida(): " + e.getMessage());
            return false;
        }
    }    
}
