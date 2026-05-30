/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaModelo;

public class Usuarios {
    private int cedula;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String correo;
    private String telefono;
    private String fotoRuta;
    private RolUsuario rol; 

    public Usuarios() {}

    // Constructor completo
    public Usuarios(String nombre, String contrasena, String apellido,
                    String email, int cedula, String telefono, RolUsuario rol) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.correo = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    // Constructor simple
    public Usuarios(String nombre, String contrasena, String correo) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = RolUsuario.COMPRADOR; 
   }

    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFotoRuta() { return fotoRuta; }
    public void setFotoRuta(String fotoRuta) { this.fotoRuta = fotoRuta; }

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public boolean esAdmin()    { return rol == RolUsuario.ADMIN; }
    public boolean esVendedor() { return rol == RolUsuario.VENDEDOR; }
    public boolean esComprador(){ return rol == RolUsuario.COMPRADOR; }
}