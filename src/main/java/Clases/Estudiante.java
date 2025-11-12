package Clases;

public class Estudiante extends Usuario{
    private String carrera;
    private String semestre;

    public Estudiante(){}
    
    public Estudiante(int id, String carnet, String telefono, String nombre, String apellido, String tipousuario, String carrera, String semestre) {
        super(id, carnet, telefono, nombre, apellido, tipousuario);
        this.carrera = carrera;
        this.semestre = semestre;
    }    

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}
