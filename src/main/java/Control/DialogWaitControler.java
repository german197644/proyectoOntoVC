/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author germa
 */
public class DialogWaitControler {
    
    private JDialog dialog;
    
    JProgressBar progressBar;

    public DialogWaitControler() {
        this.progressBar = new JProgressBar();
        this.progressBar.setIndeterminate(true);
    }

    public DialogWaitControler(int max) {
        if (max > 0){
            this.progressBar = new JProgressBar(0, max);            
        }else {
            this.progressBar = new JProgressBar(0, max);
            this.progressBar.setIndeterminate(true);            
        }
    }
    
    public void makeWait(String msg, ActionEvent evt, int max) {
    //public void makeWait(String msg, JFrame evt) {
        
        Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
        //Window win = SwingUtilities.getWindowAncestor(evt);
        dialog = new JDialog(win, "Informe", Dialog.ModalityType.APPLICATION_MODAL);        
        dialog.setPreferredSize(new Dimension(300,100));   
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel(msg), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(win);  
        dialog.setVisible(true);
   }
        
    
   public void close() {
       dialog.dispose();
   }
   
   public void incrementarProBar(int valor){
       progressBar.setValue(valor);
   }
}
