enum CellState {
  REVEALED,
  FLAG,
  HIDDEN
}

public class Cell {
  public static final int MINE_VAL = -1;

  private int value;
  private CellState cellState;

  Cell() {
    this.value = 0;
    this.cellState = CellState.HIDDEN;
  }

  Cell(int value) {
    this.value = value;
    this.cellState = CellState.HIDDEN;
  }

  Cell(int value, CellState cellState) {
    this.value = value;
    this.cellState = cellState;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public CellState getCellState() {
    return this.cellState;
  }

  public void setCellState(CellState cellState) {
    this.cellState = cellState;
  }

  public boolean isMine() {
    return this.value == MINE_VAL;
  }

  public void makeMine() {
    this.value = MINE_VAL;
  }
}
