package Tests.Helpers;

import Helpers.StringHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {

    private static StringHelper sh = new StringHelper();

    @Test
    void getLastIntegerFromString() {
        String test1 = "Hello. 1 -2. 3 test, this should be correctly parsed: 42.";
        assertEquals(42, sh.getLastIntegerFromString(test1));

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(-2);
        integers.add(3);
        integers.add(42);
        assertEquals(integers, sh.getIntegersFromString(test1));
    }

    @Test
    void getLastIntegerFromString_notThrowAnError() {
        String test1 = "Hello. 1 -2. 3 test, this should be correctly parsed: 42.";
        // check the error throwing:
        // since it expects an "Executable", you can create it with a lambda:  () -> ...

        // should not throw an error:
        assertDoesNotThrow(() -> sh.getIntegersFromString(test1));
    }

    @Test
    void getLastIntegerFromString_errorNoIntegerToParse() {
        // should throw the error, because there is no integer to parse:
        String test2 = "This message does not contain an integer!";
        assertThrows(IllegalArgumentException.class, () -> sh.getIntegersFromString(test2));
    }

    @Test
    void getLastIntegerFromString_errorADoubleWasFound() {
        // should throw an error, because at least one double was found:
        String test3 = "This Message contains a double: 1 -25.5a22232 3 test, this should. be p4.5arsed:";
        assertThrows(IllegalArgumentException.class, () -> sh.getIntegersFromString(test3));
    }


}