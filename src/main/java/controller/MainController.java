package controller;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController implements Initializable {
    @javafx.fxml.FXML
    private Canvas canvasTree;
    @javafx.fxml.FXML
    private Label lblFactN;
    @javafx.fxml.FXML
    private Button btnFactCalc;
    @javafx.fxml.FXML
    private Slider sliderFactN;
    @javafx.fxml.FXML
    private Label lblFactResult;
    @javafx.fxml.FXML
    private Label lblComplexity;
    @javafx.fxml.FXML
    private Label lblFactCalls;
    @javafx.fxml.FXML
    private ListView<String>listSteps;
    @javafx.fxml.FXML
    private Button btnFactReset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();

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
        AtomicInteger counter = new AtomicInteger(0);
        long result = model.Recursion.factorial(n, counter);
        lblFactResult.setText(util.Utility.format(result));
        lblFactCalls.setText(String.valueOf(counter.get()));

    }

}
