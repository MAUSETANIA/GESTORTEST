package examen.negocio;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import examen.persistencia.PersistenteXML;
import java.util.List;
import java.util.Vector;

import org.jdom.Element;

/**
 *
 * @author Flavia Choque C.
 */
/*
 * Clase Principal que permitira crear los examenes
 */
public class Examen implements PersistenteXML {
    /*
     * Atributos de la clase
     */
    private String descripcion;
    private String alumno,registro, grupo, asignatura,profesor;
    private int tiempo;
    private Vector<Pregunta> preguntas = new Vector<Pregunta>();
    private float t=0;

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }
    /*
     * Constructor
     */
    public Examen() {
        descripcion = "examen";
        alumno = grupo = registro= asignatura= profesor= "";
        tiempo = 1;
    }
    /*
     * Modificadores y selectores
     */
    public String getDescripcion() {
        return descripcion;
    }

    public String getAlumno() {
        return alumno;
    }

    public String getRegistro() {
        return registro;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public int getTiempo() {
        return tiempo;
    }

    public Vector<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void addPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void addPregunta(int i, Pregunta pregunta) {
        preguntas.add(i, pregunta);
    }

    public void eliminarPreguntas() {
        preguntas.clear();
    }
    
    public void setPregunta(int n, Pregunta pregunta) {
        preguntas.set(n, pregunta);
    }

    public void setAllPreguntas(Vector<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    /*
    * Permitira crear un objeto Element a traves
    * de un Examen
    */
    public Element getNodoXML() {
        Element node = new Element("examen");
        node.setAttribute("tiempo", Integer.toString(tiempo));
        Element e = new Element("nombre");
        e.setText(descripcion);
        node.addContent(e);

        e = new Element("alumno");
        e.setText(alumno);
        node.addContent(e);

        e = new Element("registro");
        e.setText(registro);
        node.addContent(e);

        e = new Element("grupo");
        e.setText(grupo);
        node.addContent(e);

        e = new Element("profesor");
        e.setText(profesor);
        node.addContent(e);

        e = new Element("asignatura");
        e.setText(asignatura);
        node.addContent(e);

        for (int i = 0; i < preguntas.size(); i++) {
            node.addContent(((PersistenteXML) preguntas.get(i)).getNodoXML());
        }
        return node;
    }
    /*
    * Permitira asignar a las variables de los Examenes un estado a traves
    * de un objeto de tipo Element
    */
    public boolean setNodoXML(Element node) {
        if (node == null) {
            return false;
        }
        preguntas.clear();
        setTiempo(Integer.parseInt(node.getAttributeValue("tiempo")));
        setDescripcion(node.getChildText("nombre"));
        setAlumno(node.getChildText("alumno"));
        setAlumno(node.getChildText("registro"));
        setGrupo(node.getChildText("grupo"));
        setProfesor(node.getChildText("profesor"));
        setAsignatura(node.getChildText("asignatura"));
        
        List children = node.getChildren("pregunta");
        for (int i = 0; i < children.size(); i++) {
            Object object = children.get(i);
            Pregunta p = new Pregunta();
            p.setNodoXML((Element) object);
            preguntas.add(p);
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }
         
       
}
