package modelo;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : EstadoPedidoObraSolicitado.java
//  @ Date : 15/06/2010
//  @ Author : 
//
//




public class EstadoPedidoObraSolicitado extends EstadoPedidoObra {

        public EstadoPedidoObraSolicitado()
        {
            super();
            this.setNombre("Solicitado");
	}
	
	public boolean esSolicitado()
        {
            return true;
	}
}
