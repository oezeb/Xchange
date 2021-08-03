package com.oezeb.xchange.utils.quotes.currency_api;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.oezeb.xchange.models.Quote;
import com.oezeb.xchange.utils.Utils;
import com.oezeb.xchange.utils.database.SQLiteJDBC;

import org.json.JSONObject;

/**
 * Quotes source : currency-api
 * Daily Updated
 * Website: https://github.com/fawazahmed0/currency-api
 * Request: https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@{apiVersion}/{date}/{endpoint}
 * e.g:
 *      Request: https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur/jpy.json
 *      Response: 
 *          {
 *              "date": "2021-08-02",
 *              "jpy": 130.129996
 *          }
 */
public class CurrencyApi {
    static private String baseUrl = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/";
    private HashMap<String, Quote> map;
    private SQLiteJDBC sqLiteJDBC;

    public CurrencyApi() {
        sqLiteJDBC = null;
        map = new HashMap<>();
        try {
            /**
             * get quotes from local data base
             */
            sqLiteJDBC = new SQLiteJDBC("currency_api.db");
            map = sqLiteJDBC.getQuotes();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Quote getQuote(String base, String second) throws Exception {
        String id = base+"/"+second;
        Quote quote1 = null;
        Quote quote2 = null;
        try {
            // search in local data base
            quote1 = map.get(id);
            if(quote1 != null && quote1.date.equals(Date.valueOf(LocalDate.now()))) return quote1;

            // search online
            URL url = new URL(baseUrl + base + "/" + second + ".json");
            String data = Utils.streamToString(url.openConnection().getInputStream());
            JSONObject jo = new JSONObject(data);
            Map<String, Object> map = jo.toMap();
            BigDecimal rate = (BigDecimal)map.get(second);
            Date date = Date.valueOf((String)map.get("date"));
            quote2 = new Quote(base, second, rate.doubleValue(), date);

            // save quote to local data base
            if(sqLiteJDBC != null) sqLiteJDBC.insertQuote(id, quote2);

            return quote2;
        } catch (Exception e) {
            if(quote2 != null) return quote2;
            if(quote1 != null) return quote1;
            throw e;
        }
    }
}



