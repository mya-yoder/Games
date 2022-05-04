//Mya Yoder
//yoder098
//Cell.java
//Instantiates Cell objects
public class Cell {
    private int row; //member variable that holds the row location of the Cell
    private int column; //member variable that hold the column location of the Cell
    private char status; //member variable that holds whether the Cell has a boat and whether it has been guessed

    public Cell(int r, int c, char stat){ //Constructor
        row = r;
        column = c;
        status = stat;
    }

    public void setRow(int newRow){ //setter for row variable
        row = newRow;
    }

    public void setColumn(int newColumn){ //setter for column variable
        column = newColumn;
    }

    public void setStatus(char newStatus){ //setter for status variable
        status = newStatus;
    }

    public int getRow(){ //getter for row variable
        return row;
    }

    public int getColumn(){ //getter for column variable
        return column;
    }

    public char getStatus(){ //getter for status variable
        return status;
    }

    public boolean equals(Cell other){ //checks if two Cells are identical
        return row==other.getRow() && column==other.getColumn();
    }

    public String toString(){ //converts Cell's information to a String
        return "The cell is in row "+row+" and column "+column+". It's status is "+status+".";
    }
}
