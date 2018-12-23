package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.Direction;
import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Run all tests in the whole project or all test in a certain package easily with a test-configurations of JUnit! <br>
 * Disable not yet implemented methods with the annotation  @Disabled("Not Yet implemented")
 */
class Nonogram4_strategyPics_Test {
    private static Nonogram no;


    @BeforeAll
    static void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "src/Examples/nonogram5.txt";
        no = data.readAllFileInputs(fileName);
    }

    @Test
    void img_4167() {
        Line horiz2 = new Line(2, 15, Direction.HORIZONTAL);

        // Box 6 should be filled:


    }

}
