/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.DublinCoreControl;
import Control.ConfigControl;
import Control.RestControl;
import Modelo.BitstreamsRest;
import Modelo.ColeccionRest;
import Modelo.ComunidadRest;
import Modelo.ItemRest;
import Modelo.JLabelLink;
import Modelo.Metadato;
import Modelo.TextAreaRenderer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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
public class Recuperando extends javax.swing.JDialog {

    ComunidadRest comunidad = null;
    ColeccionRest coleccion = null;
    DefaultTableModel tree = null;

    /**
     * Creates new form Filtro
     *
     * @param parent
     * @param modal
     */
    public Recuperando(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            this.setLocationRelativeTo(null);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int height = screenSize.height - 30;
            this.setBounds(0, 0, screenSize.width, height);
            //
            tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new TextAreaRenderer());
            tablaFiltro.setRowHeight(50); // con 50 anda

            //
            ConfigControl login = ConfigControl.getInstancia();
            JLabelLink link = new JLabelLink();
            link.setText("Busqueda en el repositorio. Aquí.");
            link.setSize(link.getText().length(), 20);
            link.setLink(login.getUri() + "/rest/static/reports/query.html");
            link.setTextLink("Aquí");
            jPanel17.add(link);
            //jPanel16.repaint();
        } catch (IOException ex) {
            Logger.getLogger(Recuperando.class.getName()).log(Level.SEVERE, null, ex);
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

        panelCabecera = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListMetadatos = new javax.swing.JList<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
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
        panelSur = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        panelCentral = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaRecursos = new javax.swing.JList<>();
        jLabel24 = new javax.swing.JLabel();
        btnDescargar = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaFiltro = new javax.swing.JTable();
        panelBase = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtrado y recupero de Items");

        panelCabecera.setPreferredSize(new java.awt.Dimension(670, 220));
        panelCabecera.setLayout(new java.awt.BorderLayout());

        jPanel5.setMaximumSize(new java.awt.Dimension(492, 122));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setMaximumSize(new java.awt.Dimension(400, 122));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder("Repositorio"));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Repositorio");
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

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel21.setText(" ");
        jPanel2.add(jLabel21, java.awt.BorderLayout.PAGE_START);

        jLabel22.setText(" ");
        jPanel2.add(jLabel22, java.awt.BorderLayout.PAGE_END);

        jLabel23.setText(" ");
        jPanel2.add(jLabel23, java.awt.BorderLayout.LINE_END);

        jLabel20.setText(" ");
        jPanel2.add(jLabel20, java.awt.BorderLayout.LINE_START);

        jPanel8.add(jPanel2);

        jPanel11.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel11);

        jPanel13.setBackground(new java.awt.Color(0, 153, 153));
        jPanel13.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
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

        jPanel18.setBackground(new java.awt.Color(0, 153, 153));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jListMetadatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Metadatos"));
        jListMetadatos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Metadatos" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jListMetadatos);

        jPanel18.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jLabel25.setText(" ");
        jPanel18.add(jLabel25, java.awt.BorderLayout.LINE_START);

        jLabel26.setText(" ");
        jPanel18.add(jLabel26, java.awt.BorderLayout.PAGE_START);

        jLabel27.setText(" ");
        jPanel18.add(jLabel27, java.awt.BorderLayout.LINE_END);

        jLabel28.setText(" ");
        jPanel18.add(jLabel28, java.awt.BorderLayout.PAGE_END);

        jPanel6.add(jPanel18);

        jPanel10.setBackground(new java.awt.Color(0, 153, 153));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("   ");
        jPanel10.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jLabel2.setText("     ");
        jPanel10.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        jLabel3.setText("     ");
        jPanel10.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jLabel4.setText("     ");
        jPanel10.add(jLabel4, java.awt.BorderLayout.LINE_END);

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.BorderLayout());

        norte.setOpaque(false);
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

        jPanel10.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel10);

        jTabbedPane1.addTab("Metadatos", jPanel6);

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setPreferredSize(new java.awt.Dimension(650, 0));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel9.setText("     ");
        jPanel7.add(jLabel9, java.awt.BorderLayout.WEST);

        jLabel11.setText("    ");
        jPanel7.add(jLabel11, java.awt.BorderLayout.PAGE_END);

        jPanel14.setBackground(new java.awt.Color(0, 153, 153));
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

        jPanel16.setBackground(new java.awt.Color(0, 153, 153));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel12.setText("   ");
        jPanel16.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        jLabel17.setText("   ");
        jPanel16.add(jLabel17, java.awt.BorderLayout.PAGE_END);

        jLabel18.setText("     ");
        jPanel16.add(jLabel18, java.awt.BorderLayout.LINE_START);

        jLabel19.setText("     ");
        jPanel16.add(jLabel19, java.awt.BorderLayout.LINE_END);

        jPanel17.setOpaque(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel17, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Búsqueda avanzada", jPanel16);

        panelCabecera.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panelSur.setBackground(new java.awt.Color(204, 255, 204));
        panelSur.setAutoscrolls(true);
        panelSur.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelSur.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jButton3.setText("Ejecutar consulta de elementos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelSur.add(jButton3);

        panelCabecera.add(panelSur, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panelCabecera, java.awt.BorderLayout.PAGE_START);

        panelCentral.setBackground(new java.awt.Color(204, 255, 204));
        panelCentral.setLayout(new java.awt.BorderLayout(0, 2));

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));
        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        jLabel15.setText("     ");
        jPanel15.add(jLabel15);

        jLabel16.setText("Resultados");
        jPanel15.add(jLabel16);

        panelCentral.add(jPanel15, java.awt.BorderLayout.PAGE_START);

        jLabel13.setText("   ");
        panelCentral.add(jLabel13, java.awt.BorderLayout.LINE_START);

        jLabel14.setText("   ");
        panelCentral.add(jLabel14, java.awt.BorderLayout.LINE_END);

        jPanel24.setOpaque(false);
        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel27.setOpaque(false);
        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel26.setOpaque(false);
        jPanel26.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 5));

        listaRecursos.setBorder(javax.swing.BorderFactory.createTitledBorder("Recursos del ítem"));
        listaRecursos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "material de estudio" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaRecursos.setPreferredSize(new java.awt.Dimension(300, 16));
        listaRecursos.setVisibleRowCount(5);
        jScrollPane2.setViewportView(listaRecursos);

        jPanel26.add(jScrollPane2);

        jLabel24.setText("   ");
        jPanel26.add(jLabel24);

        btnDescargar.setText("Descargar recurso");
        btnDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarActionPerformed(evt);
            }
        });
        jPanel26.add(btnDescargar);

        jPanel27.add(jPanel26, java.awt.BorderLayout.CENTER);

        jPanel19.setOpaque(false);
        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 5));

        jButton6.setText("Examinar ìtem en el navegador");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel19.add(jButton6);

        jPanel27.add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel24.add(jPanel27, java.awt.BorderLayout.PAGE_END);

        jPanel22.setLayout(new java.awt.GridLayout());

        jScrollPane4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(452, 30));

        tablaFiltro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nro.", "Id", "Colección", "Handle", "Titulo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaFiltro.setSelectionBackground(new java.awt.Color(255, 255, 204));
        tablaFiltro.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tablaFiltro.setShowVerticalLines(false);
        tablaFiltro.getTableHeader().setReorderingAllowed(false);
        tablaFiltro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaFiltroMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablaFiltro);
        if (tablaFiltro.getColumnModel().getColumnCount() > 0) {
            tablaFiltro.getColumnModel().getColumn(0).setMinWidth(60);
            tablaFiltro.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(90);
        }

        jPanel22.add(jScrollPane4);

        jPanel24.add(jPanel22, java.awt.BorderLayout.CENTER);

        panelCentral.add(jPanel24, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelCentral, java.awt.BorderLayout.CENTER);

        panelBase.setBackground(new java.awt.Color(0, 153, 153));
        panelBase.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelBase.add(jButton2);

        getContentPane().add(panelBase, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        RestControl rest = RestControl.getInstancia();
        rest.unFiltro(coleccion, evt, tablaFiltro, jTable1,
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
            DublinCoreControl dublinCore = DublinCoreControl.getInstancia();
            if ((jListMetadatos.getSelectedIndex() >= 0) && !txtMetadato.getText().isEmpty()) {
                Metadato mt = (Metadato) ((Object) jListMetadatos.getSelectedValue());
                String equivalente = (String) dublinCore.getEquivalenciaDC(mt.getTipo().toLowerCase().trim());
                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                dtm.addRow(new Object[]{equivalente, txtMetadato.getText()});
            }
        } catch (Exception ex) {
            Logger.getLogger(Recuperando.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            txtMetadato.setText((String) jTable1.getValueAt(row, 1));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void tablaFiltroMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaFiltroMousePressed
        RestControl rest = RestControl.getInstancia();
        int fila = tablaFiltro.rowAtPoint(evt.getPoint());
        int columna = tablaFiltro.columnAtPoint(evt.getPoint());
        //String unHandle = tablaFiltro.getModel().getValueAt(fila, columna).toString().trim();
        //rest.miHandle(fila, columna, unHandle);
        //
        // Recuperar los recursos del item        
        //
        if ((fila < 0) && (columna < 0)) {
            return;
        }
        String unLink = tablaFiltro.getModel().getValueAt(fila, 1).toString().trim();
        String unNombre = tablaFiltro.getModel().getValueAt(fila, 3).toString().trim();
        ItemRest miItem = new ItemRest(unNombre, unLink);
        DefaultListModel misItems = rest.obtenerBitstreams(miItem);
        if (misItems.size() > 0) {
            System.out.println("entre....................");
            listaRecursos.setModel(misItems);
        } else {
            System.out.println("no entre)");
            DefaultListModel<String> dlm = new DefaultListModel();
            dlm.addElement("sin recursos");
            listaRecursos.setModel(dlm);
        }
    }//GEN-LAST:event_tablaFiltroMousePressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        RestControl rest = RestControl.getInstancia();
        int fila = tablaFiltro.getSelectedRow();
        int colu = tablaFiltro.getSelectedColumn();
        System.out.println("Fila: " + fila + " - Columna: " + colu);
        //int fila = tablaFiltro.rowAtPoint(evt.getPoint());
        //int columna = tablaFiltro.columnAtPoint(evt.getPoint());
        String unHandle = tablaFiltro.getModel().getValueAt(fila, colu).toString().trim();
        rest.miHandle(fila, colu, unHandle);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarActionPerformed
        try {
            ConfigControl login = ConfigControl.getInstancia();
            if (listaRecursos.getModel().getSize() > 0) {
                if (!(((Object) listaRecursos.getSelectedValue())instanceof BitstreamsRest)) {
                    return;
                }
                BitstreamsRest bs = (BitstreamsRest) ((Object) listaRecursos.getSelectedValue());
                String dir = login.getUri().trim() + bs.getLink() + "/retrieve";
                System.out.println("Recuso: " + dir);
                Runtime.getRuntime().exec("cmd.exe /c start chrome "+ dir);
            }
        } catch (IOException ex) {
            Logger.getLogger(Recuperando.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDescargarActionPerformed

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
            java.util.logging.Logger.getLogger(Recuperando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recuperando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recuperando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recuperando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Recuperando dialog = new Recuperando(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDescargar;
    private javax.swing.JPanel center;
    private javax.swing.JFormattedTextField fTextLimite;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListMetadatos;
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
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTree jTree1;
    private javax.swing.JList<String> listaRecursos;
    private javax.swing.JPanel norte;
    private javax.swing.JPanel panelBase;
    private javax.swing.JPanel panelCabecera;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelSur;
    private javax.swing.JTable tablaFiltro;
    private javax.swing.JTextField txtMetadato;
    private javax.swing.JTextField txtOffSet;
    // End of variables declaration//GEN-END:variables
}
