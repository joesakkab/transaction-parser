package com.progressoft.transactions.transactionsrepository;


import com.progressoft.transactions.Transaction;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2TransactionRepository implements TransactionsRepository {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    // Database credentials
    static final String USER = "jsakkab";
    static final String PASS = "";

    public void save(Transaction t) {
        String sql = "INSERT INTO Transaction (description, direction, amount, currency) VALUES ( '" +
                t.getDescription() + "', '" +
                t.getDirection() + "', '" +
                t.getAmount() + "', '" +
                t.getCurrency() + "')";
        System.out.println("The sql statement for save is: " + sql);
        runSQLStatements(sql, "INSERT");


    }

    public void resetTable(String action) {
        String sql = "DROP TABLE Transaction";
//        switch (action) {
//            case "DROP":
//                sql = "DROP TABLE Transaction";
//            case "DELETE":
//                sql = "TRUNCATE TABLE Transaction";
//        }

        runSQLStatements(sql, "DELETE");

    }

    public List<Transaction> listTransactions() throws SQLException {
        String sql = "SELECT * FROM Transaction";
        ResultSet rs = runSQLStatements(sql, "READ");
        System.out.println(rs.toString());
        List<Transaction> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String description = rs.getString("description");
            String direction = rs.getString("direction");
            BigDecimal amount = new BigDecimal(rs.getInt("amount"));
            String currency = rs.getString("currency");


            Transaction t = new Transaction();
            t.setDescription(description);
            t.setDirection(direction);
            t.setAmount(amount);
            t.setCurrency(currency);
            list.add(t);
        }
        return list;
    }

    public void createTransactionTable() {
        String sql = "CREATE TABLE Transaction" +
                "(id INTEGER AUTO_INCREMENT, " +
                "description VARCHAR(255), " +
                "direction ENUM('CREDIT', 'DEBIT'), " +
                "amount INTEGER, " +
                "currency VARCHAR(255), " +
                "PRIMARY KEY (id))";
        runSQLStatements(sql, "CREATE");
    }

    private static ResultSet runSQLStatements(String sql, String action) {
        Connection conn = null;
        Statement stmt = null;

        try {

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            switch (action) {
                case "CREATE":
                    System.out.println("Creating a table in given database...");
                    stmt.executeUpdate(sql);
                    System.out.println("Created table in given database...");
                    break;
                case "READ":
                    System.out.println("Connected to database successfully... to insert data into columns");
                    ResultSet rs =  stmt.executeQuery(sql);
                    System.out.println(rs.toString());
                    return rs;

                case "INSERT":
                case "DELETE":
                    System.out.println("Connected to database successfully...");
                    stmt.executeUpdate(sql);
                    break;
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } // end try
        System.out.println("Goodbye!");
        return null;
    }

}
