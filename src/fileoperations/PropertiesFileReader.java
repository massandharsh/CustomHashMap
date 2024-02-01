package fileoperations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
    public static final Properties PROPERTIES = getProperties("src/application.properties");
    public static Properties getProperties(String filePath){
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
