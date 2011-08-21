/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.User;
import util.HashUtil;
import util.HibernateUtil;
import util.NTupla;
import vista.users.UserLogin;

/**
 *
 * @author Iuga
 */
public class GestorLogin {
    
    private UserLogin pantalla;

    public GestorLogin(UserLogin pantalla) 
    {
        this.pantalla = pantalla;
    }
   
    public ArrayList<NTupla> getActiveUsers()
    {
        ArrayList<NTupla> lista = new ArrayList<NTupla>();
        try 
        {    
            Iterator iter = HibernateUtil.getSession().createQuery("from User u WHERE u.estado='ALTA' order by u.usuario ").iterate();
            while (iter.hasNext())
            {
                User u = (User)iter.next();

                NTupla nt = new NTupla(u.getId());
                    nt.setNombre(u.getUsuario());
                    String[] datos = new String[4];
                        datos[0] = u.getUrlFoto();
                    nt.setData(datos);
                lista.add(nt);
            }
        } 
        catch (Exception ex) 
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","No se pudieron cargar los usuarios del Sistema");
        }
        
        return lista;        
    }
    
    public void login(int idUser, String pass)
    {
        String encriptedPassword = HashUtil.encriptarString(HashUtil.ENCRYPT_SHA256,pass);
        
        try 
        {
            User u = (User)HibernateUtil.getSession().load(User.class,idUser);
            
            if(u.getPassword().equals(encriptedPassword))
            {
                // LOGEADO
                UserSession.getInstance().setUsuarioLogeado(u);
                pantalla.usuarioLogeado();
            }
            else
            {
                pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","La información de usuario o contraseña introducida no es correcta");
            }
            
        } 
        catch (Exception ex) 
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","No se pudieron cargar los usuarios del Sistema");
        }

        
        
    }
    
    
}
