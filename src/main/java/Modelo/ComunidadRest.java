/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
    public String toString(){
        return nombre;
    }
}
