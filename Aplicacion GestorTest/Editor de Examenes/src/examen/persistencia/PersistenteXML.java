/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package examen.persistencia;

import java.io.Serializable;
import org.jdom.Element;

/**
 *
 * @author Flavia Choque C.
 */
/*
 * Clase Persistencia
 */
public interface PersistenteXML extends Serializable{
    public Element getNodoXML();
    public boolean setNodoXML(Element node);
}
