/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.interfaces;

/**
 *
 * @author iuga
 */
public interface IAyuda {

    /**
     * Retorna un String con el titulo de la ayuda
     * @return
     */
    public String getTituloAyuda();

    /**
     * Retorna un String con un detalle rápido de la ayuda
     * @return
     */
    public String getResumenAyuda();

    /**
     * Retorna el ID de la documentación relacionada
     * @return
     */
    public int getIdAyuda();


}
