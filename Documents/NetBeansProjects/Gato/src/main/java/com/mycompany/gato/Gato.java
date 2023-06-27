/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gato;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Gato extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private JLabel[] scoreLabels;
    private int[][] score;
    private int currentPlayer;
    private int totalGames;

    public Gato() {
        super("Gato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        buttons = new JButton[3][3];
        scoreLabels = new JLabel[2];
        score = new int[2][1];

        currentPlayer = 0;
        totalGames = 0;

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        JPanel scorePanel = new JPanel(new GridLayout(2, 1));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].addActionListener(this);
                buttonPanel.add(buttons[row][col]);
            }
        }

        scoreLabels[0] = new JLabel("Jugador 1: 0");
        scoreLabels[1] = new JLabel("Jugador 2: 0");

        scorePanel.add(scoreLabels[0]);
        scorePanel.add(scoreLabels[1]);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scorePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        button.setEnabled(false);

        if (currentPlayer == 0) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        if (checkWin()) {
            JOptionPane.showMessageDialog(this, "¡Jugador " + (currentPlayer + 1) + " ha ganado!");
            score[currentPlayer][0]++;
            updateScoreLabels();

            if (totalGames < 9) {
                resetGame();
                totalGames++;
            } else {
                endGame();
            }
        } else if (checkDraw()) {
            JOptionPane.showMessageDialog(this, "¡Empate!");
            if (totalGames < 9) {
                resetGame();
                totalGames++;
            } else {
                endGame();
            }
        } else {
            currentPlayer = (currentPlayer + 1) % 2;
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = buttons[row][col].getText();
            }
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2]) && !board[row][0].isEmpty()) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(board[1][col]) && board[1][col].equals(board[2][col]) && !board[0][col].isEmpty()) {
                return true;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setText("");
            }
        }
    }

    private void updateScoreLabels() {
        scoreLabels[0].setText("Jugador 1: " + score[0][0]);
        scoreLabels[1].setText("Jugador 2: " + score[1][0]);
    }

    private void endGame() {
        int winner = -1;
        if (score[0][0] > score[1][0]) {
            winner = 0;
        } else if (score[1][0] > score[0][0]) {
            winner = 1;
        }

        String message;
        if (winner == -1) {
            message = "¡Juego terminado! Empate";
        } else {
            message = "¡Juego terminado! Ganador: Jugador " + (winner + 1);
        }

        JOptionPane.showMessageDialog(this, message);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Gato();
        });
    }
}