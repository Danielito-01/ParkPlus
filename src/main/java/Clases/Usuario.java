package Clases;

public class Usuario {
   private int id;
   private String carnet;
   private String telefono;
   private String nombre;
   private String apellido;
   private String tipousuario;

    public Usuario(){}
    
    public Usuario(int id, String carnet, String telefono, String nombre, String apellido, String tipousuario) {
        this.id = id;
        this.carnet = carnet;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipousuario = tipousuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }
}
