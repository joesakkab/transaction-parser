import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface TransactionParser {

    List<Transaction> parse(File transactionsFile);
}
