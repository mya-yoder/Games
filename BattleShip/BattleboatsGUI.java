//Mya Yoder
//yoder098
//BattleboatsGUI.java
//Creates a user interface for the Battleboats game
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BattleboatsGUI implements ActionListener, MouseListener {
    private final BoardPanel boardPanel;
    private Label message; //removed final so it could be updated
    private Label stats; //removed final so it could be updated
    private final Rectangle[][] rectangles = new Rectangle[10][10];

    private final Board board;
    private boolean revealed; //holds whether boat locations are revealed
    private boolean drone; //tracks whether drone was the last button pressed
    private boolean scanner; //tracks whether scanner was the last button pressed
    private boolean missile; //tracks whether missile was the last button pressed
    private JFrame frame; //added as a class variable so it could be accessed in all methods
    private int[] clicked; //added as a class variable so row and column of clicks could be accessed in additional methods
    private Button r; //added as a class variable so it could be removed from frame in other method
    private Button c; //added as a class variable so it could be removed from frame in other method

    // TODO: Add other necessary class properties such as buttons, labels, and booleans for buttons clicked

    public BattleboatsGUI() {
        //starts by setting all button booleans to false
        revealed = false;
        drone = false;
        scanner = false;
        missile = false;
        board = new Board(10, 10); //creates 10 by 10 board

        frame = new JFrame();
        frame.setSize(600, 600); // the frame can be resized if you would like
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        boardPanel = new BoardPanel(this);
        boardPanel.setBounds(50, 50, 400, 400);
        boardPanel.addMouseListener(this);

		// Initialize the message displayed over the board
        message = new Label("Welcome to Battleboats!");
        message.setBounds(175,15,400,20); // This label can also be moved if you'd like
		
        // Sample button to reveal the board
        Button reveal = new Button("Reveal Board");
        reveal.addActionListener(this);
        reveal.setActionCommand("Reveal");
        reveal.setBounds(400, 500, 100, 50); // you can choose your own button positions

        // TODO: Add a label to keep track of turns, hits, and misses
        stats = new Label("Turns: "+board.getTurns()+"  Hits: "+board.getHits()+"  Misses: "+board.getMisses()+"  Ships Left: "+board.getShipsLeft());
        stats.setBounds(140, 460, 400, 30);

		// TODO: Add a button to use a drone. You may also want to consider adding buttons
		// for selecting whether to scan a row or a column. If you don't want to show these
		// buttons all the time you can use the Button.setVisible(boolean) method to hide these buttons
		// until drone is selected.
        Button drone = new Button("Drone");
        drone.addActionListener(this);
        drone.setActionCommand("Drone");
        drone.setBounds(275, 500, 100, 50);
        
		// TODO: Add a button to fire a missile
        Button missile = new Button("Missile");
        missile.addActionListener(this);
        missile.setActionCommand("Missile");
        missile.setBounds(150, 500, 100, 50);
		
		// TODO: Add a button to use the scanner
        Button scanner = new Button("Scanner");
        scanner.addActionListener(this);
        scanner.setActionCommand("Scanner");
        scanner.setBounds(25, 500, 100, 50);

        // All components must be added to the frame in order to be displayed
        frame.add(message);
        frame.add(boardPanel);
        frame.add(reveal);
        frame.add(stats);
        frame.add(drone);
        frame.add(missile);
        frame.add(scanner);

        // display the frame
        frame.setVisible(true);

        for(int row = 0; row < rectangles.length; row++) {
            for (int col = 0; col < rectangles[0].length; col++) {
				// TODO: Create a new Rectangle(int x, int y, int width, int height) object representing each cell on the board
				// Each rectangle should be 10% of the board panel's dimensions (e.g. 10% of 400 would be 40 pixels)
				// The rectangles are drawn within the JPanel, which causes the x and y position for rectangles to be relative to the panel
				// Keep in mind that the x position should correlate to the column and the y position should correlate to the row
                rectangles[row][col] = new Rectangle(col*40, row*40, 40, 40);
            }
        }
		// Call the repaint method to draw the rectangles for the first time
		// The board panel repaint method calls the repaint method in BattleboatsGUI
        boardPanel.repaint();
    }

    public void repaint(Graphics g) {
        for (int row = 0; row < rectangles.length; row++) {
            for (int col = 0; col < rectangles[0].length; col++) {
				// TODO: use the cell status to decide what color to fill the rectangle
				// it is up to you want colors you want to use, but you will need at least 4
				// one for each possible status and one more for revealed boats
                Rectangle r = rectangles[row][col];
                Color c;
                if(board.getCells()[row][col].getStatus()=='-') //Cells is empty and not guessed
                    c = Color.gray;
                else if(board.getCells()[row][col].getStatus()=='B'){ //Cell contains a Boat but is not guessed
                    if(revealed) //decides whether boat locations should be visible
                        c = Color.blue;
                    else
                        c = Color.gray;
                }
                else if(board.getCells()[row][col].getStatus()=='H') //Cells contains a Boat and is guessed
                    c = Color.green;
                else //executes if status is 'M', does not contain a Boat and is guessed
                    c = Color.orange;


				// TODO: draw a filled in rectangle using the x, y, width, and height of the rectangle
				// You should also draw an unfilled rectangle to make a border around each cell
                g.setColor(c); //draws colored cells
                g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
                g.setColor(Color.black); //draws black outline around cell
                g.drawRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
            }
        }
    }

    public static void main(String[] args) {
		// Creating the GUI instance starts the game
		// Nothing else required in main
        BattleboatsGUI game = new BattleboatsGUI();
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        String actionName = action.getActionCommand();
        int targets;
        // TODO: Implement other button events for missile, drone, and scanner
        switch (actionName) {
            case "Reveal":
                revealed = true; //sets reveal as pressed
                boardPanel.repaint(); //repaints with boat locations revealed
                break;
            // TODO: implement cases for all other buttons
            case "Drone":
                drone = true; //sets drone to last button clicked
                //resets values of scanner and missile buttons
                scanner = false;
                missile = false;
                break;
            case "Scanner":
                scanner = true; //sets scanner as last button clicked
                //resets drone and missile buttons
                drone = false;
                missile = false;
                break;
            case "Missile":
                missile = true; //sets missile as last button clicked
                //resets drone and scanner buttons
                drone = false;
                scanner = false;
                break;
            case "Row": //if Drone button clicked and then Scan Row button clicked
                //calls drone method in Board class to caluclate targets in row
                targets = board.drone(true, clicked[0]);
                message = new Label("The drone detected "+targets+" target(s)");
                message.setBounds(175,15,400,20);
                frame.add(message);
                drone = false; //resets drone button
                //removes Scan Row and Scan Column buttons
                frame.remove(r);
                frame.remove(c);
                break;
            case "Column": //if Drone button and then Scan Column button clicked
                //calls drone method in Board class to calculate targets in column
                targets = board.drone(false, clicked[1]);
                message = new Label("The drone detected "+targets+" target(s)");
                message.setBounds(175,15,400,20);
                frame.add(message);
                drone = false; //resets drone button
                //removes Scan Row and Scan Column buttons
                frame.remove(r);
                frame.remove(c);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int xCoord = mouseEvent.getX();
        int yCoord = mouseEvent.getY();

        int start = board.getShipsLeft(); //holds number of Boats remaining at beginning of turn
        try{frame.remove(message);} //removes message from last turn if there is one
        catch(Exception e){};


        // makes sure the click was inside the board
        if (!boardPanel.contains(xCoord, yCoord)) {
            return;
        }

        // TODO: calculate the row and column position of the click
        int row = yCoord / 40;
        int col = xCoord / 40;

        // TODO:
        //  - Use the other buttons and booleans to determine whether the user wanted to use an ability or just fire
        //  - Run the relevant method on the board object at the given row and col
        //  - Update turns/hits/misses counter and label
        if(missile){ //missile was last button pressed
            message = new Label("You hit "+board.missile(col, row)[0]+" target(s)!");
            message.setBounds(175, 15, 400, 20);
            frame.add(message);
            board.setShots(board.getShots() + 1); //increases shots counter
            missile = false; //resets missile button
        }

        else if(scanner){ //scanner was last button pressed
            message = new Label(board.scanner(col, row)); //sets Label to message returned by scanner method
            message.setBounds(175,15,400,20);
            frame.add(message);
            scanner = false; //resets scanner button
        }

        else if(drone){ //drone was last button pressed
            //adds Row and Column buttons
            r = new Button("Scan Row");
            r.addActionListener(this);
            r.setActionCommand("Row");
            r.setBounds(475, 125, 100, 50);
            frame.add(r);
            c = new Button("Scan Column");
            c.addActionListener(this);
            c.setActionCommand("Column");
            c.setBounds(475, 200, 100, 50);
            frame.add(c);
            clicked = new int[]{row, col}; //tracks location of click to be used with Row and Column buttons
        }

        else{ //no button was pressed
            message = new Label(board.fire(col, row)); //sets Label to message returned by fire method
            message.setBounds(225,15,400,20);
            frame.add(message);
        }

        board.setTurns(board.getTurns()+1); //increase turns counter
        this.boardPanel.repaint();
        //update stats Label
        frame.remove(stats);
        stats = new Label("Turns: "+board.getTurns()+"  Hits: "+board.getHits()+"  Misses: "+board.getMisses()+"  Ships Left: "+board.getShipsLeft());
        stats.setBounds(140, 460, 400, 30);
        frame.add(stats);

        if(start != board.getShipsLeft()){ //if a ship was sunk during the turn
            frame.remove(message);
            message = new Label("You Sunk a Boat!");
            message.setBounds(175, 15, 400, 20);
            frame.add(message);
        }

        if(board.getShipsLeft()==0){ //if there are no Boats remaining
            frame.remove(message);
            message = new Label("Congratulations! You sunk all the ships!");
            message.setBounds(160,15,400,20);
            frame.add(message);
        }

    }


    // TODO: NOTHING!
    //  The remaining methods don't need to filled in, they are just required to be present in the class
    //  because we are implementing MouseListener
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

// TODO: Nothing!
class BoardPanel extends JPanel {
    BattleboatsGUI game;

    public BoardPanel(BattleboatsGUI game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.repaint(g);
    }
}
