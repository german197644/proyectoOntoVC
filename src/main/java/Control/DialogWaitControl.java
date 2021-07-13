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
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
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
public class DialogWaitControl {

    private JDialog dialog;

    JProgressBar progressBar;
    JLabel label = new JLabel();

    public DialogWaitControl() {
        this.progressBar = new JProgressBar();
        this.progressBar.setIndeterminate(true);
    }

    public DialogWaitControl(int max) {
        if (max > 0) {
            this.progressBar = new JProgressBar(0, max);
            this.progressBar.setIndeterminate(false);
        } else {
            this.progressBar = new JProgressBar();
            this.progressBar.setIndeterminate(true);
        }
    }

    public void makeWait(String msg, ActionEvent evt) {
        Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
        dialog = new JDialog(win, msg, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setPreferredSize(new Dimension(300, 100));
        //JProgressBar progressBar = new JProgressBar();
        //progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Procesando metadatos, por favor espere..."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);
    }
    
    
    public void makeWait(String msg, JFrame miFrame) {        
        dialog = new JDialog(miFrame, msg, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setPreferredSize(new Dimension(300, 100));
        //JProgressBar progressBar = new JProgressBar();
        //progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Procesando metadatos, por favor espere..."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(miFrame);
        dialog.setVisible(true);
    }

    public void makeWait(String msg, ActionEvent evt, int max) {
        //public void makeWait(String msg, JFrame evt) {        
        Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
        //Window win = SwingUtilities.getWindowAncestor(evt);
        dialog = new JDialog(win, "Informe", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setPreferredSize(new Dimension(300, 100));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        label.setText(msg);
        panel.add(label, BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);
    }
    
    
    public void makeWait(String msg, MouseEvent evt) {

        Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
        dialog = new JDialog(win, "Informe", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setPreferredSize(new Dimension(300, 100));
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

    public void incrementar(int valor) {
        progressBar.setValue(valor);
        //progressBar.updateUI();
    }

    public void setMensaje(String msg) {
        this.label.setText(msg);
        this.label.updateUI();
    }

    public void setearProgressBar(int max) {
        if (max > 0) {
            this.progressBar.setMinimum(0);
            this.progressBar.setMaximum(max);
            this.progressBar.setIndeterminate(false);
            dialog.pack();
        }
    }
}
