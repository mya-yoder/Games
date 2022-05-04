//Mya Yoder
//yoder098
//Game.java
//Contains the main method to run the BattleBoats game
import java.util.Scanner; //allows user input to be read
public class Game {
    public static void main(String[] args){
        int[] size = Game.setUp(); //assigns size to an array holding the dimensions of the Board
        Board board = new Board(size[0], size[1]); //creates a Board object using the dimensions

        boolean debug;
        System.out.println("Enter debug if you would like to enter debug mode. Other inputs will default to normal game play: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if(input.equals("debug")) { //enters debug mode if user enters debug
            debug = true;
            System.out.println("Debug mode initialized");
            System.out.println("There are "+board.getShipsLeft()+" ships on the board.");
            board.print();
        }
        else{ //enters normal play mode if user enters anything other than debug
            debug = false;
            System.out.println("Game will proceed with normal game play");
            System.out.println("There are "+board.getShipsLeft()+" ships on the board.");
            board.display();
        }

        if(debug){
            while(board.getShipsLeft() > 0){ //runs while ships remain
                turn(board);
                System.out.println(board.getShipsLeft()+" ship(s) remain(s).");
                board.print();
            }
        }

        else{
            while(board.getShipsLeft() > 0){ //runs while ships remain
                turn(board);
                System.out.println(board.getShipsLeft()+" ship(s) remain(s).");
                board.display();
            }
        }
        //end of game information
        System.out.println("Congratulations! You sunk all the boats!");
        System.out.println("You used "+board.getTurns()+" turns and "+board.getShots()+" shots.");
    }

    public static int[] setUp(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Battle Ship!");
        System.out.println("How many rows would you like the board to have?");
        System.out.println("Enter an integer from 3 to 10 inclusive: ");
        int rows = scan.nextInt();
        boolean invalid = (rows<3 || rows>10); //checks to see if an invalid integer was entered
        while(invalid){ //prompts user to reenter until a valid number is entered
            System.out.println("Invalid Row Number");
            System.out.println("Enter an integer from 3 to 10 inclusive: ");
            rows = scan.nextInt();
            invalid = (rows<3 || rows>10);
        }

        System.out.println("How many columns would you like the board to have?");
        System.out.println("Enter an integer from 3 to 10 inclusive: ");
        int col = scan.nextInt();
        invalid = (col<3 || col>10); //checks to see if an invalid integer was entered
        while(invalid){ //prompts the user to reenter until a valid number is entered
            System.out.println("Invalid Row Number");
            System.out.println("Enter an integer from 3 to 10 inclusive: ");
            col = scan.nextInt();
            invalid = (col<3 || col>10);
        }
        int[] size = new int[]{rows, col}; //returns an array holding the dimensions of the board
        return size;
    }

    public static void turn(Board board){
        board.setTurns(board.getTurns() + 1); //increases turns counter
        System.out.println();
        System.out.println("Turn "+board.getTurns());
        System.out.println("Which action would you like to execute?");
        System.out.println("Enter fire, missile, drone, or scanner: ");
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        boolean valid = false; //tracks whether user entered a valid input
        while(!valid) {
            if (action.equals("fire")) { //if user wants to fire normally
                valid = true;
                System.out.println("Which coordinate would you like to fire on?");
                System.out.println("Enter the x(column) coordinate followed by the y(row) coordinate: ");
                int x = scan.nextInt();
                int y = scan.nextInt();
                System.out.println(board.fire(x, y)); //calls fire method in Board class and prints resulting message
                board.setShots(board.getShots() + 1); //increases shots counter
            }
            else if (action.equals("missile")) { //if user wants to fire a missile
                valid = true;
                System.out.println("Which coordinate would you like to send a missile to?");
                System.out.println("Enter the x(column) coordinate followed by the y(row) coordinate: ");
                int x = scan.nextInt();
                int y = scan.nextInt();
                //prompts user to reenter coordinates until they choose one on the board
                while (!board.validCord(x,y)) {
                    System.out.println("Invalid Coordinate");
                    System.out.println("Enter the x(column) coordinate followed by the y(row) coordinate: ");
                    x = scan.nextInt();
                    y = scan.nextInt();
                }
                int[] hits = board.missile(x, y); //calls missile method in Board class
                System.out.println("Your missile hit " + hits[0] + " target(s) and sunk " + hits[1] + " ship(s).");
                board.setShots(board.getShots() + 1); //increases shots counter
            }
            else if (action.equals("drone")) { //if user wants to use the drone
                valid = true;
                System.out.println("Would you like to scan a row or column?");
                System.out.println("Enter row or col: ");
                String input = scan.nextLine();
                //prompts user to reenter until they input either "row" or "col"
                while (!(input.equals("row") || input.equals("col"))) {
                    System.out.println("Invalid Response");
                    System.out.println("Enter row or col: ");
                    input = scan.nextLine();
                }
                boolean row;
                if (input.equals("row"))
                    row = true;
                else
                    row = false;
                System.out.println("Which index would you like to scan?");
                int index = scan.nextInt();
                //prompts user to reenter until they input a valid index
                while (!((row && index < board.getCells().length) || (!row && index < board.getCells()[0].length))) {
                    System.out.println("Invalid Index");
                    System.out.println("Which index would you like to scan?");
                    index = scan.nextInt();
                }
                int targets = board.drone(row, index); //calls drone method in Board class
                System.out.println("The drone detected " + targets + " target(s).");
            }
            else if (action.equals("scanner")) { //if user wants to use scanner
                valid = true;
                System.out.println("Which coordinate would you like to scan?");
                System.out.println("Enter the x(column) coordinate followed by the y(row) coordinate: ");
                int x = scan.nextInt();
                int y = scan.nextInt();
                System.out.println(board.scanner(x, y)); //calls scanner method in Board class and prints resulting message
            }
            else{ //executes if user did not enter a valid response
                System.out.println("Invalid Response");
                System.out.println("Enter fire, missile, drone, or scanner: ");
                action = scan.nextLine();
            }
        }
    }
}
