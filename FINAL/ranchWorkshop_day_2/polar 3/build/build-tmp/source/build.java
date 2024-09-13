import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HPolarLayout; 
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

// POLAR LAYOUT

int      numItems        = 600;
int      polarStartX     = 0;
int      polarStartY     = 0;
float    polarLength     = 1.0f; // default 1.0
float    polarAngleStep  = 0.2f; // default 0.1


PVector[] pos = new PVector[numItems];

HPolarLayout layout;

// ****************************************************************************

HOscillator[] zPos;
int           zPosMin   = -500;
int           zPosMax   =  500;
float         zPosSpeed = 1.0f;
float         zPosFreq  = 1.0f;
float         zPosStep  = 0.5f;

HOscillator   oscR;

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
	oscR = new HOscillator().range(-180,180).speed(0.1f).freq(1);

	layout = new HPolarLayout(polarLength, polarAngleStep).offsetX(polarStartX).offsetY(polarStartY);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	// background(clrBg);

	oscR.nextRaw();

	lights();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);
		rotate(radians(oscR.curr()));

		for (int i = 0; i < numItems; ++i) {
			zPos[i].nextRaw();

			strokeWeight(0);
			noStroke();
			fill(  clr1.get( ((frameCount*2)+(i*5))%clr1.width, 10 ) );

			pushMatrix();
				translate(pos[i].x, pos[i].y, pos[i].z + zPos[i].curr() );
				box(10, 10, 100);
			popMatrix();
		}
	popMatrix();
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
