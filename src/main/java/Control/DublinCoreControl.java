/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 *
 * @author germa
 */
public class DublinCoreControl {
    // unica instancia.

    private static DublinCoreControl instancia = null;

    Properties properties = null;
    Properties propertiesBE = null;
    InputStream propertiesStream = null;

    protected DublinCoreControl() throws IOException {
        //properties = new Properties();
    }

    public static DublinCoreControl getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new DublinCoreControl();
        }
        return instancia;
    }

    /**
     *
     * @param aValue Tipo de metadato.
     * @return Retorna la equivalencia Dublin Core en función del tipo de
     * metadato.
     * @throws Exception
     */
    public Object getEquivalenciaDC(String aValue) throws Exception {
        String equi = null;
        ConfigControl config = ConfigControl.getInstancia();
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configDublinCore.properties");
        //properties.load(propertiesStream);
        properties = config.getConfigDublinCore();
        equi = properties.getProperty(aValue.trim().toLowerCase());
        if (equi.contains(",")) {
            StringTokenizer st = new StringTokenizer(equi, ",");
            return st;
        }
        return equi;
    }

    /**
     *
     * @param aValue Tipo de matadato.
     * @return Retorna la equivalencia Dublin Core en función del tipo de
     * metadato.
     * @throws Exception
     */
    public String buscarEquivalencias(String aValue) throws Exception {
        ConfigControl config = ConfigControl.getInstancia();
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configDublinCore.properties");
        //properties.load(propertiesStream);
        if (propertiesBE == null) {
            propertiesBE = new Properties();
            propertiesBE = config.getConfigDublinCore();
        }
        //properties = config.getConfigDublinCore();        
        final String equi = propertiesBE.getProperty(aValue.trim().toLowerCase());
        return equi.trim();
    }

    /**
     * Pone en null la variable de propiedades de buscar Equivalencias.
     */
    public void concelarPropertieBE() {
        propertiesBE = null;
    }
}
