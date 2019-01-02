package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import Solvers.Solver;
import Solvers.Solver2;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class Solver2Test_On_Nonogram5 {
    private static Line hLine15;
    private static Line hLine17;
    private static Line vLine9;
    private static Solver solver;
    private static Solver2 solver2;


    @BeforeEach
    void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "Examples/nonogram5.txt";
        Nonogram no = data.readAllFileInputs(fileName);
        hLine15 = no.getHorizontalLine(15);
        hLine17 = no.getHorizontalLine(17);
        vLine9 = no.getVerticalLine(9);
        solver = new Solver();
        solver2 = new Solver2();
    }


    @Test
    void moveMarksForwardToCoverAllBlackBoxesWhichAreAhead() {
        solver.strategy1(hLine15);
        solver.strategy1(hLine17);
        solver.strategy1(vLine9);

        // now, the mark for the number 4 (numberIndex2) in vLine9 in forward direction is still at
        // positions 10-13
        for (int i = 10; i <= 13; i++) {
            assertEquals(2, vLine9.getMarkForBox(i));
        }
        assertEquals(-1, vLine9.getMarkForBox(9));
        assertEquals(-1, vLine9.getMarkForBox(14));

        // but with the following method, it should advance to positions 14-17:
        solver2.moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(vLine9);

        for (int i = 14; i <= 17; i++) {
            assertEquals(2, vLine9.getMarkForBox(i));
        }
        // the previous marks should be deleted:
        for (int i = 9; i <= 13; i++) {
            System.out.printf("vLine9.getMarkForBox(%s): %s\n", i, vLine9.getMarkForBox(i));
            assertEquals(-1, vLine9.getMarkForBox(i));
        }
        assertEquals(-1, vLine9.getMarkForBox(18));
        assertEquals(-1, vLine9.getMarkForBox(19));
    }
}
