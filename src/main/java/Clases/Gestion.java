package Clases;

import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gestion {
  
    public void agregarUsuario(JTable tabla, Usuario usuario){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
            if (usuario instanceof Estudiante) {
                Estudiante e = (Estudiante) usuario;
                Object[] fila = {
                    e.getIdusuario(), e.getCarnet(), e.getTelefono(),
                    e.getNombre(), e.getApellido(), e.getTipousuario(),
                    e.getCarrera(), e.getSemestre()
                };
                modelo.addRow(fila);
            } else if (usuario instanceof Docente) {
                Docente d = (Docente) usuario;
                Object[] fila = {
                    d.getIdusuario(), d.getCarnet(), d.getTelefono(),
                    d.getNombre(), d.getApellido(), d.getTipousuario(), "-", "-"
                };
                modelo.addRow(fila);
            }
    }
   
    public void agregarVehiculo(JTable tabla, ArrayList<Vehiculo> vehiculo){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (Vehiculo v : vehiculo) {
            if (v instanceof Carro) {
                Carro c = (Carro) v;
                Object[] fila = {
                    c.getIdvehiculo(),
                    c.getPlaca(),
                    c.getColor(),
                    c.getTipovehiculo(),
                    c.getRol(),
                    c.getIdusuario(),
                };
                modelo.addRow(fila);

            } else if (v instanceof Moto) {
                Moto m = (Moto) v;
                Object[] fila = {
                    m.getIdvehiculo(),
                    m.getPlaca(),
                    m.getColor(),
                    m.getTipovehiculo(),
                    m.getRol(),
                    m.getIdusuario(),
                };
                modelo.addRow(fila);
            }
       }
    }
 
    public void limpiarUsuario(JDialog parent, JTable tabla, JTextField Idu, JTextField Carnet,
    JTextField Telefono,JTextField Nombre, JTextField Apellido, JComboBox<String> Carrera, JComboBox<String> Semestre,
    ButtonGroup tipoUsuario,JTextField Placa, JTextField Color, JComboBox<String> Rol, ButtonGroup tipoVehiculo) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
           
            // Limpiar tabla
            modelo.setRowCount(0);

            // Limpiar campos de texto
            Idu.setText("");
            Carnet.setText("");
            Telefono.setText("");
            Nombre.setText("");
            Apellido.setText("");
            Placa.setText("");
            Color.setText("");

            // Resetear combos
            Carrera.setSelectedIndex(0);
            Semestre.setSelectedIndex(0);
            Rol.setSelectedIndex(0);

            // Limpiar selección de radio buttons
            tipoUsuario.clearSelection();
    }
    
    
}
