package com.progressoft.transactions.processors;

import com.progressoft.transactions.Display;
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
    private final TransactionParserFactory factory_;
    private final H2TransactionRepository repository_;
    private final Display display_;
    private final String directory_;
    private File currentFile_;

    public TransactionFileProcessor(TransactionParserFactory factory, H2TransactionRepository
            repository, Display display, String directory) {
        factory_ = factory;
        repository_ = repository;
        display_ = display;
        directory_ = directory;
    }


    public void process() {
        repository_.createTransactionTable();
        File[] listOfFiles = getListOfFiles();
        createErrorAndSuccessDirectories();

        for (File file: listOfFiles) {
            currentFile_ = file;

            // checks if current file is a directory, so ignore
            if (!file.getName().contains(".")) {
                continue;
            }
            
            TransactionParser parser = factory_.createParser(file.getName());
            if (parser == null) {
                moveInvalidFileType();
                continue;
            }
            Result result = parser.parse(file);
            moveFileToCorrectLocation(result);
            display_.printTransactionsList(file.getName(), result.getListOfTransactions());
            for (Transaction t : result.getListOfTransactions()) {
                repository_.save(t);
            }

        }

        display_.printTransactionsList("the H2 Database", repository_.listTransactions());
    }

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
        File errorDirectory = new File(directory_ + "/errors");
        if (errorDirectory.mkdir()) {
            System.out.println("Folder for 'errors' successfully created!");
        } else {
            System.out.println("Error found!");
        }

        // Create a directory called successes
        File successDirectory = new File(directory_ + "/successes");
        if (successDirectory.mkdir()) {
            System.out.println("Folder for 'successes' successfully created!");
        } else {
            System.out.println("Error found!");
        }
    }

    private File[] getListOfFiles() {
        File first = new File(directory_);
        String[] listOfFileNames = first.list();

        if (listOfFileNames != null) {
            File[] listOfFiles = new File[listOfFileNames.length];
            for (int i = 0; i < listOfFileNames.length; i++) {
                listOfFiles[i] = new File(directory_ + "/" + listOfFileNames[i]);
            }
            return listOfFiles;
        } else {
            return null;
        }

    }


}
