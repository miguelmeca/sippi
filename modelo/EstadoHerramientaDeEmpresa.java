/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class EstadoHerramientaDeEmpresa extends EstadoAbstracto {
    
    	public boolean esDisponible()
        {
            return false;
	}

	public boolean esBaja()
        {
            return false;
	}

        public void darBaja(HerramientaDeEmpresa he)
        {
//            tc.setHib_flag_estado("modelo.EstadoLugarCapacitacionBaja");
//            tc.setEstado(new EstadoLugarCapacitacionBaja());
        }

        public void darAlta(HerramientaDeEmpresa he)
        {
//            tc.setHib_flag_estado("modelo.EstadoLugarCapacitacionAlta");
//            tc.setEstado(new EstadoLugarCapacitacionAlta());
        }

}
