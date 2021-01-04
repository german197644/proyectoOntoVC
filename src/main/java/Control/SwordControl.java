/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import static org.junit.Assert.*;

import org.swordapp.client.AuthCredentials;
import org.swordapp.client.ClientConfiguration;
import org.swordapp.client.Deposit;
import org.swordapp.client.DepositReceipt;
import org.swordapp.client.SWORDClient;
import org.swordapp.client.SWORDCollection;
import org.swordapp.client.ServiceDocument;
import org.swordapp.client.UriRegistry;

import java.io.FileInputStream;
import java.util.List;
import org.swordapp.client.SWORDWorkspace;
import Modelo.Coleccion;
import Modelo.Fichero;
import Modelo.MetadataSimple;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.swordapp.client.EntryPart;
import org.swordapp.client.ProtocolViolationException;
import org.swordapp.client.SWORDClientException;
import org.swordapp.client.SWORDError;
import org.swordapp.client.SwordResponse;

public final class SwordControl {

    //String sdIRI = null;
    //String user = null;
    //String pass = null;
    //String obo = null;
    String file = null; //archivo que va a ingerir.
    String fileMd5 = null; //verifica que el archivo no haya sido modificado.
    //this.file  = "/home/richard/Code/External/JavaClient2.0/src/test/resources/example.zip";   
    //this.fileMd5  = DigestUtils.md5Hex(new FileInputStream(this.file));
    private final String METS = "http://purl.org/net/sword/package/METSDSpaceSIP";

    private static SwordControl instancia = null;
    ServiceDocument sd;
    private static final SWORDClient client = new SWORDClient(new ClientConfiguration());
    
    LoginControl login = LoginControl.getInstancia(); 
    
    protected SwordControl() throws Exception {
        //this.setearVariables();
        //this.getServiceDocument();
        this.sd = login.getSd();
    }

    /*
    public void setearVariables() throws IOException {
        ConexionSwordControl dd = ConexionSwordControl.getInstancia();
        this.sdIRI = dd.getSdIRI().trim();
        this.user = dd.getUser().trim();
        this.pass = dd.getPass().trim();
        this.obo = dd.getObo().trim();
    }
    */

    /**
     * Retorno una instancia unica de la clase.
     *
     * @return instancia
     * @throws java.lang.Exception
     */
    public static SwordControl getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new SwordControl();
        }
        return instancia;
    }

    /*    
    public void getServiceDocument() throws Exception {
        this.sd = client.getServiceDocument(this.sdIRI.trim(),
                new AuthCredentials(this.user.trim(), this.pass.trim()));
    }
    */

    public DefaultListModel getColecciones() throws Exception {
        DefaultListModel<Coleccion> colecciones = new DefaultListModel<>();

        //conectamos y traemos el service document.
        //this.sd = client.getServiceDocument(this.sdIRI,
        //        new AuthCredentials(this.user, this.pass));

        //assertTrue(sd.getService() != null);
        //assertEquals(sd.getVersion(), "2.0");
        // verificar que el documento de servicio contenga algo.
        if (sd.getService() != null) {
            List<SWORDWorkspace> ws = sd.getWorkspaces();
            for (int i = 0; i < ws.size(); ++i) {
                //System.out.println("Workspace" + "_i " + ":" + ws.get(i).getTitle());
                for (int j = 0; j < ws.get(i).getCollections().size(); ++j) {
                    Coleccion c = new Coleccion();
                    c.setColeccion(ws.get(i).getCollections().get(j));
                    colecciones.addElement(c);
                }
            }
        }
        return colecciones;
    }

    /**
     * obtiene el service document para usuarios no registrados que necesiten
     * revision de parte del administrador/es del repositorio. (OBO)
     *
     * @throws Exception
     */
    public void getServiceDocumentOBO()
            throws Exception {
        //this.sd = client.getServiceDocument(this.sdIRI,
        //        new AuthCredentials(this.user, this.pass, this.obo));
        // verify that the service document contains the sorts of things we are expecting
        assertTrue(sd.getService() != null);
        assertEquals(sd.getVersion(), "2.0");
    }

   

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public ServiceDocument getSd() {
        return sd;
    }

    /**
     * Visualiza el documento de servicio de SWORD.
     *
     *
     * @return Una cadena.
     *
     *
     * @throws Exception
     */
    public String outputServiceDocument()
            throws Exception {

        String sb = new String();
        if (sd == null) {
            sb = "--- Documento de Servicio es NULL --";
            return sb;
        }
        sb += "--- Interpretación del documento de servicio ---";
        sb += "\n Version: " + sd.getVersion();
        sb += "\n Max Upload Size: " + sd.getMaxUploadSize();
        List<SWORDWorkspace> sws = sd.getWorkspaces();
        for (SWORDWorkspace ws : sws) {
            sb += "\n Workspace: " + ws.getTitle();
            List<SWORDCollection> cols = ws.getCollections();
            for (SWORDCollection c : cols) {
                sb += "\n\tCollection: " + c.getTitle();
                sb += "\n\t\tPolicy: " + c.getCollectionPolicy();
                sb += "\n\t\tTreatment: " + c.getTreatment();
                sb += "\n\t\tURI: " + c.getHref();
                sb += "\n\t\tURI (Resolved): " + c.getResolvedHref();
                sb += "\n\t\tMediation Allowed: " + c.allowsMediation();
                List<String> packaging = c.getAcceptPackaging();
                for (String pack : packaging) {
                    sb += "\n\t\tAccepts Packaging: " + pack;
                }
                List<String> subservices = c.getSubServices();
                for (String ss : subservices) {
                    sb += "\n\t\tSub Service: " + ss;
                }
                List<String> maccepts = c.getMultipartAccept();
                for (String acc : maccepts) {
                    sb += "\n\t\tMultipart Accepts: " + acc;
                }
                List<String> accepts = c.getSinglepartAccept();
                for (String acc : accepts) {
                    sb += "\n\t\tAccepts: " + acc;
                }
                sb += "\n\t\tAccepts application/zip? " + 
                        c.singlepartAccepts("application/zip");
                sb += "\n\t\tMultipart Accepts application/zip? " + 
                        c.multipartAccepts("application/zip");
                sb += "\n\t\tAccepts Entry? " + c.singlepartAcceptsEntry();
                sb += "\n\t\tMultipart Accepts Entry? " + 
                        c.multipartAcceptsEntry();
                sb += "\n\t\tAccepts Nothing? " + c.acceptsNothing();
            }
        }
        return sb;
    }

    //Seteamos los metadatos capturados.
    private EntryPart setearMetadatos() throws Exception {
        EntryPart ep = new EntryPart();
        DefaultListModel<MetadataSimple> captura = new DefaultListModel<>();
        DublinCoreControl dc = DublinCoreControl.getInstancia();

        captura = StardogControl.getInstancia().getCapturaMetadados();

        for (int i = 0; i < captura.size(); ++i) {
            final String metadato = captura.get(i).getTipo();
            //buscamos la equivalencia de la ontología en Dublin Core.
            final String equiDC = dc.buscarEquivalencias(metadato);
            //System.out.println(metadato + " | " + algo);
            if (!equiDC.contains(",")) {
                final String dato = captura.get(i).getContenidoMetadato();
                //System.out.println(algo + " | " + dato);
                ep.addDublinCore(equiDC, dato);
            } else {
                //en el caso de recibir un intervalo
                //System.out.println("StringTokenizer");
                StringTokenizer st = new StringTokenizer(equiDC, ",");
                //correvoramos si el intervalo tiene el token.
                String aDato = captura.get(i).getContenidoMetadato();
                if (aDato.contains("-")) {
                    String[] intervalo = aDato.split("-");
                    i = 0;
                    while (st.hasMoreTokens()) {
                        //System.out.println(aux.nextToken());
                        ep.addDublinCore(st.nextToken(), intervalo[i]);
                        i++;
                    }
                }
            }
        }
        return ep;
    }

    /**
     * Realiza un deposito de un archivo solo sin empaquetar en un ZIP mas 
     * sus metadatos resuperando el deposito original.el deposito no es atómico.
     *
     * @param aCol
     * @param parent
     * @throws Exception
     */
    public void depositoDeFicheroSimple(SWORDCollection aCol, Component parent)
            throws Exception {

        File fichero = null;
        String aFormat = null;

        StardogControl stdog = StardogControl.getInstancia();
        aFormat = stdog.isExistsFormat();
        //System.out.println("aFormat:------------------- " + aFormat);
        if (aFormat == null) {
            //salimos.            
            //aValue += "\n |-> Formato inexistente.";
            //aValue += "\n Operación de deposito avanzado sin paquete cancelada.";
            //si no se selecciona un archivo.
            return;
        }

        /* seteamos el archivo ZIP que contiene(n) nuestro(s) OA(s) */
        if (FicheroControl.getInstancia().getListaFicheros().size() != 1) {
            //aValue += "\n |-> Fichero nulo.";
            //aValue += "\n Operación de deposito avanzado sin paquete cancelada.";
            return;
        }

        fichero = ((Fichero) FicheroControl.getInstancia().getListaFicheros().get(0)).getUnFile();

        Deposit deposit = new Deposit();
        deposit.setInProgress(true);
        this.file = fichero.getAbsolutePath();
        deposit.setFile(new FileInputStream(this.file));
        deposit.setMimeType(aFormat);
        deposit.setFilename(fichero.getName());
        deposit.setPackaging(UriRegistry.PACKAGE_BINARY);

        //garantizamos que se reciba el mismo que se envio.
        this.fileMd5 = DigestUtils.md5Hex(new FileInputStream(this.file));
        deposit.setMd5(this.fileMd5);
        deposit.setSuggestedIdentifier("depositSwordClient-SimpleBinary-madeInPFC");

        /*controlamos*/
        assertTrue(deposit.isBinaryOnly());

        /*depositamos*/
        DepositReceipt receipt = client.deposit(aCol, deposit,
                new AuthCredentials(login.getUser_sw(), login.getPass_sw()));

        /*controlamos*/
        assertEquals(receipt.getStatusCode(), 201);
        assertTrue(receipt.getLocation() != null);

        /*seteamos los metadatos*/
        EntryPart nep = new EntryPart();
        nep = setearMetadatos();

        /*recuperamos anterior y actualizamos*/
        receipt = client.getDepositReceipt(receipt.getLocation(), new AuthCredentials(login.getUser_sw(), login.getPass_sw()));
        Deposit replacement = new Deposit();
        replacement.setEntryPart(nep);

        SwordResponse resp = client.replace(receipt, replacement, new AuthCredentials(login.getUser_sw(), login.getPass_sw()));

        /*controlamos*/
        assertTrue((resp.getStatusCode() == 200) || (resp.getStatusCode() == 204));

    }

    /**
     * Envia al repositorio archivos binarios sin zipear mas sus metadatos
     * recuperando los enviaos originales de los archivos.
     * la operación no es atómica.
     * 
     * @param aCol
     * @param parent
     * @throws Exception
     */
    public void depositoDeFicheroSimple_repetitivo(SWORDCollection aCol, Component parent)
            throws Exception {

        File fichero = null;
        String aFormat = null;
        DefaultListModel<Fichero> archivos;

        StardogControl stdog = StardogControl.getInstancia();
        aFormat = stdog.isExistsFormat();
        if (aFormat == null) {
            return;
        }

        if (FicheroControl.getInstancia().getListaFicheros().size() == 0) {
            return;
        } else {
            archivos = FicheroControl.getInstancia().getListaFicheros();
        }

        /*seteamos los metadatos*/
        EntryPart nep = new EntryPart();
        nep = setearMetadatos();

        for (int i = 0; i < archivos.size(); ++i) {
            fichero = ((Fichero) archivos.get(0)).getUnFile();

            Deposit deposit = new Deposit();
            deposit.setInProgress(true);
            this.file = fichero.getAbsolutePath();
            deposit.setFile(new FileInputStream(this.file));
            deposit.setMimeType(aFormat);
            deposit.setFilename(fichero.getName());
            deposit.setPackaging(UriRegistry.PACKAGE_BINARY);
            //garantizamos que se reciba el mismo que se envio.
            this.fileMd5 = DigestUtils.md5Hex(new FileInputStream(this.file));
            deposit.setMd5(this.fileMd5);
            //deposit.setSuggestedIdentifier("1234567890");
            /*controlamos*/
            assertTrue(deposit.isBinaryOnly());
            /*depositamos*/
            DepositReceipt receipt = client.deposit(aCol, deposit,
                    new AuthCredentials(login.getUser_sw(), login.getPass_sw()));
            /*controlamos*/
            assertEquals(receipt.getStatusCode(), 201);
            assertTrue(receipt.getLocation() != null);
            /*recuperamos anterior y actualizamos*/
            receipt = client.getDepositReceipt(receipt.getLocation(), new AuthCredentials(login.getUser_sw(), login.getPass_sw()));
            Deposit replacement = new Deposit();
            replacement.setEntryPart(nep);
            SwordResponse resp = client.replace(receipt, replacement, new AuthCredentials(login.getUser_sw(), login.getPass_sw()));
            /*controlamos*/
            assertTrue((resp.getStatusCode() == 200) || (resp.getStatusCode() == 204));
        }
    }

    /**
     * Simula multiPart. Deposita el ZIP mas mets.xml
     *
     * @param aCol Coleccion a la que se le manda el archivo zip.
     * @param parent sobre que ventana mostramos las ventana de error.
     * @throws Exception
     */
    public void depositoMetsZip(SWORDCollection aCol, Component parent)
            throws Exception {

        File fzip = null; //ZIP        

        fzip = MetsControl.getInstancia().getFileZIP();
        if (fzip == null) {            
            return;
        }

        //iniciamos el seteo para el deposito.
        Deposit deposit = new Deposit();
        this.file = fzip.getAbsolutePath();
        deposit.setFile(new FileInputStream(this.file));
        deposit.setMimeType("application/zip");
        deposit.setFilename(fzip.getName());
        //deposit.setPackaging(UriRegistry.PACKAGE_SIMPLE_ZIP);
        deposit.setPackaging(this.METS);
        this.fileMd5 = DigestUtils.md5Hex(new FileInputStream(this.file));
        deposit.setMd5(this.fileMd5);
        deposit.setInProgress(true);
        deposit.setSuggestedIdentifier("1234567890");

        assertTrue(deposit.isBinaryOnly());

        DepositReceipt receipt = client.deposit(aCol, deposit,
                new AuthCredentials(login.getUser_sw(), login.getPass_sw()));

        //controlamos        
        assertTrue((receipt.getStatusCode() == 201) && (receipt.getLocation() != null));

        /* 
        enviamos los metadatos 
         */
        //receipt = client.getDepositReceipt(receipt.getLocation(), new AuthCredentials(this.user, this.pass));
        //EntryPart nep = new EntryPart();
        /*
        nep.addDublinCore("creator", "Pogliani, German Dario envio 2");
        nep.addDublinCore("contributor", "Miriam Arrua Soledad");
        nep.addDublinCore("title", "El señor de los anillos");
        nep.addDublinCore("alternative", "Don Quijote_v2");
        nep.addDublinCore("abstract", "algo sobre el archivo");
        nep.addDublinCore("identifier", "http://localhost:8080/");
        nep.addDublinCore("date", "2019-11-27");
        nep.addDublinCore("subject", "tema del contenido del recurso.");
         */
 /*
        //seteamos los metadatos capturados.
        nep = setearMetadatos();

        //seteamos los metadatos capturados.
        //nep = setearMetadatos();
        //generamos el deposito de los metadatos.
        Deposit replacement = new Deposit();
        replacement.setEntryPart(nep);

        SwordResponse resp = client.replace(receipt, replacement, new AuthCredentials(this.user, this.pass));

        assertTrue((resp.getStatusCode() == 200 || resp.getStatusCode() == 204));
         */
    }

    /**
     * Depostia un archivo ZIP con los ficheros seleccionados en el Panel Sword.
     * Este deposito es Binario con metadatos y se realiza en dos poartes. Parte
     * 1: se envia el archivo ZIP. Parte 2: se recupera dicho depostio y se le
     * adjunta los metadatos.
     *
     * @param aCol
     * @param parent
     * @throws SWORDClientException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SWORDError
     * @throws ProtocolViolationException
     * @throws Exception
     */
    public void depositoZipMasMetadatos(SWORDCollection aCol, Component parent)
            throws SWORDClientException, FileNotFoundException, IOException, SWORDError, ProtocolViolationException, Exception {

        File ficheroZip;

        /* 
        seteamos el archivo ZIP que contiene(n) nuestro(s) item(s).        
         */
        this.file = FicheroControl.getInstancia().getFolderZip();

        if (this.file == null) {
            return;
        }

        ficheroZip = new File(this.file);

        if (!ficheroZip.exists()) {
            return;
        }

        //iniciamos el seteo para el deposito
        Deposit deposit = new Deposit();
        deposit.setFile(new FileInputStream(this.file));
        deposit.setMimeType("application/zip");
        deposit.setFilename(ficheroZip.getName());
        deposit.setPackaging(UriRegistry.PACKAGE_BINARY);
        //garantizamos que se reciba el mismo que se envio.
        this.fileMd5 = DigestUtils.md5Hex(new FileInputStream(this.file));
        deposit.setMd5(this.fileMd5);
        deposit.setInProgress(true);
        deposit.setSuggestedIdentifier("depositSwordClient-zip-madeInPFC");

        /*controlamos*/
        assertTrue(deposit.isBinaryOnly());

        /*depositamos*/
        DepositReceipt receipt = client.deposit(aCol, deposit,
                new AuthCredentials(login.getUser_sw(), login.getPass_sw()));

        /*controlamos*/
        assertEquals(receipt.getStatusCode(), 201);
        assertTrue(receipt.getLocation() != null);

        /* enviamos los metadatos */
        receipt = client.getDepositReceipt(receipt.getLocation(), new AuthCredentials(login.getUser_sw(), login.getPass_sw()));

        EntryPart nep = new EntryPart();

        //seteamos los metadatos capturados.
        nep = setearMetadatos();

        //generamos el deposito de los metadatos.
        Deposit replacement = new Deposit();
        replacement.setEntryPart(nep);

        SwordResponse resp = client.replace(receipt, replacement, new AuthCredentials(login.getUser_sw(), login.getPass_sw()));

        assertTrue((resp.getStatusCode() == 200 || resp.getStatusCode() == 204));

    }

}
