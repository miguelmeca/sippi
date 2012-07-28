/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Definir una interfaz común para todos los tipos de objeto que puedan ser 
 * discriminados en un detalle. Esto va a desacoplar el resto del sistema con 
 * el módulo de compras.
 * 
 * 
 * @author Iuga
 */
public interface IDetallable {
    
    /**
     * Retorna el nombre del Item
     * Columna: Nombre y Descripcion
     * @return 
     */
    public String getNombre();
    /**
     * Retorna la cantidad de estos items que se estan agregando al detalle
     * Columna: Cantidad
     * Ej: La cantidad de un item que se compro en una orden de compra
     * @return 
     */
    public float getCantidad();
    /**
     * Unidad en la que se mide el item (Nombre [Abreviatura])
     * Columna: Unidad
     * @return 
     */
    public String getUnidadDeMedida();
    /**
     * Precio unitario al que se compro/Vendio el item
     * Columna: Precio Unitario
     * @return 
     */
    public float getPrecioUnitario();
    /**
     * Metodo que calcula el SubTotal de este Detalle
     * Columna: SubTotal
     * @return 
     */
    public float calcularSubTotal();
    
    
}
