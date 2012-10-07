/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;

import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import modelo.Domicilio;
import modelo.Localidad;
import modelo.Pais;
import modelo.Planta;
import modelo.Provincia;
import modelo.Telefono;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.NTupla;
import vista.comer.pantallaConsultarPlantas;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class GestorConsultarPlanta {

    private pantallaConsultarPlantas pantalla;
    private Planta planta;
    private int id_localidad;
    private int id_provincia;

    public GestorConsultarPlanta(pantallaConsultarPlantas pantalla) {
        this.pantalla = pantalla;
    }

    public GestorConsultarPlanta() {
    }

    public boolean PlantaSeleccionada(int idPlanta)
    {
        // Tengo el id de la planta, la cargo y la muestro
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();

        this.planta = (Planta) sesion.get(Planta.class,idPlanta);

        if(this.planta == null)
        {
            return false;
        }
        return true;
    }

    public String mostrarRazonSocialPlanta()
    {
        return planta.getRazonSocial();
    }

    public ArrayList<NTupla> mostrarTelefonosPlanta()
    {
        ArrayList<NTupla> listaTelefonos = new ArrayList<NTupla>();

        HashSet<Telefono> lista = new HashSet<Telefono>();
        lista.addAll((Set)planta.getTelefonos());
        
        Iterator it = lista.iterator();
        while (it.hasNext())
        {
            Telefono tel = (Telefono)it.next();
            NTupla t = new NTupla();
            t.setId(tel.getId());
            t.setNombre(tel.getNumero());
            t.setData(tel.getTipo().getNombre());
            listaTelefonos.add(t);
        }

        return listaTelefonos;
    }

    public String mostrarDireccionPlanta()
    {
        Domicilio d = planta.getDomicilio();
        return planta.getDomicilio().toString();
    }

   public String mostrarNombreBarrio()
   {
        return planta.getDomicilio().getBarrio().getNombre();
   }

   public String mostrarNombreLocalidad()
   {
       gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
       Localidad l = ggl.getLocalidadDeBarrio(this.planta.getDomicilio().getBarrio().getId());
       this.id_localidad = l.getId();
       return l.getNombre();
   }

   public String mostrarNombreProvincia()
   {
       gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
       Provincia p = ggl.getProvinciaDeLocalidad(id_localidad);
       this.id_provincia = p.getId();
       return p.getNombre();
   }

   public String mostrarNombrePais()
   {
       gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
       Pais p = ggl.getPaisDeProvincia(id_provincia);
       return p.getNombre();
   }

   public String mostrarNombreContacto()
   {
       if(planta.getContacto()==null)
       {
           return "-";
       }
       return planta.getContacto().getApellido()+", "+planta.getContacto().getNombre();
   }

   public String mostrarEmailContacto()
   {
       if(planta.getContacto()==null)
       {
           return "-";
       }
       return planta.getContacto().getEmail();
   }

   public String mostrarCUILContacto()
   {
       if(planta.getContacto()==null)
       {
           return "-";
       }
       return planta.getContacto().getCuil();
   }

   public String mostrarRolContacto()
   {
       if(planta.getContacto()==null || planta.getContacto().getRol() == null)
       {
           return "-";
       }
        return planta.getContacto().getRol().getNombre();
   }

    public ArrayList<NTupla> mostrarTelefonosContacto() {

       ArrayList<NTupla> listaTelefonos = new ArrayList<NTupla>();
       if(planta.getContacto()==null)
       {
           return listaTelefonos;
       }

        Iterator it = planta.getContacto().getTelefonos().iterator();
        while (it.hasNext())
        {
            Telefono tel = (Telefono)it.next();
            NTupla t = new NTupla();
            t.setId(tel.getId());
            t.setNombre(tel.getNumero());
            t.setData(tel.getTipo().getNombre());
            listaTelefonos.add(t);
        }
        return listaTelefonos;
    }
}
