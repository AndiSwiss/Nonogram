package Tests.NonogramStructure;

import Data.InitialData;
import Data.InputDataHandler;
import NonogramStructure.Nonogram;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class NonogramTest {

    @Test
    void toString_wholeNonogram() {
        Path filePath = new InitialData().pathToExamples.resolve("nonogram1.txt");
        InputDataHandler data = new InputDataHandler();

        Nonogram no = data.readSolutionFile(filePath, 20);

        System.out.println("\n-----------------------------");
        System.out.println("NonogramTest.test_toString():");
        System.out.println(no.toString());

        String expected = "Nonogram{title='Examples/nonogram1_solution.txt', horizontalBoxesCount=15, verticalBoxesCount=20, maxTopNumbers=4, maxSideNumbers=4, boxSize=20}\n" +
                "                            3 |    ▋▋▋        | lineNr=0, dir=HORIZONTAL\n" +
                "                         3  2 |    ▋▋▋     ▋▋ | lineNr=1, dir=HORIZONTAL\n" +
                "                      7  1  1 |  ▋▋▋▋▋▋▋  ▋  ▋| lineNr=2, dir=HORIZONTAL\n" +
                "                   1  1  1  1 |    ▋ ▋    ▋  ▋| lineNr=3, dir=HORIZONTAL\n" +
                "                   1  1  1  1 |    ▋ ▋    ▋ ▋ | lineNr=4, dir=HORIZONTAL\n" +
                "                         4  1 |    ▋▋▋▋   ▋   | lineNr=5, dir=HORIZONTAL\n" +
                "                      1  2  2 |   ▋   ▋▋  ▋▋  | lineNr=6, dir=HORIZONTAL\n" +
                "                      2  5  1 |  ▋▋   ▋▋▋▋▋ ▋ | lineNr=7, dir=HORIZONTAL\n" +
                "                      3  6  1 |  ▋▋▋ ▋▋▋▋▋▋ ▋ | lineNr=8, dir=HORIZONTAL\n" +
                "                         7  1 |  ▋▋▋▋▋▋▋   ▋  | lineNr=9, dir=HORIZONTAL\n" +
                "                         3  1 |      ▋▋▋   ▋  | lineNr=10, dir=HORIZONTAL\n" +
                "                      4  3  1 | ▋▋▋▋ ▋▋▋   ▋  | lineNr=11, dir=HORIZONTAL\n" +
                "                      2  3  1 |  ▋▋  ▋▋▋   ▋  | lineNr=12, dir=HORIZONTAL\n" +
                "                      2  3  1 |  ▋▋   ▋▋▋  ▋  | lineNr=13, dir=HORIZONTAL\n" +
                "                   1  2  1  8 |▋ ▋▋ ▋ ▋▋▋▋▋▋▋▋| lineNr=14, dir=HORIZONTAL\n" +
                "                   1  1  3  1 |▋    ▋ ▋▋▋  ▋  | lineNr=15, dir=HORIZONTAL\n" +
                "                   1  1  3  1 |▋    ▋ ▋▋▋  ▋  | lineNr=16, dir=HORIZONTAL\n" +
                "                   4  1  1  1 | ▋▋▋▋  ▋ ▋  ▋  | lineNr=17, dir=HORIZONTAL\n" +
                "                   1  1  1  2 |  ▋ ▋  ▋ ▋▋    | lineNr=18, dir=HORIZONTAL\n" +
                "                      1  1  4 |  ▋ ▋  ▋▋▋▋    | lineNr=19, dir=HORIZONTAL\n" +
                "Vertical lines, just the numbers (since formatting that would be quite hard (especially with numbers greater that 10):\n" +
                "[3]\n" +
                "[1, 1]\n" +
                "[1, 3, 4, 3]\n" +
                "[1, 4, 4, 1]\n" +
                "[6, 2, 1, 3]\n" +
                "[3, 1, 1, 3]\n" +
                "[6, 5]\n" +
                "[1, 15]\n" +
                "[1, 11, 1]\n" +
                "[2, 7]\n" +
                "[2, 1, 2]\n" +
                "[7, 1]\n" +
                "[1, 1, 9]\n" +
                "[1, 1, 2, 1]\n" +
                "[2, 1]\n";

        assertEquals(expected, no.toString());
    }
}