/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.util;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
/**
 *
 * @author Fran
 */
public class Validaciones 
{
    private static KeyAdapter kaNumeros=(new KeyAdapter()
        {
           
                    @Override
           public  void keyTyped(KeyEvent e)
           {
              char caracter = e.getKeyChar();

              // Verificar si la tecla pulsada no es un digito
              if(((caracter < '0') ||
                 (caracter > '9')) &&
                 (caracter != KeyEvent.VK_BACK_SPACE)&&(caracter != ',')/*&&(caracter != '.')*/)
              {
                  if((caracter == '.'))
                  {
                      e.setKeyChar(',');
                  }
                  else
                  { e.consume(); // ignorar el evento de teclado}
                  } 
              }
           }
        });
        private static KeyAdapter kaNumerosEnteros=(new KeyAdapter()
        {
           
           @Override
           public  void keyTyped(KeyEvent e)
           {
              char caracter = e.getKeyChar();

              // Verificar si la tecla pulsada no es un digito
              if(((caracter < '0') ||
                 (caracter > '9')) &&
                 (caracter != KeyEvent.VK_BACK_SPACE)/*&&(caracter != '.')&&(caracter != ',')*/)
              {
                  e.consume(); // ignorar el evento de teclado}
              }
           }
        });
        
    public static boolean validarNumeroPositivo(String aValidar)
    {
        aValidar=aValidar.replace( ',','.' );
        boolean valido = true;
        if(aValidar.equals(""))
        {
          valido = false;               
        }
        else
        {
           try
           {
               double cant = Double.valueOf(aValidar);
               if(cant<=0)
               {
                 valido = false;                       
               }
           }
           catch(NumberFormatException ex)
           {
             valido = false;
           }
           
        }
        return valido;
    }
     
    /**
     * @return the kaNumerosEnteros
     */
    public static KeyAdapter getKaNumerosEnteros() {
        return kaNumerosEnteros;
    }
    
    public static KeyAdapter getKaNumeros() {
        return kaNumeros;
    }
        
        
    
    
}
