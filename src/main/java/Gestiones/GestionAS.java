package Gestiones;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionAS {
    
    public void mostrarA(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        try {
             Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement("SELECT codigo, nombre, capacidad, tipodevehiculo FROM area");
             ResultSet rs = ps.executeQuery();
             
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("capacidad"),
                        rs.getString("tipodevehiculo")

                    };
                    modelo.addRow(fila);
                }
                tabla.setModel(modelo);
                
        } catch(Exception e){
            System.out.println("Error buscando el area: " + e.getMessage());
        }
    }
    
    public void mostrarS(JTable tabla){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        try {
             Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement("SELECT codigo, codigodearea, tipodevehiculo FROM spot");
             ResultSet rs = ps.executeQuery();
             
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("codigo"),
                        rs.getString("codigodearea"),
                        rs.getString("tipodevehiculo")

                    };
                    modelo.addRow(fila);
                }
                tabla.setModel(modelo);
                
        } catch(Exception e){
            System.out.println("Error buscando el spot: " + e.getMessage());
        }
    }
}
