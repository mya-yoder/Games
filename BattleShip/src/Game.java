//Mya Yoder
//Game.java
//Contains the main method to run the BattleBoats game as user vs Computer

import java.util.Scanner; //allows user input to be read

public class Game {
    private static boolean penalty; //tracks whether the user has earned a penalty on previous turn

    public static void turn(Board board){ //allows user to take their turn
        System.out.println();
        Scanner scan = new Scanner(System.in);
        System.out.println("Which coordinate would you like to fire on?");
        System.out.println("Enter the x(column) coordinate followed by the y(row) coordinate: ");
        //reads in x and y coords
        int x = scan.nextInt();
        int y = scan.nextInt();

        String result = board.fire(x, y); //fires on chosen coords
        if(result.equals("Penalty!")) //user earned a penalty
            penalty = true;
        System.out.println(result); //prints message from fire method
    } //end turn method

    public static void main(String[] args){
        Board user = new Board(true); //creates user's board
        Board comp = new Board(false); //creates computer's board
        Computer c = new Computer(); //creates Computer object
        penalty = false;

        System.out.println();
        System.out.println("The Game Will Now Begin");
        System.out.println("Opponent's Board");
        comp.display(); //displays computer's board

        while(comp.getShipsLeft()>0 && user.getShipsLeft()>0){ //runs while ships remain
            if(!penalty) { //user has not earned a penalty, can take a turn
                turn(comp); //user takes their turn
                comp.display(); //displays computer's board after user takes their turn
                if(comp.getShipsLeft()==0) //user sunk all of computer's ships
                    break; //exits while loop
                System.out.println(comp.getShipsLeft()+" ship(s) remain(s).");
                System.out.println();
            }
            else
                penalty = false; //resets penalty

            c.turn(user); //computer takes its turn
            System.out.println("Your Board After Opponent's Turn");
            user.print(); //shows user their board after the computer's turn
        } //end while

        //end of game information
        if(user.getShipsLeft()==0){ //computer sunk all of user's boats
            System.out.println();
            System.out.println("You Lost");
            System.out.println();
            System.out.println("Opponent's Board");
            comp.print(); //shows user whether computer's boats were
        }
        else //user sunk all of computer's boats
            System.out.println("Congratulations! You Won!");
    } //end main method
} //end Game class
