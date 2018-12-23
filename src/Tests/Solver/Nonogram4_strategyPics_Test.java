package Tests.Solver;

import Data.InputDataHandler;
import NonogramStructure.*;
import NonogramStructure.Number;
import Solver.Solver;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Run all tests in the whole project or all test in a certain package easily with a test-configurations of JUnit! <br>
 * Disable not yet implemented methods with the annotation  @Disabled("Not Yet implemented")
 */
class Nonogram4_strategyPics_Test {
    private static Nonogram no;


    @BeforeEach
    void setUp() {
        InputDataHandler data = new InputDataHandler();
        String fileName = "src/Examples/nonogram5.txt";
        no = data.readAllFileInputs(fileName);
    }

    @Test
    void img_4167() {
        Line horiz2 = new Line(2, 15, Direction.HORIZONTAL);

        horiz2.getNumbers().add(new Number(7));
        horiz2.getNumbers().add(new Number(1));

        // Box 6 should be filled:
        horiz2.getBox(6).setState(State.BLACK);

        System.out.println(horiz2);
        Solver solver = new Solver(no);

        // since a new BLACK box should have been set, the following should return true:
        assertTrue(solver.strategy1(no.getHorizontalLine(2)));
        assertEquals(horiz2, no.getHorizontalLine(2));
    }

    @Test
    void img_4169() {
        Line horiz3 = new Line(3, 15, Direction.HORIZONTAL);

        horiz3.getNumbers().add(new Number(2));
        horiz3.getNumbers().add(new Number(5));
        horiz3.getNumbers().add(new Number(1));


        System.out.println(horiz3);

        Solver solver = new Solver(no);

        // no box should be filled, hence it should return false:
        assertFalse(solver.strategy1(no.getHorizontalLine(3)));

        assertEquals(horiz3, no.getHorizontalLine(3));
    }

    @Test
    void allHorizontalLinesFirstRun() {
        System.out.println("\n------------------");
        System.out.println("allHorizontalLines");
        Solver solver = new Solver(no);
        for (Line hLine : no.getHorizontalLines()) {
            solver.strategy1(hLine);
            System.out.println(hLine);
        }

        Line horiz2 = new Line(2, 15, Direction.HORIZONTAL);
        horiz2.getBox(6).setState(State.BLACK);

        Line horiz15 = new Line(15, 15, Direction.HORIZONTAL);
        for (int i = 0; i < 4; i++) {
            horiz15.getBox(1 + i).setState(State.BLACK);
        }
        for (int i = 0; i < 7; i++) {
            horiz15.getBox(7 + i).setState(State.BLACK);
        }

        Line horiz17 = new Line(17, 15, Direction.HORIZONTAL);
        for (int i = 0; i < 3; i++) {
            horiz17.getBox(2 + i).setState(State.BLACK);
        }
        for (int i = 0; i < 5; i++) {
            horiz17.getBox(8 + i).setState(State.BLACK);
        }


        for (int i = 0; i < no.getVerticalBoxesCount(); i++) {
            if (i == 2) {
                assertEquals(horiz2.getBoxes(), no.getHorizontalLine(i).getBoxes());
            } else if (i == 15) {
                assertEquals(horiz15.getBoxes(), no.getHorizontalLine(i).getBoxes());
            } else if (i == 17) {
                assertEquals(horiz17.getBoxes(), no.getHorizontalLine(i).getBoxes());
            } else {
                Line emptyLine = new Line(i, 15, Direction.HORIZONTAL);
                assertEquals(emptyLine.getBoxes(), no.getHorizontalLine(i).getBoxes());
            }
        }

        System.out.println("END - allHorizontalLines.");
    }

    @Test
    void allVerticalLinesFirstRun() {
        System.out.println("\n----------------");
        System.out.println("allVerticalLines");
        Solver solver = new Solver(no);
        for (Line vLine : no.getVerticalLines()) {
            solver.strategy1(vLine);
            System.out.println(vLine);
        }

        Line vertical9 = new Line(9, 20, Direction.VERTICAL);
        vertical9.getBox(8).setState(State.BLACK);

        for (int i = 0; i < no.getHorizontalBoxesCount(); i++) {
            if (i == 9) {
                assertEquals(vertical9.getBoxes(), no.getVerticalLine(i).getBoxes());
            } else {
                Line emptyLine = new Line(i, 20, Direction.VERTICAL);
                assertEquals(emptyLine.getBoxes(), no.getVerticalLine(i).getBoxes());
            }
        }

        System.out.println("END - allVerticalLines");

    }
}

