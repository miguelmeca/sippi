/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.cotizacion;
import vista.cotizacion.ExplorarCotizaciones;
import util.NTupla;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;
import modelo.Cotizacion;
import util.FechaUtil;
/////////////////////
import java.util.Map;
import java.util.HashMap;
import util.ReporteUtil;
/**
 *
 * @author Fran
 */
public class GestorExplorarCotizaciones
{

    private ExplorarCotizaciones pantalla;
    private Session sesion;
    private List lista;





    public GestorExplorarCotizaciones(ExplorarCotizaciones pantalla)
    {
        this.pantalla = pantalla;
       
    }

    
    public List listaCotizaciones()
    {


        try{

        sesion= HibernateUtil.getSession();
        } catch (Exception ex)////////////
            {//////////////////////////////////////////
                System.out.println("No se pudo abrir la sesion");//////////

            }

            lista = sesion.createQuery("from Cotizacion").list();


            ArrayList<NTupla> listaCotizaciones = new ArrayList<NTupla>();
            for (int i = 0; i < lista.size(); i++) {
                Cotizacion cot = (Cotizacion)lista.get(i);
                String nombrePedidoObra=cot.buscarPedidoObra().getNombre();
                NTupla tupla = new NTupla(cot.getId());
                tupla.setNombre(nombrePedidoObra);//No me gusta como queda esto... Pero es la 1er columna...
                String[] datos=new String[5];
                datos[0]=String.valueOf(cot.getNroCotizacion());
                datos[1]=String.valueOf(cot.getNroRevision());
                datos[2]=String.valueOf(FechaUtil.getFecha(cot.getFechaCreacion()));                
                datos[3]=String.valueOf(FechaUtil.getFecha(cot.getFechaModificacion()));
                datos[4]=String.valueOf(cot.CalcularTotal());
                tupla.setData(datos);
                    listaCotizaciones.add(tupla);
            }

            return listaCotizaciones;

    }
    
    
    
    
    public void reporteCotizacionExternaPorSubObra(int id)
    {
        String urlReporte = "/vista/reportes/CotizacionExternaSubObra.jasper";
        Map params = parametrosAImprimir(id);
        ReporteUtil ru = new ReporteUtil();
        try
        {
            ru.mostrarReporte(urlReporte,params);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Map parametrosAImprimir(int id) {
        Map params = new HashMap();
        try {
            Cotizacion co = (Cotizacion) HibernateUtil.getSession().get(Cotizacion.class, id);
            params.put("idCOT",id);
           // params.put("PROVEEDOR", oc.getProveedor().getRazonSocial());
            //params.put("CUIT", oc.getProveedor().getCuit());
            //params.put("DIRECCION", oc.getProveedor().getDomicilio().toString());
        } catch (Exception ex) {
           
        }
        return params;
    }
    
     

}
