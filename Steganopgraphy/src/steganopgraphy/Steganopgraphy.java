package steganopgraphy;

public class Steganopgraphy 
{
  public static void main(String[] args) 
  {
    try
    {
      //Embed emb = new Embed("c:\\java\\DB.zip", "c:\\java\\Koala.jpg");
      Embed emb = new Embed("c:\\add.exe", "c:\\java\\template.png");
      emb.embedFileInImage("c:\\java\\template1.png");
      
      Extract extr = new Extract("c:\\java\\template1.png");
      String trgt = extr.getFileFromImage();
      System.out.println("File extracted as : " + trgt);
    }
    catch(Exception ex)
    {
      System.out.println("Err: " +ex);
      ex.printStackTrace();
    }
  }
}
