package Clases;


public class Area {
    private int id;
    private String codigo;
    private String nombre;
    private int capacidad;
    private String tipodevehiculo;

    public Area(){}
    
    public Area(int id, String codigo, String nombre, int capacidad, String tipodevehiculo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipodevehiculo = tipodevehiculo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipodevehiculo() {
        return tipodevehiculo;
    }

    public void setTipodevehiculo(String tipodevehiculo) {
        this.tipodevehiculo = tipodevehiculo;
    }
}
