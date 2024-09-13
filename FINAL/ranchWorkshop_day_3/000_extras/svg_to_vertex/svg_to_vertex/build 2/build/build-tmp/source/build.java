import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int    stageW   = 1000;
int    stageH   = 500;
int  clrBG    = 0xffCCCCCC;
String pathDATA = "../../data/";

// ********************************************************************************************************************

PShape s;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH,P3D);
}

public void setup(){
	background(clrBG);
	s = loadShape(pathDATA+"ai_001.svg");	
}
 
public void draw(){
	background(clrBG);

	s.disableStyle();
	strokeWeight(10);
	stroke(0xffFF3300);
	fill(0xff999999);

	shape(s);
}

// ********************************************************************************************************************
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
