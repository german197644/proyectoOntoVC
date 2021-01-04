/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author germa
 */
public class DialogWait {
    private JDialog dialog;

    //public void makeWait(String msg, ActionEvent evt) {
    public void makeWait(String msg, JFrame evt) {
        
        Window win = SwingUtilities.getWindowAncestor(evt);
        dialog = new JDialog(win, msg, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setTitle("Conectando...");
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Por favor espere..."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(win);  
        dialog.setVisible(true);
   }

   public void close() {
       dialog.dispose();
   }
}
