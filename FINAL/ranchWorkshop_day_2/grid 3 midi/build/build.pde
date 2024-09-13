import hype.*;
import hype.extended.layout.HGridLayout;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #ECECEC;
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

import themidibus.*;
MidiBus myBus;

int boxW = 5;
int boxH = 5;
int boxD = 5;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	layout = new HGridLayout().startLoc(gridStartX,gridStartY).spacing(gridSpacingX,gridSpacingY).cols(gridCols);

	buildLayout(); // lets fire the layout function

	// MidiBus.list();
	myBus = new MidiBus(this, 0, 3); // input, output
}

void draw() {
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

void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < gridTotal; ++i) {
		grid[i] = layout.getNextPoint();
	}

	// println( grid );
}




void controllerChange(int channel, int number, int value) {

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