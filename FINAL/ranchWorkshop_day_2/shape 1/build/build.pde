import hype.*;
import hype.extended.layout.HShapeLayout;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #ECECEC;
String pathDATA     = "../../data/";

// ****************************************************************************

// SHAPE LAYOUT

 int      numItems        = 2500;
PImage    myImage;
HImage    hitImage;

PVector[] pos = new PVector[numItems];

HShapeLayout layout;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	myImage = loadImage(pathDATA + "textures/shape.png");
	hitImage = new HImage(myImage);

	layout = new HShapeLayout().target(hitImage);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);

	image(myImage,0,0);

	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			ellipse(0, 0, 10, 10);
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}
}