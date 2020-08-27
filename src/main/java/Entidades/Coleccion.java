/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import org.swordapp.client.SWORDCollection;

/**
 *
 * @author Pogliani, Germán
 */
public class Coleccion {

    private SWORDCollection coleccion;

    public SWORDCollection getColeccion() {
        return coleccion;
    }

    public void setColeccion(SWORDCollection coleccion) {
        this.coleccion = coleccion;
    }

    @Override
    public String toString() {
        return coleccion.getTitle();
    }

}
