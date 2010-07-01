/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.interfaces;

/**
 * @author Iuga
 */
public interface ICallBack {

    /**
     * Es una interface que define un metodo para actualizar una ventana
     * una vez que se termino un caso de uso de extension o de inclusion.
     * La misma se activa cuando se finaliza el otro UC.
     * @param int flag Pasa un int para saber de donde es llamado el metodo (Ponerse de acuerdo)
     * @param boolean exito True: UC finalizado con exito False: Se Cancela el UC
     */
    public void actualizar(int flag, boolean exito);

}
