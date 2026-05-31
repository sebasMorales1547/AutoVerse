package view;

import java.awt.Desktop;
import java.io.File;

public class Main {

    public static void main(String[] args) {

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
