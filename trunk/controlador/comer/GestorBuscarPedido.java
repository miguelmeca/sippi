/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.comer;

import java.math.BigDecimal;
import modelo.EmpresaCliente;
import util.HibernateUtil;
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
