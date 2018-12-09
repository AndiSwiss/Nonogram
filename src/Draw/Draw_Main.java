package Draw;

import processing.core.PApplet;

public class Draw_Main extends PApplet {

    private static int width;
    private static int height;
    private static int boxSize;
    private static int horizontalBoxes;
    private static int verticalBoxes;

    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        PApplet.main("Draw.Draw_Main");
    }

    @Override
    public void setup() {
        size(width, height);
        fill(255);
        frameRate(30);
        strokeWeight(3);
        rect(30, 20, horizontalBoxes * boxSize, verticalBoxes * boxSize);
        strokeWeight(2);

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
}
