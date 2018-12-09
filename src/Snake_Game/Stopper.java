package Snake_Game;

import processing.core.PApplet;

class Stopper extends Element {
    Stopper(PApplet p, int x, int y, int groesse) {
        super(p, x, y, groesse);
    }

    @Override
    void paint() {
        p.fill(40, 40, 255);
        super.paint();
        p.fill(255);
    }
}
