/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.planificacion;

import config.Iconos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import vista.planificacion.arbolTareas.ArbolIconoNodo;

/**
 *
 * @author Fran
 */
public class ArbolTareasGestor 
{
    JTree arbolTareas;
    public ArbolTareasGestor(JTree arbolTareas)
    {this.arbolTareas= arbolTareas;}
   
    
    public ArbolIconoNodo getNodoArbolTareasPorId(JTree arbol,int id)
    {
        ArbolIconoNodo nodoActual=(ArbolIconoNodo)arbol.getModel().getRoot();
        if(id<=0)
        {
            return nodoActual;
        }
        else
        {
            return getNodoArbolTareasPorIdRecursivo(nodoActual, id);
        }
        
    }
    
    private ArbolIconoNodo getNodoArbolTareasPorIdRecursivo(ArbolIconoNodo nodoPadre, int id)
    {
        ArbolIconoNodo nodo=null;
        for (int i = 0; i < nodoPadre.getChildCount(); i++) 
        {
           
           if(((ArbolIconoNodo)nodoPadre.getChildAt(i)).getTipo().equals(ArbolDeTareasTipos.TIPO_TAREA))
           {
               if(((ArbolIconoNodo)nodoPadre.getChildAt(i)).getId()==id)
               { 
                  nodo= ((ArbolIconoNodo)nodoPadre.getChildAt(i));
                  break;
               }
               else
               {
                  nodo= getNodoArbolTareasPorIdRecursivo(((ArbolIconoNodo)nodoPadre.getChildAt(i)), id );
                  if(nodo!=null)
                  {                    
                      break;
                  }
               }  
           }      
        }   
        return nodo;
        
    }
    
    public ArbolIconoNodo obtenerNodoGrupoDeTarea(DefaultTreeModel modelo, ArbolIconoNodo tarea, String tipoGrupo)
    {
       ArbolIconoNodo nodoGrupo=null;
       for (int i = 0; i < tarea.getChildCount(); i++) 
       {
           ArbolIconoNodo nodoGrupoActual=(ArbolIconoNodo)tarea.getChildAt(i);
           if(nodoGrupoActual.getTipo().equals(tipoGrupo))
            {
             nodoGrupo=nodoGrupoActual;
             break;
           }                            
       }
       if(nodoGrupo==null)
       {
           nodoGrupo=crearNodoGrupo(modelo,tarea,tipoGrupo);                           
       }
       return nodoGrupo;
    }
    
    private ArbolIconoNodo crearNodoGrupo(DefaultTreeModel modelo,ArbolIconoNodo tarea, String tipoGrupo)
    {
        ArbolIconoNodo nodoGrupo = new ArbolIconoNodo(tarea.getId(),tipoGrupo,tipoGrupo,ArbolDeTareasTipos.getIcono(tipoGrupo));
            
        String[] grupos=ArbolDeTareasTipos.getGruposRecursos();
       int ordenGrupo=0;
       for (int i = 0; i < grupos.length; i++) {
           if(grupos[i].equals(tipoGrupo))
           {
              ordenGrupo=i;
              break;
           }           
       }
       if(ordenGrupo> tarea.getChildCount())
       {ordenGrupo=tarea.getChildCount();}
        for (int i = 0; i < tarea.getChildCount(); i++) {
            if(((ArbolIconoNodo)tarea.getChildAt(i)).getTipo().equals(ArbolDeTareasTipos.TIPO_TAREA))
            {
                ordenGrupo=i;
                break;
            }
        }
       
       //Si ya exste un nodo en esa posicion, lo inserto antes de esa.
       
       modelo.insertNodeInto(nodoGrupo, tarea, ordenGrupo);
       return nodoGrupo;
    }    
    
    public static TreePath getTreeNodePathDeNodo(TreeNode treeNode) {
    List<Object> nodes = new ArrayList<Object>();
    if (treeNode != null) {
      nodes.add(treeNode);
      treeNode = treeNode.getParent();
      while (treeNode != null) {
        nodes.add(0, treeNode);
        treeNode = treeNode.getParent();
      }
    }  

    return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
  }
    
    public static ArbolIconoNodo getNodoDeTreeNodePath(TreePath path) 
    {
        ArbolIconoNodo nodo=null;
        if(path != null)
          {
            nodo= (ArbolIconoNodo) path.getLastPathComponent();
          }
        return nodo;
    }
    
    
}
