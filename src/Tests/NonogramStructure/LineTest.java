package Tests.NonogramStructure;

import NonogramStructure.*;
import NonogramStructure.Number;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void test_toString() {
        List<Number> numberList = new ArrayList<>();
        numberList.add(new Number(3));
        numberList.add(new Number(2));
        NumberLine numberLine = new NumberLine(numberList);

        int lineNumber = 3;
        List<Box> boxes = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            boxes.add(new Box(x, lineNumber));
        }

        Line line = new Line(boxes, numberLine, Direction.HORIZONTAL);

        String expected = "                         3  2 |░░░░░░░░░░| lineNr=3, dir=HORIZONTAL";
        assertEquals(expected, line.toString());

        System.out.println(line);

        // solve some:
        line.getBox(0).setState(State.WHITE);
        line.getBox(1).setState(State.BLACK);
        line.getBox(2).setState(State.BLACK);
        line.getBox(3).setState(State.BLACK);
        line.getBox(4).setState(State.WHITE);
        line.getNumber(0).crossOut();

        line.getBox(8).setState(State.BLACK);
        line.getBox(9).setState(State.WHITE);

        expected = "                         ̶3  2 | ▋▋▋ ░░░▋ | lineNr=3, dir=HORIZONTAL";
        assertEquals(expected, line.toString());
        System.out.println(line);
/*
        String symbols = "̶3 ░ ▋";
        System.out.println("The following Symbols in Unicode are :" + symbols
                + " dash: 0x0" + Integer.toHexString(symbols.charAt(0))
                + " number: 0x00" + Integer.toHexString(symbols.charAt(1))
                + " pattern1: 0x" + Integer.toHexString(symbols.charAt(3))
                + " pattern2: 0x" + Integer.toHexString(symbols.charAt(5)));
*/

    }
}