import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        FileOutputStream fout1 = new FileOutputStream("C:\\Users\\Hp\\Documents\\XYZ.csv"); 
        PrintStream out1 = new PrintStream(fout1) ;
        out1.append("Time,Source,Destination,Bits,Energy\n");
        
        if ( !f.exists()) {
            System.err.println("file path not specified");
        }
        try {
            String regex1 = "ENERGY:\\s(.*)";
            final Pattern p = Pattern.compile(regex1);
            String regex2 = "[0-9]+{13}";
            final Pattern q = Pattern.compile(regex2);
            String regex3 = "-eT";
            final Pattern r = Pattern.compile(regex3);
            Scanner sc = new Scanner(f);
            BufferedReader br = new BufferedReader(new FileReader(realPath)) ;
            
            long x = 0,y=0;
            int count = 0;
            String nextLine="";
            
            for(String Line; (Line = br.readLine()) != null; ) {
                 // System.out.println(Line);
                  
                    Matcher n = q.matcher(Line);
                    Matcher m = p.matcher(Line);
                    Matcher o = r.matcher(Line);
                   // System.out.println(m.find()+" "+n.find()+" "+o.find());
                    if (m.find() && n.find() && o.find()) {
                       
                       
                       y = Long.parseLong(n.group(0));
                       if(count==0){
                           x = Long.parseLong(n.group(0));
                        }
                      // System.out.println((y-x)+" "+m.group(0)); // whole matched expression
                       String g = (y-x)+" "+m.group(0);
                       out.append(g+"\n") ;
                       String replaceString=g.replaceAll(" ENERGY: -s ",",") ;
                       replaceString=replaceString.replaceAll(" -d ",",") ;
                       replaceString=replaceString.replaceAll(" -b " ,",") ;
                       replaceString=replaceString.replaceAll(" -eT ",",") ;
                       System.out.println(g);
                       out1.append(replaceString+"\n");
                       count++;
                       }
             }
             
             //   sc.close();
             } catch(Exception e) {
                  throw new RuntimeException(e);
             }
    }
}
