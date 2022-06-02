//Mya Yoder
//Boat.java
//Instantiates Boat objects
//Includes a method to generate all the cell coordinates needed for a Boat with given characteristics

public class Boat {
    private int size; //member variable for length of Boat
    private boolean horizontal; //member variable to determine of Boat is horizontal or vertical
    private Cell[] cells; //member variable to hold Cells the Boat inhabits

    public Boat(int s, boolean h, Cell[] loc){ //Constructor
        size = s;
        horizontal = h;
        cells = loc;
    }

    public Boat(){}; //default constructor

    public void setSize(int newSize){ //setter for size variable
        size = newSize;
    }

    public void setHorizontal(boolean newHorizontal){//setter for horizontal variable
        horizontal = newHorizontal;
    }

    public void setCells(Cell[] newCells){//setter for cells variable
        cells = newCells;
    }

    public int getSize(){//getter for size variable
        return size;
    }

    public boolean getHorizontal(){ //getter for horizontal variable
        return horizontal;
    }

    public Cell[] getCells(){ //getter for cells variable
        return cells;
    }

    public boolean equals(Boat other){ //checks if two Boats are identical
        if(size!=other.getSize()) //ensures Boats are same size, making cells variable same length
            return false;

        boolean same = true;
        for(int i=0; i<cells.length; i++){ //checks that each Cell in cells is identical
            if(!cells[i].equals(other.getCells()[i])) //a not identical cell is found
                same = false; //boats are not identical
        }
        return same;
    } //end equals method

    public String toString(){ //converts Boat's information to a String
        String output="";
        if(horizontal)
            output+="The boat is horizontally placed and is of size "+size;
        else
            output+="The boat is vertically placed and is of size "+size;

        output+="It inhabits the following cells:";
        for(int i=0; i<cells.length; i++){ //iterates through every cell in cells variable
            //lists each cell the boat inhabits
            output+="\nrow: "+cells[i].getRow()+" column: "+cells[i].getColumn();
        }

        return output;
    } //end toString method

    //generates a 2D int array of all cell locations needed to hold a Boat object
    //whose top left corner is at a given x and y location
    public static int[][] cellsNeeded(int x, int y, int size, boolean horizontal){
        int[][] locations = new int[size][2]; //initializes array of the correct size
        int index = 0; //holds current index of locations array

        if(!horizontal){ //creates Cells with same x coordinate
            for(int row=y; row<y+size; row++){ //iterates through necessary rows
                locations[index] = new int[]{row, x};//adds new coordinate to array
                index++; //increases working index in array
            }
        }
        else{ //creates Cells with same y coordinate
            for(int col=x; col<x+size; col++){ //iterates through necessary columns
                locations[index] = new int[]{y, col}; //adds new coordinate to array
                index++; //increases working index in array
            }
        }
        return locations;
    } //end cellsNeeded method
} //end Boat class
