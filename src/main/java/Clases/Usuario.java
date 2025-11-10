package Clases;

public class Usuario {
   private String idusuario;
   private String carnet;
   private int telefono;
   private String nombre;
   private String apellido;
   private String tipousuario;
   private Vehiculo vehiculo;

    public Usuario(String idusuario, String carnet, int telefono, String nombre, String apellido, String tipousuario) {
        this.idusuario = idusuario;
        this.carnet = carnet;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipousuario = tipousuario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getTipousuario(){
        return tipousuario;
    }
    
    public void setTipoUsuario(String tipousuario){
        this.tipousuario = tipousuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
