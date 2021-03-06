package ui;

import java.util.*;
import java.util.function.DoubleFunction;

/**
 * Created by BscitXPS on 6/15/2016.
 */
public class SudokuCell implements Comparable<SudokuCell> {
    private List<SudokuCell> horizontalGroup;
    private List<SudokuCell> verticalGroup;
    private List<SudokuCell> cellGroup;
    private boolean finalized = false;
    private Integer value;
    private Set<Integer> domain;
    private int size;

    public SudokuCell(int size) {
        this.size = size;

        domain = new HashSet<>();

        List<Integer> domainList = new ArrayList<>();

        for (int i = 0; i < size; i+=1) {
            domainList.add(i + 1); // 1 based
        }

        domain.addAll(domainList);
    }

    public SudokuCell(int size, int value) {
        this(size);

        this.value = value;
        this.finalize();

        domain.remove(value);
    }

    /**
     * Sets the reference of the SudokuCells in the same cell block.
     * @param c
     */
    public void setCellGroup(List<SudokuCell> c) {
        cellGroup = c;
    }

    /**
     * Sets the references of the SudokuCells in the same column.
     * @param v
     */
    public void setVerticalGroup(List<SudokuCell> v) {
        verticalGroup = v;
    }

    /**
     * Sets the references of the the SudokuCells in the same row.
     * @param h
     */
    public void setHorizontalGroup(List<SudokuCell> h) {
        horizontalGroup = h;
    }

    /**
     * Finalizes the value of this cell, preventing it from changing.
     */
    public void finalize() {
        finalized = true;
    }

    public void assignValue(int value) {
        this.value = value;
        this.updateDomain();

        for (SudokuCell sC : cellGroup) {
            sC.updateDomain();
        }

        for (SudokuCell sC : horizontalGroup) {
            sC.updateDomain();
        }

        for (SudokuCell sC : verticalGroup) {
            sC.updateDomain();
        }
    }

    public int getValue() {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public void setValue(int value) {
        if (value == 0)  {
            this.value = null;
        } else {
            this.value = value;
        }
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void clear() {
        if (!finalized) {
            // Add the value back into the domain
            value = null;
            this.updateDomain();

            for (SudokuCell sC : cellGroup) {
                sC.updateDomain();
            }

            for (SudokuCell sC : horizontalGroup) {
                sC.updateDomain();
            }

            for (SudokuCell sC : verticalGroup) {
                sC.updateDomain();
            }

        }
    }

    public void updateDomain() {
        // If a value is already assigned to this cell, then the domain is empty
        if (this.getValue() > 0) {
            this.domain = new HashSet<>();
            return;
        }

        this.domain = new HashSet<>();
        List<Integer> temp = new ArrayList<>();

        for (int i = 0; i < size; i+=1) {
            temp.add(i + 1); // 1 based
        }

        this.domain.addAll(temp);

        List<SudokuCell> neighbours = new ArrayList<>();
        neighbours.addAll(cellGroup);
        neighbours.addAll(horizontalGroup);
        neighbours.addAll(verticalGroup);

        Set<Integer> neighbourValues = new HashSet<>();

        for (SudokuCell sC : neighbours) {
            neighbourValues.add(sC.getValue());
        }

        this.domain.removeAll(neighbourValues);
    }

    public boolean isValid() {
        Collections.sort(cellGroup);
        Collections.sort(horizontalGroup);
        Collections.sort(verticalGroup);

        for (int i = 0; i < cellGroup.size()-1; i+=1) {
            if (cellGroup.get(i).getValue() > 0 && cellGroup.get(i).equals(cellGroup.get(i+1))) {
                return false;
            }
        }

        for (int i = 0; i < horizontalGroup.size()-1; i+=1) {
            if (horizontalGroup.get(i).getValue() > 0 && horizontalGroup.get(i).equals(horizontalGroup.get(i+1))) {
                return false;
            }
        }

        for (int i = 0; i < verticalGroup.size()-1; i+=1) {
            if (verticalGroup.get(i).getValue() > 0 && verticalGroup.get(i).equals(verticalGroup.get(i+1))) {
                return false;
            }
        }

        return true;
    }


    public int domainSize() {
        return domain.size();
    }

    public int degree() {
        int degree = 0;
        List<SudokuCell> neighbours = new ArrayList<>();
        neighbours.addAll(cellGroup);
        neighbours.addAll(horizontalGroup);
        neighbours.addAll(verticalGroup);

        for (SudokuCell neighbour : neighbours) {
            if (neighbour.getValue() == 0) {
                degree += 1;
            }
        }

        if (this.getValue() == 0) {
            degree -= 3; // We subtract by - 3 because this counts our self 3 times
        }

        return degree;
//        return size - domainSize();
    }

    public Set<Integer> getDomain() {
        return Collections.unmodifiableSet(domain);
    }

    @Override
    public int compareTo(SudokuCell o) {
        return this.getValue() - o.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (((SudokuCell) o).getValue() == value) {
            return true;
        } else {
            return false;
        }
    }
}
