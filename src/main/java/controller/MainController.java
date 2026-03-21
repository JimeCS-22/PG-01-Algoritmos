package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import model.Recursion;
import model.RecursionEngine;
import model.TreePainter;
import util.Utility;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController implements Initializable {

    //Elementos de Pantalla Factorial
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

    //Elementos de Pantalla Fibonacci
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
    private Button btnFibReset;
    @javafx.fxml.FXML
    private Button btnFibCalc;
    @FXML
    private Label lblFibN;
    @FXML
    private ToggleButton btnFibSinMemo;
    @FXML
    private ToggleButton btnFibMemo;

    //Elementos de pantalla gráficos
    @FXML
    private BarChart chartTimes;
    @FXML
    private BarChart chartCalls;
    @FXML
    private TabPane mainTabs;

    //Elementos de Pantalla Fact-Fib. Parte Camila
    @FXML
    private TextField txtFieldValueN;
    @FXML
    private Label lblResult;
    @FXML
    private RadioButton btnFactorial;
    @FXML
    private Label lblTdeN;
    @FXML
    private Button btnCalcularFactFib;
    @FXML
    private Button btnLimpiarFactFib;
    @FXML
    private ListView<String> listFactFib;
    @FXML
    private RadioButton btnFibonacci;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();
        setupFibTab();
        setupGraficoTab();
        setupFactFibTab();
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
            lblFibN.setText(String.valueOf(newValue.intValue()));
        });
        btnFibCalc.setOnAction(event -> runFibonacci());
        btnFibReset.setOnAction(e -> resetFibTab());

        ToggleGroup group = new ToggleGroup();
        btnFibMemo.setToggleGroup(group);
        btnFibSinMemo.setToggleGroup(group);
        btnFibMemo.setSelected(true);

        btnFibMemo.setOnAction(e -> {
            btnFibMemo.setSelected(true);
            btnFibSinMemo.setSelected(false);
        });

        btnFibSinMemo.setOnAction(e -> {
            btnFibSinMemo.setSelected(true);
            btnFibMemo.setSelected(false);
        });
    }

    private void resetFibTab() {
        lblFibResult.setText("-");
        lblFibCalls.setText("-");
        lblFibComplexity.setText("-");
        listSeptsFib.getItems().clear();
    }

    private void runFibonacci() {
        int n = (int) sliderFibN.getValue();

        boolean usarMemo = btnFibMemo.isSelected();

        if (usarMemo) {
            engine.computeFibonacci(n); // con memo
        } else {
            engine.computeFibonacciNoMemo(n); // sin memo
        }
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

    // Grafico (Benchmarking)
    private void setupGraficoTab() {
        if (mainTabs == null) return;

        mainTabs.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == null) return;

            if ("Gráfico".equalsIgnoreCase(newTab.getText())) {
                cargarGraficosBenchmark();
            }
        });
    }

    private void cargarGraficosBenchmark() {
        if (chartTimes == null || chartCalls == null) return;

        chartTimes.setAnimated(false);
        chartCalls.setAnimated(false);

        chartTimes.getData().clear();
        chartCalls.getData().clear();

        List<Integer> ns = Arrays.asList(5, 10, 12, 15, 20);

        XYChart.Series<String, Number> sTimeArray = new XYChart.Series<>();
        sTimeArray.setName("Fib Memo Array");
        XYChart.Series<String, Number> sTimeHash = new XYChart.Series<>();
        sTimeHash.setName("Fib Memo HashMap");
        XYChart.Series<String, Number> sTimeRec = new XYChart.Series<>();
        sTimeRec.setName("Fib Recursive");

        XYChart.Series<String, Number> sCallsArray = new XYChart.Series<>();
        sCallsArray.setName("Fib Memo Array");
        XYChart.Series<String, Number> sCallsHash = new XYChart.Series<>();
        sCallsHash.setName("Fib Memo HashMap");
        XYChart.Series<String, Number> sCallsRec = new XYChart.Series<>();
        sCallsRec.setName("Fib Recursive");

        for (int n : ns) {
            BenchResult rArray = benchPromedio(n, Variant.ARRAY, 10);
            BenchResult rHash  = benchPromedio(n, Variant.HASHMAP, 10);
            BenchResult rRec   = benchPromedio(n, Variant.RECURSIVE, 3);

            String cat = String.valueOf(n);

            sTimeArray.getData().add(new XYChart.Data<>(cat, rArray.timeNs));
            sTimeHash.getData().add(new XYChart.Data<>(cat, rHash.timeNs));
            sTimeRec.getData().add(new XYChart.Data<>(cat, rRec.timeNs));

            sCallsArray.getData().add(new XYChart.Data<>(cat, rArray.calls));
            sCallsHash.getData().add(new XYChart.Data<>(cat, rHash.calls));
            sCallsRec.getData().add(new XYChart.Data<>(cat, rRec.calls));
        }

        chartTimes.getData().addAll(sTimeArray, sTimeHash, sTimeRec);
        chartCalls.getData().addAll(sCallsArray, sCallsHash, sCallsRec);
    }

    private static class BenchResult {
        final long value;
        final int calls;
        final long timeNs;

        BenchResult(long value, int calls, long timeNs) {
            this.value = value;
            this.calls = calls;
            this.timeNs = timeNs;
        }
    }

    private enum Variant { RECURSIVE, HASHMAP, ARRAY }

    private BenchResult benchPromedio(int n, Variant variant, int reps) {
        runOnce(n, variant); // warm-up

        long total = 0;
        int calls = 0;
        long value = 0;

        for (int i = 0; i < reps; i++) {
            BenchResult r = runOnce(n, variant);
            total += r.timeNs;
            calls = r.calls;
            value = r.value;
        }

        return new BenchResult(value, calls, total / reps);
    }

    private BenchResult runOnce(int n, Variant variant) {
        AtomicInteger counter = new AtomicInteger(0);
        long t1 = System.nanoTime();

        long value;
        switch (variant) {
            case RECURSIVE -> value = Recursion.fibonacci(n, counter);
            case HASHMAP -> {
                Map<Integer, Long> memo = new HashMap<>();
                value = Recursion.fibMemo(n, memo, counter);
            }
            case ARRAY -> {
                long[] memo = new long[n + 1];
                Arrays.fill(memo, -1);
                value = Recursion.fibMemoArray(n, memo, counter);
            }
            default -> throw new IllegalArgumentException("Variant no soportada: " + variant);
        }

        long t2 = System.nanoTime();
        return new BenchResult(value, counter.get(), t2 - t1);
    }


    ///Métodos para Fact-Fib

    private void setupFactFibTab() {
        ToggleGroup grupo = new ToggleGroup();
        btnFibonacci.setToggleGroup(grupo);
        btnFactorial.setToggleGroup(grupo);
        //verificación de campos vacios
        btnCalcularFactFib.setOnAction(event -> runFactorialInFactFib());
        btnLimpiarFactFib.setOnAction(e -> resetFactFibTab());
    }

    private void resetFactFibTab() {
        lblTdeN.setText("-");
        lblResult.setText("-");
        txtFieldValueN.clear();
        listFactFib.setItems(null);
    }

    private void runFactorialInFactFib() {
        //Valida que el campo de entrada no esté vacío
        if (txtFieldValueN.getText().isEmpty()) {
            showAlert("Campos Vacíos", "Por favor, rellena todos los campos.");
            return;
        }

        int n;

        try {
            //convertir el valor ingresado a un entero.
            n = Integer.parseInt(txtFieldValueN.getText());
        } catch (NumberFormatException ex) {
            showAlert("Dato inválido", "n debe ser un número entero.");
            return;
        }
        //Verifica que el usuario haya seleccionado una opción (Factorial o Fibonacci).
        if (!btnFactorial.isSelected() && !btnFibonacci.isSelected()) {
            showAlert("Selecciona una opción", "Debes elegir Factorial o Fibonacci.");
            return;
        }
        //Mide el tiempo de ejecución del cálculo.
        long t1 = System.nanoTime();

        long result;
        //Invoca el método correspondiente del motor de recursión
        if (btnFactorial.isSelected()) {
            result = engine.computeFactorial(n);
        } else {
            result = engine.computeFibonacci(n);
        }

        long t2 = System.nanoTime();

        // muestra el resultado y el tiempo en la interfaz
        lblResult.setText(Utility.format(result));
        lblTdeN.setText(util.Utility.format(t2 - t1) + " ns");

        //Llena una lista con la descripción de cada paso del proceso
        ObservableList<String> items = FXCollections.observableArrayList();
                for (int i = 0; i < engine.getSteps().size(); i++) {
                    RecursionEngine.Step step = engine.getSteps().get(i);
                    items.add(String.format("[%02d] %s", i + 1, step.description));
                }
                listFactFib.setItems(items);
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null); // Sin texto de cabecera
        alert.setContentText(message);
        alert.showAndWait(); // Muestra el diálogo y espera a que el usuario lo cierre
    }

}
