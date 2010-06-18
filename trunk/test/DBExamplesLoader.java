/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import modelo.Barrio;
import modelo.Localidad;
import modelo.Pais;
import modelo.Provincia;
import modelo.TipoTelefono;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 * Carga datos de prueba en la BD para que los podamos unsar
 * @author Iuga
 */
public class DBExamplesLoader {

    Session sesion;

    public DBExamplesLoader() 
    {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        sesion = sf.openSession();

    }

    /**
     * Cargo datos de prueba, como algunos paises, provincias, etc
     */

    public void cargarEjemplos()
    {
        Pais p1 = new Pais();
        p1.setNombre("Argentina");

            Provincia prov1 = new Provincia();
            prov1.setNombre("Córdoba");
            Provincia prov2 = new Provincia();
            prov1.setNombre("San Luis");
            p1.addProvincia(prov1);
            p1.addProvincia(prov2);

                Localidad l1 = new Localidad();
                l1.setNombre("Alta Grácia");
                Localidad l2 = new Localidad();
                l2.setNombre("Córdoba");
                prov1.addLocalidad(l1);
                prov1.addLocalidad(l2);

                    Barrio b1 = new Barrio();
                    b1.setNombre("Carlos Pellegrini");
                    Barrio b2 = new Barrio();
                    b2.setNombre("Las Flores");
                    l1.addBarrio(b1);
                    l2.addBarrio(b2);

        Pais p2 = new Pais();
        p1.setNombre("Brazil");

        sesion.beginTransaction();
        sesion.save(b1);
        sesion.save(b2);
        sesion.save(l1);
        sesion.save(l2);
        sesion.save(prov1);
        sesion.save(prov2);
        sesion.save(p1);
        sesion.save(p2);
        sesion.getTransaction().commit();
    }

    public void cargarTipoTelefono()
    {
        TipoTelefono tt1 = new TipoTelefono();
        tt1.setNombre("TEL. PARTICULAR");
        TipoTelefono tt2 = new TipoTelefono();
        tt2.setNombre("FAX");
        TipoTelefono tt3 = new TipoTelefono();
        tt3.setNombre("CELULAR");
        sesion.beginTransaction();
        sesion.save(tt1);
        sesion.save(tt2);
        sesion.save(tt3);
        sesion.getTransaction().commit();
    }
}
