package Clases;

public class UsuarioVehiculo {
    private String carnet;
    private String placa;
    private String rol;

    public UsuarioVehiculo(String carnet, String placa, String rol) {
        this.carnet = carnet;
        this.placa = placa;
        this.rol = rol;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    } 
}
