package Tests.Solver;

import NonogramStructure.Direction;
import NonogramStructure.Line;
import NonogramStructure.State;
import Solvers.Solver3;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class Solver3Test_On_Nonogram5 {
    private static Solver3 solver3;


    @BeforeEach
    void setUp() {
        solver3 = new Solver3();
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
}
