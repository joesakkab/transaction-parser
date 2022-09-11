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
        toString(csvList);

    }

    // Converts the list of transactions into a String.
    // the purpose of this method is for testing the parse method in the Main class.
    public static void toString(List<Transaction> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            Transaction t = list.get(i);
            result = result + "\n\n" + (i + 1) + ": " +
                    "Description: " + t.getDescription() + "\n   " +
                    "Direction: " + t.getDirection() + "\n   " +
                    "Amount: " + t.getAmount() + "\n   " +
                    "Currency: " + t.getCurrency();
        }
        System.out.println(result);

    }
}