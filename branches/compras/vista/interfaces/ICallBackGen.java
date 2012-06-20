package vista.interfaces;

/**
 * @author Iuga (version 3) 
 */
public interface ICallBackGen {

    /**
     * Es una interface que define un metodo para actualizar una ventana
     * una vez que se termino un caso de uso de extension o de inclusion.
     * La misma se activa cuando se finaliza el otro UC.
     * @param int id Devuelve un int que puede ser usado para pasar el id del objeto creado o modificado
     * @param String flag Devuelve un String para saber cual es el cu al que se llamó.
     * @param Class devuelve el tipo de clase que fue seleccionada
     */
    public void actualizar(int id, String flag, Class tipo);

}
