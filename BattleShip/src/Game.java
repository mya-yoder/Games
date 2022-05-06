//Mya Yoder
//yoder098
//Game.java
//Contains the main method to run the BattleBoats game
import java.util.Scanner; //allows user input to be read
public class Game {
    private static boolean penalty;

    public static void main(String[] args){
        Board user = new Board(true);
        Board comp = new Board(false); //creates a Board object
        Computer c = new Computer();
        penalty = false;

        while(comp.getShipsLeft()>0 && user.getShipsLeft()>0){ //runs while ships remain
            if(!penalty)
                turn(comp);
            comp.display();
            System.out.println(comp.getShipsLeft()+" ship(s) remain(s).");
            System.out.println();
            c.turn(user);
            penalty = false;
        }
        //end of game information
        System.out.println("Congratulations! You Won!");
    }

    public static void turn(Board board){
        System.out.println();
        Scanner scan = new Scanner(System.in);
        System.out.println("Which coordinate would you like to fire on?");
        System.out.println("Enter the x(column) coordinate followed by the y(row) coordinate: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        String result = board.fire(x, y);
        if(result.equals("Penalty!"))
            penalty = true;
        System.out.println(result); //calls fire method in Board class and prints resulting message
    }
}
