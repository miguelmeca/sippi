/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.interfaces;

/**
 * @author Fran (version 2) Iuga (Version Original)
 */
public interface ICallBack_v3 {

    /**
     * Es una interface que define un metodo para actualizar una ventana
     * una vez que se termino un caso de uso de extension o de inclusion.
     * La misma se activa cuando se finaliza el otro UC.
     * @param int id Devuelve un int que puede ser usado para pasar el id del objeto creado o modificado
     * @param String flag Devuelve un String para saber cual es el cu al que se llamó.
     * @param boolean exito True: UC finalizado con exito False: Se Cancela el UC
     */
    public void actualizar(int id, String flag, boolean exito, Object[] data);

}
