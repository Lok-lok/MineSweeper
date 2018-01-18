import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MineSweepView extends JFrame implements ActionListener {

    private MineSweepModelAndController modelAndController;
    private JButton replay;
    private JButton[][] cells;
    private int row;
    private int column;
    private int numOfMines;

    public MineSweepView(int row, int column, int numOfMines) {
        super("Mine Sweep");
        this.newInstance(row, column, numOfMines);
    }

    private void newInstance(int row, int column, int numOfMines) {
        JPanel functionPanel = new JPanel(new GridLayout(1, 1));
        this.replay = new JButton("Replay");
        functionPanel.add(this.replay);
        JPanel cellPanel = new JPanel(new GridLayout(row, column));
        this.cells = new JButton[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.cells[i][j] = new JButton();
                this.cells[i][j].setPreferredSize(new Dimension(50, 50));
                cellPanel.add(this.cells[i][j]);
            }
        }
        this.row = row;
        this.column = column;
        this.numOfMines = numOfMines;
        this.setLayout(new BorderLayout());
        this.add(functionPanel, BorderLayout.PAGE_START);
        this.add(cellPanel, BorderLayout.CENTER);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.cells[i][j].addActionListener(this);
            }
        }
        this.replay.addActionListener(this);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void registerObserver(
            MineSweepModelAndController modelAndController) {
        this.modelAndController = modelAndController;
    }

    private void resetButton() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                this.cells[i][j].setEnabled(true);
                this.cells[i][j].setText("");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == this.replay) {
            this.resetButton();
            this.modelAndController.newInstance(this.row, this.column,
                    this.numOfMines);
        } else {
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    if (source == this.cells[i][j]) {
                        int[] click = { i, j };
                        this.modelAndController.updateView(click);
                    }
                }
            }
        }
    }

    public void updateView(char[][] board) {
        boolean explode = false;
        int countRevealedEmpty = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                if (board[i][j] == 'B') {
                    this.cells[i][j].setEnabled(false);
                    countRevealedEmpty++;
                } else if (board[i][j] <= 56) {
                    this.cells[i][j].setEnabled(false);
                    this.cells[i][j].setText(Character.toString(board[i][j]));
                    countRevealedEmpty++;
                } else if (board[i][j] == 'X') {
                    this.cells[i][j].setEnabled(false);
                    this.cells[i][j].setText("Bomb");
                    explode = true;
                }

            }
        }
        if (explode) {
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    if (board[i][j] == 'M') {
                        this.cells[i][j].setText("Bomb");
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Bomb!", "Game Over",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (countRevealedEmpty == this.row * this.column
                - this.numOfMines) {
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    if (board[i][j] == 'M') {
                        this.cells[i][j].setText("Bomb");
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "You win!", "Congratulations",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}
