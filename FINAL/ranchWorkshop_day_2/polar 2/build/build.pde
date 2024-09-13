import hype.*;
import hype.extended.layout.HPolarLayout;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #ECECEC;
String pathDATA     = "../../data/";

// ****************************************************************************

// POLAR LAYOUT

int      numItems        = 600;
int      polarStartX     = stageW/2;
int      polarStartY     = stageH/2;
float    polarLength     = 1.0; // default 1.0
float    polarAngleStep  = 0.2; // default 0.1


PVector[] pos = new PVector[numItems];

HPolarLayout layout;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	layout = new HPolarLayout(polarLength, polarAngleStep).offsetX(polarStartX).offsetY(polarStartY);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);


	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(10, 10, 100);
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}

	// println( pos );
}