/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.gui;

/**
 *
 * @author Iuga
 */
public interface IFavorito {
   
    // Retorna true si la ventana puede ir al menú favoritos
    public boolean isFavorito();
    
    // Retorna la URL al Icnono a mostrar
    public String getIconoFavorito();
    
}
