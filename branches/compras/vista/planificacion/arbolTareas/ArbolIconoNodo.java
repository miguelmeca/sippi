/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.planificacion.arbolTareas;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author iuga/fran
 */
public class ArbolIconoNodo extends DefaultMutableTreeNode
{

    private String titulo;
    private ImageIcon icono;  
    private int id;
    private String tipo;

    public ArbolIconoNodo(int id, String tipo, String titulo, String urlIcono) 
    {
        this.id=id;
        this.titulo = titulo;
        setIcono(urlIcono);
        this.tipo=tipo;
    }

    public void setTitle(String titulo) 
    {
        this.titulo = titulo;
    }

    public void setIcono(String urlIcono) 
    {
        if (urlIcono != null)
	{
             this.icono =  new javax.swing.ImageIcon(getClass().getResource(urlIcono));
        }
    }

    public String getTitulo() 
    {
        return titulo;
    }

    public ImageIcon getIcono() 
    {
        return icono;
    }
   

    @Override
    public String toString() 
    {
        return this.tipo+";"+this.id+";"+getTitulo();
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }
            
    
    
    
}
