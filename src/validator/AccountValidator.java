package validator;

import enums.AssessesType;
import exceptions.InvalidAssessTypeException;
import exceptions.InvalidNameSizeException;
import exceptions.InvalidPanLengthException;
import fileoperations.PropertiesFileReader;

import java.util.Arrays;
import java.util.Properties;

import static fileoperations.PropertiesFileReader.PROPERTIES;

public class AccountValidator{
    public static void validatePan(String pan){
        int len = Integer.parseInt((String) PROPERTIES.get("pan.length"));
        if(pan == null || pan.length() != len){
            throw new InvalidPanLengthException("Pan length should be: " + len + " pan:" + pan);
        }
        int assessIndex = Integer.parseInt((String) PROPERTIES.get("pan.assessIndex"));
        try {
             AssessesType.valueOf(String.valueOf(pan.charAt(assessIndex)));
        } catch (IllegalArgumentException e) {
             throw new InvalidAssessTypeException("Invalid assess type the assess type should have values in " + Arrays.toString(AssessesType.values()) + " pan:" + pan);
        }
    }
    public static void validateName(String name){
        int maxNameLen =  Integer.parseInt((String) PROPERTIES.get("name.maxLen")) + 1;
        if(name == null || "".equals(name)){
            throw new InvalidNameSizeException("Name length should be greater than zero " + name);
        }
        if(name.length() > maxNameLen){
            throw new InvalidNameSizeException("Name length should be less than " + maxNameLen + " name:" + name);
        }
    }
    public static void validatePlace(String place){
        int maxNameLen = Integer.parseInt((String) PROPERTIES.get("place.maxLen")) + 1;
        if(place == null || "".equals(place)){
            throw new InvalidNameSizeException("Place length should be greater than zero " + place);
        }
        if(place.length() > maxNameLen){
            throw new InvalidNameSizeException("Place length should be less than " + maxNameLen + " place:" + place);
        }
    }
}
