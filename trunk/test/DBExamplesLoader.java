/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import config.PropiedadBean;
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
import modelo.HerramientaDeEmpresa;
import modelo.Material;
import modelo.OrdenDeCompra;
import modelo.Proveedor;
import modelo.Recurso;
import modelo.RecursoXProveedor;
import modelo.PrecioSegunCantidad;
import modelo.RecursoEspecifico;
import modelo.Rubro;
import modelo.TipoLicenciaEmpleado;
import modelo.UnidadDeMedida;
import util.LogUtil;
import util.RubroUtil;
//import java.util.Set;

/**
 * Carga datos de prueba en la BD para que los podamos unsar
 * @author Iuga
 */
public class DBExamplesLoader {

    Session sesion;
    boolean band=false;

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
          this.cargarConfiguraciones();

          this.cargarUnidadesMedida();
          this.cargarFormasDePago();

          this.cargarRubros();

          this.cargarPaises();
          this.cargarTipoDocumento();
          this.cargarTipoTelefono();
          this.cargarEmpresasYPlantas();
          this.cargarTipoLicencias();
          this.cargarContactoResponsable();

          this.cargarCompras();

          this.cargarOrdenDeCompra();

          this.cargarHerramientasDeEmpresa();
    }

    public void cargarConfiguraciones()
    {
        PropiedadBean pb = new PropiedadBean("SISTEMA_IVA","21");
        PropiedadBean pb7 = new PropiedadBean("SISTEMA_NOMBRE","ACERO");

        PropiedadBean pb2 = new PropiedadBean("EMPRESA_NOMBRE","MetAr S.R.L.");
        PropiedadBean pb3 = new PropiedadBean("EMPRESA_DIRECCION","Dr. Dominguez 283");
        PropiedadBean pb4 = new PropiedadBean("EMPRESA_TELFAX","(02657)431599");
        PropiedadBean pb5 = new PropiedadBean("EMPRESA_CUIT","30-70936605-6");
        PropiedadBean pb6 = new PropiedadBean("EMPRESA_IIBB","01-30709366056");


        sesion.beginTransaction();
        sesion.save(pb);
        sesion.save(pb2);
        sesion.save(pb3);
        sesion.save(pb4);
        sesion.save(pb5);
        sesion.save(pb6);
        sesion.save(pb7);
        sesion.getTransaction().commit();
    }


    private void cargarRubros()
    {
        Rubro r1 = new Rubro(1,"Material", "Material");
        Rubro r2 = new Rubro(2,"Herramienta","Herramienta");
        Rubro r3 = new Rubro(3,"Consumible","Consumible");
        Rubro r4 = new Rubro(4,"Indumentaria","Indumentaria");
        Rubro r5 = new Rubro(5,"Transporte De Materiales y Herramientas","TransporteDeMaterialesYHerramientas");
        Rubro r6 = new Rubro(6,"Transporte De Pasajeros","TransporteDePasajeros");
        Rubro r7 = new Rubro(7,"Alojamiento","Alojamiento");

        sesion.beginTransaction();
        sesion.save(r1);
        sesion.save(r2);
        sesion.save(r3);
        sesion.save(r4);
        sesion.save(r5);
        sesion.save(r6);
        sesion.save(r7);
        sesion.getTransaction().commit();
        
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
                
                this.cargarMateriales(this.cargarProveedor());
                this.cargarHerramientas();
                
                this.cargarConsumibles();
                this.cargarAlojamientos();
                
            }
        }

    }

    private void cargarProveedores()
    {
        // NO TENGO GANAS
    }

    private void cargarAlojamientos()
    {
//        Recurso2 r1 = new Recurso2();
//        r1.setNombre("Casa");
//        Recurso2 r2 = new Recurso2();
//        r2.setNombre("Hotel");
//
//
//        Alojamiento h1 = new Alojamiento();
//        h1.setNombre("3 hambientes");
//        h1.setDescipcion("Córdoba capital");
//        h1.setRecurso(r1);
//        Alojamiento h2 = new Alojamiento();
//        h2.setNombre("Habitación doble");
//        h2.setDescipcion("3 estrellas");
//        h2.setRecurso(r2);
//
//
//        sesion.beginTransaction();
//        sesion.save(r1);
//        sesion.save(r2);
//        sesion.save(h1);
//        sesion.save(h2);
//        sesion.getTransaction().commit();
    }

    private void cargarConsumibles()
    {
//        Recurso2 r1 = new Recurso2();
//        r1.setNombre("Gas");
//        Recurso2 r2 = new Recurso2();
//        r2.setNombre("Electrodo");
//
//
//        Consumible h1 = new Consumible();
//        h1.setNombre("Acetileno");
//        h1.setDescipcion("3500 cal p/Soldadura y corte");
//        h1.setRecurso(r1);
//        Consumible h2 = new Consumible();
//        h2.setNombre("Propano");
//        h2.setDescipcion("1500 cal p/Soldadura en general");
//        h2.setRecurso(r1);
//        Consumible h3 = new Consumible();
//        h3.setNombre("Cobre 30cm");
//        h3.setDescipcion("-");
//        h3.setRecurso(r2);
//
//        sesion.beginTransaction();
//        sesion.save(r1);
//        sesion.save(r2);
//        sesion.save(h1);
//        sesion.save(h2);
//        sesion.save(h3);
//        sesion.getTransaction().commit();
    }

    private void cargarMateriales(Proveedor Prov)
    {
        Material h1 = new Material();
        h1.setNombre("Chapa");
        UnidadDeMedida um = new UnidadDeMedida();
        um.setAbreviatura("Kg.");
        um.setNombre("Kilogramo");
        h1.setUnidadDeMedida(um);

        ArrayList<RecursoEspecifico> items = new ArrayList<RecursoEspecifico>();
            RecursoEspecifico re1 = new RecursoEspecifico();
            re1.setNombre("Perfil IPN 200");
            re1.setDescipcion("IPN 200 de 2x7m.");
            items.add(re1);
            RecursoEspecifico re2 = new RecursoEspecifico();
            re2.setNombre("Perfil IPN 450");
            re2.setDescipcion("IPN 450 de 3x4m.");
            items.add(re2);
            RecursoEspecifico re3 = new RecursoEspecifico();
            re3.setNombre("Perfil IPN 750");
            re3.setDescipcion("IPN 750 de 2x3m.");
            items.add(re3);
        h1.setRecursos(items);
        
        Prov.getListaArticulos().add(re1);
        Prov.getListaArticulos().add(re2);
        //Prov.getListaArticulos().add(re3);

        RecursoXProveedor rxp1 =new RecursoXProveedor();
        ArrayList<PrecioSegunCantidad> lstPSC1 = new ArrayList<PrecioSegunCantidad>();
        PrecioSegunCantidad psc1 =new PrecioSegunCantidad();
        psc1.setCantidad(1);
        psc1.setPrecio(0.5);
        psc1.setFechaVigencia(new Date());
        psc1.setFecha(new Date());
        PrecioSegunCantidad psc2 =new PrecioSegunCantidad();
        psc2.setCantidad(10);
        psc2.setPrecio(0.4);
        psc2.setFechaVigencia(new Date());
        psc2.setFecha(new Date());
        lstPSC1.add(psc1);
        lstPSC1.add(psc2);
        rxp1.setListaPrecios(lstPSC1);
        rxp1.setProveedor(Prov);
        ArrayList<RecursoXProveedor> lstRxP1 = new ArrayList<RecursoXProveedor>();
        lstRxP1.add(rxp1);
        re1.setProveedores(lstRxP1);


        RecursoXProveedor rxp2 =new RecursoXProveedor();
        ArrayList<PrecioSegunCantidad> lstPSC2 = new ArrayList<PrecioSegunCantidad>();
        PrecioSegunCantidad psc1a =new PrecioSegunCantidad();
        psc1a.setCantidad(1);
        psc1a.setPrecio(0.3);
        psc1a.setFechaVigencia(new Date());
        psc1a.setFecha(new Date());
        PrecioSegunCantidad psc2a =new PrecioSegunCantidad();
        psc2a.setCantidad(10);
        psc2a.setPrecio(0.2);
        Date d=new Date();
        d.setDate(50000);
        psc2a.setFechaVigencia(new Date());
        double n=23123;
        psc2a.setFecha(d);

        PrecioSegunCantidad psc2a_ =new PrecioSegunCantidad();
        psc2a_.setCantidad(10);
        psc2a_.setPrecio(0.1);
        psc2a_.setFechaVigencia(new Date());
        psc2a_.setFecha(new Date());
        lstPSC2.add(psc2a_);
        lstPSC2.add(psc1a);
        lstPSC2.add(psc2a);
        rxp2.setListaPrecios(lstPSC2);
        rxp2.setProveedor(Prov);
        ArrayList<RecursoXProveedor> lstRxP2 = new ArrayList<RecursoXProveedor>();
        lstRxP2.add(rxp2);
        re2.setProveedores(lstRxP2);

        sesion.beginTransaction();
        sesion.save(um);
        
        sesion.save(h1);
        //sesion.saveOrUpdate(Prov);
        sesion.save(psc1);
        sesion.save(psc2);
        sesion.save(psc1a);
        sesion.save(psc2a);
        sesion.save(rxp1);
        sesion.save(rxp2);
        sesion.saveOrUpdate(re1);
        sesion.saveOrUpdate(re2);
        sesion.saveOrUpdate(re3);
        sesion.getTransaction().commit();
    }

    private void cargarHerramientas()
    {
//        Recurso2 r1 = new Recurso2();
//        r1.setNombre("Fresadora");
//        Recurso2 r2 = new Recurso2();
//        r2.setNombre("Torno");
//
//
//        Herramienta h1 = new Herramienta();
//        h1.setNombre("Automática BOSCH");
//        h1.setDescipcion("70000 rpm con control numérico");
//        h1.setRecurso(r1);
//        Herramienta h2 = new Herramienta();
//        h2.setNombre("Pinacho S90/260");
//        h2.setDescipcion("Para metal");
//        h2.setRecurso(r2);
//
//
//        sesion.beginTransaction();
//        sesion.save(r1);
//        sesion.save(r2);
//        sesion.save(h1);
//        sesion.save(h2);
//        sesion.getTransaction().commit();

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
        
        if(band==false)
        {
            p.setRazonSocial("SIDERUGIA SAN LUIS S.R.L.");
            band=true;
        }
 else
        {p.setRazonSocial("Metalcitos SAN LUIS S.R.L.");}
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

            // WORKARROUND, DESPUES MODIFIQUENLO SI QUIEREN (IUGA)
            Rubro r1 = RubroUtil.getRubro(1);
            Rubro r2 = RubroUtil.getRubro(2);
            Rubro r3 = RubroUtil.getRubro(3);
            ArrayList<Rubro> listaRubro = new ArrayList<Rubro>();
            listaRubro.add(r1);
            listaRubro.add(r2);
            listaRubro.add(r3);
            p.setConfiabilidad(0.5);
            p.setRubros(listaRubro); // ESTE PROVEEDOR VENDE TODO !!
            
            // FIN WORKARROUND

        sesion.beginTransaction();
        sesion.save(tel);
        sesion.save(d);
        sesion.saveOrUpdate(p);
        sesion.getTransaction().commit();
        return p;
    }

    public void cargarOrdenDeCompra(){
        OrdenDeCompra oc = new OrdenDeCompra();
        oc.setEstado(new EstadoOrdenDeCompraPendienteDeRecepcion());
        oc.setProveedor(cargarProveedor());

        UnidadDeMedida um = null;
        try {
            um = (UnidadDeMedida) HibernateUtil.getSession().get(UnidadDeMedida.class, 1);
        } catch (Exception ex) {
            Logger.getLogger(DBExamplesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        DetalleOrdenDeCompra doc = new DetalleOrdenDeCompra();
        Material m = new Material();
        m.setUnidadDeMedida(um);
        m.setNombre("PLACA DE METAL 2X2MTS");
        RecursoEspecifico re = new RecursoEspecifico();
        re.setNombre("PLACA DE METAL");
        re.setDescipcion("Galvanizada");

        ArrayList<RecursoEspecifico> res = new ArrayList<RecursoEspecifico>();
        res.add(re);
        m.setRecursos(res);
        doc.setRecurso(re);
        doc.setCantidad(12);
        doc.setPrecio(150);

        List<DetalleOrdenDeCompra> docs = new ArrayList<DetalleOrdenDeCompra>();
        docs.add(doc);
        oc.setDetalle(docs);

        DetalleOrdenDeCompra doc1 = new DetalleOrdenDeCompra();
        Material m1 = new Material();
        m1.setUnidadDeMedida(um);
        m1.setNombre("TUERCAS 2 PULGADAS");
        RecursoEspecifico re1 = new RecursoEspecifico();
        re1.setNombre("TUERCA");
        re1.setDescipcion("REFORZADA");

        ArrayList<RecursoEspecifico> res1 = new ArrayList<RecursoEspecifico>();
        res1.add(re1);
        m1.setRecursos(res1);
        doc1.setRecurso(re1);
        doc1.setCantidad(12);
        doc1.setPrecio(150);

        docs.add(doc1);
        oc.setDetalle(docs);
        oc.setFechaDeGeneracion(new Date());
        oc.setFechaDePedido(new Date());
        oc.setHib_flag_estado("modelo.EstadoOrdenDeCompraGenerada");
        oc.setFechaDeRecepcion(new Date(02122010));

        FormaDePago fp = new FormaDePago();
        fp.setNombre("Efectivo");
        oc.setFormaDePago(fp);

        sesion.beginTransaction();
        sesion.save(m);
        sesion.save(m1);
        sesion.save(re);
        sesion.save(re1);
        sesion.save(doc);
        sesion.save(doc1);
        sesion.save(fp);
        sesion.save(oc);
        sesion.getTransaction().commit();
    }

    private void cargarHerramientasDeEmpresa() {
        HerramientaDeEmpresa he = new HerramientaDeEmpresa();
        he.setNroSerie("000-KR4113654560/3A");
        RecursoEspecifico re = new RecursoEspecifico();
        re.setNombre("15 Mm.");
        Herramienta h = new Herramienta();
        h.setNombre("TORNO");
        h.getRecursos().add(re);
        he.setRecursoEsp(re);

        sesion.beginTransaction();
        sesion.save(re);
        sesion.save(h);
        sesion.save(he);
        sesion.getTransaction().commit();
    }
}

