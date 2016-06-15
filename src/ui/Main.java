package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 475);
        primaryStage.setScene(scene);
        primaryStage.show();

//        GridPane[][] grid = new GridPane[3][3];
//        Node[][] sudoku = new Node[9][9];
//
//        for (int i = 0; i < 3; i+=1) {
//            for (int j = 0; j < 3; j+=1) {
//                String lookupId = ("#grdSudoku" + i) + j;
//                grid[i][j] = (GridPane) scene.lookup(lookupId).get;
//            }
//        }
//
//        List<List<Node>> groups = new ArrayList<>();
//        List<List<Node>> horizontals = new ArrayList<>();
//        List<List<Node>> verticals = new ArrayList<>();
//
//        for (int i = 0; i < 3; i+=1) {
//            for (int j = 0; j < 3; j+=1) {
//                List<Node> temp = new ArrayList<>();
//                temp = grid
//                groups.add(temp);
//            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    private Node _getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
