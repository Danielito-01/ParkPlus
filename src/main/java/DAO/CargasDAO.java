
package DAO;

public class CargasDAO {
    
    public boolean existeEnBDU(String carnet) {
        boolean existe = false;
        java.sql.Connection con = null;
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement("SELECT COUNT(*) FROM usuario WHERE carnet = ?");
            ps.setString(1, carnet);
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }

        } catch (Exception e) {
            System.err.println("Error verificando carnet en BD: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }

        return existe;
    }

    public boolean existeEnBDV(String placa) {
        boolean existe = false;
        java.sql.Connection con = null;
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement("SELECT COUNT(*) FROM vehiculo WHERE placa = ?");
            ps.setString(1, placa);
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }

        } catch (Exception e) {
            System.err.println("Error verificando placa en BD: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }

        return existe;
    }
}
