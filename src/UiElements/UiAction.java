package UiElements;

import Draw.DrawBasicObjects;
import Draw.DrawMain;
import Draw.Drawer;
import Data.Zone;
import NonogramStructure.Nonogram;
import Solver.Solver;

public class UiAction {

    private boolean popupIsActive = false;
    private int lineNumberForActionForKeyPressed;

    public void actionForUiElement(DrawMain drawMain, UiElement ui) {
        Drawer drawer = drawMain.getDrawer();

        // receiving the elements needed for this method:
        UiElementList ul = drawMain.getUl();
        Nonogram no = drawMain.getNo();
        Nonogram solutionFile = drawMain.getSolutionFile();
        Solver solver = drawMain.getSolver();


        if (ui instanceof UiFileChooser) {

            // deselect all elements of this group:
            for (UiElement uiElement : ul.getUiElements()) {
                if (uiElement instanceof UiFileChooser) {
                    uiElement.setSelected(false);
                }
            }

            // select the active element:
            ui.setSelected(true);

            // load the chosen example:
            String fileName = ui.getName();
            drawMain.loadNewExample(fileName);
        }


        if (ui instanceof UiSwitchableOption) {
            if (!ui.isSelected()) {
                ui.setSelected(true);
                switch (ui.getName()) {
                    case "drawSolution":
                        if (solutionFile != null) {
                            drawer.reDrawUi();
                        } else {
                            System.out.println("Solution file not found!");
                            DrawBasicObjects basicObjects = new DrawBasicObjects(drawMain, no);
                            basicObjects.drawText("ERROR: Solution file not found!", Zone.BOTTOM, 20, 8, 1);
                        }
                        break;
                    case "drawAllZoneBoxes":
                        Zone.drawAllZoneBoxesForTesting(drawMain, no);
                        break;
                    case "showMarks":
                        drawer.drawMarks();
                        break;
                    default:
                        throw new IllegalArgumentException("unknown UiSwitchableOption with name " + ui.getName());
                }
            } else {
                ui.setSelected(false);
                drawer.reDrawUi();
            }
            // draw the UiElement again to see the effect of the selection:
            drawer.drawUiElement(ui);
        }

        if (ui instanceof UiClickableOption) {
            switch (ui.getName()) {
                case "makeSmaller":
                    no.changeBoxSize(-1);
                    System.out.println("New boxSize is " + no.getBoxSize());
                    break;
                case "makeLarger":
                    no.changeBoxSize(1);
                    System.out.println("New boxSize is " + no.getBoxSize());
                    break;
                case "solverHorizontalOnce":
                    solver.strategy1AllHorizontal(no);
                    break;
                case "solverVerticalOnce":
                    solver.strategy1AllVertical(no);
                    break;
                default:
                    throw new IllegalArgumentException("unknown UiSwitchableOption with name " + ui.getName());
            }
            drawer.reDrawUi();
        }

        if (ui instanceof UiPopUpInvoker) {
            switch (ui.getName()) {
                case "solverOneHorizontalLine":
                    drawer.drawTextEntryPopUp("Enter horizontal line-number:");
                    break;
                case "solverOneVerticalLine":
                    drawer.drawTextEntryPopUp("Enter vertical line-number:");
                    break;
                default:
                    throw new IllegalArgumentException("unknown UiPopUpInvoker with name " + ui.getName());
            }

            popupIsActive = true;
            //initialize the lineNumber:
            lineNumberForActionForKeyPressed = 0;

        }
    }

    public void actionForKeyPressed(char key) {

        if (popupIsActive) {

            if (key >= '0' && key <= '9') {
                lineNumberForActionForKeyPressed *= 10;
                lineNumberForActionForKeyPressed += key - '0';

                // todo: show the entered number in the UI

                System.out.println("current lineNumber " + lineNumberForActionForKeyPressed);
            }

            // ending:
            if (key == 10) {
                popupIsActive = false;
                // todo: call the corresponding uiAction...
            }
        } else {

            // ASCII-values: According to https://en.wikipedia.org/wiki/ASCII
            switch (key) {
                case 8:
                    // int-value for ASCII Backspace:
                    System.out.println("Backspace was pressed");
                    break;
                case 10:
                    // int-value for ASCII Enter:
                    System.out.println("Line feed (Enter) was pressed");
                    break;
                case 27:
                    // int-value for ASCII Escape:
                    // (but Escape also quits the processing-app!)
                    System.out.println("Escape was pressed");
                    break;
                case 'y':
                    // for dialogs, don't use Escape, instead use 'y', 'n' and 'c' (in questions like "do you want to save this file"?
                    System.out.println("'y' (\u0332yes) was pressed");
                    break;
                case 'n':
                    System.out.println("'n' (\u0332no) was pressed");
                    break;
                case 'c':
                    System.out.println("'c' (\u0332cancel) was pressed");
                    break;
            }

            // numbers:
            if (key >= '0' && key <= '9') {
                int n = key - '0';
                System.out.println(n + " was pressed");
            }
        }
    }
}
