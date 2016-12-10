/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.IncorrectInputException;
import model.Node;
import model.NodeComparator1;
import model.Solver;

/**
 *
 * @author sg
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*int a1[][] = {{2, 4, 3},
        {1, 5, 6},
        {7, 8, 0}
        };
        Node n1 = new Node(3, a1);
        
        int a2[][] = {{1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
        };
        Node n2 = new Node(3, a2);
        
        int a3[][] = {{3, 1, 2},
        {6, 4, 5},
        {0, 8, 7}
        };
        Node n3 = new Node(3, a3);
        
        int a4[][] = {{2, 4, 3},
        {1, 5, 6},
        {7, 8, 0}
        };
        Node n4 = new Node(3, a4);
        
        int a[][] = {{1, 2, 0},
        {7, 8, 0},
        {3, 4, 5}
        };
        Node n = new Node(3, a);
        
        TreeSet<Node> tree = new TreeSet<>(new NodeComparator1());
        tree.add(n);
tree.add(n1);
tree.add(n2);
tree.add(n3);
tree.add(n4);

        System.out.println(tree);*/
        
        /*
        int a[][] = {{2, 8, 1},
        {4, 6, 3},
        {7, 5, 0}
        };
        Node n = new Node(3, a);
        //System.out.println(n.isSolved());
        
        //System.out.println(n.heuristicFunction1());
        try {
            System.out.println(Solver.solveWithAlgorithmA(n).size());
            //System.out.println(Solver.solveWithBFS(n).size());
        } catch (IncorrectInputException ex) {
            System.out.println(ex.getMessage());
        }
        */
        /*
        Node n = new Node(4);
        int a[][] = {
            {5,2,3,4},
            {1,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
        };
        n.setNumbers(a);
        System.out.println(n.heuristicFunction2());
*/
        Parent root = FXMLLoader.load(getClass().getResource("mainFXML.fxml"));

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("8 puzzle");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(850);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
