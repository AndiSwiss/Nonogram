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


        String expected = "Line: [3, 2] |          | direction=HORIZONTAL, lineNumber=3";
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

        char b = '\u258b';
        char w = '\u2591';
        expected = "Line: [\u03363, 2] |" + w + b + b + b + w + "   " + b + w + "| direction=HORIZONTAL, lineNumber=3";
        assertEquals(expected, line.toString());
        System.out.println(line);

    }
}