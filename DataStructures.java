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
        FileOutputStream fout2 = new FileOutputStream("C:\\Users\\Hp\\Documents\\XYZ1.csv"); 
        PrintStream out2 = new PrintStream(fout2) ;
        out1.append("Source,Destination,Bits,Energy,PktCount,Time\n");
        out2.append("Source,Destination,Bits,Energy,PktCount,Time\n");
        
        if ( !f.exists()) {
            System.err.println("file path not specified");
        }
        try {
            String regex1 = "ENERGY:(.*)";
            final Pattern p = Pattern.compile(regex1);
            String regex2 = "[0-9]+";
            final Pattern q = Pattern.compile(regex2);
            String regex3 = "-eT|-eR";
            final Pattern r = Pattern.compile(regex3);
            String regex4 = "-eR";
            final Pattern s = Pattern.compile(regex4);
            Scanner sc = new Scanner(f);
            BufferedReader br = new BufferedReader(new FileReader(realPath)) ;
            
            long x = 0,y=0;
            int count = 0;
            String nextLine="";
            
            for(String Line=null; (Line = br.readLine()) != null; ) {
                 // System.out.println(Line);
                  
                    Matcher n = q.matcher(Line);// time
                    Matcher m = p.matcher(Line);// ENERGY
                    Matcher o = r.matcher(Line);// -eT
                    Matcher t = s.matcher(Line);// -eR
                    //System.out.println(m.find()+" "+n.find()+" "+t.find());
                    
                    if (m.find() && n.find() && o.find()) { //transmission
                       
                       
                       y = Long.parseLong(n.group(0));
                       if(count==0){
                           x = Long.parseLong(n.group(0));
                            count++;
                        }
                      // System.out.println((y-x)+" "+m.group(0)); // whole matched expression
                       String g = m.group(0)+" -t "+(y-x);
                       out.append(g+"\n") ;
                       
                       System.out.println(g);
                       if(g.contains("-eR")){
                            String replaceString=g.replaceAll("ENERGY: -s","") ;
                            replaceString=replaceString.replaceAll(" -d ",",") ;
                            replaceString=replaceString.replaceAll(" -b " ,",") ;
                            //replaceString=replaceString.replaceAll(" -eT ",",") ;
                            replaceString=replaceString.replaceAll(" -eR ",",") ;
                            replaceString=replaceString.replaceAll(" -pcR ",",") ;
                            replaceString=replaceString.replaceAll(" -t ",",") ;
                            out2.append(replaceString+"\n");}
                       if(g.contains("-eT")){
                            String replaceString=g.replaceAll("ENERGY: -s","") ;
                            replaceString=replaceString.replaceAll(" -d ",",") ;
                            replaceString=replaceString.replaceAll(" -b " ,",") ;
                            replaceString=replaceString.replaceAll(" -eT ",",") ;
                           // replaceString=replaceString.replaceAll(" -eR ",",") ;
                           replaceString=replaceString.replaceAll(" -pcT ",",") ;
                            replaceString=replaceString.replaceAll(" -t ",",") ;
                            out1.append(replaceString+"\n");}
                      
                       }

                    }//for
  
             } catch(Exception e) {
                  throw new RuntimeException(e);
             }
    }
}
