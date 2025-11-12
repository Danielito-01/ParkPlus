package Clases;

public class UsuarioVehiculo {
    private int idusuario;
    private int idvehiculo;
    private String rol;

    public UsuarioVehiculo(int idusuario, int idvehiculo, String rol) {
        this.idusuario = idusuario;
        this.idvehiculo = idvehiculo;
        this.rol = rol;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(int idvehiculo) {
        this.idvehiculo = idvehiculo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
