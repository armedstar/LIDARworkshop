import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HGridLayout; 
import themidibus.*; 

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

// GRID LAYOUT
int    gridRows     = 5;
int    gridCols     = 5;
int    gridTotal    = gridRows * gridCols;
int    gridSpacingX = 100;
int    gridSpacingY = 100;
int    gridStartX   = -((gridCols-1)*(gridSpacingX/2));
int    gridStartY   = -((gridRows-1)*(gridSpacingY/2));

PVector[] grid = new PVector[gridTotal]; // an array of 25 x,y,z positions

HGridLayout layout;

// ****************************************************************************


MidiBus myBus;

int boxW = 5;
int boxH = 5;
int boxD = 5;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	layout = new HGridLayout().startLoc(gridStartX,gridStartY).spacing(gridSpacingX,gridSpacingY).cols(gridCols);

	buildLayout(); // lets fire the layout function

	// MidiBus.list();
	myBus = new MidiBus(this, 0, 3); // input, output
}

public void draw() {
	background(clrBg);


	lights();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		for (int i = 0; i < gridTotal; ++i) {
			pushMatrix();
				translate(grid[i].x, grid[i].y, grid[i].z);
				box(boxW, boxH, boxD);
			popMatrix();
		}

	popMatrix();

}

public void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < gridTotal; ++i) {
		grid[i] = layout.getNextPoint();
	}

	// println( grid );
}




public void controllerChange(int channel, int number, int value) {

	switch (number) {
		case 21: // box width
			boxW = (int)map(value, 0, 127, 5, 50);
			break;

		case 22: // box height
			boxH = (int)map(value, 0, 127, 5, 50);
			break;

		case 23: // box depth
			boxD = (int)map(value, 0, 127, 5, 3000);
			break;
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
