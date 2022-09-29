package com.progressoft.transactions.processors;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProcessor {
    private final File TRANSACTION_FILE;
    private PrintStream writeErrors;

    public FileProcessor(File transactionFile) {
        TRANSACTION_FILE = transactionFile;
    }

    public void moveInvalidFileType() {
        moveFileAndWriteError("INVALID FILE TYPE: Must be '.csv' or '.xml'", false);
    }

    public void moveFileAndWriteError(String message, boolean hasBeenMoved) {
        if (!hasBeenMoved) {
            String name = TRANSACTION_FILE.getName();
            String directoryPath = TRANSACTION_FILE.getParent();
            String errorFileName = name.substring(0, name.indexOf(".")) + "_error.txt";
            File errorFile = new File(directoryPath + "/errors/" + errorFileName);
            try {
                writeErrors = new PrintStream(errorFile);
                Files.move(Paths.get(TRANSACTION_FILE.getPath()),Paths.get(directoryPath + "/errors/" + name));
                writeErrors.append(message + "\n");
            } catch (Exception e) {
                System.out.println("Exception here!");
            }
        } else {
            writeErrors.append(message);
        }

    }
    public void moveFileToSuccessFolder() {
        String name = TRANSACTION_FILE.getName();
        String directoryPath = TRANSACTION_FILE.getParent();
        try {
            Files.move(Paths.get(TRANSACTION_FILE.getPath()),Paths.get(directoryPath + "/successes/" + name));
        } catch (Exception ignored) {
        }
    }
}
