package Snake_Game;

import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Nachdem man die Processing-Library z.B. in den Ordner 'src/Libraries/Processing' kopiert hat, muss man die Bibliothek
 * noch in den Projekt-Einstellungen importieren: <br>
 * - File -> Project Structure... -> Libraries -> '+' -> Java    <br>
 * - Pfad angegeben, fertig
 * <br>
 * <p><p>
 * ACHTUNG !!!!!! Das Processing, welches man jetzt (Oktober 2018) herunterladen kann LÄUFT NUR MIT JAVA 8 !!!!
 * <p>
 * ABER: das von der E.Tutorial-Homepage heruntergeladene File 'core.jar' ist zwar älter, aber es funktioniert
 * bestens mit Java 9 oder 10.
 * <p>
 * <p>
 * Zudem muss im Vergleich zu Eclipse eine Main-Methode enthalten sein, die so aussieht:
 * public static void main(String[] args) {
 * PApplet.main("W04_Methoden.ETutorial.Pandemie");
 * }
 * <p><p>
 * Damit ein Projekt, welches in Processing nativ läuft, in einer IDE zum laufen gebracht werden kann (egal ob Eclipse oder IntelliJ),
 * muss das Objekt "this" an alle Objekte im Konstruktor übergeben werden, also:
 * <br><br>
 * bisherig: figures[i] = new Circle((int) random(laenge), (int) random(breite), (int) random(6, 100)); <br>
 * neu: figures[i] = new Circle(this, (int) random(laenge), (int) random(breite), (int) random(6, 100));
 * <br><br>
 * Konstruktoren in allen Klassen anpassen, d.h. in Figure zusätzliches Field: <br>
 * protected PApplet p; <br>
 * public Figure(PApplet p, int x, int y) {..} <br>
 * <br><br>
 * Und in Circle und Rectangle neu:
 * public Circle(PApplet p, int x, int y, int r) {
 * super(p, x, y);
 * diameter = r;
 * }
 *
 * <br><br>
 * Zudem muss in allen Methoden der Aufruf der PApplet-Methoden wie folgt angepasst werden: <br>
 * bisherig: fill(..); <br>
 * neu:      p.fill(..);
 */
public class Snake_Abgabe extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Snake_Game.Snake_Abgabe");
    }

    /**
     * mit WALLS_ARE_PASSABLE kann eingestellt werden, ob die Ränder vom Spielfeld passierbar sind oder nicht.
     */
    static final boolean WALLS_ARE_PASSABLE = true;
    static final int GROESSE = 30;
    static final int WIDTH = 600;
    static final int HEIGHT = 600;


    ArrayList<Element> snake = new ArrayList<>(); //<>//
    ArrayList<Stopper> stopper = new ArrayList<>();
    int dirX = 1;
    int dirY = 0;
    Element rot = null;

    @Override
    public void setup() {
        size(WIDTH, HEIGHT);
        snake.add(0, new Element(this, GROESSE, GROESSE, GROESSE));
        fill(255);
        frameRate(100 / GROESSE);
        rot = new RedElement(this, GROESSE);
    }

    @Override
    public void draw() {
        bewegen();  // Die Schlange soll sich um ein Element bewegen
        fressen();  // Es wird überprüft, ob das rote Element gefressen wird
        zeichne();  // Die Schlange und das rote Element werden neu gezeichnet
        hindernisse();  // Hindernisse werden hinzugefügt
        stopp();    // Es wird berechnet, ob das Spiel zu Ende ist
    }

    void bewegen() {
        Element kopf = snake.remove(0);
        if (snake.size() > 0) {
            kopf.setX(snake.get(snake.size() - 1).x + dirX * GROESSE);
            kopf.setY(snake.get(snake.size() - 1).y + dirY * GROESSE);
        } else {
            kopf.setX(kopf.x + dirX * GROESSE);
            kopf.setY(kopf.y + dirY * GROESSE);
        }
        snake.add(kopf);
    }

    void zeichne() {
        background(0);
        // Für alle Elemente in snake und rot soll die Methode paint aufgerufen werden
        for (Element e : snake) {
            e.paint();
        }
        if (rot != null) rot.paint();

        // Stopper zeichnen:
        for (Stopper s : stopper) {
            s.paint();
        }
    }

    void fressen() {
        if (snake.contains(rot)) {
            Element kopf = snake.get(snake.size() - 1);
            snake.add(new Element(this, kopf.x + dirX * GROESSE, kopf.y + dirY * GROESSE,
                    GROESSE));
            rot = null;
        }
        if (rot == null) {
            rot = new RedElement(this, GROESSE);
        }
    }

    void hindernisse() {
        /* Je länger das Programm läuft sollen Elemente in den Weg gestellt werden. */
        if (snake.size() == 2 && stopper.size() == 0) {
            stopperHinzufuegen();
        } else if (snake.size() == stopper.size() * 2 + 1) {
            stopperHinzufuegen();
        }
    }

    void stopperHinzufuegen() {
        int x;
        int y;

        boolean nochmals;

        do {
            nochmals = false;

            x = (int) this.random(0, this.width) / GROESSE * GROESSE;
            y = (int) this.random(0, this.height) / GROESSE * GROESSE;

            // vermeide Positionen der Schlange:
            for (Element e : snake) {
                if (e.x == x && e.y == y) {
                    System.out.println("Es wurde versucht, das Element auf die Schlange zu zeichnen!");
                    nochmals = true;
                }
            }

            // vermeide nähe zum Kopf der Schlange in Richtung wohin man sich bewegt:
            Element kopf = snake.get(snake.size() - 1);
            if (Math.abs(kopf.x - x) < 7 * GROESSE && Math.abs(kopf.y - y) < 7 * GROESSE) {
                System.out.println("Es wurde versucht, das Element in der Nähe vom Kopf der Schlange zu zeichnen, " +
                        "nämlich auf die Position x/y " + x + "/" + y + ".");
                nochmals = true;
            }


        } while (nochmals);

        stopper.add(new Stopper(this, x, y, GROESSE));
    }

    void stopp() {
        // Falls die Schlange in sich selber reinfährt, soll gestoppt werden.
        Element kopf = snake.get(snake.size() - 1);
        for (int i = 0; i < snake.size() - 1; i++) {
            if (kopf.x == snake.get(i).x && kopf.y == snake.get(i).y) {
                System.out.println("Game over: you tried to eat yourself!");
                frameRate(0);
            }
        }

        // Falls die Schlange in einen Stopper reinfährt, soll gestoppt werden.
        for (Stopper s : stopper) {
            if (kopf.x == s.x && kopf.y == s.y) {
                System.out.println("Game over: you hit a stopper-object!");
                frameRate(0);
            }
        }
    }

    @Override
    public void keyPressed() {
        if (keyCode == UP && dirY != 1) {
            dirX = 0;
            dirY = -1;
        } else if (keyCode == DOWN && dirY != -1) {
            dirX = 0;
            dirY = 1;
        } else if (keyCode == RIGHT && dirX != -1) {
            dirY = 0;
            dirX = 1;
        } else if (keyCode == LEFT && dirX != 1) {
            dirY = 0;
            dirX = -1;
        }
    }
}
