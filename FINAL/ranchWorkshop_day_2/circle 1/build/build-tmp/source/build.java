import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HCircleLayout; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {




// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
int  clrBg        = 0xffECECEC;
String pathDATA     = "../../data/";

// ****************************************************************************

// CIRCLE LAYOUT

 int      numItems        = 50;
 int      circleRadius    = 300;
 int      circleStartX    = stageW/2;
 int      circleStartY    = stageH/2;
 float    circleAngleStep = 90.0f/numItems;

PVector[] pos = new PVector[numItems];

HCircleLayout layout;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	layout = new HCircleLayout().radius(circleRadius).startX(circleStartX).startY(circleStartY).angleStep(circleAngleStep);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);


	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(20, 20, 20);
		popMatrix();
	}
}

public void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}

	// println( pos );
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
