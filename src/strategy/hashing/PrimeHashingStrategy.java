package strategy.hashing;

public class PrimeHashingStrategy implements HashingStrategy{

    public static final int PRIME = 31;
    public static final int MOD = (int)1e9 + 7;
    @Override
    public int calculateHash(     value) {
        int hashValue = 0;
        for(char c : value.toCharArray()){
            hashValue = (hashValue + (int)c*PRIME) % MOD;
        }
        return hashValue;
    }
}
