import hype.*;
import hype.extended.layout.HGridLayout;
import hype.extended.behavior.HOscillator;

// ****************************************************************************

int    stageW   = 1920;
int    stageH   = 1080;
color  clrBg    = #111111;
String pathDATA = "../../data/";

// ****************************************************************************

boolean letsRender   = false;
int     renderModulo = 10; // every 10th frame lets save
int     renderNum    = 0; // start at this number
int     renderMax    = 50; // this is how many images I want
String  renderPATH   = "../renders_003/"; // where do they get saved

// ****************************************************************************

// 3D GRID

int         gridW        = 5;
int         gridH        = 5;
int         gridD        = 5;
int         gridTotal    = gridW * gridH * gridD;
int         gridSpacingX = 500;
int         gridSpacingY = 500;
int         gridSpacingZ = 500;
int         gridStartX   = -((gridW-1)*(gridSpacingX/2));
int         gridStartY   = -((gridH-1)*(gridSpacingY/2));
int         gridStartZ   = -((gridD-1)*(gridSpacingZ/2));

PVector[]   grid         = new PVector[gridTotal];

HGridLayout layout;

// ****************************************************************************

// LETS SPIN SHIT

HOscillator oscRX, oscRY, oscRZ;

HOscillator[] oscCubeScale;
int           oscCubeScaleMin   = 2;   // sprite min size
int           oscCubeScaleMax   = 800; // sprite max size
float         oscCubeScaleSpeed = 0.2;
float         oscCubeScaleFreq  = 10.0;
float         oscCubeScaleStep  = 20.0;

HOscillator[] cubeRX;
HOscillator[] cubeRY;
HOscillator[] cubeRZ;

// ****************************************************************************

// COLORS

PImage        clr1;
int           clrSpeed = 5; // how fast is the color moving
int           clrOffset = 5; // difference in color from neighbor to neighbor
String        whichColor = "colors/chrislee_002.png";

// ****************************************************************************

PImage        tex1, tex2, tex3, tex4, tex5, tex6;

// ****************************************************************************


void settings() {
	size(stageW, stageH, P3D);
	fullScreen();
}

void setup() {
	H.init(this);
	background(clrBg);

	tex1 = loadImage(pathDATA + "textures/frameW2.png");
	tex2 = loadImage(pathDATA + "textures/frameW2.png");
	tex3 = loadImage(pathDATA + "textures/frameW2.png");
	tex4 = loadImage(pathDATA + "textures/frameW2.png");
	tex5 = loadImage(pathDATA + "textures/frameW2.png");
	tex6 = loadImage(pathDATA + "textures/frameB3.png");
	textureMode(NORMAL);

	clr1 = loadImage(pathDATA + whichColor);

	// oscRX = new HOscillator().range(-180,180).speed(0.1).freq(1).waveform(H.SINE);
	// oscRY = new HOscillator().range(-180,180).speed(0.07).freq(1).waveform(H.SINE);
	// oscRZ = new HOscillator().range(-180,180).speed(0.05).freq(1).waveform(H.SINE);

	oscRX = new HOscillator().range(-180,180).speed(0.01).freq(1).waveform(H.SINE);
	oscRY = new HOscillator().range(-180,180).speed(0.07).freq(1).waveform(H.SINE);
	oscRZ = new HOscillator().range(-180,180).speed(0.05).freq(1).waveform(H.SINE);

	layout = new HGridLayout()
		.startLoc(gridStartX,gridStartY,gridStartZ)
		.spacing(gridSpacingX,gridSpacingY,gridSpacingZ)
		.cols(gridW)
		.rows(gridH)
	;

	buildLayout();
}

void draw() {
	// background(clrBg);

	// if (mousePressed) {
	// 	translate(stageW/2, stageH/2);
	// 	rotateX(map(mouseY, 0, stageH, -(TWO_PI/2), TWO_PI/2));
	// 	rotateY(map(mouseX, 0, stageW, -(TWO_PI/2), TWO_PI/2));
	// 	translate(-stageW/2, -stageH/2);
	// }

	// lights();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		oscRX.nextRaw(); rotateX(radians( oscRX.curr() ));
		oscRY.nextRaw(); rotateY(radians( oscRY.curr() ));
		oscRZ.nextRaw(); rotateZ(radians( oscRZ.curr() ));

		for (int i = 0; i < gridTotal; ++i) {
			oscCubeScale[i].nextRaw();

			cubeRX[i].nextRaw(); cubeRY[i].nextRaw(); cubeRZ[i].nextRaw();

			strokeWeight(0);
			noStroke();
			fill(255);
			tint(  clr1.get( ((frameCount*clrSpeed)+(i*clrOffset))%clr1.width, 10 ), 240 ); // color, alpha 0-255

			pushMatrix();
				translate(grid[i].x, grid[i].y, grid[i].z);

				rotateX(radians(cubeRX[i].curr()));
				rotateY(radians(cubeRY[i].curr()));
				rotateZ(radians(cubeRZ[i].curr()));

				scale( oscCubeScale[i].curr() );
				

				if( i != floor(gridTotal/2) ) buildCube();
			popMatrix();
		}
	popMatrix();

	if(frameCount%(renderModulo-1)==0 && letsRender) {
		save(renderPATH + renderNum + ".png"); // jpg, tif, tga
		renderNum++;
		if(renderNum>renderMax) exit();
	}
}

// ****************************************************************************

void buildLayout() {
	PVector pt = new PVector();
	oscCubeScale = new HOscillator[gridTotal];

	cubeRX = new HOscillator[gridTotal];
	cubeRY = new HOscillator[gridTotal];
	cubeRZ = new HOscillator[gridTotal];

	for (int i = 0; i < gridTotal; ++i) {
		grid[i] = layout.getNextPoint();

		oscCubeScale[i] = new HOscillator()
			.range(oscCubeScaleMin, oscCubeScaleMax)
			.speed(oscCubeScaleSpeed)
			.freq(oscCubeScaleFreq)
			.currentStep(i*oscCubeScaleStep)
			.waveform(H.SINE)
		;

		cubeRX[i] = new HOscillator().range(-180, 180).speed(0.08).freq(1).currentStep(i*5).waveform(H.SINE);
		cubeRY[i] = new HOscillator().range(-180, 180).speed(0.06).freq(1).currentStep(i*4).waveform(H.SINE);
		cubeRZ[i] = new HOscillator().range(-180, 180).speed(0.04).freq(1).currentStep(i*3).waveform(H.SINE);
	}
}

// ****************************************************************************

void buildCube() {
	// // back
	// beginShape(QUAD);
	// 	texture(tex1);
	// 	vertex( -(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
	// 	vertex(  (0.5f), -(0.5f), -(0.5f),   1, 0);
	// 	vertex(  (0.5f),  (0.5f), -(0.5f),   1, 1);
	// 	vertex( -(0.5f),  (0.5f), -(0.5f),   0, 1);
	// endShape(CLOSE);

	// // top
	// beginShape(QUAD);
	// 	texture(tex2);
	// 	vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
	// 	vertex( (0.5f), -(0.5f), -(0.5f),   1, 0);
	// 	vertex( (0.5f), -(0.5f),  (0.5f),   1, 1);
	// 	vertex(-(0.5f), -(0.5f),  (0.5f),   0, 1);
	// endShape(CLOSE);

	// // bottom
	// beginShape(QUAD);
	// 	texture(tex3);
	// 	vertex(-(0.5f),  (0.5f),  (0.5f),   0, 0); // x, y, z,  u, v
	// 	vertex( (0.5f),  (0.5f),  (0.5f),   1, 0);
	// 	vertex( (0.5f),  (0.5f), -(0.5f),   1, 1);
	// 	vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	// endShape(CLOSE);

	// left
	beginShape(QUAD);
		texture(tex4);
		vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex(-(0.5f), -(0.5f),  (0.5f),   1, 0);
		vertex(-(0.5f),  (0.5f),  (0.5f),   1, 1);
		vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// // right
	// beginShape(QUAD);
	// 	texture(tex5);
	// 	vertex( (0.5f), -(0.5f),  (0.5f),   0, 0); // x, y, z,  u, v
	// 	vertex( (0.5f), -(0.5f), -(0.5f),   1, 0);
	// 	vertex( (0.5f),  (0.5f), -(0.5f),   1, 1);
	// 	vertex( (0.5f),  (0.5f),  (0.5f),   0, 1);
	// endShape(CLOSE);

	// front
	beginShape(QUAD);
		texture(tex6);
		vertex( -(0.5f), -(0.5f), (0.5f),   0, 0); // x, y, z,  u, v
		vertex(  (0.5f), -(0.5f), (0.5f),   1, 0);
		vertex(  (0.5f),  (0.5f), (0.5f),   1, 1);
		vertex( -(0.5f),  (0.5f), (0.5f),   0, 1);
	endShape(CLOSE);
}

// ****************************************************************************

void keyPressed() {
	switch (key) {
		case 'r': letsRender = true; break;
	}
}