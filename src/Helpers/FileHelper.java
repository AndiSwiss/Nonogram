package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {


    /**
     * @param fileName FileName and path to read from
     * @return List<String> List of the Lines of the file
     */
    public List<String> getStringsFromAFile(String fileName) throws FileNotFoundException {
        List<String> input;
        input = new ArrayList<>();
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            input.add(sc.nextLine());
        }

        sc.close();

        return input;
    }
}
