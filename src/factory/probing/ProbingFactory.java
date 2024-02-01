package factory.probing;

import enums.ProbingType;
import strategy.probing.LinearProbingStrategy;
import strategy.probing.ProbingStrategy;
import strategy.probing.QuadraticProbingStrategy;

public class ProbingFactory{
    public static <T> ProbingStrategy<T> createProbingStrategy(ProbingType probingType){
        if(probingType == ProbingType.LINEAR){
            return new LinearProbingStrategy<T>();
        }
        if(probingType == ProbingType.QUADRATIC){
            return new QuadraticProbingStrategy<>();
        }
        return new LinearProbingStrategy<>();
    }
}
