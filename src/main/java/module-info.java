module rhettdelfierro.c195 {
    requires javafx.controls;
    requires javafx.fxml;


    opens rhettdelfierro.c195 to javafx.fxml;
    exports rhettdelfierro.c195;
    exports rhettdelfierro.c195.controllers;
    opens rhettdelfierro.c195.controllers to javafx.fxml;
}