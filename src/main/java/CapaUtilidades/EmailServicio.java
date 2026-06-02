/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailServicio {

    private Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", EmailConfig.SMTP_HOST);
        props.put("mail.smtp.port", EmailConfig.SMTP_PORT);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        EmailConfig.CORREO_REMITENTE,
                        EmailConfig.CLAVE_APP);
            }
        });
    }

    public void enviar(String destinatario, String asunto, String cuerpoHtml) throws Exception {

        System.out.println("=== INICIANDO ENVÍO DE CORREO ===");
        System.out.println("Destinatario: " + destinatario);

        Session session = crearSesion();

        Message mensaje = new MimeMessage(session);

        mensaje.setFrom(new InternetAddress(
                EmailConfig.CORREO_REMITENTE,
                EmailConfig.NOMBRE_SISTEMA));

        mensaje.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destinatario));

        mensaje.setSubject(asunto);
        mensaje.setContent(cuerpoHtml, "text/html; charset=utf-8");

        Transport.send(mensaje);

        System.out.println("=== CORREO ENVIADO CORRECTAMENTE ===");
    }
}