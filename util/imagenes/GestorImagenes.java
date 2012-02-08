/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.imagenes;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
    BufferedImage bufferedImagen;//Imagen para la interfaz
    Blob imagenBlob; //Imagen para el gestor y la base de datos
    
    public GestorImagenes()
    {
        bufferedImagen=null;
        imagenBlob=null;
    }        
    
    public void cargarImagenDeArchivo(JPanel panel,JComponent ventana )
    {
       //this.ventana =ventana;
       File archivoImagen=abrirArchivoImagen(new FileChooserImageFilter(), ventana);
       if(archivoImagen!=null)
       {
           String pathImagen= archivoImagen.getAbsolutePath();
           bufferedImagen=leerImageDeArchivo(pathImagen);
           imagenBlob=getImagenBlobDeArrayDeBytes(ArrayDeBytesDeArchivo(archivoImagen));
           if(pathImagen!=null)
           {panel.removeAll();}

           //Cento la imagen
           cargarImagenEnPanel(panel);
           //...
           //Posible Procesamiento Extra
           //...       
           //return pathImagen; 
       }     
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
           panelImagen.setBounds(inicioImagenX, inicioImagenY,bufferedImagen.getWidth() , bufferedImagen.getHeight());
           panel.add(panelImagen);
           panel.repaint();
           panel.setVisible(true);
           return true;
       }
       else
       {return false;}
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
    
    public File abrirArchivoImagen(FileFilter ff, JComponent ventana)
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
    
     private Blob getImagenBlobDeArrayDeBytes(byte[] arrayDeBytes)
     {
        return Hibernate.createBlob(arrayDeBytes);
     }
     
    private byte[] ArrayDeBytesDeArchivo(File archivo)
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
        
    public void setImagenBlob(Blob imagenB)
    {
        imagenBlob=imagenB;  
        if(imagenB!=null)
        {    
            byte[] bytes=toByteArray(imagenBlob);        
            try 
            {  
                InputStream in = new ByteArrayInputStream(bytes);
                bufferedImagen = ImageIO.read(in);
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
    
    
     
  private byte[] toByteArray(Blob fromImageBlob) {
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

