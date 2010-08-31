package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.TipoRecursoUtil;
import util.Tupla;

/**
 * Descripción:
 * @version 1.0
 * @author Iuga
 * @cambios
 * @todo
 */

public class Proveedor extends Empresa {

    private List precios;

    public List getPrecios() {
        return precios;
    }

    public void setPrecios(List precios) {
        this.precios = precios;
    }

    /**
     * Devuelve las NTuplas de los TIPOS DE RECURSO QUE DISPONE
     * SEGUN LOS RECURSOS ESPECIFICOS QUE TENGA CARGADOS !!!!!
     * @return
     */
    public ArrayList<Tupla> buscarTipoDeRecursosDisponibles()
    {
        ArrayList<Tupla> listaBase = TipoRecursoUtil.getTiposDeRecurso();
        ArrayList<Tupla> listaRet  = new ArrayList<Tupla>();

        Iterator it = this.precios.iterator();
        while (it.hasNext())
        {
            // TENGO EL PRECIO, BUSCO EL RECURSO
            PrecioXRecurso precio = (PrecioXRecurso)it.next();
            // BUSCO LOS TIDOS DE RECURSO
            RecursoEspecifico re = precio.getRecurso();
            Iterator<Tupla> itLi = listaBase.iterator();
            while (itLi.hasNext())
            {
                Tupla tp = itLi.next();
                if(tp.getNombre().equals(re.toString()))
                {
                    // Veo q no haya duplicados
                    if(!listaRet.contains(tp))
                    {
                        listaRet.add(tp);
                    }
                }
            }
        }

        return listaRet;
    }


}
