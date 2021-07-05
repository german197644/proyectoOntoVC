/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ConfigControl;
import Control.RestControl;
import Control.StardogControl;
import Modelo.Metadato;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author germa
 */
public class Esquema extends javax.swing.JDialog {

    /**
     * Creates new form Esquemeonto
     *
     * @param parent
     * @param modal
     */
    public Esquema(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            this.setLocationRelativeTo(null);
            // seteamos los prefijos existentes
            RestControl rest = RestControl.getInstancia();
            rest.obtenerPrefix(cbPrefix);
            DefaultComboBoxModel model = (DefaultComboBoxModel) cbPrefix.getModel();            
            //StardogControl base = StardogControl.getInstancia();
            //listaMeta.setModel(base.getListaMetadados2());            
        } catch (Exception ex) {
            Logger.getLogger(Esquema.class.getName()).log(Level.SEVERE, null, ex);
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

        panelNorte = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbPrefix = new javax.swing.JComboBox<>();
        bntCargar = new javax.swing.JButton();
        panelCentro = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        btnAnexar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaMeta = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaPrefix = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        txtRotulo = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnQuitar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaAsociados = new javax.swing.JTable();
        panelSur = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnTerminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de esquemas.");
        setBackground(new java.awt.Color(204, 255, 204));
        setModal(true);
        setResizable(false);

        panelNorte.setBackground(new java.awt.Color(204, 255, 204));
        panelNorte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelNorte.setPreferredSize(new java.awt.Dimension(812, 33));
        panelNorte.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Prefijos de esquemas en repositorio");
        panelNorte.add(jLabel1);

        cbPrefix.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "prefijos en repositorio." }));
        cbPrefix.setMaximumSize(new java.awt.Dimension(180, 20));
        cbPrefix.setMinimumSize(new java.awt.Dimension(180, 20));
        cbPrefix.setPreferredSize(new java.awt.Dimension(180, 20));
        panelNorte.add(cbPrefix);

        bntCargar.setText("Cargar esquema");
        bntCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCargarActionPerformed(evt);
            }
        });
        panelNorte.add(bntCargar);

        getContentPane().add(panelNorte, java.awt.BorderLayout.PAGE_START);

        panelCentro.setBackground(new java.awt.Color(204, 255, 204));
        panelCentro.setLayout(new java.awt.BorderLayout());

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(290, 383));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel24.setMinimumSize(new java.awt.Dimension(3, 100));
        jPanel24.setOpaque(false);
        jPanel24.setPreferredSize(new java.awt.Dimension(3, 33));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel24, java.awt.BorderLayout.LINE_START);

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel25.setOpaque(false);
        jPanel25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAnexar.setText("Anexar");
        btnAnexar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnexarActionPerformed(evt);
            }
        });
        jPanel25.add(btnAnexar);

        jPanel1.add(jPanel25, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(260, 444));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel2.setText(" ");
        jPanel4.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jPanel7.setOpaque(false);
        jPanel4.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridLayout(2, 0, 0, 2));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Metadatos de la ontología"));
        jScrollPane2.setOpaque(false);

        listaMeta.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Metadatos" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listaMeta);

        jPanel5.add(jScrollPane2);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Elementos del prefijo"));
        jScrollPane1.setOpaque(false);

        listaPrefix.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Prefijos" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaPrefix);

        jPanel5.add(jScrollPane1);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel10.setMinimumSize(new java.awt.Dimension(111, 20));
        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(522, 35));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(3, 14));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel13, java.awt.BorderLayout.WEST);

        jPanel19.setOpaque(false);
        jPanel19.setPreferredSize(new java.awt.Dimension(522, 5));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel20.setOpaque(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(522, 5));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        jPanel18.setMinimumSize(new java.awt.Dimension(96, 15));
        jPanel18.setOpaque(false);
        jPanel18.setPreferredSize(new java.awt.Dimension(341, 20));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Rótulo:");
        jPanel18.add(jLabel3, java.awt.BorderLayout.WEST);

        jPanel23.setMinimumSize(new java.awt.Dimension(6, 10));
        jPanel23.setOpaque(false);
        jPanel23.setPreferredSize(new java.awt.Dimension(422, 15));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        txtRotulo.setColumns(21);
        txtRotulo.setPreferredSize(new java.awt.Dimension(174, 10));
        jPanel23.add(txtRotulo);

        jPanel18.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel8.add(jPanel4, java.awt.BorderLayout.CENTER);

        panelCentro.add(jPanel8, java.awt.BorderLayout.LINE_START);

        jPanel9.setBackground(new java.awt.Color(0, 204, 204));
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 153, 0));
        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(5, 403));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel11, java.awt.BorderLayout.WEST);

        jPanel17.setOpaque(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(522, 10));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel17, java.awt.BorderLayout.PAGE_START);

        jPanel16.setOpaque(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(3, 441));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel16, java.awt.BorderLayout.EAST);

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        jPanel14.add(btnQuitar);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel14.add(btnCancelar);

        jPanel12.add(jPanel14, java.awt.BorderLayout.SOUTH);

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(452, 100));

        tablaAsociados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Metadato", "Esquema Asociado", "Rótulo Metadato"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAsociados.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(tablaAsociados);

        jPanel15.add(jScrollPane3);

        jPanel12.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        panelCentro.add(jPanel9, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelCentro, java.awt.BorderLayout.CENTER);

        panelSur.setBackground(new java.awt.Color(204, 204, 204));
        panelSur.setMinimumSize(new java.awt.Dimension(229, 40));
        panelSur.setPreferredSize(new java.awt.Dimension(809, 40));
        panelSur.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(809, 3));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 809, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        panelSur.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setMinimumSize(new java.awt.Dimension(229, 35));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(809, 35));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnGuardar.setText("Guardar configuración");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar);

        btnTerminar.setText("Finalizar");
        btnTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarActionPerformed(evt);
            }
        });
        jPanel3.add(btnTerminar);

        panelSur.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelSur, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnTerminarActionPerformed

    private void bntCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCargarActionPerformed
        try {
            RestControl rest = RestControl.getInstancia();
            if (cbPrefix.getSelectedIndex() > 0) {
                String miPrefix = (String) ((Object) cbPrefix.getSelectedItem());
                rest.obtenerEsquema(miPrefix, listaPrefix);
                rest.obtenerConfigEsquema(miPrefix, tablaAsociados);
            }
            StardogControl base = StardogControl.getInstancia();
            DefaultListModel mlm = base.getListaMetadados2();
            if (mlm.getSize() == 0) {
                base.getMetadatos_v6(new JTextArea(), evt);
            } 
            listaMeta.setModel(base.getListaMetadados2());
        } catch (Exception ex) {
            Logger.getLogger(Esquema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bntCargarActionPerformed

    private void btnAnexarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnexarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tablaAsociados.getModel();
        if ((listaPrefix.getSelectedIndex() > 0) && (listaMeta.getSelectedIndex() >= 0)) {
            String onto = ((Metadato) ((Object) listaMeta.getSelectedValue())).getRotulo();
            String schema = listaPrefix.getSelectedValue();
            String rotular = (txtRotulo.getText().isEmpty()) ? "" : txtRotulo.getText().trim();
            String[] miRow = {onto, schema, rotular};
            modelo.addRow(miRow);
        }
    }//GEN-LAST:event_btnAnexarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        try {
            //int col = jTable1.getSelectedColumn();
            //RestControl rest = RestControl.getInstancia();
            ConfigControl config = ConfigControl.getInstancia();
            config.quitarDeEsquema2(tablaAsociados, evt);
            //
            RestControl rest = RestControl.getInstancia();
            if (cbPrefix.getSelectedIndex() > 0) {
                String miPrefix = (String) ((Object) cbPrefix.getSelectedItem());
                //rest.obtenerEsquema(miPrefix, listaPrefix);
                rest.obtenerConfigEsquema(miPrefix, tablaAsociados);
            }
        } catch (IOException ex) {
            Logger.getLogger(Esquema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            ConfigControl rest = ConfigControl.getInstancia();
            TableModel modelo = tablaAsociados.getModel();
            //ListSelectionModel lsm = tablaAsociados.getSelectionModel();
            //int min = lsm.getMinSelectionIndex();
            if (modelo.getRowCount() > 0) {
                rest.grabarEsquema(modelo, evt);
            }
        } catch (IOException ex) {
            Logger.getLogger(Esquema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        RestControl rest = RestControl.getInstancia();
        if (cbPrefix.getSelectedIndex() > 0) {
            String miPrefix = (String) ((Object) cbPrefix.getSelectedItem());
            //rest.obtenerEsquema(miPrefix, listaPrefix);
            rest.obtenerConfigEsquema(miPrefix, tablaAsociados);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Esquema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Esquema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Esquema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Esquema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Esquema dialog = new Esquema(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCargar;
    private javax.swing.JButton btnAnexar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnTerminar;
    private javax.swing.JComboBox<String> cbPrefix;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listaMeta;
    private javax.swing.JList<String> listaPrefix;
    private javax.swing.JPanel panelCentro;
    private javax.swing.JPanel panelNorte;
    private javax.swing.JPanel panelSur;
    private javax.swing.JTable tablaAsociados;
    private javax.swing.JTextField txtRotulo;
    // End of variables declaration//GEN-END:variables
}
