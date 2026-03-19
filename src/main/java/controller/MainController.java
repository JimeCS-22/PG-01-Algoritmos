package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import model.Recursion;
import model.RecursionEngine;
import model.TreePainter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private Canvas canvasTree;
    @javafx.fxml.FXML
    private Slider sliderFactN;
    @javafx.fxml.FXML
    private Label lblFactN;
    @javafx.fxml.FXML
    private Button btnFactReset;
    @javafx.fxml.FXML
    private Button btnFactCalc;
    @javafx.fxml.FXML
    private Label lblComplexity;
    @javafx.fxml.FXML
    private Label lblFactCalls;
    @javafx.fxml.FXML
    private Label lblFactResult;
    @javafx.fxml.FXML
    private ListView<String> listSteps; //lista de pasos recursivos

    //atributos internos de la clase controller
    private final RecursionEngine engine = new RecursionEngine();
    private final TreePainter painter = new TreePainter();
    private RecursionEngine.CallNode lastRoot;
    private List<RecursionEngine.CallNode> factBFS;
    @javafx.fxml.FXML
    private Label lblFibComplexity;
    @javafx.fxml.FXML
    private Label lblFibResult;
    @javafx.fxml.FXML
    private Canvas canvasTreeFibo;
    @javafx.fxml.FXML
    private Slider sliderFibN;
    @javafx.fxml.FXML
    private Label lblFibCalls;
    @FXML
    private ListView<String> listSeptsFib;
    @javafx.fxml.FXML
    private Label lblFibN;
    @javafx.fxml.FXML
    private Button btnFibReset;
    @javafx.fxml.FXML
    private Button btnFibCalc;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();
        setupFibTab();
    }

    private void setupFactTab() {
        sliderFactN.setMin(1); sliderFactN.setMax(12); sliderFactN.setValue(5);
        sliderFactN.setMajorTickUnit(1); sliderFactN.setSnapToTicks(true);
        sliderFactN.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblFactN.setText(String.valueOf(newValue.intValue()));
        });
        btnFactCalc.setOnAction(event -> runFactorial());
        btnFactReset.setOnAction(e -> resetFactTab());
    }

    private void resetFactTab() {
        lblFactResult.setText("-");
        lblFactCalls.setText("-");
        lblComplexity.setText("-");
        listSteps.getItems().clear();
    }

    private void runFactorial() {
        int n = (int) sliderFactN.getValue();
//        AtomicInteger counter = new AtomicInteger(0);
//        long result = Recursion.factorial(n, counter);
//        lblFactResult.setText(util.Utility.format(result));
//        lblFactCalls.setText(String.valueOf(counter));

        engine.computeFactorial(n);
        lastRoot = engine.getTreeRoot();
        factBFS = TreePainter.collectBFS(lastRoot);

        //llenamos la lista de pasos
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i+1, step.description));
        }
        listSteps.setItems(items); //setteamos la lista de pasos recursivos
        lblFactResult.setText(util.Utility.format(engine.getTreeRoot().result));
        lblFactCalls.setText(String.valueOf(engine.getCallCount()));
        lblComplexity.setText("O(n) = O(" + n + ") llamadas");

        //dibujamos el árbol de llamadas en el canva
        painter.paint(canvasTree, lastRoot, factBFS.size(), factBFS);
    }

    private void setupFibTab(){

        sliderFibN.setMin(1);
        sliderFibN.setMax(15);
        sliderFibN.setValue(5);

        sliderFibN.setMajorTickUnit(1);
        sliderFibN.setSnapToTicks(true);

        sliderFibN.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblFibN.setText("n = " + newValue.intValue());
        });
        btnFibCalc.setOnAction(event -> runFibonacci());
        btnFibReset.setOnAction(e -> resetFibTab());
    }

    private void resetFibTab() {
        lblFibResult.setText("-");
        lblFibCalls.setText("-");
        lblFibComplexity.setText("-");
        listSeptsFib.getItems().clear();
    }

    private void runFibonacci() {
        int n = (int) sliderFibN.getValue();

        engine.computeFibonacci(n);
        lastRoot = engine.getTreeRoot();
        List<RecursionEngine.CallNode> fibBFS = TreePainter.collectBFS(lastRoot);

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i + 1, step.description));
        }
        listSeptsFib.setItems(items);

        lblFibResult.setText(util.Utility.format(engine.getTreeRoot().result));
        lblFibCalls.setText(String.valueOf(engine.getCallCount()));

        lblFibComplexity.setText("O(2^n) ≈ O(2^" + n + ") llamadas");

        painter.paint(canvasTreeFibo, lastRoot, fibBFS.size(), fibBFS);
    }


}
