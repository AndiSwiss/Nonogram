package Helpers.StringHelpers;

public class Strings {

    /**
     * Parses the last part of a line (after the last space) in to an integer. <br>
     * @throws NumberFormatException If the Integer could not be parsed.
     * @return int
     */
    public static int getLastIntegerFromString(String s) {
        String lastSubstring = s.substring(s.lastIndexOf(' ') + 1);
        try {
            return Integer.parseInt(lastSubstring);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
