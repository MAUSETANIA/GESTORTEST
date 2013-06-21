/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen.Datos;

/**
 *
 * @author modelo
 */
import java.io.Serializable;
import org.jdom.Element;
public interface PersistenteXML extends Serializable{
    public Element getNodoXML();
    public boolean setNodoXML(Element node);
}
