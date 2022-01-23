import java.util.Scanner;

public class Game {
    public static Scanner keyboard;

    public static void main(String[] args) {
        keyboard = new Scanner(System.in);

        instructions();

        Life configuration = new Life();
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
        System.out.println("Welcome to Conway's Game of Life!");
        System.out.println(String.format("This game uses a grid size of %d by %d \n"
                + "in which each cell can be occupied by an organism or not.", Life.MAX_ROW, Life.MAX_COL));
        System.out.println("The occupied cells change from generation to generation \n"
                + "according to the number of neighbouring cells which are alive.");
    }

    private static boolean userSaysYes() {
        String input = "";

        System.out.print("(y, n)? ");
        input = keyboard.nextLine();

        while (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
            System.out.println();
            System.out.println("Respond with either y or n");
            System.out.print("(y, n)? ");
            input = keyboard.nextLine();
        }

        System.out.println();

        return input.toLowerCase().equals("y");
    }

}
