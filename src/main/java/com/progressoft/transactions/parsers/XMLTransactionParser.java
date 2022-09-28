package com.progressoft.transactions.parsers;

import com.progressoft.transactions.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLTransactionParser implements TransactionParser {
    // implementation of TransactionsParser interface
    private static boolean fileHasErrors = false;
    @Override
    public List<Transaction> parse(File transactionsFile) {
        fileHasErrors = false;
        ParserValidators validators = new ParserValidators(transactionsFile, ".xml");
        try {
            validators.validateFileFormat();
            // Convert the x,l file into a document.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(transactionsFile);
            return getAllTransactions(doc, validators);
        } catch (Exception e) {
            throw new TransactionParserException(e.getMessage(), e.getCause());
        }
    }

    // Iterate through the different transactions in the xml file.
    public static List<Transaction> getAllTransactions(Document doc, ParserValidators validators) {
        NodeList transactionNodes = doc.getElementsByTagName("Transaction");
        List<Transaction> ls = new ArrayList<>();
        for (int i = 0; i < transactionNodes.getLength(); i++) {
            Node transactionNode = transactionNodes.item(i);
            Transaction t = getTransactionNode(transactionNode, validators);
            if (t != null) {
                ls.add(t);
            } else {
                fileHasErrors = true;
            }
            validators.incrementTransactionCounter();
        }
        if (!fileHasErrors) {
            validators.moveFileToSuccessFolder();
        }
        return ls;
    }

    // Go deeper into the nested nodes and retrieve information about transactions.
    public static Transaction getTransactionNode(Node transactionNode, ParserValidators validators) {
        String[] fields = new String[4];
        if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) transactionNode;
            fields[0] = element.getElementsByTagName("Description").item(0).getTextContent();
            fields[1] = element.getElementsByTagName("Direction").item(0).getTextContent();
            fields[2] = element.getElementsByTagName("Value").item(0).getTextContent();
            fields[3] = element.getElementsByTagName("Currency").item(0).getTextContent();
        }
        validators.setTokens(fields);
        return validators.performAllChecks();
    }
}
