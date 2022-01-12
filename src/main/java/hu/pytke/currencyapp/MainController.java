package hu.pytke.currencyapp;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class MainController {

    @FXML
    private TextField input_quantity_from;
    @FXML
    private ComboBox<String> cb_to_currency;
    @FXML
    private ComboBox<String> cb_from_currency;
    @FXML
    private Label lbl_total;

    private ArrayList<String> currencies;

    public void initialize() throws IOException {
        currencies = currencies();
        rendez(currencies);
        cb_from_currency.getItems().addAll(currencies);
        cb_from_currency.getSelectionModel().selectFirst();
        cb_to_currency.getItems().addAll(currencies);
        cb_to_currency.getSelectionModel().selectFirst();
    }

    private ArrayList<String> currencies() throws IOException {
        ArrayList<String> currList = new ArrayList<>();

        URL url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        System.out.println("Status code: " + status);

        BufferedReader br;
        String line;
        StringBuilder responseContent = new StringBuilder();
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        line = br.readLine();
        while(line != null){
            responseContent.append(line);
            line = br.readLine();
        }

        JSONObject jsonObject = new JSONObject((responseContent.toString()));

        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String item = it.next();
            currList.add(item);
        }

        for (String item : currList) {
            System.out.println(item);
        }


        return currList;
    }

    private String change(String from, String to) throws IOException {
        String urString = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/2022-01-06/currencies/" + from + "/" + to + ".json";
        URL url = new URL(urString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        System.out.println("Status code: " + status);

        BufferedReader br;
        String line;
        StringBuilder responseContent = new StringBuilder();

        br = new BufferedReader(new InputStreamReader(con.getInputStream()));


        line = br.readLine();
        while(line != null){
            responseContent.append(line);
            line = br.readLine();
        }

        JSONObject jsonObject = new JSONObject((responseContent.toString()));

        //System.out.println(jsonObject.getFloat(to));

        float quantity = Float.parseFloat(input_quantity_from.getText());
        float simpleChange = jsonObject.getFloat(to);
        float totalAmout = quantity * simpleChange;

        return String.valueOf(totalAmout);
    }

    @FXML
    public void quantityChanged(Event event) throws IOException {
        System.out.println("asd");
        String fromValue = cb_from_currency.getValue();
        String toValue = cb_to_currency.getValue();

        String urString = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/2022-01-06/currencies/" + fromValue + "/" + toValue + ".json";
        URL url = new URL(urString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        System.out.println("Status code: " + status);

        BufferedReader br;
        String line;
        StringBuilder responseContent = new StringBuilder();

        br = new BufferedReader(new InputStreamReader(con.getInputStream()));


        line = br.readLine();
        while(line != null){
            responseContent.append(line);
            line = br.readLine();
        }

        JSONObject jsonObject = new JSONObject((responseContent.toString()));

        //System.out.println(jsonObject.getFloat(to));

        float quantity = Float.parseFloat(input_quantity_from.getText());
        float simpleChange = jsonObject.getFloat(toValue);
        float totalAmout = quantity * simpleChange;

        lbl_total.setText(String.valueOf(totalAmout));
    }

    private void rendez(ArrayList<String> list){
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }
}