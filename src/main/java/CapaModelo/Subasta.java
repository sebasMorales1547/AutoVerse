package CapaModelo;
import java.time.LocalDateTime;

public class Subasta {
    private int idAuto;
    private double precioActual;
    private String ultimoPostor;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Subasta() {}

    public Subasta(int idAuto, double precioInicial) {
        this.idAuto = idAuto;
        this.precioActual = precioInicial;
        this.ultimoPostor = "Sin ofertas";
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = this.fechaInicio.plusHours(24); // Duraciin de 24 horas
    }

    public boolean estaActiva() {
        return LocalDateTime.now().isBefore(fechaFin);
    }

    public int getIdAuto() { return idAuto; }
    public void setIdAuto(int idAuto) { this.idAuto = idAuto; }
    public double getPrecioActual() { return precioActual; }
    public void setPrecioActual(double precioActual) { this.precioActual = precioActual; }
    public String getUltimoPostor() { return ultimoPostor; }
    public void setUltimoPostor(String ultimoPostor) { this.ultimoPostor = ultimoPostor; }
    public LocalDateTime getFechaFin() { return fechaFin; }

    public double getMonto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public long getCedula() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getIdPublicacion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setIdPublicacion(int idAuto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setMonto(double precioBase) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}