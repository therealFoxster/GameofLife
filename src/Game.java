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
        boolean invalidDimensions = true;
        while (invalidDimensions) {
            System.out.print("Enter the grid dimensions (Format: \"rows cols\"; leave empty for default dimensions (60 20)): ");

            maxRow = 0;
            maxCol = 0;

            String input = keyboard.nextLine();
            String[] dimensions = input.split(" ", 0);

            if (input.isEmpty()) {
                maxRow = 60; maxCol = 20;
                invalidDimensions = false;
            } else if (dimensions.length != 2) {
                invalidInput("dimensions");
            } else {
                maxRow = Integer.parseInt(dimensions[0]);
                maxCol = Integer.parseInt(dimensions[1]);

                if (maxRow <= 0 || maxCol <= 0) {
                    invalidInput("dimensions");
                } else {
                    invalidDimensions = false;
                }
            }
        }

        System.out.printf("\nThis game uses a grid size of %d by %d in which each cell can be occupied by an organism or not.%n", maxRow, maxCol);
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

    protected static void invalidInput(String inputType) {
        System.out.printf("[X] Invalid %s. Please try again.\n", inputType);
    }

}
