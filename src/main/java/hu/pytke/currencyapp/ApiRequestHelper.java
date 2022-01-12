package hu.pytke.currencyapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ApiRequestHelper {
    private static final String CURRENCIES = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json";
    private static final String GET = "GET";

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
}
