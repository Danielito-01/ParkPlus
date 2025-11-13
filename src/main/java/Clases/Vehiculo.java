package Clases;

public class Vehiculo {
    private int id;
    private String placa;
    private String color;
    private String tipovehiculo;
    private String rol;
    
    
    public Vehiculo(){}
    
    public Vehiculo(int id, String placa, String color, String tipovehiculo, String rol) {
        this.id = id;
        this.placa = placa;
        this.color = color;
        this.tipovehiculo = tipovehiculo;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
