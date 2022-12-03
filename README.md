# Transaction Parser
This repositroy contains the code for a bank transactions parser.

The .jar file named "transaction-parser-1.0-SNAPSHOT-jar-with-dependencies.jar" will take a directory as an input.
This directory is to contain .csv and .xml files that contain details for a bank trnasaction. 

One bank transaction contains the following fields:
- Description
- Direction (CREDIT or DEBIT)
- Amount
- Currency

Sample files are available in the resourcs folder.

The purpose of the .jar file is to parse the transactions in the files as Transaction objects. Then the objects will be saved in an H2 Database.
The .jar executable will then put all the files that were successfully parsed into a success folder. 

Then all of the files that threw validation excpeitons will be moved into an error folder. The error folder will contain the error file and another file named "{file_name}\_error.txt" that contains a log of all the errors in all transactions contained within that file.
