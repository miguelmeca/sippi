/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Alojamiento;
import modelo.Consumible;
import modelo.ContactoResponsable;
import modelo.DetalleOrdenDeCompra;
import modelo.EstadoOrdenDeCompraPendienteDeRecepcion;
import modelo.FormaDePago;
import modelo.Herramienta;
import modelo.Material;
import modelo.OrdenDeCompra;
import modelo.Proveedor;
import modelo.Recurso;
import modelo.TipoLicenciaEmpleado;
import modelo.UnidadDeMedida;
import util.LogUtil;
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
          this.cargarUnidadesMedida();
          this.cargarFormasDePago();

          this.cargarPaises();
          this.cargarTipoDocumento();
          this.cargarTipoTelefono();
          this.cargarEmpresasYPlantas();
          this.cargarTipoLicencias();
          this.cargarContactoResponsable();

          this.cargarCompras();

          this.cargarOrdenDeCompra();
    }


    private void cargarFormasDePago()
    {
        FormaDePago fp1 = new FormaDePago();
        fp1.setNombre("Cheque");
        FormaDePago fp2 = new FormaDePago();
        fp2.setNombre("Efectivo");
        FormaDePago fp3 = new FormaDePago();
        fp3.setNombre("Transferencia Bancaria");
        FormaDePago fp4 = new FormaDePago();
        fp4.setNombre("Deposito Bancario");
        try
        {

            sesion.beginTransaction();
            sesion.save(fp1);
            sesion.save(fp2);
            sesion.save(fp3);
            sesion.save(fp4);
            sesion.getTransaction().commit();

        } catch (Exception ex)
        {
            LogUtil.addError("ERROR CARGANDO LOS DATOS INICIALES DE UNIDAD DE MEDIDA");
            ex.printStackTrace();
        }
    }


    private void cargarUnidadesMedida()
    {
        UnidadDeMedida um1 = new UnidadDeMedida();
        um1.setNombre("Unidad");
        um1.setAbreviatura("UM.");
        UnidadDeMedida um2 = new UnidadDeMedida();
        um2.setNombre("Kilogramo");
        um2.setAbreviatura("Kg.");
        try
        {

            sesion.beginTransaction();
            sesion.save(um1);
            sesion.save(um2);
            sesion.getTransaction().commit();

        } catch (Exception ex)
        {
            LogUtil.addError("ERROR CARGANDO LOS DATOS INICIALES DE UNIDAD DE MEDIDA");
            ex.printStackTrace();
        }

    }

    private void cargarCompras()
    {
               int seleccion = JOptionPane.showOptionDialog(
                       new JFrame(),
                       "¿Desea cargar los datos de COMPRAS?",
                       "Seleccione una opción",
                       JOptionPane.YES_NO_CANCEL_OPTION,
                       JOptionPane.QUESTION_MESSAGE,
                       null,    // null para icono por defecto.
                       new Object[] { "Si", "No"},   // null para YES, NO y CANCEL
                       "Si");

        if (seleccion != -1)
        {
            if((seleccion + 1)==1)
            {
                this.cargarHerramientas();
                this.cargarMateriales();
                this.cargarConsumibles();
                this.cargarAlojamientos();
            }
        }

    }

    private void cargarAlojamientos()
    {
        Recurso r1 = new Recurso();
        r1.setNombre("Casa");
        Recurso r2 = new Recurso();
        r2.setNombre("Hotel");


        Alojamiento h1 = new Alojamiento();
        h1.setNombre("3 hambientes");
        h1.setDescipcion("Córdoba capital");
        h1.setRecurso(r1);
        Alojamiento h2 = new Alojamiento();
        h2.setNombre("Habitación doble");
        h2.setDescipcion("3 estrellas");
        h2.setRecurso(r2);


        sesion.beginTransaction();
        sesion.save(r1);
        sesion.save(r2);
        sesion.save(h1);
        sesion.save(h2);
        sesion.getTransaction().commit();
    }

    private void cargarConsumibles()
    {
        Recurso r1 = new Recurso();
        r1.setNombre("Gas");
        Recurso r2 = new Recurso();
        r2.setNombre("Electrodo");


        Consumible h1 = new Consumible();
        h1.setNombre("Acetileno");
        h1.setDescipcion("3500 cal p/Soldadura y corte");
        h1.setRecurso(r1);
        Consumible h2 = new Consumible();
        h2.setNombre("Propano");
        h2.setDescipcion("1500 cal p/Soldadura en general");
        h2.setRecurso(r1);
        Consumible h3 = new Consumible();
        h3.setNombre("Cobre 30cm");
        h3.setDescipcion("-");
        h3.setRecurso(r2);

        sesion.beginTransaction();
        sesion.save(r1);
        sesion.save(r2);
        sesion.save(h1);
        sesion.save(h2);
        sesion.save(h3);
        sesion.getTransaction().commit();
    }

    private void cargarMateriales()
    {
        Recurso r1 = new Recurso();
        r1.setNombre("Galvanizado");
        Recurso r2 = new Recurso();
        r2.setNombre("Chapa");


        Material h1 = new Material();
        h1.setNombre("Perfil IPN 200");
        h1.setDescipcion("Largo 3 metros espesor 7,5");
        h1.setRecurso(r1);
        Material h2 = new Material();
        h2.setNombre("Perfil IPN 450");
        h2.setDescipcion("Largo 2.5 metros espesor 10");
        h2.setRecurso(r1);
        Material h3 = new Material();
        h3.setNombre("2B frío (A-240)");
        h3.setDescipcion("1,5 x 1500 x 1829 304");
        h3.setRecurso(r2);

        sesion.beginTransaction();
        sesion.save(r1);
        sesion.save(r2);
        sesion.save(h1);
        sesion.save(h2);
        sesion.save(h3);
        sesion.getTransaction().commit();
    }

    private void cargarHerramientas()
    {
        Recurso r1 = new Recurso();
        r1.setNombre("Fresadora");
        Recurso r2 = new Recurso();
        r2.setNombre("Torno");


        Herramienta h1 = new Herramienta();
        h1.setNombre("Automática BOSCH");
        h1.setDescipcion("70000 rpm con control numérico");
        h1.setRecurso(r1);
        Herramienta h2 = new Herramienta();
        h2.setNombre("Pinacho S90/260");
        h2.setDescipcion("Para metal");
        h2.setRecurso(r2);


        sesion.beginTransaction();
        sesion.save(r1);
        sesion.save(r2);
        sesion.save(h1);
        sesion.save(h2);
        sesion.getTransaction().commit();

    }

    private void cargarTipoLicencias()
    {
        TipoLicenciaEmpleado tle = new TipoLicenciaEmpleado();
        tle.setNombre("Vacaciones");
        TipoLicenciaEmpleado tle2 = new TipoLicenciaEmpleado();
        tle2.setNombre("Enfermedad");
        TipoLicenciaEmpleado tle3 = new TipoLicenciaEmpleado();
        tle3.setNombre("Enfermedad de Familiar");
        TipoLicenciaEmpleado tle4 = new TipoLicenciaEmpleado();
        tle4.setNombre("Paternidad / Maternidad");
        TipoLicenciaEmpleado tle5 = new TipoLicenciaEmpleado();
        tle5.setNombre("Matrimonio");        
        TipoLicenciaEmpleado tle6 = new TipoLicenciaEmpleado();
        tle6.setNombre("Licencias Especiales");

        sesion.beginTransaction();
        sesion.save(tle);
        sesion.save(tle2);
        sesion.save(tle3);
        sesion.save(tle4);
        sesion.save(tle5);
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

    public void cargarContactoResponsable(){
        ContactoResponsable contacto = new ContactoResponsable();
        contacto.setNombre("Andrés");
        contacto.setApellido("Pedraza");
        contacto.setCargo("Encargado de Compras");
        contacto.setEmail("apedraza@gmail.com");
        contacto.setCuil("20-15678445-8");
        Telefono t = new Telefono();
        t.setNumero("(0351) 4564478");
        t.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 1)));
        contacto.addTelefono(t);
        contacto.setfechaAlta(new Date());

        sesion.beginTransaction();
        sesion.save(t);
        sesion.save(contacto);
        sesion.getTransaction().commit();
    }

    private Proveedor cargarProveedor(){
        Proveedor p = new Proveedor();
        p.setRazonSocial("SIDERUGIA SAN LUIS S.R.L.");
        p.setCuit("34345555-5");
        p.setEmail("atclientes@ssl.com.ar");
        p.setPaginaWeb("http://www.ssl.com.ar");

            Domicilio d = new Domicilio();
            d.setCalle("Av. Los Plátanos");
            d.setNumero(204);
            d.setPiso(0);
            d.setDepto("X");
            d.setCodigoPostal("X3000UOO");

            sesion.beginTransaction();
            Barrio b = (Barrio)sesion.get(Barrio.class, 1);
            sesion.getTransaction().commit();
            d.setBarrio(b);
            p.setDomicilio(d);

            Telefono tel = new Telefono();
            tel.setNumero("4456834");
            tel.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 2)));
            HashSet<Telefono> tels = new HashSet<Telefono>();
            tels.add(tel);
            p.setTelefonos(tels);

        sesion.beginTransaction();
        sesion.save(tel);
        sesion.save(d);
        sesion.save(p);
        sesion.getTransaction().commit();
        return p;
    }

    public void cargarOrdenDeCompra(){
        OrdenDeCompra oc = new OrdenDeCompra();
        oc.setEstado(new EstadoOrdenDeCompraPendienteDeRecepcion());
        oc.setProveedor(cargarProveedor());

        DetalleOrdenDeCompra doc = new DetalleOrdenDeCompra();
        Material m = new Material();
        m.setNombre("PLACA DE METAL 2X2MTS");
        Recurso r = new Recurso();
        r.setNombre("PLACA DE METAL");
        m.setRecurso(r);
        m.setDescipcion("GALVANIZADA");
        doc.setRecurso(m);
        doc.setCantidad(12);
        doc.setPrecio(150);

        List<DetalleOrdenDeCompra> docs = new ArrayList<DetalleOrdenDeCompra>();
        docs.add(doc);
        oc.setDetalle(docs);

        oc.setFechaDePedido(new Date());
        oc.setHib_flag_estado("modelo.EstadoOrdenDeCompraPendienteDeRecepcion");
        oc.setFechaDeRecepcion(new Date(02122010));

        FormaDePago fp = new FormaDePago();
        fp.setNombre("Efectivo");
        oc.setFormaDePago(fp);

        sesion.beginTransaction();
        sesion.save(m);
        sesion.save(r);
        sesion.save(doc);
        sesion.save(fp);
        sesion.save(oc);
        sesion.getTransaction().commit();
    }
}

