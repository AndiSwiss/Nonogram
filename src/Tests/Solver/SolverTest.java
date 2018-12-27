package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import NonogramStructure.State;
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
    private static Line hLine15; // horizontal line 15
    private static Line hLine17; // horizontal line 17
    private static Solver solver;


    @BeforeEach
    void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "Examples/nonogram5.txt";
        no = data.readAllFileInputs(fileName);
        hLine2 = no.getHorizontalLine(2);
        hLine15 = no.getHorizontalLine(15);
        hLine17 = no.getHorizontalLine(17);
        solver = new Solver();
    }


    @Test
    @Disabled("Not Yet implemented")
    void start() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    @Disabled("Not Yet implemented")
    void strategy1AllHorizontal() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    @Disabled("Not Yet implemented")
    void strategy1AllVertical() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    void strategy1_horizontalLine17() {
        boolean changedSomething = solver.strategy1(hLine17);
        assertTrue(changedSomething);

        System.out.println(hLine17);

        for (int i = 0; i < hLine17.getBoxesSize(); i++) {
            if (i == 0 || i == 1 || i == 5 || i == 6 || i == 7 || i == 13 || i == 14) {
                assertEquals(State.UNKNOWN, hLine17.getBox(i).getState());
            } else {
                assertEquals(State.BLACK, hLine17.getBox(i).getState());
            }
        }
    }

    @Test
    void markBoxesWhichHaveSameMarksInOppositeDirection_horizontalLine15() {
        solver.markLine(hLine15);
        boolean changedSomething = solver.markBoxesWhichHaveSameMarksInOppositeDirection(hLine15);

        System.out.println(hLine15);

        assertTrue(changedSomething);

        for (int i = 0; i < hLine15.getBoxesSize(); i++) {
            if (i == 0 || i == 5 || i == 6 || i == 14) {
                assertEquals(State.UNKNOWN, hLine15.getBox(i).getState());
            } else {
                assertEquals(State.BLACK, hLine15.getBox(i).getState());
            }
        }
    }

    @Test
    void markLineInOneDirection_horizontalLine2() {
        solver.markLineInOneDirection(hLine2, true);

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
    void markLineInOneDirection_horizontalLine2_reverse() {
        solver.markLineInOneDirection(hLine2, false);


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

    @Test
    void markLineInOneDirection_problem1() {
        // todo: delete current marks if they get changed!! otherwise, they will get duplicated, such as in example 5:
        //  If you run the solver for:
        //  - horizontal line 8
        //  - vertical line 9
        //  - horizontal line 8 again, then the old marks are still there after the correct new marking -> handle that!

        assertEquals(200, 15, "to be implemented");
    }

    @Test
    void markLineInOneDirection_problem2() {
        // todo: for the above scenario, it just works for the number index 2. The others are not deleted
        //  and also for the following scenario, an error "Index -1 out-of-bounds for length 20" is thrown:
        //  - vertical line 9
        //  - horizontal line 15
        //  - vertical line 9

        assertEquals(200, 15, "to be implemented");
    }


    @Test
    void deleteAMarkIfItEqualsTheGivenNumberIndex() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    @Disabled("Not Yet implemented")
    void getMarkInTCorrectDirectionFromABox() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    @Disabled("Not Yet implemented")
    void markANumberAndAdvancePosition() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    @Disabled("Not Yet implemented")
    void moveIfAWhiteSpaceWasFound() {
        assertEquals(200, 15, "to be implemented");
    }

    @Test
    @Disabled("Not Yet implemented")
    void moveIfABlackBoxIsOnThePositionToCheck() {
        assertEquals(200, 15, "to be implemented");
    }

}