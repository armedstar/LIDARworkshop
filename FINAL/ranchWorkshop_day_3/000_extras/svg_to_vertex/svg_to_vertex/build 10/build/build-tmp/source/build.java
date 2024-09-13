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




int           stageW      = 1920;
int           stageH      = 1080;
int         clrBG       = 0xff202020;
String        pathDATA    = "../../data/";

// ********************************************************************************************************************

int numAssets = 5000; // bogus number

PShape        s;
int           sNumShapes;

PImage        tex0, tex1, tex2, tex3;
int           whichTex    = 0;
PVector       uV;
int           texUVmax    = 800;

HOscillator[] texOSC      = new HOscillator[numAssets];

// ********************************************************************************************************************

// COLOR FLOWS

String[]      clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]         clr_Max     = {               500,               500,               500 };
int           clr_Len     = clr_Strings.length;

int[][]     clr_Colors  = new int[clr_Len][];
int           clr_IntLen  = numAssets;
int[][]       clr_Int     = new int[clr_Len][clr_IntLen];

PImage[]      clr_PImage  = new PImage[clr_Len];

int           whichClr    = 0;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH,P3D);
}

public void setup(){
	H.init(this);

	background(clrBG);
	s = loadShape(pathDATA+"ai_004.svg");
	// s = loadShape(pathDATA+"ai_005.svg");
	// s = loadShape(pathDATA+"ai_006.svg");
	sNumShapes = s.getChildCount();

	for (int i=0; i<sNumShapes; i++) {
		texOSC[i] = new HOscillator().range(0.0f, 1.0f).speed(0.5f).freq(5).currentStep( i );
	}

	tex0 = loadImage(pathDATA+"tex0.png");
	tex1 = loadImage(pathDATA+"tex1.png");
	tex2 = loadImage(pathDATA+"tex2.png");
	tex3 = loadImage(pathDATA+"tex3.png");

	setupColorFlow();  // COLOR FLOWS
}
 
public void draw(){
	background(clrBG);
	for (int i=0; i<sNumShapes; i++) {
		buildVertex( s.getChild(i), i );
	}

	noTint();
	updateColorFlow(); // COLOR FLOWS
	surface.setTitle( PApplet.parseInt(frameRate) + " FPS" );
}

// ********************************************************************************************************************

public void buildVertex(PShape _curShape, int _i) {
	strokeWeight(0);
	noStroke();

	HOscillator _osc = texOSC[_i];
	_osc.nextRaw();
	float mapUV = map(_osc.curr(), 0, 1, 50, texUVmax);

	int wc;
	wc  = whichClr;
	int c2 = clr_Colors[wc][clr_Int[wc][_i%clr_Max[wc]]];
	tint(c2, 225);

	beginShape(TRIANGLES);
		switch (whichTex) {
			case 0: texture(tex0); break;
			case 1: texture(tex1); break;
			case 2: texture(tex2); break;
			case 3: texture(tex3); break;
		}

		for (int i=0; i<_curShape.getVertexCount(); i++) {
			PVector v = _curShape.getVertex(i);

			switch (i) {
				case 0: uV = new PVector(0,     0); break;
				case 1: uV = new PVector(mapUV, 0); break;
				case 2: uV = new PVector(0, mapUV); break;
			}

			vertex(v.x, v.y, uV.x, uV.y);
		}
	endShape(CLOSE);
}

// ********************************************************************************************************************

// COLOR FLOWS

public void setupColorFlow() {
	for (int i = 0; i < clr_Len; ++i) {
		clr_PImage[i] = new PImage();
		clr_PImage[i] = loadImage(pathDATA + clr_Strings[i]);

		int[] tmpArray = new int[clr_Max[i]];
		clr_Colors[i] = tmpArray;

		for (int j = 0; j < clr_Max[i]; j++) {
			float tempPos = ((float)clr_PImage[i].width / clr_Max[i]) * j;
			clr_Colors[i][j] = clr_PImage[i].get( Math.round(tempPos), 1 );
		}

		for (int j = 0; j < clr_IntLen; j++) {
			clr_Int[i][j] = j%clr_Max[i];
		}
	}
}

public void updateColorFlow() {
	for (int i = 0; i < clr_Len; ++i) {
		for (int j = 0; j < clr_IntLen; ++j) {
			int _tempNum = clr_Int[i][j];
			_tempNum++;
			if(_tempNum >= clr_Max[i]) _tempNum = 0;
			clr_Int[i][j] = _tempNum;
		}
	}
}

// ********************************************************************************************************************

public void mousePressed() {
	// background(clrBG);
	whichTex = (whichTex+1)%4;
}

// ********************************************************************************************************************

public void keyPressed() {
	switch (key) {
		case '1': whichClr = 0; break;
		case '2': whichClr = 1; break;
		case '3': whichClr = 2; break;
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
