/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

import CapaExcepciones.Excepciones;
import java.util.regex.Pattern;

public class Validador {
    
    private static final String REGEX_SOLO_LETRAS = "^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ\\s]+$";

    public static void validarNombreUsuario(String username) throws Excepciones {
        // Validacion de numeros
        if (username.matches(".*\\d.*")) {
            throw new Excepciones("El nombre de usuario no puede contener números.");
        }
        
        // Validacion de caracteres escpeciales
        if (!Pattern.matches(REGEX_SOLO_LETRAS, username)) {
            throw new Excepciones("El nombre contiene caracteres no permitidos.");
        }
    }
    
public static void validarPrecioAuto(double precio) throws Excepciones {
        if (precio <= 0) {
            throw new Excepciones("El precio del auto debe ser mayor a cero.");
        }
        if (precio > 1000000) { // Regla de negocio: tope de 1 millón de USD
            throw new Excepciones("El precio excede el límite permitido por la plataforma.");
        }
    }
}