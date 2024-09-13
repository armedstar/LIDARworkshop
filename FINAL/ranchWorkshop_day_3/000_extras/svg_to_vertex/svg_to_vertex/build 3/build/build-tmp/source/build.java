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
int    sNumShapes;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH,P3D);
}

public void setup(){
	background(clrBG);
	s = loadShape(pathDATA+"ai_001.svg");
	sNumShapes = s.getChildCount();
}
 
public void draw(){
	background(clrBG);
	
	for (int i=0; i<sNumShapes; i++) {
		buildVertex( s.getChild(i), i );
	}
}

// ********************************************************************************************************************

public void buildVertex(PShape _curShape, int _i) {
	strokeWeight(1);
	stroke(0xffFF3300);
	fill(0xff999999);

	beginShape(TRIANGLES);
		for (int i=0; i<_curShape.getVertexCount(); i++) {
			PVector v = _curShape.getVertex(i);
			vertex(v.x, v.y);
		}
	endShape(CLOSE);
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
