/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Control.RestControler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Pogliani, German.
 */
public class ComunidadRest {

    private String nombre;
    private String link;

    public ComunidadRest(String nombre, String link) {
        this.nombre = nombre;
        this.link = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return Retorna el nombre de la nombre asociada.
     */
    @Override
    public String toString() {
        return nombre;
    }

    public boolean isComunidad() {
        return true;
    }

    public boolean isColeccion() {
        return false;
    }
    
        /**
     *
     * @param url El host donde se aloja DSpace.
     * @param nodo Nodo del arbol elejido.
     * @return
     */
    public DefaultMutableTreeNode getColeccion(String url, DefaultMutableTreeNode nodo) {
        String link = null;
        try {
            link = ((ComunidadRest) nodo.getUserObject()).getLink();
            String comando = "curl -X GET -H \"accept: application/json\" "
                    + url + link + "/collections";
            Process process = Runtime.getRuntime().exec(comando);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            JsonParser parser = new JsonParser();
            String result = br.lines().collect(Collectors.joining("\n"));
            JsonElement datos = parser.parse(result);
            if (((JsonElement) datos).isJsonArray()) {
                JsonArray array = (JsonArray) datos;
                if (array.size() == 0) {
                    return nodo;
                }
                //nodo.removeAllChildren(); 
                Iterator<JsonElement> iter = array.iterator();
                while (iter.hasNext()) {
                    JsonObject jsonColeccion = (JsonObject) iter.next();
                    JsonElement linkColeccion = jsonColeccion.get("link");
                    JsonElement nameColeccion = jsonColeccion.get("name");
                    //creamos la comunidad
                    ColeccionRest coleccion = new ColeccionRest(nameColeccion.getAsString(), linkColeccion.getAsString());
                    DefaultMutableTreeNode objTree = new DefaultMutableTreeNode(coleccion);
                    nodo.add(objTree);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nodo;
    }

    public DefaultMutableTreeNode getComunidad(String url, DefaultMutableTreeNode nodo) {
        String link = null;
        try {
            link = ((ComunidadRest) nodo.getUserObject()).getLink();
            String comando = "curl -X GET -H \"accept: application/json\" "
                    + url + link + "/communities";
            Process process = Runtime.getRuntime().exec(comando);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            JsonParser parser = new JsonParser();
            String result = br.lines().collect(Collectors.joining("\n"));
            JsonElement datos = parser.parse(result);
            if (((JsonElement) datos).isJsonArray()) {
                JsonArray array = (JsonArray) datos;
                if (array.size() == 0) {
                    return nodo;
                }           
                nodo.removeAllChildren();                
                Iterator<JsonElement> iter = array.iterator();
                while (iter.hasNext()) {
                    JsonObject jsonComunidad = (JsonObject) iter.next();
                    JsonElement linkComunidad = jsonComunidad.get("link");
                    JsonElement nameComunidad = jsonComunidad.get("name");
                    //creamos la comunidad
                    ComunidadRest comunidad = new ComunidadRest(nameComunidad.getAsString(), linkComunidad.getAsString());
                    DefaultMutableTreeNode nodoComunidad = new DefaultMutableTreeNode(comunidad);
                    nodo.add(nodoComunidad);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return nodo;
    }
}
