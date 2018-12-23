package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.*;
import NonogramStructure.Number;
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

        horiz2.getNumberLine().getNumbers().add(new Number(7));
        horiz2.getNumberLine().getNumbers().add(new Number(1));

        // Box 6 should be filled:
        horiz2.getBox(6).setState(State.BLACK);

        System.out.println(horiz2);
        assertEquals(horiz2, no.getHorizontalLine(2));
    }

}
