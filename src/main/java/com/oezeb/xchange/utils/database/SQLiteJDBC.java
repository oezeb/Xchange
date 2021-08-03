package com.oezeb.xchange.utils.database;

import java.sql.*;
import java.util.HashMap;

import com.oezeb.xchange.models.Quote;

public class SQLiteJDBC {
    private String fileName;
    private Connection connection;
    private Statement statement;

    public SQLiteJDBC(String fileName) throws Exception {
        this.fileName = fileName;
        createTable();
    }

    public void createTable() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:assets/database/" + fileName);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Quote " +
                "(ID TEXT PRIMARY KEY    NOT NULL," +
                " BASE           TEXT    NOT NULL," +
                " SECOND         TEXT    NOT NULL," +
                " RATE           REAL    NOT NULL, " +
                " DATE           TEXT    NOT NULL)");
        } catch (Exception e) {
            throw e;
        }
    }

    public void insertQuote(String id, Quote quote) throws Exception {
        try {
            String sql = "INSERT INTO Quote (ID,BASE,SECOND,RATE,DATE) " +
            "VALUES ('" + id + "', '" + quote.base + "', '" + quote.second + "', " + quote.rate + ", '" + quote.date.toString() + "');";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            UpdateQuote(id, quote);
        }
    }

    public void UpdateQuote(String id, Quote quote) throws Exception {
        try {
            String sql = "UPDATE Quote set (BASE,SECOND,RATE,DATE) = ('" + quote.base + "', '" + quote.second + "', " + quote.rate + ", '" + quote.date.toString() + "'') where ID='" + id +"';";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public HashMap<String , Quote> getQuotes() throws Exception {
        try {
            ResultSet rSet = statement.executeQuery("SELECT * FROM Quote");
            HashMap<String, Quote> map = new HashMap<>();
            while(rSet.next()) {
                String id = rSet.getString("id");
                Quote quote = new Quote(rSet.getString("base"), rSet.getString("second"), rSet.getDouble("rate"), Date.valueOf(rSet.getString("date")));
                map.put(id, quote);
            }
            return map;
        } catch (Exception e) {
            throw e;
        }
    }
}