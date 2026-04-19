package CapaServicios;

public interface MetodoPago {
    void procesarPago(double monto);
}

class PagoEfectivo implements MetodoPago {
    public void procesarPago(double monto) { System.out.println("Pago en efectivo: " + monto); }
}

class PagoDebito implements MetodoPago {
    public void procesarPago(double monto) { System.out.println("Cargo a cuenta débito: " + monto); }
}

class PagoCredito implements MetodoPago {
    public void procesarPago(double monto) { System.out.println("Cargo a tarjeta crédito: " + monto); }
}