/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author Pogliani, German
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigControl {

    Properties properties = new Properties();

    InputStream propertiesStream = null;
    OutputStream output = null;

    // Rest - variables de seteo
    private int nrouri = 0;
    private String uri = "";
    private String useRest = "";
    private String passRest = "";
    //private String obo = "";
    private Vector<String> servidores_rest = new Vector<>();

    // StarDog - variables de seteo
    private String base = "";
    private String userst = "";
    private String passst = "";
    private String url = "";
    private Integer nrourl = 0;
    private Vector<String> servidores_st = new Vector<>();

    // Configuracion general.
    private String folderWork = "";
    //private String folderPropiedades = "E:\\Temp\\propiedades\\";
    private String folderPropiedades = "";
    private String handle = "";

    private static ConfigControl instancia = null;

    private ConfigControl() {
        getConfigInicio();        
    }

    public static synchronized ConfigControl getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ConfigControl();
        }
        return instancia;
    }

    public void getConfigInicio() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/config.properties");
            URI uri = new URI(inputURL.toString());
            // No fijamos si existe el archivo de configuracion.
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public Properties getConfigMetadatos() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/configMetadatos.properties");
            URI uri = new URI(inputURL.toString());
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    public Properties getConfigValidacion() throws IOException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/configValidacion.properties");
            URI uri = new URI(inputURL.toString());
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    public Properties getConfigDublinCore() throws IOException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/configDublinCore.properties");
            URI uri = new URI(inputURL.toString());
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    /**
     * Setea las variables de conexion de la base grafica.
     */
    public void setup_stardog() {
        getConfigInicio();
        if (properties == null) {                        
            return;
        }
        this.servidores_st.removeAllElements();
        this.nrourl = Integer.parseInt(properties.getProperty("nrourl").trim());
        for (int i = 1; i <= this.nrourl; i++) {
            servidores_st.addElement(properties.getProperty("url_" + String.valueOf(i).trim()).trim());
        }
        this.url = properties.getProperty("url").trim();
        this.userst = properties.getProperty("stuser").trim();
        this.passst = properties.getProperty("stpass").trim();
        this.base = properties.getProperty("base").trim();
    }

    public void setup_dspace() throws IOException {
        getConfigInicio();
        if (properties == null) {
            return;
        }
        this.servidores_rest.removeAllElements();
        this.nrouri = Integer.parseInt(properties.getProperty("nrouri").trim());
        for (int i = 1; i <= this.nrouri; i++) {
            servidores_rest.addElement(properties.getProperty("uri" + "_" + String.valueOf(i).trim()).trim());
        }
        this.uri = properties.getProperty("uri").trim();
        this.useRest = properties.getProperty("restUser").trim();
        this.passRest = properties.getProperty("restPass").trim();
        //this.obo = properties.getProperty("obo").trim();
    }

    public void setup_general() {
        getConfigInicio();
        if (properties == null) {
            return;
        }
        this.folderWork = properties.getProperty("folderwork").trim();
        this.folderPropiedades = properties.getProperty("folderProperty").trim();
        this.handle = properties.getProperty("handle").trim();
    }

    //public void grabar_url_st(String aSdIRI, String aUser, String aPass, String aObo) 
    public void grabarUrlSt(String url) throws Exception {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/config.properties");
            URI uri = new URI(inputURL.toString());
            // No fijamos si existe el archivo de configuracion.
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
                OutputStream fos = new FileOutputStream(uri.getPath());
                //
                nrourl = Integer.parseInt(properties.getProperty("nrourl"));
                properties.setProperty("nrourl", String.valueOf(nrourl + 1));
                properties.setProperty("url_" + (nrourl + 1), url);
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarUriSw(String miuri) throws Exception {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/config.properties");
            URI uri = new URI(inputURL.toString());
            // No fijamos si existe el archivo de configuracion.
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
                OutputStream fos = new FileOutputStream(uri.getPath());
                //
                nrouri = Integer.parseInt(properties.getProperty("nrouri"));
                properties.setProperty("nrouri", String.valueOf(nrouri + 1));
                properties.setProperty("uri_" + (nrourl + 1), miuri);
                // Grabamos en la propiedad.
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarFolder(String folder) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/config.properties");
            URI uri = new URI(inputURL.toString());
            // No fijamos si existe el archivo de configuracion.
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
                //
                OutputStream fos = new FileOutputStream(uri.getPath());
                properties.setProperty("folderwork", folder);
                properties.store(fos, "Parametros de la Configuración general");
                fos.flush();
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarFolderProperty(String folder) {
        System.out.println(this.folderPropiedades + "config.properties");
        File file = new File(this.folderPropiedades + "config.properties");
        if (file.exists()) {
            try {
                properties.setProperty("folderProperty", folder);
                output = new FileOutputStream(file.getAbsoluteFile());
                try {
                    properties.store(output, "datos login-general v2");
                } catch (IOException ex) {
                    Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.folderPropiedades = folder;
            System.out.println("No existe archivo. Se seteo uno por defecto");
        }
    }

    public void grabarHandle(String unHandle) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/config.properties");
            URI uri = new URI(inputURL.toString());
            // No fijamos si existe el archivo de configuracion.
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
                //
                OutputStream fos = new FileOutputStream(uri.getPath());
                properties.setProperty("handle", unHandle);
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarBase(String miBase) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/config.properties");
            URI uri = new URI(inputURL.toString());
            // No fijamos si existe el archivo de configuracion.
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
                OutputStream fos = new FileOutputStream(uri.getPath());
                //
                properties.setProperty("base", miBase);
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFolderWork(String path) {
        //System.out.println("caracter setFolderWork(): " + path.charAt(path.length() - 1));
        if ((path.length() > 3) && (path.charAt(path.length() - 1) != '\\')) {
            folderWork = path.trim() + File.separator;
        } else {
            folderWork = path.trim();
        }
        //System.out.println("Control.ConfigControl.getFolderWork(): " + folderWork);
    }

    public String getFolderWork() {
        return folderWork.trim();
    }

    public String getHandle() {
        return handle;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public int getNroiri_sw() {
        return nrouri;
    }

    public void setNroiri_sw(int nroiri_sw) {
        this.nrouri = nroiri_sw;
    }

    public String getUri() {
        return uri.trim();
    }

    public void setUri(String uri) {
        this.uri = uri.trim();
    }

    public String getUseRest() {
        return useRest;
    }

    public void setUseRest(String useRest) {
        this.useRest = useRest;
    }

    public String getPassRest() {
        return passRest;
    }

    public void setPassRest(String passRest) {
        this.passRest = passRest;
    }

    public Vector<String> getServidores_rest() {
        return servidores_rest;
    }

    public void setServidores_rest(Vector<String> servidores_rest) {
        this.servidores_rest = servidores_rest;
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

    public String getFolderPropiedades() {
        return folderPropiedades;
    }

    public void setFolderPropiedades(String path) {
        if ((path.length() > 3) && (path.charAt(path.length() - 1) != '\\')) {
            this.folderPropiedades = path.trim() + File.separator;
        } else {
            this.folderPropiedades = path.trim();
        }
    }

} //fin
