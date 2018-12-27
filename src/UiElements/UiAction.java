package UiElements;

import Data.InitialData;
import Draw.DrawBasicObjects;
import Draw.DrawMain;
import Draw.Drawer;
import Data.Zone;
import NonogramStructure.Nonogram;
import Solver.Solver;

public class UiAction {

    private String popUpIsActiveFor;
    private int lineNumberForActionForKeyPressed;
    private UiElementList ul;
    private Drawer drawer;
    private DrawMain drawMain;
    private Nonogram no;
    private Nonogram solutionFile;
    private Solver solver;
    private DrawBasicObjects basicObjects;
    private InitialData id;


    public UiAction(DrawMain drawMain) {
        this.drawMain = drawMain;

        // receiving the elements needed for this method:
        drawer = drawMain.getDrawer();
        ul = drawMain.getUl();
        no = drawMain.getNo();
        solutionFile = drawMain.getSolutionFile();
        solver = drawMain.getSolver();

        basicObjects = new DrawBasicObjects(drawMain, no);
        id = new InitialData();
    }

    public void actionForUiElement(UiElement ui) {


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
                    popUpIsActiveFor = "solverOneHorizontalLine";
                    //initialize the lineNumber:
                    lineNumberForActionForKeyPressed = 0;
                    break;
                case "solverOneVerticalLine":
                    drawer.drawTextEntryPopUp("Enter vertical line-number:");
                    popUpIsActiveFor = "solverOneVerticalLine";
                    //initialize the lineNumber:
                    lineNumberForActionForKeyPressed = 0;
                    break;
                default:
                    throw new IllegalArgumentException("unknown UiPopUpInvoker with name " + ui.getName());
            }
        }
    }

    public void actionForKeyPressed(char key) {

        if (popUpIsActiveFor != null) {

            if (key >= '0' && key <= '9') {
                int number = key - '0';
                lineNumberForActionForKeyPressed *= 10;
                lineNumberForActionForKeyPressed += number;


                // Show the entered number in the UI.
                // By looking at the String of the number, I can see the amount of digits it has. I can use that as the x-value:
                int x = String.valueOf(lineNumberForActionForKeyPressed).length();
                basicObjects.drawText("" + number, Zone.POPUP, 16 + (x * 0.6), 1, 1);

                checkIfLineNumberIsTooBigAndShowMessageAndResetEnteredLineNumber();
            }

            // ending with the Enter-key (ASCII-value 10):
            if (key == 10) {
                // call the corresponding uiAction...
                if (popUpIsActiveFor.equals("solverOneHorizontalLine")) {
                    solver.strategy1(no.getHorizontalLine(lineNumberForActionForKeyPressed));
                } else if (popUpIsActiveFor.equals("solverOneVerticalLine")) {
                    solver.strategy1(no.getVerticalLine(lineNumberForActionForKeyPressed));
                }
                // resetting:
                popUpIsActiveFor = null;
                drawer.reDrawUi();
            }
        } else {

            // Just for reference
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

    private void checkIfLineNumberIsTooBigAndShowMessageAndResetEnteredLineNumber() {
        boolean horizontal = popUpIsActiveFor.equals("solverOneHorizontalLine");
        boolean ok;
        if (horizontal) {
            ok = lineNumberForActionForKeyPressed < no.getVerticalBoxesCount();
        } else {
            ok = lineNumberForActionForKeyPressed < no.getHorizontalBoxesCount();
        }

        // redraw original popUp-box, show an error message and reset the entered values:
        if (!ok) {
            if (horizontal) {
                drawer.drawTextEntryPopUp("Enter horizontal line-number:");
            } else {
                drawer.drawTextEntryPopUp("Enter vertical line-number:");
            }

            // error message:
            String message = "'" + lineNumberForActionForKeyPressed + "' is not a valid LineNumber, try again";
            basicObjects.drawText(message, Zone.POPUP, 8, 2, 0.8, id.cDarkGrey2);

            // init values:
            lineNumberForActionForKeyPressed = 0;
        }
    }
}
