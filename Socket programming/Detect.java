
import java.io.File;
import java.util.HashSet;
import java.util.Set;

class Detect
{
    static void getDirectoryInfo(File f , String bullet)
 {
  System.out.println("Directory : "+ f.getAbsolutePath());
  String arr[] = f.list(); //get a String array filled with the names of contents of this directory

  File child;
  for(String content : arr)  
  {
   System.out.println(bullet + " " + content);

   child = new File(f, content);
   if(child.isDirectory())
    getDirectoryInfo(child, " "+ bullet);
  }
  
 }

 public static void main(String args[])
 {
  //Know all the root drives on this system
  File allDrives[] = File.listRoots();
  Set<String> set1 = new HashSet<String>(); 
  Set<String> set2 = new HashSet<String>(); 
     System.out.println("Available Drives in the System :");
  for ( File x : allDrives){
        System.out.println(" "+x);
      set1.add(x.toString()) ;}
  int n = allDrives.length ;
  
  while(true){
      allDrives = File.listRoots();
      if(allDrives.length > n)
          break ;
      
  }
    for( File x : allDrives){
        set2.add(x.toString()) ; }
     System.out.println("\n");
    for(File f : allDrives){
        if(!set1.contains(f.toString())){
            System.out.println("NEW STORAGE DETECTED \n");
            System.out.println("    * Drive: " + f.getAbsolutePath());
            System.out.println("    * TotalSpace: " + f.getTotalSpace()/1024/1024/1024.0+"(GB)");
            System.out.println("    * FreeSpace: " + f.getFreeSpace()/1024/1024/1024.0+"(GB)" + "\n");
            System.out.println("_________________________________________\n");
            getDirectoryInfo(f, "*");

            break ;
        }
    }
    
    
    
 }

}