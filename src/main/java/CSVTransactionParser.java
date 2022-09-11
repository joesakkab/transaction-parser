import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTransactionParser implements TransactionParser{

    // Implementation of TransactionParser interface.
    @Override
    public List<Transaction> parse(File transactionsFile) {
        try {
            Scanner sc = new Scanner(transactionsFile);
            List<Transaction> list = new ArrayList<Transaction>();
            String line = "";
            // Iterate through each line since each line is a seperate transaction
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] tokens = line.split(",");
                list.add(convert(tokens));
            }
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("File is not found.");
            e.printStackTrace();
        }
        return null;
    }

    // creates a new transaction and sets its values according to given array of tokens.
    private Transaction convert(String[] tokens) {
        Transaction t = new Transaction();
        t.setDescription(tokens[0]);
        t.setDirection(tokens[1]);
        t.setAmount(new BigDecimal(tokens[2]));
        t.setCurrency(tokens[3]);
        return t;
    }


}