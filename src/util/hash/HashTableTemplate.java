package util.hash;

public class HashTableTemplate<T>{
    private String key;
    private T value;

    public HashTableTemplate(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public HashTableTemplate<T> setKey(String key) {
        this.key = key;
        return this;
    }

    public T getValue() {
        return value;
    }

    public HashTableTemplate<T> setValue(T value) {
        this.value = value;
        return this;
    }
}
