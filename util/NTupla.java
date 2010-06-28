package util;

/**
 * La idea es pasar NVariables en un solo objeto, Por ejemplo:
 * En Si queres pasar los telefonos y su tipo, en object pasas un ArrayList
 * de String[], uno con los telefonos, y otro con el tipo. Es para NO separar
 * el ID de la estructura del dato y de la llamada al método QUE ESTA PESIMO !!
 * En un solo objeto, tenemos el ID y TODOS los datos asociados a ese ID
 * @author iuga
 * @version 1.0
 */
public class NTupla {

    private int id;
    private String nombre;
    private Object data;

    public NTupla(int id)
    {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
