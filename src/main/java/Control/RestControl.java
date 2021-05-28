/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BitstreamsRest;
import Modelo.ColeccionRest;
import Modelo.ComunidadRest;
import Modelo.Fichero;
import Modelo.ItemRest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author germa
 */
public final class RestControl {

    private String newItem = null;

    private static RestControl instancia = null;

    private DefaultTreeModel modeloRepo = null;

    private RestControl() {
    }

    /**
     *
     * @return
     */
    public static RestControl getInstancia() {
        if (instancia == null) {
            instancia = new RestControl();
        }
        return instancia;
    }

    /**
     *
     * @return Retorna el modeloRepo de la estructura del repositorio
     * solicitado.
     *
     */
    public DefaultTreeModel getModeloRepo() {
        return modeloRepo;
    }

    /**
     *
     * @return Verdadero si logra conectar. Falso en caso contrario.
     */
    public boolean conectar() {
        String result = null;
        boolean result2 = false;
        try {
            // traemos los datos de conexion.
            ConfigControl login = ConfigControl.getInstancia();
            String folder = login.getFolderWork();
            // traerlo de configControler despues
            String url = login.getUri(); //"http://localhost:8080";
            String email = login.getUseRest(); //"gerdarpog@gmail.com";
            String pass = login.getPassRest(); //"german";
            String command = "curl -v -X POST --data \"email= " + email + "&password=" + pass + "\" "
                    + url + "/rest/login --cookie-jar \"" + folder + "cookies.txt\"";
            Process process = Runtime.getRuntime().exec(command);
            int waitFor = process.waitFor();
            if (waitFor != 0) {
                return false;
            }
            //
            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"" + folder + "cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();
            if (waitFor != 0) {
                return false;
            }
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            result = br.lines().collect(Collectors.joining("\n"));
            //
            JsonParser parse = new JsonParser();
            //System.out.println("Valor de result : " + parse.parse(result).isJsonNull());
            if (!parse.parse(result).isJsonNull()) {
                JsonElement estado = ((JsonObject) parse.parse(result)).get("authenticated");
                result2 = estado.getAsBoolean();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result2;
    }

    /**
     *
     * @return Verdadero si logra desconectar. Falso en caso contrario.
     */
    public boolean desconectar() {
        String result = null;
        boolean result2 = false;
        try {
            // Traemos los datos de conexion.
            ConfigControl login = ConfigControl.getInstancia();
            String folder = login.getFolderWork();
            // traerlo de loginControler despues
            String url = login.getUri();  //"http://localhost:8080";
            String command = "curl -v -X POST " + url + "/rest/logout --cookie \"" + folder + "cookies.txt\"";
            Process process = Runtime.getRuntime().exec(command);
            int waitFor = process.waitFor();
            if (waitFor != 0) {
                return false;
            }
            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"" + folder + "cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();
            if (waitFor != 0) {
                return false;
            }
            //
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            result = br.lines().collect(Collectors.joining("\n"));

            JsonParser parse = new JsonParser();
            JsonElement estado = ((JsonObject) parse.parse(result)).get("authenticated");
            result2 = estado.getAsBoolean();

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result2;
    }

    /**
     *
     * @return Retorna el estado de la conexion.
     */
    public boolean estatus() {
        String result = null;
        boolean estado = false;
        String command = null;
        Process process = null;
        try {
            // traerlo de loginControler despues
            ConfigControl login = ConfigControl.getInstancia();
            String url = login.getUri().trim();
            // traemos los datos de conexion.            
            String folder = login.getFolderWork().trim();
            //System.out.println("nro. de letras del folder: " + folder.length());
            command = "curl -v \"" + url.trim() + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie " + folder + "cookies.txt";
            //System.out.println("estatus: " + command);
            //
            process = Runtime.getRuntime().exec(command);
            int waitFor = process.waitFor();
            if (waitFor != 0) {
                return false; // no termino correctamente.
            }
            // obtenemos la salida.
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //
            result = br.lines().collect(Collectors.joining("\n"));
            //
            JsonParser parse = new JsonParser();
            //            
            JsonReader reader = new JsonReader(new StringReader(result));
            reader.setLenient(true);
            JsonElement elem = parse.parse(reader);
            //System.out.println("Elemento: " + elem + "   / "  + elem.isJsonObject());
            if (elem.isJsonObject()) { // si no es nulo.
                estado = ((JsonObject) parse.parse(result)).get("authenticated").getAsBoolean();
            }
            // 
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

    /*
    public String newItem(String coleccion) {
        String result = null;
        String command = null;
        Process process;
        int waitFor;
        // traerlo de loginControler despues
        try {
            //
            ConfigControl login = ConfigControl.getInstancia();
            String url = login.getUri(); //"http://localhost:8080";
            //
            command = "curl -v \"" + url + "/rest/status\" -H \"accept: application/json\" "
                    + "--cookie \"E:/cookies.txt\"";
            process = Runtime.getRuntime().exec(command);
            waitFor = process.waitFor();
            if (waitFor != 0) { // no termino correctamente.
                return "Error"; 
            }
            //
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
            if (!link.getAsString().isEmpty()) {
                this.newItem = link.getAsString();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result + " -- " + this.newItem;
    }
     */

 /*
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
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     */
    /**
     *
     * @param url dle host donde se aloja DSpace
     * @param comunidad comunidad a filtrar
     * @return
     */
    /**
     * public DefaultTreeModel getColeciones(String url, String comunidad) {
     * DefaultTreeModel modelo = null; String comando = null; try { comando =
     * "curl -X GET -H \"accept: application/json\" " + url + comunidad +
     * "/collections"; Process process = Runtime.getRuntime().exec(comando);
     * InputStream is = process.getInputStream(); InputStreamReader isr = new
     * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
     * JsonParser parser = new JsonParser(); String result =
     * br.lines().collect(Collectors.joining("\n"));
     * //System.out.println("Linea: " + result); JsonElement datos =
     * parser.parse(result); DefaultMutableTreeNode padre = new
     * DefaultMutableTreeNode("Repositorio"); modelo = new
     * DefaultTreeModel(padre); if (((JsonElement) datos).isJsonArray()) { int
     * level = 0; JsonArray array = (JsonArray) datos; //System.out.println("Es
     * array. Numero de elementos: " + array.size()); Iterator<JsonElement> iter
     * = array.iterator(); //System.out.println(padre.getRoot().toString());
     * while (iter.hasNext()) { JsonObject jsonComunidad = (JsonObject)
     * iter.next(); JsonElement linkComunidad = jsonComunidad.get("link");
     * JsonElement nameComunidad = jsonComunidad.get("name"); //creamos la
     * comunidad ComunidadRest comunidadRest = new
     * ComunidadRest(nameComunidad.getAsString(), linkComunidad.getAsString());
     * DefaultMutableTreeNode nodoComunidad = new
     * DefaultMutableTreeNode(comunidadRest); //
     * modelo.insertNodeInto(nodoComunidad, padre, level); //
     * System.out.println(" -- " + comunidadRest.toString()); level += level; }
     * return modelo; } } catch (IOException ex) {
     * Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null,
     * ex); } return modelo; }
     *
     */
    /**
     * public void prueba(java.awt.event.ActionEvent evt, JTextArea ta) {
     * DialogWaitControl wait = new DialogWaitControl(10);
     *
     * SwingWorker<Void, Integer> mySwingWorker = new
     * SwingWorker<Void, Integer>() {
     *
     * @Override protected Void doInBackground() throws Exception {
     *
     * //Here you put your long-running process... //RestControler rest = new
     * RestControl(); //tree.setModel(rest.getComunidades(txtHost.getText(),
     * txtComand.getText())); //tree.updateUI(); for (int i = 1; i < 11; i++) {
     * publish(i); Thread.sleep(1000); } wait.close(); return null; }
     *
     * @Override protected void process(List<Integer> chunks) {
     * wait.incrementarProBar(chunks.get(0));
     * ta.append(chunks.get(0).toString()); } };
     *
     * mySwingWorker.execute(); wait.makeWait("Consultando las comunidades...",
     * evt, 100); //--------------------------------------- }
     */
    /**
     *
     * @param url
     * @param comando
     * @return
     *
     * public DefaultTreeModel getComunidades(String url, String comando) {
     * DefaultTreeModel modelo = null; try { Process process =
     * Runtime.getRuntime().exec(comando); InputStream is =
     * process.getInputStream(); InputStreamReader isr = new
     * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
     * JsonParser parser = new JsonParser(); String result =
     * br.lines().collect(Collectors.joining("\n"));
     * //System.out.println("Linea: " + result); JsonElement datos =
     * parser.parse(result); DefaultMutableTreeNode padre = new
     * DefaultMutableTreeNode("Repositorio"); modelo = new
     * DefaultTreeModel(padre); if (((JsonElement) datos).isJsonArray()) { //int
     * level = 0; JsonArray array = (JsonArray) datos; // System.out.println("Es
     * array. Numero de elementos: " + array.size()); Iterator<JsonElement> iter
     * = array.iterator();
     *
     * System.out.println(padre.getRoot().toString()); while (iter.hasNext()) {
     * JsonObject jsonComunidad = (JsonObject) iter.next(); JsonElement
     * linkComunidad = jsonComunidad.get("link"); JsonElement nameComunidad =
     * jsonComunidad.get("name"); //creamos la comunidad ComunidadRest comunidad
     * = new ComunidadRest(nameComunidad.getAsString(),
     * linkComunidad.getAsString()); DefaultMutableTreeNode nodoComunidad = new
     * DefaultMutableTreeNode(comunidad);
     *
     * //modelo.insertNodeInto(nodoComunidad, padre, level);
     * padre.add(nodoComunidad); //System.out.println(" -- " +
     * comunidad.toString()); //level += level; } //return modeloRepo; } } catch
     * (IOException ex) {
     * Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null,
     * ex); }
     *
     * return modelo; }
     */
    /**
     *
     * @param ta muestra la salida en este compenente.
     * @return DefaultTreeModel
     *
     * public DefaultTreeModel obtenerComunidades(JTextArea ta, JFrame jfram) {
     * SwingWorker<DefaultTreeModel, Integer> mySwingWorker = new
     * SwingWorker<DefaultTreeModel, Integer>() {
     * @Override protected DefaultTreeModel doInBackground() throws Exception {
     * DefaultTreeModel modelo = null; try { ConfigControl conn =
     * ConfigControl.getInstancia(); String miURL = conn.getUri(); //String
     * comando = "curl -X GET -H \"accept: application/json\" " //+ miURL +
     * "/rest/communities"; String comando = "curl \"" + miURL +
     * "/rest/communities\""; Process process =
     * Runtime.getRuntime().exec(comando); InputStream is =
     * process.getInputStream(); InputStreamReader isr = new
     * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
     * JsonParser parser = new JsonParser(); String result =
     * br.lines().collect(Collectors.joining("\n")); System.out.println("Linea:
     * " + result); JsonElement datos = parser.parse(result);
     * DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Repositorio");
     * modelo = new DefaultTreeModel(padre); if (((JsonElement)
     * datos).isJsonArray()) { //int level = 0; JsonArray array = (JsonArray)
     * datos; DialogWaitControl wait = new DialogWaitControl(array.size());
     * wait.makeWait(miURL, jfram); // System.out.println("Es array. Numero de
     * elementos: " + array.size()); Iterator<JsonElement> iter =
     * array.iterator(); publish(array.size());
     * System.out.println(padre.getRoot().toString()); while (iter.hasNext()) {
     * JsonObject jsonComunidad = (JsonObject) iter.next(); JsonElement
     * linkComunidad = jsonComunidad.get("link"); JsonElement nameComunidad =
     * jsonComunidad.get("name"); //creamos la comunidad ComunidadRest comunidad
     * = new ComunidadRest(nameComunidad.getAsString(),
     * linkComunidad.getAsString()); DefaultMutableTreeNode nodoComunidad = new
     * DefaultMutableTreeNode(comunidad);
     *
     * //modelo.insertNodeInto(nodoComunidad, padre, level);
     * padre.add(nodoComunidad); //System.out.println(" -- " +
     * comunidad.toString()); //level += level; } //return modeloRepo; } } catch
     * (IOException ex) {
     * Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null,
     * ex); } return modelo; }
     *
     * @Override protected void process(List<Integer> chunks) { ta.append("Se
     * encontraron: " + chunks.get(0) + " comunidades" + "\n");
     * ta.append("Extrayendo estructura." + "\n"); } };
     *
     * mySwingWorker.execute(); DefaultTreeModel model = null; try { model =
     * mySwingWorker.get(); } catch (InterruptedException | ExecutionException
     * ex) { Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE,
     * null, ex); } return model; }
     *
     */
    /**
     *
     * @param comando l
     * @param url en que host se encuentra alojado DSpace
     * @return
     *
     * public DefaultTreeModel estructRepositorio(String comando, String url) {
     * DefaultTreeModel modelo = null; try { Process process =
     * Runtime.getRuntime().exec(comando);
     *
     * InputStream is = process.getInputStream(); InputStreamReader isr = new
     * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
     *
     * String result = br.lines().collect(Collectors.joining("\n"));
     * //System.out.println("Linea: " + result); JsonParser parser = new
     * JsonParser(); JsonElement datos = parser.parse(result);
     * DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Repositorio");
     * modelo = new DefaultTreeModel(padre); modelo = dumpJSONElement(url,
     * datos, "", modelo, padre); } catch (IOException ex) {
     * Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null,
     * ex); } return modelo; }
     */
    /**
     *
     * @param elemento JsonElement
     * @param miTree Jerarquia para mostrar en la salida
     * @param modelo modeloRepo del arbol
     * @param padre nodo inmediato superior
     * @param jerarquia el orden que ocupa en la estructura.
     * @return Un arbol.
     *
     * private DefaultTreeModel dumpJSONElement(String url, Object elemento,
     * String miTree, DefaultTreeModel modelo, DefaultMutableTreeNode padre) {
     * if (((JsonElement) elemento).isJsonArray()) { int level = 0; JsonArray
     * array = (JsonArray) elemento; //System.out.println("Es array. Numero de
     * elementos: " + array.size()); Iterator<JsonElement> iter =
     * array.iterator(); while (iter.hasNext()) { try { JsonObject jsonComunidad
     * = (JsonObject) iter.next(); JsonElement linkComunidad =
     * jsonComunidad.get("link"); JsonElement nameComunidad =
     * jsonComunidad.get("name"); //creamos la comunidad ComunidadRest comunidad
     * = new ComunidadRest(nameComunidad.getAsString(),
     * linkComunidad.getAsString()); DefaultMutableTreeNode nodoComunidad = new
     * DefaultMutableTreeNode(comunidad); modelo.insertNodeInto(nodoComunidad,
     * padre, level); //mostramos //System.out.println(miTree + " " +
     * comunidad.toString()); //nuevo //String command = "curl -X GET -H
     * \"accept: application/json\" " // + url + linkComunidad.getAsString() +
     * "/communities"; String command = "curl \"" + url +
     * linkComunidad.getAsString() + "/communities\""; // Process process =
     * Runtime.getRuntime().exec(command); InputStream is =
     * process.getInputStream(); InputStreamReader isr = new
     * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
     * String result = br.lines().collect(Collectors.joining("\n"));
     * //System.out.println("Linea: "+ result); JsonParser parser = new
     * JsonParser(); //JsonElement datos = parser.parse(result); JsonArray
     * datosComunidad = (JsonArray) parser.parse(result); if
     * (datosComunidad.size() > 0) { modelo = dumpJSONElement(url,
     * datosComunidad, miTree + "-- ", modelo, nodoComunidad); } else { String
     * commandColeccion = "curl -X GET -H \"accept: application/json\" " + url +
     * linkComunidad.getAsString() + "/collections"; Process processColeccion =
     * Runtime.getRuntime().exec(commandColeccion); InputStream is2 =
     * processColeccion.getInputStream(); InputStreamReader isr2 = new
     * InputStreamReader(is2); BufferedReader br2 = new BufferedReader(isr2);
     * String result2 = br2.lines().collect(Collectors.joining("\n"));
     * //System.out.println("Comunidades: "+ result2); //JsonParser parser2 =
     * new JsonParser(); JsonArray datosColeccion = (JsonArray)
     * parser.parse(result2); if (datosColeccion.size() > 0) { JsonArray
     * arrayColeccion = (JsonArray) datosColeccion; //System.out.println("Es
     * array. Numero de elementos: " + array2.size()); Iterator<JsonElement>
     * iterColeccion = arrayColeccion.iterator(); int n = 0; while
     * (iterColeccion.hasNext()) { JsonObject jsonColeccion = (JsonObject)
     * iterColeccion.next(); JsonElement nameColeccion =
     * jsonColeccion.get("name"); JsonElement linkColeccion =
     * jsonColeccion.get("link"); ColeccionRest coleccion = new
     * ColeccionRest(nameColeccion.getAsString(), linkColeccion.getAsString());
     * //mostramos la coleccion System.out.println(miTree + "-- " +
     * coleccion.toString()); //generamos un nodo hoja y la agregamos al arbol.
     * DefaultMutableTreeNode nodoColeccion = new
     * DefaultMutableTreeNode(coleccion); modelo.insertNodeInto(nodoColeccion,
     * nodoComunidad, n); n += n; } } } level += level; } catch (IOException ex)
     * { System.out.println("Error: " + ex.getMessage()); } } } return modelo; }
     */
    /**
     *
     * @param ta JTextArea donde se informa los avances.
     * @param tree Representacion devuelta de la estructura del repositorio.
     */
    public void estructuraRepositorio(JTextArea ta, JTree tree) {
        SwingWorker<DefaultTreeModel, String> mySwingWorker = new SwingWorker<DefaultTreeModel, String>() {
            @Override
            protected DefaultTreeModel doInBackground() {
                //DefaultTreeModel modeloRepo = null;
                try {
                    publish("Obteniendo estructura del repositorio.\n");
                    ConfigControl conn = ConfigControl.getInstancia();
                    //String comando = "curl -X GET -H \"accept: application/json\" "
                    //        + conn.getUri().trim() + "/rest/communities";
                    String comando = "curl \"" + conn.getUri().trim() + "/rest/communities\"";
                    //System.out.println(".doInBackground(): " + comando);
                    Process process = Runtime.getRuntime().exec(comando);                    
                    //
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    String result = br.lines().collect(Collectors.joining("\n"));
                    System.out.println("Linea: " + result);
                    JsonParser parser = new JsonParser();
                    JsonElement datos = parser.parse(result);
                    if (datos.isJsonArray()) {
                        JsonArray array = (JsonArray) datos;
                        publish("El repositorio posee una estructura de: " + array.size() + " elementos. Relevando, por favor espere...\n");
                    }
                    DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Repositorio");
                    padre = dumpJSONElement2(datos, padre);
                    modeloRepo = new DefaultTreeModel(padre);
                    publish("Estructura de repositorio obtenida satisfactoriamente.\n");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                    publish("No se pudo obtener la estructura del repositorio satisfactoriamente.\n");
                }
                return modeloRepo;
            }

            @Override
            protected void done() {
                try {
                    tree.setModel(this.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void process(List<String> chunks) {
                //ta.setEditable(false);
                ta.append(chunks.get(0));
            }

        };

        mySwingWorker.execute();
    }

    private DefaultMutableTreeNode dumpJSONElement2(JsonElement elemento, DefaultMutableTreeNode padre) {

        try {
            ConfigControl conn = ConfigControl.getInstancia();
            if (((JsonElement) elemento).isJsonArray()) {
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
                        padre.add(nodoComunidad);
                        //mostramos
                        //System.out.println(miTree + " " + comunidad.toString());
                        //nuevo
                        String command = "curl -X GET -H \"accept: application/json\" "
                                + conn.getUri() + linkComunidad.getAsString() + "/communities";
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
                            DefaultMutableTreeNode downTree = dumpJSONElement2(datosComunidad, nodoComunidad);
                            padre.add(downTree);
                        } else {
                            //String commandColeccion = "curl -X GET -H \"accept: application/json\" "
                            //        + conn.getUri() + linkComunidad.getAsString() + "/collections";
                            String commandColeccion = "curl \""
                                    + conn.getUri() + linkComunidad.getAsString() + "/collections\"";
                            //
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
                                    //ystem.out.println(miTree + "-- " + coleccion.toString());
                                    //generamos un nodo hoja y la agregamos al arbol.
                                    DefaultMutableTreeNode nodoColeccion = new DefaultMutableTreeNode(coleccion);
                                    nodoComunidad.add(nodoColeccion);
                                    n += n;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return padre;
    }

    /**
     *
     * @param ta Consola en ventana principal donde muestra adelantos.
     * @param coleccion Colección del repositorio.
     * @return Una Lista con los Items de la colección del Repositorio.
     */
    public DefaultListModel obtenerItems(JTextArea ta, ColeccionRest coleccion) {
        DefaultListModel<ItemRest> listItems = new DefaultListModel();
        try {
            ConfigControl conn = ConfigControl.getInstancia();

            //publish("Obteniendo estructura del repositorio.\n");
            //String comando = "curl -X GET -H \"accept: application/json\" "
            String comando = "curl \"" + conn.getUri().trim() + coleccion.getLink() + "/items\"";
            Process process = Runtime.getRuntime().exec(comando);

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String result = br.lines().collect(Collectors.joining("\n"));
            System.out.println("Linea: " + result);
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(result);
            if (datos.isJsonArray()) {
                JsonArray array = (JsonArray) datos;
                Iterator<JsonElement> iter = array.iterator();
                while (iter.hasNext()) {
                    JsonObject jsonItem = (JsonObject) iter.next();
                    JsonElement linkItem = jsonItem.get("link");
                    JsonElement nameItem = jsonItem.get("name");
                    //creamos la comunidad
                    ItemRest item = new ItemRest(nameItem.getAsString(), linkItem.getAsString());
                    listItems.addElement(item);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listItems;
    }

    public DefaultListModel obtenerBitstreams(JTextArea ta, ItemRest item) {
        DefaultListModel<BitstreamsRest> listBitstreams = new DefaultListModel();
        try {
            ConfigControl conn = ConfigControl.getInstancia();
            String comando = "curl \"" + conn.getUri().trim() + item.getLink() + "/bitstreams\"";
            Process process = Runtime.getRuntime().exec(comando);
            //
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //
            String result = br.lines().collect(Collectors.joining("\n"));
            System.out.println("Linea: " + result);
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(result);
            if (datos.isJsonArray()) {
                JsonArray array = (JsonArray) datos;
                Iterator<JsonElement> iter = array.iterator();
                while (iter.hasNext()) {
                    JsonObject jsonItem = (JsonObject) iter.next();
                    JsonElement linkItem = jsonItem.get("link");
                    JsonElement nameItem = jsonItem.get("name");
                    // Creamos el Bitstreams a presentar en el principal
                    BitstreamsRest bs = new BitstreamsRest(nameItem.getAsString(), linkItem.getAsString());
                    listBitstreams.addElement(bs);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listBitstreams;
    }

    /**
     *
     * @param miColeccion Coleccion a la que se envia dentro del repositorio.
     * @param evt evento que lo llama.
     * @param ta Componente de mensaje de salida.
     * @return true si tuvo exito.
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public boolean enviarItem(ColeccionRest miColeccion, ActionEvent evt, JTextArea ta)
            throws InterruptedException, ExecutionException {
        DialogWaitControl wait = new DialogWaitControl();
        FicheroControl ficheros = FicheroControl.getInstancia();
        DefaultListModel lista = ficheros.getListaFicheros();

        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    ConfigControl login = ConfigControl.getInstancia();
                    String folder = login.getFolderWork();
                    //
                    String item = null;
                    int cantFile = 0;
                    Process process = null;
                    //
                    publish("Iniciando Envio.\n");
                    // Creo el item en la coleccion ...
                    String comando = "curl -d \"@" + folder + "metadatos.json\" "
                            + "-H \"Content-Type: application/json\" -H \"accept: application/json\" "
                            + "--cookie \"" + folder + "cookies.txt\" -X POST "
                            + login.getUri().trim() + miColeccion.getLink() + "/items\"";
                    //
                    System.out.println("comando 1: " + comando);
                    process = Runtime.getRuntime().exec(comando);
                    int p = process.waitFor();
                    if (p != 0) {
                        wait.close();
                        return false; //no tuvo exito.
                    }
                    //
                    publish("Se enviaron los metadatos a la colección: " + miColeccion.getNombre() + ".\n");
                    //
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    JsonParser parser = new JsonParser();
                    String result = br.lines().collect(Collectors.joining("\n"));
                    //                    
                    JsonElement datos = parser.parse(result);
                    if (datos.isJsonObject()) {
                        JsonObject obj = (JsonObject) datos;
                        item = obj.get("link").getAsString();
                    } else {
                        wait.close();
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                                "Error al crear el ítem.", "Informe", JOptionPane.ERROR_MESSAGE);
                        publish("Error al crear el ítem.");
                        return false;
                    }
                    // --------------------------------------------------------------
                    // Agregar el bitstream ...
                    // Leemos la lista de archivos a enviar.
                    //                          
                    for (int i = 0; i < lista.size(); i++) {
                        Fichero archivo = (Fichero) lista.get(i);
                        cantFile = i + 1;
                        comando = "curl -k -4 -v  -H \"Content-Type: multipart/form-data\"  "
                                + "-H \"accept: application/json\" --cookie " + folder + "cookies.txt "
                                + "-X POST "
                                + login.getUri().trim() + item.trim() + "/bitstreams?name="
                                + archivo.getUnFile().getName()
                                + " -T " + archivo.getUnFile().getAbsolutePath();
                        //
                        System.out.println("comando 2: " + comando);
                        process = Runtime.getRuntime().exec(comando);
                        is = process.getInputStream();
                        isr = new InputStreamReader(is);
                        br = new BufferedReader(isr);
                        parser = new JsonParser();
                        result = br.lines().collect(Collectors.joining("\n"));
                        datos = parser.parse(result);
                        //
                        if (datos.isJsonObject()) { // devuelve in JsonObject
                            publish("Archivo " + cantFile + " - " + archivo.getUnFile().getName() + " - Enviado\n");
                        } else {
                            // avisar del error                            
                            publish("Operación cancelada por error en el envio de material.\n", "0");
                            wait.close();
                            return false;
                        }
                    }
                    //
                    publish("Operación de envio FINALIZADA con éxito.\n", "0");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                wait.close();
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
                wait.setMensaje(chunks.get(0));
            }

        };

        // logueado?.
        if (!this.estatus()) {
            ta.append("No está correctamente logueado. Se cancela el envío.\n");
            return false;
        }
        mySwingWorker.execute();
        wait.makeWait("Creando item.", evt, 0);
        return mySwingWorker.get();
    }

    public void unFiltro(ColeccionRest miColeccion, ActionEvent evt, JTable tabla, JTable filtros,
            int limite, int offset) {
        DialogWaitControl wait = new DialogWaitControl();

        SwingWorker<Void, String> mySwingWorker = new SwingWorker<Void, String>() {

            @Override
            protected Void doInBackground() throws Exception {

                ConfigControl login = ConfigControl.getInstancia();
                int cantItem = 0;
                Process process = null;
                String comandQueryField = "query_field%5B%5D=";
                String comandValField = "query_val%5B%5D=";
                //String comandOpeField = "query_op%5B%5D=";
                String comandShowField = "show_fields%5B%5D=";
                String queryFields = "";
                String queryVals = "";
                String queryOpes = "";
                String queryShows = ""; // que metadatos mostrar ademas del titulo.
                // Seteo los filtros   
                if (filtros.getRowCount() == 0) {
                    queryFields = "&" + comandQueryField + "*";
                    queryVals = "&" + comandValField + "*";
                    queryOpes = "&query_op%5B%5D=exists";
                } else {
                    for (int i = 0; i < filtros.getRowCount(); i++) {
                        queryFields = queryFields + ((i == 0) ? comandQueryField + filtros.getValueAt(i, 0)
                                : "&" + comandQueryField + filtros.getValueAt(i, 0));
                        queryVals = queryVals + "&" + comandValField + filtros.getValueAt(i, 1);
                        queryOpes = queryOpes + "&query_op%5B%5D=contains";
                    }
                }
                // Seteo los datos (metadatos) adicionales
                publish("Iniciando Envio.\n", String.valueOf(0));
                // Creamos el comando del filtro.
                String miCole = (miColeccion == null) ? "" : miColeccion.getNombre();

                //System.out.println("URI" + login.getUri().trim());
                String comando = "curl -g -H \"Accept: application/json\"  "
                        + "\"" + login.getUri().trim() + "/rest/filtered-items?"
                        + queryFields + queryOpes + queryVals
                        + "&limit=" + String.valueOf(limite).trim() + "&offset=" + String.valueOf(offset).trim()
                        + "&expand=parentCollection&collSel%5B%5D=" + miCole.trim() + "\"";
                //                
                process = Runtime.getRuntime().exec(comando);
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                JsonParser parser = new JsonParser();
                String result = br.lines().collect(Collectors.joining("\n"));
                //System.out.println("resuldato del filtro: " + result);
                JsonElement datos = parser.parse(result);
                if (datos.isJsonObject()) {
                    JsonObject obj = (JsonObject) datos;
                    cantItem = obj.get("item-count").getAsInt();
                    publish("Items encontrados: " + cantItem);
                    System.out.println("cantidad de items: " + cantItem);
                    if (cantItem == 0) {
                        ((DefaultTableModel) tabla.getModel()).setRowCount(0);
                        wait.close();
                        return null;
                    } else {
                        ((DefaultTableModel) tabla.getModel()).setRowCount(cantItem);
                    }
                } else {
                    wait.close();
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                            "No hubo resultados en el filtrado.", "Informe", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                JsonObject obj = (JsonObject) datos;
                JsonArray misItems = (JsonArray) obj.get("items");
                Iterator<JsonElement> iter = misItems.iterator();
                int i = 0;

                while (iter.hasNext()) {
                    JsonObject miFiltro = (JsonObject) iter.next();
                    tabla.getModel().setValueAt(i + 1, i, 0);
                    tabla.getModel().setValueAt(miFiltro.get("uuid").getAsString(), i, 1);
                    tabla.getModel().setValueAt(miFiltro.get("name").getAsString(), i, 4);
                    JsonElement objColl = (JsonObject) miFiltro.get("parentCollection");
                    if (objColl.isJsonObject()) {
                        if (objColl.isJsonObject()) {
                            tabla.getModel().setValueAt(miFiltro.get("name").getAsString(), i, 2);
                        } else {
                            tabla.getModel().setValueAt("", i, 2);
                        }
                        String miHandle = miFiltro.get("handle").getAsString();
                        tabla.getModel().setValueAt(miHandle, i, 3);
                    }
                    i = i + 1;
                    publish("item: " + i + " de " + cantItem);
                }
                //
                wait.close();
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                wait.setMensaje(chunks.get(0));
            }

        };
        mySwingWorker.execute();
        wait.makeWait("Filtrando...", evt, 0);
    }

    public void miHandle(int fila, int columna, String unHandle) {
        try {
            ConfigControl login = ConfigControl.getInstancia();
            if ((fila > -1) && (columna > -1) && (columna == 3)) {
                Runtime.getRuntime().exec("cmd.exe /c start chrome "
                        + login.getHandle() + unHandle);
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean enviarItem(ActionEvent evt, ColeccionRest coleccion, JTextArea ta) {
        boolean error = false;
        try {
            StardogControl base = StardogControl.getInstancia();
            // creamos el archivo json con los metadatos
            base.jsonMetadatos();
            error = enviarItem(coleccion, evt, ta);
        } catch (Exception ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error;
    }

    private Boolean EliminarItem(ItemRest item, JTextArea ta) throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                publish("Procediendo a eliminar ìtem.\n", String.valueOf(0));
                // creamos el comando del filtro.                                
                // System.out.println("URI" + login.getUri().trim());
                ConfigControl login = ConfigControl.getInstancia();

                String comando = "curl -i -X DELETE "
                        + "\"" + login.getUri().trim() + item.getLink() + "\" --cookie E:/cookies.txt";
                // vemos como queda el comnando.
                publish("Comando eliminar ------------------------------\n" + comando);
                //                
                Process process = Runtime.getRuntime().exec(comando);
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                //  capturamos el resultado de la operacion.  
                String result = br.lines().collect(Collectors.joining("\n"));
                System.out.println("resuldato de la e li mi na ciòn : " + result);
                //
                return result.contains("200");
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0) + "\n");
            }

        };
        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    public boolean enviarBitstreams(ItemRest item, ActionEvent evt, JTextArea ta)
            throws InterruptedException, ExecutionException {

        ta.append("Verificando credenciales. Aguarde.\n");
        // Esta logueado?.
        if (!this.estatus()) {
            return false;
        }
        //
        FicheroControl ficheros = FicheroControl.getInstancia();
        DefaultListModel lista = ficheros.getListaFicheros();
        ta.append("Verificando recursos seleccionados. Aguarde.\n");
        if (lista.size() == 0) {
            ta.append("No hay recursos. Operación finalizada SIN exito.\n");
            return false;
        }

        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    ConfigControl login = ConfigControl.getInstancia();
                    //String item = null;
                    int cantFile = 0;
                    Process process = null;
                    //
                    for (int i = 0; i < lista.size(); i++) {
                        Fichero archivo = (Fichero) lista.get(i);
                        cantFile = i + 1;
                        String comando = "curl -k -4 -v  -H \"Content-Type: multipart/form-data\"  "
                                + "-H \"accept: application/json\" --cookie E:/cookies.txt "
                                + "-X POST "
                                + login.getUri().trim() + item.getLink().trim() + "/bitstreams?name="
                                + archivo.getUnFile().getName()
                                + " -T " + archivo.getUnFile().getAbsolutePath();

                        process = Runtime.getRuntime().exec(comando);
                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        JsonParser parser = new JsonParser();
                        String result = br.lines().collect(Collectors.joining("\n"));
                        JsonElement datos = parser.parse(result);
                        //
                        if (datos.isJsonObject()) { // devuelve un JsonObject
                            publish("Archivo " + i + "" + archivo.getUnFile().getName()
                                    + " - Enviado\n", String.valueOf(cantFile));
                        } else {
                            // avisar del error
                            publish("Operación finalizada SIN exito.");
                            return false;
                        }
                    }
                    publish("Operación finalizada con exito.");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                //wait.close();
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
                ta.updateUI();
            }

        };
        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    public boolean enviarMetadatos(ItemRest item, ActionEvent evt, JTextArea ta)
            throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    ConfigControl login = ConfigControl.getInstancia();
                    String folder = login.getFolderWork();
                    //                    
                    Process process = null;
                    //
                    publish("Iniciando Envio de metadatos.\n", String.valueOf(0));
                    // Creo el item en la coleccion ...
                    // metadatos_sb = metas sin bitstreams.
                    String comando = "curl -d \"@" + folder + ":/metadatos_sb.json\" "
                            + "-H \"Content-Type: application/json\" -H \"accept: application/json\" "
                            + "--cookie \"" + folder + "cookies.txt\" -X POST "
                            + login.getUri().trim() + item.getLink().trim() + "/metadata";
                    //
                    process = Runtime.getRuntime().exec(comando);
                    int p = process.waitFor();
                    if (p != 0) {
                        return false; //no tuvo exito.
                    }
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    JsonParser parser = new JsonParser();
                    String result = br.lines().collect(Collectors.joining("\n"));
                    publish(result);
                    //
                    JsonElement datos = parser.parse(result);
                    if (datos.isJsonObject()) {
                        JsonObject obj = (JsonObject) datos;
                        // obj?? Para despues. 
                    } else {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                                "Error en el envio de los metadatos al item: " + item.getName().trim() + ".", "Informe", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    //
                    publish("Operación Finalizada con exito.");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }

        };
        // ----------------------------------------------------
        ta.append("Verificando credenciales. Aguarde.\n");
        ta.updateUI();
        // logueado?. 
        if (!this.estatus()) {
            return false;
        }
        //
        StardogControl base = null;
        try {
            base = StardogControl.getInstancia();
        } catch (Exception ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // nos fijamos si los metadatos tienen errores.
            if (base.validarMetadatos_v2()) {
                Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
                JOptionPane.showMessageDialog(win, base, newItem, 0);
                ta.append("Metadatos con errores. Revíselos!. Operación cancelada.\n");
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        base.jsonMetadatosSinBitstreams();
        ta.append("Metadatos generados.\n");
        // inicio el hilo y espero el resultado.
        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    public boolean EliminarMetadatos(ItemRest item, ActionEvent evt, JTextArea ta)
            throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    ConfigControl login = ConfigControl.getInstancia();
                    //                    
                    Process process = null;
                    //
                    publish("Iniciando Envio de metadatos.\n", String.valueOf(0));
                    // Creo el item en la coleccion ...
                    String comando = "curl -i -X DELETE "
                            + "--cookie \"" + login.getFolderWork().trim() + ":/cookies.txt\" "
                            + "\"" + login.getUri().trim() + item.getLink().trim() + "/metadata" + "\"";
                    //
                    process = Runtime.getRuntime().exec(comando);
                    int p = process.waitFor();
                    if (p != 0) {
                        return false; //no tuvo exito.
                    }
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    JsonParser parser = new JsonParser();
                    String result = br.lines().collect(Collectors.joining("\n"));
                    System.out.println("resultado: " + result);
                    publish(result);
                    //
                    JsonElement datos = parser.parse(result);
                    if (datos.isJsonObject()) {
                        JsonObject obj = (JsonObject) datos;
                        // obj?? 
                        if (obj.getAsString().contains("200")) {
                        };
                    } else {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                                "Error en el envio de los metadatos al item: " + item.getName().trim() + ".", "Informe", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    //
                    publish("Operación Finalizada con exito.");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }

        };
        // ----------------------------------------------------
        ta.append("Verificando credenciales. Aguarde.\n");
        ta.updateUI();
        // logueado?. 
        if (!this.estatus()) {
            return false;
        }
        // inicio el hilo y espero el resultado.
        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    public boolean EliminarBitstreams(BitstreamsRest bitStreams, ActionEvent evt, JTextArea ta)
            throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    ConfigControl login = ConfigControl.getInstancia();
                    //                    
                    Process process = null;
                    //
                    publish("Quitando recurso del ítem.\n", String.valueOf(0));
                    // Creo el item en la coleccion ...
                    String comando = "curl -i -X DELETE "
                            + "--cookie \"" + login.getFolderWork().trim() + ":/cookies.txt\" "
                            + "\"" + login.getUri().trim() + bitStreams.getLink().trim() + "\"";
                    //
                    process = Runtime.getRuntime().exec(comando);
                    int p = process.waitFor();
                    if (p != 0) {
                        return false; //no tuvo exito.
                    }
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    JsonParser parser = new JsonParser();
                    String result = br.lines().collect(Collectors.joining("\n"));
                    System.out.println("resultado: " + result);
                    if (result.contains("200")) {
                        publish("La elimanación del recurso fué exitosa. \n");
                    } else {
                        publish("Error en la elimanación del recurso fué exitosa. \n");
                    }
                    publish(result);
                    //
                    JsonElement datos = parser.parse(result);
                    if (datos.isJsonObject()) {
                        JsonObject obj = (JsonObject) datos;
                        // obj?? 
                        if (obj.getAsString().contains("200")) {
                        };
                    } else {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                                "Error en el envio de los metadatos al item: " + bitStreams.getNombre().trim() + ".", "Informe", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    //
                    publish("Operación Finalizada con exito.");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }

        };
        // ----------------------------------------------------
        ta.append("Verificando credenciales. Aguarde.\n");
        ta.updateUI();
        // logueado?. 
        if (!this.estatus()) {
            return false;
        }
        // inicio el hilo y espero el resultado.
        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    public boolean modificarMetadatos(ItemRest item, ActionEvent evt, JTextArea ta)
            throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    ConfigControl login = ConfigControl.getInstancia();
                    //                    
                    Process process = null;
                    //
                    publish("Iniciando Envio de metadatos.\n", String.valueOf(0));
                    // Creo el item en la coleccion ...
                    String comando = "curl  -i -X PUT -d \"@" + login.getFolderWork().trim() + ":/metadatos_sb.json\" "
                            + "-H \"Content-Type: application/json\" -H \"accept: application/json\" "
                            + "--cookie \"" + login.getFolderWork().trim() + ":/cookies.txt\"  "
                            + "\"" + login.getUri().trim() + item.getLink().trim() + "/metadata \"";
                    //
                    process = Runtime.getRuntime().exec(comando);
                    int p = process.waitFor();
                    if (p != 0) {
                        return false; //no tuvo exito.
                    }
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    JsonParser parser = new JsonParser();
                    String result = br.lines().collect(Collectors.joining("\n"));
                    publish(result);
                    //
                    JsonElement datos = parser.parse(result);
                    if (datos.isJsonObject()) {
                        JsonObject obj = (JsonObject) datos;
                        // obj?? 
                    } else {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                                "Error en el envio de los metadatos al item: " + item.getName().trim() + ".", "Informe", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    //
                    publish("Operación Finalizada con exito.");
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }

        };
        // ----------------------------------------------------
        ta.append("Verificando credenciales. Aguarde.\n");
        ta.updateUI();
        // logueado?. 
        if (!this.estatus()) {
            return false;
        }
        //
        StardogControl base = null;
        try {
            base = StardogControl.getInstancia();
        } catch (Exception ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // nos fijamos si los metadatos tienen errores.
            if (base.validarMetadatos_v2()) {
                Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
                JOptionPane.showMessageDialog(win, base, newItem, 0);
                ta.append("Metadatos con errores. Revíselos!. Operación cancelada.\n");
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        base.jsonMetadatosSinBitstreams();
        ta.append("Archivo de metadatos para modificar generados.\n");
        // inicio el hilo y espero el resultado.
        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    public boolean protocoloRest(int opcion, ActionEvent evt,
            ColeccionRest coleccion, ItemRest item, BitstreamsRest bitstreams,
            JTextArea ta) {
        boolean result = false;
        try {
            if (opcion < 0) {
                return result;
            }
            //opcion += 1;
            System.out.println("Control.RestControl.protocoloEnvios().Opcion: " + opcion);
            switch (opcion) {
                case 1:
                    result = enviarItem(evt, coleccion, ta);
                    break;
                case 2:
                    result = enviarMetadatos(item, evt, ta);
                    break;
                case 3:
                    result = enviarBitstreams(item, evt, ta);
                    break;
                case 4:
                    result = EliminarItem(item, ta);
                    break;
                case 5:
                    result = EliminarMetadatos(item, evt, ta);
                    break;
                case 6:
                    result = EliminarBitstreams(bitstreams, evt, ta);
                    break;
                case 7:
                    result = modificarMetadatos(item, evt, ta);
                    break;
                default:
                    // nada
                    break;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
