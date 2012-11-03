/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
            HibernateUtil.beginTransaction();
            List<User> listado = HibernateUtil.getSession().createQuery("from User u WHERE u.estado='"+User.ESTADO_ALTA+"' order by u.usuario").list();
            HibernateUtil.commitTransaction();
            
            for (int i = 0; i < listado.size(); i++) {
                User u = listado.get(i);
                NTupla nt = new NTupla(u.getId());
                    nt.setNombre(u.getUsuario());
                    String[] datos = new String[4];
                        datos[0] = u.getUrlFoto();
                    nt.setData(datos);
                lista.add(nt);
            }   
        } 
        catch (Exception ex){
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","<HTML>No se pudo establecer conexión con el <b>Servidor de Datos</b><br><i>¿Tiene acceso a la red?</i>");
        }
        
        return lista;        
    }
    
    public void login(int idUser, String pass)
    {
        String encriptedPassword = HashUtil.encriptarString(HashUtil.ENCRYPT_SHA256,pass);
        
        try 
        {
            HibernateUtil.beginTransaction();
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
            HibernateUtil.commitTransaction();
            
        } 
        catch (Exception ex) 
        {
            pantalla.MostrarMensaje(JOptionPane.ERROR_MESSAGE,"Error","No se pudieron cargar los usuarios del Sistema");
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
        }

        
        
    }
    
    
}
