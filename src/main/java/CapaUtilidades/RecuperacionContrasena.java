/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RecuperacionContrasena {

    // correo = codigo y tiempo de exp...
    private static final Map<String, long[]> codigos = new HashMap<>();
    private static final long EXPIRACION_MS = 15 * 60 * 1000; // 15 minutos

    public static String generarCodigo(String correo) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        long expira = System.currentTimeMillis() + EXPIRACION_MS;
        codigos.put(correo, new long[]{ Long.parseLong(codigo), expira });
        return codigo;
    }
    public static boolean verificarCodigo(String correo, String codigoIngresado) {
        if (!codigos.containsKey(correo)) return false;

        long[] datos = codigos.get(correo);
        boolean noExpirado = System.currentTimeMillis() < datos[1];
        boolean coincide   = String.valueOf((long) datos[0]).equals(codigoIngresado.trim());

        if (coincide && noExpirado) {
            codigos.remove(correo); // uso unico
            return true;
        }
        return false;
    }
}