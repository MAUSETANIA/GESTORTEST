package examen.negocio;

import examen.persistencia.PersistenteXML;
import java.util.List;
import java.util.Vector;
import org.jdom.Element;

/**
 *
 * @author Flavia Choque C.
 */
/*
 * Esta clase permitira guardar las preguntas de los examenes
 */
public class Pregunta implements PersistenteXML {
    /*
     * Atributos de la clase Pregunta
     */
    public static int MULTIPLE = 1;
    public static int SINGLE = 2;
    public static int COMPLETAR = 3;
    private Vector<Respuesta> respuestas = new Vector<Respuesta>();
    private float puntos;
    private String texto;
    private String imagen;
    private int tipo;
    
    /*
     * Constructor 
     */
    public Pregunta() {
        tipo = SINGLE;
    }
    /*
     * Modificadores y selectores
     */
    public void addRespuesta(Respuesta resp) {
        respuestas.add(resp);
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
    /*
    * Permitira crear un objeto Element a traves
    * de una Pregunta
    */
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
   /*
 * Permitira asignar a las variables de Preguntas un estado a traves
 * de un objeto de tipo Element
 */
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
    /*
    * Permitira poner un texto a una pergunta a traves de un String 
    */
    public void PonerTexto(int tipo,String s){
     texto=s;
     if(tipo==COMPLETAR){
       
        String tc=new String();
        tc=s;
        if (tc.charAt(tc.length()-1)!=' '){
            tc=tc+' ';
        }

        String resp;
            int ini,fin;
            int i=0;
            while (i < tc.length()) {
                if(tc.charAt(i)=='*'){
                 ini=i+1;
                 fin=tc.indexOf(' ',ini);
                 resp=tc.substring(ini,fin);
                 System.out.print(resp);
                 Respuesta r=new Respuesta();
                 r.setRespuesta(resp);
                 respuestas.addElement(r);
                 i= fin;
                }
               i=i+1;
            }
     }
   }


    @Override
    public String toString() {
        return texto;
    }
}
