/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Fichero;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Pogliani, Germán
 * 
 */
public class FicheroControl {

    //Creamos el objeto JFileChooser
    JFileChooser fileChooser = new JFileChooser(".");
 
    //Contenedor de los ficheros seleccionados
    DefaultListModel<Fichero> ficheros = new DefaultListModel<>();
    
    //instancia unica de FicheroControl
    private static FicheroControl instancia = null;
    
    // Carpeta de salida zip - en desuso
    private String folderZip = null;

    protected FicheroControl() {
    }

    public static FicheroControl getInstancia() {
        if (instancia == null) {
            instancia = new FicheroControl();
        }
        return instancia;
    }

    public DefaultListModel getListaFicheros() {
        return ficheros;        
    }

    public void ClearFicheros() {
        ficheros.clear();
    }

    public String getFolderZip() {
        return folderZip;
    }

    
    /**
     * Metodo que permite seleccionar archivos.
     *
     * @param parent
     * @throws java.io.IOException
     */
    public void getFileChooser(Component parent) throws IOException {
        File fichero = null;
        try {
            //seteamos el nombre de la ventana
            fileChooser.setDialogTitle("Selección de Objetos de Aprendizaje.");
            //Creamos el filtro
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP Documents", "zip"));
            //Indicamos lo que podemos seleccionar.
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
            int seleccion = fileChooser.showOpenDialog(parent);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                //Seleccionamos el fichero
                fichero = fileChooser.getSelectedFile();
                Fichero f = new Fichero(fichero);
                ficheros.addElement(f);                
            } else {
                // Si el usuario pulsa en cancelar o ocurre un error
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Infomación", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Solo se pueden seleccionar ficheros.
     *
     * @param parent
     * @return
     * @throws IOException
     */
    public File getFichero(Component parent) throws IOException {
        File fichero = null;

        //seteamos el nombre de la ventana
        fileChooser.setDialogTitle("Seleccione el fichero.");

        //Creamos el filtro
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP Documents", "zip"));

        //Indicamos lo que podemos seleccionar.
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fileChooser.showOpenDialog(parent);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            fichero = fileChooser.getSelectedFile();
        }
        return fichero;
    }

    /**
     * Devuelve el directorio seleccionado.
     *
     * @param parent LLamador
     * @return El directorio seleccionado.
     * 
     */
    public File getCarpeta(Component parent){
        File fichero = null;

        //seteamos el nombre de la ventana
        fileChooser.setDialogTitle("Seleccione la carpeta");

        //Indicamos lo que podemos seleccionar.
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fileChooser.showOpenDialog(parent);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            fichero = fileChooser.getSelectedFile();
        }
        return fichero;
    }

    public void quitarFichero(int aPosi){
        if ((aPosi >= 0) && (aPosi < ficheros.size())) {
            this.ficheros.remove(aPosi);
        }
    }

    public String visualizarFicheros() {
        String retorno = "";
        DefaultListModel<Fichero> lista = this.getListaFicheros();
        if (lista.size() > 0) {
            retorno += "\n -------------------------------------------------- \n";
        }
        for (int i = 0; i < lista.size(); ++i) {
            final String aux = lista.get(i).getUnFile().getName();
            final int posi = i + 1;
            retorno += "Fichero " + posi + ":: " + aux + "\n";
        }

        return retorno;
    }

    /**
     * Generador de un archivo zip con los fichero seleccinados.
     *
     * @param aParent
     * @return @throws FileNotFoundException
     * @throws IOException
     */
    public boolean crearZip(Component aParent) throws IOException {
        boolean exito = false;

        /*fijamos la carpeta de salida del ZIP.*/
        File carpetaZIP = this.getCarpeta(aParent);
        if (carpetaZIP == null) {
            return false;
        }
        File outfile = java.io.File.createTempFile("fichero", ".zip", carpetaZIP);
        this.folderZip = outfile.getAbsolutePath(); //fichero zip de salida.
        ZipOutputStream os = new ZipOutputStream(new FileOutputStream(this.folderZip));

        //hay que agregar el archivo de metadatos mets
        for (int i = 0; i < ficheros.size(); ++i) {

            //obtenego el fichero
            //File aFichero = new File(ficheros.get(i));
            File aFichero = ficheros.get(i).getUnFile();

            if (aFichero.exists()) {                
                //establecemos el nombre del archivo comprimido.
                //generamos la entrada del archivo al ZipOutputStream
                ZipEntry entrada = new ZipEntry(aFichero.getName());
                os.putNextEntry(entrada);

                FileInputStream afile = new FileInputStream(aFichero.getAbsolutePath());
                byte[] buffer = new byte[1024];
                int leido = 0;
                while (0 < (leido = afile.read(buffer))) {
                    os.write(buffer, 0, leido);
                }

                afile.close();
                os.closeEntry();
            }
            exito = true;
        }
        //cerramos el ZipOutputStream            
        os.close();
        return exito;
    }
}
