import com.progressoft.transactions.Transaction;
import com.progressoft.transactions.parsers.CSVTransactionParser;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.parsers.XMLTransactionParser;
import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.repositories.H2TransactionRepository;
import com.progressoft.transactions.repositories.TransactionsRepository;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();
        String[] listOfFiles = {"transactions.csv", "transactions.xml"};
        TransactionsRepository repository = new H2TransactionRepository();
//            repository.resetTable();
        repository.createTransactionTable();
        for (String fileName : listOfFiles) {
            TransactionParser parser = transactionParserFactory.createParser(fileName);
            File file = new File("src/main/resources/" + fileName);
            List<Transaction> transactions = parser.parse(file);
            System.out.println("The list of transactions are: \n" + listToString(transactions));

            for (Transaction t : transactions) {
                repository.save(t);
            }
        }
        List<Transaction> sqlSelect = repository.listTransactions();
        System.out.println("The list of transactions retrevied form the database are: \n" +
                listToString(sqlSelect));

    }

    public static String listToString(List<Transaction> transactions) {
        String result = "";
        for (int i = 0; i < transactions.size(); i++) {
            result = result + "\n\n" + (i + 1) + ": " + transactions.get(i).toString();
        }
        return result;
    }
}