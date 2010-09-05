/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.Compras;

import java.util.ArrayList;
import java.util.Iterator;
import modelo.Rubro;
import util.RubroUtil;
import util.Tupla;
import vista.compras.pantallaConsultarPrecioXProveedor;

/**
 * Descripción:
 * @version 1.0
 * @author Administrador
 * @cambios
 * @todo
 */

public class GestorConsultarPrecioXProveedor {

    private pantallaConsultarPrecioXProveedor pantalla;

    public GestorConsultarPrecioXProveedor(pantallaConsultarPrecioXProveedor pantalla) {
        this.pantalla = pantalla;
    }

    public ArrayList<Tupla> mostrarRubros() {

        ArrayList<Tupla> lista = new ArrayList<Tupla>();

            ArrayList<Rubro> listaRubros = RubroUtil.getRubros();
            Iterator<Rubro> itr = listaRubros.iterator();
            while (itr.hasNext())
            {
                Rubro rubro = itr.next();
                Tupla tp = new Tupla(rubro.getId(),rubro.getNombre());
                lista.add(tp);
            }
        return lista;

    }

    


}
