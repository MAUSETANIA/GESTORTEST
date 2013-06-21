/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package examen.PDF;

import java.awt.Color;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import examen.negocio.Examen;
import examen.negocio.Pregunta;
import examen.negocio.Respuesta;
import java.io.IOException;
import java.util.Vector;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import examen.negocio.AdministradorDeNotas;
import examen.negocio.Calificacion;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
/**
 *
 * @author modelo
 */
/**
 * @version 1.2
 * @class :esta clase obtiene un reporte de los examenes
 * realizados en el test
 */
public class ExamenPDF {
    
    public ExamenPDF() {
    }

    public static String cadenaImagen(String pathname) throws FileNotFoundException, IOException {
        File file = new File(pathname);
        byte[] imageData = new byte[(int) file.length()];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        in.readFully(imageData);
        in.close();
        String imageDataString = new sun.misc.BASE64Encoder().encode(imageData);
        return imageDataString;
    }

    public static byte[] imageString(String imageDataString) throws IOException {
        byte[] imageData = new sun.misc.BASE64Decoder().decodeBuffer(imageDataString);
        return imageData;
    }

    public void pdfExamen(Examen examen, String direccion) throws IOException {
        Document documento = new Document(PageSize.A5);
        Vector<Pregunta> preguntas = examen.getPreguntas();
        Vector<Respuesta> respuestas;
        int nump = preguntas.size();
        int numr = 0;
        try {
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(direccion));
            writer.setPdfVersion(PdfWriter.VERSION_1_5);
            writer.setViewerPreferences(PdfWriter.PageModeUseOC);

            documento.open();
            Font font = FontFactory.getFont("arial", 22, Font.BOLD, Color.BLUE);
            Paragraph p = new Paragraph(examen.getDescripcion(), font);
            p.setAlignment(Element.ALIGN_CENTER);
            documento.add(p);
            documento.add(new Paragraph("         "));
            documento.add(new Paragraph("Materia: " + examen.getAsignatura(),
                    FontFactory.getFont("arial", // fuente
                    14, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("Grupo: " + examen.getGrupo(),
                    FontFactory.getFont("arial", // fuente
                    14, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("Alumno: " + examen.getAlumno(),
                    FontFactory.getFont("arial", // fuente
                    14, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("         "));
            for (int i = 0; i < nump; i++) {
                if (preguntas.get(i).getTipo() == 1 && preguntas.get(i).getImagen().equals("")) { //MULTIPLE = 1
                    char[] indice = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
                    documento.add(new Paragraph("  " + (i + 1) + ". " + preguntas.get(i).getTexto() + " (Multiple)"));
                    numr = preguntas.get(i).getRespuestas().size();
                    respuestas = preguntas.get(i).getRespuestas();
                    for (int k = 0; k < numr; k++) {
                        documento.add(new Paragraph("        " + indice[k] + ". " + respuestas.get(k).getRespuesta()));
                    }
                }
                if (preguntas.get(i).getTipo() == 2 && preguntas.get(i).getImagen().equals("")) { //SINGLE = 2
                    char[] indice = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
                    documento.add(new Paragraph("  " + (i + 1) + ". " + preguntas.get(i).getTexto() + " (Simple)"));
                    numr = preguntas.get(i).getRespuestas().size();
                    respuestas = preguntas.get(i).getRespuestas();
                    for (int k = 0; k < numr; k++) {
                        documento.add(new Paragraph("        " + indice[k] + ". " + respuestas.get(k).getRespuesta()));
                    }
                }

                if (preguntas.get(i).getTipo() == 1 && !preguntas.get(i).getImagen().equals("")) { //MULTIPLE = 1, con imagen
                    char[] indice = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
                    documento.add(new Paragraph("  " + (i + 1) + ". " + preguntas.get(i).getTexto() + " (Multiple)"));
                    Image img = Image.getInstance(imageString(preguntas.get(i).getImagen()));
                    img.setAlignment(Chunk.ALIGN_MIDDLE);
                    documento.add(img);
                    numr = preguntas.get(i).getRespuestas().size();
                    respuestas = preguntas.get(i).getRespuestas();
                    for (int k = 0; k < numr; k++) {
                        documento.add(new Paragraph("        " + indice[k] + ". " + respuestas.get(k).getRespuesta()));
                    }
                }
                if (preguntas.get(i).getTipo() == 2 && !preguntas.get(i).getImagen().equals("")) { //SINGLE = 2, con imagen
                    char[] indice = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
                    documento.add(new Paragraph("  " + (i + 1) + ". " + preguntas.get(i).getTexto() + " (Simple)"));
                    Image img = Image.getInstance(imageString(preguntas.get(i).getImagen()));
                    img.setAlignment(Chunk.ALIGN_MIDDLE);
                    documento.add(img);
                    numr = preguntas.get(i).getRespuestas().size();
                    respuestas = preguntas.get(i).getRespuestas();
                    for (int k = 0; k < numr; k++) {
                        documento.add(new Paragraph("        " + indice[k] + ". " + respuestas.get(k).getRespuesta()));
                    }
                }
            }
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        documento.close();
    }

    public static void reporteNotas(List<Examen> examen, String direccion) {
        AdministradorDeNotas adm = new AdministradorDeNotas();
        adm.setExamenes(examen);
        Document documento = new Document(PageSize.A5);
        Vector<Pregunta> preguntas = examen.get(0).getPreguntas();
        int nume = examen.size();
        System.out.println(nume);
        try {
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(direccion));
            writer.setPdfVersion(PdfWriter.VERSION_1_5);
            writer.setViewerPreferences(PdfWriter.PageModeUseOC);

            documento.open();
            Font font = FontFactory.getFont("arial", 22, Font.BOLD, Color.BLUE);
            Paragraph p = new Paragraph("Administracion de Notas", font);
            p.setAlignment(Element.ALIGN_CENTER);
            documento.add(p);
            documento.add(new Paragraph("         "));

            documento.add(new Paragraph("Descripcion: " + examen.get(0).getDescripcion(),
                    FontFactory.getFont("arial", // fuente
                    8, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("Materia: " + examen.get(0).getAsignatura(),
                    FontFactory.getFont("arial", // fuente
                    8, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("Grupo: " + examen.get(0).getGrupo(),
                    FontFactory.getFont("arial", // fuente
                    8, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("Cantidad de Aprobados: " + adm.cantidadAprobados(),
                    FontFactory.getFont("arial", // fuente
                    8, // tamaño
                    Font.BOLD)));
            documento.add(new Paragraph("Cantidad de Reprobados: " + adm.cantidadReprobados(),
                    FontFactory.getFont("arial", // fuente
                    8, // tamaño
                    Font.BOLD)));            
            documento.add(new Paragraph("         "));
            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell(new Paragraph("    Numero    ", FontFactory.getFont("arial", 14, Font.BOLD)));
            tabla.addCell(new Paragraph("   Registro  ", FontFactory.getFont("arial", 14, Font.BOLD)));
            tabla.addCell(new Paragraph("    Nota    ", FontFactory.getFont("arial", 14, Font.BOLD)));
            for (int i = 0; i < nume; i++) {
                tabla.addCell("        " + (i + 1) + ". ");
                tabla.addCell(new Paragraph("" + examen.get(i).getAlumno()));
                tabla.addCell(new Paragraph("        " + Calificacion.notaExamen(examen.get(i))));
            }
            documento.add(tabla);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        documento.close();
    }

    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException {
        ExamenPDF p = new ExamenPDF();
       

        Examen exa = new Examen();
        exa.setDescripcion("Examen de Ingles Primer Parcial");
        exa.setAsignatura("Ingles");
        exa.setGrupo("SH");
        exa.setAlumno("");
        Pregunta pr = new Pregunta();
        Respuesta r1 = new Respuesta();
        Respuesta r2 = new Respuesta();
        Respuesta r3 = new Respuesta();
        Respuesta r4 = new Respuesta();
        pr.setTexto("que dia es hoy");
        pr.setTipo(1);
        pr.setImagen("conejo0.gif");
        r1.setRespuesta("Lunes");
        r2.setRespuesta("Martes");
        r3.setRespuesta("Miercoles");
        r4.setRespuesta("Jueves");
        pr.addRespuesta(r1);
        pr.addRespuesta(r2);
        pr.addRespuesta(r3);
        pr.addRespuesta(r4);
        exa.addPregunta(pr);

        Pregunta pre = new Pregunta();
        Respuesta re1 = new Respuesta();
        Respuesta re2 = new Respuesta();
        Respuesta re3 = new Respuesta();
        Respuesta re4 = new Respuesta();
        pre.setTexto("que hora es");
        pre.setTipo(2);
        pre.setImagen("conejo1.gif");
        re1.setRespuesta("una y media");
        re2.setRespuesta("dos de la tarde");
        re3.setRespuesta("cuatro de la trde");
        re4.setRespuesta("nueve de la noche");
        pre.addRespuesta(re1);
        pre.addRespuesta(re2);
        pre.addRespuesta(re3);
        pre.addRespuesta(re4);
        exa.addPregunta(pre);

        Pregunta preg = new Pregunta();
        Respuesta re11 = new Respuesta();
        Respuesta re22 = new Respuesta();
        Respuesta re33 = new Respuesta();
        Respuesta re44 = new Respuesta();
        preg.setTexto("que hora es");
        preg.setTipo(2);
        preg.setImagen("conejo2.gif");
        re11.setRespuesta("una y media");
        re22.setRespuesta("dos de la tarde");
        re33.setRespuesta("cuatro de la trde");
        re44.setRespuesta("nueve de la noche");
        preg.addRespuesta(re1);
        preg.addRespuesta(re2);
        preg.addRespuesta(re3);
        preg.addRespuesta(re4);
        exa.addPregunta(preg);

        p.pdfExamen(exa, "C:\\" + exa.getDescripcion() + ".pdf");
    }
}
