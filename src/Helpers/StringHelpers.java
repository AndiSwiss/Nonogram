package Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelpers {

    /**
     * Searches for the last integer in a String and parses it to an integer. <br>
     *
     * @return int
     * @throws IllegalArgumentException If no integer was found.
     */
    public static int getLastIntegerFromString(String s) {
        List<Integer> ints = getIntegersFromString(s);
        if (ints == null || ints.size() == 0) {
            throw new IllegalArgumentException("The String did not contain any integers!");
        }
        return ints.get(ints.size() - 1);
    }


    /**
     * Searches for all integers in a String and parses them to integers. <br>
     * @throws IllegalArgumentException If a double was found in the string.
     * @throws IllegalArgumentException If no integer was found.
     * @return List<Integer> The list of integers or null, if none were found
     */
    public static List<Integer> getIntegersFromString(String s) {

        // check for occurrence of doubles and throw an error if you found something like "4.6" [digit.digit]:
        if (s.contains(".")) {

            // search for anything like  "4.6" (so a digit, a dot and a digit):
            // a dot can be search with \\.
            // [0-9] stands for a digit between 0 and  9
            // you can also use the quick action "Check RegExp":
            // work with a pattern and the matcher: https://www.tutorialspoint.com/java/java_regular_expressions.htm
            Pattern p = Pattern.compile("[0-9]\\.[0-9]");
            Matcher m = p.matcher(s);
            if (m.find()) {
                throw new IllegalArgumentException("Found at least one double: *" + m.group() + "*");
            }
        }

        // split the strings by anything that is not '0-9' or '-'
        // regex-format -> http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
        String[] split = s.split("[^0-9-]");

        List<Integer> result = new ArrayList<>();

        for (String one : split) {
            if (one.length() > 0) {
                try {
                    result.add(Integer.parseInt(one));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }


        if (result.size() == 0) {
            throw new IllegalArgumentException("The String did not contain any integers!");
        }

        return result;
    }
}
