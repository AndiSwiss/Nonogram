package Helpers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {


    /**
     * @param filePath Path of the file to read from
     * @return List<String> List of the Lines of the file
     */
    public List<String> getStringsFromAFile(Path filePath) throws IOException {
        List<String> input;
        input = new ArrayList<>();
        Scanner sc = new Scanner(filePath);
        while (sc.hasNext()) {
            input.add(sc.nextLine());
        }

        sc.close();

        return input;
    }
}
