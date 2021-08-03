package com.oezeb.xchange.views_models;

import java.util.HashMap;

import com.oezeb.xchange.models.Quote;
import com.oezeb.xchange.utils.Utils;
import com.oezeb.xchange.utils.quotes.currency_api.CurrencyApi;


public class HomeViewModel {
    public String textField1String = "";
    public String textField2String= "";
    private Quote quote = new Quote();
    private CurrencyApi currencyApi = new CurrencyApi();
    private HashMap<String , String> currenciesMap = Utils.getCurrenciesMap(); 
    
    public void setTextField1String(String string) {
        textField1String = string;
        try {
            if(quote.base.equals(quote.second))
                quote.rate = 1.0;
            else
                quote = currencyApi.getQuote(quote.base, quote.second);
            Double val = Double.parseDouble(textField1String)*quote.rate;
            val = Math.round(val*100)/100.0;
            textField2String = val.toString();
        }
        catch (Exception e) {

        }
    }

    public void setTextField2String(String string) {
        textField2String = string;
        try {
            quote = currencyApi.getQuote(quote.base, quote.second);
            Double val = Double.parseDouble(textField2String)/quote.rate;
            val = Math.round(val*100)/100.0;
            textField1String = val.toString();
        }
        catch (Exception e) {

        }
    }

    public void setBaseCurrency(String string) {
        quote.base = currenciesMap.get(string).toLowerCase();
        setTextField1String(textField1String);
    }

    public void setSecondCurrency(String string) {
        quote.second = currenciesMap.get(string).toLowerCase();
        setTextField1String(textField1String);
    }
}
