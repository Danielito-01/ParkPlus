package Clases;

import DAO.CargasDAO;
import DAO.UsuarioDAO;
import DAO.UsuarioVehiculoDAO;
import DAO.VehiculoDAO;
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
    CargasDAO carga = new CargasDAO();
    private ArrayList<Usuario> cargaUsuarios = new ArrayList<>(); 
    private ArrayList<Vehiculo> cargaVehiculos = new ArrayList<>();
    private ArrayList<UsuarioVehiculo> cargaAV = new ArrayList<>();
    UsuarioVehiculoDAO uv = new UsuarioVehiculoDAO();
    UsuarioDAO daoU = new UsuarioDAO();
    VehiculoDAO daoV = new VehiculoDAO();
    
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
    
     public File cargarArchivo(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            return archivo;
        }
        return null;
    }
     
   public void leerArchivoU(File archivo, Component parent) {
    cargaUsuarios.clear();
    int contador = 0;
    int duplicadosArchivo = 0;
    int duplicadosBD = 0;
    ArrayList<String> carnetsLeidos = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue; // línea vacía

            String[] partes = linea.split("\\|");

            // 🔹 Validar cantidad mínima de columnas (5 o 7)
            if (partes.length != 5 && partes.length != 7) {
                System.out.println("Línea ignorada (formato incorrecto): " + linea);
                continue;
            }

            // 🔹 Verificar que ningún campo esté vacío
            boolean campoVacio = false;
            for (String p : partes) {
                if (p.trim().isEmpty()) {
                    campoVacio = true;
                    break;
                }
            }
            if (campoVacio) {
                System.out.println("Línea ignorada (campo vacío): " + linea);
                continue;
            }

            String carnet = partes[0].trim().toUpperCase();

            // 🔹 1. Validar duplicados en el mismo archivo
            if (carnetsLeidos.contains(carnet)) {
                System.out.println("Duplicado en archivo: " + carnet);
                duplicadosArchivo++;
                continue;
            }

            // 🔹 2. Validar duplicados en la base de datos
            if (carga.existeEnBDU(carnet)) {
                System.out.println("Duplicado en BD: " + carnet);
                duplicadosBD++;
                continue;
            }

            carnetsLeidos.add(carnet);

            // 🔹 3. Crear el objeto correspondiente
            if ("Estudiante".equalsIgnoreCase(partes[4].trim())) {
                String telefono = partes[1].trim().toUpperCase();
                String nombre = partes[2].trim().toUpperCase();
                String apellido = partes[3].trim().toUpperCase();
                String tipousuario = partes[4].trim().toUpperCase();
                String carrera = partes[5].trim().toUpperCase();
                String semestre = partes[6].trim().toUpperCase();

                cargaUsuarios.add(new Estudiante(0, carnet, telefono, nombre, apellido, tipousuario, carrera, semestre));
                contador++;

            } else if ("Docente".equalsIgnoreCase(partes[4].trim())) {
                String telefono = partes[1].trim().toUpperCase();
                String nombre = partes[2].trim().toUpperCase();
                String apellido = partes[3].trim().toUpperCase();
                String tipousuario = partes[4].trim().toUpperCase();

                cargaUsuarios.add(new Docente(0, carnet, telefono, nombre, apellido, tipousuario));
                contador++;
            }
        }

        JOptionPane.showMessageDialog(parent,
            "Carga completada.\n"
            + "Usuarios cargados: " + contador,
            "Resultado de carga",
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
    
    public void agregarCarga(JTable tablaCarga, String tipo) {
    DefaultTableModel datos = (DefaultTableModel) tablaCarga.getModel();
    datos.setRowCount(0); // Limpia las filas actuales
        
    if ("usuario".equalsIgnoreCase(tipo)) {
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
        }
        if ("vehiculo".equalsIgnoreCase(tipo)) {
            for (Vehiculo v : cargaVehiculos) {
            if (v instanceof Moto) {
                Moto m = (Moto) v;
                Object[] fila = {
                    m.getPlaca(), m.getColor(), m.getTipovehiculo()
                };
                datos.addRow(fila);
                } else if (v instanceof Carro) {
                    Carro c = (Carro) v;
                    Object[] fila = {
                        c.getPlaca(), c.getColor(), c.getTipovehiculo()
                    };
                    datos.addRow(fila);
                }
            }
        }
        if ("av".equalsIgnoreCase(tipo)) {
            for (UsuarioVehiculo av : cargaAV) {
                Object[] fila = {
                    av.getCarnet(), av.getPlaca(), av.getRol()
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
    
    public void limpiarTablaC(JDialog parent, JTable tablaCarga){
        DefaultTableModel modelo = (DefaultTableModel) tablaCarga.getModel();
        modelo.setRowCount(0);
    }
    
    public void limpiarArreglo(String tipo){
        if ("usuario".equalsIgnoreCase(tipo)) {
          cargaUsuarios.clear();  
        }
        if ("vehiculo".equalsIgnoreCase(tipo)) {
            cargaVehiculos.clear();
        }
        if ("av".equalsIgnoreCase(tipo)) {
            
        }
    }
    
    public void leerArchivoV(File archivo, Component parent) {
    cargaVehiculos.clear();
    int contador = 0;
    int duplicadosArchivo = 0;
    int duplicadosBD = 0;
    
    ArrayList<String> placasLeidas = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue; // línea vacía

            String[] partes = linea.split("\\|");

            // 🔹 Validar cantidad mínima de columnas (5 o 7)
            if (partes.length != 3) {
                System.out.println("Línea ignorada (formato incorrecto): " + linea);
                continue;
            }

            // 🔹 Verificar que ningún campo esté vacío
            boolean campoVacio = false;
            for (String p : partes) {
                if (p.trim().isEmpty()) {
                    campoVacio = true;
                    break;
                }
            }
            if (campoVacio) {
                System.out.println("Línea ignorada (campo vacío): " + linea);
                continue;
            }

            String placa = partes[0].trim().toUpperCase();

            // 🔹 1. Validar duplicados en el mismo archivo
            if (placasLeidas.contains(placa)) {
                System.out.println("Duplicado en archivo: " + placa);
                duplicadosArchivo++;
                continue;
            }

            // 🔹 2. Validar duplicados en la base de datos
            if (carga.existeEnBDV(placa)) {
                System.out.println("Duplicado en BD: " + placa);
                duplicadosBD++;
                continue;
            }

            placasLeidas.add(placa);

            // 🔹 3. Crear el objeto correspondiente
            if ("Moto".equalsIgnoreCase(partes[2].trim())) {
                String color = partes[1].trim().toUpperCase();
                String tipo = partes[2].trim().toUpperCase();

                cargaVehiculos.add(new Moto(0, placa, color, tipo, ""));
                contador++;

            } else if ("Carro".equalsIgnoreCase(partes[2].trim())) {
                String color = partes[1].trim().toUpperCase();
                String tipo = partes[2].trim().toUpperCase();

                cargaVehiculos.add(new Carro(0, placa, color, tipo, ""));
                contador++;
            }
        }

        JOptionPane.showMessageDialog(parent,
            "Carga completada.\n"
            + "Vehiculos cargados: " + contador,
            "Resultado de carga",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(parent,
            "Error al leer el archivo:\n" + e.getMessage(),
            "Error de lectura",
            JOptionPane.ERROR_MESSAGE);
    }
}

    public ArrayList<Vehiculo> getCargaVehiculos() {
        return cargaVehiculos;
    }
    
    public void leerArchivoAV(File archivo, Component parent) {
    cargaAV.clear();
    int contador = 0;
    int duplicadosArchivo = 0;
    int duplicadosBD = 0;
    int conflictosRol = 0;

    ArrayList<String> relacionesLeidas = new ArrayList<>();
    ArrayList<String> relacionesLeidas1 = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;

            String[] partes = linea.split("\\|");
            if (partes.length != 3) {
                System.out.println("Línea ignorada (formato incorrecto): " + linea);
                continue;
            }

            // Campos
            String carnet = partes[0].trim().toUpperCase();
            String placa = partes[1].trim().toUpperCase();
            String rol = partes[2].trim().toUpperCase();

            // Validar campos vacíos
            if (carnet.isEmpty() || placa.isEmpty() || rol.isEmpty()) {
                System.out.println("Línea ignorada (campo vacío): " + linea);
                continue;
            }
            
            // 🔹 Validar existencia del usuario (en arreglo o BD)
            boolean existeUsuario = cargaUsuarios.stream()
                    .anyMatch(u -> u.getCarnet().equalsIgnoreCase(carnet))
                    || daoU.existeCarnet(carnet);

            // 🔹 Validar existencia del vehículo (en arreglo o BD)
            boolean existeVehiculo = cargaVehiculos.stream()
                    .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa))
                    || daoV.existeVehiculo(placa);


            if (!existeUsuario) {
                System.out.println("Usuario no encontrado: " + carnet);
                continue;
            }

            if (!existeVehiculo) {
                System.out.println("Vehículo no encontrado: " + placa);
                continue;
            }
            
            // Identificador único para evitar duplicados en archivo
            String clave = carnet + "|" + placa;
            String clave1 = placa + "|" + rol;
            
            // 🔹 1. Duplicados en el mismo archivo
            if (relacionesLeidas.contains(clave)) {
                System.out.println("Duplicado en archivo: " + clave);
                duplicadosArchivo++;
                continue;
            }
                
            if (relacionesLeidas1.contains(clave1)) {
                System.out.println("Ya tiene propietario en el archivo" + clave1);
                duplicadosArchivo++;
                continue;
            }
            
            // 🔹 3. Validar si ya existe relación en BD (carnet + placa)
            if (uv.existeRol(carnet, placa)) {
                System.out.println("Duplicado en BD (ya tiene relación): " + clave);
                duplicadosBD++;
                continue;
            }
            
            
            if ("propietario".equalsIgnoreCase(rol)) {
                // 🔹 4. Validar conflicto de rol en archivo o BD
                if (uv.tienePropietario(placa)) {
                    System.out.println("Ya tiene propietario: " + placa);
                    conflictosRol++;
                    continue;
                }
            }
            
            relacionesLeidas.add(clave);
            relacionesLeidas1.add(clave1);

            // 🔹 5. Crear objeto relación válida
            cargaAV.add(new UsuarioVehiculo(carnet, placa, rol));
            contador++;
        }

            JOptionPane.showMessageDialog(parent,
                "Carga completada.\n"
                + "Relaciones cargadas: " + contador
                + "\nDuplicados en archivo: " + duplicadosArchivo
                + "\nDuplicados en BD: " + duplicadosBD
                + "\nConflictos de rol: " + conflictosRol,
                "Resultado de carga",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo:\n" + e.getMessage(),
                "Error de lectura",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<UsuarioVehiculo> getCargaAV() {
        return cargaAV;
    }
}
