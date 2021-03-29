/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.DublinCoreControler;
import Control.ConfigControler;
import Control.RestControler;
import Modelo.ColeccionRest;
import Modelo.ComunidadRest;
import Modelo.JLabelLink;
import Modelo.Metadato;
import Modelo.TextAreaRenderer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author germa
 */
public class Filtro extends javax.swing.JDialog {

    ComunidadRest comunidad = null;
    ColeccionRest coleccion = null;
    DefaultTableModel tree = null;

    /**
     * Creates new form Filtro
     *
     * @param parent
     * @param modal
     */
    public Filtro(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            this.setLocationRelativeTo(null);

            jTable2.getColumnModel().getColumn(1).setCellRenderer(new TextAreaRenderer());
            jTable2.setRowHeight(50); // con 50 anda

            //
            ConfigControler login = ConfigControler.getInstancia();
            JLabelLink link = new JLabelLink();
            link.setText("Busqueda en el repositorio. Aquí.");
            link.setSize(link.getText().length(), 20);
            link.setLink(login.getUri() + "/rest/static/reports/query.html");
            link.setTextLink("Aquí");
            jPanel17.add(link);
            //jPanel16.repaint();
        } catch (IOException ex) {
            Logger.getLogger(Filtro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTree(TreeModel model) {
        jTree1.setModel(model);
    }

    public void setMetadatos(ListModel model) {
        jListMetadatos.setModel(model);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListMetadatos = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        norte = new javax.swing.JPanel();
        txtMetadato = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        center = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        sur = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        fTextLimite = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtOffSet = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtrado de Items");
        setPreferredSize(new java.awt.Dimension(900, 630));

        jPanel1.setPreferredSize(new java.awt.Dimension(670, 220));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setMaximumSize(new java.awt.Dimension(492, 122));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setMaximumSize(new java.awt.Dimension(400, 122));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setPreferredSize(new java.awt.Dimension(400, 64));
        jTree1.setVisibleRowCount(8);
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTree1MousePressed(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jPanel8.add(jScrollPane1);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel11);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel13);

        jPanel5.add(jPanel8);

        jTabbedPane1.addTab("Repositorio", jPanel5);

        jPanel6.setPreferredSize(new java.awt.Dimension(532, 0));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jListMetadatos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Items" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jListMetadatos);

        jPanel6.add(jScrollPane5);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("   ");
        jPanel10.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jLabel2.setText("     ");
        jPanel10.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        jLabel3.setText("     ");
        jPanel10.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jLabel4.setText("     ");
        jPanel10.add(jLabel4, java.awt.BorderLayout.LINE_END);

        jPanel12.setLayout(new java.awt.BorderLayout());

        norte.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        txtMetadato.setColumns(30);
        txtMetadato.setText("dato");
        norte.add(txtMetadato);

        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        norte.add(jButton4);

        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        norte.add(jButton1);

        jPanel12.add(norte, java.awt.BorderLayout.NORTH);

        center.setLayout(new java.awt.GridLayout(1, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Campo filtro", "Valor"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        center.add(jScrollPane3);

        jPanel12.add(center, java.awt.BorderLayout.CENTER);
        jPanel12.add(sur, java.awt.BorderLayout.SOUTH);

        jPanel10.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel10);

        jTabbedPane1.addTab("Metadatos", jPanel6);

        jPanel7.setPreferredSize(new java.awt.Dimension(650, 0));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel9.setText("     ");
        jPanel7.add(jLabel9, java.awt.BorderLayout.WEST);

        jLabel11.setText("    ");
        jPanel7.add(jLabel11, java.awt.BorderLayout.PAGE_END);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setText("Límite: ");
        jPanel14.add(jLabel5);

        fTextLimite.setColumns(10);
        fTextLimite.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        fTextLimite.setText("100");
        jPanel14.add(fTextLimite);

        jLabel7.setText("   ");
        jPanel14.add(jLabel7);

        jLabel10.setText("Offset: ");
        jPanel14.add(jLabel10);

        txtOffSet.setColumns(10);
        txtOffSet.setText("0");
        jPanel14.add(txtOffSet);

        jPanel7.add(jPanel14, java.awt.BorderLayout.CENTER);

        jLabel6.setText(" ");
        jPanel7.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jLabel8.setText("     ");
        jPanel7.add(jLabel8, java.awt.BorderLayout.LINE_END);

        jTabbedPane1.addTab("Limites", jPanel7);

        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel12.setText("   ");
        jPanel16.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        jLabel17.setText("   ");
        jPanel16.add(jLabel17, java.awt.BorderLayout.PAGE_END);

        jLabel18.setText("     ");
        jPanel16.add(jLabel18, java.awt.BorderLayout.LINE_START);

        jLabel19.setText("     ");
        jPanel16.add(jLabel19, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel17, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Búsqueda especializada", jPanel16);

        jPanel1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jButton3.setText("Ejecutar consulta de elementos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton3);

        jPanel1.add(jPanel9, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        jLabel15.setText("     ");
        jPanel15.add(jLabel15);

        jLabel16.setText("Resultados");
        jPanel15.add(jLabel16);

        jPanel4.add(jPanel15, java.awt.BorderLayout.PAGE_START);

        jLabel13.setText("   ");
        jPanel4.add(jLabel13, java.awt.BorderLayout.LINE_START);

        jLabel14.setText("   ");
        jPanel4.add(jLabel14, java.awt.BorderLayout.LINE_END);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nro.", "Id", "Colección", "Ítem", "Titulo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(60);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(90);
        }

        jPanel4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        RestControler rest = RestControler.getInstancia();
        rest.unFiltro(coleccion, evt, jTable2, jTable1, 
                Integer.parseInt(fTextLimite.getText()), Integer.parseInt(txtOffSet.getText()));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTree1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MousePressed

    }//GEN-LAST:event_jTree1MousePressed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        TreePath path = evt.getPath();
        Object[] nodos = path.getPath();

        // Mirando el ultimo nodo del path, sabemos qué nodo en concreto
        // se ha seleccionado.
        DefaultMutableTreeNode ultimoNodo
                = (DefaultMutableTreeNode) nodos[nodos.length - 1];

        if (ultimoNodo.isLeaf() && ultimoNodo.getUserObject() instanceof ColeccionRest) {
            this.coleccion = (ColeccionRest) ultimoNodo.getUserObject();
        } else {
            this.coleccion = null;
        }
    }//GEN-LAST:event_jTree1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTable1.getSelectedRow() >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            DublinCoreControler dublinCore = DublinCoreControler.getInstancia();
            if ((jListMetadatos.getSelectedIndex() >= 0) && !txtMetadato.getText().isEmpty()) {
                Metadato mt = (Metadato) ((Object) jListMetadatos.getSelectedValue());
                String equivalente = (String) dublinCore.getEquivalenciaDC(mt.getTipo().toLowerCase().trim());
                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                dtm.addRow(new Object[]{equivalente, txtMetadato.getText()});
            }
        } catch (Exception ex) {
            Logger.getLogger(Filtro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            txtMetadato.setText((String) jTable1.getValueAt(row, 1));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        try {
            ConfigControler login = ConfigControler.getInstancia();
            int fila = jTable2.rowAtPoint(evt.getPoint());
            int columna = jTable2.columnAtPoint(evt.getPoint());
            if ((fila > -1) && (columna > -1) && (columna == 3)) {
                System.out.println(jTable2.getModel().getValueAt(fila, columna));
                Runtime.getRuntime().exec("cmd.exe /c start chrome " + login.getUri().trim() + "/xmlui" + "/handle/"
                        + jTable2.getModel().getValueAt(fila, columna).toString().trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(Filtro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable2MousePressed

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
            java.util.logging.Logger.getLogger(Filtro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Filtro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Filtro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Filtro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Filtro dialog = new Filtro(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel center;
    private javax.swing.JFormattedTextField fTextLimite;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListMetadatos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTree jTree1;
    private javax.swing.JPanel norte;
    private javax.swing.JPanel sur;
    private javax.swing.JTextField txtMetadato;
    private javax.swing.JTextField txtOffSet;
    // End of variables declaration//GEN-END:variables
}
