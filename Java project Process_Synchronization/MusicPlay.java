
import java.io.FileInputStream;
import java.io.InputStream;
import javazoom.jl.player.Player ;


class Speaker{
    
   synchronized void play_Song(InputStream muz,String songName){
        
    try{
        
    
    Player pl = new Player(muz);
    
    String thread_ini = songName.substring(0, 2);
    
     System.out.print(thread_ini+" has entered into Critical-Section\n");
    
   System.out.print(songName+"  \n");
    
    pl.play();
    
    System.out.print(thread_ini+" has left the Critical-Section\n\n");
    
    
    }
        catch(Exception e){ System.out.println("err" + e);}
    }
}

class Thread_songs extends Thread{
    
    String tsongpath,ThreadNAME;
    InputStream muz  ;
    
    Speaker sp ;

    Thread_songs(String name,Speaker spk,String T_name){
        
        try
        {
    tsongpath = name;
    
    sp = spk;
    
    ThreadNAME  = T_name ;
    muz = new FileInputStream(name) ;
    
    start();
        }
        catch(Exception ex)
        {
            System.out.println("Err " + ex);
        }
    }
    
    public void run()
    {
                
        sp.play_Song(muz,ThreadNAME);

    } 
}


public class MusicPlay {

   
    public static void main(String[] args)throws InterruptedException  {
        // TODO code application logic here
          Speaker speaker = new Speaker();
       
       System.out.println("Launching all songs together.\nSpeaker is acquired by only one Thread rest are suspended,"
               + "\nThey acquire it only when other thread is out of Critical-Section\n\n");
       
       Thread_songs t1 = new Thread_songs("J:\\java music\\samples\\Flute.mp3",speaker,"T1 : Plays Flute");
       
       Thread_songs t2 = new Thread_songs("J:\\java music\\samples\\Harp.mp3",speaker,"T2 : Plays Harp");
       
       Thread_songs t3 = new Thread_songs("J:\\java music\\samples\\piano.mp3",speaker,"T3 : Plays Piano");
       
       Thread_songs t4 = new Thread_songs("J:\\java music\\samples\\violin.mp3",speaker,"T4 : Plays Violin");
    }
    
}
