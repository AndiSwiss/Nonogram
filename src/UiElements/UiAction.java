package UiElements;

import Draw.DrawMain;
import Draw.Zone;
import NonogramStructure.Nonogram;
import Solver.Solver;

public class UiAction {

    public void actionForUiElement(DrawMain drawMain, UiElement ui) {

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
                            drawMain.drawNonogram(solutionFile);
                        } else {
                            System.out.println("Solution file not found!");
                            drawMain.drawText("ERROR: Solution file not found!", Zone.BOTTOM, 20, 8, 1);
                        }
                        break;
                    case "drawAllZoneBoxes":
                        Zone.drawAllZoneBoxesForTesting(drawMain, no);
                        break;
                    case "showMarks":
                        drawMain.drawMarks();
                        break;
                    default:
                        throw new IllegalArgumentException("unknown UiSwitchableOption with name " + ui.getName());
                }
            } else {
                ui.setSelected(false);
                drawMain.reDrawUi();
            }

            // draw the UiElement again to see the effect of the selection:
            drawMain.drawUiElement(ui);
        }

        if (ui instanceof UiClickableOption) {
            switch (ui.getName()) {
                case "makeSmaller":
                    drawMain.changeUiSize(-1);
                    break;
                case "makeLarger":
                    drawMain.changeUiSize(1);
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

            drawMain.reDrawUi();
        }
    }
}
