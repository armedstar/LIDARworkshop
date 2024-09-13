import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HSphereLayout; 
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

// SPHERE LAYOUT

int      numItems        = 500;
int      sphereRadius    = 100;
int      sphereX         = stageW/2;
int      sphereY         = stageH/2;
int      sphereZ         = 0;

PVector[] pos = new PVector[numItems];

HSphereLayout layout;

// ****************************************************************************

HOscillator[] wOsc; // width
float         wOscMin   = 35.0f;
float         wOscMax   = 0.1f;
float         wOscSpeed = 0.7f;
float         wOscFreq  = 5.0f;
float         wOscStep  = 5.0f;

HOscillator[] hOsc; // height
float         hOscMin   = 35.0f;
float         hOscMax   = 0.1f;
float         hOscSpeed = 0.7f;
float         hOscFreq  = 5.0f;
float         hOscStep  = 5.0f;

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

	layout = new HSphereLayout()
		.radius(sphereRadius)
		.loc(sphereX,sphereY,sphereZ)
		.extendRadius(25)
		.useSpiral()
		.phiModifier(3.0001f)
	;

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);

	if (mousePressed) {
		translate(stageW/2, stageH/2);
		rotateX(map(mouseY, 0, stageH, -(TWO_PI/2), TWO_PI/2));
		rotateY(map(mouseX, 0, stageW, -(TWO_PI/2), TWO_PI/2));
		translate(-stageW/2, -stageH/2);
	}

	lights();

	for (int i = 0; i < numItems; ++i) {

		wOsc[i].nextRaw();
		hOsc[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*10)+(i*5))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(wOsc[i].curr(), hOsc[i].curr(), 20);
		popMatrix();
	}
}

public void buildLayout() {
	PVector pt = new PVector();
	wOsc = new HOscillator[numItems];
	hOsc = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		wOsc[i] = new HOscillator()
		.range(wOscMin, wOscMax)
		.speed(wOscSpeed)
		.freq(wOscFreq)
		.currentStep(i*wOscStep)
		.waveform(H.EASE);

		hOsc[i] = new HOscillator()
		.range(hOscMin, hOscMax)
		.speed(hOscSpeed)
		.freq(hOscFreq)
		.currentStep(i*hOscStep)
		.waveform(H.EASE);

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
