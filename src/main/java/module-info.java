module rhettdelfierro.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens rhettdelfierro.c195 to javafx.fxml;
    exports rhettdelfierro.c195;
    exports rhettdelfierro.c195.controllers;
    exports rhettdelfierro.c195.models;
    opens rhettdelfierro.c195.controllers to javafx.fxml;
}