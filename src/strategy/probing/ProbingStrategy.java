package strategy.probing;

import util.hash.HashTableTemplate;

import java.util.List;

public interface ProbingStrategy<T>{
    //If not able to find index we can return -1
    int getInsertionIndex(List<HashTableTemplate<T>> hashTable,int hash);
    int getElementIndex(List<HashTableTemplate<T>> hashTable, String key, int hash);
}
