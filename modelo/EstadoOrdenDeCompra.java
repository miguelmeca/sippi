package modelo;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

class EstadoOrdenDeCompra extends EstadoAbstracto {
	public boolean esAlta()
        {
            return false;
	}

	public boolean esBaja()
        {
            return false;
	}

        public void darBaja(OrdenDeCompra oc)
        {
//            tc.setHib_flag_estado("modelo.EstadoLugarCapacitacionBaja");
//            tc.setEstado(new EstadoLugarCapacitacionBaja());
        }

        public void darAlta(OrdenDeCompra oc)
        {
//            tc.setHib_flag_estado("modelo.EstadoLugarCapacitacionAlta");
//            tc.setEstado(new EstadoLugarCapacitacionAlta());
        }
}
