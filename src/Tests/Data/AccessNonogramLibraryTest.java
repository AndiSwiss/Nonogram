package Tests.Data;

import Data.AccessNonogramLibrary;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessNonogramLibraryTest {

    @Test
    void getListOfNonogramsInLibrary() {
        AccessNonogramLibrary library = new AccessNonogramLibrary();
        String current = System.getProperty("user.dir");
        Path path = Paths.get(current);
        path = path.getParent();
        path = path.resolve("NonogramLibrary").resolve("converted-files");
        List<Path> files = library.getListOfNonogramsInLibrary(path);
        if (files != null) {
            System.out.println("The following files were found:");
            for (Path file : files) {
                System.out.println(file);

            }
        }
    }
}