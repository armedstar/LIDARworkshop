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

int      numItems        = 500;
int      sphereRadius    = 100;
int      sphereX         = stageW/2;
int      sphereY         = stageH/2;
int      sphereZ         = 0;

PVector[] pos = new PVector[numItems];

HSphereLayout layout;

// ****************************************************************************

HOscillator[] wOsc; // width
float         wOscMin   = 35.0;
float         wOscMax   = 0.1;
float         wOscSpeed = 0.7;
float         wOscFreq  = 5.0;
float         wOscStep  = 5.0;

HOscillator[] hOsc; // height
float         hOscMin   = 35.0;
float         hOscMax   = 0.1;
float         hOscSpeed = 0.7;
float         hOscFreq  = 5.0;
float         hOscStep  = 5.0;

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

	layout = new HSphereLayout()
		.radius(sphereRadius)
		.loc(sphereX,sphereY,sphereZ)
		.extendRadius(25)
		.useSpiral()
		.phiModifier(3.0001)
	;

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

		wOsc[i].nextRaw();
		hOsc[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*10)+(i*5))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(wOsc[i].curr(), hOsc[i].curr(), 20);
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();
	wOsc = new HOscillator[numItems];
	hOsc = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		wOsc[i] = new HOscillator()
		.range(wOscMin, wOscMax)
		.speed(wOscSpeed)
		.freq(wOscFreq)
		.currentStep(i*wOscStep)
		.waveform(H.EASE);

		hOsc[i] = new HOscillator()
		.range(hOscMin, hOscMax)
		.speed(hOscSpeed)
		.freq(hOscFreq)
		.currentStep(i*hOscStep)
		.waveform(H.EASE);

	}

	// println( pos );
}