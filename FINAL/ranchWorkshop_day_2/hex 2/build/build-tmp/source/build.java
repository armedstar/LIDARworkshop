import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HHexLayout; 
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

// HEX LAYOUT

 int      numItems        = 169;
 int      hexSpacing      = 25;
 int      hexStartX       = 0;
 int      hexStartY       = 0;

PVector[] pos = new PVector[numItems];

HHexLayout layout;

// ****************************************************************************

HOscillator[] zPos;
int           zPosMin   = -900;
int           zPosMax   =  100;
float         zPosSpeed = 1.0f;
float         zPosFreq  = 1.0f;
float         zPosStep  = 2.0f;

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

	layout = new HHexLayout().spacing(hexSpacing).offsetX(hexStartX).offsetY(hexStartY);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);

	lights();

	for (int i = 0; i < numItems; ++i) {
		zPos[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*2)+(i*50))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z + zPos[i].curr() );
			box(200, 200, 10);
		popMatrix();
	}
}

public void buildLayout() {
	PVector pt = new PVector();
	zPos = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		zPos[i] = new HOscillator().range(zPosMin, zPosMax).speed(zPosSpeed).freq(zPosFreq).currentStep(i*zPosStep).waveform(H.SINE);
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
