import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
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

int    stageW   = 900;
int    stageH   = 900;
int  clrBg    = 0xff000000;
String pathDATA = "../../data/";

// ****************************************************************************

// THIS IS COLORS

PImage clr1, clr2;

int whichColor = 1; // = either color 1 or color 2.... 1 or 2

// ****************************************************************************

// lets randomly put 100 things on screen

int numItems = 50;
int minS = 50;
int maxS = 150;

PVector[] myPos; // x,y,z

// ****************************************************************************

// use sine waves to oscillate between some positions

HOscillator[] zPos;
HOscillator[] xRot;
HOscillator[] yRot;
HOscillator[] zRot;

// ****************************************************************************


public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_001.png");
	clr2 = loadImage(pathDATA + "colors/color_002.png");

	pickPos();
}

public void draw() {
	// background(  clr2.get((frameCount*10)%clr2.width, 2) );

	// image(clr1,0,0,stageW,20); // image,x,y,w,h
	// image(clr2,0,21,stageW,20); // image,x,y,w,h

	noStroke();

	lights();

	for (int i = 0; i < numItems; ++i) {
		PVector pt = myPos[i];

		zPos[i].nextRaw();
		xRot[i].nextRaw();
		yRot[i].nextRaw();
		zRot[i].nextRaw();

		switch (whichColor) {
			case 1: fill( clr1.get(((frameCount*2)+(i*5))%clr1.width, 10) ); break;
			case 2: fill( clr2.get(((frameCount*10)+(i*50))%clr2.width, 10) ); break;
		}

		pushMatrix();
			translate(pt.x, pt.y, pt.z + zPos[i].curr()  );

			rotateX( radians( xRot[i].curr() ) );
			rotateY( radians( yRot[i].curr() ) );
			rotateZ( radians( zRot[i].curr() ) );

			box(25, 25, 10000);  // w,h,d
		popMatrix();

		
	}
}

// function that picks stuff

public void pickPos() {
	myPos = new PVector[numItems];

	zPos = new HOscillator[numItems];
	xRot = new HOscillator[numItems];
	yRot = new HOscillator[numItems];
	zRot = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		PVector pt = new PVector();
		pt.x = (int)random(stageW);
		pt.y = (int)random(stageH);
		pt.z = (int)random(-900,0);
		myPos[i] = pt;

		zPos[i] = new HOscillator().range(-3000,300).speed(1.0f).freq(1.0f).currentStep(i*5).waveform(H.SINE);
		xRot[i] = new HOscillator().range(-180,180).speed(0.2f).freq(1.0f).currentStep(i*3).waveform(H.SINE);
		yRot[i] = new HOscillator().range(-127,127).speed(0.2f).freq(1.0f).currentStep(i*3).waveform(H.SINE);
		zRot[i] = new HOscillator().range(-90,90).speed(0.2f).freq(1.0f).currentStep(i*3).waveform(H.SINE);
	}

	// println( myPos );
}

public void keyPressed() {
	switch (key) {
		case ' ': pickPos(); break;
		case '1': whichColor=1; break;
		case '2': whichColor=2; break;
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
