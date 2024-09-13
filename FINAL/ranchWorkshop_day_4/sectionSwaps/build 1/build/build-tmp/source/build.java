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
int  clrBg    = 0xff111111;
String pathDATA = "../../data/";

// ****************************************************************************

int sectionNum = 2; // where to start

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);
}

public void draw() {
	background(clrBg);

	switch (sectionNum) {
		case 1 : if(s1_firstRun) { s1_setup(); } s1_draw(); break;
		case 2 : if(s2_firstRun) { s2_setup(); } s2_draw(); break;
	}
}

// ****************************************************************************

public void keyPressed() {
	switch (key) {
		case '1' : sectionNum = 1; s1_firstRun = true; break;
		case '2' : sectionNum = 2; s2_firstRun = true; break;
	}
}
boolean s1_firstRun = true;
boolean s1_fireOnce = true;

// ****************************************************************************

int     s1_x        = 100;
int     s1_y        = 100;
int     s1_w        = 400;
int     s1_h        = 400;
int   s1_c        = 0xffFF3300;

HOscillator s1_osxR;

// ****************************************************************************

public void s1_setup() {
	// runs once

	if(s1_fireOnce) s1_osxR = new HOscillator().range(-180,180).speed(1).freq(1);
	s1_fireOnce = false;

	s1_firstRun = false;
}

public void s1_draw() {
	// loop

	s1_osxR.nextRaw();

	pushMatrix();
		translate(s1_x, s1_y, 0);

		rotateY(radians( s1_osxR.curr() ));

		strokeWeight(0);
		noStroke();
		fill(s1_c);
		rect(0, 0, s1_w, s1_h);

	popMatrix();
}
boolean s2_firstRun = true;

int     s2_x        = 300;
int     s2_y        = 300;
int     s2_w        = 200;
int     s2_h        = 200;
int   s2_c        = 0xff00FF00;

public void s2_setup() {
	// runs once

	s2_firstRun = false;
}

public void s2_draw() {
	// loop

	strokeWeight(0);
	noStroke();
	fill(s2_c);
	rect(s2_x, s2_y, s2_w, s2_h);
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
