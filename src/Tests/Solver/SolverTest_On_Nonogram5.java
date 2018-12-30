package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.Box;
import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import NonogramStructure.State;
import Solvers.Solver;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Run all tests in the whole project or all test in a certain package easily with a test-configurations of JUnit! <br>
 * Disable not yet implemented methods with the annotation  @Disabled("Not Yet implemented")
 */
class SolverTest_On_Nonogram5 {
    private static Line hLine2;  // horizontal line 2
    private static Line hLine8;  // horizontal line 8
    private static Line hLine15; // horizontal line 15
    private static Line hLine17; // horizontal line 17
    private static Line vLine9;  // vertical line 9
    private static Solver solver;
    private static Nonogram no;


    @BeforeEach
    void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "Examples/nonogram5.txt";
        no = data.readAllFileInputs(fileName);
        hLine2 = no.getHorizontalLine(2);
        hLine8 = no.getHorizontalLine(8);
        hLine15 = no.getHorizontalLine(15);
        hLine17 = no.getHorizontalLine(17);
        vLine9 = no.getVerticalLine(9);
        solver = new Solver();
    }


    @Test
    void runStrategy1AsLongAsPossible() {
        solver.runStrategy1AsLongAsPossible(no);

        for (Line line : no.getHorizontalLines()) {
            for (Box box : line.getBoxes()) {
                if (box.getPosX() == 6 && box.getPosY() == 2) {
                    System.out.println("checking box " + box);
                    assertEquals(State.BLACK, box.getState());
                } else if (box.getPosY() == 7 && box.getPosX() == 9) {
                    assertEquals(State.BLACK, box.getState());
                } else if (box.getPosY() == 8 && box.getPosX() == 9) {
                    assertEquals(State.BLACK, box.getState());
                } else if (box.getPosY() == 15 && ((box.getPosX() >= 1 && box.getPosX() <= 4) ||
                        (box.getPosX() >= 7 && box.getPosX() <= 13))) {
                    assertEquals(State.BLACK, box.getState());
                } else if (box.getPosY() == 17 && ((box.getPosX() >= 2 && box.getPosX() <= 4) ||
                        (box.getPosX() >= 8 && box.getPosX() <= 12))) {
                    assertEquals(State.BLACK, box.getState());
                } else {
                    System.out.println("checking box " + box);
                    assertEquals(State.UNKNOWN, box.getState());
                }
            }
        }

        // checking some marks, which should have been moved
    }

    @Test
    void strategy1AllHorizontal() {
        solver.strategy1AllHorizontal(no);

        for (Line line : no.getHorizontalLines()) {
            for (Box box : line.getBoxes()) {
                if (box.getPosX() == 6 && box.getPosY() == 2) {
                    assertEquals(State.BLACK, box.getState());
                } else if (box.getPosY() == 15 && ((box.getPosX() >= 1 && box.getPosX() <= 4) ||
                        (box.getPosX() >= 7 && box.getPosX() <= 13))) {
                    assertEquals(State.BLACK, box.getState());
                } else if (box.getPosY() == 17 && ((box.getPosX() >= 2 && box.getPosX() <= 4) ||
                        (box.getPosX() >= 8 && box.getPosX() <= 12))) {
                    assertEquals(State.BLACK, box.getState());
                } else {
                    assertEquals(State.UNKNOWN, box.getState());
                }
            }
        }
    }

    @Test
    void strategy1AllVertical() {
        solver.strategy1AllVertical(no);

        for (Line line : no.getVerticalLines()) {
            for (Box box : line.getBoxes()) {
                if (box.getPosX() == 9 && box.getPosY() == 8) {
                    assertEquals(State.BLACK, box.getState());
                } else {
                    assertEquals(State.UNKNOWN, box.getState());
                }
            }
        }
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
    void markBoxesWhichHaveSameMarksInOppositeDirection_horizontalLine15_withWhiteBox() {
        solver.markLine(hLine17);

        // mark a box white AFTER marking the line -> should throw an error later on:
        hLine17.getBox(3).setState(State.WHITE);

        assertThrows(IllegalArgumentException.class, () -> solver.markBoxesWhichHaveSameMarksInOppositeDirection(hLine17));
        System.out.println(hLine17);
    }

    @Test
    void markLineInOneDirection_horizontalLine2() {
        solver.markLineInOneDirection(hLine2);

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
        solver.markLineInOneDirection(hLine2.reversed());

        // this line contains 7 and 1 as digits, so the following output is expected:
        for (int i = 0; i < 6; i++) {
            assertEquals(-1, hLine2.getBox(i).getMarkR());
        }
        for (int i = 6; i < 13; i++) {
            assertEquals(1, hLine2.getBox(i).getMarkR());
        }

        assertEquals(-1, hLine2.getBox(13).getMarkR());
        assertEquals(0, hLine2.getBox(14).getMarkR());
    }

    @Test
    void markLineInOneDirection_problem1() {
        // delete current marks if they get changed!! otherwise, they will get duplicated, such as in example 5:
        //  If you run the solver for:
        //  - horizontal line 8
        //  - vertical line 9
        //  - horizontal line 8 again, then just the first mark gets moved, but the others not!
        // -> solved in version v0.7h (it was a very simple problem, see commit message)
        solver.strategy1(hLine8);

        System.out.println(hLine8);

        assertEquals(0, hLine8.getBox(14).getMarkR());
        assertEquals(0, hLine8.getBox(13).getMarkR());
        assertEquals(-1, hLine8.getBox(12).getMarkR());
        assertEquals(1, hLine8.getBox(11).getMarkR());
        assertEquals(1, hLine8.getBox(10).getMarkR());
        assertEquals(-1, hLine8.getBox(9).getMarkR());
        assertEquals(2, hLine8.getBox(8).getMarkR());
        assertEquals(2, hLine8.getBox(7).getMarkR());
        assertEquals(-1, hLine8.getBox(6).getMarkR());
        assertEquals(3, hLine8.getBox(5).getMarkR());
        assertEquals(3, hLine8.getBox(4).getMarkR());
        assertEquals(-1, hLine8.getBox(3).getMarkR());
        assertEquals(-1, hLine8.getBox(2).getMarkR());
        assertEquals(-1, hLine8.getBox(1).getMarkR());

        solver.strategy1(vLine9);
        // running the solver again for hLine8, that should move the marks for number 2 / 1 / 0:
        solver.strategy1(hLine8);

        System.out.print("MarkR in hLine8: ");
        for (int i = hLine8.getBoxesSize() - 1; i >= 0; i--) {
            System.out.print(i + ":" + hLine8.getBox(i).getMarkR() + " / ");
        }
        System.out.println();

        assertEquals(0, hLine8.getBox(14).getMarkR());
        assertEquals(0, hLine8.getBox(13).getMarkR());
        assertEquals(-1, hLine8.getBox(12).getMarkR());
        assertEquals(-1, hLine8.getBox(11).getMarkR());
        assertEquals(1, hLine8.getBox(10).getMarkR());
        assertEquals(1, hLine8.getBox(9).getMarkR());
        assertEquals(-1, hLine8.getBox(8).getMarkR());
        assertEquals(2, hLine8.getBox(7).getMarkR());
        assertEquals(2, hLine8.getBox(6).getMarkR());
        assertEquals(-1, hLine8.getBox(5).getMarkR());
        assertEquals(3, hLine8.getBox(4).getMarkR());
        assertEquals(3, hLine8.getBox(3).getMarkR());
        assertEquals(-1, hLine8.getBox(2).getMarkR());
        assertEquals(-1, hLine8.getBox(1).getMarkR());

    }

    @Test
    void markLineInOneDirection_problem2() {
        // In nonogram5, for the above scenario, it just works for the number index 2. The others are not deleted
        //  and also for the following scenario, an error "Index -1 out-of-bounds for length 20" is thrown:
        //  - vertical line 9
        //  - horizontal line 15
        //  - vertical line 9
        // solved in version v0.7h (problem -> see changes in commit)

        solver.strategy1(vLine9);

        // only box 8 should yet have been solved:
        assertEquals(State.UNKNOWN, vLine9.getBox(7).getState());
        assertEquals(State.BLACK, vLine9.getBox(8).getState());
        assertEquals(State.UNKNOWN, vLine9.getBox(9).getState());

        solver.strategy1(hLine15);


        solver.strategy1(vLine9);

        assertEquals(-1, vLine9.getBox(4).getMarkB());
        assertEquals(2, vLine9.getBox(5).getMarkB());
        assertEquals(-1, vLine9.getBox(6).getMarkB());

        // now, also the box 7 should be solved:
        // only box 8 should yet have been solved:
        assertEquals(State.UNKNOWN, vLine9.getBox(6).getState());
        assertEquals(State.BLACK, vLine9.getBox(7).getState());
        assertEquals(State.BLACK, vLine9.getBox(8).getState());
        assertEquals(State.UNKNOWN, vLine9.getBox(9).getState());
    }


    @Test
    void deleteAMarkIfItEqualsTheGivenNumberIndex() {
        solver.markLine(hLine2);

        // that should create the following markL:
        for (int i = 0; i < 7; i++) {
            assertEquals(0, hLine2.getBox(i).getMarkL());
        }
        assertEquals(1, hLine2.getBox(8).getMarkL());

        // now run the actual method to test:
        solver.deleteAMarkIfItEqualsTheGivenNumberIndex(hLine2, 5, 0);

        assertEquals(0, hLine2.getBox(4).getMarkL());
        // deleted mark:
        assertEquals(-1, hLine2.getBox(5).getMarkL());
        assertEquals(0, hLine2.getBox(6).getMarkL());
    }

    @Test
    void markANumberAndAdvancePosition() {
        int pos = solver.markANumberAndAdvancePosition(hLine15, 4, 1, hLine15.getNumber(1));

        assertEquals(12, pos);

        for (int i = 4; i < 12; i++) {
            assertEquals(1, hLine15.getBox(i).getMarkL());
        }
        assertEquals(-1, hLine15.getBox(3).getMarkL());
        assertEquals(-1, hLine15.getBox(12).getMarkL());
    }

    @Test
    void moveIfAWhiteSpaceWasFound() {
        vLine9.getBox(12).setState(State.WHITE);

        int pos = solver.moveIfAWhiteSpaceWasFound(vLine9, 10, vLine9.getNumber(2));

        assertEquals(13, pos);
    }

    @Test
    void moveIfAWhiteSpaceWasFound_reversed() {
        vLine9.getBox(12).setState(State.WHITE); // equals to the reversed box = 7

        Line vLine9Reversed = vLine9.reversed();

        // remember: position is also reversed (from the bottom!)
        int pos = solver.moveIfAWhiteSpaceWasFound(vLine9Reversed, 5, vLine9.getNumber(1));

        assertEquals(8, pos);
    }

    @Test
    void moveIfABlackBoxIsOnThePositionToCheck() {
        hLine15.getBox(1).setState(State.BLACK);
        int pos = solver.moveIfABlackBoxIsOnThePositionToCheck(hLine15, 2, 1);
        assertEquals(3, pos);


        vLine9.getBox(15).setState(State.BLACK); // equals to the reversed box = 4

        // reversed:
        Line vLine9Reversed = vLine9.reversed();

        // remember: position is also reversed (from the bottom!)
        pos = solver.moveIfABlackBoxIsOnThePositionToCheck(vLine9Reversed, 0, 4);
        assertEquals(1, pos);
    }
}