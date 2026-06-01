package view;

import java.awt.Desktop;
import java.io.File;

import CapaUtilidades.Conexion;

public class Main {

    public static void main(String[] args) {

        

        try {

            Conexion.getConexion();

            System.out.println("Conexion exitosa");

        } catch (Exception e) {

            System.out.println("Error de conexion");

            e.printStackTrace();
        }

        try {

            File archivo = new File("AutoVerse\\src\\main\\java\\view\\index.html");

            Desktop.getDesktop().browse(
                    archivo.toURI()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
