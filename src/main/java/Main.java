import java.io.File;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        CSVTransactionParser csvParser = new CSVTransactionParser();
        File csvFile = new File("src/main/resources/transactions.csv");
        List<Transaction> csvList = csvParser.parse(csvFile);
        String result = "";
        for (int i = 0; i < csvList.size(); i++) {
            result = result + "\n\n" + (i + 1) + ": " + csvList.get(i).toString();
        }
        System.out.println(result);
    }
}