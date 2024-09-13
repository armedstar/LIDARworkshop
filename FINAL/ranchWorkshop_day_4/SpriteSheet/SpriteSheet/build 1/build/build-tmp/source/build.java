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

int    stageW   = 1920;
int    stageH   = 981;
int  clrBG    = 0xff999999;
String pathDATA = "../../data/";

// ********************************************************************************************************************

PImage ss;
float  ssW      = 980.0f;
float  ssH      = 980.0f;
int    ssCols   = 6;
int    ssRows   = 4;
int    ssMax    = ssCols * ssRows;
float  ssCellW  = ssW/ssCols;
float  ssCellH  = ssH/ssRows;

int    ssCount  = 0;
float  ssX, ssY; // store/update x,y positions

int    speedMin = 0;
int    speedMax = 3;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
	// noSmooth(); // does nothing says joshua davis
}

public void setup(){
	// aliasing to anti-aliasing / pixel perfect to blurry
	// 2 (nearest), 3 (linear), 4 (bilinear), and 5 (trilinear):
	hint(DISABLE_TEXTURE_MIPMAPS);
	((PGraphicsOpenGL)g).textureSampling(2);

	ss = loadImage(pathDATA + "tiptoe.png");
}
 
public void draw(){
	background(clrBG);

	// lets visualize the sprite sheet
	image(ss, 0, 0, ssW, ssH);

	// lets visualize the texture selection moving through the spritesheet
	if (++speedMin%speedMax==0) {
		ssX = (ssCount%ssCols) * ssCellW;
		ssY = floor(ssCount/ssCols) * ssCellH;
		ssCount = ++ssCount%ssMax;
	}

	strokeWeight(1);
	stroke(0xffFF3300);
	noFill();
	rect(ssX, ssY, ssCellW, ssCellH);

	// create an object that performs the sprite sheet animation

	strokeWeight(0);
	noStroke();
	noFill();
	noTint();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		pushMatrix();
			translate( (stageW-ssW)/2, 0);
			scale(ssCellW); // 196 (980/5)

			beginShape(QUADS);
				texture(ss);
				vertex( -(0.5f), -(0.5f), 0,   ssX,                 ssY);
				vertex(  (0.5f), -(0.5f), 0,   ssX+ssCellW,         ssY);
				vertex(  (0.5f),  (0.5f), 0,   ssX+ssCellW, ssY+ssCellH);
				vertex( -(0.5f),  (0.5f), 0,   ssX,         ssY+ssCellH);
			endShape(CLOSE);
		popMatrix();

	popMatrix();

	surface.setTitle( PApplet.parseInt(frameRate) + " FPS" );
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
