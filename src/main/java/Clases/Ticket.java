package Clases;

import java.time.LocalDateTime;

public class Ticket {
    
    private int id;
    private String placaVehiculo;
    private String carnetUsuario;
    private String tipoUsuario; 
    private String tipoVehiculo;
    private String codigoSpot;
    private String codigoArea;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private String tarifaAplicada;
    private double monto;
    private String metodoPago;
    private String estado;        
    
    public Ticket() {}

    public Ticket(int id, String placaVehiculo, String carnetUsuario, String tipoUsuario, String tipoVehiculo, String codigoSpot, String codigoArea, LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida, String tarifaAplicada, double monto, String metodoPago, String estado) {
        this.id = id;
        this.placaVehiculo = placaVehiculo;
        this.carnetUsuario = carnetUsuario;
        this.tipoUsuario = tipoUsuario;
        this.tipoVehiculo = tipoVehiculo;
        this.codigoSpot = codigoSpot;
        this.codigoArea = codigoArea;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
        this.tarifaAplicada = tarifaAplicada;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getCarnetUsuario() {
        return carnetUsuario;
    }

    public void setCarnetUsuario(String carnetUsuario) {
        this.carnetUsuario = carnetUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getCodigoSpot() {
        return codigoSpot;
    }

    public void setCodigoSpot(String codigoSpot) {
        this.codigoSpot = codigoSpot;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public LocalDateTime getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getTarifaAplicada() {
        return tarifaAplicada;
    }

    public void setTarifaAplicada(String tarifaAplicada) {
        this.tarifaAplicada = tarifaAplicada;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
