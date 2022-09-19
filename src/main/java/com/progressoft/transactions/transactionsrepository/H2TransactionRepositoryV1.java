package com.progressoft.transactions.transactionsrepository;


import com.progressoft.transactions.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2TransactionRepositoryV1 implements TransactionsRepository {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    // Database credentials
    static final String USER = "jsakkab";
    static final String PASS = "";

    public void save(Transaction t) {
        //TODO convert it to prepared statements (read about it)
        String sql = "INSERT INTO Transaction (description, direction, amount, currency) VALUES (?, ?, ?, ?)";

        System.out.println("The sql statement for save is: " + sql);
        runSQLStatements(sql, "INSERT", t);
    }

    public void resetTable() {
        String sql = "DROP TABLE Transaction";
        runSQLStatements(sql, "DELETE", new Transaction());
    }

    public List<Transaction> listTransactions() throws SQLException {
        String sql = "SELECT * FROM Transaction";
        ResultSet rs = runSQLStatements(sql, "READ", new Transaction());
//        System.out.println(rs.toString());
        List<Transaction> list = new ArrayList<>();

        //TODO instead of while(true) use rs.next() for the while loop
        while (!(rs == null)) {
            while (rs.next()) {
                try {
//                int id = rs.getInt("id");
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
                } catch (SQLException e) {
                    //TODO Handle the exception
                }
            }
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
        runSQLStatements(sql, "CREATE", new Transaction());
    }

    //TODO refactor this long method
    private static ResultSet runSQLStatements(String sql, String action, Transaction t) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, t.getDescription());
            stmt.setString(2, "" + t.getDirection());
            stmt.setBigDecimal(3, t.getAmount());
            stmt.setString(4, t.getCurrency());

            switch (action) {
                case "CREATE":
                    System.out.println("Creating a table in given database...");
                    stmt.executeUpdate(sql);
                    System.out.println("Created table in given database...");
                    break;
                case "READ":
                    System.out.println("Connected to database successfully... to insert data into columns");
                    ResultSet rs = stmt.executeQuery(sql);
                    System.out.println(rs.toString());
                    return rs;

                case "INSERT":
                case "DELETE":
                    System.out.println("Connected to database successfully...");
                    stmt.executeUpdate(sql);
                    break;
            }

        } catch (Exception e) {
            //TODO Handle the exception
        }
        System.out.println("Goodbye!");
        return null;
    }



}
