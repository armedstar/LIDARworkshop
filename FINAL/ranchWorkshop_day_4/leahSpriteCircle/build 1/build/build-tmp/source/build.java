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

int           stageW         = 900;
int           stageH         = 900;
int         clrBg          = 0xffECECEC;
String        pathDATA       = "../../data/";

// ****************************************************************************

// SPRITE SHEET

PImage        ss;
float         ssW            = 980.0f;
float         ssH            = 980.0f;
int           ssCols         = 5;
int           ssRows         = 5;
int           ssMax          = ssCols * ssRows;
float         ssCellW        = ssW/ssCols;
float         ssCellH        = ssH/ssRows;

int           ssCount        = 0;
float         ssX, ssY; // store/update x,y positions

int           speedMin       = 0;
int           speedMax       = 3;

// ********************************************************************************************************************

// CIRCLE LAYOUT

int          numItems        = 12;
int          circleRadius    = 300;
int          circleStartX    = 0;
int          circleStartY    = 0;
float        circleAngleStep = 360.0f/numItems;

PVector[]     pos            = new PVector[numItems];

HCircleLayout layout;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	hint(DISABLE_TEXTURE_MIPMAPS);
	((PGraphicsOpenGL)g).textureSampling(2);

	ss = loadImage(pathDATA + "spritesheet.png");

	layout = new HCircleLayout().radius(circleRadius).startX(circleStartX).startY(circleStartY).angleStep(circleAngleStep);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);

	if (++speedMin%speedMax==0) {
		ssX = (ssCount%ssCols) * ssCellW;
		ssY = floor(ssCount/ssCols) * ssCellH;
		ssCount = ++ssCount%ssMax;
	}

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		for (int i = 0; i < numItems; ++i) {
			pushMatrix();
				translate(pos[i].x, pos[i].y, pos[i].z);

				scale(ssCellW);

				strokeWeight(0);
				noStroke();
				fill(255);

				beginShape(QUADS);
					texture(ss);
					vertex( -(0.5f), -(0.5f), 0,   ssX,                 ssY);
					vertex(  (0.5f), -(0.5f), 0,   ssX+ssCellW,         ssY);
					vertex(  (0.5f),  (0.5f), 0,   ssX+ssCellW, ssY+ssCellH);
					vertex( -(0.5f),  (0.5f), 0,   ssX,         ssY+ssCellH);
				endShape(CLOSE);

			popMatrix();
		}
	popMatrix();

}

public void buildLayout() {
	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
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
