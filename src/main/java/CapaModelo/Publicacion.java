/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaModelo;

public class Publicacion {
    private int idPublicacion;
    private String titulo;
    private String descripcion;
    private float precio;
    private String estado;
    private long cedula;

    public Publicacion() {}

    public Publicacion(int idPublicacion, String titulo, String descripcion, float precio, String estado, long cedula) {
        this.idPublicacion = idPublicacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.cedula = cedula;
    }

    public int getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(int idPublicacion) { this.idPublicacion = idPublicacion; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public long getCedula() { return cedula; }
    public void setCedula(long cedula) { this.cedula = cedula; }
}