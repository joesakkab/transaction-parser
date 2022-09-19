package com.progressoft.transactions.transactionsrepository;
import com.progressoft.transactions.Transaction;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2TransactionRepository implements TransactionsRepository {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:derby:memory:test";

    // Database credentials
    static final String USER = "jsakkab";
    static final String PASS = "";
//    static Connection CONN;
    static PreparedStatement STMT = null;

    private static Connection getConneciton() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Transaction t) throws SQLException {
        try {
            String sql = "INSERT INTO Transaction (description, direction, amount, currency) VALUES (?, ?, ?, ?)";
            STMT = getConneciton().prepareStatement(sql);
            STMT.setString(1, t.getDescription());
            STMT.setString(2, "" + t.getDirection());
            STMT.setBigDecimal(3, t.getAmount());
            STMT.setString(4, t.getCurrency());
            STMT.executeUpdate(sql);

        } catch (Exception e) {
            throw new SQLException("Please check sql statement");
        }
        System.out.println("Goodbye!");

    }

    @Override
    public List<Transaction> listTransactions() throws SQLException {
        try {

            String sql = "SELECT * FROM Transaction";
            STMT = getConneciton().prepareStatement(sql);
            ResultSet rs = STMT.executeQuery(sql);
            List<Transaction> list = new ArrayList<>();

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
                    throw new SQLException("Please check if table is not empty");
                }
            }
            System.out.println("Goodbye!");
            return list;

        } catch (Exception e) {
            throw new SQLException("Please check sql statement");
        }
    }

    @Override
    public void resetTable() throws SQLException {
        try {
            String sql = "DROP TABLE Transaction";
            STMT = getConneciton().prepareStatement(sql);
            STMT.executeUpdate(sql);
        } catch (Exception e) {
            throw new SQLException("Please check sql statement");
        }
        System.out.println("Goodbye!");

    }

    @Override
    public void createTransactionTable() throws SQLException {
        try {
            String sql = "CREATE TABLE Transaction" +
                    "(id INTEGER AUTO_INCREMENT, " +
                    "description VARCHAR(255), " +
                    "direction ENUM('CREDIT', 'DEBIT'), " +
                    "amount INTEGER, " +
                    "currency VARCHAR(255), " +
                    "PRIMARY KEY (id))";
            System.out.println("CHECK");
            STMT = getConneciton().prepareStatement(sql);
            System.out.println("CHECK 2");
            STMT.executeUpdate(sql);
            System.out.println("CHECK 3");
        } catch (Exception e) {
            throw new SQLException("Please check sql statement");
        }
        System.out.println("Goodbye!");
    }
}
