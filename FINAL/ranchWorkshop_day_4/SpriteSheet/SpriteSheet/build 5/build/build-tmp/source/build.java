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




int           stageW    = 1920;
int           stageH    = 1080;
int         clrBG     = 0xffCCCCCC;
String        pathDATA  = "../../data/";

// ********************************************************************************************************************

int           numAssets = 250;

// ********************************************************************************************************************

// XYZ screen positions

PVector[]     pickedXYZ = new PVector[numAssets]; // x,y,z positions on screen
PVector[]     data1     = new PVector[numAssets]; // flip left or right, scale

// ********************************************************************************************************************

PImage        ss;
float         ssW       = 980.0f;
float         ssH       = 980.0f;
int           ssCols    = 5;
int           ssRows    = 5;
int           ssMax     = ssCols * ssRows;
float         ssCellW   = ssW/ssCols;
float         ssCellH   = ssH/ssRows;

PVector[]     uvPicked  = new PVector[ssMax];
int           uvIndex   = 0;

int           ssCount   = 0;
float         ssX, ssY; // store/update x,y positions

int           speedMin  = 0;
int           speedMax  = 3;

HOscillator[] posZ      = new HOscillator[numAssets];

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup(){
	H.init(this);

	hint(DISABLE_TEXTURE_MIPMAPS);
	((PGraphicsOpenGL)g).textureSampling(2);

	for (int i = 0; i < ssMax; ++i) {
		PVector _UV = new PVector();
		_UV.x = (ssCount%ssCols) * ssCellW;
		_UV.y = floor(ssCount/ssCols) * ssCellH;
		ssCount = ++ssCount%ssMax;
		uvPicked[i] = _UV;
	}

	ss = loadImage(pathDATA + "spritesheet.png");

	setupART(); // XYZ screen positions
}
 
public void draw(){
	background(clrBG);
	hint(DISABLE_DEPTH_TEST);
	hint(ENABLE_DEPTH_SORT);

	strokeWeight(0);
	noStroke();
	noFill();
	noTint();

	if (++speedMin%speedMax==0) {
		uvIndex = (uvIndex+1)%ssMax; // 0 - 24
	}

	for (int i = 0; i < numAssets; ++i) {
		PVector _uvPicked = uvPicked[  (i+uvIndex)%ssMax  ];
		ssX = (int)_uvPicked.x;
		ssY = (int)_uvPicked.y;


		PVector pt  = pickedXYZ[i];
		PVector _d1 = data1[i];
		HOscillator _HOZ = posZ[i]; _HOZ.nextRaw();

		pushMatrix();
			translate(stageW/2, stageH/2, 0);

			pushMatrix();
				translate(pt.x, pt.y, pt.z + _HOZ.curr() );
				scale(ssCellW * (int)_d1.y); // 196 (980/5)

				pushMatrix();
					scale((int)_d1.x,1); // look left or right

					beginShape(QUADS);
						texture(ss);
						vertex( -(0.5f), -(0.5f), 0,   ssX,                 ssY);
						vertex(  (0.5f), -(0.5f), 0,   ssX+ssCellW,         ssY);
						vertex(  (0.5f),  (0.5f), 0,   ssX+ssCellW, ssY+ssCellH);
						vertex( -(0.5f),  (0.5f), 0,   ssX,         ssY+ssCellH);
					endShape(CLOSE);
				popMatrix();
			popMatrix();
		popMatrix();
	}

	surface.setTitle(
		"FPS = " + PApplet.parseInt(frameRate)
		// + " - mouseFlip = " + mouseFlip
	);
}

// ********************************************************************************************************************

// // XYZ screen positions

public void setupART() {
	for (int i = 0; i < numAssets; ++i) {
		PVector pt = new PVector();
		pt.x = (int)random( -(stageW), (stageW) );
		pt.y = (int)random( -(stageH), (stageH) );
		pt.z = 0;
		pickedXYZ[i] = pt;

		PVector _d1 = new PVector();
		int _flip = (int)random(2);
		switch (_flip) {
			case 0 : _d1.x = -1; break; // look left
			case 1 : _d1.x =  1; break; // look right
		}
		_d1.y = (int)random(3)+1; // scale

		data1[i] = _d1;

		posZ[i] = new HOscillator().range(-2500, 250).speed( 0.5f + ( (int)random(3)*0.5f ) ).freq(1.0f).currentStep( (int)random(333)*i );
	}
}

// ********************************************************************************************************************

public void keyPressed() {
	switch (key) {
		case ' ': setupART(); break;
	}
}

// ********************************************************************************************************************


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
