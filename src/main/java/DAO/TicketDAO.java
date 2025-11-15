package DAO;

import Clases.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TicketDAO {
    
    public int insertarTicketYMarcarSpot(Ticket t, String codigoSpot) {
        String sqlTicket = "INSERT INTO ticket (placaVehiculo, carnetUsuario, tipoUsuario, tipoVehiculo, "
                + "codigoSpot, codigoArea, fechaHoraIngreso, tarifaAplicada, monto, metodoPago, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlSpot = "UPDATE spot SET estado = 1 WHERE codigo = ?";

        try (Connection conn = Conexion.Conectar()) {

            conn.setAutoCommit(false);

            int generatedId = -1;

            // INSERT TICKET
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

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

            // MARCAR SPOT
            try (PreparedStatement ps2 = conn.prepareStatement(sqlSpot)) {
                ps2.setString(1, codigoSpot);
                ps2.executeUpdate();
            }

            conn.commit();
            return generatedId;

        } catch (SQLException e) {
            return -1;
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
    
    public boolean actualizarTicketYLiberarSpot(Ticket t) {
        String sqlUpdate = "UPDATE ticket SET fechaHoraSalida=?, monto=?, metodoPago=?, estado=? WHERE id=?";
        String sqlSpot = "UPDATE spot SET estado = 0 WHERE codigo=?";

        try (Connection con = Conexion.Conectar()) {

            // 1. actualizar ticket
            PreparedStatement ps1 = con.prepareStatement(sqlUpdate);
            ps1.setTimestamp(1, Timestamp.valueOf(t.getFechaHoraSalida()));
            ps1.setDouble(2, t.getMonto());
            ps1.setString(3, t.getMetodoPago());
            ps1.setString(4, "COMPLETADO");
            ps1.setInt(5, t.getId());
            ps1.executeUpdate();

            // 2. liberar spot
            PreparedStatement ps2 = con.prepareStatement(sqlSpot);
            ps2.setString(1, t.getCodigoSpot());
            ps2.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }   
}
