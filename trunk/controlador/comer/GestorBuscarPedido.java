/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import modelo.EmpresaCliente;
import modelo.PedidoObra;
import org.hibernate.Session;
import util.HibernateUtil;
import util.NTupla;
import vista.comer.pantallaBuscarPedido;

/**
 *
 * @author Emmanuel
 */
    public class GestorBuscarPedido {
    private pantallaBuscarPedido pantalla;
    private EmpresaCliente empresaCliente;

    public GestorBuscarPedido(pantallaBuscarPedido aThis) {
        this.pantalla = aThis;
        empresaCliente = null;
    }

/***
 *  Metodo que permite buscar los datos de los pedidos del sistema. Se muestran los datos
 *  en el siguiente orden:
 *  - Id pedido
 *  - Nombre pedido
 *  - Estado
 *  - Datos del contacto del pedido
 *  - Nombre de la empresa cliente
 *  - Nombre de la planta asociada al pedido
 *  - Fecha de Registro
 *  - Fecha de Aceptación
 *  - Fecha de Inicio
 *  - Fecha de Fin
 * @return Lista de Pedidos ARRAYLIST
 */
    public ArrayList<NTupla> getPedidosObra(){
        ArrayList<NTupla> pedidos = new ArrayList<NTupla>();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Iterator it = sesion.createQuery("from PedidoObra p order by p.nombre").iterate();
        PedidoObra po=null;
        while(it.hasNext()){
            ArrayList<String> datos = new ArrayList<String>();
            po = (PedidoObra)it.next();
            NTupla p = new NTupla();
            p.setId(po.getId());
            p.setNombre(po.getNombre());
            datos.add(po.getEstado().getNombre());
//            if(po.getContacto() != null)
//                datos.add(po.getContacto().getApellido()+", "+po.getContacto().getNombre());
//            else
//                datos.add("sin contacto");
            Iterator itEmpresa = sesion.createQuery("from EmpresaCliente").iterate();
            EmpresaCliente ec = null;
            while(itEmpresa.hasNext()){
                ec = (EmpresaCliente)itEmpresa.next();
                if(ec.esMiPlanta(po.getPlanta().getId()))
                    break;
            }
            if(ec !=null)
                datos.add(ec.getRazonSocial());
            datos.add(po.getPlanta().getRazonSocial());
            datos.add(po.getFechaDeRegistro().toString());
            datos.add(po.getFechaInicio().toString());
            datos.add(po.getFechaFin().toString());
            p.setData(datos);
            pedidos.add(p);
        }
        return pedidos;
    }


    public void buscarEmpresaCliente(int idPlanta) {
        try{
            HibernateUtil.beginTransaction();
            BigDecimal idEC = (BigDecimal)HibernateUtil.getSession().createSQLQuery("select ID_EMPRESA from PLANTA where ID_PLANTA="+idPlanta).uniqueResult();
            this.empresaCliente = (EmpresaCliente)HibernateUtil.getSession().load(EmpresaCliente.class,idEC.intValue());
            HibernateUtil.commitTransaction();
        }
        catch(Exception e){
            System.out.println("ERROR:"+e.getMessage()+"|");
            e.printStackTrace();
       }
    }
}
