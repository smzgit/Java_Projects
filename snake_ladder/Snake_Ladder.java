/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_ladder;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Hp
 */
class Players{
    String name;
    int position;
    
    Players(){
    position = 1;}
    }
class MakeBoard{
    private final int[] board = new int[100] ;
          
    MakeBoard(){
    for(int i = 0; i<board.length;i++){
        board[i] = i+1;
     }
    
    }
    void showBoard(){
    for (int i : board){
     System.out.print(i+" ");
     if(i%10==0){System.out.println();} }
        }
    
    
    void showSnakes_Ladders(){
    System.out.println("\n Snakes \t Ladders\n");
    System.out.println(" 99-26 \t\t 2-23");
    System.out.println(" 39-3 \t\t 7-38");
    System.out.println(" 47-20 \t\t 70-90");
    System.out.println(" 89-63 \t\t 68-95");
    System.out.println(" 59-41 \t\t 17-40");
    System.out.println(" 35-5 \t\t 9-25\n");
    }
    
    void checkSnake(Players p){
        int[] from = {99,39,47,89,59,35} ;
         int[] to = {26,3,20,63,41,5} ;
         for(int i = 0;i<from.length;i++){
         if(p.position==from[i]){
         System.out.println("\n Sorry!! You've encountered a snake at"+from[i]+"\n now your current position is"+to[i]);
         p.position = to[i] ;
         break;
          }
        }
        
    }
    void checkLadder(Players p){
        int[] from = {2,7,70,68,17,9} ;
         int[] to = {23,38,90,95,40,25} ;
         for(int i = 0;i<from.length;i++){
         if(p.position==from[i]){
         System.out.println("\n Congrats!! You've encountered a Ladder at"+from[i]+"\n now your current position is"+to[i]);
         p.position = to[i] ;
         break;
          }
        }
        
    }
    
    int playAgain(Players p){
        Random rd1 = new Random();
        System.out.println("Congrtas!! You got Bonus roll........\n") ;
       int diceNum1 = rd1.nextInt(6)+1 ;
       System.out.println(" You got "+diceNum1+" on dice\n");
    if(diceNum1==6){
        p.position = p.position + diceNum1;
     return playAgain(p) ;
     }
    else{
    return diceNum1;}
    }
    
    void play(Players p1,Players p2, Scanner read){
        Random rd = new Random() ;
        int diceNum ;
        int n;
        while(p1.position !=100 || p2.position != 100){
            System.out.println(p1.name+"'s Chance !! Plz press 1 to roll the dice");
            n = read.nextInt();
            if(n==1){
            diceNum = rd.nextInt(6)+1 ;
            System.out.println(" You got "+diceNum+" on dice\n");
            
           if(diceNum==6){
            System.out.println("Wow!! You've hit Six!!");
            if(p1.position+diceNum>100 ){}
            else{
            p1.position = p1.position +diceNum;
          p1.position = p1.position+ playAgain(p1);}
             }
            else{
               if(p1.position+diceNum>100){}
            else{
             p1.position = p1.position + diceNum;
             }
           }
           checkSnake(p1);
           checkLadder(p1);
            System.out.println("\n "+p1.name+" your current position is "+p1.position);
            if(p1.position==100){
            System.out.println("\n "+p1.name+" you've won!!!");break;
            }
            }
            else{
             System.out.println("Invalid Input");
            }
            // second players plays here-------------------
            System.out.println("\n_____________________________________________\n");
            System.out.println(p2.name+"'s Chance !! Plz press 1 to roll the dice");
            n = read.nextInt();
            if(n==1){
            diceNum = rd.nextInt(6)+1 ;
            System.out.println(" You got "+diceNum+" on dice\n");
            
           if(diceNum==6){
            System.out.println("Wow!! You've hit Six!!");
            if(p2.position+diceNum>100){}
            else{
            p2.position = p2.position +diceNum;
          p2.position = p2.position+ playAgain(p2);}
             }
            else{
               if(p2.position+diceNum>100){}
            else{
             p2.position = p2.position + diceNum;
             }
           }
           checkSnake(p2);
           checkLadder(p2);
           System.out.println("\n "+p2.name+" your current position is "+p2.position);
             if(p2.position==100){
            System.out.println("\n "+p2.name+" you've won!!!");break;
            }
            }
            else{
             System.out.println("Invalid Input");
            }
           System.out.println("\n_____________________________________________\n");
        }
   
    }
}
public class Snake_Ladder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner read = new Scanner(System.in) ;
        System.out.println("\t\t Welcome to Snake and Ladder game !!!\n");
        MakeBoard b = new MakeBoard();
        b.showBoard();
        b.showSnakes_Ladders();
        
        Players p1 = new Players() ;
        Players p2 = new Players() ;
        System.out.println("Enter first player's name :");
        p1.name = read.next() ;
        System.out.println("\nEnter second player's name :");
        p2.name = read.next() ;
        b.play(p1, p2, read);
    }
    
}
