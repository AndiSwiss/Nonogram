package UiElements;

import Data.InitialData;
import Data.NonogramLibraryAccess;
import Draw.DrawBasicObjects;
import Draw.DrawMain;
import Draw.Drawer;
import Data.Zone;
import NonogramStructure.Nonogram;
import Solvers.Solver;

import java.nio.file.Path;
import java.util.List;

public class UiAction {

    private String popUpIsActiveFor;
    private int inputFromPopUp;
    private UiElementList ul;
    private Drawer drawer;
    private DrawMain drawMain;
    private Nonogram no;
    private Nonogram solutionFile;
    private Solver solver;
    private DrawBasicObjects basicObjects;
    private final InitialData id;


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

            selectOnlyActiveFileChooserAndDeselectOthers(ui);

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
                            basicObjects.drawText("ERROR: Solution file not found!", Zone.BOTTOM, 14, 12, 1);
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
                    solver.strategyInOneDirection(no, 1, true);
                    break;
                case "solverVerticalOnce":
                    solver.strategyInOneDirection(no, 1, false);
                    break;
                case "solverRunStrategy1AsLongAsPossible":
                    solver.runStrategyAsLongAsPossible(no, 1);
                    break;
                case "solverRunStrategy2AsLongAsPossible":
                    solver.runStrategyAsLongAsPossible(no, 2);
                    break;
                case "solverRunStrategy3AsLongAsPossible":
                    solver.runStrategyAsLongAsPossible(no, 3);
                    break;
                case "solveNonogram":
                    solver.runAllStrategiesAsLongAsPossible(no);
                    break;
                default:
                    throw new IllegalArgumentException("unknown UiSwitchableOption with name " + ui.getName());
            }
            drawer.reDrawUi();
        }

        if (ui instanceof UiPopUpInvoker) {
            String message;
            switch (ui.getName()) {
                case "solverOneHorizontalLine":
                    message = "Enter horizontal line-number:";
                    callPopUpFromInvoker(ui, message);
                    break;
                case "solverOneVerticalLine":
                    message = "Enter vertical line-number:";
                    callPopUpFromInvoker(ui, message);
                    break;
                case "fromNonogramLibrary":
                    selectOnlyActiveFileChooserAndDeselectOthers(ui);
                    // calculate amount of nonograms in the library:
                    Path pathToLibrary = id.pathToNonogramLibrary;
                    int nonogramsInLibrary = new NonogramLibraryAccess().getListOfNonogramsInLibrary(pathToLibrary).size();
                    message = nonogramsInLibrary + " nonograms found. Enter nr:";
                    callPopUpFromInvoker(ui, message);
                    break;
                default:
                    throw new IllegalArgumentException("unknown UiPopUpInvoker with name " + ui.getName());
            }
        }
    }

    /**
     * Helper-Method for actionForUiElement
     * @param ui UiElement
     */
    private void selectOnlyActiveFileChooserAndDeselectOthers(UiElement ui) {
        // deselect all elements of the UiFileChooser group:
        for (UiElement uiElement : ul.getUiElements()) {
            if (uiElement instanceof UiFileChooser) {
                uiElement.setSelected(false);
            }
        }
        // also deselect the nonogram-library-file-chooser:
        if (ui.getName().equals("fromNonogramLibrary")) {
            ui.setSelected(false);
        }

        // select the active element:
        ui.setSelected(true);
    }

    /**
     * Helper-Method for actionForUiElement (if ui instanceof UiPopUpInvoker)
     * @param ui UiElement
     * @param message message for the PopUp-Window
     */
    private void callPopUpFromInvoker(UiElement ui, String message) {
        drawer.drawTextEntryPopUp(message);
        popUpIsActiveFor = ui.getName();
        //initialize the input:
        inputFromPopUp = 0;
    }

    public void actionForKeyPressed(char key) {
        if (popUpIsActiveFor != null) {

            if (key >= '0' && key <= '9') {
                int number = key - '0';
                inputFromPopUp *= 10;
                inputFromPopUp += number;


                // Show the entered number in the UI.
                // By looking at the String of the number, I can see the amount of digits it has. I can use that as the x-value:
                int x = String.valueOf(inputFromPopUp).length();
                basicObjects.drawText("" + number, Zone.POPUP, 16 + (x * 0.6), 1, 1);

                checkIfLineNumberIsTooBigAndShowMessageAndResetEnteredLineNumber();
            }

            // ending with the Enter-key (ASCII-value 10):
            if (key == 10) {
                // call the corresponding uiAction...
                if (popUpIsActiveFor.equals("solverOneHorizontalLine")) {
                    solver.strategy1(no.getHorizontalLine(inputFromPopUp));
                } else if (popUpIsActiveFor.equals("solverOneVerticalLine")) {
                    solver.strategy1(no.getVerticalLine(inputFromPopUp));
                } else if (popUpIsActiveFor.equals("fromNonogramLibrary")) {
                    // load the chosen example:
                    Path pathToLibrary = id.pathToNonogramLibrary;
                    List<Path> nonogramPaths = new NonogramLibraryAccess().getListOfNonogramsInLibrary(pathToLibrary);
                    if (inputFromPopUp > 0 && inputFromPopUp < nonogramPaths.size() + 1) {
                        Path fileName = nonogramPaths.get(inputFromPopUp + 1);
                        // todo: change "loadNewExample" to accept a Path instead of a String!! -> there will be a lot to change!!
                        drawMain.loadNewExample(fileName);

                    } else {
                        System.out.println("Invalid input: " + inputFromPopUp + " (Should be greater than 0 and "
                            + "smaller or equal than " + nonogramPaths.size());
                    }
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
            ok = inputFromPopUp < no.getVerticalBoxesCount();
        } else {
            ok = inputFromPopUp < no.getHorizontalBoxesCount();
        }

        // redraw original popUp-box, show an error message and reset the entered values:
        if (!ok) {
            if (horizontal) {
                drawer.drawTextEntryPopUp("Enter horizontal line-number:");
            } else {
                drawer.drawTextEntryPopUp("Enter vertical line-number:");
            }

            // error message:
            String message = "'" + inputFromPopUp + "' is not a valid LineNumber, try again";
            basicObjects.drawText(message, Zone.POPUP, 8, 2, 0.8, id.cDarkGrey2);

            // init values:
            inputFromPopUp = 0;
        }
    }
}
