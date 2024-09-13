import hype.*;
import hype.extended.layout.HSphereLayout;
import hype.extended.behavior.HOscillator;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #111111;
String pathDATA     = "../../data/";

// ****************************************************************************

// SPHERE LAYOUT

int      numItems        = 300;
int      sphereRadius    = 300;
int      sphereX         = stageW/2;
int      sphereY         = stageH/2;
int      sphereZ         = 0;

PVector[] pos = new PVector[numItems];

HSphereLayout layout;

// ****************************************************************************

HOscillator[] d;
int           dMin   = 10;
int           dMax   = 360;
float         dSpeed = 1.0;
float         dFreq  = 2.0;
float         dStep  = 3.0;

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

	layout = new HSphereLayout().radius(sphereRadius).loc(sphereX,sphereY,sphereZ);
	layout.useSpiral().numPoints(numItems).phiModifier(1.2345);

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
		d[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*10)+(i*5))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);

			float[] numbers = layout.getRotations(pos[i]);
			rotateX(numbers[0]); // x rotation
			rotate(numbers[1], numbers[2], numbers[3], numbers[4]); // y, xyz

			box(10, 10, d[i].curr() );
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();
	d = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		d[i] = new HOscillator().range(dMin, dMax).speed(dSpeed).freq(dFreq).currentStep(i*dStep).waveform(H.SINE);
	}
}