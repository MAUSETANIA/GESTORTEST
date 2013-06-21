/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen.Negocio;

import Examen.Datos.PersistenteXML;
import org.jdom.Element;

/**
 *
 * @author modelo
 */
public class Respuesta implements PersistenteXML {

    private String respuesta;
    private boolean correcto;
    private boolean marcado;
    private String completado;

    public Respuesta(String respuesta) {
        this.respuesta = respuesta;
        completado="";
    }

    public Respuesta() {
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

    public Element getNodoXML() {
        Element node = new Element("respuesta");
        node.setText(respuesta);
        node.setAttribute("correcto", Boolean.toString(correcto));
        node.setAttribute("marcado", Boolean.toString(marcado));
        node.setAttribute("completado", completado);
        return node;
    }

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
    