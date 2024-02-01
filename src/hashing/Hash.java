package hashing;

import enums.AssessesType;
import enums.HashingType;
import enums.ProbingType;
import factory.hashing.HashingFactory;
import factory.probing.ProbingFactory;
import fileoperations.FileWrite;
import model.Account;
import strategy.hashing.HashingStrategy;
import util.hash.HashTable;
import util.hash.HashTableTemplate;
import validator.AccountValidator;

import java.util.ArrayList;
import java.util.List;
import static fileoperations.PropertiesFileReader.PROPERTIES;
public class Hash {

    private HashTable<HashTable<Account>> flHashTable;
    private List<HashTable<Account>> slHashTables;
    public static final double LOAD_FACTOR = 0.5;
    public void initializeHash(){
        int flTableCapacity = AssessesType.values().length;
        int slTableCapacity = Integer.parseInt((String) PROPERTIES.get("sl.capacity"));
        String probingType = (String)PROPERTIES.get("probing.strategy");
        String hashingType = (String)PROPERTIES.get("hashing.strategy");
        slHashTables = new ArrayList<>();
        AssessesType [] assessesTypes = AssessesType.values();
        flHashTable = HashTable.<HashTable<Account>>builder()
                .setCapacity(flTableCapacity)
                .setProbingStrategy(ProbingFactory.
                        createProbingStrategy(ProbingType.valueOf(probingType)))
                .setHashingStrategy(HashingFactory.createHashingStrategy(HashingType.valueOf(hashingType)))
                .build();

        for (AssessesType assessesType : assessesTypes) {
            HashTable<Account> hashTable = HashTable.<Account>builder()
                    .setCapacity(slTableCapacity)
                    .setProbingStrategy(ProbingFactory.createProbingStrategy(ProbingType.valueOf(probingType)))
                    .setHashingStrategy(HashingFactory.createHashingStrategy(HashingType.valueOf(hashingType)))
                    .build();
            slHashTables.add(hashTable);
            flHashTable.put(assessesType.toString(), hashTable);
        }
    }
    public void extendRehash(int i){
        slHashTables.get(i).reHashing();
    }
    public void addEntry(Account account){
        int assessIndex = Integer.parseInt((String) PROPERTIES.get("pan.assessIndex"));
        String panNumber = account.getPanNumber();
        String assessesType = String.valueOf(panNumber.charAt(assessIndex));
        if(flHashTable.containsKey(assessesType)){
            if(flHashTable.get(assessesType).containsKey(account.getPanNumber())){
                throw new RuntimeException("Already existing pan record");
            }
            else{
                //If elements/capacity ratio exceeds 0.5 then double the size and perform rehashing
                int capacity = flHashTable.get(assessesType).getCapacity();
                int noOfElements = flHashTable.get(assessesType).getNoOfElements();
                if(noOfElements/(capacity + 0.0) > LOAD_FACTOR){
                    extendRehash(slHashTables.indexOf(flHashTable.get(assessesType)));
                }
                flHashTable.get(assessesType).put(account.getPanNumber(),account);
            }
        }
        else{
            try {
                AccountValidator.validatePan(panNumber);
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }

    public void searchDetails(String panNumber){

        int assessIndex = Integer.parseInt((String) PROPERTIES.get("pan.assessIndex"));
        String assessesType = String.valueOf(panNumber.charAt(assessIndex));
        if(flHashTable.containsKey(assessesType)){
            if(flHashTable.get(assessesType).containsKey(panNumber)){
                Account account = flHashTable.get(assessesType).get(panNumber);
                String accountDetails =
                        "\"The entry " +
                        account.getPanNumber() +
                        " does exist -" +
                        account.getName() +
                        " " +
                        account.getPlace() +
                        "\"";
                FileWrite.writeFileContent("src/output.txt", accountDetails);
            }
            else{
                String accountDetails =
                        "\"The entry " +
                                panNumber +
                                " doesn't exist" +
                                "\"";
                FileWrite.writeFileContent("src/output.txt", accountDetails);
            }
        }
        else{
            try {
                AccountValidator.validatePan(panNumber);
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

    }


 }
