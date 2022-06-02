//Mya Yoder
//Testing.java
//Includes a main method that creates a randomized Board for the Computer to play on
//Used to observe Computer's actions and guesses

import java.util.Scanner;

public class Testing {
    public static void main(String[] args){
        Board test = new Board(false); //creates randomized Board
        Computer c = new Computer(); //creates Computer object
        test.print(); //prints initial Board
        Scanner scan = new Scanner(System.in);

        while(test.getShipsLeft()>0){ //runs until all boats are sunk
            c.turn(test); //Computer takes its turn
            test.print(); //print board after Computer's turn
            String a = scan.nextLine(); //waits for you to enter something before taking next turn
        }
    } //end main method
} //end Testing class
