import java.util.Scanner;

public class Game {
    public static Scanner keyboard;
    protected static int maxRow, maxCol;

    public static void main(String[] args) {
        keyboard = new Scanner(System.in);

        // Display Instructions
        instructions();

        // CREATING AN OBJECT OF LIFE CLASS AND CALLING THE FUNCTIONS.
        Life configuration = new Life(maxRow, maxCol);
        configuration.initialize();
        configuration.print();

        System.out.println("Continue viewing new generations?");
        while (userSaysYes()) {
            configuration.update();
            configuration.print();
            System.out.println("Continue viewing new generations?");
        }

        keyboard.close();
    }

    private static void instructions() {
        System.out.println("WELCOME TO CONWAY'S GAME OF LIFE!");
        System.out.print("Enter the grid dimension for the game (Format: rows cols): ");

        maxRow = 0;
        maxCol = 0;
        do {
            String grid = keyboard.nextLine();
            String[] dimension = grid.split(" ", 0);

            if (dimension.length != 2) {
                System.out.println("Invalid dimension. Please enter again.");
            } else {
                maxRow = Integer.parseInt(dimension[0]);
                maxCol = Integer.parseInt(dimension[1]);
            }

        } while (maxRow <= 0 && maxCol <= 0);

        System.out.printf("This game uses a grid size of %d by %d in which each cell can be occupied by an organism or not.%n", maxRow, maxCol);
        System.out.println("The occupied cells change from generation to generation according to the number of neighbouring cells which are alive.");
    }

    private static boolean userSaysYes() {
        String input;

        System.out.print("(y, n)? ");
        input = keyboard.nextLine();

        while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
            System.out.println();
            System.out.println("Respond with either y or n");
            System.out.print("(y, n)? ");
            input = keyboard.nextLine();
        }

        System.out.println();

        return input.equalsIgnoreCase("y");
    }

}
