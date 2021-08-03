package com.oezeb.xchange.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Utils {
    public static String streamToString(InputStream stream) throws Exception {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            while(stream.available() != 0) {
                stringBuffer.append((char)stream.read());
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public static HashMap<String, String> getCurrenciesMap() {
        File f = new File("assets/currencies/currencies.min.json");
        String data;
        try {
            FileInputStream fs = new FileInputStream(f);
            data = streamToString(fs);
        }
        catch (Exception e) {
            data = majors;
        }
        JSONObject jo = new JSONObject(data);
        Map<String, Object> map = jo.toMap();
        HashMap<String, String> ans = new HashMap<>();
        for (String string : map.keySet()) {
            ans.put((String)map.get(string), string);
        }
        return ans;
    }
    
    public static String[] getCurrencies() {
        Map<String, String> map = getCurrenciesMap();
        String[] symbols = map.keySet().toArray(new String[0]);
        Arrays.sort(symbols);
        return symbols;
    }

    static String majors = "{"+
        "'aud': 'Australian dollar',"+
        "'btc': 'Bitcoin',"+
        "'cad': 'Canadian dollar',"+
        "'cny': 'Chinese Yuan',"+
        "'eur': 'Euro',"+
        "'gbp': 'Pound sterling',"+
        "'hkd': 'Hong Kong dollar',"+
        "'jpy': 'Japanese yen',"+
        "'usd': 'United States dollar',"+
    "}";
}
