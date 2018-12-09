package Snake_Game;

import processing.core.PApplet;

public class RedElement extends Element{
  public RedElement(PApplet p, int groesse){
    super(p,
            (int)p.random(0,p.width)/groesse*groesse,
            (int)p.random(0,p.height)/groesse*groesse,
            groesse);
  }

  @Override
  void paint(){
    p.fill(255,40,40);
    super.paint();
    p.fill(255);
  }
}