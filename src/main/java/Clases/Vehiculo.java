package Clases;

public class Vehiculo {
    String idvehiculo;
    String placa;
    String color;
    String tipovehiculo;
    String rol;
    String idusuario;

    public Vehiculo(String idvehiculo, String placa, String color, String tipovehiculo, String rol, String idusuario) {
        this.idvehiculo = idvehiculo;
        this.placa = placa;
        this.color = color;
        this.tipovehiculo = tipovehiculo;
        this.rol = rol;
        this.idusuario = idusuario;
    }

    public String getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(String idvehiculo) {
        this.idvehiculo = idvehiculo;
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

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }
    
    
}
