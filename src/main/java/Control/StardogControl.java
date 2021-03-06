/*
 * 
 * 
 * 
 */
package Control;

import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.DefaultListModel;
import org.openrdf.model.IRI;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import Modelo.Metadato;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Pogliani, Germán
 *
 */
public final class StardogControl {

    //
    private static StardogControl instancia = null;

    // Variable de conexion a la base de datos
    private static ReasoningConnection conexionStardog;

    //Variable que contiene la lista de Metadatos
    private DefaultListModel<Metadato> listaMetadados = new DefaultListModel<>();

    //Variable que contiene la lista de metadatos a capturar
    private DefaultListModel<Metadato> capturaMetadados = new DefaultListModel<>();

    Properties properties = new Properties();

    InputStream propertiesStream;

    // Mensajes de validacion
    String retornoValidacion = null;

    boolean errorValidacion = false;

    private ConfigControl login = null;

    //private static final Charset ISO = Charset.forName("ISO-8859-1");
    private StardogControl() {

        try {
            login = ConfigControl.getInstancia();
        } catch (IOException ex) {
            Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static StardogControl getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new StardogControl();
        }
        return instancia;
    }

    public DefaultListModel<Metadato> getListaMetadados() {
        return listaMetadados;
    }

    public DefaultListModel getListaMetadados2() {
        return listaMetadados;
    }

    public void conectar() throws InterruptedException, ExecutionException {
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    conexionStardog = ConnectionConfiguration
                            .to(login.getBase().trim())
                            .credentials(login.getUserst().trim(), login.getPassst().trim())
                            .server(login.getUrl_st().trim())
                            .reasoning(true)
                            .connect()
                            .as(ReasoningConnection.class);
                } catch (StardogException ex) {
                    System.out.println("Error en Control.StardogControler.conectar()" + ex.getMessage());
                }
                return null;
            }
        };
        mySwingWorker.execute();
        mySwingWorker.get();
    }

    protected void desconectar() {
        conexionStardog.close();
    }

    public boolean estatus() {
        if (conexionStardog != null) {
            return conexionStardog.isOpen();
        } else {
            return false;
        }
    }

    public boolean isErrorValidacion() {
        return errorValidacion;
    }

    /**
     * metodo usado para devolver los OAs SNRD existentes en la ontología.
     *
     * @param ta Objeto donde se imprime la accion realizada
     * @param lista Lista devuelta con los Obj. de Aprendiazaje presentes en la
     * base gráfica.
     */
    public void obtenerObjetosAprendizaje(JTextArea ta, JList lista) {
        SwingWorker<DefaultListModel, String> mySwingWorker = new SwingWorker<DefaultListModel, String>() {
            @Override
            protected DefaultListModel doInBackground() throws Exception {
                DefaultListModel<Metadato> resultado = new DefaultListModel<>();
                BindingSet fila;
                TupleQueryResult aResult;
                //
                IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasSnrdType");
                IRI sv2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasDriverType");
                IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#snrd");
                IRI on2 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#driver");
                SelectQuery aQuery = conexionStardog.select(
                        "SELECT ?sujeto ?tipoSnrd"
                        + " WHERE { "
                        + " ?sujeto ?typeon ?tipoSnrd."
                        + "}"
                );
                aQuery.parameter("typeon", on);
                aResult = aQuery.execute();
                publish("Obteniendo la lista de Objetos de Aprendizaje presente en la BD gráfica.\n");
                ConfigControl config = ConfigControl.getInstancia();
                properties = config.getConfigMetadatos();
                while (aResult.hasNext()) {
                    fila = aResult.next();
                    final String aValue = extraeMata(fila.getValue("tipoSnrd").stringValue());
                    final String aDriver = extraeMata(fila.getValue("sujeto").stringValue());
                    Metadato m;
                    String aDriver2 = driverMetadato(aDriver);
                    m = new Metadato(aValue, aValue, aDriver2);
                    resultado.addElement(m);
                }
                //
                return resultado;
            }

            @Override
            protected void done() {
                try {
                    lista.setModel(this.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }
        };
        mySwingWorker.execute();
    }

    private String extraeMata(String valor) {
        if (!valor.contains("#")) {
            return valor;
        } else {
            return valor.substring(valor.indexOf("#") + 1, valor.length());
        }
    }

    public DefaultListModel<Metadato> getCapturaMetadados() {
        return capturaMetadados;
    }

    /**
     * Limpia la lista con los metadatos a capturar.
     */
    public void clearCapturaMetadatos() {
        capturaMetadados.clear();
    }

    public void setCapturaMetadados(Metadato m) {
        capturaMetadados.addElement(m);
    }

    public void clearListaMetadatos() {
        listaMetadados.clear();
    }

    public String getRetornoValidacion() {
        return retornoValidacion;
    }

    public void removerItemSeleccionados(int[] o) throws Exception {
        for (int i = 0; i < o.length; i++) {

            Metadato m = listaMetadados.get(o[i]);
            if (!repiteMetadato(m.getTipo())) {
                listaMetadados.removeElementAt(o[i]);
            }
        }
        //return listaMetadados;
    }

    //setea el orden en que se mostraran de los metadatos en el panel.    
    private int setOrdenMetadatos(Metadato m) throws Exception {
        String pos = null;
        int ret = 0;

        ConfigControl config = ConfigControl.getInstancia();
        //String folder = config.getFolderPropiedades();        
        //Properties properties = new Properties();        
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configMetadatos.properties");
        //propertiesStream = new FileInputStream(folder + "configMetadatos.properties");
        //properties.load(propertiesStream);        
        properties = config.getConfigMetadatos();
        pos = properties.getProperty(m.getTipo().trim().toLowerCase() + ".orden");
        if (pos == null) {
            pos = properties.getProperty("default");
            ret = Integer.parseInt(pos);
        } else {
            ret = Integer.parseInt(pos);
        }
        //System.out.println("orden :" + m.getTipo() + " pos -> " + pos);
        return ret;
    }

    public String validarMetadatos() throws Exception {
        DefaultListModel<Metadato> lista = this.capturaMetadados;
        retornoValidacion = "";
        errorValidacion = false;
        for (int i = 0; i < lista.size(); ++i) {
            final String aValue = lista.get(i).getValidarMetadato();
            if (aValue.length() > 0) {
                retornoValidacion += aValue + "\n";
            }
        }

        if (retornoValidacion.length() > 0) {
            errorValidacion = true;
        }

        return retornoValidacion;
    }

    public boolean validarMetadatos_v2() throws Exception {
        DefaultListModel<Metadato> lista = this.capturaMetadados;
        retornoValidacion = "";
        errorValidacion = false;
        if (lista.size() == 0) {
            errorValidacion = true;
        }
        for (int i = 0; i < lista.size(); ++i) {
            final String aValue = lista.get(i).getValidarMetadato();
            if (aValue.length() > 0) {
                retornoValidacion += aValue + "\n";
            }
        }

        if (retornoValidacion.length() > 0) {
            errorValidacion = true;
        }

        return errorValidacion;
    }

    /**
     * Visualiza el contenido de la lista de metadatos capturados.
     *
     * @return
     * @throws Exception
     */
    public String visualizarMetadatos() throws Exception {
        String retorno = "";
        DefaultListModel<Metadato> lista = this.getCapturaMetadados();

        if (lista.size() > 0) {
            retorno += "\nMetados capturados. ";
            retorno += "\n-------------------------------------------------";
            retorno += "\n";
        }
        for (int i = 0; i < lista.size(); ++i) {
            final String aux = lista.get(i).getContenidoMetadato();
            final int posi = i + 1;
            //retorno += "-" + posi + " - " + lista.get(i).getTipo() + ": " + aux + "\n";
            retorno += "_" + posi + "_) " + lista.get(i).getRotulo() + ": " + aux + "\n";
        }
        return retorno;
    }

    private boolean obligatorioMetadato(String aString) throws Exception {
        boolean obligatorio = false;
        //ConfigControl config = ConfigControl.getInstancia();
        //String folder = config.getFolderPropiedades(); 
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configMetadatos.properties");
        //propertiesStream = new FileInputStream(folder+"configMetadatos.properties");
        //
        //properties.load(propertiesStream);
        //
        //properties = config.getConfigMetadatos();
        //    
        //String cadena = aString + ".obligatorio";
        String aValue = properties.getProperty(aString + ".obligatorio");
        //System.out.println("metadato obligatorio: " + cadena + "  | " + aValue);
        if ((aValue != null) && (aValue.equals("1"))) {
            //System.out.println("metadato obligatorio: " + aString);
            obligatorio = true;
        }
        return obligatorio;
    }

    private boolean obligatorioMetadato2(String aTipo, String aDriver) throws Exception {
        boolean obligatorio = false;
        //ConfigControl config = ConfigControl.getInstancia();
        //properties = config.getConfigMetadatos();
        String cadena = (aTipo + "." + aDriver + ".obligatorio").trim();
        //System.out.println("metadato :" + cadena);
        String aValue = properties.getProperty(cadena);
        //System.out.println("metadato obligatorio: " + cadena + "  | " + aValue);
        if (aValue != null) {
            if (aValue.equals("1")) {
                obligatorio = true;
            }
        }
        return obligatorio;
    }

    private String driverMetadato(String aTipo) throws Exception {
        String resultado = null;
        //ConfigControl config = ConfigControl.getInstancia();
        //properties = config.getConfigMetadatos();
        String cadena = (aTipo).trim().toLowerCase();
        String cadenaNormalize = Normalizer.normalize(cadena, Normalizer.Form.NFD);   
        String cadena2 = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
        System.out.println("metadato Tipo:" + cadena2);
        String aValue = properties.getProperty(cadena2);
        if (aValue == null) {
            resultado = cadena2;
        } else {
            resultado = aValue;
        }
        System.out.println("metadato obligatorio: " + cadena + "  | " + aValue);
        return resultado;
    }

    private boolean repiteMetadato(String aString) throws Exception {
        boolean repite = false;
        ConfigControl config = ConfigControl.getInstancia();
        //String folder = config.getFolderPropiedades();        
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configMetadatos.properties");
        //propertiesStream = new FileInputStream(folder+"configMetadatos.properties");
        //
        //properties.load(propertiesStream);
        properties = config.getConfigMetadatos();
        String aValue = properties.getProperty(aString.trim().toLowerCase() + ".repite");
        if ((aValue != null) && (aValue.trim().equals("1"))) {
            //System.out.println("metadato que repite: " + aString);
            repite = true;
        }
        return repite;
    }

    private String rotuloMetadato(String aString) throws Exception {
        String rot = "";
        ConfigControl config = ConfigControl.getInstancia();
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configMetadatos.properties");
        //properties.load(propertiesStream);
        properties = config.getConfigMetadatos();

        String aValue = properties.getProperty(aString + ".rotulo");

        if (aValue != null) {
            //System.out.println("metadato que repite: " + aString);
            rot = aValue;
        } else {
            rot = aString;
        }
        //System.out.println("metadato: " + rot);
        return rot;
    }

    private boolean isExists(Metadato m) throws Exception {
        for (int i = 0; i < capturaMetadados.size(); i++) {
            if (capturaMetadados.get(i).getTipo().equals(m.getTipo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna el contenido del formato del OA.Ej: application/pdf. se utiliza
     * para setear el mimeType del archivo a enviar al repositorio por Sword.
     *
     * @return retorna el formato del OA o null en caso de no existir.
     * @throws Exception
     */
    public String isExistsFormat() throws Exception {
        String retorno = null;
        //System.out.println("tamaño de captura: " + capturaMetadados.size());
        for (int i = 0; i < capturaMetadados.size(); i++) {
            final String formato = capturaMetadados.get(i).getTipo().trim().toLowerCase();
            if (formato.equals("format")) {
                //System.out.println("valor: " + capturaMetadados.get(i).getContenidoMetadato().trim().toLowerCase());
                return capturaMetadados.get(i).getContenidoMetadato().trim().toLowerCase();
            }
        }
        return retorno;
    }

    // Rellena el jpnel con los componentes a mostrar.
    private JPanel rellenarJPanel2() {
        //int FILAS = capturaMetadados.size();
        //int COLUMNAS = 0;
        JPanel miPanel = new JPanel();
        //miPanel.setLayout(new GridLayout(FILAS, COLUMNAS));
        miPanel.setLayout(new BoxLayout(miPanel, BoxLayout.Y_AXIS));
        //
        this.ordenarPanelCaptura();
        //
        for (int i = 0; i < capturaMetadados.size(); ++i) {
            JPanel miFlowContainer = new JPanel();
            miFlowContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            miFlowContainer.add(capturaMetadados.get(i).getChequeado());
            miFlowContainer.add(capturaMetadados.get(i).getEtiqueta());
            miFlowContainer.add(capturaMetadados.get(i).getColector());
            miPanel.add(miFlowContainer);
        }
        return miPanel;
    }

    public void preSeteoPanelCaptura2(JScrollPane captura, Metadato oaSelect) throws Exception {
        List<Metadato> miLista = new ArrayList<>();
        //seteamos las variables contenedoras a cero.
        this.capturaMetadados.clear();
        //generamos los elementos del panel.        
        for (int i = 0; i < listaMetadados.size(); ++i) {
            //y sino si ya no esta en la lista de captura
            //antes de agregar controlar si el metadatos se debe repetir
            Metadato miMetadato = listaMetadados.get(i);
            if (miMetadato.isObligatorio()) { //podria obviarse el isExists()
                this.setCapturaMetadados(getComponente(listaMetadados.get(i), oaSelect));
                if (miMetadato.isRepite()) { // me fijo si ademas repite.                    
                    miLista.add(miMetadato);
                }
            } else if (miMetadato.isRepite()) {
                miLista.add(miMetadato);
            } else {
                miLista.add(miMetadato);
            }
        }
        // Ordeno
        //miLista.sort(Comparator.comparing(Metadato::getRotulo));
        //
        listaMetadados.clear();
        for (Metadato meta : miLista) {
            listaMetadados.addElement(meta);
        };
        //
        JPanel miPanel = rellenarJPanel2();
        captura.getViewport().setView(miPanel);
    }

    /**
     * Resive una lista de metadatos y lo agrega al panel de captura.
     *
     * @param objeto
     * @param miScrollPanel
     * @throws Exception
     *
     */
    public void setPanelCaptura2(List objeto, JScrollPane miScrollPanel, Metadato oaSelect) throws Exception {

        JPanel miJPanel = (JPanel) miScrollPanel.getViewport().getView();
        //JPanel jp = new JPanel();

        //si no hay datos
        if (!objeto.isEmpty()) {

            //generamos los elementos del panel.        
            for (int i = 0; i < objeto.size(); ++i) {
                //y sino si ya no esta en la lista de captura
                //antes de agregar controlar si el metadatos se debe repetir
                Metadato m = (Metadato) objeto.get(i);
                if (isExists(m)) {
                    // para el caso de que el metadato exista
                    // verificamos si este se puede repetir
                    // en tal caso se agrega a la lista
                    // if (repiteMetadato(m)) { //podria obviarse el isExists()
                    if (m.isRepite()) { //podria obviarse el isExists()
                        //final Metadato met = getComponente(objeto.get(i));
                        this.setCapturaMetadados(getComponente(objeto.get(i), oaSelect));
                    }
                } else {
                    //para el caso que no exista se crea y agtega
                    this.setCapturaMetadados(getComponente(objeto.get(i), oaSelect));
                }
            }
            //ordenamos capturaMetadatos -----------------------------------                       
            this.ordenarPanelCaptura(); // mejorar
            //--------------------------------------------------------------                       
            //cargamos el panel con los colectores                           
            miJPanel.removeAll();
            for (int i = 0; i < capturaMetadados.size(); ++i) {
                JPanel miFlowContainer = new JPanel();
                miFlowContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                miFlowContainer.add(capturaMetadados.get(i).getChequeado());
                miFlowContainer.add(capturaMetadados.get(i).getEtiqueta());
                miFlowContainer.add(capturaMetadados.get(i).getColector());
                miJPanel.add(miFlowContainer);
            }
            miJPanel.updateUI();
        }
    }

    private void setRotuloCaptura(Metadato meta) {
        /*seteamos el JTextLabel*/
        JLabel e = new JLabel();
        e.setPreferredSize(new Dimension(130, 30));
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(meta.getRotulo());
        meta.setEtiqueta(e);
    }

    private Metadato setearFecha(Metadato m) throws ParseException, Exception {
        int ord = 0;

        /*seteamos el JTextLabel*/
 /*JLabel e = new JLabel();
        e.setPreferredSize(new Dimension(60, 30));
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
         */
        setRotuloCaptura(m);
        //-------------------------------------------

        /*seteamos el orden*/
        ord = setOrdenMetadatos(m);
        m.setOrden(ord);
        //-------------------------------------------

        /*seteamos la fecha*/
        JDateChooser j = new JDateChooser();
        Calendar min = Calendar.getInstance();
        min.set(Calendar.YEAR, 1970);
        min.set(Calendar.MONTH, 1);
        min.set(Calendar.DATE, 1);
        j.setMinSelectableDate(min.getTime());
        Calendar max = Calendar.getInstance();
        max.set(Calendar.YEAR, 3000);
        max.set(Calendar.MONTH, 12);
        max.set(Calendar.DATE, 31);
        j.setMaxSelectableDate(max.getTime());
        j.setLocale(Locale.US);
        j.setPreferredSize(new Dimension(400, 50));
        //seteamos la fecha con la del sistema.
        Calendar ca = new GregorianCalendar();
        String day = ca.get(Calendar.DAY_OF_MONTH) + "";
        String month = ca.get(Calendar.MONTH) + 1 + "";
        String year = ca.get(Calendar.YEAR) + "";
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        String dd = year + "-" + month + "-" + day;
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dd);
        j.setDate(date);
        j.setCalendar(ca);
        m.setColector(j);

        return m;
    }

    private Metadato setearTexArea(Metadato m) throws Exception {
        int ord = 99;

        /*seteamos el JTextLabel*/
 /*
        JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
         */
        setRotuloCaptura(m);
        //-------------------------------------------

        JTextArea ta = new JTextArea(m.getRotulo());
        //ta.setPreferredSize(new Dimension(400, 250));
        //ta.setLineWrap(true);
        JScrollPane sp = new JScrollPane();
        //sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(400, 150));
        sp.setViewportView(ta);
        m.setColector(sp);
        ord = setOrdenMetadatos(m);
        m.setOrden(ord);
        //System.out.println("orden asignado " + m.getTipo() + " :" + ord);
        return m;
    }

    private Metadato setearComboBox(Metadato m, Vector data) throws Exception {
        int ord = 0;

        /*seteamos el JTextLabel*/
 /*JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
         */
        setRotuloCaptura(m);
        //-------------------------------------------

        JComboBox jcb = new JComboBox();
        if (data.size() > 0) {
            jcb.setModel(new DefaultComboBoxModel(data));
        }
        jcb.setPreferredSize(new Dimension(400, 40));
        ord = setOrdenMetadatos(m);
        m.setOrden(ord);
        //System.out.println("orden asignado " + m.getTipo() + " :" + ord);
        m.setColector(jcb);

        return m;
    }

    private Metadato setearSNRD(Metadato m, Metadato oaSelect) throws Exception {
        int ord = 0;

        setRotuloCaptura(m);
        //-------------------------------------------

        JTextField txt = new JTextField(oaSelect.getTipo());
        txt.setPreferredSize(new Dimension(400, 40));
        ord = this.setOrdenMetadatos(m);
        m.setOrden(ord);
        //System.out.println("orden asignado " + m.getTipo() + " :" + ord);
        m.setColector(txt);

        return m;
    }

    private Metadato setearDefault(Metadato m) throws Exception {
        int ord = 0;

        setRotuloCaptura(m);
        //-------------------------------------------

        JTextField txt = new JTextField(m.getTipo());
        txt.setPreferredSize(new Dimension(400, 40));
        ord = this.setOrdenMetadatos(m);
        m.setOrden(ord);
        //System.out.println("orden asignado " + m.getTipo() + " :" + ord);
        m.setColector(txt);

        return m;
    }

    private Metadato setearNombre2(Metadato miMetadato) {
        JPanel miPanel = new JPanel(new GridBagLayout());
        miPanel.setLayout(new BoxLayout(miPanel, BoxLayout.Y_AXIS));
        miPanel.setBorder(new LineBorder(Color.BLACK));
        //miPanel.setPreferredSize(new Dimension(400, 70));
        //
        JPanel miFlowContainer = new JPanel();
        //miFlowContainer.setBorder(new LineBorder(Color.blue));
        miFlowContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel jlape = new JLabel("Apellido");
        JLabel jlnom = new JLabel("Nombre");
        JTextField jtApellido = new JTextField("Apellido");
        jtApellido.setName("apellido");
        jtApellido.setMinimumSize(new Dimension(25, 25));
        jtApellido.setMaximumSize(new Dimension(300, 25));
        jtApellido.setPreferredSize(new Dimension(300, 25));
        JTextField jtNombre = new JTextField("Nombre");
        jtNombre.setName("nombre");
        jtNombre.setPreferredSize(new Dimension(300, 25));
        //GridBagConstraints c = new GridBagConstraints();

        //miFlowContainer.add(jlape, c);
        miFlowContainer.add(jlape);

        //miFlowContainer.add(jtApellido, c);
        miFlowContainer.add(jtApellido);
        miPanel.add(miFlowContainer);
        //
        miFlowContainer = new JPanel();
        //miFlowContainer.setBorder(new LineBorder(Color.RED));
        miFlowContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        //miFlowContainer.add(jlnom, c);
        miFlowContainer.add(jlnom);
        //miFlowContainer.add(jtNombre, c);
        miFlowContainer.add(jtNombre);
        miPanel.add(miFlowContainer);
        //
        /*seteamos el JTextLabel*/
        setRotuloCaptura(miMetadato);
        /*seteamos el colector con lo generado anteriormente*/
        miMetadato.setColector(miPanel);
        return miMetadato;
    }

    /**
     * Crear el componente (txtfield-textarea-etc) dentro de la clase.Luego lo
     * recorre y lo carga en el panel.
     *
     * @param o
     * @return Metadato
     * @throws java.lang.Exception
     */
    public Metadato getComponente(Object o, Metadato oaSelect) throws Exception {
        Metadato aMetaParam = (Metadato) o;
        Metadato m = new Metadato(aMetaParam.getTipo());
        m.setRotulo(aMetaParam.getRotulo());
        String tipoMetadato = m.getTipo();
        switch (tipoMetadato) {
            case "creator":
                setearNombre2(m);
                break;
            case "contributor":
                setearNombre2(m);
                break;
            case "date": //JDateChooser                
                this.setearFecha(m);
                break;
            case "datedefensed":
                this.setearFecha(m);
                break;
            case "dateembargoed":
                this.setearFecha(m);
                break;
            case "datepublished":
                this.setearFecha(m);
                break;
            case "driver":
                this.setearComboBox(m, getTypeDriver());
                break;
            case "difficulty":
                this.setearComboBox(m, this.getDifficulty());
                break;
            case "abstract":
                this.setearTexArea(m);
                break;
            case "description":
                this.setearTexArea(m);
                break;
            case "rights":
                this.setearTexArea(m);
                break;
            case "urilicense":
                this.setearTexArea(m);
                break;
            case "legal":
                this.setearTexArea(m);
                break;
            case "agerange":
                this.setearComboBox(m, this.getAgeRange());
                break;
            case "type":
                this.setearComboBox(m, getTypeDriver());
                break;
            case "typeresource":
                this.setearComboBox(m, this.getTipoResource());
                break;
            case "interactivitylevel":
                this.setearComboBox(m, this.getInterativityLevel());
                break;
            case "interactivitytype":
                this.setearComboBox(m, this.getInterativityType());
                break;
            case "version":
                this.setearComboBox(m, this.getVersion());
                break;
            case "format":
                this.setearComboBox(m, this.getFormat());
                break;
            case "role":
                this.setearComboBox(m, this.getRole());
                break;
            case "context":
                this.setearComboBox(m, this.getContext());
                break;
            case "language":
                this.setearComboBox(m, this.getLenguaje());
                break;
            case "snrd":
                this.setearSNRD(m, oaSelect);
                break;
            default:
                this.setearDefault(m);
                break;
        }
        return m;
    }

    // 
    private void ordenarPanelCaptura() {
        ArrayList<Metadato> auxList = new ArrayList<>();
        for (int i = 0; i < capturaMetadados.size(); ++i) {
            auxList.add(capturaMetadados.get(i));
        }
        //auxList.sort(Comparator.comparing(Metadato::getOrden));
        auxList.sort(Comparator.comparing(Metadato::getRotulo));
        capturaMetadados.removeAllElements();
        for (int j = 0; j < auxList.size(); ++j) {
            capturaMetadados.addElement(auxList.get(j));
        }
    }

    // 
    public void ordenarMetadatos() {
        ArrayList<Metadato> auxList = new ArrayList<>();
        for (int i = 0; i < listaMetadados.size(); ++i) {
            auxList.add(listaMetadados.get(i));
        }
        auxList.sort(Comparator.comparing(Metadato::getRotulo));
        listaMetadados.removeAllElements();
        for (int j = 0; j < auxList.size(); ++j) {
            //listaMetadados.add(j, auxList.get(j));
            listaMetadados.addElement(auxList.get(j));
        }
    }

    /**
     * Metodo que devuelve los metadatos de un objeto SNRD buscado.
     *
     * @param filtro representa el Objeto de aprendizaje(OA) a buscar en la
     * ontologia.
     * @return
     * @throws StardogException
     * @throws java.lang.Exception
     */
    public DefaultListModel getMetadatos_v1(Metadato filtro) throws StardogException, Exception {
        ArrayList<String> auxMetadatos = new ArrayList<>();
        BindingSet fila;
        TupleQueryResult aResult;
        //
        listaMetadados.clear();
        IRI iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#" + "snrd");
        IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "isSnrdTypeOf");
        IRI iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "domain");
        IRI iri4 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "range");
        IRI iri5 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

        SelectQuery aQuery = conexionStardog.select(
                "select distinct ?predi "
                + "where { "
                + " ?sujeto ?predi1 ?snrd."
                + " ?sujeto ?predi2 ?titulo."
                + " ?titulo ?predi ?nouso."
                //+ " ?predi ?premain ?domi;  ?prange ?rango."
                //+ " ?subc ?predi5 ?domi."
                + " filter regex(str(?snrd), ?buscar)."
                //+ " filter regex(str(?domi), 'Title')."
                //+ " filter regex(str(?subc), 'subTitle')."
                + " filter regex(str(?predi), 'has')."
                + "} "
        );
        //System.out.println("filtro:   " + filtro.getTipo() );
        aQuery.parameter("predi1", iri1);
        aQuery.parameter("predi2", iri2);
        aQuery.parameter("premain", iri3);
        aQuery.parameter("prange", iri4);
        aQuery.parameter("predi5", iri5);
        aQuery.parameter("buscar", filtro.getTipo());
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue1 = fila.getValue("predi").stringValue();
            final String aValue2 = fila.getValue("predi").stringValue();
            final String aValue3 = fila.getValue("predi").stringValue();
            final Metadato m1 = new Metadato(aValue1);
            final Metadato m2 = new Metadato(aValue2);
            final Metadato m3 = new Metadato(aValue3);
            if (!auxMetadatos.contains(m1.getTipo())) {
                auxMetadatos.add(m1.getTipo());
                final boolean obligatorio = obligatorioMetadato(m1.getTipo().trim().toLowerCase());
                final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                m1.setObligatorio(obligatorio);
                m1.setRepite(repite);
                m1.setRotulo(rotulo);
                listaMetadados.addElement(m1);
            }
            if (!auxMetadatos.contains(m2.getTipo())) {
                auxMetadatos.add(m2.getTipo());
                final boolean obligatorio = obligatorioMetadato(m2.getTipo().trim().toLowerCase());
                final boolean repite = repiteMetadato(m2.getTipo().trim().toLowerCase());
                final String rotulo = rotuloMetadato(m2.getTipo().trim().toLowerCase());
                m2.setObligatorio(obligatorio);
                m2.setRepite(repite);
                m2.setRotulo(rotulo);
                listaMetadados.addElement(m2);
            }
            if (!auxMetadatos.contains(m3.getTipo())) {
                auxMetadatos.add(m3.getTipo());
                final boolean obligatorio = obligatorioMetadato(m3.getTipo().trim().toLowerCase());
                final boolean repite = repiteMetadato(m3.getTipo().trim().toLowerCase());
                final String rotulo = rotuloMetadato(m3.getTipo().trim().toLowerCase());
                m3.setObligatorio(obligatorio);
                m3.setRepite(repite);
                m3.setRotulo(rotulo);
                listaMetadados.addElement(m3);
            }
        }
        return listaMetadados;
    }

    /**
     * Metodo que devuelve los metadatos de un objeto SNRD buscado.
     *
     * @param filtro representa el Objeto de aprendizaje(OA) a buscar en la
     * ontologia.
     * @throws StardogException
     */
    public void getMetadatos_v2(Metadato filtro, ActionEvent evt) {
        DialogWaitControl wait = new DialogWaitControl();

        SwingWorker<Void, String> mySwingWorker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                BindingSet fila;
                TupleQueryResult aResult;
                listaMetadados.clear();
                IRI iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#" + "snrd");
                IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "isSnrdTypeOf");
                //
                SelectQuery aQuery = conexionStardog.select(
                        "select distinct ?predi ?predicado "
                        + "where { "
                        + " ?sujeto ?predi1 ?snrd."
                        + " ?sujeto ?predi2 ?titulo."
                        + " ?titulo ?predi ?nouso."
                        + " ?algo ?predicado ?titulo."
                        + " filter regex(str(?snrd), ?buscar)."
                        + " filter regex(str(?predi), 'has')."
                        + " filter regex(str(?predicado), 'has')."
                        + "}"
                );
                //
                aQuery.parameter("predi1", iri1);
                aQuery.parameter("predi2", iri2);
                aQuery.parameter("buscar", filtro.getTipo());
                aResult = aQuery.execute();
                //int soloUna = 0;
                while (aResult.hasNext()) {
                    fila = aResult.next();
                    final String miresultado = fila.getValue("predi").stringValue();
                    final Metadato m1 = new Metadato(miresultado);
                    //seteo del tipo
                    m1.setTipo(m1.getTipo().substring(3)); //saco el has del predicado
                    final boolean obligatorio = obligatorioMetadato(m1.getTipo().trim().toLowerCase());
                    final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                    final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                    //
                    m1.setObligatorio(obligatorio);
                    m1.setRepite(repite);
                    m1.setRotulo(rotulo);
                    listaMetadados.addElement(m1);
                }
                // obtenemos los metadatos de Title.           
                iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                        + "Title");
                iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
                IRI iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

                aQuery = conexionStardog.select(
                        "SELECT DISTINCT ?subclase "
                        + "WHERE { "
                        + " ?subclase ?predicado ?metadata."
                        + "} "
                );
                aQuery.parameter("predicado", iri3);
                aQuery.parameter("metadata", iri1);
                aResult = aQuery.execute();
                while (aResult.hasNext()) {
                    fila = aResult.next();
                    final String aValue = fila.getValue("subclase").stringValue();
                    final Metadato m1 = new Metadato(aValue);
                    final boolean obligatorio = obligatorioMetadato(m1.getTipo().trim().toLowerCase());
                    final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                    final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                    m1.setObligatorio(obligatorio);
                    m1.setRepite(repite);
                    m1.setRotulo(rotulo);
                    listaMetadados.addElement(m1);
                }
                wait.close();
                return null;
            }
        };
        if (estatus()) {
            mySwingWorker.execute();
            wait.makeWait("Obteniendo datos.", evt);
        }
    }

    /**
     * Devuelve los metadatos enxistentes en la ontologia.
     *
     * @return
     * @throws StardogException
     *
     * public DefaultListModel getMetadatos() throws StardogException {
     *
     * ArrayList<String> auxMetadatos = new ArrayList<>(); BindingSet fila;
     * TupleQueryResult aResult;
     *
     * //POR ALGUNA RAZON QUE DESCONOZCO NO DEVUELVE LA CANTIDAD QUE TIRA
     * PROTÉGÉ. listaMetadados.clear(); IRI iri1 =
     * Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf"); IRI
     * iri2 =
     * Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#"
     * + "Educational");
     *
     * SelectQuery aQuery = conexionStardog.select( "SELECT DISTINCT ?sujeto " +
     * "WHERE { " + " ?sujeto ?predicado ?objeto. " + "} " );
     * aQuery.parameter("predicado", iri1); //aQuery.parameter("predicado7",
     * iri2); aResult = aQuery.execute(); while (aResult.hasNext()) { fila =
     * aResult.next();
     *
     * //System.out.println("fila..:" + fila); final String aSujeto =
     * fila.getValue("sujeto").stringValue();
     *
     * //System.out.println("dominio..:" + aValue1); //revisamos el campo.
     * //System.out.println("rango..:" + aValue2); //revisamos el campo.
     * //System.out.println("subClase..:" + aValue3); //revisamos el campo.
     * //System.out.println("Sujeto..:" + aSujeto); //revisamos el campo. final
     * Metadato m1 = new Metadato(aSujeto, aSujeto);
     *
     * if (!auxMetadatos.contains(aSujeto)) { auxMetadatos.add(aSujeto);
     * listaMetadados.addElement(m1); } } return listaMetadados; }
     */
    /**
     * Extrae de la ontologia los formatos de los OA
     *
     * @return Vector.
     *
     * @throws StardogException
     */
    public Vector getFormat() throws StardogException {
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasFormat");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#mimeType");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT ?descrip "
                + "WHERE { "
                + "?p ?formato ?tipo."
                + " ?tipo ?mime ?descrip."
                + "}"
        );
        aQuery.parameter("formato", sv);
        aQuery.parameter("mime", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("descrip").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    /**
     *
     * @return Devuelve todos los tipos de Driver presentes en la ontologia
     * @throws StardogException
     */
    public Vector getTypeDriver() throws StardogException {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        //IRI _sv = Values.iri(instancia.getIriOnto().trim() + "hasDriverType");
        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasDriverType");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#driver");

        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?descrip WHERE { "
                + "?OA ?predicado1 ?tipo."
                + "?tipo ?predicado2 ?descrip."
                + "}"
        );
        aQuery.parameter("predicado1", sv);
        aQuery.parameter("predicado2", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {

            fila = aResult.next();
            final String aValue = fila.getValue("descrip").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getAgeRange() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //DefaultListModel<String> resultado = new DefaultListModel<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasAgeRange");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#ageRange");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?descrip WHERE {"
                + "?OA ?typeSV ?tipoRange."
                + "?tipoRange ?typeON ?descrip. "
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeON", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("descrip").stringValue();
            //System.out.println("tipo snrd ....:" + aValue);
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }

        return resultado;
    }

    /**
     * Obtiene las dificultades de los OA existentes en la ontologia.
     *
     * @return
     */
    public Vector getDifficulty() {
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasDificulty");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#difficulty");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripDifficulty WHERE { "
                + "?OA ?typeSV ?tipoDificultad."
                + "?tipoDificultad ?typeON ?DescripDifficulty"
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeON", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripDifficulty").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }

        return resultado;
    }

    /**
     * Obtiene el lenguaje de los OA existentes en la ontologia
     *
     * @return Lista
     */
    public Vector getLenguaje() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasLanguage");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#alpha3");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripLenguaje WHERE {"
                + "?OA ?typeSV ?tipoLenguaje."
                + "?tipoLenguaje ?typeON ?DescripLenguaje."
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeON", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripLenguaje").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getInterativityType() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasInteractivityType");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#typeInt");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripInteractivityType WHERE {"
                + "?OA ?typeSV ?tipo."
                + "?tipo ?typeON ?DescripInteractivityType."
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeON", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripInteractivityType").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getInterativityLevel() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasInteractivityLevel");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#level");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripInteractivityLevel WHERE {"
                + "?OA ?typeSV ?tipoLevel."
                + "?tipoLevel ?typeON ?DescripInteractivityLevel."
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeON", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripInteractivityLevel").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getVersion() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasVersion");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#version");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripVersion WHERE {"
                + "?OA ?typeSV ?tipoVersion."
                + "?tipoVersion ?typeON ?DescripVersion."
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeON", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripVersion").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getTipoResource() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasTypeResource");
        IRI sv2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#learningResource");
        //IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#version");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripResource WHERE {"
                + "?OA ?typeSV ?tipoResource."
                + "?tipoResource ?typeSV2 ?DescripResource."
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeSV2", sv2);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripResource").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getRole() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasRole");
        IRI sv2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#role");
        //IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#version");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT DISTINCT ?DescripRole WHERE {"
                + "?OA ?typeSV ?tipoResource."
                + "?tipoResource ?typeSV2 ?DescripRole."
                + "}"
        );
        aQuery.parameter("typeSV", sv);
        aQuery.parameter("typeSV2", sv2);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("DescripRole").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    public Vector getContext() {
        //DefaultListModel<MetadataSimple> resultado = new DefaultListModel<>();
        //Vector<MetadataSimple> resultado = new Vector<>();
        Vector<String> resultado = new Vector<>();
        BindingSet fila;
        TupleQueryResult aResult;

        IRI sv = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#hasContext");
        IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#context");
        SelectQuery aQuery = conexionStardog.select(
                "SELECT ?descrip "
                + "WHERE {"
                + "?objeto ?sv ?tipo."
                + "?tipo ?on ?descrip."
                + "}"
        );
        aQuery.parameter("sv", sv);
        aQuery.parameter("on", on);
        aResult = aQuery.execute();
        while (aResult.hasNext()) {
            fila = aResult.next();
            final String aValue = fila.getValue("descrip").stringValue();
            //MetadataSimple m;
            //m = new Metadato(aValue, aValue);
            //resultado.addElement(m);
            final String aux;
            if (aValue.contains("#")) {
                aux = aValue.substring(aValue.indexOf("#") + 1, aValue.length());
            } else {
                aux = aValue;
            }
            resultado.addElement(aux);
        }
        return resultado;
    }

    /**
     * Genera un archivo json con la especificación de los metadatos.
     */
    public void generarJson() {
        FileWriter file = null;
        JSONObject data = new JSONObject();
        JSONArray list = new JSONArray();
        try {
            ConfigControl config = ConfigControl.getInstancia();
            //List<Element> aListEle = new ArrayList<>();
            //StardogControler ontologia = StardogControl.getInstancia();
            DublinCoreControl dublincore = DublinCoreControl.getInstancia();
            //DefaultListModel<Metadato> aListMetadatos = ontologia.getCapturaMetadados();
            //final String uri = "http://purl.org/dc/elements/1.1/";

            for (int i = 0; i < capturaMetadados.size(); ++i) {
                Metadato m = capturaMetadados.get(i);
                //System.out.println("pase por aca!. metadato: " + m.getTipo());
                //Object aDC = dublincore.getEquivalenciaDC(m.getTipo().toLowerCase().trim());
                Object aDC = dublincore.buscarEquivalencias(m.getTipo().toLowerCase().trim());
                if (aDC != null) {
                    if (aDC instanceof StringTokenizer) {
                        StringTokenizer aux = (StringTokenizer) aDC;
                        //String[] result = "this is a test".split("\\s");
                        //dividimos la cadena de los periodos
                        String[] result = m.getContenidoMetadato().split("-"); //mejorar la entrega del valor
                        i = 0;
                        while (aux.hasMoreTokens()) {
                            //String dato = result[i];aux.nextToken()
                            JSONObject obj = new JSONObject();
                            obj.put("key", aux.nextToken().trim());
                            obj.put("value", result[i].trim());
                            list.add(obj); // [{},{},...]
                            i++;
                        }
                    } else {
                        final String myDC = ((String) aDC);
                        // ...       
                        //System.out.println("Contenido "+i+1+": " + m.getContenidoMetadato());
                        JSONObject obj = new JSONObject();
                        obj.put("key", myDC);

                        String rawString = m.getContenidoMetadato();
                        //convertimos 
                        String newValue = new String(rawString.getBytes("UTF-8"));
                        //
                        obj.put("value", newValue);
                        list.add(obj); // [{},{},...]
                    }
                }
            }
            data.put("metadata", list); // {"metadata":[{},{},...]}            
            //            
            file = new FileWriter(new File(config.getFolderWork() + "metadatos.json"));
            file.write(data.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void generarJSONSinBitstreams() {
        FileWriter file = null;
        JSONArray array = new JSONArray();
        try {
            ConfigControl config = ConfigControl.getInstancia();
            DublinCoreControl dublincore = DublinCoreControl.getInstancia();

            for (int i = 0; i < capturaMetadados.size(); ++i) {
                Metadato m = capturaMetadados.get(i);
                //System.out.println("pase por aca!. metadato: " + m.getTipo());
                //Object aDC = dublincore.getEquivalenciaDC(m.getTipo().toLowerCase().trim());
                Object aDC = dublincore.buscarEquivalencias(m.getTipo().toLowerCase().trim());
                if (aDC != null) {
                    if (aDC instanceof StringTokenizer) {
                        StringTokenizer aux = (StringTokenizer) aDC;
                        //String[] result = "this is a test".split("\\s");
                        //dividimos la cadena de los periodos
                        String[] result = m.getContenidoMetadato().split("-"); //mejorar la entrega del valor
                        i = 0;
                        while (aux.hasMoreTokens()) {
                            //String dato = result[i];aux.nextToken()
                            JSONObject obj = new JSONObject();
                            obj.put("key", aux.nextToken().trim());
                            obj.put("value", result[i].trim());
                            array.add(obj); // [{},{},...]
                            i++;
                        }
                    } else {
                        final String myDC = ((String) aDC);
                        // ...       
                        //System.out.println("Contenido "+i+1+": " + m.getContenidoMetadato());
                        JSONObject obj = new JSONObject();
                        obj.put("key", myDC);

                        String rawString = m.getContenidoMetadato();
                        //convertimos 
                        String newValue = new String(rawString.getBytes("UTF-8"));
                        //
                        obj.put("value", newValue);
                        array.add(obj); // [{},{},...]
                    }
                }
            }
            //data.put("metadata", list); // {"metadata":[{},{},...]}
            //            
            file = new FileWriter(new File(config.getFolderWork() + "metadatos_sb.json"));
            file.write(array.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //public void getMetadatos_v6(JTextArea ta, ActionEvent evt) {
    public void getMetadatos_v6(JTextArea ta, JList lista, Metadato aDriver) throws InterruptedException, ExecutionException {
        //DialogWaitControl wait = new DialogWaitControl(50);
        SwingWorker<Void, String> mySwingWorker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    TupleQueryResult aResult;
                    //
                    IRI iri1 = Values.iri("http://www.w3.org/2000/01/rdf-schema#subClassOf");
                    IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#Metadata");
                    SelectQuery aQuery = conexionStardog.select(
                            "SELECT distinct ?subclase "
                            + "WHERE { "
                            + " ?subclase ?predicado ?metadata."
                            + "} "
                    );
                    aQuery.parameter("predicado", iri1);
                    aQuery.parameter("metadata", iri2);
                    aResult = aQuery.execute();
                    // colector de resultados de la consulta.    
                    Collection<String> subjects = new ArrayList<>();
                    try {
                        while (aResult.hasNext()) {
                            BindingSet bs = aResult.next();
                            subjects.add(bs.getBinding("subclase").getValue().toString());
                        }
                    } finally {
                        aResult.close();
                    }
                    if (subjects.size() > 0) {
                        listaMetadados.removeAllElements();
                    } else {
                        return null;
                    }
                    publish("Metadatos encontrados:" + subjects.size() + ".\n");
                    // --------------------------------------------------------------------
                    publish("Procesando metadatos. Aguarde. \n");
                    //wait.setearProgressBar(subjects.size());
                    //int intProgressBar = 0;
                    // -------------------------------------------------
                    ConfigControl config = ConfigControl.getInstancia();
                    properties = config.getConfigMetadatos();
                    // -------------------------------------------------
                    for (String col : subjects) {
                        final String aValue = col;
                        final Metadato m1 = new Metadato(aValue);
                        final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                        if (!rotulo.isEmpty()) {
                            boolean obligatorio = false;
                            if (aDriver != null) {
                                obligatorio = obligatorioMetadato2(
                                        m1.getTipo().trim().toLowerCase(),
                                        aDriver.getDriver());
                            } else {
                                obligatorio = obligatorioMetadato(
                                        m1.getTipo().trim().toLowerCase());
                            }
                            final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                            m1.setObligatorio(obligatorio);
                            m1.setRepite(repite);
                            m1.setRotulo(rotulo);
                            listaMetadados.addElement(m1);
                        }
                    }
                    publish("Proceso de extracción de metadatos terminada.\n");
                    aResult.close();
                } catch (Exception ex) {
                    Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }

            @Override
            protected void done() {
                sortMetadatos();
                lista.setModel(listaMetadados);
            }
        };
        mySwingWorker.execute();
        mySwingWorker.get();
    }

    public void sortMetadatos() {
        List<Metadato> list = new ArrayList<>();
        for (int i = 0; i < listaMetadados.size(); i++) {
            //System.out.println("posicion: " + i + " - valor: " +  listaMetadados.get(i)); 
            list.add((Metadato) listaMetadados.get(i));
        }
        Collections.sort(list);
        listaMetadados.removeAllElements();
        for (Metadato s : list) {
            listaMetadados.addElement(s);
        }
    }

    public static void printList(String title, DefaultListModel miLista) {

        System.out.println(title + "tamaño: " + miLista.size());

        List<Metadato> list = new ArrayList<>();
        for (int i = 0; i < miLista.size(); i++) {
            list.add((Metadato) miLista.get(i));
        }

        list.forEach(x -> System.out.println("\t" + x.toString()));
        System.out.println("");
    }

    public void quitarMetadatoCaptura(JScrollPane miScrollPanel) {
        DefaultListModel<Metadato> misMetadatos = new DefaultListModel<>();
        if (capturaMetadados.size() > 0) {
            for (int i = 0; i < capturaMetadados.size(); i++) {
                Metadato elMetadato = capturaMetadados.get(i);
                if (!capturaMetadados.get(i).isChequeado()) {
                    misMetadatos.addElement(elMetadato);
                } else {
                    System.out.println("metadato: " + elMetadato.getRotulo());
                }
            }
            capturaMetadados.removeAllElements();
            capturaMetadados = misMetadatos;
            // Obtenemos el panel del scroll de captura.
            JPanel miJPanel = (JPanel) miScrollPanel.getViewport().getView();
            //
            //ordenamos capturaMetadatos -----------------------------------                                   
            //this.ordenar(); // No es necesatrio.
            //--------------------------------------------------------------                       
            //cargamos el panel con los colectores                           
            miJPanel.removeAll();
            for (int i = 0; i < capturaMetadados.size(); ++i) {
                JPanel miFlowContainer = new JPanel();
                miFlowContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                miFlowContainer.add(capturaMetadados.get(i).getChequeado());
                miFlowContainer.add(capturaMetadados.get(i).getEtiqueta());
                miFlowContainer.add(capturaMetadados.get(i).getColector());
                miJPanel.add(miFlowContainer);
            }
        }
    }

}
