
enum BoardState {
  WIN,
  LOSS,
  CONTINUE
}

public class Board {
  Cell[][] cells;
  int numRows;
  int numCols;

  Board(int numRows, int numCols) {
    cells = new Cell[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        cells[i][j] = new Cell();
      }
    }
    this.numRows = numRows;
    this.numCols = numCols;
  }

  public void printCells() {
    for (int i = 0; i < numRows; i++) {
      System.out.print("1: ");

      for (int j = 0; j < numCols; j++) {

        switch (cells[i][j].getCellState()) {
          case HIDDEN:
            System.out.print("[ ]");
            break;

          case FLAG:
            System.out.print("[^]");
            break;

          case REVEALED:
            if (cells[i][j].isMine()) {
              System.out.print("[*]");
            } else {
              System.out.printf("[%d]", cells[i][j].getValue());
            }
            break;
        }
      }
      System.out.println("");
    }
  }

  public void initializeCells(int numMines) {
    placeRandomMines(numMines);
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        calcCellValue(i, j);
      }
    }
  }

  public void makeMove(int row, int col) {
    Cell cell = cells[row][col];

    cells[row][col].setCellState(CellState.REVEALED);

    if (cell.getValue() != 0)
      return;

    for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, numRows - 1); i++) {
      for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, numCols - 1); j++) {
        if ((i == row && j == col) || cells[i][j].getCellState() == CellState.REVEALED)
          continue;

        makeMove(i, j);
      }
    }
  }

  private void calcCellValue(int row, int col) {
    Cell cell = cells[row][col];
    if (cell.isMine())
      return;

    for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, numRows - 1); i++) {
      for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, numCols - 1); j++) {
        if (cells[i][j].isMine()) {
          cell.setValue(cell.getValue() + 1);
        }
      }
    }
  }

  private void placeRandomMines(int numMines) {
    int numRemainingMines = Math.min(numMines, numRows * numCols);
    while (numRemainingMines > 0) {
      int randomRow = generateRandomInt(0, numRows - 1);
      int randomCol = generateRandomInt(0, numCols - 1);
      if (cells[randomRow][randomCol].isMine()) {
        continue;
      }
      cells[randomRow][randomCol].makeMine();
      numRemainingMines--;
    }
  }

  private int generateRandomInt(int min, int max) {
    return (int) Math.floor(Math.random() * (max - min + 1) + min);
  }

  public BoardState checkBoardState() {
    BoardState state = BoardState.WIN;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (cells[i][j].getCellState() == CellState.REVEALED && cells[i][j].isMine()) {
          return BoardState.LOSS;
        }

        if (cells[i][j].getCellState() != CellState.REVEALED && !cells[i][j].isMine()) {
          state = BoardState.CONTINUE;
        }
      }
    }
    return state;
  }
}
