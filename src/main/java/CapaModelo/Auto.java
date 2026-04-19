package CapaModelo;

public class Auto {
    private int id;
    private String marca;
    private String modelo;
    private double precio;
    private String gama; // Baja, Media, Alta
    private boolean vendido;

    public Auto() {}

    public Auto(int id, String marca, String modelo, double precio, String gama, boolean vendido) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.gama = gama;
        this.vendido = vendido;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getGama() { return gama; }
    public void setGama(String gama) { this.gama = gama; }
    public boolean isVendido() { return vendido; }
    public void setVendido(boolean vendido) { this.vendido = vendido; }
}