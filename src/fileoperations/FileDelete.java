package fileoperations;

import java.io.File;

public class FileDelete {
    public static void deleteFile(String path){
        File file = new File(path);
        // Check if file is present
        if (file.exists()) {
            // Attempt to delete the file
            file.delete();
        }
    }
}
