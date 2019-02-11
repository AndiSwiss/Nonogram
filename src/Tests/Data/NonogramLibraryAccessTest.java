package Tests.Data;

import Data.InitialData;
import Data.NonogramLibraryAccess;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NonogramLibraryAccessTest {

    @Test
    void getListOfNonogramsInLibrary() {
        Path path = new InitialData().pathToNonogramLibrary;
        List<Path> files = new NonogramLibraryAccess().getListOfNonogramsInLibrary(path);
        if (files != null) {
            System.out.println("The following files were found:");
            for (Path file : files) {
                System.out.println(file);

            }
        }

        // check whether it found some files at the provided path:
        assertNotNull(files);
    }
}