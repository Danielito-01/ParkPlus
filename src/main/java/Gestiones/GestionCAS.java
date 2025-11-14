package Gestiones;

import Clases.Area;
import Clases.Spot;
import DAO.CargasDAO;
import DAO.Conexion;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionCAS {
    CargasDAO daoC = new CargasDAO();
    ArrayList<Area> cargaAreas = new ArrayList<>();
    ArrayList<Spot> cargaSpots = new ArrayList<>();
    
    public File cargarArchivo(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            return archivo;
        }
        return null;
    }    
    
    public void leerArchivoA(File archivo, Component parent) {
        cargaAreas.clear();
        int contador = 0;
        int duplicadosArchivo = 0;
        int duplicadosBD = 0;
        int areasTotal;
        
        ArrayList<String> codigosRA = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                
                if (linea.trim().isEmpty()){
                  continue; // línea vacía  
                } 

                String[] partes = linea.split("\\|");

                //Validar cantidad mínima de columnas
                if (partes.length != 4) {
                    System.out.println("Linea ignorada (formato incorrecto): " + linea);
                    continue;
                }

                //Verificar que ningún campo esté vacío
                boolean campoVacio = false;
                for (String p : partes) {
                    if (p.trim().isEmpty()) {
                        campoVacio = true;
                        break;
                    }
                }
                
                if (campoVacio) {
                    System.out.println("Linea ignorada (campo vacío): " + linea);
                    continue;
                }

                String codigo = partes[0].trim().toUpperCase();

                //1. Validar codigos duplicados en el mismo archivo
                
                if (codigosRA.contains(codigo)) {
                    System.out.println("Duplicado en archivo: " + codigo);
                    duplicadosArchivo++;
                    continue;
                }

                //2. Validar codigos duplicados en la base de datos
                if (daoC.existeCA(codigo)) {
                    System.out.println("Duplicado en BD: " + codigo);
                    duplicadosBD++;
                    continue;
                }
                
                String nombre = partes[1].trim().toUpperCase();
                int capacidad = Integer.parseInt(partes[2].trim());
                String tipodevehiculo = partes[3].trim().toUpperCase();
                
                if (daoC.existeABD(nombre)) {
                    System.out.println("El area ya existe: "+ nombre);
                    continue;
                }
                
                if (!("Estudiantes".equalsIgnoreCase(nombre) || "Docentes".equalsIgnoreCase(nombre) || "Motos".equalsIgnoreCase(nombre))) {
                    System.out.println("Area no permitida, solo permite: Estudiante, Docente y Motos.");
                    continue;
                }
                
                if ("Estudiantes".equalsIgnoreCase(nombre) || "Docentes".equalsIgnoreCase(nombre)) {
                    if (!"Carro".equalsIgnoreCase(tipodevehiculo)) {
                        System.out.println("Eror de area y tipo de vehiculo: " + nombre +" "+ tipodevehiculo);
                        continue;
                    }
                }
                
                if ("Motos".equalsIgnoreCase(nombre)) {
                    if (!"Moto".equalsIgnoreCase(tipodevehiculo)) {
                        System.out.println("Error de area y tipo de vehiculo: " + nombre +" "+ tipodevehiculo);
                        continue;
                    }
                }
                
                codigosRA.add(codigo);
                //3. Crear el objeto correspondiente
                cargaAreas.add(new Area(0, codigo, nombre, capacidad, tipodevehiculo));
                contador++;
            }
            
            JOptionPane.showMessageDialog(parent,
                "Carga completada.\n"
                + "Areas cargadas: " + contador,
                "Resultado de carga",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo:\n" + e.getMessage(),
                "Error de lectura",
                JOptionPane.ERROR_MESSAGE);
          }
    }

    public ArrayList<Area> getCargaAreas() {
        return cargaAreas;
    }
    
    public void leerArchivoS(File archivo, Component parent) {
        cargaSpots.clear();
        int contador = 0;
        int duplicadosArchivo = 0;
        int duplicadosBD = 0;
        
        ArrayList<String> codigosRS = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                
                if (linea.trim().isEmpty()){
                  continue; // línea vacía  
                } 

                String[] partes = linea.split("\\|");

                //Validar cantidad mínima de columnas
                if (partes.length != 4) {
                    System.out.println("Linea ignorada (formato incorrecto): " + linea);
                    continue;
                }

                //Verificar que ningún campo esté vacío
                boolean campoVacio = false;
                for (String p : partes) {
                    if (p.trim().isEmpty()) {
                        campoVacio = true;
                        break;
                    }
                }
                
                if (campoVacio) {
                    System.out.println("Linea ignorada (campo vacío): " + linea);
                    continue;
                }

                String codigo = partes[0].trim().toUpperCase();

                //1. Validar codigos duplicados en el mismo archivo
                
                if (codigosRS.contains(codigo)) {
                    System.out.println("Duplicado en archivo: " + codigo);
                    duplicadosArchivo++;
                    continue;
                }

                //2. Validar codigos duplicados en la base de datos
                if (daoC.existeCS(codigo)) {
                    System.out.println("Duplicado en BD: " + codigo);
                    duplicadosBD++;
                    continue;
                }
                
                String codigodearea = partes[1].trim().toUpperCase();
                String tipodevehiculo = partes[2].trim().toUpperCase();
                String estadoSTR = partes[3].trim().toUpperCase();
                
                //3. Validar que el codigo de area exista en el archivo cargado de areas o en la BD
                if ((!daoC.existeCA(codigodearea)) && (!existeCAA(codigodearea))) {
                    System.out.println("Area no existe en archivo y tampoco em BD: " + codigodearea +" "+ daoC.existeCA(codigodearea) +" "+ existeCAA(codigodearea) );
                    continue;
                }
                
                //4. El estado no es permitido
                if (!("Libre".equalsIgnoreCase(estadoSTR) || "Ocupado".equalsIgnoreCase(estadoSTR))) {
                    System.out.println("El estado solo puede ser: Libre o Ocupado.");
                    continue;
                }
                
                //5. Validar que el tipo de vehiculo coincida con el del area en archivo y BD
                if (daoC.existeCA(codigodearea)) {
                    if (!(tipodevehiculo.equalsIgnoreCase(daoC.tipoVADB(codigodearea)))) {
                    System.out.println("El tipo de vehiculo del spot no con coincide con el area de la BD " + tipodevehiculo +" "+daoC.tipoVADB(codigodearea));
                    continue;
                    }
                }
                
                if (codigosRS.contains(codigo)) {
                    if (!(tipodevehiculo.equalsIgnoreCase(TipoVAA(codigodearea)))) {
                    System.out.println("El tipo de vehiculo del spot no coincide con el area: "+ tipodevehiculo +" "+ TipoVAA(codigodearea));
                    continue;
                    }
                }
                
                
                               
                boolean estado = false;
                if ("Libre".equalsIgnoreCase(estadoSTR)) {
                    estado = false;
                } else if ("Ocupado".equalsIgnoreCase(estadoSTR)){
                           estado = true;
                  }
                
                codigosRS.add(codigo);
                //Crear el objeto correspondiente
                cargaSpots.add(new Spot(0, codigo, codigodearea, tipodevehiculo, estado));
                contador++;
            }

            JOptionPane.showMessageDialog(parent,
                "Carga completada.\n"
                + "Spots cargados: " + contador,
                "Resultado de carga",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo:\n" + e.getMessage(),
                "Error de lectura",
                JOptionPane.ERROR_MESSAGE);
          }
    }
    
    public ArrayList<Spot> getCargaSpots() {
        return cargaSpots;
    }
    
    public void mostrarTabla(JTable tabla, String tipo) {
        DefaultTableModel datos = (DefaultTableModel) tabla.getModel();
        datos.setRowCount(0); // Limpia las filas actuales

            if ("Area".equalsIgnoreCase(tipo)) {
                for (Area a : cargaAreas) {
                    Object[] fila = {
                        a.getCodigo(), a.getNombre(), a.getCapacidad(), a.getTipodevehiculo()
                    };
                    datos.addRow(fila);
                }
            }
            if ("Spot".equalsIgnoreCase(tipo)) {
                for (Spot s: cargaSpots) {
                    String estado = "OCUPADO";
                    if (s.isEstado()) {
                        estado = "OCUPADO";
                    } else if (!s.isEstado()) {
                        estado = "LIBRE";
                    }
                    Object[] fila = {
                        s.getCodigo(), s.getCodigodearea(), s.getTipodevehiculo(), estado
                    };
                    datos.addRow(fila);
                }
            }
            tabla.setModel(datos);
        }
    
    public void limpiarDatosTA(JTable tabla, String tipo){
        DefaultTableModel datos = (DefaultTableModel) tabla.getModel();
        datos.setRowCount(0);
        
        if ("area".equalsIgnoreCase(tipo)) {
            cargaAreas.clear();
        }
        if ("spot".equalsIgnoreCase(tipo)) {
            cargaSpots.clear();
        }
    }
    
    public boolean existeCAA(String codigodearea) {
        for (Area area : cargaAreas) {
            if (area.getCodigo().equalsIgnoreCase(codigodearea)) {
                return true;
            }
        }
        return false;
    }
    
    public String TipoVAA(String codigodearea) {
        for (Area area : cargaAreas) {
            if (area.getCodigo().equalsIgnoreCase(codigodearea)) {
                return area.getTipodevehiculo();
            }
        }
        return null;
    }
    
    public boolean insertarA(ArrayList<Area> areas) {
        String sql = "INSERT INTO area (codigo, nombre, capacidad, tipodevehiculo) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Area a : areas) {
                ps.setString(1, a.getCodigo());
                ps.setString(2, a.getNombre());
                ps.setInt(3, a.getCapacidad());
                ps.setString(4, a.getTipodevehiculo());
                
                ps.addBatch(); // agrega el INSERT a un lote
            }

            ps.executeBatch(); // ejecuta todos los INSERT juntos
            System.out.println("Se insertaron " + areas.size() + " areas correctamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar areas: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarS(ArrayList<Spot> spots) {
        String sql = "INSERT INTO spot (codigo, codigodearea, tipodevehiculo, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.Conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Spot s : spots) {
                ps.setString(1, s.getCodigo());
                ps.setString(2, s.getCodigodearea());
                ps.setString(3, s.getTipodevehiculo());
                ps.setBoolean(4, s.isEstado());
                ps.addBatch();
            }

            int[] resultados = ps.executeBatch();

            int insertados = 0;
            int fallados = 0;

            for (int r : resultados) {
                if (r == 1) insertados++;       
                else if (r == 0) fallados++;       
            }

            System.out.println("Spots insertados en la BD: " + insertados);
            System.out.println("Spots rechazados " + fallados);

            return insertados > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar spots: " + e.getMessage());
            return false;
        }
    }
}
