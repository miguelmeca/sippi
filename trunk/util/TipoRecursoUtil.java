/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import modelo.Alojamiento;
import modelo.Consumible;
import modelo.Herramienta;
import modelo.Indumentaria;
import modelo.Material;
import modelo.Transporte;
import modelo.Viatico;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class TipoRecursoUtil {

    public static ArrayList<Tupla> getTiposDeRecurso()
    {
        ArrayList<Tupla> lista = new ArrayList<Tupla>();

            Material m = new Material();
            Tupla mat = new Tupla(1,m.toString());

            Herramienta h = new Herramienta();
            Tupla her = new Tupla(2,h.getNombre());

            Indumentaria i = new Indumentaria();
            Tupla ind = new Tupla(3,i.toString());

            Consumible c = new Consumible();
            Tupla con = new Tupla(4,c.toString());

            Viatico v = new Viatico();
            Tupla via = new Tupla(5,v.toString());

            Transporte t = new Transporte();
            Tupla tra = new Tupla(6,t.toString());

            Alojamiento a = new Alojamiento();
            Tupla alo = new Tupla(7,a.toString());
            

        lista.add(mat);
        lista.add(her);
        lista.add(ind);
        lista.add(con);
        lista.add(via);
        lista.add(tra);
        lista.add(alo);
        return lista;
    }

}
