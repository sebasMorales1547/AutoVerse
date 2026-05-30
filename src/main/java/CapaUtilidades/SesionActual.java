/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

import CapaModelo.Usuarios;

public class SesionActual {
    private static Usuarios usuarioActivo = null;

    private SesionActual() {}

    public static void iniciarSesion(Usuarios usuario) {
        usuarioActivo = usuario;
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
    }

    public static Usuarios getUsuario() {
        return usuarioActivo;
    }

    public static boolean haySesionActiva() {
        return usuarioActivo != null;
    }

    public static boolean esAdmin() {
        return haySesionActiva() && usuarioActivo.esAdmin();
    }

    public static boolean esVendedor() {
        return haySesionActiva() && usuarioActivo.esVendedor();
    }

    public static boolean esComprador() {
        return haySesionActiva() && usuarioActivo.esComprador();
    }
}