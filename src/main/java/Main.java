
import java.io.File;
import java.util.List;

public class Main {

    // This is the testing method that I used.
    public static void main (String[] args) {
        // Create new CSVTransactionParser Object
        CSVTransactionParser csvParser = new CSVTransactionParser();
        // Create new file.
        File csvFile = new File("src/main/resources/transactions.csv");
        // Assign csvList to return output of parse method.
        List<Transaction> csvList = csvParser.parse(csvFile);
        // Print list for testing.
        csvParser.toString(csvList);

    }
}