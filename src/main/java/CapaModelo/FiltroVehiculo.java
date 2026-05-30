/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaModelo;

public class FiltroVehiculo {
    private String marca;
    private String modelo;
    private Integer añoMin;
    private Integer añoMax;
    private Double precioMin;
    private Double precioMax;
    private String gama;// Baja, Media, Alta
    private Float kilometrajeMax;

    public FiltroVehiculo() {}

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAñoMin() { return añoMin; }
    public void setAñoMin(Integer añoMin) { this.añoMin = añoMin; }

    public Integer getAñoMax() { return añoMax; }
    public void setAñoMax(Integer añoMax) { this.añoMax = añoMax; }

    public Double getPrecioMin() { return precioMin; }
    public void setPrecioMin(Double precioMin) { this.precioMin = precioMin; }

    public Double getPrecioMax() { return precioMax; }
    public void setPrecioMax(Double precioMax) { this.precioMax = precioMax; }

    public String getGama() { return gama; }
    public void setGama(String gama) { this.gama = gama; }

    public Float getKilometrajeMax() { return kilometrajeMax; }
    public void setKilometrajeMax(Float kilometrajeMax) { this.kilometrajeMax = kilometrajeMax; }
}