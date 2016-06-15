package ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BscitXPS on 6/15/2016.
 */
public class Sudoku {

    private int size;
    private SudokuCell mat[][]; //;

    public Sudoku() {
        this(9);
    }

    public Sudoku(int size) {
        this.size = size;

        mat = new SudokuCell[size][size];

        for (int i = 0; i < size; i+=1) {
            for (int j = 0; j < size; j+=1) {
                mat[i][j] = new SudokuCell();
            }
        }

        List<SudokuCell> tempGroups = new ArrayList<>();

        int smallSize = (int) Math.sqrt(size);

        // Creating cellGroups
        for (int i = 0; i < smallSize; i+=1) {
            for (int j = 0; j < smallSize; j+=1) {
                mat[i][j] = new SudokuCell();
            }
        }
    }

    public Sudoku(Integer[][] preset) throws SudokuException {
        if (preset.length != preset[0].length) {
            // Not a square matrix
            throw new SudokuException();
        }

        this.size = preset.length;
        mat = new SudokuCell[size][size];

        for (int i = 0;  i < size; i+=1) {
            for (int j = 0; j < size; j+=1) {
                if (preset[i][j] != null) {
                    mat[i][j] = new SudokuCell(preset[i][j]);
                } else {
                    mat[i][j] = new SudokuCell();
                }
            }
        }
    }
}

class SudokuException extends Exception {

}
