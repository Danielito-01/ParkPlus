package DAO;

import Clases.Ticket;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReporteDAO {

    public List<Ticket> obtenerTicketsCerradosPorFecha(LocalDate fecha) {
        List<Ticket> lista = new ArrayList<>();

        String sql = "SELECT * FROM ticket "
                   + "WHERE CAST(fechaHoraSalida AS DATE) = ? "
                   + "AND estado = 'COMPLETADO'";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setPlacaVehiculo(rs.getString("placaVehiculo"));
                t.setCarnetUsuario(rs.getString("carnetUsuario"));
                t.setTipoUsuario(rs.getString("tipoUsuario"));
                t.setTipoVehiculo(rs.getString("tipoVehiculo"));
                t.setCodigoSpot(rs.getString("codigoSpot"));
                t.setCodigoArea(rs.getString("codigoArea"));
                t.setFechaHoraIngreso(rs.getTimestamp("fechaHoraIngreso").toLocalDateTime());
                t.setFechaHoraSalida(rs.getTimestamp("fechaHoraSalida").toLocalDateTime());
                t.setTarifaAplicada(rs.getString("tarifaAplicada"));
                t.setMonto(rs.getDouble("monto"));
                t.setMetodoPago(rs.getString("metodoPago"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("ERROR obtenerTicketsCerradosPorFecha(): " + e.getMessage());
        }
        return lista;
    }
    
    public List<Ticket> obtenerTicketsCerradosPorRango(LocalDate desde, LocalDate hasta) {
        List<Ticket> lista = new ArrayList<>();

        String sql = """
            SELECT * 
            FROM ticket
            WHERE fechaHoraSalida >= ? 
              AND fechaHoraSalida < DATEADD(day, 1, ?)
              AND estado = 'COMPLETADO'
            """;

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(desde));
            ps.setDate(2, java.sql.Date.valueOf(hasta));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket t = new Ticket();

                t.setId(rs.getInt("id"));
                t.setPlacaVehiculo(rs.getString("placaVehiculo"));
                t.setCarnetUsuario(rs.getString("carnetUsuario"));
                t.setTipoUsuario(rs.getString("tipoUsuario"));
                t.setTipoVehiculo(rs.getString("tipoVehiculo"));
                t.setCodigoSpot(rs.getString("codigoSpot"));
                t.setCodigoArea(rs.getString("codigoArea"));
                t.setFechaHoraIngreso(rs.getTimestamp("fechaHoraIngreso").toLocalDateTime());
                t.setFechaHoraSalida(rs.getTimestamp("fechaHoraSalida").toLocalDateTime());
                t.setTarifaAplicada(rs.getString("tarifaAplicada"));
                t.setMonto(rs.getDouble("monto"));
                t.setMetodoPago(rs.getString("metodoPago"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("ERROR obtenerTicketsCerradosPorRango(): " + e.getMessage());
        }

        return lista;
    }
    
    public void exportarTablaExcel(JTable tabla) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exportar a Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File archivo = chooser.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".xlsx")) {
                archivo = new File(archivo.getAbsolutePath() + ".xlsx");
            }

            try (Workbook wb = new XSSFWorkbook()) {

                Sheet hoja = wb.createSheet("Datos");

                TableModel model = tabla.getModel();

                // ---------------------------
                // 1. ENCABEZADOS
                // ---------------------------
                Row header = hoja.createRow(0);

                for (int col = 0; col < model.getColumnCount(); col++) {
                    header.createCell(col).setCellValue(model.getColumnName(col));
                }

                // ---------------------------
                // 2. FILAS
                // ---------------------------
                for (int row = 0; row < model.getRowCount(); row++) {

                    Row filaExcel = hoja.createRow(row + 1);

                    for (int col = 0; col < model.getColumnCount(); col++) {

                        Object valor = model.getValueAt(row, col);

                        if (valor != null) {
                            filaExcel.createCell(col).setCellValue(valor.toString());
                        }
                    }
                }

                // Auto ajustar columnas
                for (int col = 0; col < model.getColumnCount(); col++) {
                    hoja.autoSizeColumn(col);
                }

                // Guardar archivo
                try (FileOutputStream fos = new FileOutputStream(archivo)) {
                    wb.write(fos);
                }

                JOptionPane.showMessageDialog(null,
                        "Exportación completada:\n" + archivo.getAbsolutePath());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al exportar: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    public List<Ticket> obtenerTicketsIniciadosHoy() {
        List<Ticket> lista = new ArrayList<>();

        LocalDate hoy = LocalDate.now();  // <-- SE TOMA AUTOMÁTICAMENTE

        String sql = """
            SELECT *
            FROM ticket
            WHERE fechaHoraIngreso >= ?
              AND fechaHoraIngreso < DATEADD(day, 1, ?)
            """;

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(hoy));
            ps.setDate(2, java.sql.Date.valueOf(hoy));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket t = new Ticket();

                // ==== ORDEN CORRECTO ====
                t.setId(rs.getInt("id"));
                t.setPlacaVehiculo(rs.getString("placaVehiculo"));
                t.setCarnetUsuario(rs.getString("carnetUsuario"));
                t.setTipoUsuario(rs.getString("tipoUsuario"));
                t.setTipoVehiculo(rs.getString("tipoVehiculo"));
                t.setCodigoSpot(rs.getString("codigoSpot"));
                t.setCodigoArea(rs.getString("codigoArea"));
                t.setFechaHoraIngreso(rs.getTimestamp("fechaHoraIngreso").toLocalDateTime());
                t.setFechaHoraSalida(rs.getTimestamp("fechaHoraSalida") != null
                        ? rs.getTimestamp("fechaHoraSalida").toLocalDateTime()
                        : null);
                t.setTarifaAplicada(rs.getString("tarifaAplicada"));
                t.setMonto(rs.getDouble("monto"));
                t.setMetodoPago(rs.getString("metodoPago"));
                t.setEstado(rs.getString("estado"));
                // ======================

                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("ERROR obtenerTicketsIniciadosHoy(): " + e.getMessage());
        }

        return lista;
    }
}


