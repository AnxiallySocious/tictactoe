package tictactoe;

public class AI {

    /**
     * Uses the minxmax algorithm to find the best move for the AI
     *
     * @param board The current game board state.
     * @param aiPlayer The character representing the AI player.
     * @param humanPlayer The character representing the human player.
     * @return An array containing the row and column of the best move.
     */
    public static int[] bestMove(GameBoard board, char aiPlayer, char humanPlayer) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[]{-1, -1}; // Start with no move

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if the cell is empty
                if (board.getBoard()[i][j] == '-') {
                    // Make the move
                    board.getBoard()[i][j] = aiPlayer;
                    // Compute the minimax score for this move
                    int score = minimax(board, 0, false, aiPlayer, humanPlayer);
                    // Undo the move
                    board.getBoard()[i][j] = '-';
                    // If the new score is higher than the best score, update the move
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    /**
     * A minimax function for finding the best move for the AI.
     *
     * @param board The current state of the game board.
     * @param depth The current depth of recursion in the game tree.
     * @param isMaximizing Indicates if the current function call is trying to maximize or minimize the score.
     * @param aiPlayer The character representing the AI player.
     * @param humanPlayer The character representing the human player.
     * @return The best score calculated from the current game state.
     */
    private static int minimax(GameBoard board, int depth, boolean isMaximizing, char aiPlayer, char humanPlayer) {
        // Check for terminal states: win, lose, or tie, and return a score accordingly.
        if (board.checkForWin()) {
            // If the current player wins, return positive or negative score based on the player.
            return isMaximizing ? -10 : 10;  // Assign negative score for human win, positive for AI win.
        } else if (board.isBoardFull()) {
            // If the board is full and there's no winner, it's a tie.
            return 0; // A tie is neutral
        }

        if (isMaximizing) {
            // If we are maximizing (AI's turn), start with the lowest possible score.
            int bestScore = Integer.MIN_VALUE;
            // Iterate through all board cells.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if the cell is empty to make a move.
                    if (board.getBoard()[i][j] == '-') {
                        // Make a temporary move.
                        board.getBoard()[i][j] = aiPlayer;
                        // Recursively call minimax to evaluate this move.
                        int score = minimax(board, depth + 1, false, aiPlayer, humanPlayer);
                        // Undo the move.
                        board.getBoard()[i][j] = '-';
                        // Update bestScore if the current score is better.
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            // If we are minimizing (human's turn), start with the highest possible score.
            int bestScore = Integer.MAX_VALUE;
            // Iterate through all board cells.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if the cell is empty to make a move.
                    if (board.getBoard()[i][j] == '-') {
                        // Make a temporary move.
                        board.getBoard()[i][j] = humanPlayer;
                        // Recursively call minimax to evaluate this move.
                        int score = minimax(board, depth + 1, true, aiPlayer, humanPlayer);
                        // Undo the move.
                        board.getBoard()[i][j] = '-';
                        // Update bestScore if the current score is better.
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
}