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
    public void setNombre(String nombre);
    /**
     * Descricpión del Item detallable
     * @return 
     */
    public String getDescripcion();
    public void setDescripcion(String nombre);
    /**
     * Retorna la cantidad de estos items que se estan agregando al detalle
     * Columna: Cantidad
     * Ej: La cantidad de un item que se compro en una orden de compra
     * @return 
     */
    public float getCantidad();
    public void setCantidad(float cantidad);
    /**
     * Unidad en la que se mide el item (Nombre [Abreviatura])
     * Columna: Unidad
     * @return 
     */
    public String getUnidadDeMedida();
    public void setUnidadDeMedida(String unidadDeMedida);
    /**
     * Precio unitario al que se compro/Vendio el item
     * Columna: Precio Unitario
     * @return 
     */
    public double getPrecioUnitario();
    public void setPrecioUnitario(double precio);
    /**
     * Metodo que calcula el SubTotal de este Detalle
     * Columna: SubTotal
     * @return 
     */
    public float calcularSubTotal();
    /**
     * Setea el objeto concreto que se este detallando
     * @return 
     */
    public Object getItem();
    public void setItem(Object item);
    
}
