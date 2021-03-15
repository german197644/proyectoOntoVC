/**
 *
 * @author Pogliani, German
 *
 */
package Control;

import Modelo.Fichero;
import Modelo.Metadato;
import edu.harvard.hul.ois.mets.helper.MetsException;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import javax.swing.DefaultListModel;
import org.jdom.Element;
import org.jdom.Namespace;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MetsControler {

    private SIPControler mets; //clase que genera el mets.xml
    private FicheroControler ficheros;
    private File rutaFileZIP; //archivo donde depositar el zip con el mets.
    private String nomFileZip;
    private Component aParent;

    private static MetsControler instancia = null;

    /**
     *
     * @throws Exception
     */
    private MetsControler() throws Exception {
    }

    public static MetsControler getInstancia() {
        try {
            if (instancia == null) {
                instancia = new MetsControler();
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
    public File getRutaFileZIP() {
        return rutaFileZIP;
    }

    //encapsulamos los metadatos.
    private List myMakeMetadata() throws Exception {

        List<Element> aListEle = new ArrayList<>();
        StardogControler ontologia = StardogControler.getInstancia();
        DublinCoreControler dublincore = DublinCoreControler.getInstancia();
        DefaultListModel<Metadato> aListMetadatos = ontologia.getCapturaMetadados();
        final String uri = "http://purl.org/dc/elements/1.1/";

        for (int i = 0; i < aListMetadatos.size(); ++i) {
            Metadato m = aListMetadatos.get(i);
            //System.out.println("pase por aca!. metadato: " + m.getTipo());
            Object aDC = dublincore.getEquivalenciaDC(m.getTipo().toLowerCase().trim());
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
                        //final String uri = "http://purl.org/dc/elements/1.1/";
                        //Element t = new Element(aux.nextToken(), "dcterms", uri);
                        Element t = new Element(aux.nextToken(), "dc", uri + aux.nextToken());
                        //t.setText(m.getDataContenedor());
                        //System.out.println("entre al WHILE: " + aux.nextToken());
                        t.setText(result[i]);
                        aListEle.add(t); //revisar
                        i++;
                    }
                } else {
                    //titulo, creator, etc. ...    
                    // Esto de abajo no sirve, hay que mejorar ...
                    final String auxDC = ((String) aDC);
                    //System.out.println("entre al else " + auxDC);
                    //final String uri = "http://purl.org/dc/terms/" + auxDC;
                    //final String uri = "http://purl.org/dc/elements/1.1/";
                    Element t = new Element(auxDC, "dc", uri + auxDC);
                    t.setText(m.getContenidoMetadato());

                    //t.setText(m.getContenidoMetadato());
                    //t = t.setAttribute(auxDC, m.getContenidoMetadato());
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
    public void newMETS() throws MetsException, IOException, Exception {

        // Creamos un mets; validate = false, ZIP compression = BEST_SPEED
        mets = new SIPControler(false, Deflater.BEST_SPEED);

        // Optional: configurar el METS OBJID
        mets.setOBJID("Sword-Mets-Ontologico");

        // Optional: Set the METS creator
        mets.addAgent("CREATOR", "ORGANIZATION", "RIA - UTN - FRSF");

        //variable del archivo de salida.
        java.io.File outfile = null;

        // agregamos los objetos de aprendizaje al zip - el ultimo argumento es si "is Primary Bitstream".
        ficheros = FicheroControler.getInstancia();
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

        // aggregamos la descripcion de los metadatos como JDOM element        
        List<Element> dcElt = myMakeMetadata();

        mets.addDescriptiveMD("DC", dcElt, "Metadatos Dublin Core");
        //sip.addDescriptiveMD("DC", myMakeMetadata2());

        // escribimos el SIPControler a un fichero de salida
        //fileZIP = ficheros.getCarpeta(this.aParent);
        //System.getProperty("java.io.tmpdir");
        outfile = java.io.File.createTempFile("ficheroMETS_", ".zip");

        //guardamos la ruta al zip para su posterior envio.
        File afileZIP = new File(outfile.getAbsolutePath());
        if (afileZIP.exists()) {
            rutaFileZIP = afileZIP;
            nomFileZip = afileZIP.getName();
        }
        mets.write(outfile);
    }

    public String getNomFileZip() {
        return nomFileZip;
    }

    public void crearMets_v2() throws MetsException, IOException, Exception {
        // Creamos un mets; validate = false, ZIP compression = BEST_SPEED
        mets = new SIPControler(false, Deflater.BEST_SPEED);
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
        mets.addDescriptiveMD("DC", dcElt, "Metadatos Dublin Core");
        /* Write SIPControler to an output file} */
        rutaFileZIP = ficheros.getCarpeta(this.aParent);
        outfile = java.io.File.createTempFile("fichero", ".zip", rutaFileZIP);
        /*guardamos la ruta al zip para su posterior envio.*/
        File afileZIP = new File(outfile.getAbsolutePath());
        if (afileZIP.exists()) {
            rutaFileZIP = afileZIP;
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
