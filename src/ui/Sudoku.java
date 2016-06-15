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


        int smallSize = (int) Math.sqrt(size);

        // 00 01 02 10 11 12 20 21 22
        // 03 04 05 13 14 15 23 24 25
        // 06 07 08 16 17 18 26 27 28
        // 30 31 32 40 41 42 50 51 52
        // ..
        // nn n(n+1) n(n+2) (n+1)n (n+1)(n+1) (n+1)(n+2) (n+2)n (n+2)(n+1) (n+2)(n+2)
        //

        // for n=0 to 9 step 3
        //  for m=0 to 9 step 3
        //   for i=n to n+2
        //    for j=m to m+2
        // Creating cellGroups

        for (int n = 0; n < size; n+=smallSize) {
            for (int m = 0; m < size; m+=smallSize) {
                List<SudokuCell> tempGroup = new ArrayList<>();
                for (int i = n; i < n+2; i+=1) {
                    for (int j = m; j < m+2; m+=1) {
                        tempGroup.add(mat[i][j]);
                    }
                }

                for (SudokuCell sCell : tempGroup) {
                    sCell.setCellGroup(tempGroup);
                }
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
