package Gestiones;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionUV {
    
    public void mostrarU(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        try {
             Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement("SELECT carnet, telefono, nombre, apellido, tipoUsuario, carrera, semestre FROM usuario");
             ResultSet rs = ps.executeQuery();
             
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("carnet"),
                        rs.getString("telefono"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("tipoUsuario"),
                        rs.getString("carrera"),
                        rs.getString("semestre")

                    };
                    modelo.addRow(fila);
                }
                tabla.setModel(modelo);
                
        } catch(Exception e){
            System.out.println("Error buscando el usuario: " + e.getMessage());
        }
    }
    
    public void mostrarV(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        try {
             Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement("SELECT placa, color, tipo FROM vehiculo");
             ResultSet rs = ps.executeQuery();
             
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("placa"),
                        rs.getString("color"),
                        rs.getString("tipo")

                    };
                    modelo.addRow(fila);
                }
                tabla.setModel(modelo);
                
        } catch(Exception e){
            System.out.println("Error buscando el vehiculo: " + e.getMessage());
        }
    }
       
    public void mostrarUV(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        try {
             Connection con = Conexion.Conectar();
            String sql = "SELECT u.carnet, u.nombre, u.tipoUsuario, v.placa, v.tipo, uv.rol " +
                         "FROM usuario_vehiculo uv " +
                         "JOIN usuario u ON uv.idUsuario = u.id " +
                         "JOIN vehiculo v ON uv.idVehiculo = v.id";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getString("carnet"),
                    rs.getString("nombre"),
                    rs.getString("tipoUsuario"),
                    rs.getString("placa"),
                    rs.getString("tipo"),
                    rs.getString("rol")
                };
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);
            
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
