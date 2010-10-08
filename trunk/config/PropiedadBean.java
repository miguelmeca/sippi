package config;

/**
 * Descripción: Bean para las propiedades
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class PropiedadBean {

    private int id;
    private String key;
    private String value;

    public PropiedadBean() {
    }

    public PropiedadBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
