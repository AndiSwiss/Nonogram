package Tests.NonogramStructure;

import NonogramStructure.Number;
import NonogramStructure.NumberLine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberLineTest {
    private static NumberLine numberLine;

    @BeforeEach
    void setUp() {
        List<Number> numberList = new ArrayList<>();
        numberList.add(new Number(5));
        numberList.add(new Number(7));
        numberList.add(new Number(14));
        numberLine = new NumberLine(numberList);
    }

    @Test
    void toString_start() {
        assertEquals("[5, 7, 14]", numberLine.toString());
    }

    @Test
    void toString_crossOut() {
        numberLine.get(2).crossOut();
        assertEquals("[5, 7, \u03361\u03364]", numberLine.toString());
    }

    @Test
    void toString_unCrossAndCrossOutAnother() {
        System.out.println(numberLine);
        numberLine.get(2).crossOut();
        numberLine.get(2).unCross();
        numberLine.get(0).crossOut();
        System.out.println(numberLine);
        assertEquals("[\u03365, 7, 14]", numberLine.toString());
    }
}