package CapaModelo;

import java.sql.Timestamp;

public class Ofertas {
    private int idOfertas;
    private double monto;
    private double precioMinimo;
    private String estado;
    private Timestamp fecha;
    private Timestamp fechaLimite;
    private long cedula;
    private int idPublicacion;
    private String tipo;
    private String tituloPublicacion;

    public int getIdOfertas() { return idOfertas; }
    public void setIdOfertas(int idOfertas) { this.idOfertas = idOfertas; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public double getPrecioMinimo() { return precioMinimo; }
    public void setPrecioMinimo(double precioMinimo) { this.precioMinimo = precioMinimo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public Timestamp getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(Timestamp fechaLimite) { this.fechaLimite = fechaLimite; }

    public long getCedula() { return cedula; }
    public void setCedula(long cedula) { this.cedula = cedula; }

    public int getIdPublicacion() { return idPublicacion; }
    public void setIdPublicacion(int idPublicacion) { this.idPublicacion = idPublicacion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTituloPublicacion() { return tituloPublicacion; }
    public void setTituloPublicacion(String tituloPublicacion) { this.tituloPublicacion = tituloPublicacion; }
}