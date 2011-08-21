/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.MessageDigest;

/**
 *
 * @author Iuga
 */
public class HashUtil {
    
    public static final String ENCRYPT_SHA256 = "SHA-256";
    
    public static String encriptarString(String algoritmo, String mensaje)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(mensaje.getBytes());
 
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) 
            {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

           return sb.toString();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
}
