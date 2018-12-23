package NonogramStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data storage for various values, which have to be set, changed or accessed across different classes. <br>
 * An instance of this gets created in Nonogram and saved there as a private variable. <br>
 * If any other class needs access, the Nonogram-class should pass that instance (or just some specific fields)
 * to that class-constructor or its class-method.
 */
public class Nonogram {

    private String title;
    private int horizontalBoxesCount;
    private int verticalBoxesCount;

    private List<Line> horizontalLines;    // Contains all the lines of Boxes and Numbers - in horizontal direction
    private List<Line> verticalLines;      // Contains all the lines of Boxes and Numbers - in vertical direction

    private int maxTopNumbers;
    private int maxSideNumbers;

    public int boxSize;


    /**
     * Constructor 1
     *
     * @param title       title
     * @param topNumbers  topNumbers
     * @param sideNumbers sideNumbers
     * @param boxSize     boxSize
     */
    public Nonogram(String title, List<NumberLine> topNumbers, List<NumberLine> sideNumbers, int boxSize) {
        this.title = title;
        this.boxSize = boxSize;

        // calculating the amount of horizontal and vertical boxes:
        horizontalBoxesCount = topNumbers.size();
        verticalBoxesCount = sideNumbers.size();

        // calculating maxTopNumbers and maxSideNumbers
        maxTopNumbers = 0;
        for (NumberLine one : topNumbers) {
            if (one.size() > maxTopNumbers) {
                maxTopNumbers = one.size();
            }
        }

        maxSideNumbers = 0;
        for (NumberLine one : sideNumbers) {
            if (one.size() > maxSideNumbers) {
                maxSideNumbers = one.size();
            }
        }

        // construct all the HORIZONTAL lines:
        horizontalLines = new ArrayList<>();
        // save all the created horizontal boxes for use when constructing the vertical boxes:
        List<List<Box>> horizontalBoxes = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < verticalBoxesCount; lineNumber++) {
            List<Box> horizontalBoxLine = new ArrayList<>();
            for (int i = 0; i < horizontalBoxesCount; i++) {
                horizontalBoxLine.add(new Box(i, lineNumber));
            }
            horizontalLines.add(new Line(horizontalBoxLine, sideNumbers.get(lineNumber), Direction.HORIZONTAL));
            horizontalBoxes.add(horizontalBoxLine);

        }

        // construct all the VERTICAL lines:
        verticalLines = new ArrayList<>();
        for (int columnNumber = 0; columnNumber < horizontalBoxesCount; columnNumber++) {
            List<Box> verticalBoxLine = new ArrayList<>();
            for (int i = 0; i < verticalBoxesCount; i++) {
                Box box = horizontalBoxes.get(i).get(columnNumber);
                verticalBoxLine.add(box);
            }
            verticalLines.add(new Line(verticalBoxLine, topNumbers.get(columnNumber), Direction.VERTICAL));
        }
    }

    //----------------//
    // Custom Methods //
    //----------------//

    /**
     * Changes the boxSize.
     *
     * @param value A positive value increases the boxSize, a negative value decreases the boxSize.
     */
    public void changeBoxSize(int value) {
        boxSize += value;
    }

    public Box getBox(int x, int y) {
        return getHorizontalLines().get(y).getBox(x);
    }

    //---------//
    // Getters //
    //---------//
    public String getTitle() {
        return title;
    }

    public int getHorizontalBoxesCount() {
        return horizontalBoxesCount;
    }

    public int getVerticalBoxesCount() {
        return verticalBoxesCount;
    }

    public List<Line> getHorizontalLines() {
        return horizontalLines;
    }

    public Line getHorizontalLine(int i) {
        return horizontalLines.get(i);
    }

    public List<Line> getVerticalLines() {
        return verticalLines;
    }

    public Line getVerticalLine(int i) {
        return verticalLines.get(i);
    }

    public int getMaxTopNumbers() {
        return maxTopNumbers;
    }

    public int getMaxSideNumbers() {
        return maxSideNumbers;
    }

    public List<NumberLine> getTopNumbers() {
        List<NumberLine> result = new ArrayList<>();
        for (Line line : verticalLines) {
            result.add(line.getNumberLine());
        }
        return result;
    }

    public NumberLine getTopNumberLine(int i) {
        return verticalLines.get(i).getNumberLine();
    }

    public List<NumberLine> getSideNumbers() {
        List<NumberLine> result = new ArrayList<>();
        for (Line line : horizontalLines) {
            result.add(line.getNumberLine());
        }
        return result;
    }

    public NumberLine getSideNumberLine(int i) {
        return horizontalLines.get(i).getNumberLine();
    }


    public int getBoxSize() {
        return boxSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nonogram nonogram = (Nonogram) o;
        return horizontalBoxesCount == nonogram.horizontalBoxesCount &&
                verticalBoxesCount == nonogram.verticalBoxesCount &&
                maxTopNumbers == nonogram.maxTopNumbers &&
                maxSideNumbers == nonogram.maxSideNumbers &&
                Objects.equals(horizontalLines, nonogram.horizontalLines) &&
                Objects.equals(verticalLines, nonogram.verticalLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(horizontalBoxesCount, verticalBoxesCount, horizontalLines, verticalLines, maxTopNumbers, maxSideNumbers);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        String header = "Nonogram{" +
                "title='" + title + '\'' +
                ", horizontalBoxesCount=" + horizontalBoxesCount +
                ", verticalBoxesCount=" + verticalBoxesCount +
                ", maxTopNumbers=" + maxTopNumbers +
                ", maxSideNumbers=" + maxSideNumbers +
                ", boxSize=" + boxSize +
                "}\n";
        output.append(header);
        for (Line line : horizontalLines) {
            output.append(line).append('\n');
        }

        // and append the vertical lines, but just the numbers:
        output.append("Vertical lines, just the numbers (since formatting that would be quite hard " +
                "(especially with numbers greater that 10):\n");
        for (int i = 0; i < horizontalBoxesCount; i++) {
            output.append(getTopNumberLine(i)).append('\n');
        }
        return output.toString();
    }
}
