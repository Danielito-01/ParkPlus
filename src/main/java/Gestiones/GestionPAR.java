package Gestiones;

import Clases.Spot;
import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionPAR {
    
    public List<Spot> obtenerTodosLosSpots() {
        List<Spot> lista = new ArrayList<>();

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM spot");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Spot s = new Spot(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("codigodearea"),
                    rs.getString("tipodevehiculo"),
                    rs.getBoolean("estado")
                );
                lista.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    public Map<String, String> obtenerNombreAreaPorCodigo() {
        Map<String, String> mapa = new HashMap<>();

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement("SELECT codigo, nombre FROM area");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mapa.put(rs.getString("codigo"), rs.getString("nombre")); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }
}
