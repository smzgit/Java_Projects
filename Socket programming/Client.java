
import java.net.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
 Socket svr;
 static DataInputStream din;
 static DataOutputStream dout;

Client(String ip, int port) throws Exception
 {
  //request a connection
  svr = new Socket(ip, port);

  //refer the socket i/o streams
  din = new DataInputStream(svr.getInputStream());
  dout = new DataOutputStream(svr.getOutputStream());

 }

 void process() throws Exception
 {
File allDrives[] = File.listRoots();
  Set<String> set1 = new HashSet<String>(); 
  Set<String> set2 = new HashSet<String>(); 
  
  for ( File x : allDrives){
        set1.add(x.toString()) ;}
  int n = allDrives.length ;
  
  while(true){
      allDrives = File.listRoots();
      if(allDrives.length > n)
          break ;
      
  }
    for( File x : allDrives){
        set2.add(x.toString()) ; }
     dout.writeBytes("\n");
    for(File f : allDrives){
        if(!set1.contains(f.toString())){
            dout.writeBytes("NEW STORAGE DETECTED \n");
            dout.writeBytes("    * Drive: " + f.getAbsolutePath());
            dout.writeBytes("    * TotalSpace: " + f.getTotalSpace()/1024/1024/1024.0+"(GB)");
            dout.writeBytes("    * FreeSpace: " + f.getFreeSpace()/1024/1024/1024.0+"(GB)" + "\n");
            dout.writeBytes("_________________________________________\n");
            getDirectoryInfo(f, "*");

            break ;
        }
    }
   
 }
 void getDirectoryInfo(File f , String bullet) throws IOException
 {
  dout.writeBytes("Directory : "+ f.getAbsolutePath());
  String arr[] = f.list(); //get a String array filled with the names of contents of this directory

  File child;
  for(String content : arr)  
  {
      try {
          dout.writeBytes(bullet + " " + content);
      } catch (IOException ex) {
          Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
      }

   child = new File(f, content);
   if(child.isDirectory())
    getDirectoryInfo(child, " "+ bullet);
  }
  
 }

 void close() throws Exception
 {
   svr.close();
 } 


    public static void main(String[] args) {
        
  try{
    Client clnt = new Client("11.11.3.129", 9876);
   
        InetAddress ip;
        String hostname;
        
        ip = InetAddress.getLocalHost();
           
        Pattern pattern = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
        Matcher matcher = pattern.matcher(ip.toString());
        if (matcher.find())
             dout.writeBytes(matcher.group());
        
       
    clnt.process();
    
    clnt.close();
  }
  catch(Exception ex)
  {
   System.out.println("Err: " +ex);
  }
        
    }
    
}
