/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.presentacion.disenador;

import java.io.*;

/**
 *
 * @author Lorena
 */
public class otraImagen {
    public static String cadenaImagen(String pathname) throws FileNotFoundException, IOException {
        File file = new File(pathname);
        byte[] imageData = new byte[(int) file.length()];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        in.readFully(imageData);
        in.close();
        String imageDataString = new sun.misc.BASE64Encoder().encode(imageData);
        return imageDataString;
    }

    public static byte[] imageString(String imageDataString) throws IOException {
        byte[] imageData = new sun.misc.BASE64Decoder().decodeBuffer(imageDataString);
        return imageData;
    }    
}
