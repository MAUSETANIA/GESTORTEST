/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen.Datos;

/**
 *
 * @author modelo
 */
import Examen.Negocio.Examen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
public class ArchivoXML {

       public static Examen abrirXML() {
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new TestFilter());
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
            try {
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(new FileInputStream(path));
                Element root = doc.getRootElement();
                Examen examen = new Examen();
                examen.setNodoXML(root);
                return examen;
            } catch (JDOMException ex) {
                Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Examen abrirXML(String path) {
        try {
            System.out.println(path);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(path));
            Element root = doc.getRootElement();
            Examen examen = new Examen();
            examen.setNodoXML(root);
            return examen;
        } catch (JDOMException ex) {
            Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void guardarXML(Examen examen, String path) {
        try {
            File file = null;
            if (path.toLowerCase().lastIndexOf(".resp") == path.length() - 5) {
                file = new File(path+examen.getAlumno());
            } else {
                file = new File(path+examen.getAlumno() + ".resp");
            }
            Comment comment = null;
            if (file.exists() && file.isFile()) {
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(new FileInputStream(file));
            }
            String s = "MiRespuesta\n"
                    + "Version 1.1.0  e-mail:IngenieriaDeSoftware\n"
                    + "Alumno: " + examen.getAlumno() + "\n"
                    + "Fecha de examen: " + DateFormat.getDateInstance().format(new Date()) + "\n"
                    + "Descripcion: Respuestas del test ";
            if (comment == null) {
                comment = new Comment(s);
            }
            Document doc = new Document();
            doc.addContent(comment);
            doc.setRootElement(examen.getNodoXML());
            Format format = Format.getPrettyFormat();
            XMLOutputter out = new XMLOutputter(format);
            if (path.toLowerCase().lastIndexOf(".resp") == path.length() - 5) {
                out.output(doc, new FileOutputStream(path + examen.getAlumno()+".resp"));
            } else {
                out.output(doc, new FileOutputStream(path + examen.getAlumno()+".resp"));
            }

        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }
    }

    public static void guardarXML(Examen examen) {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new TestFilter());

            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                String title = chooser.getSelectedFile().getName();
                //examen
                //e.setId(id);
                Document doc = new Document(examen.getNodoXML());
                Format format = Format.getPrettyFormat();
                XMLOutputter out = new XMLOutputter(format);
                if (path.toLowerCase().lastIndexOf(".test") == path.length() - 5) {
                    out.output(doc, new FileOutputStream(path + ".test"));
                } else {
                    out.output(doc, new FileOutputStream(path));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class TestFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f.isFile()) {
            int n = f.getName().lastIndexOf(".test");
            if (f.getName().length() - n == 4) {
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        return "Archivo Tipo Examen(*.test)";
    }
}
