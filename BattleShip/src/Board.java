//Mya Yoder
//yoder098
//Board.java
//Initializes a Board object, creates the appropriate number of Cells
//and randomly places Boats on the board
//Class also includes methods to use actions fire, missile, scanner, and drone,
//print and display the Board, and check if a given coordinate is on the Board
import java.lang.Math; //for random number generation
import java.util.Scanner;
public class Board {
    private Cell[][] cells; //member variable for 2D array of Cell objects on the Board
    private Boat[] boats; //member variable for array of Boat object on the Board
    private int shipsLeft; //member variable for ships left to be sunk on the Board

    public Board(boolean user){ //Constructor
        cells = new Cell[10][10];
        for (int r=0; r<10; r++){ //defaults each Cell status to '-'
            for (int c=0; c<10; c++){
                cells[r][c]=new Cell(r, c, '-'); //creates appropriate number of cells
            }
        }

        boats = new Boat[5];
        shipsLeft = 5;

        if(user)
            userPlace();
        else
            placeBoats(); //randomly places boats on the board
    }

    public int getShipsLeft(){ //getter method for shipsLeft variable
        return shipsLeft;
    }

    public Cell[][] getCells(){ //getter method for cells variable
        return cells;
    }

    public Boat[] getBoats(){ //getter method for boats variable
        return boats;
    }

    public boolean equals(Board other){ //checks if two Board objects are identical
        boolean same = true;
        //ensures boards have the same dimensions
        if(cells.length!=other.getCells().length || cells[0].length!=other.getCells()[0].length)
            return false;
        for(int i=0; i<cells.length; i++){ //checks if each cell status is identical
            for(int j=0; j<cells[0].length; j++){
                if(cells[i][j].getStatus()!=other.getCells()[i][j].getStatus())
                    same = false;
            }
        }
        return same;
    }

    public String toString(){ //converts Board data to a String
        String output=" ";
        for(int i=0; i<cells[0].length; i++){
            output+=" " + i; //column labels
        }
        output+="\n";
        for (int row=0; row<cells.length; row++){
            output+=row + " "; //row labels
            //iterates through each Cell in the variable cells and prints its status
            for (int col=0; col<cells[0].length; col++){
                output+=cells[row][col].getStatus() + " ";
            }
            output+="\n";
        }
        return output;
    }

    //randomly places the correct Boats on the Board
    public void placeBoats(){
        Boat temp;//will hold Boats to be added to the Board
        int x;//will hold the x coordinate/column of the upper left corner of potential boats
        int y;//will hold the y coordinate/row of the upper left corner of potential boats
        boolean horizontal = true;//will hold whether a potential boat is oriented horizontally
        double random;//will hold random number generated to decide orientation of Boat

        //builds int arrays to hold the sizes of each boat to be placed
        //larger sizes are first for easier placement
        int[] boatLen = new int[]{5, 4, 3, 3, 2};

        //iterates through each boat size that needs to be added
        //and adds a boat of that size to the board
        for(int i=0; i<boatLen.length; i++){
            boolean fits = false;//holds whether a potential Boat position is viable
            int[][] location = new int[boatLen[i]][2];//will hold all the cells coordinates a potential Boat inhabits

            //continues until a generated Boat fits on the Board
            while (!fits) {
                random = Math.random();//random number generated to decide orientation of Boat
                if (random > 0.5){//Boat is oriented horizontally 50% of the time
                    horizontal = true;
                    //generates x & y coordinated for the Boat's upper left corner
                    //cells[0].length-boatLen[i]+1 ensures boat will not go over the edge of the Board
                    x = (int) Math.floor(Math.random() * (cells[0].length-boatLen[i]+1));
                    y = (int) Math.floor(Math.random() * cells.length);
                }
                else{//Boat is oriented vertically 50% of the time
                    horizontal = false;
                    //generates x & y coordinated for the Boat's upper left corner
                    //cell.length-boatLen[i]+1 ensures boat will not go over the edge of the Board
                    x = (int) Math.floor(Math.random() * cells[0].length);
                    y = (int) Math.floor(Math.random() * (cells.length-boatLen[i]+1));
                }

                //generates a Cell array of all the cells the potential Boat would inhabit
                location = Boat.cellsNeeded(x, y, boatLen[i], horizontal);
                fits = shipFits(location);//checks to see if all needed Cells are empty
            }

            Cell[] boatCells = new Cell[boatLen[i]];//holds the Cell objects the Boat will inhabit
            for(int j=0; j<location.length; j++){//updates status of all Cells the new Boat inhabits
                cells[location[j][0]][location[j][1]].setStatus('B');
                boatCells[j] = cells[location[j][0]][location[j][1]];//adds needed Cells to Array
            }
            temp = new Boat(boatLen[i], horizontal, boatCells);//creates a new Boat using randomly generated info
            boats[i] = temp;//adds new Boat to Board's boats variable
        }
    }

    public void userPlace(){
        Boat temp;//will hold Boats to be added to the Board
        int x;//will hold the x coordinate/column of the upper left corner of potential boats
        int y;//will hold the y coordinate/row of the upper left corner of potential boats

        //builds int arrays to hold the sizes of each boat to be placed
        //larger sizes are first for easier placement
        int[] boatLen = new int[]{5, 4, 3, 3, 2};

        //iterates through each boat size that needs to be added
        //and adds a boat of that size to the board
        for(int i=0; i<boatLen.length; i++){
            boolean fits = false;//holds whether a potential Boat position is viable
            int[][] location = new int[boatLen[i]][2];//will hold all the cells coordinates a potential Boat inhabits
            boolean horizontal = true;

            //continues until a generated Boat fits on the Board
            while (!fits) {
                System.out.println("You need to place a boat of length "+boatLen[i]);
                System.out.println("Would you like to place it vertically or horizontally?");
                System.out.println("Enter v or h:");
                Scanner scan = new Scanner(System.in);
                String orientation = scan.nextLine();
                while(orientation.equals("v") && orientation.equals("h")){
                    System.out.println("Invalid Response");
                    System.out.println("Enter v or h:");
                    orientation = scan.nextLine();
                }

                if (orientation.equals("h")){//Boat is oriented horizontally
                    while(!fits) {
                        print();
                        System.out.println("At which index would you like to place the boat's upper left corner?");
                        System.out.println("Enter the x coordinate:");
                        x = scan.nextInt();
                        while (x<cells[0].length-boatLen[i] || x<0) {
                            System.out.println("Coordinate Places Boat Off Board");
                            System.out.println("Enter new x coordinate:");
                            x = scan.nextInt();
                        }
                        System.out.println("Enter the y coordinate:");
                        y = scan.nextInt();
                        while (y<0 || y>cells.length) {
                            System.out.println("Coordinate Places Boat Off Board");
                            System.out.println("Enter a new y coordinate:");
                            y = scan.nextInt();
                        }

                        int[][] cellsNeeded = Boat.cellsNeeded(x, y, boatLen[i], true);
                        fits = shipFits(cellsNeeded);
                        if(!fits){
                            System.out.println("This placement overlaps with another boat.");
                            System.out.println("Please choose a new location.");
                        }
                    }
                }
                else{//Boat is oriented vertically
                    while(!fits) {
                        horizontal = false;
                        print();
                        System.out.println("At which index would you like to place the boat's upper left corner?");
                        System.out.println("Enter the x coordinate:");
                        x = scan.nextInt();
                        while (x<cells[0].length || x<0) {
                            System.out.println("Coordinate Places Boat Off Board");
                            System.out.println("Enter new x coordinate:");
                            x = scan.nextInt();
                        }
                        System.out.println("Enter the y coordinate:");
                        y = scan.nextInt();
                        while (y<0 || y>cells.length-boatLen[i]) {
                            System.out.println("Coordinate Places Boat Off Board");
                            System.out.println("Enter a new y coordinate:");
                            y = scan.nextInt();
                        }

                        int[][] cellsNeeded = Boat.cellsNeeded(x, y, boatLen[i], false);
                        fits = shipFits(cellsNeeded);
                        if(!fits){
                            System.out.println("This placement overlaps with another boat.");
                            System.out.println("Please choose a new location.");
                        }
                    }
                }
            }

            Cell[] boatCells = new Cell[boatLen[i]];//holds the Cell objects the Boat will inhabit
            for(int j=0; j<location.length; j++){//updates status of all Cells the new Boat inhabits
                cells[location[j][0]][location[j][1]].setStatus('B');
                boatCells[j] = cells[location[j][0]][location[j][1]];//adds needed Cells to Array
            }
            temp = new Boat(boatLen[i], horizontal, boatCells);//creates a new Boat using randomly generated info
            boats[i] = temp;//adds new Boat to Board's boats variable
        }
    }

    //takes in the cell locations needed for a Boat to be placed
    //and checks to see if all the necessary Cells are empty
    public boolean shipFits(int[][] location){
        boolean fits = true;
        for(int i=0; i<location.length; i++){//iterates through each Cell the Boat would occupy
            //checks to see if the necessary cells are empty
            if(cells[location[i][0]][location[i][1]].getStatus() != '-')
                fits = false;
        }
        return fits;
    }

    public boolean validCord(int x, int y){//checks if a given coordinate is on the Board
        return x>=0 && x<cells[0].length && y>=0 && y<cells.length;
    }

    public void print(){ //prints Board with not hit Boat locations visible
        System.out.print(this);//calls toString()
    }

    public void display(){ //prints Board with only status of guessed cells visible
        System.out.print(" ");
        for(int i=0; i<cells[0].length; i++){
            System.out.print(" " + i);//prints out column numbers
        }
        System.out.println();
        for (int row=0; row<cells.length; row++){
            System.out.print(row + " ");//prints out row numbers
            for (int col=0; col<cells[0].length; col++){
                char status = cells[row][col].getStatus();//accesses Cell's status
                if(status=='H' || status=='M') //checks if cells has been guessed
                    System.out.print(status + " ");
                else
                    System.out.print("-" + " "); //prints general cell if it hasn't been guessed
            }
            System.out.println();
        }
    }


    public String fire(int x, int y){
        String action;
        if(validCord(x,y)){ //checks if coordinate is valid
            if (cells[y][x].getStatus() == '-') { //checks if fired Cell is empty and not guessed yet
                cells[y][x].setStatus('M'); //updates Cell's status to a miss
                action = "Miss!";
            } else if (cells[y][x].getStatus() == 'B') { //checks if fired Cell has a boat and is not guessed yet
                cells[y][x].setStatus('H'); //updates Cell's status to a hit
                action = "Hit!";

                Boat hitBoat = new Boat(); //will hold the Boat object that was fired on
                boolean found = false;
                for (int i = 0; i < boats.length && !found; i++) { //runs through every Boat on the Board
                    for (int j = 0; j < boats[i].getSize() && !found; j++) { //runs through each Cell inhabited by the Boat
                        //checks to see if current Cell is equal to the hit Cell
                        if ((boats[i].getCells()[j].equals(cells[y][x]))){
                            hitBoat = boats[i]; //assigns hitBoat to the current Boat
                            //hitBoat.getCells()[j].setStatus('H'); //updates the cell's status in the Boat's cells array
                            found = true; //exits nested for loop
                        }
                    }
                }

                //checks to see if guess sunk the boat
                boolean sunk = true;
                for (int i = 0; i < hitBoat.getSize(); i++) { //runs through each Cell in the hit Boat
                    if (hitBoat.getCells()[i].getStatus() != 'H') //checks to see if every Cell has been guessed
                        sunk = false;
                }
                if (sunk) {
                    action = "Sunk!";
                    shipsLeft = shipsLeft - 1;
                }
            } else { //executes if fired Cell has already been guessed
                action = "Penalty!";
            }
        }
        else{ //executes if guess is out of bounds
            action = "Penalty!";
        }
        return action;
    }
}
