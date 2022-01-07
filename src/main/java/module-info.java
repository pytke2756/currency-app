module hu.pytke.currencyapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens hu.pytke.currencyapp to javafx.fxml;
    exports hu.pytke.currencyapp;
}