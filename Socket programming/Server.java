/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;

class Server implements Runnable
{
 ServerSocket port;
 boolean flag;
 Thread connectionThread; 
    DataInputStream din;
 Server(int p) throws Exception
 {
  //open a port
  port = new ServerSocket(p);

  //as port is open, so start taking connections in a new thread
  flag = true;
  connectionThread = new Thread(this);
  connectionThread.start();
 }

 //thread life cycle method that executes concurrently
 public void run()
 {
  try
  {
   while(flag)
    acceptConnection();

   //finally close the port
   port.close();
  }
  catch(Exception ex)
  {
   System.out.println("Err : "+ ex);
  }     

 }//run

 void acceptConnection()
 {
  try
  {
    //wait for a client connection request and on request form a connection
    System.out.println("Waiting for client connection request ...");
    Socket s = port.accept();
    System.out.println("... made a connection !!! ");
    din = new DataInputStream(s.getInputStream());
    String ip = din.readLine() ;
      System.out.println(" Cheater's IP : "+ip);
    //execute the service in a new thread
 //   new ServiceProvider(s);
  } 
  catch(Exception ex)
  {
   System.out.println("Err : " +ex);
  }
 }


 public static void main(String args[])
 {
  try
  {
    Server svr = new Server(9876);
    //... more work for main thread

  }
  catch(Exception ex)
  {
   System.out.println("Err : "+ ex);
  }
  
 }
}//Server

