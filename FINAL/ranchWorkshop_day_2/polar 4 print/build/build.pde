import hype.*;
import hype.extended.layout.HPolarLayout;
import hype.extended.behavior.HOscillator;


// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #111111;
String pathDATA     = "../../data/";

// ****************************************************************************

boolean letsRender   = false;
int     renderModulo = 10; // every 10th frame lets save
int     renderNum    = 0; // start at this number
int     renderMax    = 50; // this is how many images I want
String  renderPATH   = "../renders_001/"; // where do they get saved

// ****************************************************************************


// POLAR LAYOUT

int      numItems        = 600;
int      polarStartX     = 0;
int      polarStartY     = 0;
float    polarLength     = 1.0; // default 1.0
float    polarAngleStep  = 0.2; // default 0.1


PVector[] pos = new PVector[numItems];

HPolarLayout layout;

// ****************************************************************************

HOscillator[] zPos;
int           zPosMin   = -500;
int           zPosMax   =  500;
float         zPosSpeed = 1.0;
float         zPosFreq  = 1.0;
float         zPosStep  = 0.5;

HOscillator   oscR;

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
	oscR = new HOscillator().range(-180,180).speed(0.1).freq(1);

	layout = new HPolarLayout(polarLength, polarAngleStep).offsetX(polarStartX).offsetY(polarStartY);

	buildLayout(); // lets fire the layout function
}

void draw() {
	// background(clrBg);

	oscR.nextRaw();

	lights();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);
		rotate(radians(oscR.curr()));

		for (int i = 0; i < numItems; ++i) {
			zPos[i].nextRaw();

			strokeWeight(0);
			noStroke();
			fill(  clr1.get( ((frameCount*2)+(i*5))%clr1.width, 10 ) );

			pushMatrix();
				translate(pos[i].x, pos[i].y, pos[i].z + zPos[i].curr() );
				box(10, 10, 100);
			popMatrix();
		}
	popMatrix();

	if(frameCount%(renderModulo-1)==0 && letsRender) {
		save(renderPATH + renderNum + ".png"); // jpg, tif, tga
		renderNum++;
		if(renderNum>renderMax) exit();
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


void keyPressed() {
	switch (key) {
		case 'r': letsRender = true; break;
	}
}