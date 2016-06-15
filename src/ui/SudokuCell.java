package ui;

import java.util.List;

/**
 * Created by BscitXPS on 6/15/2016.
 */
public class SudokuCell {
    private List<SudokuCell> horizontalGroup;
    private List<SudokuCell> verticalGroup;
    private List<SudokuCell> cellGroup;
    private boolean finalized = false;
    private Integer value;

    public SudokuCell() {}

    public SudokuCell(int value) {
        this.value = value;
        finalize();
    }

    public void setGroups(List<SudokuCell> h, List<SudokuCell> v, List<SudokuCell> c) {
        horizontalGroup = h;
        verticalGroup = v;
        cellGroup = c;
    }

    public void setCellGroup(List<SudokuCell> c) {
        cellGroup = c;
    }

    public void setVerticalGroup(List<SudokuCell> v) {
        verticalGroup = v;
    }

    public void setHorizontalGroup(List<SudokuCell> h) {
        horizontalGroup = h;
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
