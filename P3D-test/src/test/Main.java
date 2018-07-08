package test;

import processing.core.*;
import processing.opengl.*;

public class Main extends PApplet {
  PShader blur;
  
  public void settings() {
    size(600, 400, P2D);
  }
  
  public void setup() {
    blur = loadShader("blur.glsl"); 
    stroke(255, 0, 0);
    rectMode(CENTER);  
  }
 
  public void draw() {
    filter(blur);  
    rect(mouseX, mouseY, 150, 150); 
    ellipse(mouseX, mouseY, 100, 100);
  }
  
  public static void main(final String[] args) {
    PApplet.main("test.Main");
  }    
}
