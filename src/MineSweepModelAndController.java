import java.util.Stack;

public class MineSweepModelAndController {

    private final MineSweepView view;
    private char[][] board;
    private int numOfMines;
    private boolean created;

    public MineSweepModelAndController(MineSweepView view, int row, int column,
            int numOfMines) {
        this.view = view;
        this.newInstance(row, column, numOfMines);
    }

    public void newInstance(int row, int column, int numOfMines) {
        this.board = new char[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                this.board[i][j] = 'E';
            }
        }
        this.numOfMines = numOfMines;
        this.created = false;
    }

    public void resetInstance() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                char c = this.board[i][j];
                switch (c) {
                    case 'X':
                        this.board[i][j] = 'M';
                        break;
                    case 'M':
                        break;
                    case 'E':
                        break;
                    default:
                        this.board[i][j] = 'E';
                }
            }
        }
    }

    public void updateView(int[] click) {
        if (!this.created) {
            this.setMines(click);
        }
        this.updateBoard(click);
        this.view.updateView(this.board);
    }

    private void setMines(int[] click) {
        for (int i = 0; i < this.numOfMines; i++) {
            int xPos = (int) (Math.random() * this.board.length);
            int yPos = (int) (Math.random() * this.board[0].length);
            if (this.board[xPos][yPos] == 'M'
                    || (click[0] == xPos && click[1] == yPos)) {
                i--;
            } else {
                this.board[xPos][yPos] = 'M';
            }
        }
        this.created = true;
    }

    private int count(int[] loc) {
        int count = 0;
        for (int i = loc[0] - 1; i <= loc[0] + 1
                && i < this.board.length; i++) {
            for (int j = loc[1] - 1; j <= loc[1] + 1
                    && j < this.board[0].length; j++) {
                if (i >= 0 && j >= 0 && this.board[i][j] == 'M') {
                    count++;
                }
            }
        }
        return count;
    }

    private void updateBoard(int[] click) {
        Stack<int[]> s = new Stack<>();
        switch (this.board[click[0]][click[1]]) {
            case 'M':
                this.board[click[0]][click[1]] = 'X';
                break;
            case 'E':
                this.board[click[0]][click[1]] = 'B';
                int count = this.count(click);
                if (count > 0) {
                    this.board[click[0]][click[1]] = (char) (count + '0');
                }
                s.add(click);
                break;
        }
        while (s.size() > 0) {
            int[] loc = s.pop();
            int count = this.board[loc[0]][loc[1]] - '0';
            if (count == 18) {
                count = 0;
            }
            for (int i = loc[0] - 1; i <= loc[0] + 1
                    && i < this.board.length; i++) {
                for (int j = loc[1] - 1; j <= loc[1] + 1
                        && j < this.board[0].length; j++) {
                    if (i >= 0 && j >= 0 && this.board[i][j] == 'E') {
                        int[] newLoc = { i, j };
                        int newCount = this.count(newLoc);
                        if (count == 0 || newCount == 0) {
                            this.board[i][j] = 'B';
                            if (newCount > 0) {
                                this.board[i][j] = (char) (newCount + '0');
                            }
                            s.push(newLoc);
                        }
                    }
                }
            }
        }
    }

}
