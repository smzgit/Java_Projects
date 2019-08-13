package steganopgraphy;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//refer : Embed.png
class Embed 
{
  String fileToEmbed, vesselImage;
          
  Embed(String fileToEmbed, String vesselImage)
  {
    this.fileToEmbed = fileToEmbed;
    this.vesselImage = vesselImage;
    
  }
  
  void embedFileInImage(String trgtFile) throws Exception
  {
    File src = new File(fileToEmbed);
    File img = new File(vesselImage);
    
    if(!src.exists())
      throw new Exception("File to embed (" + fileToEmbed + ") not found");
    if(!img.exists())
      throw new Exception("Vessel image (" + vesselImage + ") not found");

    //load the image in memory
    BufferedImage bImg = ImageIO.read(img);
    
    //check for the embedding capacity
    int w = bImg.getWidth();
    int h = bImg.getHeight();
    long capacity = (long)w *h;
    long need = src.length();
    
    if((need + Header.HEADER_LENGTH ) > capacity)
      throw new Exception("File size is greater than the embedding capacity of image");

    //get the source name 
    String name = src.getName();//c:\\java\\DB.zip ---> DB.zip
    
    //Form the header using the name and size of the source file
    Header hdr = new Header(name, need);
    
    //get the raster
    WritableRaster wRaster = bImg.getRaster();

    //open the file for reading
    FileInputStream fin = new FileInputStream(src);
    
    //per pixel
    int i, j;
    int r, g, b;
    int data, b1,b2,b3;
    boolean flag = true;
    int q = 0;
    byte arr[] = hdr.getHeader();
    
    for(j =0 ; j < h && flag; j++)
    {
      for(i =0; i < w && flag; i++)
      {
        if(q < Header.HEADER_LENGTH)
        {
          data = arr[q];
          q++;
        }
        else
          //fetch a byte from file to embed
          data = fin.read();
        
        if(data == -1)//EOF
        {//embedding done
          flag  = false;
        }
        else
        {
          //pixel : raster[i][j]
          r = wRaster.getSample(i, j, 0);//RED
          g = wRaster.getSample(i, j, 1);//BLUE
          b = wRaster.getSample(i, j, 2);//GREEN

          
          //split the byte
          b3 = data & 0x3;
          b2 = (data >>2) & 0x7;
          b1 = (data >>5) & 0x7;
          
          //merge
          r = (r & (~0x7)) | b1;
          g = (g & (~0x7)) | b2;
          b = (b & (~0x3)) | b3;
                  
          //update the raster
          wRaster.setSample(i, j, 0, r); //pixel[i][j].band(0) = r
          wRaster.setSample(i, j, 1, g);
          wRaster.setSample(i, j, 2, b);
          
        }
      }//for(i
    }//for(j
    
    //update the raster in buffered image
    bImg.setData(wRaster);
    
    //save back
    File trgt = new File(trgtFile);
    ImageIO.write(bImg, "PNG", trgt);
  }//embedFileInImage
  
}//Embed
