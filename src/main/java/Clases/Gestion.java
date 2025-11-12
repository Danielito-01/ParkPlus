package Clases;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gestion {
    
    private ArrayList<Usuario> cargaUsuarios = new ArrayList<>(); 
    
    public void agregarUsuario(JTable tabla, Usuario usuario){
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
            if (usuario instanceof Estudiante) {
                Estudiante e = (Estudiante) usuario;
                Object[] fila = {
                    e.getCarnet(), e.getTelefono(), e.getNombre(), e.getApellido(), 
                    e.getTipousuario(), e.getCarrera(), e.getSemestre()
                };
                modelo.addRow(fila);
            } else if (usuario instanceof Docente) {
                Docente d = (Docente) usuario;
                Object[] fila = {
                    d.getCarnet(), d.getTelefono(), d.getNombre(), d.getApellido(), 
                    d.getTipousuario(), "-", "-"
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
                    c.getPlaca(), c.getColor(), c.getTipovehiculo(), c.getRol()
                };
                modelo.addRow(fila);

            } else if (v instanceof Moto) {
                Moto m = (Moto) v;
                Object[] fila = {
                    m.getPlaca(), m.getColor(), m.getTipovehiculo(), m.getRol()
                };
                modelo.addRow(fila);
            }
       }
    }
 
    public void limpiarUsuario(JDialog parent, JTable tabla, JTextField Carnet, JTextField Telefono, JTextField Nombre,
    JTextField Apellido, JComboBox<String> Carrera, JComboBox<String> Semestre, ButtonGroup tipoUsuario) {
    
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
           
            // Limpiar tabla
            modelo.setRowCount(0);

            // Limpiar campos de texto
            Carnet.setText("");
            Telefono.setText("");
            Nombre.setText("");
            Apellido.setText("");

            // Resetear combos
            Carrera.setSelectedIndex(0);
            Semestre.setSelectedIndex(0);

            // Limpiar selección de radio buttons
            tipoUsuario.clearSelection();
    }
   
    public void limpiarVehiculo(JDialog parent, JTable tabla, JTextField Placa, JTextField Color, 
    JComboBox<String> Rol, ButtonGroup tipoVehiculo) {
        
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
           
            // Limpiar tabla
            modelo.setRowCount(0);

            // Limpiar campos de texto
            Placa.setText("");
            Color.setText("");
            
            // Resetear combos
            Rol.setSelectedIndex(0);

            // Limpiar selección de radio buttons
            tipoVehiculo.clearSelection();
    }
    
     public void cargarArchivo(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            leerArchivo(archivo, parent);
        }
    }
     
    private void leerArchivo(File archivo, Component parent) {
        cargaUsuarios.clear(); // Limpia la lista antes de cargar
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                // Ignorar líneas vacías o con solo espacios
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split("\\|");

                if (partes.length == 7) { // Estudiante
                    String carnet = partes[0].trim();
                    String telefono = partes[1].trim();
                    String nombre = partes[2].trim();
                    String apellido = partes[3].trim();
                    String tipousuario = partes[4].trim();
                    String carrera = partes[5].trim();
                    String semestre = partes[6].trim();

                    cargaUsuarios.add(
                        new Estudiante(0, carnet, telefono, nombre, apellido, tipousuario, carrera, semestre)
                    );
                    contador++;

                } else if (partes.length == 5) { // Docente
                    String carnet = partes[0].trim();
                    String telefono = partes[1].trim();
                    String nombre = partes[2].trim();
                    String apellido = partes[3].trim();
                    String tipousuario = partes[4].trim();

                    cargaUsuarios.add(
                        new Docente(0, carnet, telefono, nombre, apellido, tipousuario)
                    );
                    contador++;

                } else {
                    System.out.println("Linea ignorada (formato incorrecto): " + linea);
                }
            }

            JOptionPane.showMessageDialog(parent,
                "Archivo cargado correctamente con " + contador + " usuarios.",
                "Carga exitosa",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo:\n" + e.getMessage(),
                "Error de lectura",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<Usuario> getCargaUsuarios() {
        return cargaUsuarios;
    }
    
    public void agregarCarga(JTable tablaCarga) {
    DefaultTableModel datos = (DefaultTableModel) tablaCarga.getModel();
    datos.setRowCount(0); // Limpia las filas actuales

    for (Usuario u : cargaUsuarios) {
        if (u instanceof Estudiante) {
            Estudiante e = (Estudiante) u;
            Object[] fila = {
                e.getCarnet(), e.getTelefono(), e.getNombre(), e.getApellido(),
                e.getTipousuario(), e.getCarrera(), e.getSemestre()
            };
            datos.addRow(fila);
        } else if (u instanceof Docente) {
            Docente d = (Docente) u;
            Object[] fila = {
                d.getCarnet(), d.getTelefono(), d.getNombre(), d.getApellido(),
                d.getTipousuario(), "-", "-"
            };
            datos.addRow(fila);
        }
    }

        tablaCarga.setModel(datos);
    }
    
    public boolean yaExisteVenLista(List<Vehiculo> listaVehiculos, String nuevaPlaca) {
 
    // 2. Si el rol SÍ es Propietario, revisamos la lista en busca de duplicados.
    for (Vehiculo v : listaVehiculos) {
        
        // Comparamos la placa (ignorando mayúsculas/minúsculas)
        boolean esMismaPlaca = v.getPlaca().equalsIgnoreCase(nuevaPlaca);
        
        
        // Si coinciden la placa Y el rol, la validación falla.
        if (esMismaPlaca) {
            return true; // CONFLICTO ENCONTRADO
        }
    }
    
    // 3. Todo bien, no hay conflicto.
    return false;
    }   
}
