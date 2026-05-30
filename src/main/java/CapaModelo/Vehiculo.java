package CapaModelo;

public class Vehiculo {
    private int idPublicacion;
    private String marca;
    private String modelo;
    private double año;
    private double kilometraje;
    private String gama; // Baja, Media, Alta
    private String placa;
    private String color;

    public Vehiculo() {}

    public Vehiculo(int idPublicacion,String color,double año,double kilometraje, 
            String marca, String modelo, double precio, String gama, String placa) {
        this.idPublicacion = idPublicacion;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.gama = gama;
        this.placa = placa;
        this.idPublicacion = idPublicacion;
        this.kilometraje = kilometraje;
        this.color = color;
    }
    // Getters y Setters
    public int getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(int idPublicacion) { this.idPublicacion = idPublicacion; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public double getAño() { return año; }
    public void setAño(double año) { this.año = año; }
    public double getKilometraje() { return kilometraje; }
    public void setKilometraje(double kilometraje) { this.kilometraje = kilometraje; }
    public String getGama() { return gama; }
    public void setGama(String gama) { this.gama = gama; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.color = modelo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}