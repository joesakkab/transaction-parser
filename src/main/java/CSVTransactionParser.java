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
        Scanner sc = checkFileformat(transactionsFile);
        List<Transaction> list = new ArrayList<Transaction>();
        String line = "";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] tokens = line.split(",");
            list.add(checkErrorsAndConvert(tokens));
        }
        return list;
    }

    private Scanner checkFileformat(File transactionsFile) {
        if (!transactionsFile.getName().endsWith(".csv")) {
            throw new TransactionParserException("Please make sure file is in csv format (ends with .csv)");
        }

        try {
            Scanner scan = new Scanner(transactionsFile);
            return scan;
        } catch (Exception e) {
            throw new TransactionParserException("", e);
        }
    }
    private void checkTransactionFields(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
            if (tokens[i].equals("")) {
                throw new TransactionParserException("Please make sure none of the fields are white spaces");
            }
        }
        if (tokens.length != 4){
            throw new TransactionParserException("Please make sure none of the fields are empty");
        }
    }

    private BigDecimal checkIfNumeric(String token) {
        try {
            BigDecimal bd = new BigDecimal(token);
            return bd;
        } catch (Exception e){
            throw new TransactionParserException("Please make sure transaction amount is numeric");
        }
    }
    private Transaction checkErrorsAndConvert(String[] tokens) {
        checkTransactionFields(tokens);
        checkIfNumeric(tokens[2]);
        Transaction t = new Transaction();
        t.setDescription(tokens[0]);
        t.setDirection(tokens[1]);
        t.setAmount(checkIfNumeric(tokens[2]));
        t.setCurrency(tokens[3]);
        return t;
    }


}