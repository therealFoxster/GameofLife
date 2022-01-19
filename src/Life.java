
public class Life {
    protected static final int MAX_ROW = 20, MAX_COL = 60; // Grid dimensions
    private int grid[][] = new int[MAX_ROW + 2][MAX_COL + 2];


    private int neighbourCount(int rowIn, int colIn) {
        int row, col;
        int count = 0;

        for (row = rowIn - 1; row <= rowIn + 1; row++) {
            for (col = colIn - 1; col <= colIn + 1; col++) {
                count += grid[row][col]; // Increases the count if the neighbour is alive
            }
        }

        count -= grid[rowIn][colIn]; // Reducing count since the cell is not its own neighbour

        return count;
    }

    public void initialize() {
        int row = 0, col = 0;

        // Initializing grid with blank cells
        for (row = 0; row <= MAX_ROW + 1; row++) {
            for (col = 0; col <= MAX_COL + 1; col++) {
                grid[row][col] = 0;
            }
        }

        System.out.println("List the coordinates for living cells.");
        System.out.println("Terminate the list with the special pair \"-1 -1\".");

        do {
            String input = Game.keyboard.nextLine();
            String[] coordinatesStr = input.split(" ", 0);

            if (coordinatesStr.length != 2) {
                System.out.println("Invalid coordinates. Please try again.");
            }
            else {
                row = Integer.parseInt(coordinatesStr[0]);
                col = Integer.parseInt(coordinatesStr[1]);

                if (row > 1 && row <= MAX_ROW) {
                    if (col > 1 && col <= MAX_COL) {
                        grid[row][col] = 1;
                    } else {
                        System.out.println(String.format("Column %d is out of range.", col));
                    }
                } else if (row != -1) {
                    System.out.println(String.format("Row %d is out of range.", row));
                }
            }
        } while (row != -1 && col != -1);

    }

    public void print() {
        System.out.println("\nThe current life configuration is:");

        for (int row = 0; row <= MAX_ROW; row++) { // Traversing each row
            for (int col = 0; col <= MAX_COL; col++) { // Traversing each column of each row (in other words, each cell)
                if (grid[row][col] == 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(); // Moves to next row
        }
        System.out.println();
    }

    public void update() {
        int row, col;
        int newGrid[][] = new int[MAX_ROW + 2][MAX_COL + 2];

        for (row = 1; row <= MAX_ROW; row++) {
            for (col = 1; col <= MAX_COL; col++) {
                switch (neighbourCount(row, col)) {
                    case 2: newGrid[row][col] = grid[row][col];
                        break;
                    case 3: newGrid[row][col] = 1;
                        break;
                    default: newGrid[row][col] = 0;
                        break;
                }
            }
        }

        // Copying newGrid to grid
        for (row = 1; row <= MAX_ROW; row++) {
            for (col = 1; col <= MAX_COL; col++) {
                grid[row][col] = newGrid[row][col];
            }
        }
    }
}
