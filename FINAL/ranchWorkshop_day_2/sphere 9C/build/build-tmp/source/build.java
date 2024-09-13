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

int      numItems        = 300;
int      sphereRadius    = 300;
int      sphereX         = stageW/2;
int      sphereY         = stageH/2;
int      sphereZ         = 0;

PVector[] pos = new PVector[numItems];

HSphereLayout layout;

// ****************************************************************************

HOscillator[] d;
int           dMin   = 10;
int           dMax   = 40;
float         dSpeed = 1.0f;
float         dFreq  = 5.0f;
float         dStep  = 3.0f;

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

	layout = new HSphereLayout().radius(sphereRadius).loc(sphereX,sphereY,sphereZ);
	layout.useSpiral().numPoints(numItems).phiModifier(1.2345f);

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
		d[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*10)+(i*5))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);

			float[] numbers = layout.getRotations(pos[i]);
			rotateX(numbers[0]); // x rotation
			rotate(numbers[1], numbers[2], numbers[3], numbers[4]); // y, xyz

			sphereDetail(20);
			sphere( d[i].curr() );

			// box(10, 10, d[i].curr() );
		popMatrix();
	}
}

public void buildLayout() {
	PVector pt = new PVector();
	d = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		d[i] = new HOscillator().range(dMin, dMax).speed(dSpeed).freq(dFreq).currentStep(i*dStep).waveform(H.SINE);
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
