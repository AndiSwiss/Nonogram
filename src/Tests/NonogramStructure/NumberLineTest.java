package Tests.NonogramStructure;

import NonogramStructure.Number;
import NonogramStructure.NumberLine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberLineTest {

    @Test
    void test_toString() {
        List<Number> numberList = new ArrayList<>();
        numberList.add(new Number(5));
        numberList.add(new Number(7));
        numberList.add(new Number(14));
        NumberLine numberLine = new NumberLine(numberList);
        assertEquals("[5, 7, 14]", numberLine.toString());

        numberLine.get(2).crossOut();
        System.out.println(numberLine);
        assertEquals("[5, 7, \u03361\u03364]", numberLine.toString());

        numberLine.get(2).unCross();
        numberLine.get(0).crossOut();
        System.out.println(numberLine);
        assertEquals("[\u03365, 7, 14]", numberLine.toString());
    }
}