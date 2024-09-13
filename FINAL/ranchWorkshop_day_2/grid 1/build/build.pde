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
int    gridStartX   = 50; // where to start the grid, x and y
int    gridStartY   = 50;
int    gridSpacingX = 100;
int    gridSpacingY = 100;

PVector[] grid = new PVector[gridTotal]; // an array of 25 x,y,z positions

HGridLayout layout;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	layout = new HGridLayout().startLoc(gridStartX,gridStartY).spacing(gridSpacingX,gridSpacingY).cols(gridCols);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);


	lights();

	for (int i = 0; i < gridTotal; ++i) {
		pushMatrix();
			translate(grid[i].x, grid[i].y, grid[i].z);
			box(50, 50, 50);
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < gridTotal; ++i) {
		grid[i] = layout.getNextPoint();
	}

	// println( grid );
}