package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.RubroUtil;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Proveedor extends Empresa {

    private List<Rubro> rubros;
    private List<RecursoEspecifico> listaArticulos;
    private double confiabilidad;

    public Proveedor()
    {
        rubros=new ArrayList<Rubro>();;
        listaArticulos  = new ArrayList<RecursoEspecifico>();
    }

    public List<Rubro> getRubros() {
        return rubros;
    }

    public void setRubros(List<Rubro> rubros) {
        this.rubros = rubros;
    }

    public List<RecursoEspecifico> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<RecursoEspecifico> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public void setConfiabilidad(double c) {
        this.confiabilidad=c;
    }
    public double getConfiabilidad() {
        return this.confiabilidad;
    }

    /**
     * Devuelve las NTuplas de los TIPOS DE RECURSO QUE DISPONE
     * SEGUN LOS RECURSOS ESPECIFICOS QUE TENGA CARGADOS !!!!!
     * @return
     */
//    public ArrayList<Tupla> buscarTipoDeRecursosDisponibles()
//    {
//        ArrayList<Tupla> listaBase = TipoRecursoUtil.getTiposDeRecurso();
//        ArrayList<Tupla> listaRet  = new ArrayList<Tupla>();
//
//        Iterator it = this.precios.iterator();
//        while (it.hasNext())
//        {
//            // TENGO EL PRECIO, BUSCO EL RECURSO
//            PrecioXRecurso precio = (PrecioXRecurso)it.next();
//            // BUSCO LOS TIDOS DE RECURSO
//            RecursoEspecifico re = precio.getRecurso();
//            Iterator<Tupla> itLi = listaBase.iterator();
//            while (itLi.hasNext())
//            {
//                Tupla tp = itLi.next();
//                if(tp.getNombre().equals(re.toString()))
//                {
//                    // Veo q no haya duplicados
//                    if(!listaRet.contains(tp))
//                    {
//                        listaRet.add(tp);
//                    }
//                }
//            }
//        }
//
//        return listaRet;
//    }


}
