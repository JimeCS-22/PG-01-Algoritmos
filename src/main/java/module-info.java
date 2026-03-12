module ucr.pg01 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.pg01 to javafx.fxml;
    exports ucr.pg01;
    exports controller;
    opens controller to javafx.fxml;
}