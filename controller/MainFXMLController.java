package controller;

import java.awt.Point;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.Node;
import model.Solver;

public class MainFXMLController implements Initializable {

    @FXML
    private ComboBox<Byte> comboBox;

    @FXML
    private Label mainLabel;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private AnchorPane mainAnchorPane, leftAnchorPane, rightAnchorPane;

    @FXML
    private Button shuffleButton, solveButton, resultButton;

    @FXML
    private Label bfsLabel, f1Label, f2Label, resultLabel;

    @FXML
    private CheckBox bfsCheckBox, f1CheckBox, f2CheckBox;

    private HashMap<String, Button> buttons = new HashMap<>();
    private HashMap<Button, Point> buttonsInGrid = new HashMap<>();
    private HashMap<Point, Button> buttonsWithPoint = new HashMap<>();
    private Point emptyButtonPoint = new Point();
    private byte number;
    private boolean isShuffling;
    private ArrayList<Node> answer;

    private Button emptyButton;

    private double time = 0;

    private Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(number <= 4 ? 0.01 : (number <= 7) ? 0.0001 : 0.000001), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            Random random = new Random();
            while (true) {
                int k = random.nextInt(4);
                Point p = new Point(emptyButtonPoint);
                if (k == 0) {
                    p.x++;
                } else if (k == 1) {
                    p.x--;
                } else if (k == 2) {
                    p.y++;
                } else if (k == 3) {
                    p.y--;
                }
                if (p.x >= 0 && p.x < number && p.y >= 0 && p.y < number) {
                    changeButtons(buttonsWithPoint.get(p));
                    break;
                }
            }
        }
    }));

    private Timeline solveTimeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(0.5), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            Point p = answer.get(0).getZeroIndex();
            answer.remove(0);
            int z = p.x;
            p.x = p.y;
            p.y = z;
            changeButtons(buttonsWithPoint.get(p));
            if (answer.size() == 0) {
                solveButton.setDisable(false);
                solveTimeline.stop();
            }
        }
    }));

    private Timeline ansTimeLine = new Timeline(new KeyFrame(javafx.util.Duration.seconds(0.1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            DecimalFormat formatter = new DecimalFormat("#0.0");
            if (bfsCheckBox.isSelected()) {
                bfsLabel.setText("სრული გადარჩევა - " + formatter.format(time) + " წამი.");
            }
            if (f1CheckBox.isSelected()) {
                f1Label.setText("ფუნქცია 1 - " + formatter.format(time) + " წამი.");
            }
            if (f2CheckBox.isSelected()) {
                f2Label.setText("ფუნქცია 2 - " + formatter.format(time) + " წამი.");
            }
            if (!bfsCheckBox.isSelected() && !f1CheckBox.isSelected() && !f2CheckBox.isSelected()) {
                resultButton.setVisible(true);
                ansTimeLine.stop();
            }
            time += 0.1;
            if (time >= 20) {
                Solver.stopBFS = true;
                Solver.stopF1 = true;
            }
            if (time >= 45) {
                Solver.stopF2 = true;
            }
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBox();
        initButtons((byte) 3);
    }

    /**
     * დაფის სიგრძის ComboBox-ს ინიციალიზაცია
     */
    public void initComboBox() {
        number = 3;
        comboBox.getItems().clear();
        comboBox.getItems().addAll((byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10);
        comboBox.setValue((byte) 3);
    }

    /**
     * ღილაკების ინიციალიზაცია გარკვეული სიგრძის დაფისთვის
     *
     * @param number დაფის სიგრძე
     */
    public void initButtons(final byte number) {
        this.number = number;
        mainGridPane.getChildren().clear();
        buttons.clear();

        ArrayList<ColumnConstraints> columnConstraints = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ColumnConstraints c = new ColumnConstraints(10.0, 10.0, 10000);
            c.setHgrow(Priority.SOMETIMES);
            columnConstraints.add(c);
        }
        mainGridPane.getColumnConstraints().setAll(columnConstraints);

        ArrayList<RowConstraints> rowConstraints = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            RowConstraints r = new RowConstraints(10.0, 10.0, 100000);
            r.setVgrow(Priority.SOMETIMES);
            rowConstraints.add(r);
        }
        mainGridPane.getRowConstraints().setAll(rowConstraints);

        for (int i = 0; i < number * number - 1; i++) {
            final Button btn = new Button(Integer.toString(i + 1));
            btn.setMnemonicParsing(false);
            btn.setContentDisplay(ContentDisplay.CENTER);
            btn.setMaxHeight(Double.MAX_VALUE);
            btn.setMaxWidth(Double.MAX_VALUE);
            if (number <= 5) {
                btn.setStyle("-fx-font-size:40; -fx-font-family:sylfaen; -fx-background-color:#81BEF7; -fx-text-fill:white;");
            } else if (number == 6) {
                btn.setStyle("-fx-font-size:32; -fx-font-family:sylfaen; -fx-background-color:#81BEF7; -fx-text-fill:white;");
            } else if (number == 7) {
                btn.setStyle("-fx-font-size:28; -fx-font-family:sylfaen; -fx-background-color:#81BEF7; -fx-text-fill:white;");
            } else if (number <= 9) {
                btn.setStyle("-fx-font-size:20; -fx-font-family:sylfaen; -fx-background-color:#81BEF7; -fx-text-fill:white;");
            } else {
                btn.setStyle("-fx-font-size:18; -fx-font-family:sylfaen; -fx-background-color:#81BEF7; -fx-text-fill:white;");
            }
            mainGridPane.add(btn, i % number, i / number);
            buttons.put(Integer.toString(i + 1), btn);
            buttonsInGrid.put(btn, new Point(i % number, i / number));
            buttonsWithPoint.put(new Point(i % number, i / number), btn);
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Point point = buttonsInGrid.get(btn);
                    if (point.x == emptyButtonPoint.x - 1 && point.y == emptyButtonPoint.y
                            || point.x == emptyButtonPoint.x + 1 && point.y == emptyButtonPoint.y
                            || point.x == emptyButtonPoint.x && point.y == emptyButtonPoint.y - 1
                            || point.x == emptyButtonPoint.x && point.y == emptyButtonPoint.y + 1) {
                        changeButtons(btn);
                    }
                }
            });
        }
        final Button btn = new Button("");
        btn.setMnemonicParsing(false);
        btn.setContentDisplay(ContentDisplay.CENTER);
        btn.setMaxHeight(Double.MAX_VALUE);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color:white;");
        mainGridPane.add(btn, number - 1, number - 1);
        buttons.put("", btn);
        emptyButton = btn;
        emptyButtonPoint = new Point(number - 1, number - 1);

        mainGridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    if (emptyButtonPoint.getY() == number - 1) {
                        event.consume();
                        return;
                    }
                    changeButtons(buttonsWithPoint.get(new Point(emptyButtonPoint.x, emptyButtonPoint.y + 1)));
                    event.consume();
                }
                if (event.getCode() == KeyCode.DOWN) {
                    if (emptyButtonPoint.getY() == 0) {
                        event.consume();
                        return;
                    }
                    changeButtons(buttonsWithPoint.get(new Point(emptyButtonPoint.x, emptyButtonPoint.y - 1)));
                    event.consume();
                }
                if (event.getCode() == KeyCode.LEFT) {
                    if (emptyButtonPoint.getX() == number - 1) {
                        event.consume();
                        return;
                    }
                    changeButtons(buttonsWithPoint.get(new Point(emptyButtonPoint.x + 1, emptyButtonPoint.y)));
                    event.consume();
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    if (emptyButtonPoint.getX() == 0) {
                        event.consume();
                        return;
                    }
                    changeButtons(buttonsWithPoint.get(new Point(emptyButtonPoint.x - 1, emptyButtonPoint.y)));
                    event.consume();
                }
            }
        });

    }

    public void changeButtons(Button btn) {
        mainGridPane.getChildren().remove(btn);
        mainGridPane.getChildren().remove(emptyButton);
        mainGridPane.add(btn, emptyButtonPoint.x, emptyButtonPoint.y);
        mainGridPane.add(emptyButton, buttonsInGrid.get(btn).x, buttonsInGrid.get(btn).y);
        buttonsWithPoint.remove(new Point(buttonsInGrid.get(btn).x, buttonsInGrid.get(btn).y));
        Point point = emptyButtonPoint;
        emptyButtonPoint = new Point(buttonsInGrid.get(btn).x, buttonsInGrid.get(btn).y);
        //buttonsInGrid.remove(btn);
        buttonsInGrid.put(btn, point);
        //buttonsInGrid.replace(btn, point);
        buttonsWithPoint.put(point, btn);
    }

    public void actionOnComboBox() {
        initButtons(comboBox.getValue());
    }

    public void actionOnshuffleButton() {
        if (!isShuffling) {
            isShuffling = true;
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            comboBox.setDisable(true);
            shuffleButton.setText("გაჩერება");
            solveButton.setDisable(true);
        } else {
            isShuffling = false;
            timeline.stop();
            comboBox.setDisable(false);
            shuffleButton.setText("არევა");
            solveButton.setDisable(false);
            bfsCheckBox.setSelected(true);
            f1CheckBox.setSelected(true);
            f2CheckBox.setSelected(true);
        }
    }

    public void actionOnSolveButton() {
        time = 0;
        resultLabel.setText("");
        Solver.stopBFS = false;
        Solver.stopF1 = false;
        Solver.stopF2 = false;
        solveButton.setDisable(true);
        byte numbers[][] = new byte[number][number];
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                if (i != emptyButtonPoint.x || j != emptyButtonPoint.y) {
                    Button btn = buttonsWithPoint.get(new Point(i, j));
                    numbers[j][i] = Byte.parseByte(btn.getText());
                } else {
                    numbers[j][i] = 0;
                }
            }
        }
        final Node node = new Node(number, numbers);

        //ArrayList<Node> bfsAns, f1Ans, f2Ans = new ArrayList<>();
        ansTimeLine.setCycleCount(Timeline.INDEFINITE);
        ansTimeLine.play();
        if (bfsCheckBox.isSelected()) {
            bfsLabel.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:green;");
            Thread thread1 = new Thread(new Runnable() {

                @Override
                public void run() {
                    if (Solver.solveWithBFS(node) == null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                bfsLabel.setText("მეხსიეერრების გადავსება!");
                                bfsLabel.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:red;");
                            }
                        });

                    }
                    bfsCheckBox.setSelected(false);
                }
            });
            thread1.start();
        } else {
            bfsLabel.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:red;");
        }
        if (f1CheckBox.isSelected()) {
            f1Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:green;");
            Thread thread2 = new Thread(new Runnable() {

                @Override
                public void run() {
                    if (Solver.solveWithAlgorithmA1(node) == null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                f1Label.setText("მეხსიეერრების გადავსება!");
                                f1Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:red;");
                            }
                        });

                    }
                    f1CheckBox.setSelected(false);
                }
            });
            thread2.start();
        } else {
            f1Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:red;");
        }
        // ყველა ვარიანტში ირთვება
        f2Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:green;");
        Thread thread3 = new Thread(new Runnable() {

            @Override
            public void run() {
                answer = Solver.solveWithAlgorithmA2(node);
                if (answer == null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            f2Label.setText("მეხსიეერრების გადავსება!");
                            f2Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:red;");
                        }
                    });

                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            resultLabel.setText("სიღრმე - " + answer.size());
                            resultLabel.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:green;");
                        }
                    });

                }
                f2CheckBox.setSelected(false);
            }
        });

        thread3.start();

    }

    public void actionOnResultButton() {
        resultButton.setVisible(false);
        answer.remove(0);
        solveTimeline.setCycleCount(Timeline.INDEFINITE);
        solveTimeline.play();
        solveButton.setDisable(true);
        f1Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:black;");
        f2Label.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:black;");
        bfsLabel.setStyle("-fx-font-size:15; -fx-font-family:sylfaen; -fx-text-fill:black;");
    }

}
