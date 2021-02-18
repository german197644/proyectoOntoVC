/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 *
 * @author germa
 */
public class DublinCoreControler {
    // unica instancia.

    private static DublinCoreControler instancia = null;

    Properties properties = null;
    InputStream propertiesStream = null;

    protected DublinCoreControler() throws IOException {
        properties = new Properties();
    }

    public static DublinCoreControler getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new DublinCoreControler();
        }
        return instancia;
    }

    public Object getEquivalenciaDC(String aValue) throws Exception {
        String equi = null;

        propertiesStream = new FileInputStream("src/main/java/propiedades/configDublinCore.properties");
        properties.load(propertiesStream);
        equi = properties.getProperty(aValue.trim().toLowerCase());
        if (equi.contains(",")) {
            StringTokenizer st = new StringTokenizer(equi, ",");            
            return st;
        }
        return equi;
    }

    public String buscarEquivalencias(String aValue) throws Exception {
        propertiesStream = new FileInputStream("src/main/java/propiedades/configDublinCore.properties");
        properties.load(propertiesStream);
        final String equi = properties.getProperty(aValue.trim().toLowerCase());
        return equi.trim();
    }
}


