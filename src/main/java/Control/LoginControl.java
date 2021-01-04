/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author germa
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

public final class LoginControl {
    
    Properties properties = null;
    InputStream propertiesStream = null;
    OutputStream output = null;
    
    // Sword - variables de seteo
    int nroiri_sw = 0;
    private String sdiri_sw = "";
    private String user_sw = "";
    private String pass_sw = "";
    private String obo = "";    
    private Vector<String> servidores_sw = new Vector<>();    
    
    // StarDog - variables de seteo
    private String base = "";
    private String user_st = "";
    private String pass_st = "";
    private String url_st = "";
    private Integer nrourl_st = 0;   
    private Vector<String> servidores_st = new Vector<>();
  
    // Variable de conexion a la base de datos
    private static ReasoningConnection conexionStardog;
    
    //Variables de coneccion de Sword
    ServiceDocument sd; 
    private SWORDClient client = null;              

    private static LoginControl instancia = null;
    
    public LoginControl() {
        try {
            getProperty();
            setup_stardog();
            setup_sword();
            conectarStardog();
            ConectarSword();
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (Exception ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static LoginControl getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new LoginControl();
        }
        return instancia;
    }
    
     /**
     * Este metodo permite la conexión al servidor de StarDog.
     *
     * @throws StardogException
     * @throws java.lang.Exception
     */
    private void conectarStardog() throws StardogException {        
        conexionStardog = ConnectionConfiguration
                .to(this.base.trim())
                .credentials(this.user_st.trim(), this.pass_st.trim())
                .server(this.url_st.trim())
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
        sd = client.getServiceDocument(sdiri_sw.trim(),
                new AuthCredentials(user_sw.trim(), pass_sw.trim()));
    }
    
    
    private void getProperty() throws IOException {
        properties = new Properties();
        propertiesStream = new FileInputStream("src/main/java/propiedades/login.properties");        
        properties.load(propertiesStream);
    }
    
    
    private void setup_stardog() throws IOException {       
        this.servidores_st.removeAllElements();
        this.nrourl_st = Integer.parseInt(properties.getProperty("sturl").trim());
        for(int i=1;i<=this.nrourl_st;i++){
            servidores_st.addElement(properties.getProperty("sturl"+"_"+String.valueOf(i).trim()).trim());
        }
        this.url_st = properties.getProperty("sturl").trim();
        this.user_st = properties.getProperty("stuser").trim();
        this.pass_st = properties.getProperty("stpass").trim();
        this.base = properties.getProperty("base").trim();
    }
 
    
    private void setup_sword() throws IOException {
        this.servidores_sw.removeAllElements();
        this.nroiri_sw = Integer.parseInt(properties.getProperty("swnroiri").trim());
        for(int i=1;i<=this.nroiri_sw;i++){
            servidores_sw.addElement(properties.getProperty("swsdiri"+"_"+String.valueOf(i).trim()).trim());
        }
        this.sdiri_sw = properties.getProperty("swsdiri").trim();
        this.user_sw = properties.getProperty("swuser").trim();
        this.pass_sw = properties.getProperty("swpass").trim();
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
        return nroiri_sw;
    }

    public void setNroiri_sw(int nroiri_sw) {
        this.nroiri_sw = nroiri_sw;
    }

    public String getSdiri_sw() {
        return sdiri_sw;
    }

    public void setSdiri_sw(String sdiri_sw) {
        this.sdiri_sw = sdiri_sw;
    }

    public String getUser_sw() {
        return user_sw;
    }

    public void setUser_sw(String user_sw) {
        this.user_sw = user_sw;
    }

    public String getPass_sw() {
        return pass_sw;
    }

    public void setPass_sw(String pass_sw) {
        this.pass_sw = pass_sw;
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

    public String getUser_st() {
        return user_st;
    }

    public void setUser_st(String user_st) {
        this.user_st = user_st;
    }

    public String getPass_st() {
        return pass_st;
    }

    public void setPass_st(String pass_st) {
        this.pass_st = pass_st;
    }

    public String getUrl_st() {
        return url_st;
    }

    public void setUrl_st(String url_st) {
        this.url_st = url_st;
    }

    public Integer getNrourl_st() {
        return nrourl_st;
    }

    public void setNrourl_st(Integer nrourl_st) {
        this.nrourl_st = nrourl_st;
    }

    public Vector<String> getServidores_st() {
        return servidores_st;
    }

    public void setServidores_st(Vector<String> servidores_st) {
        this.servidores_st = servidores_st;
    }

    public static ReasoningConnection getConexionStardog() {
        return conexionStardog;
    }

    public static void setConexionStardog(ReasoningConnection conexionStardog) {
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
