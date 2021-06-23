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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
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

    public void conectar() {
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
                IRI on = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#snrd");
                SelectQuery aQuery = conexionStardog.select(
                        "SELECT DISTINCT ?tipoSnrd WHERE { "
                        + " ?sujeto ?typeSV ?objeto."
                        + " ?objeto ?typeON ?tipoSnrd."
                        + "}"
                );
                aQuery.parameter("typeSV", sv);
                aQuery.parameter("typeON", on);
                aResult = aQuery.execute();
                publish("Obteniendo la lista de Objetos de Aprendizaje presente en la BD gráfica.\n");
                while (aResult.hasNext()) {
                    fila = aResult.next();
                    final String aValue = fila.getValue("tipoSnrd").stringValue();
                    Metadato m;
                    m = new Metadato(aValue, aValue);
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
        ConfigControl config = ConfigControl.getInstancia();
        //String folder = config.getFolderPropiedades(); 
        //propertiesStream = new FileInputStream("src/main/java/propiedades/configMetadatos.properties");
        //propertiesStream = new FileInputStream(folder+"configMetadatos.properties");
        //
        //properties.load(propertiesStream);
        properties = config.getConfigMetadatos();
        //String cadena = aString + ".obligatorio";
        String aValue = properties.getProperty(aString + ".obligatorio");
        //System.out.println("metadato obligatorio: " + cadena + "  | " + aValue);
        if ((aValue != null) && (aValue.equals("1"))) {
            //System.out.println("metadato obligatorio: " + aString);
            obligatorio = true;
        }
        return obligatorio;
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
    
    /*
    public String getRotulo(String aString) throws Exception {
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
    */

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

    //rellena el jpnel con los componentes a mostrar.
    private JPanel rellenarJPanel(JPanel jp) {
        jp.setLayout(new GridBagLayout());
        //seteamos el grid
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 0, 0, 0);
        //cargamos el panel con los colectores                
        for (int i = 0; i < capturaMetadados.size(); ++i) {
            c.gridx = 0;
            c.gridy = i;
            jp.add(capturaMetadados.get(i).getEtiqueta(), c);
            c.gridx = 1;
            c.gridy = i;
            jp.add(capturaMetadados.get(i).getColector(), c);
        }

        //mejoramos la visual si tiene pocos componentes la lista capturada.           
        if (capturaMetadados.size() < 10) {
            for (int i = capturaMetadados.size() + 1; i < 10; ++i) {
                JLabel aux = new JLabel("-");
                c.gridx = 0;
                c.gridy = i;
                jp.add(aux, c);
                c.gridx = 1;
                c.gridy = i;
                jp.add(aux, c);
            }
        }
        return jp;
    }

    /**
     * Setea el panel de captura con los metadatos obligatorios.
     *
     * @return
     * @throws Exception
     */
    public JPanel preSeteoPanelCaptura() throws Exception {
        JPanel jp = new JPanel();
        DefaultListModel<Metadato> noMostrar = new DefaultListModel<>();

        //seteamos las variables contenedoras a cero.
        this.capturaMetadados.clear();

        //generamos los elementos del panel.        
        for (int i = 0; i < listaMetadados.size(); ++i) {
            //y sino si ya no esta en la lista de captura
            //antes de agregar controlar si el metadatos se debe repetir
            Metadato m = listaMetadados.get(i);
            //System.out.println("mertadato:" + m.getTipo() + " | repite: " + m.isRepite() + " | obligatorio: " + m.isObligatorio());
            if (m.isObligatorio()) { //podria obviarse el isExists()
                //final Metadato met = getComponente(objeto.get(i));
                //if (m.isRepite()) {
                this.setCapturaMetadados(getComponente(listaMetadados.get(i)));
                //} else {
                //para el caso que no exista se crea y agtega
                //    this.setCapturaMetadados(getComponente(listaMetadados.get(i)));
                //listaMetadados.remove(i);
                //noMostrar.addElement(m); //ya no hace falta
            }
            if (m.isRepite()) {
                noMostrar.addElement(m); //ya no hace falta
            }
        }

        listaMetadados = noMostrar;
        //quitamos los metadatos que se agregaron pero no se repiten.
        //for (int i = 0; i < eliminar.size(); ++i) {
        //    listaMetadados.removeElement(eliminar.get(i));
        //}

        //ordenamos capturaMetadatos -----------------------------------
        this.ordenar(); // mejorar
        //--------------------------------------------------------------
        jp = rellenarJPanel(jp);
        return jp;
    }

    /**
     * Genera los contenedores para los metadatos seleccionados.
     *
     * @param objeto Lista de metadatos seleccionados.
     * @return El panel con los metadatos seteados.
     * @throws Exception
     */
    public JPanel setPanelCaptura(List objeto) throws Exception {
        JPanel jp = new JPanel();

        //si no hay datos
        if (objeto.isEmpty()) {
            return jp;
        }

        //generamos los elementos del panel.        
        for (int i = 0; i < objeto.size(); ++i) {
            //y sino si ya no esta en la lista de captura
            //antes de agregar controlar si el metadatos se debe repetir
            Metadato m = (Metadato) objeto.get(i);
            if (isExists(m)) {
                //para el caso de que el metadato exista
                //verificamos si este se puede repetir
                //en tal caso se agrega a la lista
                //if (repiteMetadato(m)) { //podria obviarse el isExists()
                if (m.isRepite()) { //podria obviarse el isExists()
                    //final Metadato met = getComponente(objeto.get(i));
                    this.setCapturaMetadados(getComponente(objeto.get(i)));
                }
            } else {
                //para el caso que no exista se crea y agtega
                this.setCapturaMetadados(getComponente(objeto.get(i)));
            }
        }

        //ordenamos capturaMetadatos -----------------------------------                       
        this.ordenar(); // mejorar
        //--------------------------------------------------------------

        //System.out.println("entreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee ---  despues de ordenar" );    
        jp.setLayout(new GridBagLayout());
        //seteamos el grid
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 0, 0, 0);
        //cargamos el panel con los colectores                
        for (int i = 0; i < capturaMetadados.size(); ++i) {
            c.gridx = 0;
            c.gridy = i;
            jp.add(capturaMetadados.get(i).getEtiqueta(), c);
            c.gridx = 1;
            c.gridy = i;
            jp.add(capturaMetadados.get(i).getColector(), c);
        }

        //mejoramos la visual            
        if (capturaMetadados.size() < 10) {
            for (int i = capturaMetadados.size() + 1; i < 10; ++i) {
                JLabel aux = new JLabel("-");
                c.gridx = 0;
                c.gridy = i;
                jp.add(aux, c);
                c.gridx = 1;
                c.gridy = i;
                jp.add(aux, c);
            }
        }

        return jp;
    }

    private Metadato setearFecha(Metadato m) throws ParseException, Exception {
        int ord = 0;

        /*seteamos el JTextLabel*/
        JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
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
        JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
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
        JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
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

    private Metadato setearDefault(Metadato m) throws Exception {
        int ord = 0;

        /*seteamos el JTextLabel*/
        JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(m.getRotulo());
        m.setEtiqueta(e);
        //-------------------------------------------

        JTextField txt = new JTextField(m.getTipo());
        txt.setPreferredSize(new Dimension(400, 40));
        ord = this.setOrdenMetadatos(m);
        m.setOrden(ord);
        //System.out.println("orden asignado " + m.getTipo() + " :" + ord);
        m.setColector(txt);

        return m;
    }

    private Metadato setearNombre(Metadato miMetadato) {
        JPanel jp = new JPanel(new GridBagLayout());
        jp.setPreferredSize(new Dimension(400, 150));
        JLabel jlape = new JLabel("Apellido:");
        JLabel jlnom = new JLabel("Nombre:");
        JTextField apellido = new JTextField("Apellido");
        apellido.setName("apellido");
        apellido.setPreferredSize(new Dimension(200, 40));
        JTextField nombre = new JTextField("Nombre");
        nombre.setName("nombre");
        nombre.setPreferredSize(new Dimension(200, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        //cargamos los componentes al panel
        c.gridx = 0;
        c.gridy = 0;
        jp.add(jlape, c);
        c.gridx = 1;
        c.gridy = 0;
        jp.add(apellido, c);
        //-------
        c.gridx = 0;
        c.gridy = 1;
        jp.add(jlnom, c);
        c.gridx = 1;
        c.gridy = 1;
        jp.add(nombre, c);

        /*seteamos el JTextLabel*/
        JLabel e = new JLabel();
        e.setFont(new Font("Serif", Font.BOLD, 12));
        e.setForeground(Color.BLACK);
        e.setText(miMetadato.getRotulo());
        miMetadato.setEtiqueta(e);
        /*seteamos el colector con lo generado anteriormente*/
        miMetadato.setColector(jp);
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
    public Metadato getComponente(Object o) throws Exception {
        int ord = 0;
        Metadato aMetaParam = (Metadato) o;
        Metadato m = new Metadato(aMetaParam.getTipo());
        m.setRotulo(aMetaParam.getRotulo());
        //JLabel e = new JLabel();
        //e.setFont(new Font("Serif", Font.BOLD, 12));
        //e.setForeground(Color.BLACK);
        //e.setText(aMetaParam.getRotulo());
        //System.out.println("tiipoooooo :" + m.getTipo());
        String tipoMetadato = m.getTipo();
        switch (tipoMetadato) {
            case "creator":
                setearNombre(m);
                break;
            case "contributor":
                setearNombre(m);
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
            default:
                this.setearDefault(m);
                break;
        }
        return m;
    }

    // FIX ME.
    private void ordenar() {
        ArrayList<Metadato> auxList = new ArrayList<>();
        for (int i = 0; i < capturaMetadados.size(); ++i) {
            auxList.add(capturaMetadados.get(i));
        }
        auxList.sort(Comparator.comparing(Metadato::getOrden));
        capturaMetadados.removeAllElements();
        for (int j = 0; j < auxList.size(); ++j) {
            capturaMetadados.add(j, auxList.get(j));
        }
    }

    // FIX ME.
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
    public void jsonMetadatos() {
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

    public void jsonMetadatosSinBitstreams() {
        FileWriter file = null;
        JSONObject data = new JSONObject();
        JSONArray list = new JSONArray();
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
            //data.put("metadata", list); // {"metadata":[{},{},...]}
            //            
            file = new FileWriter(new File(config.getFolderWork() + "metadatos_sb.json"));
            file.write(list.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     *
     * @return Lista con los metadatos presentes en la ontología.
     * @throws StardogException
     *
     */
    public DefaultListModel getMetadatos_v3() {
        try {
            BindingSet fila;
            TupleQueryResult aResult;
            //
            listaMetadados.clear();
            IRI iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#" + "Content");
            IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            IRI iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            SelectQuery aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
            //aQuery.parameter("buscar", filtro.getTipo());
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
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de Coverage.            
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "Coverage");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de Subject.
            // falta agregar keyboard
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "Subject");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
            // -----------------------------------------------------------------------------
            /*
            // obtenemos los metadatos de Title.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "Title");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
             */
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de Instantiation.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "Instantiation");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
            // -----------------------------------------------------------------------------
            /*
            // obtenemos los metadatos de Date.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "Date");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
             */
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de Educational.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "Educational");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri2);
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
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de General.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "General");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri2);
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
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de IntelectualProperty.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "IntelectualProperty");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
            // -----------------------------------------------------------------------------
            // obtenemos los metadatos de Agent.           
            iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "");
            iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "Agent");
            iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri2);
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaMetadados;
    }

    public DefaultListModel getMetadatos_v4() {
        try {
            BindingSet fila;
            TupleQueryResult aResult;

            listaMetadados.clear();
            IRI iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#"
                    + "");
            IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "Educational");
            IRI iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#" + "subClassOf");

            SelectQuery aQuery = conexionStardog.select(
                    "SELECT DISTINCT ?subclase "
                    + "WHERE { "
                    + " ?subclase ?predicado ?metadata."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("metadata", iri2);
            //aQuery.parameter("buscar", filtro.getTipo());
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
        } catch (Exception ex) {
            Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMetadados;
    }

    public void getMetadatos_v5(JTextArea ta, ActionEvent evt)
            throws InterruptedException, ExecutionException {
        DialogWaitControl wait = new DialogWaitControl(50);
        SwingWorker<Void, String> mySwingWorker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    //BindingSet fila;
                    TupleQueryResult aResult;
                    //, aResult2;
                    listaMetadados.removeAllElements();

                    // obtenemos los metadatos de IntelectualProperty.           
                    //IRI iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#IntelectualProperty");
                    //IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "");
                    IRI iri3 = Values.iri("http://www.w3.org/2000/01/rdf-schema#subClassOf");
                    /*
            SelectQuery aQuery = conexionStardog.select(
                    "SELECT distinct ?subclase "
                    + "WHERE { "
                    + "?subclase ?predicado ?tipo."
                    + "} "
            );
            aQuery.parameter("predicado", iri3);
            aQuery.parameter("tipo", iri1);
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
                //listaMetadados.addElement(m1);
            }*/
                    // -----------------------------------------------------------------------------

                    // obtenemos los metadatos presentes en la ontologia.           
                    //iri1 = Values.iri("http://www.semanticweb.org/lk/ontologies/2017/3/SharedVocabulary.owl#Instantiation");
                    //IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#" + "Educational");
                    IRI iri2 = Values.iri("http://www.semanticweb.org/valeria/ontologies/2017/10/OntoVC#Metadata");

                    SelectQuery aQuery = conexionStardog.select(
                            "SELECT distinct ?subclase "
                            + "WHERE { "
                            + " ?subclase ?predicado ?metadata."
                            + "} "
                    );
                    aQuery.parameter("predicado", iri3);
                    aQuery.parameter("metadata", iri2);
                    //aQuery.parameter("buscar", filtro.getTipo());
                    aResult = aQuery.execute();
                    //aResult2 = aQuery.execute();
                    //                    
                    //DialogWaitControl wait = new DialogWaitControl(100);
                    //wait.makeWait("Procesando metadatos. Aguarde", evt);

                    //Collection<Value> subjects = new ArrayList<>();
                    Collection<String> subjects = new ArrayList<>();
                    try {
                        while (aResult.hasNext()) {
                            BindingSet bs = aResult.next();
                            subjects.add(bs.getBinding("subclase").getValue().toString());
                        }
                    } finally {
                        aResult.close();
                    }
                    System.out.println("cantidad de tuplas :" + subjects.size() + "\n");
                    for (String col : subjects) {
                        System.out.println(col + "\n");
                    }
                    System.out.println("cantidad de tuplas :\n" + subjects);
                    // --------------------------------------------
                    publish("Procesando metadatos. Aguarde. \n");
                    int cantTuplas = subjects.size();
                    /*
                    while (aResult2.hasNext()) {
                        //fila = aResult2.next();
                        cantTuplas += 1;
                    }*/
                    //ystem.out.println("cantidad de tuplas :" + cantTuplas);
                    //wait.setearProgressBar(cantTuplas);
                    //
                    wait.setearProgressBar(cantTuplas);
                    int intProgressBar = 0;
                    for (String col : subjects) {
                        System.out.println(col + "\n");
                        final String aValue = col;
                        //final String aValue = fila.getValue("subclase").stringValue();
                        final Metadato m1 = new Metadato(aValue);
                        final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                        if (!rotulo.isEmpty()) {
                            final boolean obligatorio = obligatorioMetadato(m1.getTipo().trim().toLowerCase());
                            final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                            m1.setObligatorio(obligatorio);
                            m1.setRepite(repite);
                            m1.setRotulo(rotulo);
                            listaMetadados.addElement(m1);
                        }
                        intProgressBar += 1;
                        wait.incrementar(intProgressBar);
                    }

                    /*
                    while (aResult.hasNext()) {
                        fila = aResult.next();
                        final String aValue = fila.getValue("subclase").stringValue();
                        final Metadato m1 = new Metadato(aValue);
                        final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                        if (!rotulo.isEmpty()) {
                            final boolean obligatorio = obligatorioMetadato(m1.getTipo().trim().toLowerCase());
                            final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                            m1.setObligatorio(obligatorio);
                            m1.setRepite(repite);
                            m1.setRotulo(rotulo);
                            listaMetadados.addElement(m1);
                        }
                        intProgressBar += 1;
                        wait.incrementar(intProgressBar);
                    }
                     */
                    publish("Proceso de extracción de metadatos terminada.\n");
                    aResult.close();
                    //aResult2.close();
                } catch (Exception ex) {
                    Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                //return listaMetadados;
                wait.close();
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }
        };

        // ordenamos
        mySwingWorker.execute();
        wait.makeWait("Procesando metadatos. Aguarde", evt);
        this.ordenarMetadatos();

        //return mySwingWorker.get();
    }

    public void getMetadatos_v6(JTextArea ta, ActionEvent evt) {
        DialogWaitControl wait = new DialogWaitControl(50);
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
                    publish("Número de metadatos encontrados:" + subjects.size() + ".\n");
                    // --------------------------------------------------------------------
                    publish("Procesando metadatos. Aguarde. \n");
                    wait.setearProgressBar(subjects.size());
                    int intProgressBar = 0;
                    for (String col : subjects) {
                        final String aValue = col;
                        final Metadato m1 = new Metadato(aValue);
                        final String rotulo = rotuloMetadato(m1.getTipo().trim().toLowerCase());
                        if (!rotulo.isEmpty()) {
                            final boolean obligatorio = obligatorioMetadato(m1.getTipo().trim().toLowerCase());
                            final boolean repite = repiteMetadato(m1.getTipo().trim().toLowerCase());
                            m1.setObligatorio(obligatorio);
                            m1.setRepite(repite);
                            m1.setRotulo(rotulo);
                            listaMetadados.addElement(m1);
                        }
                        intProgressBar += 1;
                        wait.incrementar(intProgressBar);
                    }
                    publish("Proceso de extracción de metadatos terminada.\n");
                    aResult.close();
                } catch (Exception ex) {
                    Logger.getLogger(StardogControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                wait.close();
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                ta.append(chunks.get(0));
            }
        };
        mySwingWorker.execute();
        wait.makeWait("Procesando metadatos. Aguarde", evt);
    }

}
