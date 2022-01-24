import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Life {
    private final int[][] grid;
    protected int maxRow;
    protected int maxCol;

    public Life(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        this.grid = new int[maxRow + 2][maxCol + 2];
    }

    public void initialize() {
        int row, col;

        // Initializing Grids with Blank Cell.
        for (row = 0; row <= maxRow; row++) {
            for (col = 0; col <= maxCol; col++) {
                grid[row][col] = 0;
            }
        }

        boolean invalidChoice = true;
        while (invalidChoice) {
            // Asking the user if they want to enter the values or read from a file.
            System.out.print("\nDo you want to: \n" +
                    "1. Input the values in the console.\n" +
                    "2. Read from a CSV File.\n" +
                    "3. Generate sample grid with random values\n" +
                    "Enter your choice: ");

            try {
                int choice = Integer.parseInt(Game.keyboard.nextLine());
                invalidChoice = false;
                switch (choice) {
                    case 1:
                        inputValues();
                        break;
                    case 2:
                        readFromCSV();
                        break;
                    case 3:
                        generateRandom();
                        break;
                    default:
                        Game.invalidInput("choice");
                        invalidChoice = true;
                        break;
                }
            } catch (Exception e) {
                Game.invalidInput("choice");
            }
        }

    }

    public void inputValues() {
        int row = 0, col = 0;

        System.out.println("\nList the coordinates for living cells. (Format: row col)");
        System.out.println("Terminate the list with the special pair \"-1 -1\".");

        do {
            String coordinates = Game.keyboard.nextLine();
            String[] cells = coordinates.split(" ", 0);

            if (cells.length != 2) {
                Game.invalidInput("coordinates");
            } else {
                row = Integer.parseInt(cells[0]);
                col = Integer.parseInt(cells[1]);
                insertValuesToGrid(row, col);
            }

        } while (row != -1 && col != -1);

    }

    private void readFromCSV() {
        String filePath;
        String delimiter = ",";
        String coordinates;
        int row, col;

        boolean invalidPath = true;
        while (invalidPath) {
            try {
                // Getting the path from the user.
                System.out.print("\nEnter the path for the CSV file (leave empty for default file path (file/coords.csv)): ");
                filePath = Game.keyboard.nextLine().replace("\\", File.separator);

                if (filePath.isEmpty()) {
                    filePath = "file/coords.csv".replace("\\", File.separator);
                }

                // Parsing the CSV file.
                BufferedReader reader = new BufferedReader(new FileReader(filePath));

                // Skip the header if it exists.
                if (!reader.readLine().equals("row,col")) {
                    while ((coordinates = reader.readLine()) != null) {
                        String[] cells = coordinates.split(delimiter, 0);
                        row = Integer.parseInt(cells[0]);
                        col = Integer.parseInt(cells[1]);
                        insertValuesToGrid(row, col);
                    }
                }

                invalidPath = false;
            } catch (Exception fnf) {
//            System.out.println("EXCEPTION: Check if the file path is correct.");
                Game.invalidInput("file path");
            }//            ex.printStackTrace();

        }

    }

    private void generateRandom() {
        int gridSize = maxRow * maxCol;
        int coordinateCount = (int) Math.floor(Math.random() * (gridSize) + 1);

        for (int coordinates = 0; coordinates < coordinateCount; coordinates++) {
            int row = (int) Math.floor(Math.random() * (maxRow) + 1);
            int col = (int) Math.floor(Math.random() * (maxCol) + 1);
            if ((row != 1 && col != 1)) {
                insertValuesToGrid(row, col);
            }
        }

    }

    private void insertValuesToGrid(int row, int col) {
        if (row > 1 && row <= maxRow) {
            if (col > 1 && col <= maxCol) {
                grid[row][col] = 1;
            } else {
                System.out.printf("Column %d is out of range.%n", col);
            }
        } else if (row != -1) {
            System.out.printf("Row %d is out of range.%n", row);
        }
    }

    public void print() {
        System.out.println("\nThe current life configuration is:");

        for (int row = 0; row <= maxRow; row++) { // Traversing each row
            for (int col = 0; col <= maxCol; col++) { // Traversing each column of each row (in other words, each cell)
                // Creating a number line for the first row.
                if (row == 0) {
                    if (col == 0) {
                        System.out.print("   ");
                    } else {
                        System.out.print(col);
                        if (col < 10) {
                            System.out.print("  ");
                        } else {
                            System.out.print(" ");
                        }
                    }
                    // Creating a number line for the first column.
                } else if (col == 0) {
                    System.out.print(row);
                    if (row < 10) {
                        System.out.print("  ");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    if (grid[row][col] == 1) {
                        System.out.print("#  ");
                    } else {
                        System.out.print(".  ");
                    }
                }
            }
            System.out.println(); // Moves to next row
        }
        System.out.println();
    }

    public void update() {
        int row, col;
        int[][] newGrid = new int[maxRow + 2][maxCol + 2];

        for (row = 1; row <= maxRow; row++) {
            for (col = 1; col <= maxCol; col++) {
                switch (neighbourCount(row, col)) {
                    case 2:
                        newGrid[row][col] = grid[row][col];
                        break;
                    case 3:
                        newGrid[row][col] = 1;
                        break;
                    default:
                        newGrid[row][col] = 0;
                        break;
                }
            }
        }

        // Copying newGrid to grid
        for (row = 1; row <= maxRow; row++) {
            for (col = 1; col <= maxCol; col++) {
                grid[row][col] = newGrid[row][col];
            }
        }
    }

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

}
