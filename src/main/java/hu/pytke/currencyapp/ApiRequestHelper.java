package hu.pytke.currencyapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ApiRequestHelper {
    private static final String CURRENCIES = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/2022-01-11/currencies.json";
    private static final String CHANGE_CURRENCIES = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/2022-01-11/currencies/";
    // hozzá kell fűzni mindig a két valutát pl : "huf/eur.json"
    private static final String GET = "GET";
    private static ArrayList<String> dates;

    private static URL url;

    private static BufferedReader br;
    private static StringBuilder responseContent;


    public static ArrayList<String> getAllCurrencies() throws IOException {
        ArrayList<String> currList = new ArrayList<>();

        url = new URL(CURRENCIES);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(GET);
        con.setRequestProperty("Content-Type", "application/json");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        System.out.println("Status code: " + status);

        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        responseContent = response(br);

        JSONObject jsonObject = new JSONObject((responseContent.toString()));

        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String item = it.next();
            currList.add(item);
        }
        rendez(currList);
        return currList;
    }

    public static float quantityChanged(String fromValue, String toValue, String quantityFrom) throws IOException {

        String changeUrl = (new StringBuilder().append(CHANGE_CURRENCIES)
                                .append(fromValue)
                                .append("/")
                                .append(toValue)
                                .append(".json"))
                                .toString();

        //String changeUrlMethodTwo = CHANGE_CURRENCIES + fromValue + "/" + toValue + ".json";
        URL url = new URL(changeUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(GET);

        con.setRequestProperty("Content-Type", "application/json");

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        System.out.println("Status code: " + status);

        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        responseContent = response(br);

        JSONObject jsonObject = new JSONObject((responseContent.toString()));

        //System.out.println(jsonObject.getFloat(to));

        float quantity = Float.parseFloat(quantityFrom);
        float simpleChange = jsonObject.getFloat(toValue);
        float totalAmout = quantity * simpleChange;

        return totalAmout;
    }


    private static StringBuilder response(BufferedReader br) throws IOException {
        responseContent = new StringBuilder();
        String line = br.readLine();
        while (line != null){
            responseContent.append(line);
            line = br.readLine();
        }
        return responseContent;
    }

    private static void rendez(ArrayList<String> list){
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    private static void daysCountToDatesList(){
        dates = new ArrayList<>();

        LocalDate now = LocalDate.now();
        now = now.minusDays(1);
        LocalDate first = LocalDate.parse("2020-11-22", DateTimeFormatter.ISO_LOCAL_DATE);

        Duration diff = Duration.between(first.atStartOfDay(), now.atStartOfDay());
        long diffDays = diff.toDays();
        for (int i = 0; i <= diffDays; i++) {
            dates.add(first.toString());
            first = first.plusDays(1);
        }
    }
}
