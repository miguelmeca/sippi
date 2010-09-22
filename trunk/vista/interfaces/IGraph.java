/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.interfaces;

import java.util.Date;

/**
 * Interfaz q debe cumplir toda clase q tenga un gráfico de Gantt
 * @author iuga
 */
public interface IGraph {

    /**
     * Agrega una etapa al gráfico
     * @param id
     * @param nombre
     * @param fechaInicio
     * @param FechaFin
     */
    public void addEtapa(int id, String nombre, Date fechaInicio, Date FechaFin);

    /**
     * Repinta y refresca el gráfico
     */
    public void refescarGrafico();

    /**
     * Pone la etapa de idEtapaAntes como predecesora de la etapa idEtapaDespues
     * @param idEtapaAntes
     * @param idEtapaDespues
     */
    public void setEtapaPredecesora(int idEtapaAntes, int idEtapaDespues);

}
