package Clases;


public class Spot {
    private int id;
    private String codigo;
    private String codigodearea;
    private String tipodevehiculo;
    private boolean estado;

    public Spot(int id, String codigo, String codigodearea, String tipodevehiculo, boolean estado) {
        this.id = id;
        this.codigo = codigo;
        this.codigodearea = codigodearea;
        this.tipodevehiculo = tipodevehiculo;
        this.estado = estado;
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

    public String getCodigodearea() {
        return codigodearea;
    }

    public void setCodigodearea(String codigodearea) {
        this.codigodearea = codigodearea;
    }

    public String getTipodevehiculo() {
        return tipodevehiculo;
    }

    public void setTipodevehiculo(String tipodevehiculo) {
        this.tipodevehiculo = tipodevehiculo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
