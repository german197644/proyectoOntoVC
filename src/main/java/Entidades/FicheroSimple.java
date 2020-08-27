/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Pogliani, German
 */
public class FicheroSimple {

    //private String nombreArchivo = null;
    //private String absolutePath = null;
    //private String relativePath = null;
    private File unFile = null;

    public FicheroSimple() {
    }

    public FicheroSimple(String aFile) throws IOException {
        File f = new File(aFile);
        if (f.exists()) {
            //nombreArchivo = f.getName();
            //relativePath = f.getPath();
            //absolutePath = f.getAbsolutePath();
            unFile = f;
        }
    }

    public FicheroSimple(File aFile) throws IOException {
        if (aFile.exists()) {
            //nombreArchivo = aFile.getName();
            //relativePath = aFile.getPath();
            //absolutePath = aFile.getAbsolutePath();
            unFile = aFile;
        }
    }

    public File getUnFile() {
        return unFile;
    }

    public void setUnFile(File unFile) {
        this.unFile = unFile;
    }

    @Override
    public String toString() {
        return unFile.getName().toString();
    }

}
