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

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		for (int i = 0; i < gridTotal; ++i) {
			pushMatrix();
				translate(grid[i].x, grid[i].y, grid[i].z);
				box(50, 50, 50);
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