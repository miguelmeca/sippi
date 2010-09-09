package modelo;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class RecursoEspecifico {

    private int id;
    private String nombre;
    private String descipcion;
    private List<RecursoXProveedor> proveedores;

    public RecursoEspecifico() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public List<RecursoXProveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<RecursoXProveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<RecursoXProveedor> getRecursosXProveedor()
    {
        return this.proveedores;
    }

    public RecursoXProveedor getUltimoRecursoXProveedor(int idProv)
    {
        // MANTENGO EL ULTIMO
        RecursoXProveedor ultimo = null;
        Date ultimaFecha = null;

        if(this.id!=0 && proveedores!=null)
        {
            // EL OBJETO ESTA CARGADO
            Iterator<RecursoXProveedor> it = getProveedores().iterator();

            if(getProveedores().size()==0)
            {
                return null;
            }

            while (it.hasNext())
            {
                RecursoXProveedor rxp = it.next();

                if(rxp.getProveedor().getId()==idProv)
                {
                    if(ultimo==null)
                    {
                        if(rxp.getProveedor().getId()==idProv)
                        {
                            ultimo = rxp;
                        }
                    }

                    Iterator<PrecioSegunCantidad> itx = rxp.getListaPrecios().iterator();
                    while (itx.hasNext())
                    {
                        PrecioSegunCantidad psc = itx.next();
                        if(ultimaFecha==null)
                        {
                            ultimaFecha = psc.getFecha();
                        }
                        if(psc.getFecha().after(ultimaFecha))
                        {
                            ultimaFecha = psc.getFecha();
                            ultimo = rxp;
                        }
                    }
                }
            }
        }

        return ultimo;
    }

    /**
     * Hace una busqueda y devuelve el recurso
     * USAR CON DISCRECION YA QUE HACE UNA BUSQUEDA GRANDE
     * SI NO LO ENCUENTRA O NO HAY NADA RETORNA NULL !!!
     * @author: Iuga
     * @return
     */
    public Recurso getRecurso()
    {
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                List<Recurso> listaRec = sesion.createQuery("FROM Recurso").list();
                Iterator<Recurso> itRec = listaRec.iterator();
                while (itRec.hasNext())
                {
                   Recurso rec = itRec.next();
                   Iterator<RecursoEspecifico> itRecEsp = rec.getRecursosEspecificos().iterator();
                    while (itRecEsp.hasNext())
                    {
                        RecursoEspecifico recEsp = itRecEsp.next();
                        if(recEsp.getId() == this.id)
                        {
                            return rec;
                        }
                    }
                }
               }catch(Exception ex)
               {
                    return null;
               }
           return null;
    }


}
