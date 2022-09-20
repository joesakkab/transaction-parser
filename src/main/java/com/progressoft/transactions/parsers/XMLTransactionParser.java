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
    @Override
    public List<Transaction> parse(File transactionsFile) {
        ParserValidators validators = new ParserValidators();
        validators.validateFileFormat(transactionsFile, ".xml");
        try {
            // Convert the x,l file into a document.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(transactionsFile);
            return getAllTransactions(doc);
        } catch (Exception e) {
            throw new TransactionParserException(e.getMessage(), e.getCause());
        }
    }

    // Iterate through the different transactions in the xml file.
    public static List<Transaction> getAllTransactions(Document doc) {
        NodeList transactionNodes = doc.getElementsByTagName("Transaction");
        List<Transaction> ls = new ArrayList<Transaction>();
        for (int i = 0; i < transactionNodes.getLength(); i++) {
            Node transactionNode = transactionNodes.item(i);
            ls.add(getTransactionNode(transactionNode));
        }
        return ls;
    }

    // Go deeper into the nested nodes and retrieve information about transactions.
    public static Transaction getTransactionNode(Node transactionNode) {
        ParserValidators validators = new ParserValidators();
        String[] fields = new String[4];
        if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) transactionNode;
            fields[0] = element.getElementsByTagName("Description").item(0).getTextContent();
            fields[1] = element.getElementsByTagName("Direction").item(0).getTextContent();
            fields[2] = element.getElementsByTagName("Value").item(0).getTextContent();
            fields[3] = element.getElementsByTagName("Currency").item(0).getTextContent();
        }
        return validators.checkErrorsAndConvert(fields);
    }
}
