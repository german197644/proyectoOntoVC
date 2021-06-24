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
import static com.clarkparsia.pellet.hierarchy.HierarchyFunctions.data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import jdk.nashorn.internal.runtime.ListAdapter;
import org.apache.commons.csv.CSVFormat;

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
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public boolean conectar() throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, Void> mySwingWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                String out = null;
                JsonElement estado = null;
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
                    out = br.lines().collect(Collectors.joining("\n"));
                    //
                    JsonParser parse = new JsonParser();
                    JsonElement datos = parse.parse(new StringReader(out));
                    if (datos.isJsonObject()) {
                        estado = datos.getAsJsonObject().get("authenticated");
                    } else {
                        return false;
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return estado.getAsBoolean();
            }
        };

        mySwingWorker.execute();
        return mySwingWorker.get();
    }

    /**
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void conectar2() throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, Void> mySwingWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                String out = null;
                JsonElement estado = null;
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
                    out = br.lines().collect(Collectors.joining("\n"));
                    // 
                    JsonParser parse = new JsonParser();
                    JsonElement datos = parse.parse(new StringReader(out));
                    if (datos.isJsonObject()) {
                        estado = datos.getAsJsonObject().get("authenticated");
                    } else {
                        return false;
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return estado.getAsBoolean();
            }
        };
        mySwingWorker.execute();
    }

    /**
     *
     * @return Verdadero si logra desconectar. Falso en caso contrario.
     */
    public boolean desconectar() {
        String out = null;
        boolean resultado = false;
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

            out = br.lines().collect(Collectors.joining("\n"));

            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(new StringReader(out));
            if (datos.isJsonObject()) {
                //JsonElement estado = ((JsonObject) parse.parse(out)).get("authenticated");
                JsonElement estado = datos.getAsJsonObject().get("authenticated");
                resultado = estado.getAsBoolean();
            } else {
                resultado = false;
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    /**
     *
     * @return Retorna el estado de la conexion.
     */
    public boolean estatus() {
        String out = null;
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
            out = br.lines().collect(Collectors.joining("\n"));
            //
            JsonParser parse = new JsonParser();
            JsonReader reader = new JsonReader(new StringReader(out));
            reader.setLenient(true);
            JsonElement elem = parse.parse(reader);
            //System.out.println("Elemento: " + elem + "   / "  + elem.isJsonObject());
            if (elem.isJsonObject()) { // si no es nulo.
                estado = ((JsonObject) parse.parse(out)).get("authenticated").getAsBoolean();
            }
            // 
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }

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
                                    JsonElement uuidColeccion = jsonColeccion.get("uuid");
                                    ColeccionRest coleccion
                                            = new ColeccionRest(nameColeccion.getAsString(), linkColeccion.getAsString(), uuidColeccion.getAsString());
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
     * @param coleccion Colección del repositorio.
     * @return Una Lista con los Items de la colección del Repositorio.
     */
    public DefaultListModel obtenerItems(ColeccionRest coleccion) {
        DefaultListModel<ItemRest> listItems = new DefaultListModel();
        try {
            ConfigControl conn = ConfigControl.getInstancia();
            //
            String comando = "curl \"" + conn.getUri().trim() + coleccion.getLink() + "/items\"";
            Process process = Runtime.getRuntime().exec(comando);
            //
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //
            String out = br.lines().collect(Collectors.joining("\n"));
            // System.out.println("Linea: " + out);
            JsonParser parser = new JsonParser();
            JsonReader reader = new JsonReader(new StringReader(out));
            JsonElement datos = parser.parse(reader);
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
            } else {
                JOptionPane.showMessageDialog(null, "No hay Items en esta colección.");
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listItems;
    }

    /**
     *
     * @param item Representa el item de la coleccion.
     * @param miListaRecursos
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public void obtenerBitstreams(ItemRest item, JList miListaRecursos)
            throws InterruptedException, ExecutionException {
        DefaultListModel<BitstreamsRest> listBitstreams = new DefaultListModel();
        SwingWorker<DefaultListModel, Void> mySwingWorker = new SwingWorker<DefaultListModel, Void>() {
            @Override
            protected DefaultListModel doInBackground() throws Exception {
                try {
                    ConfigControl conn = ConfigControl.getInstancia();
                    String comando = "curl \"" + conn.getUri().trim() + item.getLink() + "/bitstreams\"";
                    Process process = Runtime.getRuntime().exec(comando);
                    //
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    String out = br.lines().collect(Collectors.joining("\n"));
                    //System.out.println("Linea: " + out);
                    JsonParser parser = new JsonParser();
                    JsonReader reader = new JsonReader(new StringReader(out));
                    JsonElement datos = parser.parse(reader);
                    //
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

            @Override
            protected void done() {
                try {
                    if (this.get().size() > 0) {
                        miListaRecursos.setModel(this.get());
                    } else {
                        DefaultListModel<String> dlm = new DefaultListModel();
                        dlm.addElement("sin recursos");
                        miListaRecursos.setModel(dlm);
                    }
                    //miListaRecursos.setModel(this.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        mySwingWorker.execute();
    }

    public void obtenerMetadata(ItemRest item, JTextArea ta) {
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    ConfigControl conn = ConfigControl.getInstancia();

                    String comando = "curl \"" + conn.getUri().trim() + item.getLink().trim() + "/metadata\"";
                    Process process = Runtime.getRuntime().exec(comando);
                    int p = process.waitFor();
                    if (p != 0) {
                        return null;
                    }
                    //
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    String out = new String(br.lines().collect(Collectors.joining("\n")).getBytes(), "UTF-8");
                    //System.out.println("OUT: " + new String(out.getBytes(), "UTF-8"));
                    //System.out.println("OUT: " + out);
                    JsonParser parser = new JsonParser();
                    JsonReader reader = new JsonReader(new StringReader(out));
                    JsonElement datos = parser.parse(reader);
                    //
                    if (datos.isJsonArray()) {
                        JsonArray array = (JsonArray) datos;
                        Iterator<JsonElement> iter = array.iterator();
                        //
                        ta.setText("");
                        //
                        Properties property = new Properties();
                        property = conn.getConfigDublinCore();
                        int ancho = ta.getWidth();
                        while (iter.hasNext()) {
                            JsonObject jsonItem = (JsonObject) iter.next();
                            JsonElement unaKey = jsonItem.get("key");
                            JsonElement unValue = jsonItem.get("value");
                            //
                            StringTokenizer tokens = new StringTokenizer(unaKey.getAsString(), ".");
                            int nDatos = tokens.countTokens();
                            String[] miMeta = new String[nDatos];
                            int n = 0;
                            while (tokens.hasMoreTokens()) {
                                String str = tokens.nextToken();
                                miMeta[n] = str;
                                //System.out.println(miMeta[i]);
                                n++;
                            }
                            //
                            //ta.append(unaKey.getAsString() + "\n");
                            //String datoMeta = miMeta[nDatos - 1];
                            //System.out.println("metadato.rotulo: " + datoMeta);
                            String rotulo = null;
                            if (property != null) {
                                rotulo = property.getProperty(unaKey.getAsString());
                            }
                            //ta.append(miMeta[nDatos - 1] + "\n");

                            if (rotulo == null) {
                                ta.append(miMeta[nDatos - 1] + "\n");
                            } else {
                                //ta.append(miMeta[nDatos - 1] + "\n");
                                String rotuloMetadato = new String(rotulo.getBytes(), "UTF-8");
                                ta.append(rotuloMetadato + "\n");
                            }
                            // Valor del metadato.
                            ta.append(unValue.getAsString() + "\n");
                            for (int i = 0; i <= (ancho / 9); i++) {
                                ta.append("-");
                            }
                            ta.append("\n");
                        }
                    } else {
                        ta.append("No se obtuvieron metadatos representativos.\n");
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        mySwingWorker.execute();
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
        //
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
                    //System.out.println("comando 1: " + comando);
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
                    String out = br.lines().collect(Collectors.joining("\n"));
                    JsonParser parser = new JsonParser();
                    JsonReader reader = new JsonReader(new StringReader(out));
                    JsonElement datos = parser.parse(reader);
                    //
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
                        //System.out.println("comando 2: " + comando);
                        process = Runtime.getRuntime().exec(comando);
                        is = process.getInputStream();
                        isr = new InputStreamReader(is);
                        br = new BufferedReader(isr);

                        parser = new JsonParser();
                        out = br.lines().collect(Collectors.joining("\n"));
                        reader = new JsonReader(new StringReader(out));
                        datos = parser.parse(reader);
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

    /**
     *
     * @param miColeccion
     * @param evt
     * @param tabla
     * @param filtros
     * @param limite
     * @param offset
     *
     */
    public void miFiltro(ColeccionRest miColeccion,
            ActionEvent evt,
            JTable tabla,
            JTable filtros,
            int limite,
            int offset) {

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
                    //queryFields = "&" + comandQueryField + "*";
                    queryFields = comandQueryField + "*";
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
                String miCole = (miColeccion == null) ? "" : miColeccion.getUuid().trim();
                //System.out.println("URI" + login.getUri().trim());
                String comando = "curl -g -H \"Accept: application/json\"  "
                        + "\"" + login.getUri().trim() + "/rest/filtered-items?"
                        + queryFields + queryOpes + queryVals
                        + "&limit=" + String.valueOf(limite).trim() + "&offset=" + String.valueOf(offset).trim()
                        + "&expand=parentCollection&collSel%5B%5D=" + miCole.trim() + "\"";
                //                
                //System.out.println("Comando: " + comando);                
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
                    //tabla.getModel().setValueAt(miFiltro.get("uuid").getAsString(), i, 1);
                    tabla.getModel().setValueAt(miFiltro.get("link").getAsString(), i, 1);
                    tabla.getModel().setValueAt(miFiltro.get("handle").getAsString(), i, 3);
                    tabla.getModel().setValueAt(miFiltro.get("name").getAsString(), i, 4);
                    //
                    JsonObject objCole = (JsonObject) miFiltro.get("parentCollection");
                    tabla.getModel().setValueAt(objCole.get("name").getAsString(), i, 2);
                    //
                    //String miHandle = miFiltro.get("handle").getAsString();
                    //tabla.getModel().setValueAt(miHandle, i, 3);
                    i = i + 1;
                    publish("item: " + i + " de " + cantItem);
                }
                if (i > 0) {
                    //establecemos el alto de las filas.
                    tabla.setRowHeight(40);
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

    /**
     *
     * @param miColeccion
     * @param evt
     * @param tabla
     * @param filtros
     * @param limite
     * @param offset
     */
    public void unFiltro2(ColeccionRest miColeccion,
            ActionEvent evt,
            JTable tabla,
            JTable filtros,
            int limite,
            int offset) {

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
                publish("Iniciando filtro.\n", String.valueOf(0));
                // Creamos el comando del filtro.
                String miCole = (miColeccion == null) ? "" : miColeccion.getUuid().trim();

                //System.out.println("URI" + login.getUri().trim());
                String comando = "curl -g -H \"Accept:application/json\"  "
                        + "\"" + login.getUri().trim() + "/rest/filtered-items?"
                        + queryFields + queryOpes + queryVals
                        + "&limit%5B%5D=" + String.valueOf(limite).trim() + "&offset%5B%5D=" + String.valueOf(offset).trim()
                        + "&expand%5B%5D=parentCollection&collSel%5B%5D=" + miCole + "\"";
                //                
                //URI uri = new URI(comando);
                System.out.println("Comando filtro: " + comando);
                process = Runtime.getRuntime().exec(comando);
                int p = process.waitFor();
                if (p != 0) {
                    wait.close();
                    return null;
                }
                //process = Runtime.getRuntime().exec(uri.getPath());
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
                //
                while (iter.hasNext()) {
                    JsonObject miFiltro = (JsonObject) iter.next();
                    tabla.getModel().setValueAt(i + 1, i, 0);
                    //tabla.getModel().setValueAt(miFiltro.get("uuid").getAsString(), i, 1);
                    tabla.getModel().setValueAt(miFiltro.get("link").getAsString(), i, 1);
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
        wait.makeWait("Ejecutando filtro...", evt);
    }

    public void unFiltro3(ColeccionRest miColeccion,
            ActionEvent evt,
            JTable tabla,
            JTable filtros,
            int limite,
            int offset) {

        DialogWaitControl wait = new DialogWaitControl();

        SwingWorker<Void, String> mySwingWorker = new SwingWorker<Void, String>() {

            @Override
            protected Void doInBackground() throws Exception {

                ConfigControl login = ConfigControl.getInstancia();
                int cantItem = 0;
                Process process = null;
                String comandQueryField = "query_field[]=";
                String comandValField = "query_val[]=";
                //String comandOpeField = "query_op[]=";
                String comandShowField = "show_fields[]=";
                String queryFields = "";
                String queryVals = "";
                String queryOpes = "";
                String queryShows = ""; // que metadatos mostrar ademas del titulo.
                // Seteo los filtros   
                if (filtros.getRowCount() == 0) {
                    queryFields = "&" + comandQueryField + "*";
                    //queryFields = comandQueryField + "*";
                    queryVals = "&" + comandValField + "*";
                    queryOpes = "&query_op[]=exists";
                } else {
                    for (int i = 0; i < filtros.getRowCount(); i++) {
                        queryFields = queryFields + ((i == 0) ? comandQueryField + filtros.getValueAt(i, 0)
                                : "&" + comandQueryField + filtros.getValueAt(i, 0));
                        queryVals = queryVals + "&" + comandValField + filtros.getValueAt(i, 1);
                        queryOpes = queryOpes + "&query_op[]=contains";
                    }
                }
                // Seteo los datos (metadatos) adicionales
                publish("Iniciando filtro.\n", String.valueOf(0));
                // Creamos el comando del filtro.
                String miCole = (miColeccion == null) ? "" : miColeccion.getUuid().trim();
                //
                String miCodFiltro = queryFields + queryOpes + queryVals
                        + "&limit=" + String.valueOf(limite).trim()
                        + "&offset=" + String.valueOf(offset).trim()
                        + "&expand=parentCollection&collSel[]=" + miCole;
                //
                String micadena = login.getUri().trim() + "/rest/filtered-items?" + miCodFiltro;
                URL url = new URL(micadena);
                //
                URI uri = new URI(url.getProtocol(),
                        null,
                        url.getHost(),
                        url.getPort(),
                        url.getPath(),
                        URLEncoder.encode(url.getQuery(), "UTF-8"),
                        null);

                System.out.println("URI: " + uri);
                //
                String comando = "curl -g -H \"Accept:application/json\"  "
                        + "\"" + uri + "\"";
                //                
                //URI uri = new URI(comando);
                System.out.println("Comando filtro: " + comando);
                process = Runtime.getRuntime().exec(comando);
                int p = process.waitFor();
                if (p != 0) {
                    wait.close();
                    return null;
                }
                //process = Runtime.getRuntime().exec(uri.getPath());
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
                //
                while (iter.hasNext()) {
                    JsonObject miFiltro = (JsonObject) iter.next();
                    tabla.getModel().setValueAt(i + 1, i, 0);
                    //tabla.getModel().setValueAt(miFiltro.get("uuid").getAsString(), i, 1);
                    tabla.getModel().setValueAt(miFiltro.get("link").getAsString(), i, 1);
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
        wait.makeWait("Ejecutando filtro...", evt);
    }

    public void miHandle(int fila, int columna, String unHandle) {
        try {
            ConfigControl login = ConfigControl.getInstancia();
            //if ((fila > -1) && (columna > -1) && (columna == 3)) {
            if ((fila > -1) && (columna > -1)) {
                Runtime.getRuntime().exec("cmd.exe /c start chrome "
                        + login.getHandle() + unHandle);
            }
        } catch (IOException ex) {
            Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean EliminarItem(ItemRest item, JTextArea ta) throws InterruptedException, ExecutionException {
        SwingWorker<Boolean, String> mySwingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                publish("Procediendo a eliminar ìtem.\n", String.valueOf(0));
                // creamos el comando del filtro.                                
                // System.out.println("URI" + login.getUri().trim());
                ConfigControl config = ConfigControl.getInstancia();
                String folder = config.getFolderWork().trim();
                String comando = "curl -i -X DELETE "
                        + "\"" + config.getUri().trim() + item.getLink() + "\" --cookie " + folder + "cookies.txt";
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
            throws ExecutionException, InterruptedException {
        ta.append("Verificando credenciales. Aguarde.\n");
        // Esta logueado?.
        if (!this.estatus()) {
            ta.append("No se encuentra correctamente acreditado.\n");
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
                        //
                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        //
                        JsonParser parser = new JsonParser();
                        String out = br.lines().collect(Collectors.joining("\n"));
                        JsonReader reader = new JsonReader(new StringReader(out));
                        JsonElement datos = parser.parse(reader);
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
                    String folder = login.getFolderWork().trim();
                    //                    
                    Process process = null;
                    //
                    publish("Iniciando Envio de metadatos.\n", String.valueOf(0));
                    // Creo el item en la coleccion ...
                    // metadatos_sb = metas sin bitstreams.
                    String comando = "curl -d \"@" + folder + "metadatos_sb.json\" "
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
                    String out = br.lines().collect(Collectors.joining("\n"));
                    JsonReader reader = new JsonReader(new StringReader(out));
                    //publish(result);
                    //
                    JsonElement datos = parser.parse(reader);
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
            ta.append("No se encuentra correctamente acreditado.\n");
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
        //base.jsonMetadatosSinBitstreams();
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
                    String foler = login.getFolderWork().trim();
                    String comando = "curl -i -X DELETE "
                            + "--cookie \"" + foler + ":/cookies.txt\" "
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
                    String out = br.lines().collect(Collectors.joining("\n"));
                    //                                        
                    //JsonReader reader = new JsonReader(new StringReader(out));                     
                    /*
                    //JsonParser parser = new JsonParser();
                    JsonElement datos = parser.parse(reader);
                    if (datos.isJsonObject()) {
                        JsonObject obj = (JsonObject) datos;
                        // obj?? 
                        if (obj.getAsString().contains("200")) {
                            publish("Se eliminaron los metadatos.\n");
                        };
                    } else {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource()),
                                "Error en el envio de los metadatos al item: " + item.getName().trim() + ".", "Informe", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }*/
                    if (out.contains("200")) {
                        publish("La elimanación del recurso fué exitosa. \n");
                    } else {
                        publish("Error en la elimanación del recurso fué exitosa. \n");
                    }
                    publish(out);
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
            ta.append("No se encuentra correctamente acreditado.\n");
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
                    //JsonParser parser = new JsonParser();
                    String out = br.lines().collect(Collectors.joining("\n"));
                    //JsonReader reader = new JsonReader(new StringReader(out));
                    //System.out.println("resultado: " + out);
                    if (out.contains("200")) {
                        publish("La elimanación del recurso fué exitosa. \n");
                    } else {
                        publish("Error en la elimanación del recurso fué exitosa. \n");
                    }
                    publish(out);
                    //
                    /*
                    JsonElement datos = parser.parse(reader);
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
                     */
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
        //ta.updateUI();
        // logueado?. 
        if (!this.estatus()) {
            ta.append("No se encuentra correctamente acreditado.\n");
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
                    String folder = login.getFolderWork().trim();
                    // Creo el item en la coleccion ...
                    String comando = "curl  -i -X PUT -d \"@" + folder + "metadatos_sb.json\" "
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
                    String out = br.lines().collect(Collectors.joining("\n"));
                    JsonReader reader = new JsonReader(new StringReader(out));
                    //publish(out);
                    //
                    JsonElement datos = parser.parse(reader);
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
        //ta.updateUI();
        // logueado?. 
        if (!this.estatus()) {
            return false;
        }
        //
        StardogControl base = null;
        try {
            base = StardogControl.getInstancia();
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
        //
        //base.jsonMetadatosSinBitstreams();
        //ta.append("Archivo de metadatos para modificar generados.\n");
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
            System.out.println("Control.RestControl.protocoloEnvios().Opcion: " + opcion);
            switch (opcion) {
                case 1:
                    //StardogControl base = StardogControl.getInstancia();
                    // creamos el archivo json con los metadatos
                    //base.jsonMetadatos();
                    result = enviarItem(coleccion, evt, ta);
                    if (result) {
                        ta.append("Se creó con éxito el item.\n");
                    } else {
                        ta.append("La creación del item no tuvo éxito.\n");
                    }
                    break;
                case 2:
                    result = enviarMetadatos(item, evt, ta);
                    if (result) {
                        ta.append("Se envió con éxito los metadatos.\n");
                    } else {
                        ta.append("El envió de los metadatos no tuvo éxito.\n");
                    }
                    break;
                case 3:
                    result = enviarBitstreams(item, evt, ta);
                    if (result) {
                        ta.append("Se envió con éxito el recurso.\n");
                    } else {
                        ta.append("El envió no tuvo éxito.\n");
                    }
                    break;
                case 4:
                    result = EliminarItem(item, ta);
                    if (!result) {
                        ta.append("Se eliminó con éxito el item.\n");
                    } else {
                        ta.append("La eliminacion del item no tuvo éxito.\n");
                    }
                    break;
                case 5:
                    result = EliminarMetadatos(item, evt, ta);
                    if (result) {
                        ta.append("Se eliminó con éxito los metadatos.\n");
                    } else {
                        ta.append("La eliminación de los metadatos no tuvo éxito.\n");
                    }
                    break;
                case 6:
                    result = EliminarBitstreams(bitstreams, evt, ta);
                    if (result) {
                        ta.append("Se eliminó con éxito el recurso.\n");
                    } else {
                        ta.append("La eliminación del recurso no tuvo éxito.\n");
                    }
                    break;
                case 7:
                    result = modificarMetadatos(item, evt, ta);
                    if (result) {
                        ta.append("Se modificó con éxito los metadatos.\n");
                    } else {
                        ta.append("La modificación de los metadatos no tuvo éxito.\n");
                    }
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

    public void obtenerPrefix(JComboBox miCombo) {
        DefaultComboBoxModel<String> listaPrefix = new DefaultComboBoxModel();

        SwingWorker<DefaultComboBoxModel, Void> miSwingWorker = new SwingWorker<DefaultComboBoxModel, Void>() {
            @Override
            protected DefaultComboBoxModel doInBackground() throws Exception {
                try {
                    ConfigControl conn = ConfigControl.getInstancia();
                    //System.out.println("URI ::: " + conn.getUri().trim());
                    //
                    String comando = "curl \"" + conn.getUri().trim() + "/rest/registries/schema\"";
                    Process process = Runtime.getRuntime().exec(comando);
                    //
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    String out = br.lines().collect(Collectors.joining("\n"));
                    // System.out.println("Linea: " + out);
                    JsonParser parser = new JsonParser();
                    JsonReader reader = new JsonReader(new StringReader(out));
                    JsonElement datos = parser.parse(reader);
                    if (datos.isJsonArray()) {
                        JsonArray array = (JsonArray) datos;
                        Iterator<JsonElement> iter = array.iterator();
                        while (iter.hasNext()) {
                            JsonObject jsonItem = (JsonObject) iter.next();
                            JsonElement miPrefix = jsonItem.get("prefix");
                            // Creamos la lista con los prefix de los esquemas presentes.
                            listaPrefix.addElement(miPrefix.getAsString());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay esquemas presentes en el repositorio.");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return listaPrefix;
            }

            @Override
            protected void done() {
                try {
                    miCombo.setModel(this.get());
                    miCombo.updateUI();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        miSwingWorker.execute();
    }

    public void obtenerEsquema(String miPrefix, JList miEsquema) {
        SwingWorker<DefaultListModel, Void> miSwingWorker = new SwingWorker<DefaultListModel, Void>() {
            @Override
            protected DefaultListModel doInBackground() throws Exception {
                DefaultListModel miModelo = new DefaultListModel();
                try {
                    ConfigControl conn = ConfigControl.getInstancia();
                    //System.out.println("URI ::: " + conn.getUri().trim());
                    //
                    String miURL = (conn.getUri().trim() + "/rest/registries/schema/" + miPrefix).trim();
                    String comando = "curl \"" + miURL + "\"";
                    System.out.println("COMANDO ::: " + comando);
                    Process process = Runtime.getRuntime().exec(comando);
                    //
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    //
                    String out = br.lines().collect(Collectors.joining("\n"));
                    // System.out.println("Linea: " + out);
                    JsonParser parser = new JsonParser();
                    JsonReader reader = new JsonReader(new StringReader(out));
                    JsonElement datos = parser.parse(reader);
                    // Obtenemos los metadatos del esquema.
                    JsonElement objetos = datos.getAsJsonObject().get("metadataFields");
                    if (objetos.isJsonArray()) {
                        JsonArray array = (JsonArray) objetos;
                        Iterator<JsonElement> iter = array.iterator();
                        while (iter.hasNext()) {
                            JsonObject jsonItem = (JsonObject) iter.next();
                            JsonElement miPrefix = jsonItem.get("name");
                            // Creamos la lista con los prefix de los esquemas presentes.
                            miModelo.addElement(miPrefix.getAsString());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay elementos con este prefijo: " + miPrefix);
                    }
                } catch (JsonIOException | JsonSyntaxException | HeadlessException | IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return miModelo;
            }

            @Override
            protected void done() {
                try {
                    //ListModel modelo = miEsquema.getModel();                    
                    //modelo = this.get();
                    miEsquema.setModel(this.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        miSwingWorker.execute();
    }

    public void obtenerConfigEsquema(String prefix, JTable miTalba) {
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    ConfigControl config = ConfigControl.getInstancia();
                    Properties miProp = config.getConfigDublinCore();
                    Set<Object> Keys = miProp.keySet();
                    DefaultTableModel modelo = (DefaultTableModel) miTalba.getModel();
                    if (Keys.size() > 0) {
                        int filas = modelo.getRowCount();
                        for (int i = 1; i <= filas; i++) {
                            modelo.removeRow(0);
                        }
                        for (Object clave : Keys) {
                            String key = (String) clave;
                            String value = (String) miProp.get(key);
                            //String rotulo = new String(miProp.get(value).toString().getBytes("cp1252"));
                            String rotulo = (String) miProp.get(value);
                            if (value.contains(prefix)) {
                                String[] miEsquema = {key, value, rotulo};
                                modelo.addRow(miEsquema);
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(RestControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;
            }
        };
        mySwingWorker.execute();
    }

    public void quitarFilaTabla(JTable tabla) {
        /*
        ListSelectionModel lsm = tabla.getSelectionModel();
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        System.out.println("minimo: " + minIndex + " - Maximo: " + maxIndex);
        if (!lsm.isSelectionEmpty()) {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                    System.out.println("seleccionado: " + i);
                    modelo.removeRow(i);
                }
            }
        }
         */
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int numRows = tabla.getSelectedRows().length;
        if (numRows > 0) {
            System.out.println("numero de filas seleccionadas: " + numRows);
            for (int i = 0; i < numRows; i++) {
                System.out.println("fila seleccionada: " + tabla.getSelectedRow());
                modelo.removeRow(tabla.getSelectedRow());
            }
        }
    }

}
