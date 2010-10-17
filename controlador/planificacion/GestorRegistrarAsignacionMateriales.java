/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.planificacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.DetalleConsumible;
import modelo.DetalleMaterial;
import modelo.GrupoDeTrabajo;
import modelo.HerramientaDeEmpresa;
import modelo.Material;
import modelo.PrecioSegunCantidad;
import modelo.RecursoEspecifico;
import modelo.RecursoXProveedor;
import modelo.Tarea;
import util.HibernateUtil;
import util.NTupla;
import util.RecursosUtil;
import util.Tupla;
import vista.planificacion.pantallaRegistrarTarea;

/**
 *
 * @author dell
 */
public class GestorRegistrarAsignacionMateriales {
    private pantallaRegistrarTarea pantalla;
    private int idTarea;

    public GestorRegistrarAsignacionMateriales(pantallaRegistrarTarea pantalla) {
        this.pantalla = pantalla;
        this.idTarea = 0;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public GestorRegistrarAsignacionMateriales(pantallaRegistrarTarea pantalla, int idTarea) {
        this.pantalla = pantalla;
        this.idTarea = idTarea;
    }

    public ArrayList<Tupla> getMaterialesDisponibles(){
        ArrayList<Tupla> materiales = new ArrayList<Tupla>();
        try {
            Iterator it = HibernateUtil.getSession().createQuery("from Material").iterate();
            while(it.hasNext()){
                Tupla t = new Tupla();
                Material m = (Material)it.next();
                t.setId(m.getId());
                t.setNombre(m.getNombre());
                materiales.add(t);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AM-0001");
        }
        return materiales;
    }

    public ArrayList<Tupla> getEspecificacionMaterial(int id) {
        ArrayList<Tupla> esps = new ArrayList<Tupla>();
        try {
            Material m = (Material)HibernateUtil.getSession().load(Material.class, id);
            Iterator it = m.getRecursosEspecificos().iterator();
            while(it.hasNext()){
                Tupla t = new Tupla();
                RecursoEspecifico re = (RecursoEspecifico)it.next();
                t.setId(re.getId());
                t.setNombre(re.getNombre());
                esps.add(t);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AM-0002");
        }
        return esps;
    }

    public ArrayList<NTupla> mostrarRecursosEspecificosXProveedor(int idR, int idRE) {
        ArrayList<NTupla> esps = new ArrayList<NTupla>();
        try {
            RecursoEspecifico re = (RecursoEspecifico)HibernateUtil.getSession().load(RecursoEspecifico.class, idRE);
            Iterator it = re.getRecursosXProveedor().iterator();

            Material m = (Material)HibernateUtil.getSession().load(Material.class, idR);

            while(it.hasNext()){
                NTupla nt = new NTupla();
                RecursoXProveedor rxp = (RecursoXProveedor)it.next();
                nt.setId(rxp.getId());
                nt.setNombre(rxp.getProveedor().getRazonSocial());
                String[] precios= new String[2];
                precios[0]= "<HTML><BODY>";
                Iterator itPSC = rxp.getListaUltimosPrecios().iterator();
                while(itPSC.hasNext()){
                    PrecioSegunCantidad psc = (PrecioSegunCantidad)itPSC.next();
                    precios[0]+= psc.getCantidad()+m.getUnidadDeMedida().getAbreviatura()+" <b>x</b> "+psc.getPrecio()+"<br>";;
                    precios[1]=psc.getFechaVigencia().toString();
                }
                nt.setData(precios);
                esps.add(nt);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AM-0003");
        }
        return esps;
    }

    public String getNombreRecurso(int idR, int idRE) {
        String nombre="";
        try {
            Material m = (Material) HibernateUtil.getSession().load(Material.class, idR);
            nombre=m.getNombre();
            RecursoEspecifico re = (RecursoEspecifico)HibernateUtil.getSession().load(RecursoEspecifico.class, idRE);
            nombre+=(" "+re.getNombre());
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AM-0004");
        }
        return nombre;
    }

    public String getSubtotal(int idRXP, int cantidad) {
        String subtotal="";
        try {
            RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
            Iterator it = rxp.getListaUltimosPrecios().iterator();
            double precio=0;
            while(it.hasNext()){
                PrecioSegunCantidad psc = (PrecioSegunCantidad)it.next();
                if(cantidad >= psc.getCantidad())
                    precio=psc.getPrecio();
            }
            double sub = precio*cantidad;
            subtotal = String.valueOf(sub);
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AM-0005");
        }
        return subtotal;
    }

    public void agregarMaterial(int idRXP, int cantidad, String desc) {
        try {
            HibernateUtil.beginTransaction();
            DetalleMaterial dm = new DetalleMaterial();
            RecursoXProveedor rxp = (RecursoXProveedor) HibernateUtil.getSession().load(RecursoXProveedor.class, idRXP);
            dm.setMaterial(rxp);
            dm.setCantidad(cantidad);
            dm.setDescripcion(desc);
            Tarea t = (Tarea)HibernateUtil.getSession().load(Tarea.class, this.idTarea);
            t.getDetallesMaterial().add(dm);
            HibernateUtil.getSession().save(dm);
            HibernateUtil.getSession().saveOrUpdate(t);
            HibernateUtil.commitTransaction();
            pantalla.actualizar(dm.getId(), "...", true);

        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            this.pantalla.MostrarMensaje("AM-0006");
            this.pantalla.actualizar(-1, "NO IMPLEMENTADO AUN", false);
        }
    }

    public ArrayList<NTupla> getMaterialesAUtilizar() {
        ArrayList<NTupla> mau = new ArrayList<NTupla>();
        try {
            Tarea t = (Tarea) HibernateUtil.getSession().load(Tarea.class, idTarea);
            Iterator<DetalleMaterial> it = t.getDetallesMaterial().iterator();
            DetalleMaterial dm = null;
            NTupla nt = new NTupla();
            while(it.hasNext()){
                dm = it.next();
                nt.setId(dm.getId());
                RecursoXProveedor rxp = dm.getMaterial();
                RecursoEspecifico re = RecursosUtil.getRecursoEspecifico(rxp);
                Material m = (Material)RecursosUtil.getMaterial(re);
                nt.setNombre(m.getNombre());
                Object[] o = new Object[2];
                o[0]= re.getNombre();
                o[1]= dm.getCantidad();
                nt.setData(o);
                mau.add(nt);
            }
        } catch (Exception ex) {
            pantalla.MostrarMensaje("AM-0007");
        }
        return mau;
    }

    public void crearTareaPrueba() {
        try {
            Tarea t = new Tarea();
            t.setDescripcion("MUY DIFICIL");
            //t.setDetallesConsumible(new ArrayList<DetalleConsumible>());

            List<DetalleMaterial> materiales = new ArrayList<DetalleMaterial>();
            RecursoXProveedor rxp = (RecursoXProveedor)HibernateUtil.getSession().load(RecursoXProveedor.class, 1);
            DetalleMaterial dm = new DetalleMaterial();
            dm.setCantidad(12);
            dm.setDescripcion("PROBANDO");
            dm.setMaterial(rxp);
            materiales.add(dm);
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(dm);

            t.setDetallesMaterial(materiales);
            GrupoDeTrabajo gt = new GrupoDeTrabajo();
            HibernateUtil.getSession().save(gt);
            ArrayList<GrupoDeTrabajo> gts = new ArrayList<GrupoDeTrabajo>();
            t.setGrupos(gts);
            ArrayList<HerramientaDeEmpresa> herramientras = new ArrayList<HerramientaDeEmpresa>();
            t.setHerramientas(herramientras);
            t.setUbicacion("ALGUN LUGAR DE PENSILVANIA");
            HibernateUtil.getSession().saveOrUpdate(t);
            HibernateUtil.commitTransaction();
            this.idTarea=t.getId();
        } catch (Exception ex) {
            this.pantalla.MostrarMensaje("AM-0008");
            HibernateUtil.rollbackTransaction();
        }
    }

    public boolean quitarMaterial(int idDM) {
        boolean respuesta=true;
        try {
            HibernateUtil.beginTransaction();
            Tarea t = (Tarea) HibernateUtil.getSession().load(Tarea.class, idTarea);
            DetalleMaterial dm = (DetalleMaterial) HibernateUtil.getSession().load(DetalleMaterial.class, idDM);
            t.getDetallesMaterial().remove(dm);
            HibernateUtil.getSession().delete(dm);
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            respuesta=false;
            pantalla.MostrarMensaje("AM-0007");
        }
        return respuesta;
    }
}
