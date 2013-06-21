/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

/**
 *
 * @author modelo
 * @version v1.2
 */

import Examen.Presentacion.FormDatosAlumno;
import Examen.Presentacion.FormPrincipal;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Runner {   
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        /*if (args == null || args.length < 1) {
            return;
        }*/
        //final String file = args[0];
        //Examen examen = ArchivoXML.abrirXML("D:/PROYECTOS/PROYECTO%FINAL/PROYECTOS/VISOR%EXAMEN%FINAL/dist/prueba.test");
        //UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //JFrame.setDefaultLookAndFeelDecorated(true);
                    UIManager.setLookAndFeel(new BernsteinLookAndFeel());
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    //Examen examen = ArchivoXML.abrirXML(file);
                    FormDatosAlumno form= new FormDatosAlumno(null,true);
                    form.setVisible(true);
                    /*Examen examen = ArchivoXML.abrirXML("E:/Lorena.test");
                    FormPrincipal form = new FormPrincipal();
                    form.setExamen(examen);
                    form.pack();
                    form.setVisible(true);*/
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
     }