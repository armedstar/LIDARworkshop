import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HShapeLayout; 

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

// SHAPE LAYOUT

 int      numItems        = 2500;
PImage    myImage;
HImage    hitImage;

PVector[] pos = new PVector[numItems];

HShapeLayout layout;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	myImage = loadImage(pathDATA + "textures/shape.png");
	hitImage = new HImage(myImage);

	layout = new HShapeLayout().target(hitImage);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);

	image(myImage,0,0);

	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			ellipse(0, 0, 10, 10);
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
