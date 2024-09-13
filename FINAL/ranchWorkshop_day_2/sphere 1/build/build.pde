import hype.*;
import hype.extended.layout.HSphereLayout;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #ECECEC;
String pathDATA     = "../../data/";

// ****************************************************************************

// SPHERE LAYOUT

int      numItems        = 100;
int      sphereRadius    = 200;
int      sphereX         = stageW/2;
int      sphereY         = stageH/2;
int      sphereZ         = 0;

PVector[] pos = new PVector[numItems];

HSphereLayout layout;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	layout = new HSphereLayout().radius(sphereRadius).loc(sphereX,sphereY,sphereZ);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);

	if (mousePressed) {
		translate(stageW/2, stageH/2);
		rotateX(map(mouseY, 0, stageH, -(TWO_PI/2), TWO_PI/2));
		rotateY(map(mouseX, 0, stageW, -(TWO_PI/2), TWO_PI/2));
		translate(-stageW/2, -stageH/2);
	}

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