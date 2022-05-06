import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;

public class Computer {
    private boolean nearby;
    private Queue<int[]> toGuess;
    int orientation;

    public Computer(){
        nearby = false;
        toGuess = new LinkedList<>();
        orientation = -1;
    }

    public void turn(Board user) {
        if (!nearby) {
            boolean valid = false;
            Cell guessed = null;
            int x = -1;
            int y = -1;
            while (!valid) {
                x = (int) (Math.random() * user.getCells()[0].length);
                y = (int) (Math.random() * user.getCells().length);
                if (user.validCord(x, y)) {
                    guessed = user.getCells()[x][y];
                    valid = guessed.getStatus() == 'B' || guessed.getStatus() == '-';
                }
            }
            user.fire(x, y);
            if (guessed.getStatus() == 'H') {
                nearby = true;
                char status;
                int[] directions = new int[]{0, 1, 2, 3}; //resets the directions array
                for (int i = 4; i > 0; i--) { //iterates through each cardinal direction until an unvisited neighbor is identified
                    int index = (int) Math.floor(Math.random() * i); //randomizes which cardinal direction will be chosen in this loop
                    int direction = directions[index]; //accesses randomly chosen direction
                    switch (direction) { //accesses cell in cardinal direction chosen from current cell
                        case 0: //accesses cell below current cell
                            if (user.validCord(x - 1, y)) {
                                status = user.getCells()[x - 1][y].getStatus();
                                if (status == 'B' || status == '-')
                                    toGuess.add(new int[]{x - 1, y, 0});
                            }
                            break;
                        case 1: //accesses cell to the right of current cell
                            if (user.validCord(x + 1, y)) {
                                status = user.getCells()[x + 1][y].getStatus();
                                if (status == 'B' || status == '-')
                                    toGuess.add(new int[]{x + 1, y, 0});
                            }
                            break;
                        case 2: //accesses cell above current cell
                            if (user.validCord(x, y - 1)) {
                                status = user.getCells()[x][y - 1].getStatus();
                                if (status == 'B' || status == '-')
                                    toGuess.add(new int[]{x, y - 1, 1});
                            }
                            break;
                        case 3: //accesses cell left of current cell
                            if (user.validCord(x, y + 1)) {
                                status = user.getCells()[x][y + 1].getStatus();
                                if (status == 'B' || status == '-')
                                    toGuess.add(new int[]{x, y + 1, 1});
                            }
                            break;
                    }
                    while (index < i - 1) { //removes the chosen direction from the directions array so it isn't chosen again
                        directions[index] = directions[index + 1];
                        index++;
                    }
                }
            }
        }

        else {
            int[] cord = toGuess.remove();
            String result = user.fire(cord[0], cord[1]);
            if (result == "Sunk!") {
                nearby = false;
                toGuess.clear();
                orientation = -1;
            }
            else {
                if (user.getCells()[cord[0]][cord[1]].getStatus() == 'H') {
                    if (orientation == -1) { //orientation has not been determined yet
                        orientation = cord[2];
                        int length = toGuess.size();
                        for (int i = 0; i < length; i++) {
                            int[] temp = toGuess.remove();
                            if (temp[2] == orientation)
                                toGuess.add(temp);
                        }
                    }

                    char status;
                    if (orientation == 0) { //boat is horizontal
                        if (user.validCord(cord[0]-1, cord[1])) {
                            status = user.getCells()[cord[0]-1][cord[1]].getStatus();
                            if(status=='B' || status=='-')
                                toGuess.add(new int[]{cord[0]-1, cord[1], 0});
                        }
                        if(user.validCord(cord[0]+1, cord[1])){
                            status = user.getCells()[cord[0]+1][cord[1]].getStatus();
                            if(status=='B' || status=='-')
                                toGuess.add(new int[]{cord[0]+1, cord[1], 0});
                        }
                    }

                    else { //boat is vertical
                        if (user.validCord(cord[0], cord[1]-1)) {
                            status = user.getCells()[cord[0]][cord[1]-1].getStatus();
                            if(status=='B' || status=='-')
                                toGuess.add(new int[]{cord[0], cord[1]-1, 1});
                        }
                        if(user.validCord(cord[0], cord[1]+1)){
                            status = user.getCells()[cord[0]][cord[1]+1].getStatus();
                            if(status=='B' || status=='-')
                                toGuess.add(new int[]{cord[0], cord[1]+1, 1});
                        }
                    }
                }
            }
        }
    }
}
