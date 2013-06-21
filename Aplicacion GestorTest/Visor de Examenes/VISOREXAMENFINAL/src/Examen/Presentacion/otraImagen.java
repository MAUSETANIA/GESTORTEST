/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen.Presentacion;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Angel Cespedes
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
