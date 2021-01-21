/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.FicheroControler;
import Control.MetsControler;
import Control.StardogControler;
import Control.SwordControler;
import Modelo.Coleccion;
import Modelo.Metadato;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author germa
 */
public class DOS extends javax.swing.JFrame {

    
    public DOS() {                        
        initComponents();
        this.setTitle("Depósito Ontológico Simple.");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);                       

        try {
            StardogControler stardog = StardogControler.getInstancia();
            SwordControler sword = SwordControler.getInstancia();

            // sword                
            DefaultListModel colecciones = new DefaultListModel();
            colecciones = sword.getColecciones();
            listaColecciones.removeAll();
            listaColecciones.setModel(colecciones);
            listaColecciones.updateUI();
            // end

            // stardog
            DefaultListModel aList = stardog.getTiposOA();
            System.out.println(aList );
            listaOA.setModel(aList);
            listaOA.updateUI();                
            // end                                

        } catch (Exception ex) {
            Logger.getLogger(DOS.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_superior = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panel_inferior = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnDepositar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panel_central = new javax.swing.JPanel();
        panelSur = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taConsola = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaOA = new javax.swing.JList<>();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listaMetadato = new javax.swing.JList<>();
        jPanel14 = new javax.swing.JPanel();
        btnAgregarMetadato = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        captura = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaColecciones = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaRecursos = new javax.swing.JList<>();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnAddFichero = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        btnDelFichero = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuTool = new javax.swing.JMenu();
        mnuConectar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_superior.setBackground(new java.awt.Color(0, 153, 204));
        panel_superior.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("    ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jLabel2.setText("          ");
        jPanel1.add(jLabel2, java.awt.BorderLayout.WEST);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("       ");
        jPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_END);

        jLabel4.setText("       ");
        jPanel1.add(jLabel4, java.awt.BorderLayout.LINE_END);

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel14.setText("Envío de ítems.");
        jPanel1.add(jLabel14, java.awt.BorderLayout.CENTER);

        panel_superior.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_superior, java.awt.BorderLayout.PAGE_START);

        panel_inferior.setBackground(new java.awt.Color(0, 153, 204));
        panel_inferior.setLayout(new java.awt.BorderLayout());

        jLabel5.setText("   ");
        panel_inferior.add(jLabel5, java.awt.BorderLayout.LINE_END);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("          ");
        panel_inferior.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jLabel7.setText("          ");
        panel_inferior.add(jLabel7, java.awt.BorderLayout.LINE_START);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("          ");
        panel_inferior.add(jLabel8, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(255, 51, 51));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        btnDepositar.setText("Depositar");
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });
        jPanel3.add(btnDepositar);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel3.add(btnSalir);

        panel_inferior.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_inferior, java.awt.BorderLayout.PAGE_END);

        panel_central.setLayout(new java.awt.BorderLayout());

        panelSur.setLayout(new java.awt.BorderLayout());

        jPanel15.setBackground(new java.awt.Color(204, 255, 204));
        jPanel15.setLayout(new java.awt.GridLayout(7, 0));

        jLabel26.setText("          ");
        jPanel15.add(jLabel26);

        panelSur.add(jPanel15, java.awt.BorderLayout.WEST);

        jPanel16.setLayout(new java.awt.GridLayout(5, 0));

        jLabel27.setText("          ");
        jPanel16.add(jLabel27);

        panelSur.add(jPanel16, java.awt.BorderLayout.LINE_END);

        taConsola.setBackground(new java.awt.Color(255, 204, 153));
        taConsola.setColumns(20);
        taConsola.setLineWrap(true);
        taConsola.setRows(5);
        taConsola.setToolTipText("");
        taConsola.setWrapStyleWord(true);
        taConsola.setBorder(javax.swing.BorderFactory.createTitledBorder("Consola"));
        taConsola.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jScrollPane2.setViewportView(taConsola);

        panelSur.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panel_central.add(panelSur, java.awt.BorderLayout.SOUTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(0, 3, 4, 5));
        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel12.setText("  ");
        jPanel6.add(jLabel12, java.awt.BorderLayout.PAGE_END);

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        jLabel13.setText("                                                                        ");
        jPanel7.add(jLabel13);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Objetos de Aprendizajes"));

        listaOA.setBackground(new java.awt.Color(240, 240, 240));
        listaOA.setVisibleRowCount(5);
        listaOA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaOAMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaOA);

        jPanel7.add(jScrollPane1);

        jLabel19.setText("          ");
        jPanel7.add(jLabel19);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder("Metadatos"));

        listaMetadato.setBackground(new java.awt.Color(240, 240, 240));
        listaMetadato.setOpaque(false);
        listaMetadato.setVisibleRowCount(5);
        listaMetadato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaMetadatoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(listaMetadato);

        jPanel7.add(jScrollPane7);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnAgregarMetadato.setText("Agregar Metadato");
        btnAgregarMetadato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMetadatoActionPerformed(evt);
            }
        });
        jPanel14.add(btnAgregarMetadato);

        jPanel7.add(jPanel14);

        jPanel6.add(jPanel7, java.awt.BorderLayout.WEST);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel20.setText("     ");
        jPanel8.add(jLabel20, java.awt.BorderLayout.WEST);

        captura.setBackground(new java.awt.Color(204, 204, 255));
        captura.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Describir el ítem"));
        captura.setToolTipText("");
        captura.setMaximumSize(new java.awt.Dimension(33, 44));
        jPanel8.add(captura, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        panel_central.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel15.setText("          ");
        jPanel2.add(jLabel15);

        panel_central.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setText("          ");
        jPanel12.add(jLabel9);

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        panel_central.add(jPanel9, java.awt.BorderLayout.EAST);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel11.setBackground(new java.awt.Color(204, 255, 204));
        jLabel11.setText("          ");
        jLabel11.setOpaque(true);
        jPanel10.add(jLabel11, java.awt.BorderLayout.LINE_START);

        jPanel11.setLayout(new java.awt.GridLayout(0, 4, 5, 0));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Colección"));

        listaColecciones.setBackground(new java.awt.Color(240, 240, 240));
        listaColecciones.setVisibleRowCount(5);
        jScrollPane4.setViewportView(listaColecciones);

        jPanel11.add(jScrollPane4);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Recursos"));

        listaRecursos.setBackground(new java.awt.Color(240, 240, 240));
        listaRecursos.setOpaque(false);
        listaRecursos.setVisibleRowCount(5);
        jScrollPane3.setViewportView(listaRecursos);

        jPanel11.add(jScrollPane3);

        jPanel13.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        jLabel17.setText("  ");
        jPanel13.add(jLabel17);

        jLabel10.setText("  ");
        jPanel13.add(jLabel10);

        btnAddFichero.setText("Agregar Recurso");
        btnAddFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFicheroActionPerformed(evt);
            }
        });
        jPanel13.add(btnAddFichero);

        jLabel18.setText("  ");
        jPanel13.add(jLabel18);

        btnDelFichero.setText("Quitar Recurso");
        btnDelFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelFicheroActionPerformed(evt);
            }
        });
        jPanel13.add(btnDelFichero);

        jLabel16.setText("  ");
        jPanel13.add(jLabel16);

        jPanel11.add(jPanel13);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        panel_central.add(jPanel10, java.awt.BorderLayout.NORTH);

        getContentPane().add(panel_central, java.awt.BorderLayout.CENTER);

        mnuTool.setText("Herramienta");

        mnuConectar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuConectar.setText("Conectar");
        mnuConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConectarActionPerformed(evt);
            }
        });
        mnuTool.add(mnuConectar);
        mnuTool.add(jSeparator1);

        mnuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        mnuTool.add(mnuSalir);

        jMenuBar1.add(mnuTool);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        this.setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConectarActionPerformed
        Login login = new Login(this, rootPaneCheckingEnabled);
        login.setVisible(true);
    }//GEN-LAST:event_mnuConectarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAddFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFicheroActionPerformed
        try {
            FicheroControler fichero = FicheroControler.getInstancia();        
            fichero.getFileChooser(this);
            listaRecursos.setModel(fichero.getListaFicheros());
        } catch (IOException ex) {
            Logger.getLogger(DOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddFicheroActionPerformed

    private void btnDelFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelFicheroActionPerformed
        try {
            FicheroControler fichero = FicheroControler.getInstancia();
            if (fichero.getListaFicheros().size() > 0) {
                fichero.quitarFichero(listaRecursos.getSelectedIndex());
            }    
        } catch (Exception ex) {
            Logger.getLogger(DOS.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_btnDelFicheroActionPerformed

    private void listaOAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaOAMouseClicked
        StardogControler stardog = null;
        try {
            Metadato dato = (Metadato) ((Object) listaOA.getSelectedValue());
            DefaultListModel datos2 = stardog.getMetadatos_v1(dato);
            listaMetadato.setModel(datos2);
            
            //preseteamos el panel de captura con los metadatos obligatorios.
            final JPanel aJp = stardog.preSeteoPanelCaptura();
            captura.getViewport().setView(aJp);
            captura.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(DOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_listaOAMouseClicked

    private void listaMetadatoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMetadatoMouseClicked

    }//GEN-LAST:event_listaMetadatoMouseClicked

    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositarActionPerformed
        try {            
            SwingWorker<SwordControler, Void> mySwingWorker = new SwingWorker<SwordControler, Void>() {

                @Override
                protected SwordControler doInBackground() throws Exception {
                    // Sword
                     SwordControler repositorio = SwordControler.getInstancia();
                    //wait.close();
                    return repositorio;
                }
            };
            mySwingWorker.execute();
            //SwordControler repositorio = mySwingWorker.get();
            //---------------------------------------------------------
            taConsola.append("********************************"); taConsola.append(System.getProperty("line.separator"));
            taConsola.append("Iniciando depósito.");taConsola.append(System.getProperty("line.separator"));
            taConsola.append("Generando METS.");taConsola.append(System.getProperty("line.separator"));
            taConsola.append("********************************");taConsola.append(System.getProperty("line.separator"));
            taConsola.updateUI();
            //espera a que termine.
            //SwordControler repositorio = mySwingWorker.get();
                //fichero = FicheroControler.getInstancia();                     
            //MetsControler xmlMets = MetsControler.getInstancia();
            /*generamos el zip con el mets*/
            //xmlMets.newMETS();
            taConsola.append("METS generado");taConsola.append(System.getProperty("line.separator"));
            taConsola.append("Depositando... Por Favor espere...");
            //taConsola.append(System.getProperty("line.separator"));
            //this.viewMsjSeguimiento("Archivo Mets generado.");
            /* depositado en el repositorio, en la coleccion... */
            //Coleccion col = (Coleccion) ((Object) listaColecciones.getSelectedValue());
            //----------------------------------------------------------------
            //repositorio.myDepositoMets(col.getColeccion());
            //----------------------------------------------------------------
            taConsola.append("Deposito finalizado.");
            //taConsola.append(System.getProperty("line.separator"));
        } catch (Exception ex) {
            Logger.getLogger(DOS.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al depositar", "Informe", JOptionPane.ERROR_MESSAGE);
        }
       
    }//GEN-LAST:event_btnDepositarActionPerformed

    private void btnAgregarMetadatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMetadatoActionPerformed
        StardogControler stardog = null;   
        try {
            List objeto = listaMetadato.getSelectedValuesList();
            final JPanel aJp = stardog.setPanelCaptura(objeto);
            captura.getViewport().setView(aJp);
            captura.updateUI();
            //eliminamos los metadatos afectados si estos no se repiten.
            
            //if (ListMetadatos.getSelectedIndices().length > 0) {
            //    stardog.removerItemSeleccionados(ListMetadatos.getSelectedIndices());
            //}
            //this.viewMsjTextArea("Operación finalizada.\nSe agregaron " + objeto.size() + " metadatos para su registro.");

        } catch (Exception ex) {            
            JOptionPane.showMessageDialog(this, "Error", "Informe", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarMetadatoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFichero;
    private javax.swing.JButton btnAgregarMetadato;
    private javax.swing.JButton btnDelFichero;
    private javax.swing.JButton btnDepositar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane captura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JList<String> listaColecciones;
    private javax.swing.JList<String> listaMetadato;
    private javax.swing.JList<String> listaOA;
    private javax.swing.JList<String> listaRecursos;
    private javax.swing.JMenuItem mnuConectar;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JMenu mnuTool;
    private javax.swing.JPanel panelSur;
    private javax.swing.JPanel panel_central;
    private javax.swing.JPanel panel_inferior;
    private javax.swing.JPanel panel_superior;
    private javax.swing.JTextArea taConsola;
    // End of variables declaration//GEN-END:variables
}
