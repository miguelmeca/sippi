package controlador.comer;

//

import java.util.Date;
import modelo.EmpresaCliente;
import modelo.Planta;
import vista.comer.pantallaRegistrarPedido;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Proyecto2010_Requerimientos-iuga
//  @ File Name : GestorRegistrarPedido.java
//  @ Date : 14/06/2010
//  @ Author : Iuga
//
//




public class GestorRegistrarPedido {

        private pantallaRegistrarPedido pantalla;

        private String nombre;
        private String descripcion;
        private EmpresaCliente empresa;
	private Planta planta;
	private Date fechaInicio;
	private Date fechaFin;
	private Object montoMaximo;
	private Object fechaLEP;
	private Object fechaLVP;
	private Object pliegoObra;
	private Object planosObra;

        public GestorRegistrarPedido(pantallaRegistrarPedido pantalla)
        {
            this.pantalla = pantalla;
        }
        
	public void registrarPedido()
        {
            
	}

        /**
         * Tomo el nombre  de la obra
         * @param nombre
         * @param descripcion
         */
	public void nombreObra(String nombre)
        {
            this.nombre = nombre;
	}

        /**
         * Tomo la descripcion de la obra
         * @param descripcion
         */
        public void descripcionObra(String descripcion)
        {
            this.descripcion = descripcion;
        }

        /**
         * Busco los nombres de las empresas cliente
         */
	public void mostrarEmpresasCliente() {
	
	}
	
	public void seleccionEmpresaCliente() {
	
	}
	
	public void buscarPlantas() {
	
	}
	
	public void mostrarPlantasEmpresaSeleccionada() {
	
	}
	
	public void seleccionPlanta() {
	
	}
	
	public void fechaInicioYFin() {
	
	}
	
	public void montoMaximo() {
	
	}
	
	public void fechaLEP() {
	
	}
	
	public void fechaLVP() {
	
	}
	
	public void pliegoObra() {
	
	}
	
	public void planosObra() {
	
	}
	
	public void confirmacionRegistro() {
	
	}
	
	public void crearPedidoObra() {
	
	}
	
	public void generarNumeroPedido() {
	
	}
	
	public void buscarUltimoNumeroPedidoObra() {
	
	}
	
	public void finCU() {
	
	}
}
