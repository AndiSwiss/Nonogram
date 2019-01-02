package Tests.NonogramStructure;

import NonogramStructure.*;
import NonogramStructure.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    private static Line line1;
    private static Line line2;


    @BeforeEach
    void setUp() {
        // line 1:
        List<Number> numberList = new ArrayList<>();
        numberList.add(new Number(3));
        numberList.add(new Number(2));
        NumberLine numberLine = new NumberLine(numberList);

        int lineNumber = 3;
        List<Box> boxes = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            boxes.add(new Box(x, lineNumber));
        }
        line1 = new Line(boxes, numberLine, Direction.HORIZONTAL);

        // line 2:
        line2 = new Line(4, 10, Direction.VERTICAL);
        // create numbers:
        line2.getNumbers().add(new Number(4));
        line2.getNumbers().add(new Number(2));

        // set a white box
        line2.getBox(6).setState(State.WHITE);

        // set boxes to different states: Intersected by the number 5:
        line2.getBox(2).setState(State.BLACK);
        line2.getBox(3).setState(State.BLACK);
        // Intersected by the number 2:
        line2.getBox(8).setState(State.BLACK);

        // set marks:
        line2.getBox(0).setMarkT(0);
        line2.getBox(1).setMarkT(0);
        line2.getBox(2).setMarkT(0);
        line2.getBox(3).setMarkT(0);

        line2.getBox(7).setMarkT(1);
        line2.getBox(8).setMarkT(1);

        line2.getBox(9).setMarkB(0);  // is now index 0 with the new reversed logic!
        line2.getBox(8).setMarkB(0);

        line2.getBox(5).setMarkB(1);  // is now index 1 with the new reversed logic!
        line2.getBox(4).setMarkB(1);
        line2.getBox(3).setMarkB(1);
        line2.getBox(2).setMarkB(1);
    }

    @Test
    void toString_simple() {
        String expected = "                         3  2 |░░░░░░░░░░| lineNr=3, dir=HORIZONTAL";
        assertEquals(expected, line1.toString());

        System.out.println(line1);
    }

    @Test
    void toString_moreComplex() {
        // solve some:
        line1.getBox(0).setState(State.WHITE);
        line1.getBox(1).setState(State.BLACK);
        line1.getBox(2).setState(State.BLACK);
        line1.getBox(3).setState(State.BLACK);
        line1.getBox(4).setState(State.WHITE);
        line1.getNumber(0).crossOut();

        line1.getBox(8).setState(State.BLACK);
        line1.getBox(9).setState(State.WHITE);

        String expected = "                         ̶3  2 | ▋▋▋ ░░░▋ | lineNr=3, dir=HORIZONTAL";
        assertEquals(expected, line1.toString());
        System.out.println(line1);
/*
        String symbols = "̶3 ░ ▋";
        System.out.println("The following Symbols in Unicode are :" + symbols
                + " dash: 0x0" + Integer.toHexString(symbols.charAt(0))
                + " number: 0x00" + Integer.toHexString(symbols.charAt(1))
                + " pattern1: 0x" + Integer.toHexString(symbols.charAt(3))
                + " pattern2: 0x" + Integer.toHexString(symbols.charAt(5)));
*/
    }

    @Test
    void reversed() {
        // reversed:
        Line reversed = line2.reversed();
        // boxes:
        assertEquals(State.UNKNOWN, reversed.getBox(0).getState());
        assertEquals(State.BLACK, reversed.getBox(1).getState());
        assertEquals(State.UNKNOWN, reversed.getBox(2).getState());
        assertEquals(State.WHITE, reversed.getBox(3).getState());
        assertEquals(State.UNKNOWN, reversed.getBox(4).getState());
        assertEquals(State.UNKNOWN, reversed.getBox(5).getState());
        assertEquals(State.BLACK, reversed.getBox(6).getState());
        assertEquals(State.BLACK, reversed.getBox(7).getState());
        assertEquals(State.UNKNOWN, reversed.getBox(8).getState());
        assertEquals(State.UNKNOWN, reversed.getBox(9).getState());

        // numbers:
        assertEquals(2, reversed.getNumber(0).getN());
        assertEquals(4, reversed.getNumber(1).getN());
    }

    @Test
    void getMarkForBox_forward() {
        assertEquals(0, line2.getMarkForBox(0));
        assertEquals(0, line2.getMarkForBox(1));
        assertEquals(0, line2.getMarkForBox(2));
        assertEquals(0, line2.getMarkForBox(3));
        assertEquals(-1, line2.getMarkForBox(4));
        assertEquals(-1, line2.getMarkForBox(5));
        assertEquals(-1, line2.getMarkForBox(6));
        assertEquals(1, line2.getMarkForBox(7));
        assertEquals(1, line2.getMarkForBox(8));
        assertEquals(-1, line2.getMarkForBox(9));
    }

    @Test
    void getMarkForBox_reversed() {
        Line reversed = line2.reversed();
        assertEquals(0, reversed.getMarkForBox(0));  // -1
        assertEquals(0, reversed.getMarkForBox(1));  //  1
        assertEquals(-1, reversed.getMarkForBox(2)); //  1
        assertEquals(-1, reversed.getMarkForBox(3)); // -1
        assertEquals(1, reversed.getMarkForBox(4));  // -1
        assertEquals(1, reversed.getMarkForBox(5));  // -1
        assertEquals(1, reversed.getMarkForBox(6));  //  0
        assertEquals(1, reversed.getMarkForBox(7));  //  0
        assertEquals(-1, reversed.getMarkForBox(8)); //  0
        assertEquals(-1, reversed.getMarkForBox(9)); //  0
    }

    @Test
    void setMarkForBox_horizontal() {
        Line line = new Line(7, 15, Direction.HORIZONTAL);

        line.setMarkForBox(3, 0);
        line.setMarkForBox(14, 1);

        assertEquals(0, line.getBox(3).getMarkL());
        assertEquals(1, line.getBox(14).getMarkL());

        // reversed:
        line.reversed().setMarkForBox(13, 0);
        assertEquals(0, line.getBox(1).getMarkR());
    }

    @Test
    void setMarkForBox_vertical() {
        Line line = new Line(3, 10, Direction.VERTICAL);

        line.setMarkForBox(1, 5);
        assertEquals(5, line.getBox(1).getMarkT());

        // reversed:
        line.reversed().setMarkForBox(2, 3);
        assertEquals(3, line.getBox(7).getMarkB());
    }

    @Test
    void getFirstPossiblePositionForNumber() {
        assertEquals(0, line2.getFirstPossiblePositionForNumber(0));
        assertEquals(7, line2.getFirstPossiblePositionForNumber(1));
    }

    @Test
    void getLastPossiblePositionForNumber() {
        assertEquals(5, line2.getLastPossiblePositionForNumber(0));
        assertEquals(9, line2.getLastPossiblePositionForNumber(1));
    }
}