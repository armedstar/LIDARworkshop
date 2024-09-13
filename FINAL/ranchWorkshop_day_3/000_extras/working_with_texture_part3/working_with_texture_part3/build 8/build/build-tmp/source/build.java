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




int         stageW      = 1920;
int         stageH      = 1080;

int       clrBG       = 0xffCCCCCC;

String      pathDATA    = "../../data/";

// ********************************************************************************************************************

// TEXTURES

String[]    texNames    = { "texture1.png" };
int         texNamesLen = texNames.length;
PImage[]    texLoaded   = new PImage[texNamesLen];

int         texW        = 1000;
int         texH        = 1000;

//                         0    1    2    3
int[]       texUV       = {200, 400, 600, 800};
int         whichUV     = 0;
int         texUVmax    = 1000;

HOscillator texOSC;

// ********************************************************************************************************************

// COLOR FLOWS

String[]    clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]       clr_Max     = {               300,               500,               700 };
int         clr_Len     = clr_Strings.length;

int[][]   clr_Colors  = new int[clr_Len][];
int         clr_IntLen  = 1;
int[][]     clr_Int     = new int[clr_Len][clr_IntLen];

PImage[]    clr_PImage  = new PImage[clr_Len];

int         whichClr    = 0;

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup(){
	H.init(this);
	setupTEX(); // TEXTURES
	setupColorFlow();  // COLOR FLOWS

	texOSC = new HOscillator().range(0.0f, 1.0f).speed(1).freq(1);
}
 
public void draw(){
	background(clrBG);

	texOSC.nextRaw();
	float map1UV = map(texOSC.curr(), 0, 1, texUV[0], texUVmax);

	int c = clr_Colors[whichClr][clr_Int[whichClr][0]];
	tint(c);

	// VISUALIZE THE TEXTURE SOURCE

	pushMatrix();
		translate(540, stageH/2, 0);

		image(texLoaded[0], -(texW/2), -(texH/2), texW, texH);

		strokeWeight(2);
		stroke(0xff000000);
		noFill();
		line( -(texW/2), 0, (texW/2), 0 );
		line( 0, -(texH/2), 0, (texH/2) );

		stroke(0xffFF0000);

		rect( -( map1UV/2 ), -( map1UV/2 ), map1UV, map1UV ); // UV array subitem 0
	popMatrix();



	strokeWeight(0);
	noStroke();
	noFill();



	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 001

	pushMatrix();
		translate(1270, 230, 0);
		scale(380);
		whichUV = 0;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5f), -(0.5f), 0,   (texW/2)-(map1UV/2), (texH/2)-(map1UV/2) );
			vertex(  (0.5f), -(0.5f), 0,   (texW/2)+(map1UV/2), (texH/2)-(map1UV/2) );
			vertex(  (0.5f),  (0.5f), 0,   (texW/2)+(map1UV/2), (texH/2)+(map1UV/2) );
			vertex( -(0.5f),  (0.5f), 0,   (texW/2)-(map1UV/2), (texH/2)+(map1UV/2) );
		endShape(CLOSE);
	popMatrix();

	noTint();
	updateColorFlow(); // COLOR FLOWS
	// surface.setTitle( int(frameRate) + " FPS" );
}

// ********************************************************************************************************************

// TEXTURES

public void setupTEX() {
	for (int i = 0; i < texNamesLen; ++i) {
		PImage _temp = loadImage(pathDATA + texNames[i]);
		texLoaded[i] = _temp;
	}
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