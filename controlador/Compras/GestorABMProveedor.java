/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import controlador.utiles.gestorGeoLocalicacion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import vista.compras.pantallaRegistrarProveedor;



/**
 *
 * @author Emmanuel
 */
public class GestorABMProveedor {
    private pantallaRegistrarProveedor pantalla;
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

    public GestorABMProveedor(pantallaRegistrarProveedor p) {
        this.pantalla = p;
        rubros = new ArrayList<Tupla>();
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
        Proveedor nuevo = new Proveedor();
        nuevo.setRazonSocial(this.nombreEmpresaCliente);
        nuevo.setCuit(this.cuit);
        nuevo.setEmail(this.email);

        Domicilio d = new Domicilio();
        d.setCalle(this.calle);
        d.setCodigoPostal(this.cp);
        d.setNumero(Integer.parseInt(this.altura));
        d.setPiso(Integer.parseInt(this.piso));
        d.setDepto(this.dpto);
        d.setBarrio(this.barrio);
        nuevo.setDomicilio(d);

        nuevo.setTelefonos(new HashSet<Telefono>(this.telefonos));

        nuevo.setPaginaWeb(this.paginaWeb);

        Session sesion;
        try {
            sesion = HibernateUtil.getSession();
            HibernateUtil.beginTransaction();
            try{
                ArrayList<Rubro> listaRubros = new ArrayList<Rubro>();
                for(Tupla t : rubros){
                    Rubro r = (Rubro)sesion.load(Rubro.class, t.getId());
                    listaRubros.add(r);
                }
                nuevo.setRubros(listaRubros);
                sesion.saveOrUpdate(nuevo.getDomicilio());
                for (Telefono tell : (HashSet<Telefono>)nuevo.getTelefonos())
                {
                    sesion.saveOrUpdate(tell);
                }
                sesion.saveOrUpdate(nuevo);
                HibernateUtil.commitTransaction();
            }catch(Exception e) {
                System.out.println("No se pudo inicia la transaccion\n"+e.getMessage());
                HibernateUtil.rollbackTransaction();
        }
        } catch (Exception ex) { System.out.println("No se pudo abrir la sesion");  }
        return nuevo.getId();
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

}
