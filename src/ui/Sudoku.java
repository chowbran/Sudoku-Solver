package ui;

import java.util.*;

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
                mat[i][j] = new SudokuCell(size);
            }
        }

        _initGroups();
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
                    mat[i][j] = new SudokuCell(size, preset[i][j]);
                } else {
                    mat[i][j] = new SudokuCell(size);
                }
            }
        }

        _initGroups();
    }

    private void _initGroups() {
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

        for (int i = 0; i < size; i+=1) {
            List<SudokuCell> tempGroup = new ArrayList<>();
            for (int j = 0; j < size; j+=1) {
                tempGroup.add(mat[i][j]);
            }

            for (SudokuCell sCell : tempGroup) {
                sCell.setHorizontalGroup(tempGroup);
            }
        }

        for (int i = 0; i < size; i+=1) {
            List<SudokuCell> tempGroup = new ArrayList<>();
            for (int j = 0; j < size; j+=1) {
                tempGroup.add(mat[j][i]);
            }

            for (SudokuCell sCell : tempGroup) {
                sCell.setVerticalGroup(tempGroup);
            }
        }
    }

    public boolean isValid() {
        boolean ret = true;

        for (SudokuCell[] row : mat) {
            for (SudokuCell sCell : row) {
                ret &= sCell.isValid();
            }
        }

        return ret;
    }

    public void solve() {
        Map<Integer,List<SudokuCell>> smallestDomains = new HashMap<>();
        Map<Integer,List<SudokuCell>> highestDegrees = new HashMap<>();
        SudokuCell activeCell;
        int smallestDomainSize = size;

        // Creating a Map with Integer keys and List of SudokuCell values
        for (SudokuCell[] row : mat) {
            for (SudokuCell sCell : row) {
                int domainSize = sCell.domainSize();
                if (domainSize > 0) {
                    if (smallestDomains.containsKey(domainSize)) {
                        smallestDomains.get(domainSize).add(sCell);
                    } else {
                        smallestDomains.put(domainSize, new ArrayList<>());
                    }
                }
            }
        }


        for (int domSize : smallestDomains.keySet()) {
            if (domSize < smallestDomainSize) {
                smallestDomainSize = domSize;
            }
        }

        List<SudokuCell> candidates = smallestDomains.get(smallestDomainSize);


        int highestDegree;
        int degree;

        if (candidates.size() > 1) {
            // If there is a tie for the smallest domain, choose the one with the highest degree
            activeCell = candidates.get(0); // Init activeCell
            highestDegree = candidates.get(0).degree();
            for (SudokuCell sCell : candidates) {
                degree = sCell.degree();
                if (degree > highestDegree) {
                    highestDegree = degree;
                    activeCell = sCell;
                }
            }
        } else {
            // If there is no tie for the smallest domain, simply choose the smallest domain
            activeCell = smallestDomains.get(smallestDomainSize).get(0);
        }

        Set<Integer> domain = activeCell.getDomain();
        int choose = (Integer) domain.toArray()[0];

        activeCell.assignValue(choose);
    }
}

class SudokuException extends Exception {

}
