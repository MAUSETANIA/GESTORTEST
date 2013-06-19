package examen.persistencia;

import examen.negocio.Examen;
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


/**
 *
 * @author Flavia Choque C.
 */
/*
 * Esta clase contendra las operaciones principales para abrir y guardar los examenes 
 */
public class ArchivoXML {
    
/*
 * Permitira abrir un documento xml devolviendo un examen
 * @return un objeto de tipo Examen 
 */
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
/*
 * Permitira abrir un documento xml dado una direccion,devolviendo un examen 
 * @return un objeto de tipo Examen 
 */
    public static Examen abrirXML(String path) {
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
        return null;
    }
/*
 * Permitira crear un documento xml a partir de un examen ,dando su
 * direccion y el autor del examen
 */
    public static void guardarXML(Examen examen, String path, String autor) {
        try {
            File file = null;
            if (path.toLowerCase().lastIndexOf(".test") == path.length() - 5) {
                file = new File(path);
            } else {
                file = new File(path + ".test");
            }
            Comment comment = null;

            String s = "MiTest\n"
                    + "Version 1.1.0  e-mail:IngenieriaDeSoftware@uagrm.edu.bo\n"
                    + "Fecha de creación: " + DateFormat.getDateInstance().format(new Date()) + "\n"
                    + "Descripcion: Diseñador y editor de test ";
            if (comment == null) {
                comment = new Comment(s);
            }
            Document doc = new Document();
            doc.addContent(comment);
            doc.setRootElement(examen.getNodoXML());
            Format format = Format.getPrettyFormat();
            XMLOutputter out = new XMLOutputter(format);
            if (path.toLowerCase().lastIndexOf(".test") == path.length() - 5) {
                out.output(doc, new FileOutputStream(path));
            } else {
                out.output(doc, new FileOutputStream(path + ".test"));
            }

        } catch (IOException ex) {
        }
    }    
/*
 * Permitira crear una extension (.test) para el examen que se guardara
 */
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
                    out.output(doc, new FileOutputStream(path));
                } else {
                    out.output(doc, new FileOutputStream(path+ ".test"));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
/*
 * Clase que permitira adicionar la extension creada para el examen en un
 * componente de Swing
 */
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
