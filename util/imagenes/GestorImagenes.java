/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.imagenes;



import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.hibernate.Hibernate;

/**
 *
 * @author Fran
 */
public class GestorImagenes 
{
    BufferedImage bufferedImagenOriginal;//Guarda la imagen original pero con tamaÃ±o reducido.
    BufferedImage bufferedImagen;//Imagen que se muestra
    Blob imagenBlob; //Imagen para el gestor y la base de datos
    public static final int cargoImagenAjustadaEnAltura=1;
    public static final int cargoImagenAjustadaEnAncho=2;
    public static final int noCargoImagen=0;
    
    public GestorImagenes()
    {
        bufferedImagenOriginal=null;
        imagenBlob=null;
    }        
    
    public int cargarImagenDeArchivo(JPanel panel,JComponent ventana )
    {
       //this.ventana =ventana;
       File archivoImagen=abrirArchivoImagen(new FileChooserImageFilter(), ventana);
       if(archivoImagen!=null)
       {
           String pathImagen= archivoImagen.getAbsolutePath();
           bufferedImagenOriginal=leerImageDeArchivo(pathImagen);           
           
           if(pathImagen!=null)
           {panel.removeAll();}
           
           //Para ajustar a alto o a ancho dependiendo de la proporcion entre la imagen y el panel:
           boolean ajustarA;
           int retorno;
           if(bufferedImagenOriginal.getWidth()>bufferedImagenOriginal.getHeight())
           {
               ajustarA=true;
               retorno=cargoImagenAjustadaEnAltura;
           }
           else
           {
               ajustarA=false;
               retorno=cargoImagenAjustadaEnAncho;
           }
           bufferedImagenOriginal=ajustarTamanoImagenAPanel(panel,bufferedImagenOriginal, ajustarA);
           bufferedImagen=obtenerImagenVisibleEnPanel(bufferedImagenOriginal, panel);
           imagenBlob=aBlob(bufferedImagen);
           cargarImagenEnPanel(panel);
           return retorno;
       }  
       return noCargoImagen;
       
    }
    
    private BufferedImage obtenerImagenVisibleEnPanel(BufferedImage imagen, JPanel panel)
    {
       if(imagen==null)
       {return null;}       
       BufferedImage imagenNueva;
       int largo;
       int alto;
       int x;
       int y;
       if(panel.getHeight()>imagen.getHeight())
       {
           alto=imagen.getHeight();
           y=0;
       }
       else
       {
           alto=panel.getHeight();
           y= (int)((imagen.getHeight()-panel.getHeight())/2);
       }       
       if(panel.getWidth()>imagen.getWidth())
       {
           largo=imagen.getWidth();
           x=0;
       }
       else
       {
           largo=panel.getWidth();
           x= (int)((imagen.getWidth()-panel.getWidth())/2);
       }      
       
       imagenNueva=imagen.getSubimage(x, y, largo, alto);
       return imagenNueva;
    }
    
    public boolean cargarImagenEnPanelAjustandoTamano(JPanel panel, boolean ajustarAAltura) 
    {
       
        bufferedImagen=ajustarTamanoImagenAPanel(panel,bufferedImagenOriginal, ajustarAAltura);
        bufferedImagen=obtenerImagenVisibleEnPanel(bufferedImagen, panel);
        imagenBlob=aBlob(bufferedImagen);
       return cargarImagenEnPanel(panel);
      
    }
      
    public boolean cargarImagenEnPanel(JPanel panel) 
    {
       
       if(bufferedImagen!=null)
       { 
           //Cento la imagen
           PanelImagen panelImagen = new PanelImagen(bufferedImagen, 0, 0);
           int largo=bufferedImagen.getWidth();
           int alto=bufferedImagen.getHeight();
           int centroPanelX=panel.getWidth()/2;
           int centroPanelY=panel.getHeight()/2;
           int inicioImagenX=centroPanelX-(largo/2);
           int inicioImagenY=centroPanelY-(alto/2);           
           //Creo el panel de la imagen y lo inserto en el panel objetivo
           panelImagen.setBounds(inicioImagenX, inicioImagenY,bufferedImagen.getWidth() ,bufferedImagen.getHeight());
           panel.removeAll();
           panel.add(panelImagen);
           panel.repaint();
           panel.setVisible(true);
           return true;
       }
       else
       {return false;}
    }
    
    private BufferedImage ajustarTamanoImagenAPanel(JPanel panel,BufferedImage imagen, boolean ajustarAAltura)
    {
      if(panel==null||imagen ==null)
      {return null;}
      int imgW=imagen.getWidth();
      int imgH=imagen.getHeight();
      int panelH=panel.getHeight();
      int panelW=panel.getWidth();
      if(ajustarAAltura)
      {
         float proporcion=((float)panelH/(float)imgH);
         imgW =(int) (imgW*proporcion);
         imgH =panel.getHeight();
      }
      else
      {
         float proporcion=((float)panelW/(float)imgW);
         imgH =(int) (imgH*proporcion);
         imgW=panel.getWidth();
      }
      return cambiarTamano(imagen, imgW, imgH);
        
    }
    
    private static BufferedImage cambiarTamano(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        
        
        int imageType = img.getType();
        if(imageType == 0) {imageType = 5;}//Workarround para soporte para png
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, imageType);  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }
     private static BufferedImage leerImageDeArchivo(String ref) {  
        BufferedImage bimg = null;  
        try {  
  
            bimg = ImageIO.read(new File(ref));  
        } catch (Exception e) 
        {  
            e.printStackTrace();  
        }  
        return bimg;  
    } 
    
    private File abrirArchivoImagen(FileFilter ff, JComponent ventana)
    {
        JFileChooser fc = new JFileChooser();        
        fc.setFileFilter(ff);
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(ventana);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
          return fc.getSelectedFile();          
        }
        else
        {
            return null;
        }        
    }   
    
     
        
    public void setImagenBlob(Blob imagenB)
    {
        imagenBlob=imagenB;  
        if(imagenB!=null)
        {    
            byte[] bytes=aByteArray(imagenBlob);        
            try 
            {  
                InputStream in = new ByteArrayInputStream(bytes);
                bufferedImagenOriginal = ImageIO.read(in);
                bufferedImagen =bufferedImagenOriginal;
            } catch (Exception e) 
            {  
                e.printStackTrace();  
            } 
        }
    }
    public Blob getImagenBlob()
    {
        return imagenBlob;    
    }
    
   public static byte[] aByteArray(BufferedImage img) throws ImageFormatException, IOException
   {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(img);
		return os.toByteArray();	
    } 
   /*private byte[] aByteArray(BufferedImage image)
    {
        ////////////////////////////////////////////////////////
        WritableRaster raster = image.getRaster();
        DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
        return buffer.getData();
        //////////////////////////////////////////
       /* byte[] byteArray=null;
         try {  
            ByteArrayOutputStream out  = new ByteArrayOutputStream();
            out = new ByteArrayOutputStream();  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);  
            int quality = 5;  
            quality = Math.max(0, Math.min(quality, 100));  
            param.setQuality((float) quality / 100.0f, false);  
            encoder.setJPEGEncodeParam(param);  
            encoder.encode(image);  
            byteArray=out.toByteArray();
            out.close();  
            } 
         catch (Exception e) {  
            e.printStackTrace();
            }
         return byteArray;*/
        ///////////////////////////////////////////
   // }  */ 
  private byte[] aByteArray(Blob fromImageBlob) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      return toByteArrayImpl(fromImageBlob, baos);
    } catch (Exception e) {
    }
    return null;
  }

  private byte[] toByteArrayImpl(Blob fromImageBlob, 
      ByteArrayOutputStream baos) throws SQLException, IOException {
    byte buf[] = new byte[4000];
    int dataSize;
    InputStream is = fromImageBlob.getBinaryStream(); 

    try {
      while((dataSize = is.read(buf)) != -1) {
        baos.write(buf, 0, dataSize);
      }    
    } finally {
      if(is != null) {
        is.close();
      }
    }
    return baos.toByteArray();
  }
  
  private Blob aBlob(BufferedImage imagen)
  {
        if(imagen!=null)
        {    
           byte[] ba=null;
            try{ ba= aByteArray(imagen);}
           catch(Exception e)
           {
               e.printStackTrace();
               //Espero q nunca pase por aca. Recen.
               System.out.println("A esta altura ya estoy podrido de esta mierda de imagenes");
           }
            return aBlob(ba);
        }
        else
        {return null;}
    }
  private Blob aBlob(byte[] arrayDeBytes)
  {
        return Hibernate.createBlob(arrayDeBytes);
  }
     
  private byte[] aArrayDeBytes(File archivo)
  {
        if(archivo!=null)
        {
            byte[] bFile = new byte[(int) archivo.length()];
 
            try 
            {
                 FileInputStream fileInputStream = new FileInputStream(archivo);
                 //convert file into array of bytes
                 fileInputStream.read(bFile);
                 fileInputStream.close();
            } catch (Exception e) {
                 e.printStackTrace();
            }
            return bFile;
        }
        else
        {return null;}        
        
   } 
  
  
    //Panel personalizado en el que serÃ¡ reenderizada la imagen (uso interno unicamente)
    public class PanelImagen extends JPanel
    {  
        private BufferedImage image;  
        int x, y;  
        public PanelImagen(BufferedImage image, int x, int y) 
        {  
            super();  
            this.image = image;  
            this.x = x;  
            this.y = y;  
        }  
        @Override  
        protected void paintComponent(Graphics g) {  
            super.paintComponent(g);  
            g.drawImage(image, x, y, null);  
        }  
    }
}

