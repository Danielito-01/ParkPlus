package Clases;

public class Vehiculo {
    String placa;
    String color;
    String tipovehiculo;

    public Vehiculo(String placa, String color, String tipovehiculo) {
        this.placa = placa;
        this.color = color;
        this.tipovehiculo = tipovehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(String tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }
}
