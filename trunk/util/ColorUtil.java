package util;

import java.awt.Color;

/**
 *
 * @author Iuga
 */
public class ColorUtil {
    
  public static String encodeRGB(Color color){
    if(null == color) {
      throw new IllegalArgumentException("NULL_COLOR_PARAMETER_ERROR_2");
    }
    return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
  }
    
}
