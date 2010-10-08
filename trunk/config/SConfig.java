package config;
/**
 * Singleton para manejar las configuraciones del Sistema
 * Ejemplo: IVA
 * @author Iuga
 */
public class SConfig extends Propiedad {

   private static SConfig instance = null;

   public static SConfig getInstance()
   {
      if(instance == null) {
         instance = new SConfig();
      }
      return instance;
   }

   public double getIVA()
   {
        return getDouble("SISTEMA_IVA");
   }

   public String getNombreSistema()
   {
       return getString("SISTEMA_NOMBRE");
   }

   public String getNombreEmpresa()
   {
       return getString("EMPRESA_NOMBRE");
   }

   public String getDireccionEmpresa()
   {
       return getString("EMPRESA_DIRECCION");
   }

}
