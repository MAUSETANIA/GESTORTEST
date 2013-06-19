/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.negocio;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tania
 */
public class AdministradorDeNotas {
    
    List<Examen> examenes;
    
   
   /**
    * 
    * @param e 
    */ 
     public void AdicionarExamen(Examen e){
        examenes.add(e);
    }
/**
 * 
 */
    public void clearExamenes(){
        examenes.clear();
    }

    public AdministradorDeNotas(){
        examenes= new LinkedList<Examen>();
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public int cantidadAprobados(){
        return 0;
        
    }
    
     public int cantidadReprobados(){
        return 0;
        
     }
     
}
