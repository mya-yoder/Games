//Mya Yoder
//Computer.java
//Instantiates a Computer object and creates variables necessary for Computer to take its turns
//Includes methods necessary for Computer to guess on user's board and sink their ships

import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;

public class Computer {
    private boolean nearby; //tracks if a boat has been located and is in the process of being sunk
    private Queue<int[]> toGuess; //holds the cells that are possible boat locations
    private int orientation; //tracks the orientation of the boat being sunk, 0:horizontal, 1:vertical, -1:unknown
    private Queue<int[]> boatCells; //holds cells that were hits in process of sinking boat, used when boats are touching
    private boolean multipleShips; //tracks whether program knows multiple boats are touching and is in process of sinking them

    public Computer(){ //Constructor
        nearby = false;
        toGuess = new LinkedList<>();
        orientation = -1;
        boatCells = new LinkedList<>();
        multipleShips = false;
    }

    public void turn(Board user) { //Computer takes its turn
        if (!nearby) { //a random cell should be guessed
            boolean valid = false; //tracks whether the randomized location is a cell that can be guessed
            Cell guessed = null; //holds the cell that was fired on
            int x = -1; //holds the x coord of the cell to be fired on
            int y = -1; //holds the y coord of the cell to be fired on
            while (!valid) { //runs until the randomized coords are a cell that can be fired on
                x = (int) (Math.random() * user.getCells()[0].length); //randomizes x coord
                y = (int) (Math.random() * user.getCells().length); //randomizes y coord
                guessed = user.getCells()[y][x]; //updates guessed to cell at randomized coords
                valid = guessed.getStatus() == 'B' || guessed.getStatus() == '-'; //checks if cell is not guessed
            }

            String result = user.fire(x, y); //fires on chosen cell
            System.out.println("Your opponent fired on " + x + " " + y);
            if (result.equals("Hit!")) { //if the cell contained a boat
                System.out.println("It was a hit.");
                nearby = true; //starts sequence of sinking the boat
                boatCells.clear(); //resets boatCells
                boatCells.add(new int[]{x, y}); //adds fired on cell to identified boat locations
                char status; //will hold status of each nearby cell
                int[] directions = new int[]{0, 1, 2, 3}; //resets the directions array
                for (int i = 4; i > 0; i--) { //iterates through each cardinal direction until an unvisited neighbor is identified
                    int index = (int) Math.floor(Math.random() * i); //randomizes which cardinal direction will be chosen in this loop
                    int direction = directions[index]; //accesses randomly chosen direction
                    switch (direction) { //accesses cell in cardinal direction chosen from current cell
                        case 0: //accesses cell below current cell
                            if (user.validCoord(x, y+1)) { //if there is a cell below the current cell
                                status = user.getCells()[y+1][x].getStatus(); //accesses status of cell
                                if (status == 'B' || status == '-') //if cell is not yet fired on
                                    toGuess.add(new int[]{x, y+1, 1}); //adds cell to list of potential boat locations
                            }
                            break;
                        case 1: //accesses cell to the right of current cell
                            if (user.validCoord(x+1, y)) { //if there is a cell right of the current cell
                                status = user.getCells()[y][x+1].getStatus(); //accesses status of cell
                                if (status == 'B' || status == '-') //if cell is not yet fired on
                                    toGuess.add(new int[]{x+1, y, 0}); //adds cell to list of potential boat locations
                            }
                            break;
                        case 2: //accesses cell above current cell
                            if (user.validCoord(x, y-1)) { //if there is a cell above the current cell
                                status = user.getCells()[y-1][x].getStatus(); //accesses status of cell
                                if (status == 'B' || status == '-') //if cell is not yet fired on
                                    toGuess.add(new int[]{x, y-1, 1}); //adds cell to list of potential boat locations
                            }
                            break;
                        case 3: //accesses cell left of current cell
                            if (user.validCoord(x-1, y)) { //if there is a cell left of the current cell
                                status = user.getCells()[y][x-1].getStatus(); //accesses status of cell
                                if (status == 'B' || status == '-') //if cell is not yet fired on
                                    toGuess.add(new int[]{x-1, y, 0}); //adds cell to list of potential boat locations
                            }
                            break;
                    }
                    while (index < i - 1) { //removes the chosen direction from the directions array so it isn't chosen again
                        directions[index] = directions[index + 1];
                        index++;
                    }
                }
            } //end result.equals("Hit!") if
            else
                System.out.println("It was a miss.");
        } //end !nearby if

        else { //a boat was located and is currently being sunk
            if (toGuess.isEmpty()) { //determined orientation was incorrect, boats are touching
                if (boatCells.size() == 1 && !multipleShips) { //no other boats were identified
                    nearby = false; //go back to random guessing
                    turn(user); //restart turn since no guess was made
                }

                else { //other boats were identified
                    multipleShips = true;
                    if (orientation == 0) //boat was previously thought to be horizontal
                        orientation = 1; //touching boats are vertical
                    else //boat was previously thought to be vertical
                        orientation = 0; //touching boats are horizontal
                    int[] temp = boatCells.remove(); //removes first boat coord from queue
                    addNearbyCells(user, new int[]{temp[0], temp[1], orientation}); //adds adjacent relevant cells to toGuess
                    sink(user); //starts sequence of sinking first boat
                }
            }

            else
                sink(user); //continues sinking boat
        } //end else
    } //end turn method

    public void sink(Board user){
        int[] coord = toGuess.remove(); //accesses coordinate at start of queue
        String result = user.fire(coord[0], coord[1]); //fires on coordinate
        System.out.println("Your opponent fired on " + coord[0] + " " + coord[1]);

        if (result.equals("Sunk!")) { //fired on cell was last boat location
            System.out.println("They sunk one of your ships.");
            toGuess.clear(); //resets queue
            if(boatCells.isEmpty() || !multipleShips) { //there are no other identified boats
                nearby = false; //guesses can go back to random
                orientation = -1; //resets orientation
                multipleShips = false; //resets if all touching boats were sunk
                boatCells.clear(); //resets if boat was sunk and not touching others
            }

            else{ //there are still identified boats that need to be sunk
                int[] temp = boatCells.remove(); //removes next boat coord
                addNearbyCells(user, new int[]{temp[0], temp[1], orientation}); //adds relevant adjacent cells to toGuess
            }
        }

        else { //the boat was not sunk
            if (result.equals("Hit!")) { //cell did contain a boat
                System.out.println("It was a hit.");
                if(!multipleShips) //computer has not determined boats are touching
                    boatCells.add(coord); //adds cell to identified boat locations, in case boats are later determined to be touching
                if (orientation == -1) { //orientation has not been determined
                    orientation = coord[2]; //updates orientation based on direction this cell was from previous guess
                    int length = toGuess.size(); //how many cells are still in the queue
                    for (int i = 0; i < length; i++) { //runs through each cell in the queue
                        int[] temp = toGuess.remove(); //removes cell from queue
                        if (temp[2] == orientation) //if the cell has the correct orientation relative to the initial guess
                            toGuess.add(temp); //cell is added back to the queue
                    }
                }
                addNearbyCells(user, coord); //adds relevant adjacent cells to toGuess
            }

            else
                System.out.println("It was a miss.");
        }
    } //end sink method

    public void addNearbyCells(Board user, int[] coord){
        char status; //status of cell being accessed
        if (orientation == 0) { //boat is horizontal
            if (user.validCoord(coord[0]-1, coord[1])) { //there is a cell left of current cell
                status = user.getCells()[coord[1]][coord[0]-1].getStatus();
                if (status == 'B' || status == '-') //cell is not yet fired on
                    toGuess.add(new int[]{coord[0] - 1, coord[1], 0}); //adds cell to queue
            }
            if (user.validCoord(coord[0] + 1, coord[1])) { //there is a cell right of current cell
                status = user.getCells()[coord[1]][coord[0]+1].getStatus();
                if (status == 'B' || status == '-') //cell is not yet fired on
                    toGuess.add(new int[]{coord[0] + 1, coord[1], 0}); //adds cell to queue
            }
        }

        else { //boat is vertical
            if (user.validCoord(coord[0], coord[1] - 1)) { //there is a cell above current cell
                status = user.getCells()[coord[1]-1][coord[0]].getStatus();
                if (status == 'B' || status == '-') //cell is not yet fired on
                    toGuess.add(new int[]{coord[0], coord[1] - 1, 1}); //adds cell to queue
            }
            if (user.validCoord(coord[0], coord[1] + 1)) { //there is a cell below current cell
                status = user.getCells()[coord[1]+1][coord[0]].getStatus();
                if (status == 'B' || status == '-') //cell is not yet fired on
                    toGuess.add(new int[]{coord[0], coord[1] + 1, 1}); //adds cell to queue
            }
        }
    } //end addNearbyCells method
} //end Computer class
