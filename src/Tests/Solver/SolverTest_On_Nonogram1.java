package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import Solvers.Solver;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest_On_Nonogram1 {
    private static Line hLine9;  // horizontal line 9
    private static Line vLine7;  // vertical line 7
    private static Line vLine8;  // vertical line 8
    private static Solver solver;


    @BeforeEach
    void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "Examples/nonogram1.txt";
        Nonogram no = data.readAllFileInputs(fileName);
        hLine9 = no.getHorizontalLine(9);
        vLine7 = no.getVerticalLine(7);
        vLine8 = no.getVerticalLine(8);


        solver = new Solver();
    }

    @Test
    void markLineInOneDirection_problem3() {
        // in the file nonogram1 there is still an error thrown, if
        //  - run horizontal line 9
        //  - run vertical line 7
        //  - run vertical line 8
        //  - run horizontal line 9 again -> then the following error occurs: Index 15 out-of-bounds for length 15
        // -> the problem lies in the method Solver.checkIfAlreadyPassedTheMark() -> see test below:
        // -> the actual problem is not the method, but the fact that that mark doesn't exist anymore! It was
        // overwritten by the number 7 (numberIndex 0), so it cannot be found anymore!
        // -> solved in version v0.7i (problem -> see changes in commit) with breaking out of the if-loop
        // with a label -> see LabelBreakForCheck: in Solver.java!

        solver.strategy1(hLine9);
        solver.strategy1(vLine7);
        solver.strategy1(vLine8);
        solver.strategy1(hLine9);

        assertEquals(0, hLine9.getBox(8).getMarkL());
        assertEquals(-1, hLine9.getBox(9).getMarkL());
        assertEquals(1, hLine9.getBox(10).getMarkL());
        assertEquals(-1, hLine9.getBox(11).getMarkL());

    }

    @Test
    void checkIfAlreadyPassedTheMark() {
        // see also test markLineInOneDirection_problem3!
        solver.strategy1(hLine9);
        solver.strategy1(vLine7);
        solver.strategy1(vLine8);
//        solver.strategy1(hLine9);

        boolean passed = solver.checkIfAlreadyPassedTheMark(hLine9, 10, 1);
        assertTrue(passed);
    }



}
