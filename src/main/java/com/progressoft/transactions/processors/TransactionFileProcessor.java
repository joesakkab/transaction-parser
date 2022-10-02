package com.progressoft.transactions.processors;

import com.progressoft.transactions.DisplayContent;
import com.progressoft.transactions.Transaction;
import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.repositories.H2TransactionRepository;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileProcessor {
    private final TransactionParserFactory FACTORY;
    private final H2TransactionRepository REPOSITORY;
    private final DisplayContent DISPLAY_CONTENT;
    private String DIRECTORY;
    private File currentFile_;

    private void moveFileToCorrectLocation(Result result) {
        if (result.isFileWithError()) {
            moveFileAndWriteError(result.getListOfErrorMessages());
        } else {
            moveFileToSuccessFolder();
        }
    }

    private void moveInvalidFileType() {
        List<String> message = new ArrayList<>();
        message.add("INVALID FILE TYPE: Must be '.csv' or '.xml'");
        moveFileAndWriteError(message);
    }

    private void moveFileAndWriteError(List<String> listOfErrorMessages) {
        String name = currentFile_.getName();
        String directoryPath = currentFile_.getParent();
        String errorFileName = name.substring(0, name.indexOf(".")) + "_error.txt";
        File errorFile = new File(directoryPath + "/errors/" + errorFileName);
        try {
            PrintStream writeErrors = new PrintStream(errorFile);
            Files.move(Paths.get(currentFile_.getPath()),Paths.get(directoryPath + "/errors/" + name));
            for (String message: listOfErrorMessages) {
                writeErrors.append(message + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private void moveFileToSuccessFolder() {
        String name = currentFile_.getName();
        String directoryPath = currentFile_.getParent();
        try {
            Files.move(Paths.get(currentFile_.getPath()),Paths.get(directoryPath + "/successes/" + name));
        } catch (Exception ignored) {
        }
    }

    private void createErrorAndSuccessDirectories() {
        // Create directory called errors.
        File errorDirectory = new File(DIRECTORY + "/errors");
        if (errorDirectory.mkdir()) {
            System.out.println("Folder for 'errors' successfully created!");
        } else {
            System.out.println("Error found!");
        }

        // Create a directory called successes
        File successDirectory = new File(DIRECTORY + "/successes");
        if (successDirectory.mkdir()) {
            System.out.println("Folder for 'successes' successfully created!");
        } else {
            System.out.println("Error found!");
        }
    }

    private File[] getListOfFiles() {
        File first = new File(DIRECTORY);
        String[] listOfFileNames = first.list();

        if (listOfFileNames != null) {
            File[] listOfFiles = new File[listOfFileNames.length];
            for (int i = 0; i < listOfFileNames.length; i++) {
                listOfFiles[i] = new File(DIRECTORY + "/" + listOfFileNames[i]);
            }
            return listOfFiles;
        } else {
            return null;
        }

    }

    public TransactionFileProcessor() {
        FACTORY = new TransactionParserFactory();
        REPOSITORY = new H2TransactionRepository();
        DISPLAY_CONTENT = new DisplayContent();
    }

    public void setDIRECTORY(String directory) {
        DIRECTORY = directory;
    }

    public void process() {
        REPOSITORY.createTransactionTable();
        File[] listOfFiles = getListOfFiles();
        createErrorAndSuccessDirectories();

        for (File file: listOfFiles) {
            currentFile_ = file;
            TransactionParser parser = FACTORY.createParser(file.getName());
            if (parser == null) {
                moveInvalidFileType();
                continue;
            }
            Result result = parser.parse(file);
            moveFileToCorrectLocation(result);
            DISPLAY_CONTENT.printTransactionsList(file.getName(), result.getListOfTransactions());
            for (Transaction t : result.getListOfTransactions()) {
                REPOSITORY.save(t);
            }

        }

        DISPLAY_CONTENT.printTransactionsList("the H2 Database", REPOSITORY.listTransactions());
    }
}
