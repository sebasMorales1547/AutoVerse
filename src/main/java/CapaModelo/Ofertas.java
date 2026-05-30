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

public class Ofertas {
    private int idOfertas;
    private float monto;
    private String estado;
    private Date fecha;
    private int cedula;
    private int idPublicacion;
    public Ofertas() {}
    public Ofertas(int idOfertas, float monto, String estado, Date fecha, int cedula, int idPublicacion) {
        this.idOfertas = idOfertas;
        this.monto = monto;
        this.estado = estado;
        this.fecha = fecha;
        this.cedula = cedula;
        this.idPublicacion = idPublicacion;
    }

    public int getIdOfertas() { return idOfertas; }
    public void setIdOfertas(int idOfertas) { this.idOfertas = idOfertas; }

    public float getMonto() { return monto; }
    public void setMonto(float monto) { this.monto = monto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    public int getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(int idPublicacion) { this.idPublicacion = idPublicacion; }
}