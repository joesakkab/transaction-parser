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
//        System.out.println(csvList.get(0).getDescription());
        String result = "";
        for (int i = 0; i < csvList.size(); i++) {
            result = result + "\n\n" + (i + 1) + ": " + csvList.get(i).toString();
        }
        System.out.println(result);

    }
}