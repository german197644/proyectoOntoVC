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
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
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
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
    private String reporte = "";
    private String filtro = "";

    private static ConfigControl instancia = null;

    private ConfigControl() {
        //getConfigInicio();
    }

    public static synchronized ConfigControl getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ConfigControl();
        }
        return instancia;
    }

    public void getConfigInicio() {
        URL inputURL = null;
        URI uri = null;
        ClassLoader classLoader = null;
        //properties = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            //System.out.println("1");            
            inputURL = classLoader.getResource("propiedades/config.properties");
            //System.out.println("2");
            if (inputURL != null) {
                uri = new URI(inputURL.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra el archivo.\n");
                properties = null;
                return;
            }
            // No fijamos si existe el archivo de configuracion.            
            File file = new File(uri.getPath());
            if (file.exists()) {
                //System.out.println("PATH: " + uri.getPath());
                properties = new Properties();
                propertiesStream = new FileInputStream(uri.getPath());
                properties.load(propertiesStream);
            } else {
                properties = null;
            }
        } catch (HeadlessException | IOException | URISyntaxException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties getConfigMetadatos() {
        try {
            URI uri = null;
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/configMetadatos.properties");
            
            if (inputURL != null) {
                uri = new URI(inputURL.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra el archivo.\n");
                properties = null;
                return properties; // Paro la ejecución de este método.
            }
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties = new Properties();
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
                properties = new Properties();
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
            URI uri = null;
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            URL inputURL = classLoader.getResource("propiedades/configDublinCore.properties");
            if (inputURL != null) {
                uri = new URI(inputURL.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra el archivo.\n");
                properties = null;
                return null;
            }
            //URI uri = new URI(inputURL.toString());
            File file = new File(uri.getPath());
            if (file.exists()) {
                propertiesStream = new FileInputStream(uri.getPath());
                properties = new Properties();
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
        //getConfigInicio();
        if (properties == null) {
            return;
        }
        String auxnro = properties.getProperty("nrourl").trim();
        if ((auxnro != null) && !(auxnro.isEmpty())) {
            this.servidores_st.removeAllElements();
            this.nrourl = Integer.parseInt(auxnro);
            for (int i = 1; i <= this.nrourl; i++) {
                String indice = "url_" + String.valueOf(i).trim();
                String dato = properties.getProperty(indice).trim();
                if ((dato != null) && (!dato.isEmpty())) {
                    servidores_st.addElement(dato);
                }
            }
            this.url = properties.getProperty("url").trim();
            this.userst = properties.getProperty("stuser").trim();
            this.passst = properties.getProperty("stpass").trim();
            this.base = properties.getProperty("base").trim();
        }
    }

    public void setup_dspace() throws IOException {
        //getConfigInicio();
        if (properties == null) {
            return;
        }
        String auxnro = properties.getProperty("nrouri").trim();
        if ((auxnro != null) && !(auxnro.isEmpty())) {
            this.servidores_rest.removeAllElements();
            this.nrouri = Integer.parseInt(auxnro);
            for (int i = 1; i <= this.nrouri; i++) {
                String indice = "uri_" + String.valueOf(i).trim();
                String dato = properties.getProperty(indice).trim();
                if ((dato != null) && (!dato.isEmpty())) {
                    servidores_rest.addElement(dato);
                }
            }
            this.uri = properties.getProperty("uri").trim();
            this.useRest = properties.getProperty("restUser").trim();
            this.passRest = properties.getProperty("restPass").trim();
            //this.obo = properties.getProperty("obo").trim();
        }
    }

    public void setup_general() {
        //getConfigInicio();
        if (properties == null) {
            return;
        }
        this.folderWork = properties.getProperty("folderwork").trim();
        this.folderPropiedades = properties.getProperty("folderProperty").trim();
        this.handle = properties.getProperty("handle").trim();
        this.reporte = properties.getProperty("reporte").trim();
        this.filtro = properties.getProperty("filtro").trim();
    }

    //public void grabar_url_st(String aSdIRI, String aUser, String aPass, String aObo) 
    /**
     *
     * @param url Nueva URL que aloja el servicio de Stardog.
     * @param evt Evento que llama al método.
     */
    public void grabarUrlSt(String url, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                String indice = String.valueOf(nrourl + 1);
                properties.setProperty("nrourl", indice);
                properties.setProperty("url_" + indice, url);
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescar_url_stardog(ActionEvent evt) {
        try {
            //Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                // cantidad de servidores actuales despues de eliminar.
                try (OutputStream fos = new FileOutputStream(uri.getPath())) {
                    // cantidad de servidores actuales despues de eliminar.
                    int cantURL = this.servidores_st.size();
                    properties.setProperty("nrourl", String.valueOf(cantURL));
                    for (int i = 1; i <= cantURL; i++) {
                        String clave = "url_" + String.valueOf(i);
                        String valor = this.servidores_st.get(i - 1);
                        properties.setProperty(clave, valor);
                    }
                    properties.store(fos, "Parámetros de la Configuración general");
                    fos.flush();
                    //JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
                }
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refrescar_uri_dspace(ActionEvent evt) {
        try {
            //Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                // cantidad de servidores actuales despues de eliminar.
                try (OutputStream fos = new FileOutputStream(uri.getPath())) {
                    // cantidad de servidores actuales despues de eliminar.
                    int cantURI = this.servidores_rest.size();
                    properties.setProperty("nrouri", String.valueOf(cantURI));
                    for (int i = 1; i <= cantURI; i++) {
                        String clave = "uri_" + String.valueOf(i);
                        String valor = this.servidores_rest.get(i - 1);
                        properties.setProperty(clave, valor);
                    }
                    properties.store(fos, "Parámetros de la Configuración general");
                    fos.flush();
                    fos.close();
                    //JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
                }
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void quitarUrlSt(int indice, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                String id = String.valueOf(indice);
                properties.setProperty("url_" + id, "");
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void quitarUriSw(int indice, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                String id = String.valueOf(indice);
                properties.setProperty("uri_" + id, "");
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
                fos.close();
                //JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarUriSw(String miuri, ActionEvent evt) throws Exception {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                String NewUriValor = String.valueOf(nrouri + 1);
                //System.out.println("NewUriValor : " + NewUriValor);
                properties.setProperty("nrouri", NewUriValor);
                String uri_aux = "uri_" + NewUriValor;
                //System.out.println("uri_aux : " + uri_aux + "    MiURI: " + miuri);
                properties.setProperty(uri_aux, miuri);
                // Grabamos en la propiedad.
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarFolder(String folder, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
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

    public void grabarHandle(String unHandle, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarReporte(String aReporte, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                properties.setProperty("reporte", aReporte);
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarFiltro(String aReporte, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                properties.setProperty("filtro", aReporte);
                properties.store(fos, "Parámetros de la Configuración general");
                fos.flush();
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
            } else {
                properties = null;
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ConfigControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void grabarBase(String miBase, ActionEvent evt) {
        try {
            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
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
                fos.close();
                JOptionPane.showMessageDialog(win, "Operación concluida con éxito.");
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

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

} //fin
