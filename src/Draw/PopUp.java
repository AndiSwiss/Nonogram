package Draw;

import Data.Align;
import Data.InitialData;
import Data.Zone;

class PopUp {

    private DrawBasicObjects basicObjects;
    private InitialData id;
    private int boxSize;

    PopUp(DrawBasicObjects basicObjects, int boxSize) {
        this.basicObjects = basicObjects;
        this.boxSize = boxSize;
        id = new InitialData();
    }


    void textEntryPopUp(String message) {
        // full box:
        basicObjects.drawRectangle(Zone.POPUP, 0, 0, Zone.POPUP.getSizeX() / boxSize,
                Zone.POPUP.getSizeY() / boxSize, Zone.POPUP.getColor(), 3, id.cLightGrey);

        // text-message:
        basicObjects.drawText(message, Zone.POPUP, 1, 1);

        // after the text-message:
        basicObjects.drawRectangle(Zone.POPUP, 16, 1, 3, 1, id.cLightGrey2, 1, id.cLightGrey2);

        // buttons:
        button("... and press enter", Align.RIGHT, true);
//        button("Cancel", Align.LEFT, false);
    }

    private void button(String message, Align horizontalAlign, boolean selectedForEnter) {
        int x;
        int y = 4;
        int buttonLength = 11;

        // x-values for the rectangle:
        switch (horizontalAlign) {
            case LEFT:
                x = 1;
                break;
            case CENTER:
                x = (Zone.POPUP.getSizeX() / boxSize) / 2 - (buttonLength / 2);
                System.out.println("Case CENTER: x=" + x);
                break;
            case RIGHT:
                x = (Zone.POPUP.getSizeX() / boxSize) - buttonLength - 1;
                System.out.println("Case RIGHT: x=" + x);
                break;
            default:
                throw new IllegalArgumentException("Unknown Alignment: " + horizontalAlign);
        }
        basicObjects.drawRectangle(Zone.POPUP, x, y, buttonLength, 1,
                selectedForEnter ? id.cLightGrey2 : id.cLightGrey3,
                1, id.cDarkGrey);

        // new x-values for the text
        x += buttonLength / 2;
        basicObjects.drawText(message, Zone.POPUP, x, y - 0.1, 1, id.cDarkGrey2, Align.CENTER, false);
    }
}
