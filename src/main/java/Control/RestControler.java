/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ColeccionRest;
import Modelo.ComunidadRest;
import Vista.prueba;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;

/**
 *
 * @author germa
 */
public class RestControler {
    
    private String newItem = null;
    
    public RestControler() {        
    }


    public String Login() throws Exception {
        String result = null;
        try {
            // traerlo de loginControler despues
            String url = "http://localhost:8080";
            String email = "gerdarpog@gmail.com";
            String pass = "german";
            String command = "curl -v -X POST --data \"email= " + email + "&password=" + pass + "\" "
                    + url + "/rest/login --cookie-jar \"E:/cookies.txt\"";
            Process process = Runtime.getRuntime().exec(command);
            int waitFor = process.waitFor();

            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"E:/cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            result = br.lines().collect(Collectors.joining("\n"));

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String Logout() throws Exception {
        String result = null;
        try {
            // traerlo de loginControler despues
            String url = "http://localhost:8080";
            String command = "curl -v -X POST " + url + "/rest/login --cookie \"E:/cookies.txt\"";
            Process process = Runtime.getRuntime().exec(command);
            int waitFor = process.waitFor();

            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"E:/cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            result = br.lines().collect(Collectors.joining("\n"));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String newItem(String coleccion) {
        String result = null;
        String command = null;
        Process process;
        int waitFor;
        // traerlo de loginControler despues
        String url = "http://localhost:8080";
        try {
            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"E:/cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            result = br.lines().collect(Collectors.joining("\n"));
            JsonParser parser = new JsonParser();            
            JsonElement valor = ((JsonObject) parser.parse(result)).get("authenticated");
            if (valor.getAsBoolean() != true) {
                return "No estas logueado";
            }
            System.out.println("1");
            // commando
            command = "curl -v -X POST " + url + coleccion + "/items "
                    + "-d \"@E:/metadatos.txt\" -H \"Content-Type: application/json\" "
                    + "-H \"accept: application/json\" --cookie \"E:/cookies.txt\"";

            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();
            //leemos la salida.          
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            //retornamos la salida.
            result = br.lines().collect(Collectors.joining("\n"));
            JsonElement link = ((JsonObject) parser.parse(result)).get("link");
            if (!link.getAsString().isEmpty()){
                this.newItem = link.getAsString();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result + " -- " + this.newItem;
    }
    
    public String sendBitstreams(String item, String dirFile) {
        String result = null;
        String command = null;
        Process process;
        int waitFor;
        // traerlo de loginControler despues
        String url = "http://localhost:8080";
        try {
            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"E:/cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            result = br.lines().collect(Collectors.joining("\n"));
            JsonParser parser = new JsonParser();            
            JsonElement valor = ((JsonObject) parser.parse(result)).get("authenticated");
            if (valor.getAsBoolean() != true) {
                return "No estas logueado";
            }
            System.out.println("1");
            // commando
            command = "curl -v -X POST " + url + item + "/bitstreams?name=swrl.pdf&description=MyTest "
                    + "-T \"C:/swrl.pdf\" "
                    + "-H \"Content-Type: application/json\" "
                    + "-H \"accept: application/json\" "
                    + "--cookie \"E:/cookies.txt\"";

            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();
            //leemos la salida.          
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            //retornamos la salida.
            result = br.lines().collect(Collectors.joining("\n"));
            //JsonElement link = ((JsonObject) parser.parse(result)).get("link");
            //if (!link.getAsString().isEmpty()){
            //    this.newItem = link.getAsString();
            //}
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result + " -- " + this.newItem;
    }    

    public void comandoEXEC(String command) throws JSONException {
        try {
            //String command = "curl -X POST 'https://xxxxxxxxxxxxx' --digest -u user:pass -H 'Content-Type: application/json' -H 'Accept: application/json' --data-binary $'{\"from\" : \"xxxx\", \"msg\" : \"xxxxxx\", \"frag\": null}'";
            Process p = Runtime.getRuntime().exec(command);
            int waitFor = p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            System.out.println(reader.toString());
            //JSONObject myJson = new JSONObject(reader.toString());
            String line = "";
            String salida = "";
            while ((line = reader.readLine()) != null) {
                //output.append(line + "\n");
                salida = salida + line;
                System.out.println(line + "\n");
            }
            //JSONObject myJson = new JSONObject(salida);
            //System.out.print("authenticated: " + myJson.get("authenticated"));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DefaultTreeModel getTreeCominudad(String url, String comando) {
        DefaultTreeModel modelo = null;
        try {
            Process process = Runtime.getRuntime().exec(comando);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            JsonParser parser = new JsonParser();
            String result = br.lines().collect(Collectors.joining("\n"));
            //System.out.println("Linea: " + result);
            JsonElement datos = parser.parse(result);
            DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Repositorio");
            modelo = new DefaultTreeModel(padre);            
            if (((JsonElement) datos).isJsonArray()) {
                int level = 0;
                JsonArray array = (JsonArray) datos;
                System.out.println("Es array. Numero de elementos: " + array.size());
                Iterator<JsonElement> iter = array.iterator();
                
                System.out.println(padre.getRoot().toString());
                while (iter.hasNext()) {
                    JsonObject jsonComunidad = (JsonObject) iter.next();
                    JsonElement linkComunidad = jsonComunidad.get("link");
                    JsonElement nameComunidad = jsonComunidad.get("name");
                    //creamos la comunidad
                    ComunidadRest comunidad = new ComunidadRest(nameComunidad.getAsString(), linkComunidad.getAsString());
                    DefaultMutableTreeNode nodoComunidad = new DefaultMutableTreeNode(comunidad);

                    modelo.insertNodeInto(nodoComunidad, padre, level);

                    System.out.println(" -- " + comunidad.toString());
                    level += level;
                }
                return modelo;
            }

        } catch (IOException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modelo;
    }

    public DefaultTreeModel estructRepositorio(String comando, String url) {
        DefaultTreeModel modelo = null;
        try {
            Process process = Runtime.getRuntime().exec(comando);

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String result = br.lines().collect(Collectors.joining("\n"));
            System.out.println("Linea: " + result);
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(result);
            DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Repositorio");
            modelo = new DefaultTreeModel(padre);
            modelo = dumpJSONElement(url, datos, "", modelo, padre);
        } catch (IOException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modelo;
    }

    /**
     *
     * @param elemento JsonElement
     * @param miTree Jerarquia para mostrar en la salida
     * @param modelo modelo del arbol
     * @param padre nodo inmediato superior
     * @param jerarquia el orden que ocupa en la estructura.
     * @return Un arbol.
     */
    private DefaultTreeModel dumpJSONElement(String url, Object elemento, String miTree, DefaultTreeModel modelo, DefaultMutableTreeNode padre) {
        if (((JsonElement) elemento).isJsonArray()) {
            int level = 0;
            JsonArray array = (JsonArray) elemento;
            //System.out.println("Es array. Numero de elementos: " + array.size());
            Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                try {
                    JsonObject jsonComunidad = (JsonObject) iter.next();
                    JsonElement linkComunidad = jsonComunidad.get("link");
                    JsonElement nameComunidad = jsonComunidad.get("name");
                    //creamos la comunidad
                    ComunidadRest comunidad = new ComunidadRest(nameComunidad.getAsString(), linkComunidad.getAsString());
                    DefaultMutableTreeNode nodoComunidad = new DefaultMutableTreeNode(comunidad);
                    modelo.insertNodeInto(nodoComunidad, padre, level);
                    //mostramos
                    System.out.println(miTree + " " + comunidad.toString());
                    //nuevo
                    String command = "curl -X GET -H \"accept: application/json\" "
                            + url + linkComunidad.getAsString() + "/communities";
                    Process process = Runtime.getRuntime().exec(command);
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String result = br.lines().collect(Collectors.joining("\n"));
                    //System.out.println("Linea: "+ result);
                    JsonParser parser = new JsonParser();
                    //JsonElement datos = parser.parse(result);
                    JsonArray datosComunidad = (JsonArray) parser.parse(result);
                    if (datosComunidad.size() > 0) {
                        modelo = dumpJSONElement(url, datosComunidad, miTree + "-- ", modelo, nodoComunidad);
                    } else {
                        String commandColeccion = "curl -X GET -H \"accept: application/json\" "
                                + url + linkComunidad.getAsString() + "/collections";
                        Process processColeccion = Runtime.getRuntime().exec(commandColeccion);
                        InputStream is2 = processColeccion.getInputStream();
                        InputStreamReader isr2 = new InputStreamReader(is2);
                        BufferedReader br2 = new BufferedReader(isr2);
                        String result2 = br2.lines().collect(Collectors.joining("\n"));
                        //System.out.println("Comunidades: "+ result2);
                        //JsonParser parser2 = new JsonParser();
                        JsonArray datosColeccion = (JsonArray) parser.parse(result2);
                        if (datosColeccion.size() > 0) {
                            JsonArray arrayColeccion = (JsonArray) datosColeccion;
                            //System.out.println("Es array. Numero de elementos: " + array2.size());
                            Iterator<JsonElement> iterColeccion = arrayColeccion.iterator();
                            int n = 0;
                            while (iterColeccion.hasNext()) {
                                JsonObject jsonColeccion = (JsonObject) iterColeccion.next();
                                JsonElement nameColeccion = jsonColeccion.get("name");
                                JsonElement linkColeccion = jsonColeccion.get("link");
                                ColeccionRest coleccion
                                        = new ColeccionRest(nameColeccion.getAsString(), linkColeccion.getAsString());
                                //mostramos la coleccion
                                System.out.println(miTree + "-- " + coleccion.toString());
                                //generamos un nodo hoja y la agregamos al arbol.
                                DefaultMutableTreeNode nodoColeccion = new DefaultMutableTreeNode(coleccion);
                                modelo.insertNodeInto(nodoColeccion, nodoComunidad, n);
                                n += n;
                            }
                        }
                    }
                    level += level;
                } catch (IOException ex) {
                    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return modelo;
    }
}
