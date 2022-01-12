package hu.pytke.currencyapp;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainController {

    @FXML
    private TextField input_quantity_from;
    @FXML
    private ComboBox<String> cb_to_currency;
    @FXML
    private ComboBox<String> cb_from_currency;
    @FXML
    private Label lbl_total;

    public void initialize() throws IOException {
        cb_from_currency.getItems().addAll(ApiRequestHelper.getAllCurrencies());
        cb_from_currency.getSelectionModel().selectFirst();
        cb_to_currency.getItems().addAll(ApiRequestHelper.getAllCurrencies());
        cb_to_currency.getSelectionModel().selectFirst();
    }

    @FXML
    public void quantityChanged(Event event) throws IOException {
        String fromValue = cb_from_currency.getValue();
        String toValue = cb_to_currency.getValue();

        String getInput = input_quantity_from.getText();
        float totalAmout = ApiRequestHelper.quantityChanged(fromValue, toValue, getInput);

        lbl_total.setText(String.valueOf(totalAmout));
    }


}