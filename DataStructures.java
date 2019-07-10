import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class DataStructures {

    

    public static void main(String[] args) throws IOException {
        //movies = new ArrayList<movie>();
       String realPath = "C:\\Users\\Hp\\Downloads\\unetsim-1.3\\logs\\log-0.txt";
        
        File f = new File(realPath);
       
        FileOutputStream fout = new FileOutputStream("C:\\Users\\Hp\\Desktop\\MyLog.txt"); 
        PrintStream out = new PrintStream(fout) ;
        
        if ( !f.exists()) {
            System.err.println("file path not specified");
        }
        try {
            String regex1 = "ENERGY:\\s(.*)";//"[0-9]+\|INFO\|MyHalfDuplexModem@[0-9]+:println\|ENERGY:";
            final Pattern p = Pattern.compile(regex1);
            String regex2 = "[0-9]+";//"[0-9]+\|INFO\|MyHalfDuplexModem@[0-9]+:println\|ENERGY:";
            final Pattern q = Pattern.compile(regex2);
            String regex3 = "-eT";//"[0-9]+\|INFO\|MyHalfDuplexModem@[0-9]+:println\|ENERGY:";
            final Pattern r = Pattern.compile(regex3);
            Scanner sc = new Scanner(f);
            long x = 0,y=0;
            int count = 0;
             
                while (sc.hasNextLine()) {
                   
                    String nextLine = sc.nextLine();
                    Matcher n = q.matcher(nextLine);
                    Matcher m = p.matcher(nextLine);
                    Matcher o = r.matcher(nextLine);
                    
                    if (m.find() && n.find() && o.find()) {
                       // System.out.println(n.group(0));
                       y = Long.parseLong(n.group(0));
                       if(count==0){
                           x = Long.parseLong(n.group(0));
                        }
                       System.out.println((y-x)+" "+m.group(0)); // whole matched expression
                       out.append((y-x)+" "+m.group(0)+"\n") ;
                       count++;
                       }
                            
                }  

             //   sc.close();
             } catch(Exception e) {
                  throw new RuntimeException(e);
             }
    }
}
