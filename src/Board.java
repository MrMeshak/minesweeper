
enum BoardState {
  WIN,
  LOSS,
  CONTINUE
}

public class Board {
  private Cell[][] cells;
  private int numRows;
  private int numCols;
  private BoardState boardState;

  Board(int numRows, int numCols) {
    cells = new Cell[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        cells[i][j] = new Cell();
      }
    }
    this.numRows = numRows;
    this.numCols = numCols;
    this.boardState = BoardState.CONTINUE;
  }

  public void printCells() {
    System.out.println("");
    System.out.print("   ");
    for (int k = 0; k < numCols; k++) {
      System.out.printf(" %d ", k);
    }
    System.out.print("\n\n");

    for (int i = 0; i < numRows; i++) {
      System.out.printf("%d  ", i);

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
    System.out.println("");
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
    revealCellsRecursively(row, col);
    updateBoardState();
  }

  public void toggleFlag(int row, int col) {
    Cell cell = cells[row][col];
    switch (cell.getCellState()) {
      case REVEALED:
        return;
      case HIDDEN:
        cell.setCellState(CellState.FLAG);
        return;
      case FLAG:
        cell.setCellState(CellState.HIDDEN);
        return;
    }
  }

  private void revealCellsRecursively(int row, int col) {
    Cell cell = cells[row][col];

    cells[row][col].setCellState(CellState.REVEALED);

    if (cell.getValue() != 0)
      return;

    for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, numRows - 1); i++) {
      for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, numCols - 1); j++) {
        if ((i == row && j == col) || cells[i][j].getCellState() == CellState.REVEALED)
          continue;

        revealCellsRecursively(i, j);
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

  public void updateBoardState() {
    BoardState state = BoardState.WIN;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (cells[i][j].getCellState() == CellState.REVEALED && cells[i][j].isMine()) {
          this.boardState = BoardState.LOSS;
          return;
        }

        if (cells[i][j].getCellState() != CellState.REVEALED && !cells[i][j].isMine()) {
          state = BoardState.CONTINUE;
        }
      }
    }
    this.boardState = state;
  }

  public int getNumCols() {
    return numCols;
  }

  public int getNumRows() {
    return numRows;
  }

  public BoardState getBoardState() {
    return boardState;
  }
}
