import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HPolarLayout; 

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

// POLAR LAYOUT

int      numItems        = 600;
int      polarStartX     = stageW/2;
int      polarStartY     = stageH/2;
float    polarLength     = 1.0f; // default 1.0
float    polarAngleStep  = 0.2f; // default 0.1


PVector[] pos = new PVector[numItems];

HPolarLayout layout;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	layout = new HPolarLayout(polarLength, polarAngleStep).offsetX(polarStartX).offsetY(polarStartY);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);


	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(10, 10, 100);
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
