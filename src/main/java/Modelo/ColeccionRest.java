/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.google.gson.JsonElement;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author german, Pogliani
 */
public class ColeccionRest {

    private String nombre;
    private String link;
    private String uuid;

    public ColeccionRest(String nombre, String link, String uuid) {
        this.nombre = nombre;
        this.link = link;
        this.uuid = uuid;
    }

    public ColeccionRest(JsonElement nameColeccion, JsonElement linkColeccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return Retorna el nombre de la coleccion asociada.
     */
    @Override
    public String toString() {
        return nombre;
    }

    public boolean isColeccion() {
        return true;
    }

    public boolean isComunidad() {
        return false;
    }

    public boolean isItem() {
        return false;
    }

    public DefaultMutableTreeNode getComunidad(String url, DefaultMutableTreeNode nodo) {
        return null;
    }

}
