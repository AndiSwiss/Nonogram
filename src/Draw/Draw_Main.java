package Draw;

import processing.core.PApplet;

import java.util.List;

public class Draw_Main extends PApplet {

    private static int width;
    private static int height;
    private static int boxSize;
    private static int horizontalBoxes;
    private static int verticalBoxes;
    private static int maxTopNumbers;
    private static int maxSideNumbers;
    private static List<List<Integer>> topNumbers;
    private static List<List<Integer>> sideNumbers;

    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        PApplet.main("Draw.Draw_Main");
    }

    @Override
    public void setup() {
        size(width, height);
        frameRate(30);

        drawBackground();
        drawDigits();

    }


    @Override
    public void draw() {

    }

    @Override
    public void keyPressed() {
        if (keyCode == UP) {
            //
        } else if (keyCode == DOWN) {
            //
        } else if (keyCode == RIGHT) {
            //
        } else if (keyCode == LEFT) {
            //
        }
    }


    //---------------------//
    // Custom Draw Methods //
    //---------------------//
    private void drawBackground() {
        fill(255);
        stroke(127);

        strokeWeight(3);
        // top box:
        rect(boxSize * (1 + maxSideNumbers), boxSize,
                boxSize * horizontalBoxes, boxSize * maxTopNumbers);

        // side box:
        rect(boxSize, boxSize * (1 + maxTopNumbers),
                boxSize * maxSideNumbers, boxSize * verticalBoxes);


        //main box:
        rect(boxSize * (1 + maxSideNumbers),
                boxSize * (1 + maxTopNumbers),
                boxSize * horizontalBoxes, boxSize * verticalBoxes);

        // thin lines:
        // horizontally:
        strokeWeight(1);
        int initialY = boxSize * (1 + maxTopNumbers);
        for (int i = 1; i < verticalBoxes; i++) {
            if (i % 5 == 0) {
                strokeWeight(2);
            }
            int y = initialY + i * boxSize;
            line(boxSize, y, width - boxSize, y);
            strokeWeight(1);
        }

        // vertically:
        int initialX = boxSize * (1 + maxSideNumbers);
        for (int i = 1; i < horizontalBoxes; i++) {
            if (i % 5 == 0) {
                strokeWeight(2);
            }
            int x = initialX + i * boxSize;
            line(x, boxSize, x, height - boxSize);
            strokeWeight(1);
        }
    }

    private void drawDigits() {
        // todo: draw '...topNumbers;' and '...sideNumbers':

    }


    //-------------------//
    // Getter and Setter //
    //-------------------//
    public static void setWidth(int width) {
        Draw_Main.width = width;
    }

    public static void setHeight(int height) {
        Draw_Main.height = height;
    }

    public static int getBoxSize() {
        return boxSize;
    }

    public static void setBoxSize(int boxSize) {
        Draw_Main.boxSize = boxSize;
    }

    public static int getVerticalBoxes() {
        return verticalBoxes;
    }

    public static void setVerticalBoxes(int verticalBoxes) {
        Draw_Main.verticalBoxes = verticalBoxes;
    }

    public static int getHorizontalBoxes() {
        return horizontalBoxes;
    }

    public static void setHorizontalBoxes(int horizontalBoxes) {
        Draw_Main.horizontalBoxes = horizontalBoxes;
    }

    public static void setMaxTopNumbers(int maxTopNumbers) {
        Draw_Main.maxTopNumbers = maxTopNumbers;
    }

    public static void setMaxSideNumbers(int maxSideNumbers) {
        Draw_Main.maxSideNumbers = maxSideNumbers;
    }

    public static void setTopNumbers(List<List<Integer>> topNumbers) {
        Draw_Main.topNumbers = topNumbers;
    }

    public static void setSideNumbers(List<List<Integer>> sideNumbers) {
        Draw_Main.sideNumbers = sideNumbers;
    }
}
