package com.progressoft.transactions.processors;

import java.io.File;

public class InitialSetup {
    private final String MAIN_DIRECTORY;

    public InitialSetup(String transactionFilesDirectory) {
        MAIN_DIRECTORY = transactionFilesDirectory;
    }

    public void createErrorAndSuccessDirectories() {
        // Create directory called errors.
        File errorDirectory = new File(MAIN_DIRECTORY + "/errors");
        if (errorDirectory.mkdir()) {
            System.out.println("Folder for 'errors' successfully created!");
        } else {
            System.out.println("Error found!");
        }

        // Create a directory called successes
        File successDirectory = new File(MAIN_DIRECTORY + "/successes");
        if (successDirectory.mkdir()) {
            System.out.println("Folder for 'successes' successfully created!");
        } else {
            System.out.println("Error found!");
        }
    }

    public File[] getListOfFiles() {
        //Create file to get list of file names
        File first = new File(MAIN_DIRECTORY);
        String[] listOfFileNames = first.list();

        if (listOfFileNames != null) {
            //Convert list of file names to list of files
            File[] listOfFiles = new File[listOfFileNames.length];
            for (int i = 0; i < listOfFileNames.length; i++) {
                listOfFiles[i] = new File(MAIN_DIRECTORY + "/" + listOfFileNames[i]);
            }
            return listOfFiles;
        } else {
            return null;
        }

    }
}
