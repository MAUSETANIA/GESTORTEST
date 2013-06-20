/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.presentacion.administrarNotas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Tania
 */
public class FileRenderer extends JLabel implements ListCellRenderer {
    
     private final int TYPE_UNKNOWN = 0;
    private final int TYPE_AUDIO = 1;
    private final int TYPE_DOCUMENT = 2;
    private final int TYPE_IMAGE = 3;
    private final int TYPE_SOURCE = 4;
    private final int TYPE_ZIP = 5;
    private final int TYPE_RESP = 6;

    private ImageIcon icons[];
    private int iconSize;
    
     /** Creates a new instance of FileRenderer */
    public FileRenderer(int iconSize) {
        this.iconSize = iconSize;
        icons = new ImageIcon[7];
        icons[TYPE_UNKNOWN] = getImageIcon("mime-empty.png");
        icons[TYPE_AUDIO] = getImageIcon("audio.png");
        icons[TYPE_DOCUMENT] = getImageIcon("document.png");
        icons[TYPE_IMAGE] = getImageIcon("image.png");
        icons[TYPE_SOURCE] = getImageIcon("source_java.png");
        icons[TYPE_ZIP] = getImageIcon("zip.png");
        icons[TYPE_RESP] = getImageIcon("Resp.png");
    }

    public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
        File f = (File) value;
        String fileName = "";
        int type = extensionType(f);
        setIcon(icons[type]);
        setOpaque(true);
        if (isSelected) {
            setBackground(new Color(0, 200, 100, 150));
            setForeground(Color.BLACK);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        setText(f.getName());
        return this;
    }

    public int extensionType(File f) {
        String name = f.getName();
        String ext = "";
        try {
            ext = name.substring(name.lastIndexOf('.') + 1, name.length());
        } catch (Exception e) {
        }
        /** Audio */
        if (ext.equals("mp3")) {
            return TYPE_AUDIO;
        }
        if (ext.equals("wav")) {
            return TYPE_AUDIO;
        }
        if (ext.equals("au")) {
            return TYPE_AUDIO;
        }
        /** Documentos */
        if (ext.equals("doc")) {
            return TYPE_DOCUMENT;
        }
        if (ext.equals("rtf")) {
            return TYPE_DOCUMENT;
        }
        if (ext.equals("odt")) {
            return TYPE_DOCUMENT;
        }
        if (ext.equals("xls")) {
            return TYPE_DOCUMENT;
        }
        if (ext.equals("ods")) {
            return TYPE_DOCUMENT;
        }
        if (ext.equals("doc")) {
            return TYPE_DOCUMENT;
        }
        if (ext.equals("txt")) {
            return TYPE_DOCUMENT;
        }
        /** Imagenes */
        if (ext.equals("jpg")) {
            return TYPE_IMAGE;
        }
        if (ext.equals("gif")) {
            return TYPE_IMAGE;
        }
        if (ext.equals("bmp")) {
            return TYPE_IMAGE;
        }
        /** Fuentes */
        if (ext.equals("java")) {
            return TYPE_SOURCE;
        }
        if (ext.equals("class")) {
            return TYPE_SOURCE;
        }
        if (ext.equals("cpp")) {
            return TYPE_SOURCE;
        }
        /** Comprimidos */
        if (ext.equals("zip")) {
            return TYPE_ZIP;
        }
        if (ext.equals("rar")) {
            return TYPE_ZIP;
        }
        if (ext.equals("7z")) {
            return TYPE_ZIP;
        }
        if (ext.equals("resp")) {
            return TYPE_RESP;
        }
        return TYPE_UNKNOWN;
    }

    public ImageIcon getImageIcon(String fileName) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.net.URL imgURL = DirectoryRenderer.class.getResource(fileName);
        if (imgURL != null) {
            Image img = tk.getImage(imgURL);
            return new ImageIcon(
                    img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        } else {
            return null;
        }
    }
}
