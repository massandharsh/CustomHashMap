package strategy.hashing;

import java.util.Objects;

public class InbuiltHashingStrategy implements HashingStrategy{
    @Override
    public int calculateHash(String value) {
        return Math.abs(Objects.hash(value));
    }
}
