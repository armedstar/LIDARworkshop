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

int       clrBG       = 0xffFF3300;

String      pathDATA    = "../../data/";

// ********************************************************************************************************************

// TEXTURES

String[]    texNames    = { "texture5.png" };
int         texNamesLen = texNames.length;
PImage[]    texLoaded   = new PImage[texNamesLen];

int         texW        = 1000;
int         texH        = 1000;
int         texUVmax    = 1000;

//                         0    1    2    3
int[]       texUV       = {300, 300, 300, 300};
int         texUVLen    = texUV.length;
PVector[]   pickedUVXY  = new PVector[texUVLen];
int         whichUV     = 0;


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
	setupUVXY(); // XY COORDS FOR UV MAPPING
	setupColorFlow();  // COLOR FLOWS

	texOSC = new HOscillator().range(0.0f, 1.0f).speed(1).freq(1).waveform(H.EASE);
}
 
public void draw(){
	background(clrBG);

	texOSC.nextRaw();
	float map1UV = map(texOSC.curr(), 0, 1, texUV[0], texUVmax);
	float map2UV = map(texOSC.curr(), 0, 1, texUV[1], texUVmax);
	float map3UV = map(texOSC.curr(), 0, 1, texUV[2], texUVmax);
	float map4UV = map(texOSC.curr(), 0, 1, texUV[3], texUVmax);

	// color c = clr_Colors[whichClr][clr_Int[whichClr][0]];
	// tint(c);

	// VISUALIZE THE TEXTURE SOURCE

	// pushMatrix();
	// 	translate(40, 40, 0);

	// 	image(texLoaded[0], 0, 0, texW, texH);

	// 	strokeWeight(2);
	// 	stroke(#FF0000);
	// 	noFill();

	// 	for (int i = 0; i < texUVLen; ++i) {
	// 		rect( pickedUVXY[i].x, pickedUVXY[i].y, texUV[i], texUV[i] );
	// 	}
	// popMatrix();

	strokeWeight(2);
	stroke(0xffFF0000);

	pushMatrix();
		translate(40, 40, 0);
		image(texLoaded[0], 0, 0, texW, texH);

		for (int i = 0; i < texUVLen; ++i) {
			pushMatrix();
				translate(pickedUVXY[i].x, pickedUVXY[i].y, 0);

				switch (i) {
					case 0 : rect( -( map1UV/2 ), -( map1UV/2 ), map1UV, map1UV ); break; // UV array subitem 0
					case 1 : rect( -( map2UV/2 ), -( map2UV/2 ), map2UV, map2UV ); break; // UV array subitem 1
					case 2 : rect( -( map3UV/2 ), -( map3UV/2 ), map3UV, map3UV ); break; // UV array subitem 2
					case 3 : rect( -( map4UV/2 ), -( map4UV/2 ), map4UV, map4UV ); break; // UV array subitem 3
				}
			popMatrix();
		}
	popMatrix();



// pickedUVXY[whichUV].x

	strokeWeight(0);
	noStroke();
	noFill();



	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 001

	pushMatrix();
		translate(1270, 230, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5f), -(0.5f), 0,   (pickedUVXY[0].x)-(map1UV/2), (pickedUVXY[0].y)-(map1UV/2) );
			vertex(  (0.5f), -(0.5f), 0,   (pickedUVXY[0].x)+(map1UV/2), (pickedUVXY[0].y)-(map1UV/2) );
			vertex(  (0.5f),  (0.5f), 0,   (pickedUVXY[0].x)+(map1UV/2), (pickedUVXY[0].y)+(map1UV/2) );
			vertex( -(0.5f),  (0.5f), 0,   (pickedUVXY[0].x)-(map1UV/2), (pickedUVXY[0].y)+(map1UV/2) );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 002

	pushMatrix();
		translate(1690, 230, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5f), -(0.5f), 0,   (pickedUVXY[1].x)-(map2UV/2), (pickedUVXY[1].y)-(map2UV/2) );
			vertex(  (0.5f), -(0.5f), 0,   (pickedUVXY[1].x)+(map2UV/2), (pickedUVXY[1].y)-(map2UV/2) );
			vertex(  (0.5f),  (0.5f), 0,   (pickedUVXY[1].x)+(map2UV/2), (pickedUVXY[1].y)+(map2UV/2) );
			vertex( -(0.5f),  (0.5f), 0,   (pickedUVXY[1].x)-(map2UV/2), (pickedUVXY[1].y)+(map2UV/2) );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 003

	pushMatrix();
		translate(1270, 650, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5f), -(0.5f), 0,   (pickedUVXY[2].x)-(map3UV/2), (pickedUVXY[2].y)-(map3UV/2) );
			vertex(  (0.5f), -(0.5f), 0,   (pickedUVXY[2].x)+(map3UV/2), (pickedUVXY[2].y)-(map3UV/2) );
			vertex(  (0.5f),  (0.5f), 0,   (pickedUVXY[2].x)+(map3UV/2), (pickedUVXY[2].y)+(map3UV/2) );
			vertex( -(0.5f),  (0.5f), 0,   (pickedUVXY[2].x)-(map3UV/2), (pickedUVXY[2].y)+(map3UV/2) );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 004

	pushMatrix();
		translate(1690, 650, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5f), -(0.5f), 0,   (pickedUVXY[3].x)-(map4UV/2), (pickedUVXY[3].y)-(map4UV/2) );
			vertex(  (0.5f), -(0.5f), 0,   (pickedUVXY[3].x)+(map4UV/2), (pickedUVXY[3].y)-(map4UV/2) );
			vertex(  (0.5f),  (0.5f), 0,   (pickedUVXY[3].x)+(map4UV/2), (pickedUVXY[3].y)+(map4UV/2) );
			vertex( -(0.5f),  (0.5f), 0,   (pickedUVXY[3].x)-(map4UV/2), (pickedUVXY[3].y)+(map4UV/2) );
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

// SETUP TEXTURE XY COORDS FOR UV MAPPING

public void setupUVXY() {
	for (int i = 0; i < texUVLen; ++i) {
		PVector _pt = new PVector();
		_pt.x = (int)random( -(100), texW-100 );
		_pt.y = (int)random( -(100), texH-100 );
		pickedUVXY[i] = _pt;
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
		case ' ': setupUVXY();  break;
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
