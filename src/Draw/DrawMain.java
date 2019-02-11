package Draw;

import Data.InitialData;
import Data.InputDataHandler;
import Data.Position;
import NonogramStructure.Nonogram;
import Solvers.Solver;
import UiElements.UiAction;
import UiElements.UiElement;
import UiElements.UiElementList;
import processing.core.PApplet;

import java.nio.file.Path;


/**
 * @author Andreas Ambühl
 * @version 1.0.1c
 * <p>
 * This is the main class, where the program can be started -> execute the main()-method to launch the application.
 */
public class DrawMain extends PApplet {

    private InitialData id;
    private Nonogram no;
    private Nonogram solutionFile;
    private UiElementList ul;
    private Solver solver;
    private Drawer drawer;
    private UiAction uiAction;


    // for tracking the mouse:
    private Position mousePressedPos = null;

    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        PApplet.main("Draw.DrawMain");
    }

    @Override
    public void setup() {
        id = new InitialData();
        ul = new UiElementList();
        solver = new Solver();

        size(id.myWidth, id.myHeight);
        System.out.printf("width: %s, height: %s\n", id.myWidth, id.myHeight);
        frameRate(10);

        ul.buildUiElementList();

        Path filePath = id.pathToExamples.resolve(id.initialFileToOpen + ".txt");
        // set the chosen example-Ui to selected:
        ul.getUiElements().stream()
                .filter(ui -> ui.getName().contains(id.initialFileToOpen))
                .forEach(ui -> ui.setSelected(true));
        loadNewExample(filePath);
    }

    public void loadNewExample(Path filePath) {

        // reset the drawSolution-Ui to 'not selected':
        ul.getUiElements().stream()
                .filter(ui -> ui.getName().equals("drawSolution"))
                .forEach(ui -> ui.setSelected(false));

        InputDataHandler data = new InputDataHandler();
        no = data.readAllFileInputs(filePath);
        solutionFile = data.readSolutionFile(filePath, no.getBoxSize());

        if (solutionFile != null) {
            data.checkIfInputMatchesSolution(no, solutionFile);
        }

        drawer = new Drawer(this, no, solutionFile, ul);
        drawer.reDrawUi();
        uiAction = new UiAction(this);

    }

    @Override
    public void draw() {

    }


    @Override
    public void mousePressed() {
        // since this is called multiple times on every click and during the mouse is pressed, store just the first one:
        if (mousePressedPos == null) {
            mousePressedPos = new Position(mouseX, mouseY, no.getBoxSize());
        }
    }

    @Override
    public void mouseReleased() {
        // check if mousePressedX and ..Y are still in the same UI-Element as mouseReleased:

        // check, if mousePressed was on an UI-Element, and which one:
        UiElement ui = ul.inWhichUiElementIsIt(mousePressedPos);

        if (ui != null) {
            UiElement selectedReleased = ul.inWhichUiElementIsIt(new Position(mouseX, mouseY, no.getBoxSize()));

            if (ui.equals(selectedReleased)) {

                System.out.println("UiElement is successfully clicked: " + ui);

                uiAction.actionForUiElement(ui);
            }
        }

        // reset mousePressed-values:
        mousePressedPos = null;
    }


    @Override
    public void keyPressed() {
        uiAction.actionForKeyPressed(key);

/*
        if (keyCode == UP) {
            //
        } else if (keyCode == DOWN) {
            //
        } else if (keyCode == RIGHT) {
            //
        } else if (keyCode == LEFT) {
            //
        }
*/
    }


    //---------//
    // Getters //
    //---------//
    public Nonogram getNo() {
        return no;
    }

    public Nonogram getSolutionFile() {
        return solutionFile;
    }

    public UiElementList getUl() {
        return ul;
    }

    public Solver getSolver() {
        return solver;
    }

    public Drawer getDrawer() {
        return drawer;
    }
}
