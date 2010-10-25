/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;
import java.util.List;

/**
 *
 * @author dell
 */
public class DetalleEtapa
{
    int id;
    int orden;

    public DetalleEtapa()
    {
    }

    public void setId(int id)
    {this.id=id;}
    public int getId()
    {return id; }
    public void setOrden(int orden)
    {this.orden=orden;}
    public int getOrden()
    {return orden; }
    public List<DetalleMaterial> getDetallesMaterial() {
        return null;
    }
    public List<InstanciaDeRolPorTarea> getListaInstRolXTarea() {
        return null;
    }
    public String getDescripcion() {
        return "";
    }
    public List<HerramientaDeEmpresa> getHerramientas() {
        return null;
    }

}
