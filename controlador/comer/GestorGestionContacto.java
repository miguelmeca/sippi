/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;
import java.util.ArrayList;
import java.util.Iterator;
import modelo.ContactoResponsable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.Tupla;
import java.util.List;
import java.util.HashSet;
import util.NTupla;
import modelo.*;

/**
 *
 * @author Ana
 */
public class GestorGestionContacto {

private HashSet<Telefono> telefonos;

     public ContactoResponsable crear(String nombre, String apellido, String cargo, String cuil, String email, List telefono )
    {
        ContactoResponsable nuevo = new ContactoResponsable();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setCargo(cargo);
        nuevo.setCuil(cuil);
        nuevo.setEmail(email);
        //nuevo.setTelefonos(telefono);


         SessionFactory sf = HibernateUtil.getSessionFactory();
                    Session sesion;
                    try {
                    sesion = HibernateUtil.getSession();
                    try{
                    HibernateUtil.beginTransaction();

                            sesion.save(nuevo);

                        HibernateUtil.commitTransaction();
                    }catch(Exception e) {
                        System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                        HibernateUtil.rollbackTransaction();
                        return null;
                }
            } catch (Exception ex)
            {
                System.out.println("No se pudo abrir la sesion");
            }

        return nuevo;
    }

    public ArrayList<Tupla> mostrarNombreContacto()
    {
           ArrayList<Tupla> lista = new ArrayList<Tupla>();

           SessionFactory sf = HibernateUtil.getSessionFactory();
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Iterator iter = sesion.createQuery("from TipoCapacitacion tc order by tc.nombre").iterate();
                while (iter.hasNext())
                {
                   ContactoResponsable cr = (ContactoResponsable)iter.next();
                   Tupla t = new Tupla();
                   t.setId(cr.getId());
                   t.setNombre(cr.getNombre());
                   lista.add(t);
                }

               }catch(Exception ex)
           {
                System.out.println("No se pudo abrir la sesion");
           }

           return lista;
    }

    public void telefono(HashSet<NTupla> listaTel) {
            HashSet<Telefono> lista = new HashSet();
            // Convierto el NTupla en Telefono
            Iterator it = listaTel.iterator();
            while (it.hasNext())
            {
                NTupla nt = (NTupla)it.next();
                Telefono tel = new Telefono();
                tel.setNumero(nt.getNombre());

                TipoTelefono ttel = new TipoTelefono();
                Tupla aux = (Tupla)nt.getData();
                ttel.setId(aux.getId());
                ttel.setNombre(aux.getNombre());
                tel.setTipo(ttel);
                lista.add(tel);

            }

            this.telefonos = lista;
    }

    public void seleccionTipoTelefono() {
        // DEPRECATED - NO SIRVE DE NADA... ESTA INFO VA EN TELEFONO()
    }

    public ArrayList<Tupla> mostrarTiposTelefono() {
        ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
        Tupla tupla = null;
        try{
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesion = sf.openSession();
            Iterator iter = sesion.createQuery("from TipoTelefono q order by q.nombre").iterate();
            while ( iter.hasNext() ) {
                TipoTelefono tipo = (TipoTelefono) iter.next();
                tupla = new Tupla(tipo.getId(),tipo.getNombre());
                tuplas.add(tupla);
            }
        }catch(Exception e)
        {
            System.out.println("ERROR:"+e.getMessage()+"|");
            e.printStackTrace();
        }
        return tuplas;
        }


}
