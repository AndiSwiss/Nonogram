package Tests.Solver;

import NonogramStructure.Box;
import NonogramStructure.Direction;
import NonogramStructure.Line;
import NonogramStructure.Number;
import NonogramStructure.State;
import Solvers.Solver;
import Solvers.Solver3;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class Solver3Test_On_Nonogram5 {
    private static Solver solver;
    private static Solver3 solver3;


    @BeforeEach
    void setUp() {
        solver3 = new Solver3();
        solver = new Solver();
    }

    @Test
    void setWhiteMarkForBoxRange_horizontal() {
        Line line = new Line(5, 15, Direction.HORIZONTAL);

        boolean changedSomething = solver3.setWhiteMarkForBoxRange(line, 3, 6);
        assertTrue(changedSomething);
        assertEquals(State.UNKNOWN, line.getBox(2).getState());
        assertEquals(State.WHITE, line.getBox(3).getState());
        assertEquals(State.WHITE, line.getBox(4).getState());
        assertEquals(State.WHITE, line.getBox(5).getState());
        assertEquals(State.WHITE, line.getBox(6).getState());
        assertEquals(State.UNKNOWN, line.getBox(7).getState());


        line.getBox(9).setState(State.BLACK);
        assertThrows(IllegalArgumentException.class, () -> solver3.setWhiteMarkForBoxRange(line, 8, 10));
    }

    @Test
    void setWhiteMarkForBoxRange_vertical() {
        Line line = new Line(5, 15, Direction.VERTICAL);
        boolean changedSomething = solver3.setWhiteMarkForBoxRange(line, 0, 3);
        assertTrue(changedSomething);
        assertEquals(State.WHITE, line.getBox(0).getState());
        assertEquals(State.WHITE, line.getBox(1).getState());
        assertEquals(State.WHITE, line.getBox(2).getState());
        assertEquals(State.WHITE, line.getBox(3).getState());
        assertEquals(State.UNKNOWN, line.getBox(4).getState());
    }

    @Test
    void setWhiteMarkForBoxRange_nothingChanged() {
        Line line = new Line(7, 15, Direction.HORIZONTAL);

        line.getBox(3).setState(State.WHITE);
        line.getBox(4).setState(State.WHITE);
        line.getBox(5).setState(State.WHITE);

        boolean changedSomething = solver3.setWhiteMarkForBoxRange(line, 3, 5);
        assertFalse(changedSomething);

    }

    @Test
    void setWhiteMarkForBoxRange_nothingToBeMarked() {
        Line line = new Line(7, 15, Direction.HORIZONTAL);

        boolean changedSomething = solver3.setWhiteMarkForBoxRange(line, 10, 5);
        assertFalse(changedSomething);

        for (Box b : line.getBoxes()) {
            assertEquals(State.UNKNOWN, b.getState());
        }
    }

    @Test
    void markABoxWhiteIfNotCoveredByAMarking() {
        Line line = new Line(10, 20, Direction.HORIZONTAL);

        Number n0 = new Number(3);
        Number n1 = new Number(4);
        Number n2 = new Number(2);

        line.getNumbers().add(n0);
        line.getNumbers().add(n1);
        line.getNumbers().add(n2);

        solver.markANumberAndAdvancePosition(line, 2, 0, n0);
        solver.markANumberAndAdvancePosition(line, 7, 1, n1);
        solver.markANumberAndAdvancePosition(line, 17, 2, n2);

        // reversed:
        System.out.println("Marking in reversed direction:");
        Line reversed = line.reversed();
        solver.markANumberAndAdvancePosition(reversed, 1, 0, n2);
        solver.markANumberAndAdvancePosition(reversed, 4, 1, n1);
        solver.markANumberAndAdvancePosition(reversed, 13, 2, n0);

        // that would result in the following black boxes:
        line.getBox(4).setState(State.BLACK);
        line.getBox(17).setState(State.BLACK);
        line.getBox(18).setState(State.BLACK);

        System.out.println(line);

        solver3.markABoxWhiteIfNotCoveredByAMarking(line);

        System.out.println(line);
        for (int i = 0; i < line.getBoxesSize(); i++) {
            State expected;
            if (i == 0 || i == 1 || i == 16 || i == 19) {
                expected = State.WHITE;
            } else if (i == 4 || i == 17 || i == 18) {
                expected = State.BLACK;
            } else {
                expected = State.UNKNOWN;
            }
            assertEquals(expected, line.getBox(i).getState());
        }
    }
}
