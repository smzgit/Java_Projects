/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

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
            
            Scanner sc = new Scanner(f);
     

                while (sc.hasNextLine()) {
                   
                    String nextLine = sc.nextLine();
                    Matcher m = p.matcher(nextLine);
                    if (m.find()) {
                       System.out.println(m.group(0)); // whole matched expression
                       out.append(m.group(0)+"\n") ;
                       }
                            
                }  

             //   sc.close();
             } catch(Exception e) {
                  throw new RuntimeException(e);
             }
    }
}
    

