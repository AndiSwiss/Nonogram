package Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AccessNonogramLibrary {
    public List<Path> getListOfNonogramsInLibrary(Path pathToLibrary) {
        // p. 337: get list of files in a directory, lambda-version:
        // note: that stream is not sorted!
        try (Stream<Path> files = Files.list(pathToLibrary)) {
            List<Path> filePaths = new ArrayList<>();
            files.filter(file -> Files.isRegularFile(file))
                    .filter(file -> file.toString().toLowerCase().endsWith(".txt"))
                    .forEach(filePaths::add);

            // sort them alphabetically:
            filePaths.sort(Comparator.comparing(Path::getFileName));
            return filePaths;
        } catch (IOException e) {
            System.out.println("No files were found at " + pathToLibrary);
        }
        return null;
    }
}

