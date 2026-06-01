package CapaModelo;

public class Vehiculos {

    private int idPublicacion;
    private String marca;
    private String modelo;
    private int año;
    private float kilometraje;
    private String placa;
    private String color;
    private String combustible;



    public Vehiculos() {
    }

    public Vehiculos(int idPublicacion, String marca, String modelo,
                     int año, float kilometraje,
                     String placa, String color, String combustible) {

        this.idPublicacion = idPublicacion;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.kilometraje = kilometraje;
        this.placa = placa;
        this.color = color;
        this.combustible = combustible;
    }

    public String getCombustible() { 
        return combustible; 
    }
    
    public void setCombustible(String combustible) {
         this.combustible = combustible; 
        }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public float getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(float kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}