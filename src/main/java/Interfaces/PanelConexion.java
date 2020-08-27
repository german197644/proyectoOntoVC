/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Control.ErrorControl;
import Control.ConexionSwordControl;
import Control.ConexionStardogControl;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 *
 * @author germa
 */
public class PanelConexion extends javax.swing.JPanel {

    //private static RepositorioEngine repositorio;
    //private static StardogControl ontologia;
    private static ConexionStardogControl connOnto;
    private static ConexionSwordControl connRepo;

    //Boolean jtfModify = false;
    /**
     * Creates new form PanelConexion2
     */
    public PanelConexion() {
        initComponents();

        this.setEnableContainer(contenedor1, false);
        this.setEnableContainer(contenedor2, false);

        btnGuardar.setEnabled(false);

        //escuchamos los cambios de los jTextField
        addChangeListener(urlSwordField, e -> doSomething(e));
        addChangeListener(userSwordField, e -> doSomething(e));
        addChangeListener(urlStardogField, e -> doSomething(e));
        addChangeListener(nombreBDStardogField, e -> doSomething(e));
        addChangeListener(userStardogField, e -> doSomething(e));

        //si modificamos los campos de las claves
        addChangeListener(passwordStardogField, e -> setPass(e));
        addChangeListener(passwordSwordField, e -> setPass(e));
        
        
        //FIXME: no es funcional el campo obo.
        this.oboSwordField.setVisible(false);
        this.oboSwordLabel.setVisible(false);
    }

    private void setPass(ChangeEvent e){        
        connOnto.setPass(new String(passwordStardogField.getPassword()));
        connRepo.setPass(new String(passwordSwordField.getPassword()));
        //System.out.println("pass onto: " + connOnto.getPass());
    }
    
    private void doSomething(ChangeEvent e) {

        if ((urlSwordField.getText().trim().equals(connRepo.getSdIRI().trim()))
                && (userSwordField.getText().trim().equals(connRepo.getUser().trim()))
                && (urlStardogField.getText().trim().equals(connOnto.getUrl().trim()))
                && (nombreBDStardogField.getText().trim().equals(connOnto.getTo().trim()))
                && (userStardogField.getText().trim().equals(connOnto.getUser().trim()))) {
            this.btnGuardar.setEnabled(false);
        } else {
            this.btnGuardar.setEnabled(true);
        }
    }

    /**
     * Installs a listener to receive notification when the text of any
     * {@code JTextComponent} is changed. Internally, it installs a
     * {@link DocumentListener} on the text component's {@link Document}, and a
     * {@link PropertyChangeListener} on the text component to detect if the
     * {@code Document} itself is replaced.
     *
     * @param text any text component, such as a {@link JTextField} or
     * {@link JTextArea}
     * @param changeListener a listener to receieve {@link ChangeEvent}s when
     * the text is changed; the source object for the events will be the text
     * component
     * @throws NullPointerException if either parameter is null
     */
    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }

        };

        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document) e.getOldValue();
            Document d2 = (Document) e.getNewValue();
            if (d1 != null) {
                d1.removeDocumentListener(dl);
            }
            if (d2 != null) {
                d2.addDocumentListener(dl);
            }
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) {
            d.addDocumentListener(dl);
        }
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

    private void getPropiedades() throws IOException, Exception {
        //Inicializamo los campos para el repositorio.
        connRepo = ConexionSwordControl.getInstancia();
        connRepo.setUpRead();

        urlSwordField.setText(connRepo.getSdIRI());
        userSwordField.setText(connRepo.getUser());
        passwordSwordField.setText(connRepo.getPass());
        oboSwordField.setText(connRepo.getObo());

        //System.out.println("obo..:" + connRepo.getObo());
        //Inicializamos los campos para la Ontologia
        connOnto = ConexionStardogControl.getInstancia();
        connOnto.setUpRead();

        this.urlStardogField.setText(connOnto.getUrl());
        this.nombreBDStardogField.setText(connOnto.getTo());
        this.userStardogField.setText(connOnto.getUser());
        this.passwordStardogField.setText(connOnto.getPass());
        //this.iriStardogField.setText(ontologia.getIriOnto());

        //habilitamos los contenedores 1y2.
        if (connOnto != null) {
            this.setEnableContainer(contenedor1, true);
        }

        if (connRepo != null) {
            this.setEnableContainer(contenedor2, true);
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

        jPanel1 = new javax.swing.JPanel();
        btnPropiedades = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        contenedor = new javax.swing.JPanel();
        contenedor1 = new javax.swing.JPanel();
        urlSwordField = new javax.swing.JTextField();
        urlSwordLabel = new javax.swing.JLabel();
        userSwordLabel = new javax.swing.JLabel();
        userSwordField = new javax.swing.JTextField();
        passwordSwordField = new javax.swing.JPasswordField();
        passSwordLabel = new javax.swing.JLabel();
        oboSwordLabel = new javax.swing.JLabel();
        oboSwordField = new javax.swing.JTextField();
        verPassSword = new javax.swing.JCheckBox();
        contenedor2 = new javax.swing.JPanel();
        passStardogLabel = new javax.swing.JLabel();
        userStardogLabel = new javax.swing.JLabel();
        passwordStardogField = new javax.swing.JPasswordField();
        userStardogField = new javax.swing.JTextField();
        verPassStardog = new javax.swing.JCheckBox();
        nombreBDStardogLabel = new javax.swing.JLabel();
        nombreBDStardogField = new javax.swing.JTextField();
        urlStardogField = new javax.swing.JTextField();
        urlStardogLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnPropiedades.setText("Get propiedades");
        btnPropiedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropiedadesActionPerformed(evt);
            }
        });
        jPanel1.add(btnPropiedades);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnGuardar.setText("Guardar Cambios");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar);

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Solo los campos en rojo se guardan. Siguiente para continuar.");
        jPanel2.add(jLabel1);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);

        contenedor1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login Sword", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        urlSwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlSwordFieldActionPerformed(evt);
            }
        });

        urlSwordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        urlSwordLabel.setForeground(new java.awt.Color(255, 0, 0));
        urlSwordLabel.setText("Url servidor SWORD");
        urlSwordLabel.setName(""); // NOI18N

        userSwordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userSwordLabel.setForeground(new java.awt.Color(255, 0, 0));
        userSwordLabel.setText("Usuario Sword");
        userSwordLabel.setName(""); // NOI18N

        userSwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSwordFieldActionPerformed(evt);
            }
        });

        passwordSwordField.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                passwordSwordFieldInputMethodTextChanged(evt);
            }
        });
        passwordSwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordSwordFieldActionPerformed(evt);
            }
        });

        passSwordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        passSwordLabel.setText("Clave");
        passSwordLabel.setName(""); // NOI18N

        oboSwordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oboSwordLabel.setForeground(new java.awt.Color(255, 0, 0));
        oboSwordLabel.setText("Obo");
        oboSwordLabel.setName(""); // NOI18N

        oboSwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oboSwordFieldActionPerformed(evt);
            }
        });

        verPassSword.setText("ver clave");
        verPassSword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verPassSwordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenedor1Layout = new javax.swing.GroupLayout(contenedor1);
        contenedor1.setLayout(contenedor1Layout);
        contenedor1Layout.setHorizontalGroup(
            contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(contenedor1Layout.createSequentialGroup()
                        .addComponent(urlSwordLabel)
                        .addGap(18, 18, 18)
                        .addComponent(urlSwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contenedor1Layout.createSequentialGroup()
                        .addComponent(userSwordLabel)
                        .addGap(54, 54, 54)
                        .addComponent(userSwordField))
                    .addGroup(contenedor1Layout.createSequentialGroup()
                        .addComponent(passSwordLabel)
                        .addGap(109, 109, 109)
                        .addComponent(passwordSwordField)
                        .addGap(18, 18, 18)
                        .addComponent(verPassSword))
                    .addGroup(contenedor1Layout.createSequentialGroup()
                        .addComponent(oboSwordLabel)
                        .addGap(116, 116, 116)
                        .addComponent(oboSwordField)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        contenedor1Layout.setVerticalGroup(
            contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor1Layout.createSequentialGroup()
                .addGroup(contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(urlSwordLabel)
                    .addComponent(urlSwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userSwordLabel)
                    .addComponent(userSwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedor1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passSwordLabel)
                            .addComponent(passwordSwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contenedor1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(verPassSword)))
                .addGap(7, 7, 7)
                .addGroup(contenedor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(oboSwordLabel)
                    .addComponent(oboSwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contenedor2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login Stardog", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        passStardogLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        passStardogLabel.setText("Clave");
        passStardogLabel.setName(""); // NOI18N

        userStardogLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userStardogLabel.setForeground(new java.awt.Color(255, 0, 0));
        userStardogLabel.setText("Usuario StarDog");
        userStardogLabel.setName(""); // NOI18N

        passwordStardogField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordStardogFieldActionPerformed(evt);
            }
        });

        userStardogField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userStardogFieldActionPerformed(evt);
            }
        });

        verPassStardog.setText("ver clave");
        verPassStardog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verPassStardogActionPerformed(evt);
            }
        });

        nombreBDStardogLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nombreBDStardogLabel.setForeground(new java.awt.Color(255, 0, 0));
        nombreBDStardogLabel.setText("Nombre BD");
        nombreBDStardogLabel.setName(""); // NOI18N

        urlStardogField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlStardogFieldActionPerformed(evt);
            }
        });

        urlStardogLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        urlStardogLabel.setForeground(new java.awt.Color(255, 0, 0));
        urlStardogLabel.setText("Url Servidor StarDog");
        urlStardogLabel.setName(""); // NOI18N

        javax.swing.GroupLayout contenedor2Layout = new javax.swing.GroupLayout(contenedor2);
        contenedor2.setLayout(contenedor2Layout);
        contenedor2Layout.setHorizontalGroup(
            contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addComponent(urlStardogLabel)
                        .addGap(4, 4, 4)
                        .addComponent(urlStardogField, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addComponent(nombreBDStardogLabel)
                        .addGap(59, 59, 59)
                        .addComponent(nombreBDStardogField))
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addComponent(userStardogLabel)
                        .addGap(30, 30, 30)
                        .addComponent(userStardogField))
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addComponent(passStardogLabel)
                        .addGap(97, 97, 97)
                        .addComponent(passwordStardogField)
                        .addGap(18, 18, 18)
                        .addComponent(verPassStardog)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        contenedor2Layout.setVerticalGroup(
            contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(urlStardogLabel)
                    .addComponent(urlStardogField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreBDStardogLabel)
                    .addComponent(nombreBDStardogField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userStardogLabel)
                    .addComponent(userStardogField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passStardogLabel)
                    .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordStardogField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(verPassStardog)))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contenedor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contenedor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(contenedor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contenedor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        add(contenedor, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropiedadesActionPerformed
        try {
            this.getPropiedades();
            this.setEnableContainer(jPanel1, false);
            //this.setEnableContainer(jPanel2, true);
        } catch (IOException ex) {
            //Logger.getLogger(PanelConexion.class.getName()).log(Level.SEVERE, null, ex);
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "013", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            //Logger.getLogger(PanelConexion.class.getName()).log(Level.SEVERE, null, ex);
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "012", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPropiedadesActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        //verificamos si hubo cambio en los campos de entrada.
        try {
            PanelConexion.connRepo = ConexionSwordControl.getInstancia();
            PanelConexion.connOnto = ConexionStardogControl.getInstancia();

            //guardamos los datos de conexion ontologicos.
            PanelConexion.connRepo.setUpSave(
                    urlSwordField.getText(),
                    userSwordField.getText(),
                    new String(passwordSwordField.getPassword()),
                    oboSwordField.getText());
            //guardamos los datos de conexion del repositorio.
            PanelConexion.connOnto.setUpSave(
                    nombreBDStardogField.getText(),
                    urlStardogField.getText(),
                    userStardogField.getText(),
                    new String(passwordStardogField.getPassword())
            );
            //levantamos la informacion nuevamente
            //this.getPropiedades();
            //} else {
            //    JOptionPane.showMessageDialog(this, "son iguales");
            //}
        } catch (Exception ex) {
            //Logger.getLogger(PanelConexion.class.getName()).log(Level.SEVERE, null, ex);
            ErrorControl error = ErrorControl.getInstancia();
            error.setMsj(ex.getMessage());
            error.viewError(this, "011", JOptionPane.ERROR_MESSAGE);
        }
        //}

        //deshabilitamos los contenedores 1y2
        this.setEnableContainer(this.contenedor1, false);
        this.setEnableContainer(this.contenedor2, false);
        this.setEnableContainer(jPanel1, true); //boton btnPropiedades
        this.setEnableContainer(jPanel2, false); //boton btnGuardar
        //set        
        //this.jtfModify = false;
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void urlSwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlSwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urlSwordFieldActionPerformed

    private void userSwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userSwordFieldActionPerformed

    private void verPassSwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verPassSwordActionPerformed
        // TODO add your handling code here:
        if (verPassSword.isSelected()) {
            passwordSwordField.setEchoChar((char) 0);
        } else {
            passwordSwordField.setEchoChar('*');
        }
    }//GEN-LAST:event_verPassSwordActionPerformed

    private void userStardogFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userStardogFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userStardogFieldActionPerformed

    private void verPassStardogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verPassStardogActionPerformed
        // TODO add your handling code here:
        if (verPassStardog.isSelected()) {
            passwordStardogField.setEchoChar((char) 0);
        } else {
            passwordStardogField.setEchoChar('*');
        }
    }//GEN-LAST:event_verPassStardogActionPerformed

    private void urlStardogFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlStardogFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urlStardogFieldActionPerformed

    private void passwordStardogFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordStardogFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordStardogFieldActionPerformed

    private void oboSwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oboSwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oboSwordFieldActionPerformed

    private void passwordSwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordSwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordSwordFieldActionPerformed

    private void passwordSwordFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_passwordSwordFieldInputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_passwordSwordFieldInputMethodTextChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPropiedades;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel contenedor1;
    private javax.swing.JPanel contenedor2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nombreBDStardogField;
    private javax.swing.JLabel nombreBDStardogLabel;
    private javax.swing.JTextField oboSwordField;
    private javax.swing.JLabel oboSwordLabel;
    private javax.swing.JLabel passStardogLabel;
    private javax.swing.JLabel passSwordLabel;
    private javax.swing.JPasswordField passwordStardogField;
    private javax.swing.JPasswordField passwordSwordField;
    private javax.swing.JTextField urlStardogField;
    private javax.swing.JLabel urlStardogLabel;
    private javax.swing.JTextField urlSwordField;
    private javax.swing.JLabel urlSwordLabel;
    private javax.swing.JTextField userStardogField;
    private javax.swing.JLabel userStardogLabel;
    private javax.swing.JTextField userSwordField;
    private javax.swing.JLabel userSwordLabel;
    private javax.swing.JCheckBox verPassStardog;
    private javax.swing.JCheckBox verPassSword;
    // End of variables declaration//GEN-END:variables
}
