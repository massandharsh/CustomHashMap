import fileoperations.FileDelete;
import fileoperations.FileRead;
import hashing.Hash;
import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PanMain {
    public static void main(String[] args) {
        FileDelete.deleteFile("src/output.txt");
        List<String> file = FileRead.readFileContent("src/input.txt");
        Hash hash = new Hash();
        hash.initializeHash();
        for(String s : file){
            String [] accountDetails = s.split(",");
            try {
                Account account = Account.builder()
                        .setPanNumber(accountDetails[0])
                        .setName(accountDetails[1])
                        .setPlace(accountDetails[2])
                        .build();
                hash.addEntry(account);
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

        List<String> searchData = FileRead.readFileContent("src/search.txt");
        for(String pan : searchData) {
            hash.searchDetails(pan);
        }
    }
}