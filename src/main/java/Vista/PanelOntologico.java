/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ErrorControl;
import Control.StardogControler;
import Modelo.Metadato;
import java.awt.Component;
import java.awt.Container;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pogliani German
 */
public class PanelOntologico extends javax.swing.JPanel {

    //private RepositorioControl repositorio;
    private StardogControler stardog;

    /**
     * Creates new form PanelOntologico2
     */
    public PanelOntologico() {
        initComponents();

        //que el texto quede dentro de los limites del componente.
        //this.seguimiento.setLineWrap(true);

        captura.getVerticalScrollBar().setUnitIncrement(15);
        
        this.setEnableContainer(panelCentral, false);
        this.setEnableContainer(this.panelComando, false);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JPanel();
        panelComando = new javax.swing.JPanel();
        btn_describir = new javax.swing.JButton();
        btn_validar = new javax.swing.JButton();
        btn_verificar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        getOntologia = new javax.swing.JButton();
        panelCentral = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        seguimiento = new javax.swing.JTextArea();
        captura = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaOA = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListMetadatos = new javax.swing.JList<>();
        filtrar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        contenedor.setLayout(new java.awt.BorderLayout());

        panelComando.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btn_describir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_describir.setText("Descripbir Seleccionados");
        btn_describir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_describirActionPerformed(evt);
            }
        });
        panelComando.add(btn_describir);

        btn_validar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_validar.setText("Validar");
        btn_validar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validarActionPerformed(evt);
            }
        });
        panelComando.add(btn_validar);

        btn_verificar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_verificar.setText("Verificar");
        btn_verificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verificarActionPerformed(evt);
            }
        });
        panelComando.add(btn_verificar);

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        panelComando.add(btn_cancelar);

        contenedor.add(panelComando, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        getOntologia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getOntologia.setText("Get Ontología");
        getOntologia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getOntologiaActionPerformed(evt);
            }
        });
        jPanel2.add(getOntologia);

        contenedor.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mensajes\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        seguimiento.setEditable(false);
        seguimiento.setBackground(new java.awt.Color(240, 240, 240));
        seguimiento.setColumns(20);
        seguimiento.setLineWrap(true);
        seguimiento.setRows(5);
        seguimiento.setEnabled(false);
        seguimiento.setFocusable(false);
        jScrollPane5.setViewportView(seguimiento);

        captura.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Capturar metadatos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Objetos de Aprendizajes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        listaOA.setBackground(new java.awt.Color(240, 240, 240));
        jScrollPane1.setViewportView(listaOA);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Metadatos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        ListMetadatos.setBackground(new java.awt.Color(240, 240, 240));
        jScrollPane2.setViewportView(ListMetadatos);

        filtrar.setText("Filtrar metadatos");
        filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(captura, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCentralLayout.createSequentialGroup()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addComponent(filtrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addComponent(captura, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5)))
                .addContainerGap())
        );

        contenedor.add(panelCentral, java.awt.BorderLayout.CENTER);

        add(contenedor, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void getOntologiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getOntologiaActionPerformed
        try {
            //this.viewMsjTextArea(new StringBuffer("Instancia actual de stardog :" + stardog));
            stardog = StardogControler.getInstancia();
            if (stardog != null) {

                DefaultListModel aList = stardog.getTiposOA();

                if (aList != null) {
                    listaOA.setModel(aList);

                    //DefaultListModel datos2 = stardog.getMetadatos();
                    //this.ListMetadatos.setModel(datos2);
                    //habilitamos los paneles y sus contenidos.
                    this.setEnableContainer(this.panelCentral, true);
                    this.setEnableContainer(this.panelComando, true);
                    this.viewMsjTextArea("Se encontraron "
                            + aList.size() + " tipos de objetos de aprendizajes.");
                }
            } else {
                this.viewMsjTextArea("No fué posible conectar con el servidor de Stardog.");
            }
        } catch (Exception e) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(e.getMessage());
            error.viewError(this, "006", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_getOntologiaActionPerformed

    private void btn_validarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validarActionPerformed
        String auxString = new String();
        String retString = new String();
        DefaultListModel<Metadato> lista = stardog.getCapturaMetadados();
        try {
            if (lista.size() > 0) {
                auxString = "\n\n ==========================================================";
                auxString += "\n\t Validando de los metadatos. ";
                auxString += "\n ---------------------------------------------------------";
                retString = stardog.validateMetadatos();
                if (retString.length() == 0) {
                    auxString += "\n No se encontraron errores durante la validacion ";
                    this.viewMsjTextArea(auxString);
                } else {
                    this.viewMsjTextArea(auxString);
                    this.viewMsjTextArea(retString);
                }
            }
        } catch (Exception ex) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "015", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_validarActionPerformed

    private void btn_verificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_verificarActionPerformed
        try {
            String aux = stardog.visualizarMetadatos();
            //this.viewMsjTextArea(new StringBuffer(aux));
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            PanelResumen dialogoResumen = new PanelResumen(frame, true, false);
            dialogoResumen.setVisible(true);
        } catch (Exception e) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(e.getMessage());
            error.viewError(this, "010", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_verificarActionPerformed

    private void btn_describirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_describirActionPerformed
        try {
            List objeto = ListMetadatos.getSelectedValuesList();
            final JPanel aJp = stardog.setPanelCaptura(objeto);
            captura.getViewport().setView(aJp);

            //eliminamos los metadatos afectados si estos no se repiten.
            if (ListMetadatos.getSelectedIndices().length > 0) {
                stardog.removerItemSeleccionados(ListMetadatos.getSelectedIndices());
            }
            this.viewMsjTextArea("Operación finalizada.\nSe agregaron " + objeto.size() + " metadatos para su registro.");

        } catch (Exception ex) {
            ErrorControl error = ErrorControl.getInstancia();
            error.viewError(this, "010", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_describirActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed

        listaOA.setModel(new DefaultListModel<>());
        ListMetadatos.setModel(new DefaultListModel<>());
        seguimiento.selectAll();
        seguimiento.setText("");
        captura.setViewportView(new JPanel());

        this.setEnableContainer(this.panelCentral, false);
        this.setEnableContainer(this.panelComando, false);

        //cerramos la conexion
        //StardogControl.desconectarServidor();
        stardog.clearCapturaMetadatos();
        stardog.clearListaMetadatos();

    }//GEN-LAST:event_btn_cancelarActionPerformed

    //visualiza los mensajes
    private void viewMsjTextArea(String msj) {
        String aux = seguimiento.getText();
        if (aux.equals("")) {
            seguimiento.append(msj);
        } else {
            seguimiento.append("\n" + msj);
        }
        this.seguimiento.setCaretPosition(this.seguimiento.getDocument().getLength());
    }

    private void filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarActionPerformed
        try {
            Metadato dato = (Metadato) ((Object) listaOA.getSelectedValue());
            DefaultListModel datos2 = stardog.getMetadatos_v1(dato);
            this.ListMetadatos.setModel(datos2);
            this.viewMsjTextArea("\nSe encontraron " + datos2.size() + " metadatos.");

            //preseteamos el panel de captura con los metadatos obligatorios.
            final JPanel aJp = stardog.preSeteoPanelCaptura();
            captura.getViewport().setView(aJp);            
            captura.updateUI();
        } catch (Exception ex) {
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "008", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_filtrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListMetadatos;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_describir;
    private javax.swing.JButton btn_validar;
    private javax.swing.JButton btn_verificar;
    private javax.swing.JScrollPane captura;
    private javax.swing.JPanel contenedor;
    private javax.swing.JButton filtrar;
    private javax.swing.JButton getOntologia;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JList<String> listaOA;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelComando;
    private javax.swing.JTextArea seguimiento;
    // End of variables declaration//GEN-END:variables
}
