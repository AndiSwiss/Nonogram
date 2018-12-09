package Draw;

import processing.core.PApplet;

public class Draw_Main extends PApplet {

    private static int width;
    private static int height;
    private static int boxSize;

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
        rect(30, 20, 20 * boxSize, 20 * boxSize);
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

/*
    @Override
    public static int getWidth() {
        return width;
    }
*/

    public static void setWidth(int width) {
        Draw_Main.width = width;
    }

/*
    @Override
    public static int getHeight() {
        return height;
    }
*/

    public static void setHeight(int height) {
        Draw_Main.height = height;
    }

    public static int getBoxSize() {
        return boxSize;
    }

    public static void setBoxSize(int boxSize) {
        Draw_Main.boxSize = boxSize;
    }
}
