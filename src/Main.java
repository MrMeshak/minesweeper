public class Main {
  public static void main(String[] args) {
    Board board = new Board(10, 10);
    board.initializeCells(10);
    board.printCells();

    int randomRow = (int) Math.floor(Math.random() * (9 - 0 + 1) + 0);
    int randomCol = (int) Math.floor(Math.random() * (9 - 0 + 1) + 0);
    board.makeMove(randomRow, randomCol);
    System.out.println("");
    board.printCells();

    Game game = new Game(10, 10, 5);
    // game.playerMove();
    game.play();
  }
}