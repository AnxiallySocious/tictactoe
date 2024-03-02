package tictactoe;

public class GameBoard {
    private char[][] board;

    public GameBoard() {
        this.board = new char[3][3];
        initializeBoard();
    }

    public char[][] getBoard() {
        return board;
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false; // Empty space found, board is not full.
                }
            }
        }
        return true; // No empty spaces, board is full.
    }

    public boolean checkForWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((checkRowCol(board[i][0], board[i][1], board[i][2])) || (checkRowCol(board[0][i], board[1][i], board[2][i]))) {
                return true;
            }
        }
        // Check diagonals
        if ((checkRowCol(board[0][0], board[1][1], board[2][2])) || (checkRowCol(board[0][2], board[1][1], board[2][0]))) {
            return true;
        }
        return false;
    }

    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    public boolean placeMove(int row, int col, char symbol) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = symbol;
            return true; // Move was successfully placed.
        }
        return false; // Move could not be placed (invalid position or already taken).
    }

    public boolean isLastMoveWin(char playerChar) {
        // Check rows for win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == playerChar && board[i][1] == playerChar && board[i][2] == playerChar) {
                return true;
            }
        }

        // Check columns for win
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == playerChar && board[1][j] == playerChar && board[2][j] == playerChar) {
                return true;
            }
        }

        // Check diagonals for win
        if (board[0][0] == playerChar && board[1][1] == playerChar && board[2][2] == playerChar) {
            return true;
        }
        if (board[0][2] == playerChar && board[1][1] == playerChar && board[2][0] == playerChar) {
            return true;
        }

        // No win condition met
        return false;
    }
}