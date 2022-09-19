import com.progressoft.transactions.Transaction;
import com.progressoft.transactions.transactionsparser.CSVTransactionParser;
import com.progressoft.transactions.transactionsparser.TransactionParser;
import com.progressoft.transactions.transactionsrepository.H2TransactionRepository;
import com.progressoft.transactions.transactionsrepository.TransactionsRepository;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionParser csvParser = new CSVTransactionParser();
        File csvFile = new File("src/main/resources/transactions.csv");
        List<Transaction> transactions = csvParser.parse(csvFile);
        System.out.println(listToString(transactions));

        TransactionsRepository repository = new H2TransactionRepository();
        repository.resetTable();
        repository.createTransactionTable();
        for (Transaction t : transactions) {
            repository.save(t);
        }
        List<Transaction> sqlSelect = repository.listTransactions();
        System.out.println(listToString(sqlSelect));

    }

    public static String listToString(List<Transaction> transactions) {
        String result = "";
        for (int i = 0; i < transactions.size(); i++) {
            result = result + "\n\n" + (i + 1) + ": " + transactions.get(i).toString();
        }
        return result;
    }
}