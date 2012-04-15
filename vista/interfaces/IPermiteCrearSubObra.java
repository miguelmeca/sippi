/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.interfaces;

import util.Tupla;

/**
 *
 * @author Fran
 */


//Deberia haberse hecho con la interfaz ICallBack pero cuando fui a refactorizar
//esto para hacerlo generico para que se pueda usar en planificacion veo q el
//gestor esta directamente llamando a metodos de la pantalla. Asi q aca hago esto
//para no joder trabajo viejo
public interface IPermiteCrearSubObra {
    
    
    public void crearNuevaSubObra(String nombre);
    
    //Este metodo esta aca solamente para poder usar la interfaz "IPermiteCrearSubObra"
    //En realidad el metodo para modificar nombre no se esta usando en
    //ninguna parte del sistema asi q podria volar a la mierda perfectamente
    public void cambiarNombreSubObra(Tupla tp); ;
    
    
}
