import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class serialList extends PApplet {





Serial myPort;

public void setup() {
	
	printArray( Serial.list() );
}

public void draw() {
	
}
  public void settings() { 	size(900, 900, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "serialList" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
