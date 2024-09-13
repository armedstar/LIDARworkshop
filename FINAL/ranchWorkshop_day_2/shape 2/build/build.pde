import hype.*;
import hype.extended.layout.HShapeLayout;
import hype.extended.behavior.HOscillator;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #111111;
String pathDATA     = "../../data/";

// ****************************************************************************

// SHAPE LAYOUT

 int      numItems        = 2500;
PImage    myImage;
HImage    hitImage;

PVector[] pos = new PVector[numItems];

HShapeLayout layout;

// ****************************************************************************

HOscillator[] sPos;
int           sPosMin   = 0;
int           sPosMax   = 20;
float         sPosSpeed = 1.0;
float         sPosFreq  = 5.0;
float         sPosStep  = 5.0;

// ****************************************************************************

PImage        clr1;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_002.png");
	myImage = loadImage(pathDATA + "textures/shape.png");
	hitImage = new HImage(myImage);

	layout = new HShapeLayout().target(hitImage);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);

	// image(myImage,0,0);

	lights();

	for (int i = 0; i < numItems; ++i) {
		sPos[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*10)+(i))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			ellipse(0, 0, sPos[i].curr(), sPos[i].curr());
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();
	sPos = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		sPos[i] = new HOscillator().range(sPosMin, sPosMax).speed(sPosSpeed).freq(sPosFreq).currentStep(i*sPosStep).waveform(H.SINE);
	}
}