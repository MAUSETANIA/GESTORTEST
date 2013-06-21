/*
 * FormPrincipal.java
 *
 * Created on 21 de junio de 2013, 9:43
 */
package Examen.Presentacion;

import Examen.Negocio.Examen;
import Examen.Negocio.Pregunta;
import Examen.Negocio.Respuesta;
import Examen.Datos.ArchivoXML;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author  Guido Cáceres
 */
public class FormPrincipal extends javax.swing.JFrame{

    private String indice = "ABCDEFGHIJKLMN";
    private Examen examen;
    //private PanelLogin panelLogin = new PanelLogin();
    private JPanel panelFormulario = new JPanel();
    private Vector<ButtonGroup> grupos = new Vector<ButtonGroup>();
    private Timer timer;
    private Hashtable hash = new Hashtable();
    public static int  contador = 0;

    /** Creates new form FormPrincipal */
    public FormPrincipal() {
        initComponents();
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.GRAY);
        /*PanelDesarrollo.getViewport().add(panelLogin);
        panelLogin.getBtnOK().addActionListener(this);*/

        timer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
                lbContador.setText(Integer.toString(contador));
                contador--;
                if (contador == 0) {
                    TerminarExamen(e);
                    JOptionPane.showMessageDialog(null, "Examen Finalizado!!!!");
                }
            }
        });

    }

   
    public void setExamen(Examen examen) {
        this.examen = examen;
    }
    public void RecibirDatosAlumno(String nombre, String reg){
        lbNombreAlumno.setText(nombre);
        lbRegistro.setText(reg);
    }

    public void AbrirExamen(){
        panelFormulario=new JPanel();
        PanelDesarrollo.add(panelFormulario);
        JFileChooser jf = new JFileChooser();
        int res = jf.showOpenDialog(this);
        if (res == jf.APPROVE_OPTION) {
            String n=jf.getSelectedFile().getName();
            String ext=n.substring(n.indexOf('.')+1, n.length());
            if(ext.equals("test")){
              examen=new Examen();
              examen = ArchivoXML.abrirXML(jf.getSelectedFile().getAbsolutePath());
              examen.setAlumno(lbNombreAlumno.getText());
              examen.setRegistro(lbRegistro.getText());

              IniciarExamen();
            }else{
                JOptionPane.showMessageDialog(this, "Tipo de Archivo es incorrecto. Se requiere archivo *.test");
            }
        }

    }

    private int CargarTipoMultiple(int x, int y, Vector<Respuesta> respuestas) {
        int w;
        for (int i = 0; i < respuestas.size(); i++) {
            Respuesta r = respuestas.get(i);
            JRadioButton radio = new JRadioButton(indice.charAt(i) + ". " + r.getRespuesta());
            w = radio.getFontMetrics(radio.getFont()).stringWidth(r.getRespuesta());
            radio.setBounds(x, y, w + 60, 25);
            y = y + 26;
            panelFormulario.add(radio);
            hash.put(r, radio);
        }
        return y;
    }

    private int CargarTipoSimple(int x, int y, Vector<Respuesta> respuestas) {
        ButtonGroup group = new ButtonGroup();
        int w;
        for (int i = 0; i < respuestas.size(); i++) {
            Respuesta r = respuestas.get(i);
            JRadioButton radio = new JRadioButton(indice.charAt(i) + ". " + r.getRespuesta());
            group.add(radio);
            w = radio.getFontMetrics(radio.getFont()).stringWidth(r.getRespuesta());
            radio.setBounds(x, y, w + 60, 25);
            y = y + 26;
            panelFormulario.add(radio);
            hash.put(r, radio);
        }
        grupos.add(group);
        return y;
    }

    private int CargarTipoCompletar(int x,int y,Pregunta pregunta, int numero){
        String texto= new String();
        texto=pregunta.getTexto();
        String txt="";
        Respuesta resp;
        JLabel num=new JLabel(Integer.toString(numero+1)+". ");
        int w=num.getFontMetrics(num.getFont()).stringWidth(num.getText());
        num.setBounds(x, y, w, 25);
        x = x + num.getWidth();
        panelFormulario.add(num);
        char ch;
        int i,r;i=r=0;

            while (i < texto.length()) {
                ch=texto.charAt(i);
                if(ch!='*'){
                    txt=txt+ch;
                    i++;
                }else{
                    JLabel label=new JLabel(txt);
                    txt="";
                    w=label.getFontMetrics(label.getFont()).stringWidth(label.getText());
                    label.setBounds(x, y, w+90, 25);
                    x = x + w+5;
                    panelFormulario.add(label);
                    resp=pregunta.getRespuestas().get(r);
                    JTextField txtResp=new JTextField();
                    w=txtResp.getFontMetrics(txtResp.getFont()).stringWidth(resp.getRespuesta());
                    txtResp.setBounds(x,y,w+5,20);
                    x = x + w+10;
                    panelFormulario.add(txtResp);
                    hash.put(resp, txtResp);
                    i=i+pregunta.getRespuestas().get(r).getRespuesta().length()+1;
                    r++;
                }
              
            }
return y=y+30;
    }

    public void MostrarDatosExamen(){
        lbNombreExamen.setText(examen.getDescripcion());
        lbAsignatura.setText(examen.getAsignatura());
        lbProfesor.setText(examen.getProfesor());
        lbGrupo.setText(examen.getGrupo());
    }

    private void CargarFormulario() {
        int x = 50, y = 10;
        int w = 0;
        panelFormulario.setLayout(null);
        Vector<Pregunta> preguntas = examen.getPreguntas();
        Pregunta pregunta;
        Image img;
        for (int i = 0; i < preguntas.size(); i++) {
            pregunta = preguntas.get(i);
           if(preguntas.get(i).getTipo()==1 || preguntas.get(i).getTipo()==2){
            JLabel label = new JLabel(Integer.toString(i + 1) + ". " + pregunta.getTexto());
            w = label.getFontMetrics(label.getFont()).stringWidth(pregunta.getTexto());
            label.setBounds(x, y, w + 90, 25);
            y = y + 26;
            panelFormulario.add(label);

           }else{
                y = CargarTipoCompletar(x,y,pregunta,i);
           }
            if (pregunta.getImagen().trim().length() > 0) {
                try {
                    ImageIcon images = new ImageIcon(otraImagen.imageString(pregunta.getImagen()));
                    JLabel foto = new JLabel();
                    foto.setIcon(images);
                    foto.setBounds(x + 50, y, images.getIconWidth(), images.getIconHeight());
                    y = y + images.getIconHeight();
                    panelFormulario.add(foto);
                } catch (Exception e) {
                }
            }
            switch(pregunta.getTipo()){
                case 1: y = CargarTipoMultiple(x, y, pregunta.getRespuestas());
                break;
                case 2: y = CargarTipoSimple(x, y, pregunta.getRespuestas());
                break;

            }
            
        }
        panelFormulario.setPreferredSize(new Dimension(647, y + 90));
    }

    public void IniciarExamen(){
        MostrarDatosExamen();
        contador = examen.getTiempo() * 60;
        CargarFormulario();
        PanelDesarrollo.getViewport().add(panelFormulario);
        timer.start();
    }

    private void cargarRespuesta(int tipoPregunta,Vector<Respuesta> respuestas) {
        boolean sw;
        String texto;
        Respuesta r;
          if(tipoPregunta==1||tipoPregunta==2){
            for (int i = 0; i < respuestas.size(); i++) {
                r = respuestas.get(i);
                Object o = hash.get(r);
                 sw = ((JRadioButton) o).isSelected();
                r.setMarcado(sw);
            }
          }else{
            for (int i = 0; i < respuestas.size(); i++) {
                r = respuestas.get(i);
                Object o = hash.get(r);
                texto = ((JTextField) o).getText();
                r.setCompletado(texto.toLowerCase().trim());
            }
          }
    }

    private void TerminarExamen(ActionEvent e) {
       

        Vector<Pregunta> preguntas = examen.getPreguntas();
        Pregunta pregunta;
        //examen.setAlumno(lbNombreAlumno.getText());
        for (int i = 0; i < preguntas.size(); i++) {
            pregunta = preguntas.get(i);
            cargarRespuesta(pregunta.getTipo(),pregunta.getRespuestas());

        }
        timer.stop();
        timer = null;
      
        ArchivoXML.guardarXML(examen,"C:\\GestorTest\\RESPUESTAS\\");
        setVisible(false);
        dispose();
        

       
         
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelDesarrollo = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        lbContador = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbNombreExamen = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbProfesor = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbAsignatura = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbGrupo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbRegistro = new javax.swing.JLabel();
        lbNombreAlumno = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Realizar Examen");
        setBackground(new java.awt.Color(255, 255, 204));

        PanelDesarrollo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Desarrollo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(102, 102, 255))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Reloj.png"))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Examen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(102, 102, 255))); // NOI18N

        lbNombreExamen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbNombreExamen.setForeground(new java.awt.Color(102, 102, 255));
        lbNombreExamen.setText("TituloExamen");

        jLabel4.setBackground(new java.awt.Color(102, 102, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 255));
        jLabel4.setText("Profesor:");

        lbProfesor.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lbProfesor.setPreferredSize(new java.awt.Dimension(89, 14));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 255));
        jLabel6.setText("Asignatura:");

        lbAsignatura.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 255));
        jLabel8.setText("Grupo:");

        lbGrupo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lbGrupo.setPreferredSize(new java.awt.Dimension(89, 14));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LogoGestorTest.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel10)
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbAsignatura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lbNombreExamen)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lbNombreExamen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbAsignatura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(146, 146, 146))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Alumno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 11), new java.awt.Color(102, 102, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 255));
        jLabel2.setText("Registro:");

        lbRegistro.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N

        lbNombreAlumno.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNombreAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRegistro)
                .addGap(102, 102, 102))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jLabel2)
                .addComponent(lbNombreAlumno)
                .addComponent(lbRegistro))
        );

        jToolBar1.setRollover(true);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Terminar.png"))); // NOI18N
        jButton2.setText("Terminar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 255));
        jLabel5.setText("Tiempo:");

        jMenu1.setText("Achivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/abrir.png"))); // NOI18N
        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Terminar.png"))); // NOI18N
        jMenuItem2.setText("Terminar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Cerrar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lbContador)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelDesarrollo, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbContador)
                        .addComponent(jLabel5))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDesarrollo, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
        Integer sum=(examen.getTiempo()*60)-Integer.parseInt(lbContador.getText());
        examen.setTiempo(sum);
        contador=1;

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AbrirExamen();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Integer sum=(examen.getTiempo()*60)-Integer.parseInt(lbContador.getText());
        examen.setTiempo(sum);
        contador=1;
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane PanelDesarrollo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbAsignatura;
    private javax.swing.JLabel lbContador;
    private javax.swing.JLabel lbGrupo;
    private javax.swing.JLabel lbNombreAlumno;
    private javax.swing.JLabel lbNombreExamen;
    private javax.swing.JLabel lbProfesor;
    private javax.swing.JLabel lbRegistro;
    // End of variables declaration//GEN-END:variables


}
