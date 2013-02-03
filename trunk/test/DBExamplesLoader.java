package test;

import config.PropiedadBean;
import controlador.cotizacion.GestorCotizacionMateriales;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Barrio;
import modelo.Empleado;
import modelo.Domicilio;
import modelo.RangoEspecialidad;
import modelo.TipoEspecialidad;
import modelo.Especialidad;
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
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import modelo.*;
import org.omg.CORBA.PRIVATE_MEMBER;
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
        // EN Duda, debería volar:
        this.cargarRubros(); // ???? Sigue existiendo ???
        
        // LA PRIMERA VEZ
        // Cosas que se tienen que cargar si o si y no son ejemplos:
        this.cargarConfiguraciones();
        this.cargarUsuarios();
        this.cargarUnidadesMedida(); 
        this.cargarFormasDePago();
        this.cargarLocalidades();
        this.cargarTipoTelefono();
        this.cargarTipoDocumento();
        this.cargarTipoLicencias();
        this.cargarTiposGastosVarios();
        this.cargarTipoAlquilerCompra();
        this.cargarTipoTarea();
        this.cargarRolesContactoResponsable();
          
        // EJEMPLOS PARA DEVELOPMENT
//        this.cargarEmpresasYPlantas();
//        this.cargarEmplados();
//        this.cargarContactoResponsable();
//        this.cargarCompras(); 
//        this.cargarOrdenDeCompra();
//        this.cargarHerramientasDeEmpresa();
//        this.cargarCotizacionEjemplo();
//        this.cargarPlanificacionEjemplo();
//        this.cargarStock();
//        this.cargarOrdenesDeCompra();
        
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
        PropiedadBean pb8 = new PropiedadBean("MULTIPLICADOR_HORAS_50","1.5");
        PropiedadBean pb9 = new PropiedadBean("MULTIPLICADOR_HORAS_100","2.0");


        sesion.beginTransaction();
        sesion.save(pb);
        sesion.save(pb2);
        sesion.save(pb3);
        sesion.save(pb4);
        sesion.save(pb5);
        sesion.save(pb6);
        sesion.save(pb7);
        sesion.save(pb8);
        sesion.save(pb9);
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
        um1.setAbreviatura("Un");
        
        UnidadDeMedida um2 = new UnidadDeMedida();
        um2.setNombre("Kilogramo");
        um2.setAbreviatura("Kg");
        
        UnidadDeMedida um3 = new UnidadDeMedida();
        um3.setNombre("Metros");
        um3.setAbreviatura("m");
        
        UnidadDeMedida um4 = new UnidadDeMedida();
        um4.setNombre("Gramos");
        um4.setAbreviatura("g");   
        
        UnidadDeMedida um5 = new UnidadDeMedida();
        um5.setNombre("Bolsas");
        um5.setAbreviatura("bo");           

        UnidadDeMedida um6 = new UnidadDeMedida();
        um6.setNombre("Centimetros");
        um6.setAbreviatura("cm");     
        
        UnidadDeMedida um7 = new UnidadDeMedida();
        um7.setNombre("Litros");
        um7.setAbreviatura("l");
        
        UnidadDeMedida um8 = new UnidadDeMedida();
        um8.setNombre("Metros Cuadrados");
        um8.setAbreviatura("m2");            

        UnidadDeMedida um9 = new UnidadDeMedida();
        um9.setNombre("Pulgadas");
        um9.setAbreviatura("pul");                    
        
        UnidadDeMedida um10 = new UnidadDeMedida();
        um10.setNombre("Onza");
        um10.setAbreviatura("oz");          

        UnidadDeMedida um11 = new UnidadDeMedida();
        um11.setNombre("Tonelada");
        um11.setAbreviatura("Tm");        
        
        try
        {
            sesion.beginTransaction();
            sesion.saveOrUpdate(um1);
            sesion.saveOrUpdate(um2);
            sesion.saveOrUpdate(um3);
            sesion.saveOrUpdate(um4);
            sesion.saveOrUpdate(um5);
            sesion.saveOrUpdate(um6);
            sesion.saveOrUpdate(um7);
            sesion.saveOrUpdate(um8);
            sesion.saveOrUpdate(um9);
            sesion.saveOrUpdate(um10);
            sesion.saveOrUpdate(um11);
            sesion.getTransaction().commit();

        } catch (Exception ex)
        {
            LogUtil.addError("ERROR CARGANDO LOS DATOS INICIALES DE UNIDAD DE MEDIDA");
            ex.printStackTrace();
        }
    }

    private void cargarCompras()
    {
        this.cargarMateriales(this.cargarProveedor());
        this.cargarHerramientas();

        this.cargarConsumibles();
        this.cargarAlojamientos();
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
//        h1.setDescipcion("CÃ³rdoba capital");
//        h1.setRecurso(r1);
//        Alojamiento h2 = new Alojamiento();
//        h2.setNombre("HabitaciÃ³n doble");
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
        um.setAbreviatura("Pk.");
        um.setNombre("Pack");
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
        psc1a.setPrecio(10);
        psc1a.setFechaVigencia(new Date());
        psc1a.setFecha(new Date());
        PrecioSegunCantidad psc2a =new PrecioSegunCantidad();
        psc2a.setCantidad(10);
        psc2a.setPrecio(70);
        Date d=new Date();
        psc2a.setFechaVigencia(new Date());
        psc2a.setFecha(new Date());
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
        sesion.save(psc2a_);
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
//        h1.setNombre("AutomÃ¡tica BOSCH");
//        h1.setDescipcion("70000 rpm con control numÃ©rico");
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
        TipoLicenciaEmpleado tle7 = new TipoLicenciaEmpleado();
        tle7.setNombre("Varios");        

        sesion.beginTransaction();
        sesion.save(tle);
        sesion.save(tle2);
        sesion.save(tle3);
        sesion.save(tle4);
        sesion.save(tle5);
        sesion.save(tle6);
        sesion.save(tle7);
        sesion.getTransaction().commit();
    }

    private void cargarLocalidades()
    {
        Pais p1 = new Pais();
        p1.setNombre("Argentina");

            Provincia prov1 = new Provincia();
            prov1.setNombre("Buenos Aires");
            Provincia prov2 = new Provincia();
            prov2.setNombre("Catamarca");
            Provincia prov3 = new Provincia();
            prov3.setNombre("Chacho");
            Provincia prov4 = new Provincia();
            prov4.setNombre("Chubut");
            Provincia prov5 = new Provincia();
            prov5.setNombre("Córdoba");
            Provincia prov6 = new Provincia();
            prov6.setNombre("Corrientes");
            Provincia prov7 = new Provincia();
            prov7.setNombre("Entre Ríos");
            Provincia prov8 = new Provincia();
            prov8.setNombre("Formosa");
            Provincia prov9 = new Provincia();
            prov9.setNombre("Jujuy");
            Provincia prov10 = new Provincia();
            prov10.setNombre("La Rioja");
            Provincia prov11 = new Provincia();
            prov11.setNombre("Mendoza");
            Provincia prov12 = new Provincia();
            prov12.setNombre("Misiones");
            Provincia prov13 = new Provincia();
            prov13.setNombre("Neuquén");
            Provincia prov14 = new Provincia();
            prov14.setNombre("Río Negro");
            Provincia prov15 = new Provincia();
            prov15.setNombre("Salta");
            Provincia prov16 = new Provincia();
            prov16.setNombre("San Juan");
            Provincia prov17 = new Provincia();
            prov17.setNombre("San Luis");
            Provincia prov18 = new Provincia();
            prov18.setNombre("Santa Cruz");
            Provincia prov19 = new Provincia();
            prov19.setNombre("Santa Fe");
            Provincia prov20 = new Provincia();
            prov20.setNombre("Santiago del Estero");
            Provincia prov21 = new Provincia();
            prov21.setNombre("Tierra del Fuego");
            Provincia prov22 = new Provincia();
            prov22.setNombre("Tucumán");
            
            
            p1.addProvincia(prov1);
            p1.addProvincia(prov2);
            p1.addProvincia(prov3);
            p1.addProvincia(prov4);
            p1.addProvincia(prov5);
            p1.addProvincia(prov6);
            p1.addProvincia(prov7);
            p1.addProvincia(prov8);
            p1.addProvincia(prov9);
            p1.addProvincia(prov10);
            p1.addProvincia(prov11);
            p1.addProvincia(prov12);
            p1.addProvincia(prov13);
            p1.addProvincia(prov14);
            p1.addProvincia(prov15);
            p1.addProvincia(prov16);
            p1.addProvincia(prov17);
            p1.addProvincia(prov18);
            p1.addProvincia(prov19);
            p1.addProvincia(prov20);
            p1.addProvincia(prov21);
            p1.addProvincia(prov22);

                // Algunas Localidades (Ver bien las de SanLuis)
                // Cordoba
                Localidad l1 = new Localidad();
                l1.setNombre("Alta Gracia");
                prov5.addLocalidad(l1);
                Localidad l2 = new Localidad();
                l2.setNombre("Cordoba");
                prov5.addLocalidad(l2);
                Localidad l10 = new Localidad();
                l10.setNombre("Villa del Totoral");
                prov5.addLocalidad(l10);
                Localidad l11 = new Localidad();
                l11.setNombre("Rio Cuarto");
                prov5.addLocalidad(l11);
                
                // San Luis
                Localidad l3 = new Localidad();
                l3.setNombre("Villa Mercedes");
                Localidad l4 = new Localidad();
                l4.setNombre("San Luis (Capital)");
                Localidad l5 = new Localidad();
                l5.setNombre("Justo Daract");
                prov17.addLocalidad(l3);
                prov17.addLocalidad(l4);
                prov17.addLocalidad(l5);
                
                    // Barrios de Villa Mercedes
                    Barrio b5 = new Barrio();
                    b5.setNombre("Pimpollo");
                    Barrio b6 = new Barrio();
                    b6.setNombre("Centro");
                    Barrio b7 = new Barrio();
                    b7.setNombre("Las Mirandas");
                    Barrio b8 = new Barrio();
                    b8.setNombre("San Antonio");
                    Barrio b9 = new Barrio();
                    b9.setNombre("Policlínico");
                    Barrio b10 = new Barrio();
                    b10.setNombre("Güemes");
                    Barrio b11 = new Barrio();
                    b11.setNombre("La Ribera");
                    Barrio b12 = new Barrio();
                    b12.setNombre("Atte 2");
                    Barrio b13 = new Barrio();
                    b13.setNombre("Jardín del Sur");
                    l3.addBarrio(b5);
                    l3.addBarrio(b6);
                    l3.addBarrio(b7);
                    l3.addBarrio(b8);
                    l3.addBarrio(b9);
                    l3.addBarrio(b10);
                    l3.addBarrio(b11);
                    l3.addBarrio(b12);
                    l3.addBarrio(b13);
                
                
                // Buenos Aires
                Localidad l6 = new Localidad();
                l6.setNombre("Salto");
                Localidad l7 = new Localidad();
                l7.setNombre("9 de Julio");
                Localidad l8 = new Localidad();
                l8.setNombre("Ciudad Autonoma de Buenos Aires");
                Localidad l9 = new Localidad();
                l9.setNombre("Bahía Blanca ");
                prov1.addLocalidad(l6);
                prov1.addLocalidad(l7);
                prov1.addLocalidad(l8);
                prov1.addLocalidad(l9);
                
                // Santa Fe
                Localidad l12 = new Localidad();
                l12.setNombre("Rosario");
                prov19.addLocalidad(l12);
                Localidad l13 = new Localidad();
                l13.setNombre("Santa Fe");
                prov19.addLocalidad(l13);
                        
                        
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
       

        sesion.beginTransaction();
        
            // Barrios
            sesion.save(b1);
            sesion.save(b2);
            sesion.save(b3);
            sesion.save(b4);
            sesion.save(b5);
            sesion.save(b6);
            sesion.save(b7);
            sesion.save(b8);
            sesion.save(b9);
            sesion.save(b10);
            sesion.save(b11);
            sesion.save(b12);
            sesion.save(b13);

            // Localidades
            sesion.save(l1);
            sesion.save(l2);
            sesion.save(l3);
            sesion.save(l4);
            sesion.save(l5);
            sesion.save(l6);
            sesion.save(l7);
            sesion.save(l8);
            sesion.save(l9);
            sesion.save(l10);
            sesion.save(l11);
            sesion.save(l12);
            sesion.save(l13);

            // Provincias
            sesion.save(prov1);
            sesion.save(prov2);
            sesion.save(prov3);
            sesion.save(prov4);
            sesion.save(prov5);
            sesion.save(prov6);
            sesion.save(prov7);
            sesion.save(prov8);
            sesion.save(prov9);
            sesion.save(prov10);
            sesion.save(prov11);
            sesion.save(prov12);
            sesion.save(prov13);
            sesion.save(prov14);
            sesion.save(prov15);
            sesion.save(prov16);
            sesion.save(prov17);
            sesion.save(prov18);
            sesion.save(prov19);
            sesion.save(prov20);
            sesion.save(prov21);
            sesion.save(prov22);

            // Pais (Argentina)
            sesion.save(p1);

        sesion.getTransaction().commit();
    }
    
    public void cargarEmplados()
    {
        Empleado emp=new Empleado();//
        emp.setApellido("Pizarro");//
        emp.setNombre("Nestor Fabian");//
        emp.setLegajo(53);//
        emp.setNroDoc("22697030");//
        emp.setCuil("20-22697030-5");
        TipoDocumento td=new TipoDocumento();
        td.setNombre("DNI");
        td.setDescripcion("");
        emp.setTipoDoc(td);
        Date d=new Date();
        d.setTime(894665);
        emp.setFechadeNac(d);
        emp.setFechaAlta(d);
        Domicilio dom=new Domicilio();
        //Barrio b=new Barrio();
       // b.setNombre("la concha d tu vieja");
        dom.setCalle("Av. Salvador");
        dom.setNumero(372);
        emp.setDomicilio(dom);
////////////////////////////////////////////
        TipoEspecialidad te= new TipoEspecialidad();
        te.setNombre("Soldador");
        TipoEspecialidad te2= new TipoEspecialidad();
        te2.setNombre("Chapista");
        RangoEspecialidad rng1= new RangoEspecialidad();
        rng1.setNombre("Principiante");
        RangoEspecialidad rng2= new RangoEspecialidad();
        rng2.setNombre("Medio");
        RangoEspecialidad rng3= new RangoEspecialidad();
        rng3.setNombre("Experto");
        TipoCapacitacion tc= new TipoCapacitacion();
        tc.setNombre("Seguridad 1");
        TipoCapacitacion tc2= new TipoCapacitacion();
        tc2.setNombre("Manejo de Herramienta");
        Especialidad esp1a=new Especialidad(te, rng1,10.0);
        Especialidad esp1b=new Especialidad(te, rng2,12.0);
        Especialidad esp1c=new Especialidad(te, rng3,14.0);
        Especialidad esp2a=new Especialidad(te2, rng1,11.0);
        Especialidad esp2b=new Especialidad(te2, rng2,13.0);
        Especialidad esp2c=new Especialidad(te2, rng3,15.0);
        sesion.beginTransaction();
        sesion.save(td);
        sesion.save(te);
        sesion.save(te2);
        sesion.save(tc);
        sesion.save(tc2);
        sesion.save(rng1);
        sesion.save(rng2);
        sesion.save(rng3);
        sesion.save(esp1a);
        sesion.save(esp1b);
        sesion.save(esp1c);
        sesion.save(esp2a);
        sesion.save(esp2b);
        sesion.save(esp2c);
        sesion.save(dom);
        sesion.save(emp); //
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
        tt1.setNombre("Particular");
        TipoTelefono tt2 = new TipoTelefono();
        tt2.setNombre("Fax");
        TipoTelefono tt3 = new TipoTelefono();
        tt3.setNombre("Celular");
        TipoTelefono tt4 = new TipoTelefono();
        tt4.setNombre("Empresa");        
        sesion.beginTransaction();
        sesion.save(tt1);
        sesion.save(tt2);
        sesion.save(tt3);
        sesion.save(tt4);
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

        EmpresaCliente ec2 = new EmpresaCliente();
        ec2.setRazonSocial("DANONE ARGENTINA S.A.");
        ec2.setCuit("66555670-5");
        ec2.setEmail("atcliente@danone.com.ar");
        ec2.setPaginaWeb("http://www.danone.com.ar");

            Domicilio d3 = new Domicilio();
            d3.setCalle("CÃ¡rcanos");
            d3.setNumero(388);
            d3.setPiso(0);
            d3.setDepto("");
            d3.setCodigoPostal("X5115PDK");

            sesion.beginTransaction();
            Barrio b2 = (Barrio)sesion.get(Barrio.class, 1);
            sesion.getTransaction().commit();
            d3.setBarrio(b2);
            ec2.setDomicilio(d3);

            Telefono tel2 = new Telefono();
            tel2.setNumero("424-5654");
            tel2.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 1)));
            HashSet<Telefono> tels2 = new HashSet<Telefono>();
            tels2.add(tel2);
            ec2.setTelefonos(tels2);

            Planta planta2 = new Planta();
            planta2.setRazonSocial("Planta Gral. RodrÃ­guez");

                Domicilio d4 = new Domicilio();
                d4.setCalle("Alte. Brown");
                d4.setNumero(957);
                d4.setPiso(0);
                d4.setCodigoPostal("B1748KFS");
                d4.setBarrio(b); // Se les olvido esto !!
                planta2.setDomicilio(d4);

                Telefono t10 = new Telefono();
                t10.setNumero("(0237) 485-9000");
                t10.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 2)));
                planta2.addTelefono(t10);

            ArrayList<Planta> aux2 = new ArrayList<Planta>();
            aux2.add(planta2);
            ec2.setPlantas(aux2);

        sesion.beginTransaction();
        sesion.save(tel2);
        sesion.save(t10);
        sesion.save(d4);
        sesion.save(planta2);

        sesion.save(d3);
        sesion.save(ec2);
        sesion.getTransaction().commit();

    }

    public void cargarContactoResponsable(){
        ContactoResponsable contacto = new ContactoResponsable();
        contacto.setNombre("AndrÃ©s");
        contacto.setEmail("apedraza@gmail.com");
        Telefono t = new Telefono();
        t.setNumero("(0351) 4564478");
        t.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 1)));
        sesion.beginTransaction();
        sesion.save(t);
        sesion.save(contacto);
        sesion.getTransaction().commit();
    }

    private Proveedor cargarProveedor(){

        Proveedor p = new Proveedor();
        if(band==false) //PARCHE DE ALAMBRE: pasa por aqui solo si es la primera vez q usa el metodo
        {
                Proveedor tM = new Proveedor();
                tM.setRazonSocial("Transportes GORDON");
                tM.setCuit("34368455-5");
                tM.setEmail("tgorodn@tg.com.ar");
                tM.setPaginaWeb("http://www.tg.com.ar");
                Rubro rt5 = RubroUtil.getRubro(5);
                ArrayList<Rubro> listaTRubro = new ArrayList<Rubro>();
                    listaTRubro.add(rt5);
                    tM.setConfiabilidad(0.5);
                    tM.setRubros(listaTRubro);

               Proveedor tP = new Proveedor();
                tP.setRazonSocial("La veloz del norte");
                tP.setCuit("34378455-5");
                tP.setEmail("laveloz@lvdn.com.ar");
                tP.setPaginaWeb("http://www.laveloz.com.ar");
                Rubro rt6 = RubroUtil.getRubro(6);
                listaTRubro = new ArrayList<Rubro>();
                    listaTRubro.add(rt6);
                    tP.setConfiabilidad(0.5);
                    tP.setRubros(listaTRubro);

                 Proveedor tA = new Proveedor();
                tA.setRazonSocial("Hotel Hilton");
                tA.setCuit("31378455-5");
                tA.setEmail("hilton@lvdn.com.ar");
                tA.setPaginaWeb("http://www.hilton.com");
                Rubro rt7 = RubroUtil.getRubro(7);
                listaTRubro = new ArrayList<Rubro>();
                    listaTRubro.add(rt7);
                tA.setConfiabilidad(0.5);
                tA.setRubros(listaTRubro);
                sesion.beginTransaction();
                sesion.saveOrUpdate(tM);
                sesion.saveOrUpdate(tP);
                sesion.saveOrUpdate(tA);
                sesion.getTransaction().commit();

            p.setRazonSocial("SIDERUGIA SAN LUIS S.R.L.");
            band=true;
        }
 else
        {p.setRazonSocial("Metalcitos SAN LUIS S.R.L.");}
        p.setCuit("34345555-5");
        p.setEmail("atclientes@ssl.com.ar");
        p.setPaginaWeb("http://www.ssl.com.ar");

            Domicilio d = new Domicilio();
            d.setCalle("Av. Los PlÃ¡tanos");
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
//        OrdenDeCompra oc = new OrdenDeCompra();
//        oc.setEstado(new EstadoOrdenDeCompraPendienteDeRecepcion());
//        oc.setProveedor(cargarProveedor());
//
//        UnidadDeMedida um = null;
//        try {
//            um = (UnidadDeMedida) HibernateUtil.getSession().get(UnidadDeMedida.class, 1);
//        } catch (Exception ex) {
//            Logger.getLogger(DBExamplesLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        DetalleOrdenDeCompra doc = new DetalleOrdenDeCompra();
//        Material m = new Material();
//        m.setUnidadDeMedida(um);
//        m.setNombre("PLACA DE METAL 2X2MTS");
//        RecursoEspecifico re = new RecursoEspecifico();
//        re.setNombre("PLACA DE METAL");
//        re.setDescipcion("Galvanizada");
//
//        ArrayList<RecursoEspecifico> res = new ArrayList<RecursoEspecifico>();
//        res.add(re);
//        m.setRecursos(res);
//        doc.setRecurso(re);
//        doc.setCantidad(12);
//        doc.setPrecio(150);
//
//        List<DetalleOrdenDeCompra> docs = new ArrayList<DetalleOrdenDeCompra>();
//        docs.add(doc);
//        oc.setDetalle(docs);
//
//        DetalleOrdenDeCompra doc1 = new DetalleOrdenDeCompra();
//        Material m1 = new Material();
//        m1.setUnidadDeMedida(um);
//        m1.setNombre("TUERCAS 2 PULGADAS");
//        RecursoEspecifico re1 = new RecursoEspecifico();
//        re1.setNombre("TUERCA");
//        re1.setDescipcion("REFORZADA");
//
//        ArrayList<RecursoEspecifico> res1 = new ArrayList<RecursoEspecifico>();
//        res1.add(re1);
//        m1.setRecursos(res1);
//        doc1.setRecurso(re1);
//        doc1.setCantidad(12);
//        doc1.setPrecio(150);
//
//        docs.add(doc1);
//        oc.setDetalle(docs);
//        oc.setFechaDeGeneracion(new Date());
//        oc.setFechaDePedido(new Date());
//        oc.setHib_flag_estado("modelo.EstadoOrdenDeCompraGenerada");
//        oc.setFechaDeRecepcion(new Date(02122010));
//
//        FormaDePago fp = new FormaDePago();
//        fp.setNombre("Efectivo");
//        oc.setFormaDePago(fp);
//
//        sesion.beginTransaction();
//        sesion.save(m);
//        sesion.save(m1);
//        sesion.save(re);
//        sesion.save(re1);
//        sesion.save(doc);
//        sesion.save(doc1);
//        sesion.save(fp);
//        sesion.save(oc);
//        sesion.getTransaction().commit();
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

        UnidadDeMedida um = null;
        try {
            um = (UnidadDeMedida) HibernateUtil.getSession().get(UnidadDeMedida.class, 1);
        } catch (Exception ex) {
            Logger.getLogger(DBExamplesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        h.setUnidadDeMedida(um);

        HerramientaDeEmpresa he1 = new HerramientaDeEmpresa();
        he1.setNroSerie("AK900094343");
        RecursoEspecifico re1 = new RecursoEspecifico();
        re1.setNombre("CNC");
        Herramienta h1 = new Herramienta();
        h1.setNombre("FRESADORA");
        h1.getRecursos().add(re1);
        he1.setRecursoEsp(re1);
        h1.setUnidadDeMedida(um);
        sesion.beginTransaction();

        sesion.save(re);
        sesion.save(h);
        sesion.save(he);

        sesion.save(re1);
        sesion.save(h1);
        sesion.save(he1);

        sesion.getTransaction().commit();
    }

    private void cargarCotizacionEjemplo() {
        try {
            PedidoObra po = new PedidoObra();
            po.setNombre("Provision de Mano de Obra y Materiales para adecuacion electrica del sistema de envio de salvado a amasadora horizontal 5. ");
            po.setDescripcion("Todas las tareas a desarrollar se haran de acuerdo a las normas de higiene y seguridad imperantes en la planta y proveera de un cerramiento para aislar el Area de trabajo.");
            GregorianCalendar dummy = new GregorianCalendar(2011, 3, 15);
            po.setFechaInicio(new Date(dummy.getTimeInMillis()));
            dummy.set(2011, 9, 15);
            po.setFechaFin(new Date(dummy.getTimeInMillis()));
            po.setPresupuestoMaximo(3000);
            po.setFormaPago((FormaDePago) sesion.load(FormaDePago.class,2));
            po.setPliego("Pliego n° 84834838");

            // -------     COTIZACIONES     --------
            Cotizacion cot = new Cotizacion();
            cot.setNroRevision(1);
            cot.setNroCotizacion("P0000-0000000A");
            cot.setDescripcion("Primer cotizacion que se le entrega al cliente. No tiene en cuenta descuentos");
            cot.setFechaCreacion(new Date());
            dummy.set(2011, 2, 13);
            cot.setFechaLimiteEntrega(dummy.getTime());
            cot.setFechaModificacion(null);
            cot.setLugarEntrega("Planta. Seccion Amazadoras");
            cot.setPlazoEntrega("15 dÃ­as");
            dummy.set(2011, 2, 20);
            cot.setValidezOferta(dummy.getTime());

            // ------- SUBOBRAS -------
                // SUBOBRA 1
                SubObra so1 = new SubObra();
                so1.setNombre("Desconexionado de la tolva");
                so1.setDescripcion("");
                
                    // Agrego una herramienta
                    SubObraXHerramienta soxh = new SubObraXHerramienta();
                    soxh.setHerramienta((HerramientaDeEmpresa)sesion.load(HerramientaDeEmpresa.class,1));
//                    soxh.setCantDias(2);
                    soxh.setCantHoras(8);
                    soxh.setCostoXHora(30);
                    soxh.setObservaciones("Llevar repuestos");
                    so1.addHerramienta(soxh);

                    // Agrego una Tarea
                    SubObraXTarea soxt = new SubObraXTarea();
                    soxt.setNombre("Armado estructura");
                    soxt.setTipoTarea((TipoTarea)sesion.load(TipoTarea.class, 1));
                    DetalleSubObraXTarea dsoxt=new DetalleSubObraXTarea();
                    dsoxt.setCantHorasNormales(17.0);
                    dsoxt.setCantHorasAl50(5.0);
                    dsoxt.setCantHorasAl100(4.0);
                    dsoxt.setCostoXHoraNormal(10.0);
                    dsoxt.setCantidadPersonas(2);
                    dsoxt.setEspecialidad((Especialidad)sesion.load(Especialidad.class, 1));
                    //dsoxt.setTipoEspecialidad((TipoEspecialidad)sesion.load(TipoEspecialidad.class, 1));
                    //dsoxt.setRangoEmpleado((RangoEspecialidad)sesion.load(RangoEspecialidad.class, 1));
                    soxt.agreagarDetalle(dsoxt);
                    DetalleSubObraXTarea dsoxt1=new DetalleSubObraXTarea();
                    dsoxt1.setCantHorasNormales(15.0);
                    dsoxt1.setCantHorasAl50(3.0);
                    dsoxt1.setEspecialidad((Especialidad)sesion.load(Especialidad.class, 2));
                    //dsoxt1.setTipoEspecialidad((TipoEspecialidad)sesion.load(TipoEspecialidad.class, 2));
                    dsoxt1.setCantHorasAl100(2.0);
                    dsoxt1.setCostoXHoraNormal(12.0);
                    dsoxt1.setCantidadPersonas(1);
                   // dsoxt1.setRangoEmpleado((RangoEspecialidad)sesion.load(RangoEspecialidad.class, 2));
                    soxt.agreagarDetalle(dsoxt1);

                so1.addTarea(soxt);


                SubObraXTarea soxt1 = new SubObraXTarea();
                soxt1.setNombre("Preparacion de la base");
                soxt1.setTipoTarea((TipoTarea)sesion.load(TipoTarea.class, 2));
                DetalleSubObraXTarea dsoxt2=new DetalleSubObraXTarea();
                dsoxt2.setCantHorasNormales(17.0);
                dsoxt2.setCantHorasAl50(5.0);
                dsoxt2.setCantHorasAl100(4.0);
                dsoxt2.setCostoXHoraNormal(10.0);
                dsoxt2.setCantidadPersonas(3);
                dsoxt2.setEspecialidad((Especialidad)sesion.load(Especialidad.class, 1));
                //dsoxt2.setTipoEspecialidad((TipoEspecialidad)sesion.load(TipoEspecialidad.class, 1));
                //dsoxt2.setRangoEmpleado((RangoEspecialidad)sesion.load(RangoEspecialidad.class, 1));
                soxt1.agreagarDetalle(dsoxt2);
                DetalleSubObraXTarea dsoxt3=new DetalleSubObraXTarea();
                dsoxt3.setCantHorasNormales(14.0);
                dsoxt3.setCantHorasAl50(3.0);
                dsoxt3.setCantHorasAl100(1.0);
                dsoxt3.setCostoXHoraNormal(12.0);
                dsoxt3.setCantidadPersonas(2);
                dsoxt3.setEspecialidad((Especialidad)sesion.load(Especialidad.class, 2));
                //dsoxt3.setTipoEspecialidad((TipoEspecialidad)sesion.load(TipoEspecialidad.class, 2));
                //dsoxt3.setRangoEmpleado((RangoEspecialidad)sesion.load(RangoEspecialidad.class, 2));
                soxt1.agreagarDetalle(dsoxt3);
                

                SubObraXTarea soxt6 = new SubObraXTarea();
                soxt6.setNombre("Pintado de la Estructura");
                soxt6.setTipoTarea((TipoTarea)sesion.load(TipoTarea.class, 2));
                    
                    DetalleSubObraXTarea dsoxt6=new DetalleSubObraXTarea();
                    dsoxt6.setCantHorasNormales(10.0);
                    dsoxt6.setCantHorasAl50(2.0);
                    dsoxt6.setCantHorasAl100(1.0);
                    dsoxt6.setCostoXHoraNormal(10.0);
                    dsoxt6.setCantidadPersonas(1);
                    dsoxt6.setEspecialidad((Especialidad)sesion.load(Especialidad.class, 1));
                    //dsoxt6.setTipoEspecialidad((TipoEspecialidad)sesion.load(TipoEspecialidad.class, 1));
                    //dsoxt6.setRangoEmpleado((RangoEspecialidad)sesion.load(RangoEspecialidad.class, 1));
                    soxt6.agreagarDetalle(dsoxt6);
                             
                so1.addTarea(soxt6);

//                SubObraXMaterial
                //TODO: IMPEDIMENT WITH THE STRUCTURE OF SUBOBRAXMATERIAL
                RecursoXProveedor rxp1 = (RecursoXProveedor)sesion.load(RecursoXProveedor.class, 1);
                SubObraXMaterial soxm = new SubObraXMaterial();
                soxm.setCantidad(44);
                soxm.setDescripcion("PROBANDO");
                soxm.setMaterial(rxp1);
                GestorCotizacionMateriales gestor = new GestorCotizacionMateriales();
                soxm.setPrecioUnitario(gestor.getPrecioMaterial(rxp1.getId(), 44));
                so1.addMaterial(soxm);

                so1.setearGananciaMonto(500.0);
                cot.addSubObra(so1);

                //SUBOBRA 2
                SubObra so2 = new SubObra();
                so2.setNombre("Desmontaje electrico de Molino y posterior montaje de cernidor con sus respectivas seguridades.");
                so2.setDescripcion("");

                SubObraXAdicional soxa = new SubObraXAdicional();
                soxa.setCantDias(1);
                soxa.setCantidad(1);
                soxa.setDescripcion("Hospedaje en hotel 2 estrellas");
                soxa.setPrecioUnitario(120);
                soxa.setTipoAdicional((TipoAdicional)sesion.load(TipoAdicional.class,1));
                so2.addAdicional(soxa);

                SubObraXAlquilerCompra soxac = new SubObraXAlquilerCompra();
                soxac.setCantidad(1);
                soxac.setPrecioUnitario(300);
                soxac.setTipoAlquilerCompra((TipoAlquilerCompra)sesion.load(TipoAlquilerCompra.class,3));
                soxac.setDescripcion("Flete desde Cordoba");
                so2.addAlquilerCompra(soxac);
                so2.setearGananciaPorcentaje(5.0);
                cot.addSubObra(so2);


            // -------     CONTACTOS RESPONSABLES     --------
            ContactoResponsable contacto = new ContactoResponsable();
            contacto.setNombre("Andres");
            contacto.setApellido("Pedraza");
            contacto.setEmail("apedraza@gmail.com");
            contacto.setRol(((RolContactoResponsable)sesion.load(RolContactoResponsable.class,1)));
            Telefono t = new Telefono();
            t.setNumero("(0351) 4564478");
            t.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 1)));
            List<Telefono> telefonos1 = new ArrayList<Telefono>();
            telefonos1.add(t);
            contacto.setTelefonos(telefonos1);

            ContactoResponsable contacto2 = new ContactoResponsable();
            contacto2.setNombre("Julio");
            contacto2.setApellido("Ferreyra");
            contacto2.setEmail("eyjuliooscar@gmail.com");
            contacto2.setRol(((RolContactoResponsable)sesion.load(RolContactoResponsable.class,2)));
            Telefono t2 = new Telefono();
            t2.setNumero("(011) 55544566");
            t2.setTipo(((TipoTelefono)sesion.load(TipoTelefono.class, 1)));
            List<Telefono> telefonos2 = new ArrayList<Telefono>();
            telefonos2.add(t2);
            contacto2.setTelefonos(telefonos2);

            po.addContacto(contacto);
            po.addContacto(contacto2);

            // -------     PLANTA     --------
            po.setPlanta(((Planta)sesion.load(Planta.class,2)));

            // -------     FECHA CREACION     -------
            po.setFechaDeRegistro(new Date());

            // ------- ASIGNO LA COTIZACION AL PEDIDO DE OBRA -----
            po.addCotizaciones(cot);

            // ------- ALTA EN BASE DE DATOS -------

            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().saveOrUpdate(t);
            HibernateUtil.getSession().saveOrUpdate(contacto);
            HibernateUtil.getSession().saveOrUpdate(t2);
            HibernateUtil.getSession().saveOrUpdate(contacto2);

            HibernateUtil.getSession().saveOrUpdate(soxh);
            HibernateUtil.getSession().saveOrUpdate(soxt);
            HibernateUtil.getSession().saveOrUpdate(soxt1);
            HibernateUtil.getSession().saveOrUpdate(soxm);
            HibernateUtil.getSession().saveOrUpdate(so1);
            HibernateUtil.getSession().saveOrUpdate(soxa);
            HibernateUtil.getSession().saveOrUpdate(soxac);
            HibernateUtil.getSession().saveOrUpdate(so2);
            HibernateUtil.getSession().saveOrUpdate(cot);

            HibernateUtil.getSession().saveOrUpdate(po);
            HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getCause().toString());
            
            HibernateUtil.rollbackTransaction();
        }
    }

    private void cargarPlanificacionEjemplo() {


        try {
            HibernateUtil.beginTransaction();

            // ---------------------------------------------------------
            // --------------- COTIZACION MODIFICADA -------------------
            // ---------------------------------------------------------

            Cotizacion cot = (Cotizacion) HibernateUtil.getSession().load(Cotizacion.class, 1);
            Iterator<SubObra> itSubObras = cot.getSubObras().iterator();
            CotizacionModificada cotMod = new CotizacionModificada();
            cotMod.setCotizacionOriginal(cot);
            cotMod.setDescripcion(cot.getDescripcion());
            cotMod.setEstado(cot.getEstado());
            cotMod.setFechaCreacion(cot.getFechaCreacion());
            cotMod.setFechaLimiteEntrega(cot.getFechaLimiteEntrega());
            cotMod.setLugarEntrega(cot.getLugarEntrega());
            cotMod.setNroCotizacion(cot.getNroCotizacion());
            cotMod.setNroRevision(cot.getNroRevision());
            cotMod.setPlazoEntrega(cot.getPlazoEntrega());
            cotMod.setValidezOferta(cot.getValidezOferta());

            while(itSubObras.hasNext()){
                SubObra subObra = itSubObras.next();

                SubObraModificada subObraMod = new SubObraModificada();
                List<SubObraXAdicional> subObraXAdicionalModifs = new ArrayList<SubObraXAdicional>();
                List<SubObraXAlquilerCompra> subObraXAlquilerCompraModifs = new ArrayList<SubObraXAlquilerCompra>();
                List<SubObraXHerramienta> subObraXHerramientaModifs = new ArrayList<SubObraXHerramienta>();
                List<SubObraXMaterial> subObraXMaterialModifs = new ArrayList<SubObraXMaterial>();
                List<SubObraXTarea> subObraXTareaModifs = new ArrayList<SubObraXTarea>();
                   
                subObraMod.setDescripcion(subObra.getDescripcion());
                subObraMod.setearGananciaMonto(subObra.getGananciaMonto());
                subObraMod.setearGananciaPorcentaje(subObra.getGananciaPorcentaje());
                subObraMod.setNombre(subObra.getNombre());
                Iterator<SubObraXAdicional> itSubObraXAdcional = subObra.getAdicionales().iterator();
                
                while(itSubObraXAdcional.hasNext()){
                    SubObraXAdicional subObraXAdicional = itSubObraXAdcional.next();
                    SubObraXAdicionalModif soXAdicionalModif = new SubObraXAdicionalModif();
                    soXAdicionalModif.setOriginal(subObraXAdicional);
                    int aleaCantDias = (int) (Math.random()*subObraXAdicional.getCantDias() + 2);
                    soXAdicionalModif.setCantDias(aleaCantDias);
                    int aleaCantOperarios = (int) (Math.random()*subObraXAdicional.getCantidad() + 3);
                    soXAdicionalModif.setCantidad(aleaCantOperarios);
                    soXAdicionalModif.setDescripcion(subObraXAdicional.getDescripcion());
                    double aleaPrecioUnit = (int) Math.random()*subObraXAdicional.getPrecioUnitario() + 1;
                    soXAdicionalModif.setPrecioUnitario(aleaPrecioUnit);
                    soXAdicionalModif.setTipoAdicional(subObraXAdicional.getTipoAdicional());
                    HibernateUtil.getSession().save(soXAdicionalModif);
                    subObraXAdicionalModifs.add(soXAdicionalModif);
                }
                subObraMod.setAdicionales(subObraXAdicionalModifs);


                Iterator<SubObraXAlquilerCompra> itSubObraXAlquilerCompra = subObra.getAlquileresCompras().iterator();
                while(itSubObraXAlquilerCompra.hasNext()){
                    SubObraXAlquilerCompra subObraXAlquilerCompra = itSubObraXAlquilerCompra.next();
                    SubObraXAlquilerCompraModif subObraXAlquilerCompraModif = new SubObraXAlquilerCompraModif();
                    subObraXAlquilerCompraModif.setOriginal(subObraXAlquilerCompra);
                    int aleaCant = (int) Math.random()*subObraXAlquilerCompraModif.getCantidad() + 10;
                    subObraXAlquilerCompraModif.setCantidad(aleaCant);
                    subObraXAlquilerCompraModif.setDescripcion(subObraXAlquilerCompra.getDescripcion());
                    double aleaPU = (int) Math.random()*subObraXAlquilerCompraModif.getPrecioUnitario() + 10;
                    subObraXAlquilerCompraModif.setPrecioUnitario(aleaPU);
                    subObraXAlquilerCompraModif.setTipoAlquilerCompra(subObraXAlquilerCompra.getTipoAlquilerCompra());
                    HibernateUtil.getSession().save(subObraXAlquilerCompraModif);
                    subObraXAlquilerCompraModifs.add(subObraXAlquilerCompraModif);
                }
                subObraMod.setAlquileresCompras(subObraXAlquilerCompraModifs);

                Iterator<SubObraXHerramienta> itSubObraXHerramienta = subObra.getHerramientas().iterator();
                while(itSubObraXHerramienta.hasNext()){
                    SubObraXHerramienta subObraXHerramienta = itSubObraXHerramienta.next();
                    SubObraXHerramientaModif subObraXHerramientaModif = new SubObraXHerramientaModif();
                    subObraXHerramientaModif.setOriginal(subObraXHerramienta);
//                    int aleaCantDias = (int) (Math.random()*subObraXHerramienta.getCantDias() + 4);
//                    subObraXHerramientaModif.setCantDias(aleaCantDias);
                    int aleaCantHoras = (int) (Math.random()*subObraXHerramienta.getCantHoras() + 10);
                    subObraXHerramientaModif.setCantHoras(aleaCantHoras);
                    subObraXHerramientaModif.setHerramienta(subObraXHerramienta.getHerramienta());
                    subObraXHerramientaModif.setObservaciones(subObraXHerramienta.getObservaciones());
                    HibernateUtil.getSession().save(subObraXHerramientaModif);
                    subObraXHerramientaModifs.add(subObraXHerramientaModif);
                }
                subObraMod.setHerramientas(subObraXHerramientaModifs);

                Iterator<SubObraXMaterial> itSubObraXMaterial = subObra.getMateriales().iterator();
                while(itSubObraXMaterial.hasNext()){
                    SubObraXMaterial subObraXMaterial = itSubObraXMaterial.next();
                    SubObraXMaterialModif subObraXMaterialModif = new SubObraXMaterialModif();
                    subObraXMaterialModif.setOriginal(subObraXMaterial);
                    int aleaCant = (int) (Math.random()*subObraXMaterial.getCantidad() + 15);
                    subObraXMaterialModif.setCantidad(aleaCant);
                    subObraXMaterialModif.setMaterial(subObraXMaterial.getMaterial());
                    double aleaPrecioUnit = (int) Math.random()*subObraXMaterial.getPrecioUnitario() + 3;
                    subObraXMaterialModif.setPrecioUnitario(aleaPrecioUnit);
                    HibernateUtil.getSession().saveOrUpdate(subObraXMaterialModif);
                    subObraXMaterialModifs.add(subObraXMaterialModif);
                }
                subObraMod.setMateriales(subObraXMaterialModifs);

                Iterator<SubObraXTarea> itSubObraXTarea = subObra.getTareas().iterator();
                while(itSubObraXTarea.hasNext()){
                    SubObraXTarea subObraXTarea = itSubObraXTarea.next();
                    SubObraXTareaModif subObraXTareaModif = new SubObraXTareaModif();
                    subObraXTareaModif.setOriginal(subObraXTarea);
                    subObraXTareaModif.setObservaciones(subObraXTarea.getObservaciones());
                    subObraXTareaModif.setNombre(subObraXTarea.getNombre());
                    subObraXTareaModif.setTipoTarea(subObraXTarea.getTipoTarea());
                    List<DetalleSubObraXTareaModif> detalleMods = new ArrayList<DetalleSubObraXTareaModif>();
                    Iterator<DetalleSubObraXTarea> itDetalleSOXT = subObraXTarea.getDetalles().iterator();
                    while(itDetalleSOXT.hasNext()){
                        DetalleSubObraXTarea detalle = itDetalleSOXT.next();
                        DetalleSubObraXTareaModif detalleMod = new DetalleSubObraXTareaModif();
                        detalleMod.setOriginal(detalle);
                        double aleaCantHoras100 = detalle.getCantHorasAl100();
                        detalleMod.setCantHorasAl100(aleaCantHoras100);
                        double aleaCantHoras50 = detalle.getCantHorasAl50();
                        detalleMod.setCantHorasAl50(aleaCantHoras50);
                        double aleaCantHorasNormales = detalle.getCantHorasNormales();
                        detalleMod.setCantHorasNormales(aleaCantHorasNormales);
                        int aleaCantPersona = detalle.getCantidadPersonas();
                        detalleMod.setCantidadPersonas(aleaCantPersona);
                        double aleaCostoXHoraNormal = detalle.getCostoXHoraNormal();
                        detalleMod.setCostoXHoraNormal(aleaCostoXHoraNormal);
                        detalleMod.setEspecialidad(detalle.getEspecialidad());
                        //detalleMod.setRangoEmpleado(detalle.getRangoEmpleado());
                        //detalleMod.setTipoEspecialidad(detalle.getTipoEspecialidad());
                        HibernateUtil.getSession().saveOrUpdate(detalleMod);
                        detalleMods.add(detalleMod);
                    }
                    subObraXTareaModif.setDetallesMod(detalleMods);
                    HibernateUtil.getSession().saveOrUpdate(subObraXTareaModif);
                    subObraXTareaModifs.add(subObraXTareaModif);
                }
                subObraMod.setTareas(subObraXTareaModifs);
                
                subObraMod.setSubObraOriginal(subObra);

                cotMod.getSubObras().add(subObraMod);
                HibernateUtil.getSession().saveOrUpdate(subObraMod);
            }
            HibernateUtil.getSession().saveOrUpdate(cotMod);

                // ---------------------------------------------------------
                // ------------------- PLANIFICACIONXXX --------------------
                // ---------------------------------------------------------

                PedidoObra PO = (PedidoObra)HibernateUtil.getSession().createQuery("from PedidoObra PO where :cID in elements(PO.cotizaciones)").setParameter("cID", cot.getId()).uniqueResult();
                Planificacion planificacion = new Planificacion();
                GregorianCalendar cal = new GregorianCalendar() {};
                cal.setTime(PO.getFechaInicio());
                cal.add(Calendar.DAY_OF_MONTH, 5);
                planificacion.setFechaInicio(cal.getTime());
                cal.setTime(PO.getFechaFin());
                cal.add(Calendar.DAY_OF_MONTH, 5);
                planificacion.setFechaFin(cal.getTime());
                planificacion.setCotizacion(cotMod);
                PO.setPlanificacion(planificacion);

                int idTareaGantt = 1;
                
                GregorianCalendar fechaInicioTarea = new GregorianCalendar();
                fechaInicioTarea.setTime(PO.getFechaInicio());
                Iterator<SubObra> itSubObraMod = cotMod.getSubObras().iterator();
                ArrayList<TareaPlanificacion> tareas = new ArrayList<TareaPlanificacion>();
                while(itSubObraMod.hasNext()){
                    SubObra subObraModificada = itSubObraMod.next();
                    
                    Iterator<SubObraXTarea> itSOXTM = subObraModificada.getTareas().iterator();
                    while(itSOXTM.hasNext()){
                        SubObraXTareaModif soxtm = (SubObraXTareaModif)itSOXTM.next();
                        TareaPlanificacion tarea = new TareaPlanificacion();
                        tarea.setTareaCotizada(soxtm); // by Iuga -> Checkear
                        tarea.setNombre(soxtm.getNombre());
                        tarea.setObservaciones(soxtm.getObservaciones());
                        tarea.setTipoTarea(soxtm.getTipoTarea());
                        tarea.setFechaInicio(fechaInicioTarea.getTime());
                        
                        // Orden en el Gantt ( Va a la DB para q simpre se vea igual
                        tarea.setIdTareaGantt(idTareaGantt);
                        idTareaGantt++;
                        
                        GregorianCalendar fechaFinTarea = fechaInicioTarea;
                        fechaFinTarea.add(Calendar.DAY_OF_MONTH, (int) Math.random()*5+4);
                        tarea.setFechaFin(fechaFinTarea.getTime());
                        fechaInicioTarea.add(Calendar.DAY_OF_MONTH, (int) Math.random()*8-3);
                        
//                        // Probando Subtareas
//                        TareaPlanificacion subTarea = new TareaPlanificacion();
//                        subTarea.setNombre("SubTarea perteneciente a: "+tarea.getNombre());
//                        subTarea.setFechaInicio(new Date());
//                        subTarea.setFechaFin(new Date());
//                        subTarea.setTipoTarea(soxtm.getTipoTarea());
//
//                        tarea.addSubTarea(subTarea);
//                        HibernateUtil.getSession().save(subTarea);
                        
                        //tareas.add(tarea);
                        //HibernateUtil.getSession().saveOrUpdate(tarea);
                    }
                }
                //planificacion.setTareas(tareas);
                HibernateUtil.getSession().saveOrUpdate(planificacion);
                HibernateUtil.getSession().saveOrUpdate(PO);
                HibernateUtil.commitTransaction();
        } catch (Exception ex) {
            System.err.println("ERROR AL CARGAR LOS EJEMPLOS: "+ex.getMessage());
            //HibernateUtil.rollbackTransaction();
        }

    }

    private void cargarTipoTarea() {
        TipoTarea tt1 = new TipoTarea();
        tt1.setNombre("Limpieza y pintura");
        TipoTarea tt2 = new TipoTarea();
        tt2.setNombre("Calibracion");
        TipoTarea tt3 = new TipoTarea();
        tt3.setNombre("Mecanizado");
        TipoTarea tt4 = new TipoTarea();
        tt4.setNombre("Protección de equipo");
        TipoTarea tt5 = new TipoTarea();
        tt5.setNombre("Ensamble");
        TipoTarea tt6 = new TipoTarea();
        tt6.setNombre("Traslado");
        TipoTarea tt7 = new TipoTarea();
        tt7.setNombre("Desarme");
        TipoTarea tt8 = new TipoTarea();
        tt8.setNombre("Mejoras en equipo");
        TipoTarea tt9 = new TipoTarea();
        tt9.setNombre("Armado");
        TipoTarea tt10 = new TipoTarea();
        tt10.setNombre("Prueba");        
        TipoTarea tt11 = new TipoTarea();
        tt11.setNombre("Montaje"); 
        TipoTarea tt12 = new TipoTarea();
        tt12.setNombre("Desmontaje"); 
        TipoTarea tt13 = new TipoTarea();
        tt13.setNombre("General");         
        
        try{
            sesion.beginTransaction();
            sesion.saveOrUpdate(tt1);
            sesion.saveOrUpdate(tt2);
            sesion.saveOrUpdate(tt3);
            sesion.saveOrUpdate(tt4);
            sesion.saveOrUpdate(tt5);
            sesion.saveOrUpdate(tt6);
            sesion.saveOrUpdate(tt7);
            sesion.saveOrUpdate(tt8);
            sesion.saveOrUpdate(tt9);
            sesion.saveOrUpdate(tt10);
            sesion.saveOrUpdate(tt11);
            sesion.saveOrUpdate(tt12);
            sesion.getTransaction().commit();
        }catch(Exception ex) {
            System.out.println(ex.getCause().toString());
            HibernateUtil.rollbackTransaction();
        }
    }

    private void cargarTiposGastosVarios() {
        TipoAdicional ta1 = new TipoAdicional();
        ta1.setNombre("Impuesto");
        TipoAdicional ta2 = new TipoAdicional();
        ta2.setNombre("Viático");
//        TipoAdicional ta3 = new TipoAdicional();
//        ta3.setNombre("Transporte");
        TipoAdicional ta4 = new TipoAdicional();
        ta4.setNombre("Combustible");
        TipoAdicional ta5 = new TipoAdicional();
        ta5.setNombre("Servicio");
        TipoAdicional ta6 = new TipoAdicional();
        ta6.setNombre("Consumible");
        TipoAdicional ta7 = new TipoAdicional();
        ta7.setNombre("Otro");

        try{
            sesion.beginTransaction();
            sesion.saveOrUpdate(ta1);
            sesion.saveOrUpdate(ta2);
//            sesion.saveOrUpdate(ta3);
            sesion.saveOrUpdate(ta4);
            sesion.saveOrUpdate(ta5);
            sesion.saveOrUpdate(ta6);
            sesion.saveOrUpdate(ta7);
            sesion.getTransaction().commit();
        }catch(Exception ex) {
            System.out.println(ex.getCause().toString());
            HibernateUtil.rollbackTransaction();
        }
    }

    private void cargarTipoAlquilerCompra() {
        TipoAlquilerCompra tac1 = new TipoAlquilerCompra();
        tac1.setNombre("Galpón");
        TipoAlquilerCompra tac2 = new TipoAlquilerCompra();
        tac2.setNombre("Grua");
        TipoAlquilerCompra tac3 = new TipoAlquilerCompra();
        tac3.setNombre("Flete");
        TipoAlquilerCompra tac4 = new TipoAlquilerCompra();
        tac4.setNombre("Camión");
        TipoAlquilerCompra tac5 = new TipoAlquilerCompra();
        tac5.setNombre("Transporte");
        TipoAlquilerCompra tac6 = new TipoAlquilerCompra();
        tac6.setNombre("Alquiler de casa");        
        TipoAlquilerCompra tac7 = new TipoAlquilerCompra();
        tac7.setNombre("Hospedaje");        
//        TipoAlquilerCompra tac8 = new TipoAlquilerCompra();
//        tac8.setNombre("Alojamiento");        
        TipoAlquilerCompra tac9 = new TipoAlquilerCompra();
        tac9.setNombre("Boleto de transporte de pasajeros");
        TipoAlquilerCompra tac10 = new TipoAlquilerCompra();
        tac10.setNombre("Montacarga");
        TipoAlquilerCompra tac11 = new TipoAlquilerCompra();
        tac11.setNombre("Ticket de Comida");        
        TipoAlquilerCompra tac12 = new TipoAlquilerCompra();
        tac12.setNombre("Otro");      
        
        try{
            sesion.beginTransaction();
            sesion.saveOrUpdate(tac1);
            sesion.saveOrUpdate(tac2);
            sesion.saveOrUpdate(tac3);
            sesion.saveOrUpdate(tac4);
            sesion.saveOrUpdate(tac5);
            sesion.saveOrUpdate(tac6);
            sesion.saveOrUpdate(tac7);
//            sesion.saveOrUpdate(tac8);
            sesion.saveOrUpdate(tac9);
            sesion.saveOrUpdate(tac10);
            sesion.saveOrUpdate(tac11);
            sesion.saveOrUpdate(tac12);

            sesion.getTransaction().commit();
        }catch(Exception ex) {
            System.out.println(ex.getCause().toString());
            HibernateUtil.rollbackTransaction();
        }
    }

    private void cargarRolesContactoResponsable() {
        
        RolContactoResponsable rcr1 = new RolContactoResponsable();
        rcr1.setNombre("Administrador");
        RolContactoResponsable rcr2 = new RolContactoResponsable();
        rcr2.setNombre("Guardia");
        RolContactoResponsable rcr3 = new RolContactoResponsable();
        rcr3.setNombre("Gerente");
        RolContactoResponsable rcr4 = new RolContactoResponsable();
        rcr4.setNombre("Abogado");
        RolContactoResponsable rcr5 = new RolContactoResponsable();
        rcr5.setNombre("Contratista");
        RolContactoResponsable rcr6 = new RolContactoResponsable();
        rcr6.setNombre("Vendedor");  
        RolContactoResponsable rcr7 = new RolContactoResponsable();
        rcr7.setNombre("Ingeniero");          
        RolContactoResponsable rcr8 = new RolContactoResponsable();
        rcr8.setNombre("Jefe de Área");          
        RolContactoResponsable rcr9 = new RolContactoResponsable();
        rcr9.setNombre("Otro");     
        
        try{
            sesion.beginTransaction();
            sesion.saveOrUpdate(rcr1);
            sesion.saveOrUpdate(rcr2);
            sesion.saveOrUpdate(rcr3);
            sesion.saveOrUpdate(rcr4);
            sesion.saveOrUpdate(rcr5);
            sesion.saveOrUpdate(rcr6);
            sesion.saveOrUpdate(rcr8);
            sesion.getTransaction().commit();
        }catch(Exception ex) {
            System.out.println(ex.getCause().toString());
            HibernateUtil.rollbackTransaction();
        }
    }

    private void cargarUsuarios() 
    {
        User u = new User();
        u.setEstado(User.ESTADO_BAJA);
        u.setUsuario("UsuarioAccesoFull");
        u.setPassword("ece48e107580b03bcef00f85781846191e6be5a2c34aa5191fef78932a39936e");
        u.setUrlFoto("/res/avatares/1.png");
        u.setIsAdmin(true);      
        
        User u2 = new User();
        u2.setEstado(User.ESTADO_ALTA);
        u2.setUsuario("Javier Romero");
        u2.setPassword("532eaabd9574880dbf76b9b8cc00832c20a6ec113d682299550d7a6e0f345e25");
        u2.setUrlFoto("/res/avatares/6.png");
        u2.setIsAdmin(true);      
        
        User u3 = new User();
        u3.setEstado(User.ESTADO_ALTA);
        u3.setUsuario("Aldo Romero");
        u3.setPassword("9c24f45a7ea9e4668ee31dc18bd0a9153f1413ceb3fad18b0a07e16e6a9bc587");
        u3.setUrlFoto("/res/avatares/13.png");
        u3.setIsAdmin(true);          
        
        sesion.beginTransaction();
        sesion.save(u);
        sesion.save(u2);
        sesion.save(u3);
        sesion.getTransaction().commit(); 
    }

    private void cargarStock() {

        ItemComprable ic1 = new ItemComprable(RecursoEspecifico.class,1);
        ItemStock is1 = new ItemStock();
        is1.setCantidad(13);
        is1.setFechaModificacion(new Date());
        is1.setItem(ic1);

        ItemComprable ic2 = new ItemComprable(TipoAlquilerCompra.class,1);
        ItemStock is2 = new ItemStock();
        is2.setCantidad(2);
        is2.setFechaModificacion(new Date());
        is2.setItem(ic2);
        
        ItemComprable ic3 = new ItemComprable(TipoAdicional.class,1);
        ItemStock is3 = new ItemStock();
        is3.setCantidad(3);
        is3.setFechaModificacion(new Date());
        is3.setItem(ic3);        
        try
        {
            sesion.beginTransaction();
            sesion.save(ic1);
            sesion.save(is1);
            sesion.save(ic2);
            sesion.save(is2);
            sesion.save(ic3);
            sesion.save(is3);
            sesion.getTransaction().commit();         
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void cargarOrdenesDeCompra() {
        ItemComprable ic1 = new ItemComprable(RecursoEspecifico.class,3);

        ItemComprable ic2 = new ItemComprable(TipoAlquilerCompra.class,3);
        
        ItemComprable ic3 = new ItemComprable(TipoAdicional.class,3);  
        
        FormaDePago fdp = null;
        Proveedor prov = null;
        try
        {
            sesion.beginTransaction();
            fdp = (FormaDePago)sesion.load(FormaDePago.class,1);
            prov = (Proveedor)sesion.load(Proveedor.class,3);
            sesion.getTransaction().commit();         
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        OrdenDeCompra odc = new OrdenDeCompra();
        odc.setEstado(OrdenDeCompra.ESTADO_PENDIENTE);
        odc.setFechaDeGeneracion(new Date());
        odc.setFechaUltimaModificacion(new Date());
        odc.setFormaDeEntrega(OrdenDeCompra.FORMAS_DE_ENTREGA[0]);
        odc.setFormaDePago(fdp);
        odc.setProveedor(prov);

        
            DetalleOrdenDeCompra doc = new DetalleOrdenDeCompra();
            doc.setCantidad(20);
            doc.setDescripcion("Item 1");
            doc.setItem(ic1);
            doc.setPrecioUnitario(77);
        
            DetalleOrdenDeCompra doc2 = new DetalleOrdenDeCompra();
            doc2.setCantidad(77);
            doc2.setDescripcion("Item 2");
            doc2.setItem(ic2);
            doc2.setPrecioUnitario(123);
        
            DetalleOrdenDeCompra doc3 = new DetalleOrdenDeCompra();
            doc3.setCantidad(53);
            doc3.setDescripcion("Item 3");
            doc3.setItem(ic3);
            doc3.setPrecioUnitario(20);
            
            odc.getDetalle().add(doc);
            odc.getDetalle().add(doc2);
            odc.getDetalle().add(doc3);
        
        try
        {
            sesion.beginTransaction();
            sesion.save(ic1);
            sesion.save(ic2);
            sesion.save(ic3);
            sesion.save(odc);
            sesion.save(doc);
            sesion.save(doc2);
            sesion.save(doc3);
            sesion.getTransaction().commit();         
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}