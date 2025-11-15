package Clases;

import java.time.Duration;

public class TarifaCalculator {

    public static double calcular(Ticket t) {

        long minutos = Duration
                .between(t.getFechaHoraIngreso(), t.getFechaHoraSalida())
                .toMinutes();

        String modo = ConfigTarifas.getModo();

        switch (modo) {

            case "PLANA":
                return ConfigTarifas.getTarifaPlana();

            case "HORA":
                double horas = Math.ceil(minutos / 60.0);
                return horas * ConfigTarifas.getValorFraccion();

            case "FRACCION":
                int fr = ConfigTarifas.getMinutosFraccion();
                double fracciones = Math.ceil(minutos / (double) fr);
                return fracciones * ConfigTarifas.getValorFraccion();

            case "AUTO":   // usa tarifa del ticket
                if (t.getTarifaAplicada().equalsIgnoreCase("PLANA"))
                    return ConfigTarifas.getTarifaPlana();
                else
                    return minutos * (ConfigTarifas.getValorFraccion() / ConfigTarifas.getMinutosFraccion());

            default:
                return ConfigTarifas.getTarifaPlana();
        }
    }
}