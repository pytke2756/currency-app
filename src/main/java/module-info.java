module hu.pytke.currencyapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.pytke.currencyapp to javafx.fxml;
    exports hu.pytke.currencyapp;
}