package Snake_Game;

import processing.core.PApplet;

public class Element {
    int x, y;
    int groesse;
    PApplet p;

    Element(PApplet p, int x, int y, int g) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.groesse = g;
    }

    void paint() {
        p.rect(x, y, groesse, groesse);
    }

    void setX(int x) {
        if (Snake_Abgabe.WALLS_ARE_PASSABLE) {
            this.x = x % p.width;
            if (this.x < 0) {
                this.x = p.width - Snake_Abgabe.GROESSE;
            }
        } else {
            this.x = x;
            // stop the game, if you reach the border:
            if (this.x < 0 || this.x >= p.width) {
                p.frameRate(0);
            }
        }
    }

    void setY(int y) {
        if (Snake_Abgabe.WALLS_ARE_PASSABLE) {

            this.y = y % p.height;
            if (this.y < 0) {
                this.y = p.height - Snake_Abgabe.GROESSE;
            }
        } else {
            this.y = y;
            // stop the game, if you reach the border:
            if (this.y < 0 || this.y >= p.height) {
                p.frameRate(0);
            }
        }
    }
    // Überschreiben Sie hier die equals Methode von Object
    // Zwei Elemente sind gleich, wenn die Werte von x und y gleich sind.

    /**
     * @param o Object -> hier MUSS ein Object akzeptiert werden -> es reicht nicht, wenn man nur Element akzeptieren
     *          würde, denn wir benutzen ja die Methode contains(...) -> die arbeitet mit Objects!
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Element) {
            Element element = (Element) o;
            return x == element.x &&
                    y == element.y;
        }
        return false;
    }

}