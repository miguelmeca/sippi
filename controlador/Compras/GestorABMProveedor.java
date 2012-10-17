/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Barrio;
import modelo.Domicilio;
import modelo.Localidad;
import modelo.Pais;
import modelo.Proveedor;
import modelo.Provincia;
import modelo.Rubro;
import modelo.Telefono;
import modelo.TipoTelefono;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Tupla;
import vista.compras.ABMProveedor;



/**
 *
 * @author Emmanuel
 */
public class GestorABMProveedor {
    private ABMProveedor pantalla;
    private ArrayList<Telefono> telefonos;
    private String nombreEmpresaCliente;
    private String cuit;
    private String calle;
    private String altura;
    private String piso;
    private String dpto;
    private String cp;
    private String email;
    private Barrio barrio;
    private Localidad localidad;
    private Provincia provincia;
    private Pais pais;
    private String paginaWeb;
    private ArrayList<Tupla> rubros;
//    private Proveedor proveedor;
    private int idProveedor;

    public GestorABMProveedor(ABMProveedor p) {
        this.pantalla = p;
        rubros = new ArrayList<Tupla>();
        this.idProveedor =-1;
    }
    
    public GestorABMProveedor(ABMProveedor p, int idProveedor) {
        this.pantalla = p;
        rubros = new ArrayList<Tupla>();
        this.idProveedor = idProveedor;
    }

    public ArrayList<Tupla> mostrarNombrePaises() {
        gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
        return ggl.getPaises();
    }

    public ArrayList<Tupla> mostrarProvincias(Tupla pais) {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getProvincias(pais.getId());
    }

    public ArrayList<Tupla> mostrarLocalidades(Tupla prov) {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getLocalidades(prov.getId());
    }

    public ArrayList<Tupla> mostrarBarrios(Tupla loc) {
           gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
           return ggl.getBarrios(loc.getId());
    }

    public int agregarTelefono(Tupla tipo, String numero) {
        if(this.telefonos == null){
            this.telefonos = new ArrayList<Telefono>();
        }
        Telefono tel = new Telefono();
        tel.setNumero(numero);
        TipoTelefono ttel = new TipoTelefono();
        ttel.setId(tipo.getId());
        ttel.setNombre(tipo.getNombre());
        tel.setTipo(ttel);
        this.telefonos.add(tel);
        return this.telefonos.indexOf(tel);
    }

    public void borrarTelefono(int id) {
        Telefono telefonoABorrar = this.telefonos.get(id);
        this.telefonos.remove(id);
    }

    public void nombreEmpresa(String nombre) {
        this.nombreEmpresaCliente = nombre;
    }

    public void CUIT(String cuit) {
        this.cuit = cuit;
    }

    public void datosDomicilio(String calle,String altura,String piso,String dpto,String cp) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.dpto = dpto;
        this.cp = cp;
    }

    public void EMail(String email) {
        this.email = email;
    }

    public void seleccionBarrio(int id) {
        gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
        this.barrio = ggl.getBarrio(id);
    }

    public void seleccionLocalidad(int id) {
        gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
        this.localidad = ggl.getLocalidad(id);
    }

    public void seleccionProvincia(int id) {
        gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
        this.provincia = ggl.getProvincia(id);
    }

    public void seleccionPais(int id) {
        gestorGeoLocalicacion ggl = new gestorGeoLocalicacion();
        this.pais = ggl.getPais(id);
    }

    public int confirmacionRegistro() {
        //Si es nuevo, lo creo
        Proveedor proveedor = null;
        if(this.idProveedor != -1)
        {
            try
            {
                HibernateUtil.beginTransaction();
                
                proveedor = (Proveedor) HibernateUtil.getSession().load(Proveedor.class, this.idProveedor);
                
                HibernateUtil.commitTransaction();
            }catch(Exception ex)
            {
                HibernateUtil.rollbackTransaction();
            }
        }
        else
        {
            proveedor = new Proveedor();
        }
        
        proveedor.setRazonSocial(this.nombreEmpresaCliente);
        proveedor.setCuit(this.cuit);
        proveedor.setEmail(this.email);

        Domicilio d = null;
        if(proveedor.getDomicilio() == null)
            proveedor.setDomicilio(new Domicilio());
        d = proveedor.getDomicilio();
        d.setCalle(this.calle);
        d.setCodigoPostal(this.cp);
        d.setNumero(Integer.parseInt(this.altura));
        d.setPiso(Integer.parseInt(this.piso));
        d.setDepto(this.dpto);
        d.setBarrio(this.barrio);
        
        proveedor.getRubros().clear();

        proveedor.setPaginaWeb(this.paginaWeb);

        if(proveedor.getTelefonos() == null)
            proveedor.setTelefonos(new HashSet<Telefono>());
        
        try {
            HibernateUtil.beginTransaction();
            try{
                for(Tupla t : rubros){
                    Rubro r = (Rubro)HibernateUtil.getSession().load(Rubro.class, t.getId());
                    proveedor.getRubros().add(r);
                }
                HibernateUtil.getSession().saveOrUpdate(proveedor.getDomicilio());
                
                // Teléfonos!
                for (Telefono tell : this.telefonos)
                {
                    if(tell.getId() == 0){
                        proveedor.getTelefonos().add(tell);
                        HibernateUtil.getSession().save(tell);
                    }
                }
                ArrayList<Telefono> telefonosBorrados = new ArrayList<Telefono>();
                Iterator<Telefono> itTelefono = proveedor.getTelefonos().iterator();
                boolean borrado = false;
                while(itTelefono.hasNext())
                {
                    Telefono tel = itTelefono.next();
                    Iterator<Telefono> it = this.telefonos.iterator();
                    borrado = false;
                    while(it.hasNext())
                    {
                        Telefono aux = it.next();
                        if(aux.getId() == tel.getId())
                        {
                            borrado = true;
                            break;
                        }
                    }
                    if(!borrado)
                    {
                        telefonosBorrados.add(tel);
                    }
                }
                Iterator<Telefono> itBorrados = telefonosBorrados.iterator();
                while(itBorrados.hasNext())
                {
                    Telefono telBorrado = itBorrados.next();
                    proveedor.getTelefonos().remove(telBorrado);
                    HibernateUtil.getSession().delete(telBorrado);
                }
                
                HibernateUtil.getSession().saveOrUpdate(proveedor);
                HibernateUtil.commitTransaction();
            }catch(Exception e) {
                System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                HibernateUtil.rollbackTransaction();
        }
        } catch (Exception ex) { System.out.println("No se pudo abrir la sesion");  }
        return proveedor.getId();
    }

    public void paginaWeb(String text) {
        this.paginaWeb = text;
    }

    public ArrayList<Tupla> mostrarTiposTelefono() {
        ArrayList<Tupla> tuplas = new ArrayList<Tupla>();
        Tupla tupla = null;
        try{
            Session sesion = HibernateUtil.getSession();
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

    public void agregarRubro(Tupla t) {
        this.rubros.add(t);
    }

    public void quitarRubro(Tupla t) {
        this.rubros.remove(t);
    }

    public boolean validarExistenciaCUIT(String cuit, int idProveedor) {
        boolean respuesta = true;
        try {
            Proveedor pr = (Proveedor) HibernateUtil.getSession().createQuery("FROM Proveedor WHERE cuit =:cuitP").setParameter("cuitP", cuit).uniqueResult();
            if(pr == null)
                respuesta = false;
            else
            {
                if(pr.getId() == idProveedor)
                {
                    respuesta = false;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GestorABMProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public Proveedor getProveedor() {
        Proveedor proveedor = null;
        try
        {
            HibernateUtil.beginTransaction();

            proveedor = (Proveedor) HibernateUtil.getSession().load(Proveedor.class, this.idProveedor);

            HibernateUtil.commitTransaction();
        }catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return proveedor;
    }

    public boolean darDeBajaProveedor() {
        Proveedor proveedor = null;
        boolean dadoDeBaja = false;
        try
        {
            HibernateUtil.beginTransaction();

            proveedor = (Proveedor) HibernateUtil.getSession().load(Proveedor.class, this.idProveedor);
            proveedor.setEstadoBaja();
            HibernateUtil.getSession().update(proveedor);

            HibernateUtil.commitTransaction();
            dadoDeBaja = true;
        }catch(Exception ex)
        {
            HibernateUtil.rollbackTransaction();
        }
        return dadoDeBaja;
    }
}
