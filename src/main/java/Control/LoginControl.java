/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author Pogliani, german
 */
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.swordapp.client.AuthCredentials;
import org.swordapp.client.ClientConfiguration;
import org.swordapp.client.SWORDClient;
import org.swordapp.client.ServiceDocument;

public class LoginControl {
    
    Properties properties = null;
    InputStream propertiesStream = null;
    OutputStream output = null;
    
    // Sword - variables de seteo
    private int nroiri = 0;
    private String iri = "";
    private String usesw = "";
    private String passw = "";
    private String obo = "";    
    private Vector<String> servidores_sw = new Vector<>();    
    
    // StarDog - variables de seteo
    private String base = "";
    private String userst = "";
    private String passst = "";
    private String url = "";
    private Integer nrourl = 0;   
    private Vector<String> servidores_st = new Vector<>();
  
    // Variable de conexion a la base de datos de Stardog
    private static ReasoningConnection conexionStardog;
    
    //Variables de coneccion de Sword
    ServiceDocument sd; 
    private SWORDClient client = null;              

    private static LoginControl instancia = null;
    
    private LoginControl() {
        try {
            getProperty();
            setup_stardog();
            setup_sword();
            //conectarStardog();
            //ConectarSword();
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (Exception ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static synchronized LoginControl getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new LoginControl();
        }
        return instancia;
    }
    
     /**
     * Este metodo permite la conexión al servidor de StarDog.
     *
     * @throws StardogException
     */
    public void conectarStardog() throws StardogException {        
        conexionStardog = ConnectionConfiguration
                .to(this.base.trim())
                .credentials(this.userst.trim(), this.passst.trim())
                .server(this.url.trim())
                .reasoning(true)
                .connect()
                .as(ReasoningConnection.class);        
    }

    
    
    public static void desconectarServidor()  {
        conexionStardog.close();
    }
    
    public void ConectarSword() throws Exception {
        client = null; sd = null;
        client = new SWORDClient(new ClientConfiguration());
        sd = client.getServiceDocument(iri.trim(),
                new AuthCredentials(usesw.trim(), passw.trim()));
    }
    
    
    private void getProperty() throws IOException {
        properties = new Properties();
        propertiesStream = new FileInputStream("src/main/java/propiedades/login.properties");        
        properties.load(propertiesStream);
    }
    
    
    private void setup_stardog() throws IOException {       
        this.servidores_st.removeAllElements();
        this.nrourl = Integer.parseInt(properties.getProperty("nrourl").trim());
        for(int i=1;i<=this.nrourl;i++){
            servidores_st.addElement(properties.getProperty("url"+"_"+String.valueOf(i).trim()).trim());
        }
        this.url = properties.getProperty("url").trim();
        this.userst = properties.getProperty("stuser").trim();
        this.passst = properties.getProperty("stpass").trim();
        this.base = properties.getProperty("base").trim();
    }
 
    
    private void setup_sword() throws IOException {
        this.servidores_sw.removeAllElements();
        this.nroiri = Integer.parseInt(properties.getProperty("nroiri").trim());
        for(int i=1;i<=this.nroiri;i++){
            servidores_sw.addElement(properties.getProperty("iri"+"_"+String.valueOf(i).trim()).trim());
        }
        this.iri = properties.getProperty("iri").trim();
        this.usesw = properties.getProperty("swuser").trim();
        this.passw = properties.getProperty("swpass").trim();
        this.obo = properties.getProperty("obo").trim();
    }
    
    
    /*
    public void setUpSave(String aSdIRI, String aUser, String aPass, String aObo) 
            throws Exception {
        properties.setProperty("sdIRI", aSdIRI.trim());
        properties.setProperty("user", aUser.trim());
        File file = new File("src/main/java/propiedades/configSword.properties");
        if (file.exists()) {
            output = new FileOutputStream(file.getAbsoluteFile());
            properties.store(output, "datos conexion Sword v2");
        }
    }
    */

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public int getNroiri_sw() {
        return nroiri;
    }

    public void setNroiri_sw(int nroiri_sw) {
        this.nroiri = nroiri_sw;
    }

    public String getIri() {
        return iri;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    public String getUsesw() {
        return usesw;
    }

    public void setUsesw(String usesw) {
        this.usesw = usesw;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getObo() {
        return obo;
    }

    public void setObo(String obo) {
        this.obo = obo;
    }

    public Vector<String> getServidores_sw() {
        return servidores_sw;
    }

    public void setServidores_sw(Vector<String> servidores_sw) {
        this.servidores_sw = servidores_sw;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getUserst() {
        return userst;
    }

    public void setUserst(String userst) {
        this.userst = userst;
    }

    public String getPassst() {
        return passst;
    }

    public void setPassst(String passst) {
        this.passst = passst;
    }

    public String getUrl_st() {
        return url;
    }

    public void setUrl_st(String url_st) {
        this.url = url_st;
    }

    public Integer getNrourl_st() {
        return nrourl;
    }

    public void setNrourl_st(Integer nrourl_st) {
        this.nrourl = nrourl_st;
    }

    public Vector<String> getServidores_st() {
        return servidores_st;
    }

    public void setServidores_st(Vector<String> servidores_st) {
        this.servidores_st = servidores_st;
    }

    public ReasoningConnection getConexionStardog() {
        return conexionStardog;
    }

    public void setConexionStardog(ReasoningConnection conexionStardog) {
        LoginControl.conexionStardog = conexionStardog;
    }

    public ServiceDocument getSd() {
        return sd;
    }

    public void setSd(ServiceDocument sd) {
        this.sd = sd;
    }

    public SWORDClient getClient() {
        return client;
    }

    public void setClient(SWORDClient client) {
        this.client = client;
    }    
    
} //fin
