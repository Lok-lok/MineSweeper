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

    private void updateBoard(int[] click) {
        switch (this.board[click[0]][click[1]]) {
            case 'M':
                this.board[click[0]][click[1]] = 'X';
                break;
            case 'E':
                this.board[click[0]][click[1]] = 'B';
                int count = 0;
                int rowStart = click[0] - 1;
                int rowEnd = click[0] + 1;
                int columnStart = click[1] - 1;
                int columnEnd = click[1] + 1;
                if (rowStart < 0) {
                    rowStart = 0;
                }
                if (columnStart < 0) {
                    columnStart = 0;
                }
                if (rowEnd >= this.board.length) {
                    rowEnd = this.board.length - 1;
                }
                if (columnEnd >= this.board[0].length) {
                    columnEnd = this.board[0].length - 1;
                }
                for (int i = rowStart; i <= rowEnd; i++) {
                    for (int j = columnStart; j <= columnEnd; j++) {
                        if (this.board[i][j] == 'M') {
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    this.board[click[0]][click[1]] = (char) (count + '0');
                    for (int i = rowStart; i <= rowEnd; i++) {
                        for (int j = columnStart; j <= columnEnd; j++) {
                            if (this.board[i][j] == 'E') {
                                int newRowStart = i - 1;
                                int newRowEnd = i + 1;
                                int newColumnStart = j - 1;
                                int newColumnEnd = j + 1;

                                if (newRowStart < 0) {
                                    newRowStart = 0;
                                }
                                if (newColumnStart < 0) {
                                    newColumnStart = 0;
                                }
                                if (newRowEnd >= this.board.length) {
                                    newRowEnd = this.board.length - 1;
                                }
                                if (newColumnEnd >= this.board[0].length) {
                                    newColumnEnd = this.board[0].length - 1;
                                }
                                boolean keepGoing = true;
                                for (int m = newRowStart; m <= newRowEnd; m++) {
                                    for (int n = newColumnStart; n <= newColumnEnd; n++) {
                                        if (this.board[m][n] == 'M') {
                                            keepGoing = false;
                                        }
                                    }
                                }
                                if (keepGoing) {
                                    int[] thisChange = { i, j };
                                    this.updateBoard(thisChange);
                                }
                            }
                        }
                    }
                } else {
                    for (int i = rowStart; i <= rowEnd; i++) {
                        for (int j = columnStart; j <= columnEnd; j++) {
                            if (this.board[i][j] == 'E') {
                                int[] thisChange = { i, j };
                                this.updateBoard(thisChange);
                            }
                        }
                    }
                }
                break;
        }
    }

}
