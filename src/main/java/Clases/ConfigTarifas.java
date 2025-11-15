package Clases;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigTarifas {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigTarifas.class.getClassLoader().getResourceAsStream("app.properties")) {

            if (input != null) {
                props.load(input);
            } else {
                System.err.println("No se encontró app.properties, usando valores por defecto.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getModo() {
        return props.getProperty("tarifa.modo", "AUTO").toUpperCase();
    }

    public static double getTarifaPlana() {
        return Double.parseDouble(props.getProperty("tarifa.plana", "15"));
    }

    public static double getValorFraccion() {
        return Double.parseDouble(props.getProperty("tarifa.valor", "5"));
    }

    public static int getMinutosFraccion() {
        return Integer.parseInt(props.getProperty("fraccion.minutos", "15"));
    }
}
