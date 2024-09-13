import hype.*;
import hype.extended.layout.HHexLayout;
import hype.extended.behavior.HOscillator;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #111111;
String pathDATA     = "../../data/";

// ****************************************************************************

// HEX LAYOUT

 int      numItems        = 169;
 int      hexSpacing      = 25;
 int      hexStartX       = 0;
 int      hexStartY       = 0;

PVector[] pos = new PVector[numItems];

HHexLayout layout;

// ****************************************************************************

HOscillator[] zPos;
int           zPosMin   = -900;
int           zPosMax   =  100;
float         zPosSpeed = 1.0;
float         zPosFreq  = 1.0;
float         zPosStep  = 2.0;

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

	layout = new HHexLayout().spacing(hexSpacing).offsetX(hexStartX).offsetY(hexStartY);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);

	lights();

	for (int i = 0; i < numItems; ++i) {
		zPos[i].nextRaw();

		strokeWeight(0);
		noStroke();
		fill(  clr1.get( ((frameCount*2)+(i*50))%clr1.width, 10 ) );

		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z + zPos[i].curr() );
			box(20, 20, 1000);
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();
	zPos = new HOscillator[numItems];

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();

		zPos[i] = new HOscillator().range(zPosMin, zPosMax).speed(zPosSpeed).freq(zPosFreq).currentStep(i*zPosStep).waveform(H.SINE);
	}

	// println( pos );
}