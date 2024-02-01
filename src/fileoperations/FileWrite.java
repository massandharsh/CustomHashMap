package fileoperations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWrite {
    public static void writeFileContent(String filePath,String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
                //Write content to file
                writer.write(data);
                writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
