package factory.hashing;

import enums.HashingType;
import strategy.hashing.HashingStrategy;
import strategy.hashing.InbuiltHashingStrategy;
import strategy.hashing.PrimeHashingStrategy;

public class HashingFactory {
    public static HashingStrategy createHashingStrategy(HashingType hashingType){
        if(hashingType == HashingType.INBUILT_TYPE){
            return new InbuiltHashingStrategy();
        }
        if(hashingType == HashingType.PRIME_TYPE){
            return new PrimeHashingStrategy();
        }

        //By default, we will return inbuilt hashing strategy
        return new InbuiltHashingStrategy();
    }
}
