package Tests.NonogramStructure;

import NonogramStructure.Number;
import NonogramStructure.NumberLine;
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

    @Test
    void equals() {
        List<Number> numberList2 = new ArrayList<>();
        numberList2.add(new Number(7));
        numberList2.add(new Number(14));
        numberList2.add(new Number(5));

        NumberLine numberLine2 = new NumberLine(numberList2);

        assertNotEquals(numberLine, numberLine2);

        // sorting the above arrayList, so it will be in the same order like numberLine:
        numberList2.sort((o1, o2) -> {
            if (o1.getN() == o2.getN()) {
                return 0;
            }
            return o1.getN() < o2.getN() ? -1 : 1;
        });
        numberLine2 = new NumberLine(numberList2);

        assertEquals(numberLine, numberLine2);

        numberLine2.get(2).crossOut();
        assertNotEquals(numberLine, numberLine2);

        numberLine.get(2).crossOut();
        System.out.printf("numberLine1: %s, numberLine2: %s\n", numberLine, numberLine2);
        assertEquals(numberLine, numberLine2);
    }
}