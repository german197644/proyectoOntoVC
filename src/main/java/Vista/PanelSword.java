/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.SwordControler;
import Control.MetsControler;
import Control.ErrorControl;
import Control.FicheroControler;
import Control.StardogControler;
import Modelo.Coleccion;

import java.awt.Component;
import java.awt.Container;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pogliani, Germán 2019
 */
public class PanelSword extends javax.swing.JPanel {

    SwordControler repositorio;
    StardogControler stardog;
    FicheroControler fichero;
    MetsControler xmlMets;

    /**
     * Creates new form PanelSword2
     */
    public PanelSword() {
        initComponents();

        //this.listaSeguimiento.setLineWrap(true);
        //inhabilitamos los botones
        this.setEnableContainer(this.panelCentral, false);
        this.setEnableContainer(this.panelBotones, false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGetSD = new javax.swing.JButton();
        panelBotones = new javax.swing.JPanel();
        btnDepositar = new javax.swing.JButton();
        panelCentral = new javax.swing.JPanel();
        comunidades = new javax.swing.JScrollPane();
        listaComunidades = new javax.swing.JList<>();
        ficheros = new javax.swing.JScrollPane();
        listaFicheros = new javax.swing.JList<>();
        out = new javax.swing.JScrollPane();
        listaSeguimiento = new javax.swing.JTextArea();
        ds = new javax.swing.JScrollPane();
        listaSD = new javax.swing.JTextArea();
        btnSubirFichero = new javax.swing.JButton();
        btnQuitarF = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnGetSD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnGetSD.setText("Get Documento de Servicio");
        btnGetSD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetSDActionPerformed(evt);
            }
        });
        jPanel1.add(btnGetSD);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnDepositar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDepositar.setText("Depositar mi(s) ïtem(s)");
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });
        panelBotones.add(btnDepositar);

        add(panelBotones, java.awt.BorderLayout.PAGE_END);

        panelCentral.setEnabled(false);

        comunidades.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comunidad(es) existente(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        listaComunidades.setBackground(new java.awt.Color(240, 240, 240));
        comunidades.setViewportView(listaComunidades);

        ficheros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ficheros(s) elegido(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        listaFicheros.setBackground(new java.awt.Color(240, 240, 240));
        ficheros.setViewportView(listaFicheros);

        out.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mensajes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        listaSeguimiento.setBackground(new java.awt.Color(240, 240, 240));
        listaSeguimiento.setColumns(20);
        listaSeguimiento.setLineWrap(true);
        listaSeguimiento.setRows(5);
        out.setViewportView(listaSeguimiento);

        ds.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Documento de servicio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        listaSD.setEditable(false);
        listaSD.setBackground(new java.awt.Color(240, 240, 240));
        listaSD.setColumns(20);
        listaSD.setRows(5);
        listaSD.setFocusable(false);
        ds.setViewportView(listaSD);

        btnSubirFichero.setText("Subir Fichero(s)");
        btnSubirFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirFicheroActionPerformed(evt);
            }
        });

        btnQuitarF.setText("Quitar Fichero");
        btnQuitarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ds)
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comunidades)
                            .addComponent(ficheros, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addGroup(panelCentralLayout.createSequentialGroup()
                                .addComponent(btnSubirFichero)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQuitarF)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(out, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addComponent(comunidades, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ficheros, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSubirFichero)
                            .addComponent(btnQuitarF)))
                    .addComponent(out))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ds, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
        );

        add(panelCentral, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void restablecerPanel() {
        listaComunidades.setModel(new DefaultComboBoxModel<>());
        listaFicheros.setModel(new DefaultListModel<>());
        listaSeguimiento.selectAll();
        listaSeguimiento.setText("");
        listaSD.selectAll();
        listaSD.setText("");
        this.setEnableContainer(panelCentral, false);
        this.setEnableContainer(this.panelBotones, false);
    }

    private void setEnableContainer(Container c, boolean band) {
        Component[] components = c.getComponents();
        c.setEnabled(band);
        for (Component component : components) {
            component.setEnabled(band);
            if (component instanceof Container) {
                setEnableContainer((Container) component, band);
            }
        }
    }

    //el deposito se realiza en forma binaria pero con un archivo ZIP
    //porque son dos o mas ficheros los que se envian con un grupo de 
    //metadatos.
    private boolean depositoZIP() throws Exception {
        this.viewMsjSeguimiento("\nIniciando depósito zip.");
        this.repositorio = SwordControler.getInstancia();
        this.fichero = FicheroControler.getInstancia();
        /*generamos el ZIP*/
        final boolean ret = fichero.crearZip(this);
        if (!ret) {
            this.viewMsjSeguimiento("Fallo al generar zip. Depósito terminado.");
            return false;
        } else {
            this.viewMsjSeguimiento("Archivo zip generado.");
        }        
        /* depositoDeFicheroSimple en el repositorio. */
        Coleccion col = (Coleccion) ((Object) listaComunidades.getSelectedValue());
        this.viewMsjSeguimiento("Comenzando depósito sobre la coleccion: " + col.getColeccion().getTitle());
        repositorio.depositoZipMasMetadatos(col.getColeccion(), this);
        this.viewMsjSeguimiento("Depósito del ítem terminado."); //fin del depositoDeFicheroSimple.
        return true;
    }

    private void depositoSimple() throws Exception {
        Coleccion col = (Coleccion) ((Object) listaComunidades.getSelectedValue());
        this.viewMsjSeguimiento("Iniciando depósito de ítem(s) sobre la coleccion: " + col.getColeccion().getTitle());
        //String aValue;
        repositorio.depositoDeFicheroSimple(col.getColeccion(), this);
        this.viewMsjSeguimiento("\nDepósito de ítem(s) terminado(s).");
    }


    private void btnGetSDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetSDActionPerformed
        this.restablecerPanel();
        try {            
            repositorio = SwordControler.getInstancia();
            if (repositorio != null) {
                //repositorio.setearVariables();
                DefaultListModel aComunidades = null;
                aComunidades = repositorio.getColecciones();
                if (aComunidades != null) {
                    listaComunidades.setModel(aComunidades);
                    //habilitamos los botones
                    this.setEnableContainer(panelCentral, true);
                    this.setEnableContainer(this.panelBotones, true);                    
//                    this.listaSD.append(repositorio.outputServiceDocument());
                    listaSeguimiento.append("Se obtuvo el Service Document "
                            + "de SWORD y las colecciones que componen "
                            + "el repositorio.");
                } else {
                    this.setEnableContainer(panelCentral, false);
                    this.setEnableContainer(this.panelBotones, false);
                }

            } else {
                this.setEnableContainer(panelCentral, false);
                this.setEnableContainer(this.panelBotones, false);
            }

        } catch (Exception ex) {

            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "004", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGetSDActionPerformed

    //visualiza los mensajes
    private void viewMsjSeguimiento(String msj) {
        String aux = this.listaSeguimiento.getText();
        if (aux.equals("")) {
            this.listaSeguimiento.append(msj);
        } else {
            this.listaSeguimiento.append("\n" + msj);
        }
        this.listaSeguimiento.updateUI();
        this.listaSeguimiento.setCaretPosition(
                this.listaSeguimiento.getDocument().getLength());
    }

    private void depositoMEts() throws Exception {
        
        this.viewMsjSeguimiento("\nIniciando depósito SIP"
                + "(Submission Information Package).");
        repositorio = SwordControler.getInstancia();
        //fichero = FicheroControler.getInstancia();
        xmlMets = MetsControler.getInstancia();
        /*generamos el zip con el mets*/
        xmlMets.newMETS();        
        this.viewMsjSeguimiento("Archivo Mets generado.");
        /* depositado en el repositorio, en la coleccion... */
        Coleccion col = (Coleccion) ((Object) listaComunidades.getSelectedValue());
        this.viewMsjSeguimiento("Comenzando el deposito de item(s) sobre la coleccion: " 
                + col.getColeccion().getTitle());
        repositorio.depositoMetsZip(col.getColeccion(), this);
        this.viewMsjSeguimiento("Deposito de ítem(s) terminado.");
    }

    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositarActionPerformed

        try {
            this.stardog = StardogControler.getInstancia();
            String retStardog = this.stardog.visualizarMetadatos();
            String retFichero = FicheroControler.getInstancia().visualizarFicheros();
            String valError = this.stardog.validateMetadatos();            
            if ((retStardog.length() == 0) || (retFichero.length() == 0)
                    || (valError.length() > 0)) {
                this.viewMsjSeguimiento("Datos ontológicos vacios o\n"
                        + "la captura de metadatos contiene errores\n"
                        + "de validación o\n"
                        + "no hay ficheros disponibles."
                );
            } else {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                PanelResumen dialogoResumen = new PanelResumen(frame, true, true);
                dialogoResumen.setVisible(true);
                final String dato = dialogoResumen.getBoton();
                switch (dato) {
                    case "btn_cancelar":
                        this.viewMsjSeguimiento("Operación de Depósito cancelada.");
                        break;
                    case "btn_binario":
                        this.depositoSimple();
                        break;
                    case "btn_zip":
                        this.depositoZIP();
                        break;
                    case "btn_mets":
                        this.depositoMEts();
                        break;
                }
            }
        } catch (Exception ex) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "003",
                    JOptionPane.ERROR_MESSAGE);
            this.viewMsjSeguimiento("--- Fallo el depositar ---");
        }
    }//GEN-LAST:event_btnDepositarActionPerformed

    private void btnSubirFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirFicheroActionPerformed
        try {
            fichero = FicheroControler.getInstancia();
            fichero.getFileChooser(this);
            listaFicheros.setModel(fichero.getListaFicheros());
            panelCentral.setEnabled(true);
            this.viewMsjSeguimiento("Se agragó un nuevo archivo. total añadidos: " 
                    + fichero.getListaFicheros().size());
        } catch (IOException ex) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "005",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSubirFicheroActionPerformed

    private void btnQuitarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarFActionPerformed
        try {
            fichero = FicheroControler.getInstancia();
            if (fichero.getListaFicheros().size() > 0) {
                fichero.quitarFichero(listaFicheros.getSelectedIndex());
            } else {
                this.viewMsjSeguimiento("No hay ficheros para quitar.");
            }
        } catch (Exception ex) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "016",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnQuitarFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDepositar;
    private javax.swing.JButton btnGetSD;
    private javax.swing.JButton btnQuitarF;
    private javax.swing.JButton btnSubirFichero;
    private javax.swing.JScrollPane comunidades;
    private javax.swing.JScrollPane ds;
    private javax.swing.JScrollPane ficheros;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JList<String> listaComunidades;
    private javax.swing.JList<String> listaFicheros;
    private javax.swing.JTextArea listaSD;
    private javax.swing.JTextArea listaSeguimiento;
    private javax.swing.JScrollPane out;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelCentral;
    // End of variables declaration//GEN-END:variables
}
