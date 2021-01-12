/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 *
 * @author Pogliani, Germán
 */
public class Metadato {

    private String tipo = null; //Title, SubTitle, Creator, etc   
    private String rotulo = null; //nombre a mostrar en la visualización
    private Component etiqueta = null;//tipo de contenedor: JLabel
    private Component colector = null; //tipo de contenedor: textField, textArea, etc.
    private int orden = 0; //orden del metadato dentro del panel.
    private boolean obligatorio; //indica si el metadato es obligatorio.
    private boolean repite; //indica si el metadato es cargado mas de una vez.
    Properties properties = new Properties();
    
    //private String data = null; //descripcion del mismo
    //private IRI iriMeta = null; //la IRI
    //private boolean selected = false; //para el arbol
    //private String manejador = null; //driver del metadato

    public Component getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Component etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Component getColector() {
        return colector;
    }

    public void setColector(Component colector) {
        this.colector = colector;
    }

    private String extraeMata(String valor) {
        if (!valor.contains("#")) {
            return valor;
        } else {
            return valor.substring(valor.indexOf("#") + 1, valor.length());
        }
    }

    public Metadato() {
    }

    public Metadato(String aTipo) {
        final String atipo = extraeMata(aTipo);
        tipo = atipo.toLowerCase().trim();
        //data = atipo.toLowerCase().trim();
    }

    /**
     *
     * @param aTipo
     * @param aData
     */
    public Metadato(String aTipo, String aData) {
        final String atipo = extraeMata(aTipo);
        tipo = atipo;
        rotulo = atipo;
    }

    public Metadato(String aTipo, String aData, boolean aObligatorio) {
        tipo = aTipo;
        //data = aData;
        obligatorio = aObligatorio;
    }

    @Override
    public String toString() {
        return this.rotulo;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public boolean isRepite() {
        return repite;
    }

    public void setRepite(boolean repite) {
        this.repite = repite;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    /**
     * Valida los campos desde el colector y usando las propiedades.
     *
     * @return
     * @throws Exception
     */
    public String getValidarMetadato() throws Exception {
        String aValue = "";
        //System.out.println("es un :" + this.getTipo());
        if (colector instanceof JTextField) {
            //System.out.println("entre jtextfield");
            JTextField txt = (JTextField) colector;
            aValue = validateTextField(txt);
        } else if (colector instanceof JTextArea) {
            //System.out.println("entre jtextArea");
            JTextArea txt = (JTextArea) colector;
            aValue = validateTextArea(txt);
            // System.out.println("es un jtextarea" );
        } else if (colector instanceof JPanel) {
            //System.out.println("entre jpanel");
            JPanel jp = ((JPanel) colector);
            aValue = validarPanel(jp);
        } else if (colector instanceof JScrollPane) {
            //System.out.println("entre jscrollpane");
            JViewport viewport = ((JScrollPane) colector).getViewport();
            Component[] components = viewport.getComponents();
            Object obj = components[0];
            if (obj instanceof JTextArea) {
                JTextArea txtArea = (JTextArea) obj;
                aValue = validateTextArea(txtArea);
            }
        }

        //System.out.println("salimos ...");

        if (!aValue.equals("")) {
            this.etiqueta.setForeground(Color.red);
        } else {
            this.etiqueta.setForeground(Color.black);
        }

        //System.out.println("salimos al fin ...");
        return aValue;
    }

    private String validarPanel(JPanel jp) throws IOException {
        String aValue = "";
        Component[] components = jp.getComponents();
        //System.out.println("es un jpanel ");
        for (Component component : components) {        
            if (component instanceof JTextField) {
                String jt = component.getName();                
                if (jt != null) {
                    jt = component.getName().trim().toLowerCase();
                    switch (jt) {
                        case "apellido":
                            final JTextField txt = (JTextField) component;
                            aValue += this.validateTextField(txt);
                            break;
                        case "nombre":
                            final JTextField txt2 = (JTextField) component;
                            aValue += this.validateTextField(txt2);
                            break;
                    }
                }
            }
        }
        return aValue;
    }

    private String validateTextField(JTextField txt) throws FileNotFoundException, IOException {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase().trim() + ".validacion");

        if ((cadenaValidacion == null) || cadenaValidacion.equals("default")) {
            if (txt.getText().trim().equals("")) {
                aux = "El metadato " + this.getTipo() + " no debe ser vacio.";
            }
        } else {
            Pattern pat = Pattern.compile(cadenaValidacion);
            Matcher mat = pat.matcher(txt.getText());
            if (!mat.matches()) {
                aux = properties.getProperty(this.tipo.toLowerCase() + ".msg").trim();
            }
        }
        return aux;
    }

    private String validateTextArea(JTextArea txt) throws FileNotFoundException, IOException {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

        if ((cadenaValidacion == null) || cadenaValidacion.equals("default")) {
            if (txt.getText().trim().equals("")) {
                aux = "El metadato " + this.getTipo() + " no debe ser vacio.";
            }
        } else {
            Pattern pat = Pattern.compile(cadenaValidacion);
            Matcher mat = pat.matcher(txt.getText());
            if (!mat.matches()) {
                aux = properties.getProperty(this.tipo.toLowerCase() + ".msg").trim();
            }
        }
        return aux;
    }

    /**
     * Devuelve la validacion de los metadatos.
     *
     * @return
     * @throws java.lang.Exception
     */
    public String getValidarMetadato_viejo() throws Exception {
        String aValue = "";
        String aux = tipo.toLowerCase().trim();

        switch (aux) {
            case "title":
                //aValue = validarTitulo();
                break;
            case "subtitle":
                //aValue = validarTitulo();
                break;
            case "creator":
                //aValue = validarNombre();
                break;
            case "subject":
                //tema del contenido del recurso.
                //aValue = validarXdefecto();
                break;
            case "description":
                //description y abstract lo mismo
                //aValue = validarTextArea();
                break;
            case "abstract":
                //description y abstract lo mismo
                //aValue = validarTextArea();
                break;
            case "publisher":
                aValue = "";
                break;
            case "contributor":
                //aValue = validarNombre();
                break;
            case "identifier":
                //cualquier fecha asociada con el ciclo de vida de un recurso.
                //formato YYYY-MM-DD (1999-06-05).
                //aValue = validarXdefecto();
                break;
            case "source":
                //aValue = validarXdefecto();
                break;

            case "relation":
                //aValue = validarXdefecto();
                break;

            case "coverage":
                //aValue = validarXdefecto();
                break;
            case "rights":
                //aValue = validarTextArea();
                break;
            case "dcmiperiod":
                //formato YYYY-MM-DD (1999-06-05).
                //aValue = validarDcmiperiod();
                break;
            case "publised":
                //aValue = validarPublished();
                break;
            default:
                //se validan que no el componente:: JtextField, JTextArea
                //no sea vacio unicamente.
                //aValue = validarXdefecto();
                break;
        }
        return aValue;
    }

    //por defecto el componente debe ser un JTextField, JTextArea.
    private String validarXdefecto() throws FileNotFoundException, IOException {
        String auxValue = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextField) {
            JTextField txt = (JTextField) colector;
            if (txt.getText().equals("")) {
                final String msj = properties.getProperty(this.tipo.toLowerCase().trim() + ".msg");
                if (msj != null) {
                    auxValue = msj;
                } else {
                    auxValue = "No debe ser vacio el colector: " + this.getTipo();
                }
            }
        } else if (colector instanceof JTextArea) {
            JTextArea txt = (JTextArea) colector;
            if (txt.getText().equals("")) {
                auxValue = "No debe ser vacio el colector: " + this.getTipo();
            }
        }
        return auxValue;
    }

    private String validarDcmiperiod() throws FileNotFoundException, IOException {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextField) {
            JTextField txt = (JTextField) colector;

            final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

            if (cadenaValidacion.equals("default") || (cadenaValidacion == null)) {
                if (txt.getText().trim().equals("")) {
                    aux = "La fecha no debe ser vacio. ";
                } else {
                    Pattern pat = Pattern.compile(cadenaValidacion);
                    Matcher mat = pat.matcher(txt.getText());
                    if (!mat.matches()) {
                        aux = "El " + tipo.toLowerCase() + " intervalo de fecha incorrecta. Ej: 2019/05/01-2020/02/30\n ";
                    }
                }
            }
        }
        return aux;
    }

    private String validarNombre() throws Exception {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextField) {
            JTextField txt = (JTextField) colector;
            //Ej: Gaston Paul      
            final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

            if (cadenaValidacion.equals("default") || (cadenaValidacion == null)) {
                if (txt.getText().trim().equals("")) {
                    aux = "El nombre no debe ser vacio. Ej: Juan Perez.";
                }
            } else {
                Pattern pat = Pattern.compile(cadenaValidacion);
                Matcher mat = pat.matcher(txt.getText());
                if (!mat.matches()) {
                    aux = properties.getProperty(this.tipo.toLowerCase().trim() + ".msg").trim();
                }
            }
        }
        return aux;
    }

    private String validarIdentifier() throws Exception {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextField) {
            JTextField txt = (JTextField) colector;
            //Ej: Gaston Paul      
            final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

            if (cadenaValidacion.equals("default") || (cadenaValidacion == null)) {
                if (txt.getText().trim().equals("")) {
                    aux = "El identificador debe contener información. Ej: Juan Perez.";
                }
            } else {
                Pattern pat = Pattern.compile(cadenaValidacion);
                Matcher mat = pat.matcher(txt.getText());
                if (!mat.matches()) {
                    aux = properties.getProperty(this.tipo.toLowerCase() + ".msg").trim();
                }
            }
        }
        return aux;
    }

    private String validarPublished() throws Exception {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextField) {
            JTextField txt = (JTextField) colector;
            //Ej: Gaston Paul      
            final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

            if (cadenaValidacion.equals("default") || (cadenaValidacion == null)) {
                if (txt.getText().trim().equals("")) {
                    aux = "El editor no debe ser vacio. Ej: Juan Perez.";
                }
            } else {
                Pattern pat = Pattern.compile(cadenaValidacion);
                Matcher mat = pat.matcher(txt.getText());
                if (!mat.matches()) {
                    aux = properties.getProperty(this.tipo.toLowerCase() + ".msg").trim();
                }
            }
        }
        return aux;
    }

    private String validarTitulo() throws FileNotFoundException, IOException {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextField) {
            JTextField txt = (JTextField) colector;
            final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

            if ((cadenaValidacion == null) || cadenaValidacion.equals("default")) {
                if (txt.getText().trim().equals("")) {
                    aux = "El titulo no debe ser vacio. Ej: El señor de los anillos";
                }
            } else {
                Pattern pat = Pattern.compile(cadenaValidacion);
                Matcher mat = pat.matcher(txt.getText());
                if (!mat.matches()) {
                    aux = properties.getProperty(this.tipo.toLowerCase() + ".msg").trim();
                }
            }
        }

        return aux;
    }

    private String validarFecha() {
        String aux = "";
        if (colector instanceof JDateChooser) {
            //obtenemos la fecha del componente.
            JDateChooser fecha = (JDateChooser) colector;
            int anio = fecha.getCalendar().get(Calendar.YEAR);
            int mes = fecha.getCalendar().get(Calendar.MONTH);
            int dia = fecha.getCalendar().get(Calendar.DAY_OF_MONTH);
            //obtenemos la fecha del sistema.    
            Calendar fecha2 = new GregorianCalendar();
            int anio2 = fecha2.get(Calendar.YEAR);
            int mes2 = fecha2.get(Calendar.MONTH);
            int dia2 = fecha2.get(Calendar.DAY_OF_MONTH);
            //La fecha no debe ser superior al dia de la carga.
            //operamos sobre cada elemento que compone la fecha
            //para evitar errores.
            if ((dia <= dia2)
                    && (mes <= mes2)
                    && (anio <= anio2)) {
                aux = "La fecha: " + dia + "-" + mes + "-" + anio + " no es correcto. "
                        + "Tal vez deba ser <= a la fecha actual. \n ";
            }
        }
        if (!aux.equals("")) {
            this.etiqueta.setForeground(Color.red);
        } else {
            this.etiqueta.setForeground(Color.black);
        }

        return aux;
    }

    private String validarTextArea() throws FileNotFoundException, IOException {
        String aux = "";
        //Properties properties = new Properties();
        InputStream propertiesStream = new FileInputStream("src/main/java/propiedades/configValidacion.properties");
        properties.load(propertiesStream);

        if (colector instanceof JTextArea) {
            JTextArea txt = (JTextArea) colector;
            //Ej: El arte de la guerra.
            final String cadenaValidacion = properties.getProperty(this.tipo.toLowerCase() + ".validacion").trim();

            if ((cadenaValidacion == null) || cadenaValidacion.equals("default")) {
                if (txt.getText().trim().equals("")) {
                    aux = "(-) El metadato: " + tipo.toLowerCase() + " no debe ser vacio. \n ";
                }
            } else {
                Pattern pat = Pattern.compile(cadenaValidacion);
                Matcher mat = pat.matcher(txt.getText());
                if (!mat.matches()) {
                    aux = properties.getProperty(this.tipo.toLowerCase() + ".msg").trim();
                }
            }
        }
        if (!aux.equals("")) {
            this.etiqueta.setForeground(Color.red);
        } else {
            this.etiqueta.setForeground(Color.black);
        }
        return aux;
    }

    /**
     *
     * @return Retorna los valores captados por el contenedor de la clase.
     * @throws java.lang.Exception
     */
    public String getContenidoMetadato() 
            throws Exception {
        String aValue = "";        
        if (colector instanceof JTextField) {
            aValue = ((JTextField) colector).getText();
        } else if (colector instanceof JTextArea) {            
            aValue = ((JTextArea) colector).getText();
        } else if (colector instanceof JDateChooser) {
            Calendar fecha = ((JDateChooser) colector).getCalendar();
            int anio = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            aValue = anio + "/" + mes + "/" + dia;
        } else if (colector instanceof JComboBox) {
            Object obj = (((JComboBox) colector).getSelectedItem());
            if (obj instanceof String) {
                aValue = (String) obj;
            }
        } else if (colector instanceof JScrollPane) {           
            JViewport viewport = ((JScrollPane) colector).getViewport();
            Component[] components = viewport.getComponents();
            Object obj = components[0];
            if (obj instanceof JTextArea) {
                aValue = ((JTextArea) obj).getText();
            }
        } else if (colector instanceof JPanel) {
            Component[] components = ((JPanel) colector).getComponents();            
            for (Component component : components) {                
                if (component instanceof JTextField) {                    
                    Object o = component;
                    String jt = ((JTextField) o).getName().trim().toLowerCase();                    
                    switch (jt) {
                        case "apellido":
                            if (aValue.equals("")) {
                                aValue = ((JTextField) component).getText() + ", ";
                            } else {
                                aValue = ((JTextField) component).getText() + ", " + aValue;
                            }
                            break;
                        case "nombre":
                            if (aValue.equals("")) {
                                aValue = ((JTextField) component).getText();
                            } else {
                                aValue += ((JTextField) component).getText();
                            }
                    }
                }
            }
        }
        return aValue;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
