/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examen.Negocio;

import Examen.Datos.PersistenteXML;
import java.util.List;
import java.util.Vector;
import org.jdom.Element;

/**
 *
 * @author modelo
 */
public class Pregunta implements PersistenteXML {

    public static int MULTIPLE = 1;
    public static int SINGLE = 2;
    public static int COMPLETAR= 3;
    private Vector<Respuesta> respuestas = new Vector<Respuesta>();
    private float puntos;
    private String texto;
    private String imagen;
    private int tipo;

    public Pregunta() {
        tipo = SINGLE;
    }

    public Vector<Respuesta> getRespuestas() {
        return respuestas;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public float getPuntos() {
        return puntos;
    }

    public void setPuntos(float puntos) {
        this.puntos = puntos;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Element getNodoXML() {
        Element node = new Element("pregunta");
        node.setAttribute("tipo", Integer.toString(tipo));
        Element e = new Element("puntos");
        e.setText(Float.toString(puntos));
        node.addContent(e);

        e = new Element("texto");
        e.setText(texto);
        node.addContent(e);

        e = new Element("imagen");
        e.setText(imagen);
        node.addContent(e);

        for (int i = 0; i < respuestas.size(); i++) {
            node.addContent(((PersistenteXML) respuestas.get(i)).getNodoXML());
        }
        return node;
    }

    public boolean setNodoXML(Element node) {
        if (node == null) {
            return false;
        }

        setTipo(Integer.parseInt(node.getAttributeValue("tipo")));
        setPuntos(Float.parseFloat(node.getChildText("puntos")));
        setTexto(node.getChildText("texto"));
        setImagen(node.getChildText("imagen"));
        List children = node.getChildren("respuesta");
        for (int i = 0; i < children.size(); i++) {
            Object object = children.get(i);
            Respuesta p = new Respuesta();
            p.setNodoXML((Element) object);
            respuestas.add(p);
        }
        return true;
    }

    @Override
    public String toString() {
        return texto;
    }
    
 }
    