import tictactoe.AI;
import tictactoe.GameBoard;
import tictactoe.TicTacToeGUI;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    //Console version
    public static void main(String[] args) {
        String[] options = {"Play on the console", "Play with graphics"};
        int response = JOptionPane.showOptionDialog(null, "Choose your mode", "Player Choice",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (response==0) {
            playConsole();
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new TicTacToeGUI().setVisible(true);
                }
            });
        }
    }

    private static void playConsole() {
        Scanner sc = new Scanner(System.in);
        GameBoard board = new GameBoard();
        while (match(sc,board)) {
            //Empty intentionally. Loops till it returns false.
        }
    }

    private static boolean match(Scanner sc, GameBoard board) {
        System.out.println("Do you want to be X or O? Enter X or O:");
        String input = sc.next().toUpperCase();
        while (!input.equals("X") && !input.equals("O")) {
            System.out.println("Please try again. Do you want to be X or O?:");
            input = sc.next().toUpperCase();
        }
        char playerChar = input.charAt(0);
        char aiChar = (playerChar == 'X') ? 'O' : 'X';
        boolean playerTurn = playerChar == 'X';
        board.initializeBoard();
        while (!board.isBoardFull() && !board.checkForWin()) {
            if (playerTurn) {
                // Player's turn
                int row, col;
                boolean validMove;
                do {
                    System.out.println("Player, submit your row [1-3], then your column [1-3]: ");
                    row = sc.nextInt() - 1; // -1 for 0 index based array
                    col = sc.nextInt() - 1; // -1 for 0 index based array

                    validMove = board.placeMove(row, col, playerChar);
                    if (!validMove) {
                        System.out.println("This move at (" + (row + 1) + "," + (col + 1) + ") is not valid. Try again.");
                    }
                } while (!validMove);
            } else {
                // AI's turn
                int[] move = AI.bestMove(board, aiChar, playerChar);
                board.placeMove(move[0], move[1], aiChar);
                System.out.println("AI placed a move at " + (move[0] + 1) + ", " + (move[1] + 1));
            }
            board.printBoard();
            if (board.checkForWin()) {
                String winner = playerTurn ? "Player" : "AI";
                System.out.println(winner + " wins!");
                System.out.println("Would you like to play again?");
                String response = sc.next().toLowerCase();
                return response.equals("yes") ? true : false;
            }
            playerTurn = !playerTurn; // Switch turns
        }
        if (board.isBoardFull()) {
            System.out.println("The game is a draw!");
            System.out.println("Would you like to play again?");
            String response = sc.next().toLowerCase();
            return response.equals("yes") ? true : false;
        }
        return false;
    }
}