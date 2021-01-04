
package Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author germa
 */
public final class ConexionSwordControl {

    int nroIRI = 0;
    String sdIRI = "";
    String user = "";
    String pass = "";
    String obo = "";    
    Vector<String> servidores = new Vector<>();


    static ConexionSwordControl instancia = null;

    static Properties properties = null;
    static InputStream propertiesStream = null;
    static OutputStream output = null;

    protected ConexionSwordControl() throws IOException {
        this.conectorProperty();        
        this.setUpRead();
    }

    public static ConexionSwordControl getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ConexionSwordControl();
        }
        return instancia;
    }

    private void conectorProperty() throws IOException {
        properties = new Properties();
        propertiesStream = new FileInputStream("src/main/java/propiedades/configSword.properties");        
        properties.load(propertiesStream);
    }

    public void setUpRead() throws IOException {
        this.servidores.removeAllElements();
        this.nroIRI = Integer.parseInt(properties.getProperty("nroIRI").trim());
        for(int i=1;i<=this.nroIRI;i++){
            servidores.addElement(properties.getProperty("sdIRI"+"_"+String.valueOf(i).trim()).trim());
        }
        this.sdIRI = properties.getProperty("sdIRI").trim();
        this.user = properties.getProperty("user").trim();
        this.pass = properties.getProperty("pass").trim();
        this.obo = properties.getProperty("obo").trim();
    }

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
   
    public Vector getServidores() {
        return servidores;
    }
    

    public String getSdIRI() {
        return sdIRI;
    }

    public void setSdIRI(String sdIRI) {
        this.sdIRI = sdIRI;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getObo() {
        return obo;
    }

    public void setObo(String obo) {
        this.obo = obo;
    }

}
