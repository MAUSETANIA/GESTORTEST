/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.negocio;

import examen.persistencia.PersistenteXML;
import org.jdom.Element;

/**
 *
 * @author Flavia Choque C.
 */
/*
 * Esta clase permitira guardar las respuestas de las pregunta
 * creadas en los examenes
 */
public class Respuesta implements PersistenteXML {

    /*
     * Atributos de la clase Respuesta
     */
    private String respuesta;
    private boolean correcto;
    private boolean marcado;
    private String completado;
    /*
    * Modificadores y Selectores
    */
    public Respuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Respuesta() {
        respuesta=completado="";
        correcto=marcado=false;
    }

    public void setCompletado(String completado) {
        this.completado = completado;
    }

    public String getCompletado() {
        return completado;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
/*
 * Permitira asignar a las variables de un objeto Element a traves
 * de un objeto de Respuesta
 */
    public Element getNodoXML() {
        Element node = new Element("respuesta");
        node.setText(respuesta);
        node.setAttribute("correcto", Boolean.toString(correcto));
        node.setAttribute("marcado", Boolean.toString(marcado));
        node.setAttribute("completado", completado.toString());
        return node;
    }
/*
 * Permitira asignar a las variables de Respuetas un estado a traves
 * de un objeto de tipo Element
 */
    public boolean setNodoXML(Element node) {
        if (node == null) {
            return false;
        }
        respuesta = node.getText();
        correcto = Boolean.parseBoolean(node.getAttributeValue("correcto"));
        marcado = Boolean.parseBoolean(node.getAttributeValue("marcado"));
        completado=node.getAttributeValue("completado");
        return true;
    }
}

