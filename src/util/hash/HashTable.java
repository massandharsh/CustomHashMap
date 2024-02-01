package util.hash;

import enums.HashingType;
import enums.ProbingType;
import exceptions.NoHashingStrategyProvidedException;
import exceptions.NoProbingStrategyProvidedException;
import factory.hashing.HashingFactory;
import factory.probing.ProbingFactory;
import model.Account;
import strategy.hashing.HashingStrategy;
import strategy.probing.LinearProbingStrategy;
import strategy.probing.ProbingStrategy;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashTable<T>{
    public static final int DEFAULT_CAPACITY = 10;
    public static final double LOAD_FACTOR = 0.5;
    private int noOfElements = 0;
    private List<HashTableTemplate<T>> hashTable;
    private int capacity = 10;
    private final HashingStrategy hashingStrategy;
    private final ProbingStrategy<T> probingStrategy;
    private HashTable(HashTableBuilder<T> hashTableBuilder){
        this.capacity = hashTableBuilder.capacity;
        this.hashingStrategy = hashTableBuilder.hashingStrategy;
        this.probingStrategy = hashTableBuilder.probingStrategy;
        this.hashTable = new ArrayList<>(this.capacity);
        for(int i = 0 ; i < this.capacity ; ++i){
            this.hashTable.add(null);
        }
    }

    public int getNoOfElements() {
        return noOfElements;
    }

    public int getCapacity() {
        return capacity;
    }

    public static class HashTableBuilder<T>{
        private int capacity;
        private HashingStrategy hashingStrategy;
        private ProbingStrategy<T> probingStrategy;

        public HashTableBuilder<T> setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public HashTableBuilder<T> setHashingStrategy(HashingStrategy hashingStrategy) {
            this.hashingStrategy = hashingStrategy;
            return this;
        }

        public HashTableBuilder<T> setProbingStrategy(ProbingStrategy<T> probingStrategy) {
            this.probingStrategy = probingStrategy;
            return this;
        }

        public HashTable<T> build(){
            if(this.capacity <= 0){
                this.capacity = HashTable.DEFAULT_CAPACITY;
            }
            if(probingStrategy == null){
                throw new NoProbingStrategyProvidedException("No Probing strategy provided");
            }
            if(hashingStrategy == null){
                throw new NoHashingStrategyProvidedException("No Hashing strategy provided");
            }
            return new HashTable<>(this);
        }
    }

    public static <S> HashTableBuilder<S> builder(){
        return new HashTableBuilder<>();
    }

    //It will be taken care by client based on their load factor
    public void reHashing(){
        //Perform the rehashing
        List<HashTableTemplate<T>> newHashTable = new ArrayList<>(2 * this.capacity);
        for(int i = 0 ; i < 2  * this.capacity ; ++i){
            newHashTable.add(null);
        }
        for(HashTableTemplate<T> template : this.hashTable){
            if(template == null) continue;
            //Calculate the hash value and insert to relevant index
            int hashValue = hashingStrategy.calculateHash(template.getKey());
            int insertionIndex = probingStrategy.getInsertionIndex(newHashTable,hashValue);
            //If not found vacant index perform Linear probing if older strategy was not linear probing
            if(insertionIndex == -1 && !(probingStrategy instanceof LinearProbingStrategy<T>)){
                //Perform linear probing
                insertionIndex = ProbingFactory.<T>createProbingStrategy(ProbingType.LINEAR).getInsertionIndex(this.hashTable,hashValue);
            }
            newHashTable.set(insertionIndex,template);
        }
        this.capacity = 2 * this.capacity;
        this.hashTable = newHashTable;
        //Deletion of current hash table will be taken care by garbage collector
    }

    public void put(String key,T value){
        //Calculate the hash value and insert it based on probing. If index not found use linear probing
        int hashValue = hashingStrategy.calculateHash(key);
        int insertionIndex = probingStrategy.getInsertionIndex(this.hashTable,hashValue);
        //If not found vacant index perform Linear probing if older strategy was not linear probing
        if(insertionIndex == -1 && !(probingStrategy instanceof LinearProbingStrategy<T>)){
            //Perform linear probing
            insertionIndex = ProbingFactory.<T>createProbingStrategy(ProbingType.LINEAR).getInsertionIndex(this.hashTable,hashValue);
        }
        this.hashTable.set(insertionIndex,new HashTableTemplate<>(key,value));
        noOfElements++;
    }

    public boolean containsKey(String key){
        int hashValue = hashingStrategy.calculateHash(key);
        int index = probingStrategy.getElementIndex(hashTable,key,hashValue);
        if(index == -1 && !(probingStrategy instanceof LinearProbingStrategy<T>)){
            index = ProbingFactory.<T>createProbingStrategy(ProbingType.LINEAR).getElementIndex(hashTable,key,hashValue);
        }
        return index != -1;
    }

    public T get(String key){
        int hashValue = hashingStrategy.calculateHash(key);
        int index = probingStrategy.getElementIndex(hashTable,key,hashValue);
        if(index == -1 && !(probingStrategy instanceof LinearProbingStrategy<T>)){
            index = ProbingFactory.<T>createProbingStrategy(ProbingType.LINEAR).getElementIndex(hashTable,key,hashValue);
        }
        return index == -1 ? null : this.hashTable.get(index).getValue();
    }



}
