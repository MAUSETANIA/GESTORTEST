/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examen.negocio;

import java.util.List;

/**
 *
 * @author Tania
 */
public class Calificacion {
    
    public static int devolverPuntaje(Pregunta pregunta){
        List<Respuesta> respuestas= pregunta.getRespuestas();

        if(pregunta.getTipo()==1||pregunta.getTipo()==2){
            for(int i=0;i<respuestas.size();i++){
                if(respuestas.get(i).isCorrecto()!=respuestas.get(i).isMarcado()){
                return 0;
            }
         }

        }else{
              for(int i=0;i<respuestas.size();i++){
                if(!respuestas.get(i).getCompletado().equals(respuestas.get(i).getRespuesta())){
                    return 0;
                }
            }
        }
        return (int)pregunta.getPuntos();
        
    }
    
    
    public static int notaExamen(Examen examen){
          List<Pregunta> preguntas= examen.getPreguntas();
        int suma=0;
        for(int i=0;i<preguntas.size();i++){
            suma= suma+ devolverPuntaje(preguntas.get(i));
        }
        return suma;
        
    }
    
    public static int totalExamen(Examen examen){
         List<Pregunta> preguntas= examen.getPreguntas();
        int suma=0;
        for(int i=0;i<preguntas.size();i++){
            suma= suma+ (int)preguntas.get(i).getPuntos();
        }
        return suma;
        
    }
    
     public static int cantidadSimplesExamen(Examen examen){
        List<Pregunta> preguntas= examen.getPreguntas();
        int c=0;
        for (int i = 0; i < preguntas.size(); i++) {
            if(preguntas.get(i).getTipo()==1){
                c++;
            }
        }
        return  c;
         
     }
     
     public static int cantidadMultiplesExamen(Examen examen){
        List<Pregunta> preguntas= examen.getPreguntas();
        int c=0;
        for (int i = 0; i < preguntas.size(); i++) {
            if(preguntas.get(i).getTipo()==2){
                c++;
            }
        }
        return  c;    
         
     }
    
}
