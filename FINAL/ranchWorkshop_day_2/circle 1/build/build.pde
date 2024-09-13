import hype.*;
import hype.extended.layout.HCircleLayout;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #ECECEC;
String pathDATA     = "../../data/";

// ****************************************************************************

// CIRCLE LAYOUT

 int      numItems        = 50;
 int      circleRadius    = 300;
 int      circleStartX    = stageW/2;
 int      circleStartY    = stageH/2;
 float    circleAngleStep = 360.0/numItems;

PVector[] pos = new PVector[numItems];

HCircleLayout layout;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	layout = new HCircleLayout().radius(circleRadius).startX(circleStartX).startY(circleStartY).angleStep(circleAngleStep);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);


	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(20, 20, 20);
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