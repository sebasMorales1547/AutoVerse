/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaModelo;

import java.util.Date;

public class HistorialCompra {
    private int idVenta;
    private String tituloPublicacion;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private double montoFinal;
    private String metodoPago;
    private Date fechaVenta;
    private String estadoVenta; 

    public HistorialCompra() {}

    public HistorialCompra(int idVenta, String tituloPublicacion, String marcaVehiculo,
                           String modeloVehiculo, double montoFinal, String metodoPago,
                           Date fechaVenta, String estadoVenta) {
        this.idVenta = idVenta;
        this.tituloPublicacion = tituloPublicacion;
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
        this.montoFinal = montoFinal;
        this.metodoPago = metodoPago;
        this.fechaVenta = fechaVenta;
        this.estadoVenta = estadoVenta;
    }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public String getTituloPublicacion() { return tituloPublicacion; }
    public void setTituloPublicacion(String tituloPublicacion) { this.tituloPublicacion = tituloPublicacion; }

    public String getMarcaVehiculo() { return marcaVehiculo; }
    public void setMarcaVehiculo(String marcaVehiculo) { this.marcaVehiculo = marcaVehiculo; }

    public String getModeloVehiculo() { return modeloVehiculo; }
    public void setModeloVehiculo(String modeloVehiculo) { this.modeloVehiculo = modeloVehiculo; }

    public double getMontoFinal() { return montoFinal; }
    public void setMontoFinal(double montoFinal) { this.montoFinal = montoFinal; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Date getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }

    public String getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(String estadoVenta) { this.estadoVenta = estadoVenta; }

    @Override
    public String toString() {
        return "[" + fechaVenta + "] " + tituloPublicacion +
               " | " + marcaVehiculo + " " + modeloVehiculo +
               " | $" + montoFinal + " via " + metodoPago;
    }
}