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


public class Runner {   
    public static void main(String[] args)  {
        FormDatosAlumno form= new FormDatosAlumno(null,true);
                    form.setVisible(true);

    }
}
