import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HShapeLayout; 
import hype.extended.behavior.HOscillator; 

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
int  clrBg        = 0xff111111;
String pathDATA     = "../../data/";

// ****************************************************************************

// SHAPE LAYOUT

 int      numItems        = 2500;
PImage    myImage;
HImage    hitImage;

PVector[] pos = new PVector[numItems];

HShapeLayout layout;

// ****************************************************************************

HOscillator[] sPos;
int           sPosMin   = 0;
int           sPosMax   = 20;
float         sPosSpeed = 1.0f;
float         sPosFreq  = 5.0f;
float         sPosStep  = 5.0f;

// ****************************************************************************

PImage        clr1;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_002.png");
	myImage = loadImage(pathDATA + "textures/shape.png");
	hitImage = new HImage(myImage);

	layout = new HShapeLayout().target(hitImage);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);

	// image(myImage,0,0);

	lights();

	for (int i = 0; i < numItems; ++i) {
		sPos[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*10)+(i))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			ellipse(0, 0, sPos[i].curr(), sPos[i].curr());
		popMatrix();
	}
}

public void buildLayout() {
	PVector pt = new PVector();
	sPos = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		sPos[i] = new HOscillator().range(sPosMin, sPosMax).speed(sPosSpeed).freq(sPosFreq).currentStep(i*sPosStep).waveform(H.SINE);
	}
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
