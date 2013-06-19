/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.negocio;

import examen.persistencia.PersistenteXML;
import org.jdom.Element;

/**
 *
 * @author Lorena
 */
/*
 * Permitira crear las Asignaturas para los examenes
 */
public class Asignatura implements PersistenteXML {
    
    /*
     * Atributos
     */
    private String sigla;
    private String descripcion;
    
    /*
     * Constructor de Asignatura
     */
    public Asignatura() {
    sigla=descripcion="";
    }

    public Asignatura(String sigla, String descripcion) {
        this.sigla = sigla;
        this.descripcion = descripcion;
    }
    /*
     * Modificadores y Selectores
     */
    public String getDescripcion() {
        return descripcion;
    }

    public String getSigla() {
        return sigla;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    /*
    * Permitira crear un objeto Element a traves
    * de una Asignatura
    */
    public Element getNodoXML() {
        Element node = new Element("Asignatura");
        node.setAttribute("sigla", sigla);
        node.setAttribute("descripcion",descripcion);

        return node;
    }
    /*
    * Permitira asignar a las variables de las Asignaturas un estado a traves
    * de un objeto de tipo Element
    */
    public boolean setNodoXML(Element node) {
        if (node == null) {
            return false;
        }
        setSigla(node.getAttributeValue("sigla"));
        setDescripcion(node.getAttributeValue("descripcion"));

        return true;
    }


}
