import java.util.Arrays;
import java.util.Scanner;

enum InputMode {
  DEFAULT,
  FLAG
}

public class Game {
  private Board board;
  private InputMode inputMode;

  Game(int numRows, int numCols, int numMines) {
    this.board = new Board(numRows, numCols);
    this.board.initializeCells(numMines);
    this.inputMode = InputMode.DEFAULT;
  }

  public void play() {
    System.out.println("\n-----------");
    System.out.println("MINESWEEPER");
    System.out.println("-----------");
    board.printCells();

    Scanner scanner = new Scanner(System.in);

    while (true) {
      int[] userInput = getUserMove(scanner);
      int moveRow = userInput[0];
      int moveCol = userInput[1];

      if (inputMode == InputMode.FLAG) {
        board.toggleFlag(moveRow, moveCol);
        board.printCells();
        continue;
      }

      board.makeMove(moveRow, moveCol);
      board.printCells();

      if (board.getBoardState() == BoardState.CONTINUE) {
        continue;
      }
      if (board.getBoardState() == BoardState.LOSS) {
        System.out.println("GAME OVER");
        break;
      }
      if (board.getBoardState() == BoardState.WIN) {
        System.out.println("YOU WIN");
        break;
      }
    }

    scanner.close();
  }

  private void toggleInputMode() {
    if (this.inputMode == InputMode.DEFAULT) {
      this.inputMode = InputMode.FLAG;
      return;
    }
    this.inputMode = InputMode.DEFAULT;
  }

  private int[] getUserMove(Scanner scanner) {
    while (true) {
      System.out.printf("[%s mode] Enter row (0-%d) and column (0-%d), or F to %s flag mode: ", inputMode,
          board.getNumRows() - 1,
          board.getNumCols() - 1, inputMode != InputMode.FLAG ? "enter" : "exit");

      String input = scanner.nextLine();

      if (input.toLowerCase().equals("f")) {
        toggleInputMode();
        continue;
      }

      String[] inputParts = input.split(" ");

      int row = -1;
      try {
        row = Integer.parseInt(inputParts[0]);
        if (row < 0 || row >= board.getNumRows()) {
          throw new Exception();
        }
      } catch (Exception e) {
        System.out.printf("Row must be a whole number between %d and %d, please try again...\n\n", 0,
            board.getNumRows() - 1);
        continue;
      }

      int col = -1;
      try {
        col = Integer.parseInt(inputParts[1]);
        if (col < 0 || col >= board.getNumCols()) {
          throw new Exception();
        }
      } catch (Exception e) {
        System.out.printf("Column must be a whole number between %d and %d, please try again...\n\n", 0,
            board.getNumCols() - 1);
        continue;
      }

      return new int[] { row, col };
    }

    // private int[] getUserInput(Scanner scanner) {
    // while (true) {
    // int row = -1;
    // try {
    // row = Integer.parseInt(scanner.next());
    // if (row <= 0 && row >= board.getNumRows()) {
    // throw new Exception();
    // }
    // } catch (Exception e) {
    // System.out.printf("Row must be a whole number between %d and %d, please try
    // again", 0, board.getNumRows());
    // }

    // int col = -1;
    // try {
    // col = Integer.parseInt(scanner.next());
    // if (col <= 0 && col >= board.getNumRows()) {
    // throw new Exception();
    // }
    // } catch (Exception e) {
    // System.out.printf("Col must be a whole number between %d and %d, please try
    // again", 0, board.getNumCols());
    // }

    // String flag = "";
    // int flagIntValue = 0;
    // try {
    // flag = scanner.next();
    // } catch (Exception e) {
    // }

    // if (flag == "f") {
    // flagIntValue = 1;
    // }

    // return new int[] { row, col, flagIntValue };
    // }

    // private int getUserIntInput(Scanner scanner, int min, int max) {
    // while (true) {
    // try {
    // int input = Integer.parseInt(scanner.nextLine());
    // if (input <= min && input >= max) {
    // throw new Exception();
    // }
    // } catch (Exception e) {
    // System.out.printf("Must be a whole number between %d and %d, please try
    // again: ", min, max);
    // }
    // }
    // }

    // private boolean getUserFlagInput(Scanner scanner, String flag) {
    // while (true) {
    // String input = scanner.nextLine();
    // if (input == flag) {
    // return true;
    // } else if (input == "") {
    // return false;
    // } else {
    // System.out.printf("Press enter to skip or enter %s\n", flag);
    // }
    // }
  }
}
