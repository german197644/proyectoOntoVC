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
 * @author Pogliani, German
 */
public final class ConexionStardogControl {

    private String to = "";
    private String user = "";
    private String pass = "";
    private String url = "";

    static ConexionStardogControl instancia = null;

    Properties properties = null;
    InputStream propertiesStream = null;
    OutputStream output = null;

    protected ConexionStardogControl() throws IOException, Exception {
        this.conectorProperty();
    }

    public static ConexionStardogControl getInstancia() throws Exception {
        try {
            if (instancia == null) {
                instancia = new ConexionStardogControl();
            }
        } catch (IOException e) {
        }
        return instancia;
    }

    private void conectorProperty() throws IOException {
        properties = new Properties();
        propertiesStream = new FileInputStream("src/main/java/propiedades/configStardog.properties");
        properties.load(propertiesStream);
    }

    /**
     * Setea las variables de la pantalla de conexion.
     *
     * @throws Exception
     */
    public void setUpRead() throws Exception {
        this.to = properties.getProperty("to").trim();
        this.user = properties.getProperty("userOnto").trim();
        //el pass no se debe recuperar.
        //this.pass = properties.getProperty("passOnto").trim();
        this.url = properties.getProperty("urlOnto").trim();
    }

    /**
     * Este metodo setea las variables de configuración ontológicas.
     *
     * @param aTo nombre de la base de datos
     * @param aUrl la URL donde se encuentra la BD
     * @param aUser usuario
     * @param aPass password del usuario
     * @throws Exception
     *
     *
     *
     */
    public void setUpSave(String aTo, String aUrl, String aUser, String aPass) throws Exception {
        properties.setProperty("to", aTo.trim());
        properties.setProperty("urlOnto", aUrl.trim());
        properties.setProperty("userOnto", aUser.trim());
        //el pass no debe guardarse solo temporalmente.
        //properties.setProperty("passOnto", aPass.trim());
        File file = new File("src/main/java/propiedades/configStardog.properties");
        if (file.exists()) {
            output = new FileOutputStream(file.getAbsoluteFile());
            properties.store(output, "datos conexion Sword v2");
        }
    }

    private void setUpSave_viejo(String aTo, String aUrl, String aUser, String aPass) throws Exception {
        properties.setProperty("to", aTo);
        properties.setProperty("urlOnto", aUrl);
        properties.setProperty("userOnto", aUser);
        //properties.setProperty("passOnto", aPass);
        File file = new File("src/main/java/propiedades/configStardog.properties");
        if (file.exists()) {
            output = new FileOutputStream(file.getAbsoluteFile());
            properties.store(output, "datos conexion Sword v2");
        }
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
