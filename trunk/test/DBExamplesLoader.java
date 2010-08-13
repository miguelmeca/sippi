/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.ArrayList;
import modelo.Barrio;
import modelo.Empleado;
import modelo.Domicilio;
import modelo.RangoEspecialidad;
import modelo.TipoEspecialidad;
import modelo.TipoCapacitacion;
import modelo.EmpresaCliente;
import modelo.Localidad;
import modelo.Pais;
import modelo.Planta;
import modelo.Provincia;
import modelo.Telefono;
import modelo.TipoTelefono;
import modelo.TipoDocumento;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.Date;
import java.util.HashSet;
import modelo.TipoLicenciaEmpleado;
//import java.util.Set;

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
          this.cargarPaises();
          this.cargarTipoDocumento();
          this.cargarTipoTelefono();
          this.cargarEmpresasYPlantas();
          this.cargarTipoLicencias();
    }

    private void cargarTipoLicencias()
    {
        TipoLicenciaEmpleado tle = new TipoLicenciaEmpleado();
        tle.setNombre("Vacaciones");

        TipoLicenciaEmpleado tle2 = new TipoLicenciaEmpleado();
        tle2.setNombre("Enfermedad");

        sesion.beginTransaction();
        sesion.save(tle);
        sesion.save(tle2);
        sesion.getTransaction().commit();
    }

    private void cargarPaises()
    {
        Pais p1 = new Pais();
        p1.setNombre("Argentina");

            Provincia prov1 = new Provincia();
            prov1.setNombre("Cordoba");
            Provincia prov2 = new Provincia();
            prov2.setNombre("San Luis");
            p1.addProvincia(prov1);
            p1.addProvincia(prov2);

                Localidad l1 = new Localidad();
                l1.setNombre("Alta Gracia");
                Localidad l2 = new Localidad();
                l2.setNombre("Cordoba");
                prov1.addLocalidad(l1);
                prov1.addLocalidad(l2);
                Localidad l3 = new Localidad();
                l3.setNombre("Villa Mercedes");
                prov2.addLocalidad(l3);

                    Barrio b1 = new Barrio();
                    b1.setNombre("Carlos Pellegrini");
                    Barrio b2 = new Barrio();
                    b2.setNombre("Las Flores");
                    Barrio b3 = new Barrio();
                    b3.setNombre("Norte");
                    Barrio b4 = new Barrio();
                    b4.setNombre("Sarmiento");
                    l1.addBarrio(b1);
                    l2.addBarrio(b2);
                    l3.addBarrio(b3);
                    l3.addBarrio(b4);

        Pais p2 = new Pais();
        p2.setNombre("Brazil");

            Provincia prov3 = new Provincia();
            prov3.setNombre("Ceara");
            p2.addProvincia(prov3);

                Localidad l9 = new Localidad();
                l9.setNombre("Fortaleza");
                prov3.addLocalidad(l9);

///////////////////////////////////////////
        Empleado emp=new Empleado();//
        emp.setApellido("sorongo");//
        emp.setNombre("pedro");//
        emp.setLegajo(53);//
        emp.setNroDoc("12345678");//
        Date d=new Date();
        d.setTime(894665);
        emp.setFechadeNac(d);
        emp.setFechaAlta(d);
        Domicilio dom=new Domicilio();
        //Barrio b=new Barrio();
       // b.setNombre("la concha d tu vieja");
        dom.setCalle("Av. MeImportaUnCarajo");
        emp.setDomicilio(dom);
////////////////////////////////////////////
        TipoEspecialidad te= new TipoEspecialidad();
        te.setNombre("Soldador");
        TipoEspecialidad te2= new TipoEspecialidad();
        te2.setNombre("Chapista");
        RangoEspecialidad rng1= new RangoEspecialidad();
        rng1.setNombre("1");
        RangoEspecialidad rng2= new RangoEspecialidad();
        rng2.setNombre("2");
        RangoEspecialidad rng3= new RangoEspecialidad();
        rng3.setNombre("3");
        TipoCapacitacion tc= new TipoCapacitacion();
        tc.setNombre("Seguridad 1");
        TipoCapacitacion tc2= new TipoCapacitacion();
        tc2.setNombre("Manejo de Herramienta");


        sesion.beginTransaction();
        sesion.save(b1);
        sesion.save(b2);
        sesion.save(b3);
        sesion.save(b4);
        sesion.save(l1);
        sesion.save(l2);
        sesion.save(l3);
        sesion.save(prov1);
        sesion.save(prov2);
        sesion.save(p1);
        sesion.save(p2);
        sesion.save(prov3);
        sesion.save(l9);
        sesion.save(te);
        sesion.save(te2);
        sesion.save(tc);
        sesion.save(tc2);
        sesion.save(rng1);
        sesion.save(rng2);
        sesion.save(rng3);
        //sesion.save(emp); //
        sesion.getTransaction().commit();
    }


    public void cargarTipoDocumento()
    {
        TipoDocumento td1= new TipoDocumento();
        td1.setNombre("D.N.I.");
        td1.setDescripcion("Documento Nacional de Identidad");
        TipoDocumento td2= new TipoDocumento();
        td2.setNombre("L.E.");
        td2.setDescripcion("Libreta de Enrolamiento");
        TipoDocumento td3= new TipoDocumento();
        td3.setNombre("L.C.");
        td3.setDescripcion("Libreta Civica");
        sesion.beginTransaction();
        sesion.save(td1);
        sesion.save(td2);
        sesion.save(td3);
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

    public void cargarEmpresasYPlantas(){

        EmpresaCliente ec1 = new EmpresaCliente();
        ec1.setRazonSocial("ARCOR SRL");
        ec1.setCuit("66688990-5");
        ec1.setEmail("contacto@arcor.com.ar");
        ec1.setPaginaWeb("http://www.arcor.com.ar");

            Domicilio d = new Domicilio();
            d.setCalle("Av. Chacabuco");
            d.setNumero(4545);
            d.setPiso(4);
            d.setDepto("G");
            d.setCodigoPostal("X5000UGT");

            sesion.beginTransaction();
            Barrio b = (Barrio)sesion.get(Barrio.class, 1);
            sesion.getTransaction().commit();
            d.setBarrio(b);
            ec1.setDomicilio(d);

            Telefono tel = new Telefono();
            tel.setNumero("4567888");
            tel.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 1)));
            HashSet<Telefono> tels = new HashSet<Telefono>();
            tels.add(tel);
            ec1.setTelefonos(tels);

            Planta planta1 = new Planta();
            planta1.setRazonSocial("Bagleysita SA");

                Domicilio d2 = new Domicilio();
                d2.setCalle("Chacabucoxios");
                d2.setNumero(123);
                d2.setPiso(9);
                d2.setDepto("A");
                d2.setCodigoPostal("X5000UGT");
                d2.setBarrio(b); // Se les olvido esto !!
                planta1.setDomicilio(d2);

                Telefono t9 = new Telefono();
                t9.setNumero("420708");
                t9.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 2)));
                planta1.addTelefono(t9);

            ArrayList<Planta> aux = new ArrayList<Planta>();
            aux.add(planta1);
            ec1.setPlantas(aux);

        sesion.beginTransaction();
        sesion.save(tel);
        sesion.save(t9);
        sesion.save(d2);
        sesion.save(planta1);
        
        sesion.save(d);
        sesion.save(ec1);
        sesion.getTransaction().commit();

    }
}

