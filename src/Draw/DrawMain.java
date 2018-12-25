package Draw;

import Data.InitialData;
import Data.InputDataHandler;
import Data.Position;
import NonogramStructure.Nonogram;
import Solver.Solver;
import UiElements.UiAction;
import UiElements.UiElement;
import UiElements.UiElementList;
import processing.core.PApplet;


/**
 * @author Andreas Ambühl
 * @version 0.6o
 */
public class DrawMain extends PApplet {

    private InitialData id;
    private Nonogram no;
    private Nonogram solutionFile;
    private UiElementList ul;
    private Solver solver;
    private Drawer drawer;

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

        String fileName = "Examples/" + id.initialFileToOpen + ".txt";
        // set the chosen example-Ui to selected:
        ul.getUiElements().stream()
                .filter(ui -> ui.getName().contains(id.initialFileToOpen))
                .forEach(ui -> ui.setSelected(true));
        loadNewExample(fileName);
    }

    public void loadNewExample(String fileName) {

        // reset the drawSolution-Ui to 'not selected':
        ul.getUiElements().stream()
                .filter(ui -> ui.getName().equals("drawSolution"))
                .forEach(ui -> ui.setSelected(false));

        InputDataHandler data = new InputDataHandler();
        no = data.readAllFileInputs(fileName);
        solutionFile = data.readSolutionFile(fileName, no.getBoxSize());

        if (solutionFile != null) {
            data.checkIfInputMatchesSolution(no, solutionFile);
        }

        drawer = new Drawer(this, no, solutionFile, ul);
        drawer.reDrawUi();
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

                UiAction uiAction = new UiAction();
                uiAction.actionForUiElement(this, ui);
            }
        }

        // reset mousePressed-values:
        mousePressedPos = null;
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
