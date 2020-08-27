/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author germa
 */
public final class ConexionSwordControl {

    String sdIRI = "";
    String user = "";
    String pass = "";
    String obo = "";

    static ConexionSwordControl instancia = null;

    static Properties properties = null;
    static InputStream propertiesStream = null;
    static OutputStream output = null;

    protected ConexionSwordControl() throws IOException {
        this.conectorProperty();        
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
        this.sdIRI = properties.getProperty("sdIRI").trim();
        this.user = properties.getProperty("user").trim();        
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
