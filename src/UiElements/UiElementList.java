package UiElements;

import Data.Position;
import Draw.Zone;

import java.util.ArrayList;
import java.util.List;

public class UiElementList {

    public static List<UiElement> uiElements;



    public static void buildUiElementList() {
        uiElements = new ArrayList<>();

        // File chooser:
        uiElements.add(new UiTextbox("fileChooserTitle", "Choose the file:"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram1.txt", "Example 1"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram2.txt", "Example 2"));

        // Options:
        uiElements.add(new UiTextbox("emptySpace", ""));
        uiElements.add(new UiTextbox("optionsTitle", "Options:"));
        uiElements.add(new UiSwitchableOption("drawSolution", "Draw the solution"));
    }




    public static void updateAllUiElementPositions() {
        for (int i = 0; i < uiElements.size(); i++) {
            UiElement ui = uiElements.get(i);

            ui.updatePositionValues(new Position(Zone.BOTTOM, 0, i), 15, 1);
        }
    }
}

