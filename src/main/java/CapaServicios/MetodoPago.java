package CapaServicios;

public interface MetodoPago {
    void procesarPago(double monto);

    public String prepararPasarela(double monto, String producto);
}