package Gestiones;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuscarService {
    
      public static class Resultado {
        private String tabla;
        private String columna;
        private String valor;

        public Resultado(String tabla, String columna, String valor) {
            this.tabla = tabla;
            this.columna = columna;
            this.valor = valor;
        }

        public String getTabla() { return tabla; }
        public String getColumna() { return columna; }
        public String getValor() { return valor; }
    }

    public List<Resultado> buscar(String palabra) {
        List<Resultado> resultados = new ArrayList<>();
        String esquema = "dbo";

        try (Connection conn = Conexion.Conectar()) {

            // Obtener tablas del esquema dbo
            ResultSet tablas = conn.getMetaData().getTables(null, esquema, "%", new String[]{"TABLE"});

            while (tablas.next()) {
                String tabla = tablas.getString("TABLE_NAME");

                // Obtener columnas de tipo String
                ResultSet columnas = conn.getMetaData().getColumns(null, esquema, tabla, "%");
                List<String> columnasTexto = new ArrayList<>();
                while (columnas.next()) {
                    String columna = columnas.getString("COLUMN_NAME");
                    String tipo = columnas.getString("TYPE_NAME").toUpperCase();
                    if (tipo.contains("CHAR") || tipo.contains("TEXT")) {
                        columnasTexto.add(columna);
                    }
                }

                // Buscar palabra en cada columna de texto
                for (String columna : columnasTexto) {
                    String sql = "SELECT * FROM " + esquema + "." + tabla + 
                                 " WHERE " + columna + " LIKE ? COLLATE SQL_Latin1_General_CP1_CI_AS";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, "%" + palabra + "%");
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            resultados.add(new Resultado(tabla, columna, rs.getString(columna)));
                        }
                    } catch (SQLException e) {
                        // Ignorar columnas que puedan dar error
                        System.err.println("Error buscando en " + tabla + "." + columna + ": " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
    
}
