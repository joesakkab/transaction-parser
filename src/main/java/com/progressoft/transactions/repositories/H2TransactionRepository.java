package com.progressoft.transactions.repositories;
import com.progressoft.transactions.Transaction;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import org.h2.Driver;

public class H2TransactionRepository implements TransactionsRepository {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    // Database credentials
    static final String USER = "jsakkab";
    static final String PASS = "";
//    static Connection CONN;
    static PreparedStatement STMT = null;
    static final String TABLENAME = "Transaction";
    static Connection CONN = null;

    public H2TransactionRepository() {
        CONN = getConnection();
    }

    private static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            throw new H2TransactionRepositoryException("The message is: " + e.getMessage(),e.getCause());
        }
    }


    @Override
    public void save(Transaction t) {
        try {
            String sql = "INSERT INTO " + TABLENAME + " (description, direction, amount, currency) VALUES (?, ?, ?, ?)";
            STMT = CONN.prepareStatement(sql);
            STMT.setString(1, t.getDescription());
            STMT.setString(2, "" + t.getDirection());
            STMT.setBigDecimal(3, t.getAmount());
            STMT.setString(4, t.getCurrency());
            STMT.executeUpdate();

        } catch (Exception e) {
            throw new H2TransactionRepositoryException(e.getMessage(), e.getCause());
        }
//        System.out.println("Goodbye!");

    }

    @Override
    public List<Transaction> listTransactions() {
        try {

            String sql = "SELECT * FROM " + TABLENAME;
            STMT = CONN.prepareStatement(sql);
            ResultSet rs = STMT.executeQuery();
            List<Transaction> list = new ArrayList<>();

            while (rs.next()) {
                try {
                    String description = rs.getString("description");
                    String direction = rs.getString("direction");
                    BigDecimal amount = new BigDecimal(rs.getInt("amount"));
                    String currency = rs.getString("currency");

                    Transaction t = new Transaction(description, direction, amount, currency);
                    list.add(t);
                } catch (SQLException e) {
                    throw new H2TransactionRepositoryException(e.getMessage(), e.getCause());
                }
            }
            return list;

        } catch (Exception e) {
            throw new H2TransactionRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void resetTable() {
        try {
            String sql = "DROP TABLE " + TABLENAME;
            STMT = CONN.prepareStatement(sql);
            STMT.executeUpdate();
        } catch (Exception e) {
            throw new H2TransactionRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void createTransactionTable() {
        try {
            String sql = "CREATE TABLE " + TABLENAME +
                    "(id INTEGER AUTO_INCREMENT, " +
                    "description VARCHAR(255), " +
                    "direction ENUM('CREDIT', 'DEBIT'), " +
                    "amount INTEGER, " +
                    "currency VARCHAR(255), " +
                    "PRIMARY KEY (id))";
            STMT = CONN.prepareStatement(sql);
            STMT.executeUpdate();
        } catch (Exception e) {
            throw new H2TransactionRepositoryException(e.getMessage(), e.getCause());
        }
//        System.out.println("Goodbye!");
    }
}
