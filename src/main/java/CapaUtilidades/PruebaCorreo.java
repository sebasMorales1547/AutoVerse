/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

public class PruebaCorreo {

    public static void main(String[] args) {
        EmailServicio emailServicio = new EmailServicio();

        String destinatario = EmailConfig.CORREO_REMITENTE;
        String asunto       = "✅ Prueba de correo — Autoverse";
        String cuerpoHtml   = "<h2 style='color:#2c3e50'>¡Funciona!</h2>"
                            + "<p>El servicio de correo de <strong>Autoverse</strong> "
                            + "está configurado correctamente.</p>";

        System.out.println("Enviando correo a: " + destinatario);

        try {
            emailServicio.enviar(destinatario, asunto, cuerpoHtml);
            System.out.println("✅ ¡Correo enviado! Revisa tu bandeja.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}