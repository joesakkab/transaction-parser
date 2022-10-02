package com.progressoft.transactions.parsers;

import com.progressoft.transactions.processors.Result;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlTransactionParser implements TransactionParser {

    private static ParserValidators VALIDATOR;
    private final Result RESULT;
    public XmlTransactionParser() {
        VALIDATOR = new ParserValidators();
        RESULT = new Result();
    }
    @Override
    public Result parse(File transactionsFile) {
        try {
            NodeList transactionNodes = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(transactionsFile).getElementsByTagName("Transaction");
            for (int i = 0; i < transactionNodes.getLength(); i++) {
                setTransactionFields(transactionNodes.item(i));
                updateResult();
            }
            return RESULT;
        } catch(Exception e){
            throw new TransactionParserException(e.getMessage(), e.getCause());
        }
    }

    private static void setTransactionFields(Node transactionNode) {
        String[] fields = new String[4];
        if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) transactionNode;
            fields[0] = element.getElementsByTagName("Description").item(0).getTextContent();
            fields[1] = element.getElementsByTagName("Direction").item(0).getTextContent();
            fields[2] = element.getElementsByTagName("Value").item(0).getTextContent();
            fields[3] = element.getElementsByTagName("Currency").item(0).getTextContent();
        }
        VALIDATOR.setFields(fields);

    }

    private void updateResult() {
        String message = VALIDATOR.getErrorMessage();
        if (message.equals("")) {
            RESULT.addTransaction(VALIDATOR.getTransaction());
        } else {
            RESULT.addErrorMessage(message);
        }
    }

}
