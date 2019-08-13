package steganopgraphy;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//refer : Extract.png
class Extract 
{
  String vesselImage;
  Extract(String vesselImage) 
  {
    this.vesselImage = vesselImage;
  }
  
  String getFileFromImage()throws Exception
  {
    File img = new File(vesselImage);
    if(!img.exists())
      throw new Exception("Vessel image (" + vesselImage + ") not found");

    //load the vessel image in memory
    BufferedImage bImg = ImageIO.read(img);
    
    //fetch the Raster from the buffered image
    Raster rstr = bImg.getData();
    
    int w = bImg.getWidth();
    int h = bImg.getHeight();
    int i,j;//loop control
    int r,g,b;//band value (a byte)
    int data;//fetched byte
    Header hdr = new Header();//empty one
    boolean flag = true; //loop control
    byte arr[] = new byte[Header.HEADER_LENGTH];
    int q = 0;

    String trgt = "";
    long size = 0;
    FileOutputStream fout = null;
    
    for(j =0; j < h && flag; j++)
    {
      for(i = 0 ; i < w && flag; i++)
      {
        //extract the bands
        r = rstr.getSample(i, j, 0);//RED
        g = rstr.getSample(i, j, 1);//GREEN
        b = rstr.getSample(i, j, 2);//BLUE
        
        //bits from bands
        r = r & 0x7;//3
        g = g & 0x7;//3
        b = b & 0x3;//2
    
        //merge to form a byte
        data = r;
        data = data << 3;
        data = data | g;
        data = data << 2;
        data = data | b;
        
        //first 25 bytes are for header
        if(q < Header.HEADER_LENGTH)
          arr[q++] = (byte)data;
        else
        { //rest for file
          if(q == Header.HEADER_LENGTH)
          {
            try
            {
              size = hdr.getSize(arr);
              trgt = "f:\\" + hdr.getName(arr);
              fout = new FileOutputStream(trgt);
              q++;//to avoid reexecution of this if
            }
            catch(Exception ex)
            {
              throw new Exception("Embedding Not Found/Corrupt"); 
            }
          }//if
          
          fout.write(data);
          size--;
          if(size == 0)
            flag = false;
        }//else
      }//for(i
    }//for(j
    
    fout.close();
    return trgt;
  }

}//Extract
