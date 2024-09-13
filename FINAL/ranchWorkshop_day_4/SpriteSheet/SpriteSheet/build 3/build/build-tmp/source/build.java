import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int       stageW    = 1920;
int       stageH    = 1080;
int     clrBG     = 0xffCCCCCC;
String    pathDATA  = "../../data/";

// ********************************************************************************************************************

int       numAssets = 100;

// ********************************************************************************************************************

// XYZ screen positions

PVector[] pickedXYZ = new PVector[numAssets]; // x,y,z positions on screen
PVector[] data1     = new PVector[numAssets]; // flip left or right, scale

// ********************************************************************************************************************

PImage    ss;
float     ssW       = 980.0f;
float     ssH       = 980.0f;
int       ssCols    = 5;
int       ssRows    = 5;
int       ssMax     = ssCols * ssRows;
float     ssCellW   = ssW/ssCols;
float     ssCellH   = ssH/ssRows;

int       ssCount   = 0;
float     ssX, ssY; // store/update x,y positions

int       speedMin  = 0;
int       speedMax  = 2;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup(){
	hint(DISABLE_TEXTURE_MIPMAPS);
	((PGraphicsOpenGL)g).textureSampling(2);

	ss = loadImage(pathDATA + "spritesheet.png");

	setupART(); // XYZ screen positions
}
 
public void draw(){
	background(clrBG);
	hint(DISABLE_DEPTH_TEST);

	// update the sprite sheet positions
	if (++speedMin%speedMax==0) {
		ssX = (ssCount%ssCols) * ssCellW;
		ssY = floor(ssCount/ssCols) * ssCellH;
		ssCount = ++ssCount%ssMax;
	}

	strokeWeight(0);
	noStroke();
	noFill();
	noTint();

	for (int i = 0; i < numAssets; ++i) {
		PVector pt  = pickedXYZ[i];
		PVector _d1 = data1[i];

		pushMatrix();
			translate(stageW/2, stageH/2, 0);

			pushMatrix();
				translate(pt.x, pt.y, pt.z);
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
		pt.x = (int)random( -(stageW/2), (stageW/2) );
		pt.y = (int)random( -(stageH/2), (stageH/2) );
		pt.z = 0;
		pickedXYZ[i] = pt;

		PVector _d1 = new PVector();
		int _flip = (int)random(2);
		switch (_flip) {
			case 0 : _d1.x = -1; break; // look left
			case 1 : _d1.x =  1; break; // look right
		}
		_d1.y = (int)random(3)+1; // scale / 1,2,3 - 196 * var

		data1[i] = _d1;
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
