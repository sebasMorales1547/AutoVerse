package CapaModelo;

import java.time.LocalDateTime;

public class Subasta {

    private int idPublicacion;
    private double monto;
    private int cedula;  
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado; 

    public Subasta() {
        this.fechaInicio = LocalDateTime.now();
        this.estado = "ACTIVA";
    }
    public Subasta(int idPublicacion, double precioInicial) {
        this();
        this.idPublicacion = idPublicacion;
        this.monto = precioInicial;
        this.fechaFin = this.fechaInicio.plusHours(24); 
    }

    public boolean estaActiva() {
        return LocalDateTime.now().isBefore(fechaFin) && "ACTIVA".equals(this.estado);
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }
    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public long getCedula() {
        return cedula;
    }
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}