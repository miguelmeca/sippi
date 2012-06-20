package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class EstadoIndumentaria extends EstadoAbstracto {

   	public boolean esAlta()
        {
            return false;
	}

	public boolean esBaja()
        {
            return false;
	}

        public void darBaja(Indumentaria id)
        {
//            tc.setHib_flag_estado("modelo.EstadoLugarCapacitacionBaja");
//            tc.setEstado(new EstadoLugarCapacitacionBaja());
        }

        public void darAlta(Indumentaria id)
        {
//            tc.setHib_flag_estado("modelo.EstadoLugarCapacitacionAlta");
//            tc.setEstado(new EstadoLugarCapacitacionAlta());
        }

}
