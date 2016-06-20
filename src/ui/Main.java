package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Sudoku sudoku;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 475);
        primaryStage.setScene(scene);
        primaryStage.show();

        GridPane[][] grid = new GridPane[3][3];
        Integer[][] preset = new Integer[9][9];
        int[][] cells = new int[9][9];

        for (int i = 0; i < 3; i+=1) {
            for (int j = 0; j < 3; j+=1) {
                String lookupId = ("#grdSudoku" + i) + j;
                grid[i][j] = (GridPane) scene.lookup(lookupId);
//                System.out.println(grid[i][j]);
            }
        }

        int r = 0;
        int c = 0;

        for (int i = 0; i < 3; i+=1) {
            for (int j = 0; j < 3; j+=1) {
//                for (int r = 0; r < 3; r+=1) {
//                    for (int c = 0; c < 3; c+=1) {
                        for (Node node : grid[i][j].getChildren()) {
//                            System.out.println(c + "," + r);

                            if (!(node instanceof TextField)) {
                                continue;
                            }

                            if (((TextField) node).lengthProperty().intValue() == 0) {
//                                System.out.print(" E ");
                                cells[c][r] = 0;
                                r += 1;

                            } else {
//                                System.out.print(" " + ((TextField) node).getCharacters() + " ");
                                cells[c][r] = Integer.parseInt(((TextField) node).getCharacters().toString());
                                r += 1;

                            }

                            if (r >= 9) {
                                r = 0;
                            }

//                            if (r >= 9) {
//                                r = 0;
//                                c += 1;
//                            }
//
//                            if (c >= 9) {
//                                c = 0;
//                                r = 0;
//                            }

                        }
//                System.out.print("\n");
                c += 1;
                r = 0;

//                    }
//                }

            }
        }

        c = 0;
        r = 0;

        for (int j = 0; j < 9; j+=3) {
            for (int i = 0; i < 9; i+=3) {
                for (int jj = 0; jj < 3; jj+=1) {
                    for (int ii = 0; ii < 3; ii += 1) {
//                        System.out.print(cells[j + jj][i + ii]);
                        preset[r][c] = cells[j + jj][i + ii];
                        c += 1;
                    }
                }
//                System.out.println();
                c = 0;
                r += 1;
            }
        }

        sudoku = new Sudoku(preset);
        System.out.println(sudoku.solve());
        System.out.println(sudoku);
        System.out.println("Done");
    }


    public static void main(String[] args) {
        launch(args);
    }

    private Node _getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            System.out.println(((TextField) node).getCharacters());
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
