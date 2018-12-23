package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import Solver.Solver;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Run all tests in the whole project or all test in a certain package easily with a test-configurations of JUnit! <br>
 * Disable not yet implemented methods with the annotation  @Disabled("Not Yet implemented")
 */
class SolverTest {
    private static Nonogram no;
    private static Line hLine2;  // horizontal line 2
    private static Solver solver;


    @BeforeEach
    void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "src/Examples/nonogram5.txt";
        no = data.readAllFileInputs(fileName);
        hLine2 = no.getHorizontalLine(2);
        solver = new Solver(no);


    }


    @Test
    @Disabled("Not Yet implemented")
    void start() {
        assertEquals(200, 15, "to be implemented");
    }


    @Test
    void markLine_horizontalLine2() {
        solver.markLine(hLine2);

        // this line contains 7 and 1 as digits, so the following output is expected:
        for (int i = 0; i < 7; i++) {
            assertEquals(0, hLine2.getBox(i).getMarkL());
        }
        assertEquals(-1, hLine2.getBox(7).getMarkL());
        assertEquals(1, hLine2.getBox(8).getMarkL());
        for (int i = 9; i < 15; i++) {
            assertEquals(-1, hLine2.getBox(i).getMarkL());
        }
    }

    @Test
    void markLine_horizontalLine2reverse() {
        solver.markLine(hLine2);

        // this line contains 7 and 1 as digits, so the following output is expected:
        for (int i = 0; i < 6; i++) {
            assertEquals(-1, hLine2.getBox(i).getMarkR());
        }
        for (int i = 6; i < 13; i++) {
            assertEquals(0, hLine2.getBox(i).getMarkR());
        }

        assertEquals(-1, hLine2.getBox(13).getMarkR());
        assertEquals(1, hLine2.getBox(14).getMarkR());
    }


}