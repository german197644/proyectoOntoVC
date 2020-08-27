/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entidades.FicheroSimple;
import edu.harvard.hul.ois.mets.helper.MetsException;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author germa
 */
public class FicheroControl {

    //Creamos el objeto JFileChooser
    JFileChooser fileChooser = new JFileChooser(".");
    //Contenedor de los ficheros seleccionados
    DefaultListModel<FicheroSimple> listaFicheros = new DefaultListModel<>();
    //instancia unica de FicheroControl
    private static FicheroControl instancia = null;

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
        return listaFicheros;
    }

    public void ClearFicheros() {
        listaFicheros.clear();
    }

    public String getFolderZip() {
        return folderZip;
    }

    
    /**
     * Metodo que permite seleccionar ficheros.
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
                FicheroSimple f = new FicheroSimple(fichero);
                listaFicheros.addElement(f);                
            } else {
                //Si el usuario pulsa en cancelar o ocurre un error
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
     * Solo se pueden seleccionar ficheros.
     *
     * @param parent
     * @return
     * @throws IOException
     */
    public File getCarpeta(Component parent) throws IOException {
        File fichero = null;

        //seteamos el nombre de la ventana
        fileChooser.setDialogTitle("Seleccione la carpeta");

        //Creamos el filtro
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP Documents", "zip"));

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

    public void quitarFichero(int aPosi) throws Exception {
        if ((aPosi >= 0) && (aPosi < listaFicheros.size())) {
            this.listaFicheros.remove(aPosi);
        }
    }

    public String visualizarFicheros() {
        String retorno = "";
        DefaultListModel<FicheroSimple> lista = this.getListaFicheros();
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
        for (int i = 0; i < listaFicheros.size(); ++i) {

            //obtenego el fichero
            //File aFichero = new File(listaFicheros.get(i));
            File aFichero = listaFicheros.get(i).getUnFile();

            if (aFichero.exists()) {
                //System.out.println("gererandoooooooooooooooooooooooooo");
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
