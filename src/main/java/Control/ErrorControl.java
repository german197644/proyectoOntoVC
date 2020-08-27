/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.Component;
import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pogliani, German
 */
public final class ErrorControl {

    // unica instancia.
    private static ErrorControl instancia = null;
    //lista de errores.
    private final HashMap<String, String> errores;

    //mensaje de error
    private String msj = null;

    protected ErrorControl() {
        this.errores = new HashMap<>();
        errores.put("001", "001: Error, operación de depósito cancelada.");
        errores.put("002", "002: Error inesperado al generar el archivo mets.");
        errores.put("003", "003: Error inesperado al depositar.");
        errores.put("004", "004: Error inesperado al obtener las comunidades.");
        errores.put("005", "005: Error subiendo archivos.");
        errores.put("006", "006: Error al conectar con el servidor de Stardog.");
        errores.put("007", "007: Error inesperado al obtener los tipos SNRD.");
        errores.put("008", "008: Error al intentar filtrar metatadatos.\n Debe seleccionar un tipo de Obj. de Aprendizaje");
        errores.put("009", "009: Error al generar los componentes de captura.");
        errores.put("010", "010: Error inesperado, consulte al desarrollador.");
        errores.put("011", "011: Error inesperado al guardar.");
        errores.put("012", "012: Error al obtener los datos de conexión.");
        errores.put("013", "013: Error al intentar abrir el archivo de propiedades.");
        errores.put("014", "014: Error al generar el archivo mets.");
        errores.put("015", "015: Error al intentar validar.");
        errores.put("016", "016: Error quitando un ficehro.");
        errores.put("017", "017: Archivo inexistente.");
        errores.put("018", "018: .");

        errores.put("019", "019: .");
        errores.put("020", "020: .");        
    }

    /**
     * Devuelve la instancia única de la clase.
     *
     * @return
     */
    public static ErrorControl getInstancia() {
        try {
            if (instancia == null) {
                instancia = new ErrorControl();
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
        return instancia;
    }

    /**
     * Muestra el mensaje de error cuando este se produce.
     *
     * @param comp componente sobre el que se muestra.
     * @param error mensaje de error.
     * @param tipo tipo de icono.
     */
    public void viewError(Component comp, String error, int tipo) {
        try {
            if (this.msj != null) {
                JOptionPane.showMessageDialog(comp, this.errores.get(error) + "\n"
                        + this.msj, "Informe", tipo);
            } else {
                JOptionPane.showMessageDialog(comp, this.errores.get(error), "Informe", tipo);
            }
            this.msj = null;
        } catch (HeadlessException e) {
            Logger.getLogger(e.getMessage());
        }
    }

    public HashMap<String, String> getErrores() {
        return errores;
    }

    /**
     *
     * @param aValue
     */
    public void setMsj(String aValue) {
        this.msj = aValue;
    }
}
