import javafx.application.Platform;
import javafx.scene.Node;


public class Computer extends Player {

    public Computer(String name, String marker, int turn) {
        super(name, marker, turn);
    }

    @Override
    public void takeTurn(TicTacToe game) {
        Node[][] board = game.getBoard();
        int aiPlayerIdx = this.getTurn();
        int maximizingPlayerIdx = determineMaximizingPlayerIdx(game);
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                BoardButton square = (BoardButton) board[r][c];
                if (square.isAvailable()) {
                    square.setText(this.getMarker());

                    int nextPlayerIdx = (aiPlayerIdx + 1) % game.getPlayers().size();

                    int score = minimax(game, square, 0, nextPlayerIdx, aiPlayerIdx, maximizingPlayerIdx);

                    square.setText("");

                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = r;
                        move[1] = c;
                    }
                }
            }
        }
        Platform.runLater(((BoardButton) board[move[0]][move[1]])::fire);
    }




    private int minimax(TicTacToe game, BoardButton sq, int depth, int playerIdx, int aiPlayerIdx, int maximizingPlayerIdx) {

        int prevPlayerIdx = (playerIdx + game.getPlayers().size() - 1) % game.getPlayers().size();

        if (game.checkWinner(sq.getRow(), sq.getCol(), game.getPlayers().get(prevPlayerIdx).getMarker())) {
            return (aiPlayerIdx == prevPlayerIdx) ? 1 : -1;
        }
        if (checkTie(game.getBoard())) {
            return 0;
        }

        boolean isMaximizing = (playerIdx == maximizingPlayerIdx);
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Node[][] board = game.getBoard();

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                BoardButton square = (BoardButton) board[r][c];
                if (square.isAvailable()) {

                    square.setText(game.getPlayers().get(playerIdx).getMarker());

                    int nextPlayerIdx = (playerIdx + 1) % game.getPlayers().size();

                    int score = minimax(game, square, depth + 1, nextPlayerIdx, aiPlayerIdx, maximizingPlayerIdx);

                    square.setText("");

                    if (isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }







    private int determineMaximizingPlayerIdx(TicTacToe game) {
        return this.getTurn();
    }

    private boolean checkTie(Node[][] board) {
        for(Node[] nodes : board) {
            for (Node node : nodes) {
                if (((BoardButton) node).getText().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
