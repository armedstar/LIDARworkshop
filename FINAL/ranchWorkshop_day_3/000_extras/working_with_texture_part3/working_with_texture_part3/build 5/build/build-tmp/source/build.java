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

int       stageW      = 1920;
int       stageH      = 1080;

int     clrBG       = 0xffCCCCCC;

String    pathDATA    = "../../data/";

// ********************************************************************************************************************

int       numAssets   = 100;

// ********************************************************************************************************************

// TEXTURES

String[]  texNames    = { "photo.png" };
int       texNamesLen = texNames.length;
PImage[]  texLoaded   = new PImage[texNamesLen];

int       texW        = 1000;
int       texH        = 1000;

//                       0    1    2
int[]     texUV       = {200, 400, 600};
int       texUVLen    = texUV.length;
int[]     whichUV     = new int[numAssets];

PVector[] pickedUVXY  = new PVector[numAssets];

boolean   showUVMaps  = false;

// ********************************************************************************************************************

// COLOR FLOWS

String[]  clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]     clr_Max     = {               100,               100,               100 };
int       clr_Len     = clr_Strings.length;

int[][] clr_Colors  = new int[clr_Len][];
int       clr_IntLen  = numAssets;
int[][]   clr_Int     = new int[clr_Len][clr_IntLen];

PImage[]  clr_PImage  = new PImage[clr_Len];

int       whichClr    = 1;

// ********************************************************************************************************************

// XYZ screen positions

PVector[] pickedXYZ   = new PVector[numAssets];

// ********************************************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup(){
	setupTEX(); // TEXTURES
	setupColorFlow();  // COLOR FLOWS
	setupUVXY(); // XY COORDS FOR UV MAPPING
	setupXYZ(); // XYZ screen positions
}
 
public void draw(){
	background(clrBG);

	strokeWeight(0);
	noStroke();
	noFill();

	for (int i = 0; i < numAssets; ++i) {
		PVector pt = pickedXYZ[i];

		pushMatrix();
			translate(stageW/2, stageH/2, 0);

			pushMatrix();
				translate(pt.x, pt.y, pt.z);
				scale( texUV[whichUV[i]]/2 );

				strokeWeight(0);
				noStroke();
				noFill();

				int c = clr_Colors[whichClr][clr_Int[whichClr][i%clr_Max[whichClr]]];
				tint(c, 225);

				beginShape(QUADS);
					texture( texLoaded[0] );
					vertex( -(0.5f), -(0.5f), 0,   pickedUVXY[i].x,                     pickedUVXY[i].y );
					vertex(  (0.5f), -(0.5f), 0,   pickedUVXY[i].x + texUV[whichUV[i]], pickedUVXY[i].y );
					vertex(  (0.5f),  (0.5f), 0,   pickedUVXY[i].x + texUV[whichUV[i]], pickedUVXY[i].y + texUV[whichUV[i]] );
					vertex( -(0.5f),  (0.5f), 0,   pickedUVXY[i].x,                     pickedUVXY[i].y + texUV[whichUV[i]] );
				endShape(CLOSE);

			popMatrix();

		popMatrix();
	}

	if(showUVMaps) {
		noTint();
		image(texLoaded[0], 0, 0, texW, texH);

		strokeWeight(1);
		noFill();

		for (int i = 0; i < numAssets; ++i) {
			int _UV = whichUV[i];

			switch (_UV) {
				case 0 : stroke(0xffFF0000); break;
				case 1 : stroke(0xff00FF00); break;
				case 2 : stroke(0xff0000FF); break;
			}

			rect( pickedUVXY[i].x, pickedUVXY[i].y, texUV[whichUV[i]], texUV[whichUV[i]] );
		}

	}


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

// // XYZ screen positions

public void setupXYZ() {
	for (int i = 0; i < numAssets; ++i) {
		PVector pt = new PVector();
		pt.x = (int)random( -(stageW/2), (stageW/2) );
		pt.y = (int)random( -(stageH/2), (stageH/2) );
		pt.z = 0;
		pickedXYZ[i] = pt;
	}
}

// ********************************************************************************************************************

// SETUP TEXTURE XY COORDS FOR UV MAPPING

public void setupUVXY() {
	for (int i = 0; i < numAssets; ++i) {
		int ranNum = (int)random(texUVLen);

		PVector _pt = new PVector();
		_pt.x = (int)random((texW-texUV[ranNum]));
		_pt.y = (int)random((texH-texUV[ranNum]));
		pickedUVXY[i] = _pt;

		whichUV[i] = ranNum;
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
		case ' ': setupUVXY();  break;
		case '1': whichClr = 0; break;
		case '2': whichClr = 1; break;
		case '3': whichClr = 2; break;
		case 'm': showUVMaps = !showUVMaps; break;
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
