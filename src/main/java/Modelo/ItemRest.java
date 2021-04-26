package Modelo;

/**
 *
 * @author Pogliani, German
 */
public class ItemRest {

    private String name; 
    private String link; 

    public ItemRest(String name, String link) {
        this.name = name;
        this.link = link;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return this.name;
    }
 
    public boolean isItem(){
        return true;
    }
    
    public boolean isColeccion(){
        return false;       
    }
    
    public boolean isComunidad(){
        return false;
    }
    
}
