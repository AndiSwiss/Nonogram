package Tests.NonogramStructure;

import NonogramStructure.Number;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    void smallNumberToString() {
        Number n1 = new Number(6);
        assertEquals("6", n1.toString());
    }

    @Test
    void smallNumberToStringCrossedOut() {
        Number n1 = new Number(6);
        n1.crossOut();
        char cross = 0x0336;
        String crossedString = "" + cross + 6;
        assertEquals(crossedString, n1.toString());
    }

    @Test
    void largerNumberToString() {
        Number n2 = new Number(13);
        assertEquals("13", n2.toString());
    }

    @Test
    void largerNumberToStringCrossedOut() {
        Number n2 = new Number(13);
        n2.crossOut();
        char strikethrough = 0x0336;
        String crossedString = "" + strikethrough + 1 + strikethrough + 3;
        assertEquals(crossedString, n2.toString());
    }
}