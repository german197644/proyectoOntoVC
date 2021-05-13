/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Pogliani, German
 */
public class BitstreamsRest {

    private String nombre = new String();
    private String link = new String();

    public BitstreamsRest() {
    }

    public BitstreamsRest(String name, String link) {
        this.nombre = name;
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

    @Override
    public String toString() {
        return nombre;
    }
    
    

}
