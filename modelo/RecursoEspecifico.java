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

public class RecursoEspecifico implements IComprable {

    public final static String[] tiposDeRecurso = {"Materiales", "Herramientas"};
    
    private int id;
    private String nombre;
    private String descipcion;
    private List<RecursoXProveedor> proveedores;
    private transient Recurso recurso;

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
        if(descipcion==null)
        {
            return "";
        }
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
        if(recurso==null){
           Session sesion;
           try {
                sesion = HibernateUtil.getSession();

                Recurso RE= (Recurso)HibernateUtil.getSession().createQuery("from Recurso RE where :cID in elements(RE.recursos)").setParameter("cID", this).uniqueResult(); 
                recurso=RE;
               }catch(Exception ex)
               {
                    return null;
               }
          // return null;
        }
        
        return recurso;
        
    }
    
    public String getNombreRecurso()
    {
        Recurso r = getRecurso();
        if(r!=null)
        {
            return r.getNombre();
        }
        return "";
    }
    
    public String getTipoRecursoespecifico()
    {
        Recurso r = getRecurso();
        if(r instanceof Material)
        {
            return RecursoEspecifico.tiposDeRecurso[0];
        }
        if(r instanceof Herramienta)
        {
            return RecursoEspecifico.tiposDeRecurso[1];
        }
        return "";
    }


}
