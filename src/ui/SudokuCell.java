package ui;

import java.util.List;

/**
 * Created by BscitXPS on 6/15/2016.
 */
public class SudokuCell {
    private List<Integer> horizontalGroup;
    private List<Integer> verticalGroup;
    private List<Integer> cellGroup;
    private boolean finalized = false;
    private Integer value;

    public SudokuCell() {}

    public SudokuCell(int value) {
        this.value = value;
        finalize();
    }

    public void setGroups(List<Integer> h, List<Integer> v, List<Integer> c) {
        horizontalGroup = h;
        verticalGroup = v;
        cellGroup = c;
    }

    public void finalize() {
        finalized = true;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void clear() {
        if (!finalized) {
            value = null;
        }
    }
}
