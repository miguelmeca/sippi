/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.users;

import modelo.User;
import org.hibernate.Hibernate;
import util.HibernateUtil;
import util.SwingPanel;

/**
 *
 * @author Iuga
 */
public class UserSession {

   private static UserSession instance = null;

   private User usuarioLogeado;
   
   public static UserSession getInstance()
   {
      if(instance == null) {
         instance = new UserSession();
      }
      return instance;
   }

    private UserSession() 
    {
        
    }
    
    public boolean isLogeado()
    {
        if(usuarioLogeado==null)
        {
            return false;
        }
        return true;
    }

    public User getUsuarioLogeado() {
        return usuarioLogeado;
    }
    
    public boolean isAdmin()
    {
        if(usuarioLogeado==null)
        {
            return false;
        }
        else
        {
            return usuarioLogeado.isIsAdmin();
        }
    }

    public void setUsuarioLogeado(User usuarioLogeado) 
    {
        this.usuarioLogeado = usuarioLogeado;
    }

    public void updateUser() 
    {
        this.usuarioLogeado.setListaFavoritos(SwingPanel.getInstance().getListaFavoritos());
        try
        {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(this.usuarioLogeado);
            HibernateUtil.commitTransaction();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
   
   
    
}
