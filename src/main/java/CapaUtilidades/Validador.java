/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

import CapaExcepciones.Excepciones;
import java.util.regex.Pattern;

public class Validador {

    private static final String REGEX_SOLO_LETRAS = "^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ\\s]+$";
    private static final String REGEX_PLACA = "^[A-Z]{3}[0-9]{3}$"; 

    public static void validarNombreUsuario(String username) throws Excepciones {
        if (username.matches(".*\\d.*"))
            throw new Excepciones("El nombre de usuario no puede contener números.");
        if (!Pattern.matches(REGEX_SOLO_LETRAS, username))
            throw new Excepciones("El nombre contiene caracteres no permitidos.");
    }

    public static void validarPrecioAuto(double precio) throws Excepciones {
        if (precio <= 0)
            throw new Excepciones("El precio del auto debe ser mayor a cero.");
        if (precio > 900_000_000)
            throw new Excepciones("El precio excede el límite permitido por la plataforma ($900.000.000).");
    }

    public static void validarPlaca(String placa) throws Excepciones {
        if (placa == null || !Pattern.matches(REGEX_PLACA, placa.toUpperCase()))
            throw new Excepciones("La placa no tiene un formato válido. Ejemplo: ABC123.");
    }

    public static void validarAño(int año) throws Excepciones {
        if (año < 1900 || año > 2025)
            throw new Excepciones("El año del vehículo debe estar entre 1900 y 2025.");
    }
    
    public static void validarCorreo(String correo) throws Excepciones {
        if (correo == null || !correo.contains("@") || !correo.contains("."))
            throw new Excepciones("El correo electrónico no tiene un formato válido.");
    }
}