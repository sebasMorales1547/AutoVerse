/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaModelo;

/**
 *
 * @author Familia
 */

import java.util.Date;
public class Venta {
    private int idVenta;
    private Date fechaVenta;
    private double montoFinal;
    private String metodoPago;
    private int idOferta;
    public Venta() {}
    public Venta(int idVenta, Date fechaVenta, double montoFinal, String metodoPago, int idOferta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.montoFinal = montoFinal;
        this.metodoPago = metodoPago;
        this.idOferta = idOferta;
    }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public Date getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }

    public double getMontoFinal() { return montoFinal; }
    public void setMontoFinal(double montoFinal) { this.montoFinal = montoFinal; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public int getIdItem() { return idOferta; }
    public void setIdOferta(int idOferta) { this.idOferta = idOferta; }
}