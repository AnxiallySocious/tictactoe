package tictactoe;
import javax.swing.*;
import java.awt.*;

public class TicTacToeGUI extends JFrame {
    private char playerChar = 'X'; // Default player character
    private char aiChar = 'O'; // Default AI character
    private GameBoard board = new GameBoard();
    private JPanel boardPanel; // Panel for the Tic Tac Toe board

    public TicTacToeGUI() {
        setupGameWindow();
        playerChoice(); // Prompt for player choice at the start
        initializeBoard();
        addResetButton();
    }

    private void setupGameWindow() {
        setTitle("Tic Tac Toe");
        setSize(350, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout to add the reset button

        boardPanel = new JPanel(new GridLayout(3, 3)); // Use GridLayout for the board
        add(boardPanel, BorderLayout.CENTER); // Add the boardPanel to the center
    }

    private void playerChoice() {
        String[] options = {"X", "O"};
        int response = JOptionPane.showOptionDialog(null, "Choose your side", "Player Choice",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        playerChar = (response == 0) ? 'X' : 'O';
        aiChar = (playerChar == 'X') ? 'O' : 'X';
    }

    private void initializeBoard() {
        boardPanel.removeAll(); // Clear the previous board
        board.initializeBoard(); // Reset the board logic
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                JButton button = new JButton();
                button.addActionListener(e -> {
                    if (board.placeMove(row, col, playerChar)) {
                        button.setText(String.valueOf(playerChar));
                        button.setEnabled(false);
                        if (checkGameState()) {
                            if (!board.isBoardFull() && !board.checkForWin()) {
                                aiMove();
                            }
                        }
                    }
                });
                boardPanel.add(button);
            }
        }
        boardPanel.validate();
        boardPanel.repaint();
        if (aiChar == 'X') { // If AI is X, it makes the first move.
            aiMove();
        }
    }

    private void addResetButton() {
        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH); // Place the reset button below the game board
    }

    private boolean checkGameState() {
        if (board.checkForWin()) {
            JOptionPane.showMessageDialog(this, "Game Over. " + (board.isLastMoveWin(playerChar) ? "Player wins!" : "AI wins!"));
            resetGame();
            return false;
        } else if (board.isBoardFull()) {
            JOptionPane.showMessageDialog(this, "Game Over. It's a draw!");
            resetGame();
            return false;
        }
        return true;
    }

    private void aiMove() {
        SwingUtilities.invokeLater(() -> {
            int[] move = AI.bestMove(board, aiChar, playerChar);
            if (move[0] != -1) {
                // Access the button within boardPanel specifically
                JButton button = (JButton) boardPanel.getComponent(move[0] * 3 + move[1]);
                button.setText(String.valueOf(aiChar));
                button.setEnabled(false);
                board.placeMove(move[0], move[1], aiChar); // Directly update the board with AI's move
                checkGameState(); // Check the game state after AI's move
            }
        });
    }

    private void resetGame() {
        int result = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            playerChoice(); // Reprompt player choice for the new game
            initializeBoard(); // Reinitialize the board for a new game
        } else {
            System.exit(0); // Exit the game if the player chooses not to continue
        }
    }

}