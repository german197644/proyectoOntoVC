/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.MetsXml;
import Modelo.Fichero;
import Modelo.MetadataSimple;
import edu.harvard.hul.ois.mets.helper.MetsException;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.Deflater;
import javax.swing.DefaultListModel;
import org.jdom.Element;

/**
 *
 * @author Pogliani, German
 */
public class MetsControl {

    //private final RepositorioControl repositorio;
    //private final DCoreControl dc;
    private DublinCoreControl dcore;

    private final StardogControl ontologia;
    private MetsXml mets;
    private final FicheroControl ficheros;
    private File fileZIP; //archivo donde depositar el zip con el mets.
    private Component aParent;

    private static MetsControl instancia = null;

    /**
     *
     * @throws Exception
     */
    private MetsControl() throws Exception {
        //this.repositorio = RepositorioControl.getInstancia();
        this.ontologia = StardogControl.getInstancia();
        this.ficheros = FicheroControl.getInstancia();
        //this.dc = DCoreControl.getInstancia();
        this.dcore = DublinCoreControl.getInstancia();
    }

    public static MetsControl getInstancia() {
        try {
            if (instancia == null) {
                instancia = new MetsControl();
            }

        } catch (Exception e) {
        }
        return instancia;
    }

    /**
     * Devuelve el nombre del fichero que contiene los OA y el mets .ZIP
     * necesario para el depostio en el repostiorio a traves de SWORD.
     *
     * @return
     */
    public File getFileZIP() {
        return fileZIP;
    }

    //encapsulamos los metadatos capturados.    
    private List myMakeMetadata() throws Exception {

        List<Element> aListEle = new ArrayList<>();
        DefaultListModel<MetadataSimple> aListMetadatos = ontologia.getCapturaMetadados();

        for (int i = 0; i < aListMetadatos.size(); ++i) {
            MetadataSimple m = aListMetadatos.get(i);
            //System.out.println("pase por aca!. metadato: " + m.getTipo());
            Object aDC = dcore.getEquivalenciaDC(m.getTipo().toLowerCase().trim());
            if (aDC != null) {

                if (aDC instanceof StringTokenizer) {
                    StringTokenizer aux = (StringTokenizer) aDC;
                    //String[] result = "this is a test".split("\\s");
                    //dividimos la cadena de los periodos
                    String[] result = m.getContenidoMetadato().split("-"); //mejorar la entrega del valor
                    i = 0;
                    while (aux.hasMoreTokens()) {
                        //System.out.println(aux.nextToken());
                        //final String uri = "http://purl.org/dc/terms/" + aux.nextToken().trim().toLowerCase();
                        final String uri = "http://purl.org/dc/terms/";
                        //Element t = new Element(aux.nextToken(), "dcterms", uri);
                        Element t = new Element(aux.nextToken(), "dc", uri);
                        //t.setText(m.getDataContenedor());
                        //System.out.println("entre al WHILE: " + aux.nextToken());
                        t.setText(result[i]);
                        aListEle.add(t);
                        i++;
                    }
                } else {
                    final String auxDC = ((String) aDC);
                    //System.out.println("entre al else " + auxDC);
                    //final String uri = "http://purl.org/dc/terms/" + auxDC;
                    final String uri = "http://purl.org/dc/terms/";
                    //Element t = new Element(auxDC, "dcterms", uri);
                    Element t = new Element(auxDC, "dc", uri);
                    //t.setText(m.getContenidoMetadato());
                    t = t.setAttribute(auxDC, m.getContenidoMetadato());
                    //System.out.println("entre al else " + auxDC);
                    aListEle.add(t);
                }
            }
        }
        return (aListEle);
    }

    /**
     * Crea el fichero mets con los metadatos relevados para su ingesta.
     *
     * @throws MetsException
     * @throws IOException
     */
    public void crearMets() throws MetsException, IOException, Exception {

        // Creamos un mets; validate = false, ZIP compression = BEST_SPEED
        mets = new MetsXml(false, Deflater.BEST_SPEED);

        // Optional: configurar el METS OBJID
        mets.setOBJID("Sword-Mets");

        // Optional: Set the METS creator
        mets.addAgent("CREATOR", "ORGANIZATION", "RIA - UTN - FRSF");

        //variable del archivo de salida.
        java.io.File outfile = null;

        // agregamos los objetos de aprendizaje al zip - el ultimo argumento es si "is Primary Bitstream".
        DefaultListModel<Fichero> aFiles = ficheros.getListaFicheros();
        //System.out.println("tamaño de los archivos" + aFiles.size());
        for (int i = 0; i < aFiles.size(); ++i) {
            File unArchivo = aFiles.get(i).getUnFile();
            //String aRelativo = aFiles.get(i).getAbsolutePath(); //al 11/11/2019 no lo diferencia.
            String aAbsoluto = aFiles.get(i).getUnFile().getAbsolutePath();
            if (i == 0) //aMets.addBitstream(new java.io.File(aRelativo), aAbsoluto, "ORIGINAL", true);
            {
                mets.addBitstream(unArchivo, aAbsoluto, "ORIGINAL", true);
            } else //aMets.addBitstream(new java.io.File(aRelativo), aAbsoluto, "ORIGINAL", false);
            {
                mets.addBitstream(unArchivo, aAbsoluto, "ORIGINAL", false);
            }
        }

        //System.out.println("pase por aca!.");
        // aggregamos la descripcion de los metadatos como JDOM element        
        List<Element> dcElt = myMakeMetadata();
        //System.out.println("pase por aca!. tamaño de dcElt: " + dcElt.size());
        mets.addDescriptiveMD("DC", dcElt);
        //sip.addDescriptiveMD("DC", myMakeMetadata2());

        // Write SIP to an output file}
        fileZIP = ficheros.getCarpeta(this.aParent);
        outfile = java.io.File.createTempFile("fichero", ".zip", fileZIP);

        //guardamos la ruta al zip para su posterior envio.
        File afileZIP = new File(outfile.getAbsolutePath());
        if (afileZIP.exists()) {
            fileZIP = afileZIP;
        }
        /*
        fileZIP = new java.io.File("/outDeposit");
        if (fileZIP.exists()) {
            outfile = java.io.File.createTempFile("deposito", ".zip", new java.io.File("/outDeposit"));
        } else {
            JFileChooser fc = new JFileChooser(".");
            fc.setDialogTitle("Selección de Directorio");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int respuesta = fc.showOpenDialog(null);
            if (respuesta == JFileChooser.APPROVE_OPTION) {
                //File directorioElegido = fc.getSelectedFile();
                fileZIP = fc.getSelectedFile(); //path absoluto?
                outfile = java.io.File.createTempFile("fichero", ".zip", fileZIP);
            }
        }
         */

        mets.write(outfile);

        //System.out.println("terminamos el mets!.");
    }

    public void crearMets_v2() throws MetsException, IOException, Exception {
        // Creamos un mets; validate = false, ZIP compression = BEST_SPEED
        mets = new MetsXml(false, Deflater.BEST_SPEED);
        // Optional: configurar el METS OBJID
        mets.setOBJID("Sword-Mets");
        // Optional: Set the METS creator
        mets.addAgent("CREATOR", "ORGANIZATION", "RIA - UTN - FRSF");
        //variable del archivo de salida.
        java.io.File outfile = null;
        // agregamos los objetos de aprendizaje al zip - el ultimo argumento es si "is Primary Bitstream".
        DefaultListModel<Fichero> aFiles = ficheros.getListaFicheros();
        for (int i = 0; i < aFiles.size(); ++i) {
            File unArchivo = aFiles.get(i).getUnFile();
            String aAbsoluto = aFiles.get(i).getUnFile().getAbsolutePath();
            if (i == 0) {
                mets.addBitstream(unArchivo, aAbsoluto, "ORIGINAL", true);
            } else {
                mets.addBitstream(unArchivo, aAbsoluto, "ORIGINAL", false);
            }
        }
        /* aggregamos la descripcion de los metadatos como JDOM element  */      
        List<Element> dcElt = myMakeMetadata();        
        mets.addDescriptiveMD("DC", dcElt);       
        /* Write SIP to an output file} */
        fileZIP = ficheros.getCarpeta(this.aParent);
        outfile = java.io.File.createTempFile("fichero", ".zip", fileZIP);
        /*guardamos la ruta al zip para su posterior envio.*/
        File afileZIP = new File(outfile.getAbsolutePath());
        if (afileZIP.exists()) {
            fileZIP = afileZIP;
        }       
        mets.write(outfile);
    }

    public Component getaParent() {
        return aParent;
    }

    public void setaParent(Component aParent) {
        this.aParent = aParent;
    }

}
