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
    public List<String> getStringsFromAFile(String fileName) {
        List<String> input;
        input = new ArrayList<>();
        File file = new File(fileName);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                input.add(sc.nextLine());
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return input;
    }


}
