package strategy.probing;

import util.hash.HashTableTemplate;

import java.util.List;

public class QuadraticProbingStrategy<T> implements ProbingStrategy<T>{
    @Override
    public int getInsertionIndex(List<HashTableTemplate<T>> hashTable, int hash) {
        for(int i = 0 ; i < hashTable.size() ; ++i){
            int newIndex = (hash + i*i) % hashTable.size();
            if(hashTable.get(newIndex) == null){
                //We have a space to insert the value in that index
                return newIndex;
            }
        }
        //Not able to find vacant place using quadratic probing
        return -1;
    }

    @Override
    public int getElementIndex(List<HashTableTemplate<T>> hashTable, String key, int hash) {
        for(int i = 0 ; i < hashTable.size() ; ++i){
            int searchIndex = (hash + i*i) % hashTable.size();
            if(hashTable.get(searchIndex) != null && hashTable.get(searchIndex).getKey().equals(key)) {
                //We are able to find index of key
                return searchIndex;
            }
        }
        //Not able to find vacant place using linear probing
        return -1;
    }
}
