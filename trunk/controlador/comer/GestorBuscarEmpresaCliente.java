/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import modelo.EmpresaCliente;
import modelo.Planta;
import org.hibernate.Session;
import util.HibernateUtil;
import util.NTupla;
import vista.comer.pantallaBuscarEmpresaCliente;

/**
 *
 * @author Emmanuel
 */
public class GestorBuscarEmpresaCliente {
    private pantallaBuscarEmpresaCliente pantalla;

    public GestorBuscarEmpresaCliente(pantallaBuscarEmpresaCliente p){
        this.pantalla = p;
    }

    /***
 *  Metodo que permite buscar los datos de los pedidos del sistema. Se muestran los datos
 *  en el siguiente orden:
 *  - Id Empresa
 *  - Razón Social Empresa
 *  - Cuit Empresa
 *  - E-mail Empresa
 *  - Estado
 *  - Página Web
 *  - Plantas
 * @return Lista de EmpresasClientes ARRAYLIST
 */
    public ArrayList<NTupla> getEmpresasCliente(){
        ArrayList<NTupla> empresas = new ArrayList<NTupla>();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Iterator it = sesion.createQuery("from EmpresaCliente").iterate();
        EmpresaCliente ec;
        while(it.hasNext()){
            ArrayList<String> datos = new ArrayList<String>();
            ec = (EmpresaCliente)it.next();
            NTupla e = new NTupla();
            e.setId(ec.getId());
            e.setNombre(ec.getRazonSocial());
            datos.add(ec.getCuit());
            datos.add(ec.getEmail());
            datos.add(ec.getEstado().getNombre());
            datos.add(ec.getPaginaWeb());
            Iterator itPlantas = ec.getPlantas().iterator();
            Planta p = null;
            String plantas = "";
            while(itPlantas.hasNext()){
                if(plantas.equals(""))
                    plantas+=", ";
                p = (Planta)itPlantas.next();
                plantas+=p.getRazonSocial();
            }
            datos.add(plantas);
            e.setData(datos);
            empresas.add(e);
        }
        return empresas;
    }
/**

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
    }**/
}
