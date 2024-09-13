int       stageW      = 1920;
int       stageH      = 1080;

color     clrBG       = #CCCCCC;

String    pathDATA    = "../../data/";

// ********************************************************************************************************************

// TEXTURES

String[]  texNames    = { "photo.png" };
int       texNamesLen = texNames.length;
PImage[]  texLoaded   = new PImage[texNamesLen];

int       texW        = 1000;
int       texH        = 1000;

//                       0    1    2    3
int[]     texUV       = {250, 500, 750, 1000};
int       whichUV     = 0;

// ********************************************************************************************************************

// COLOR FLOWS

String[]  clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]     clr_Max     = {               300,               500,               700 };
int       clr_Len     = clr_Strings.length;

color[][] clr_Colors  = new color[clr_Len][];
int       clr_IntLen  = 1;
int[][]   clr_Int     = new int[clr_Len][clr_IntLen];

PImage[]  clr_PImage  = new PImage[clr_Len];

int       whichClr    = 0;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup(){
	setupTEX(); // TEXTURES
	setupColorFlow();  // COLOR FLOWS
}
 
void draw(){
	background(clrBG);

	color c = clr_Colors[whichClr][clr_Int[whichClr][0]];
	tint(c);

	// VISUALIZE THE TEXTURE SOURCE

	pushMatrix();
		translate(40, 40, 0);

		image(texLoaded[0], 0, 0, texW, texH);

		strokeWeight(2);
		stroke(#FF0000);
		noFill();

		rect( 0, 0, texUV[0], texUV[0] ); // UV array subitem 0
		rect( 0, 0, texUV[1], texUV[1] ); // UV array subitem 1
		rect( 0, 0, texUV[2], texUV[2] ); // UV array subitem 2
		rect( 0, 0, texUV[3], texUV[3] ); // UV array subitem 3
	popMatrix();



	strokeWeight(0);
	noStroke();
	noFill();



	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 001

	pushMatrix();
		translate(1270, 230, 0);
		scale(380);
		whichUV = 0;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   0, 0 );
			vertex(  (0.5), -(0.5), 0,   texUV[whichUV], 0 );
			vertex(  (0.5),  (0.5), 0,   texUV[whichUV], texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   0, texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 002

	pushMatrix();
		translate(1690, 230, 0);
		scale(380);
		whichUV = 1;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   0, 0 );
			vertex(  (0.5), -(0.5), 0,   texUV[whichUV], 0 );
			vertex(  (0.5),  (0.5), 0,   texUV[whichUV], texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   0, texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 003

	pushMatrix();
		translate(1270, 650, 0);
		scale(380);
		whichUV = 2;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   0, 0 );
			vertex(  (0.5), -(0.5), 0,   texUV[whichUV], 0 );
			vertex(  (0.5),  (0.5), 0,   texUV[whichUV], texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   0, texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 004

	pushMatrix();
		translate(1690, 650, 0);
		scale(380);
		whichUV = 3;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   0, 0 );
			vertex(  (0.5), -(0.5), 0,   texUV[whichUV], 0 );
			vertex(  (0.5),  (0.5), 0,   texUV[whichUV], texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   0, texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();



	noTint();
	updateColorFlow(); // COLOR FLOWS
	// surface.setTitle( int(frameRate) + " FPS" );
}

// ********************************************************************************************************************

// TEXTURES

void setupTEX() {
	for (int i = 0; i < texNamesLen; ++i) {
		PImage _temp = loadImage(pathDATA + texNames[i]);
		texLoaded[i] = _temp;
	}
}

// ********************************************************************************************************************

// COLOR FLOWS

void setupColorFlow() {
	for (int i = 0; i < clr_Len; ++i) {
		clr_PImage[i] = new PImage();
		clr_PImage[i] = loadImage(pathDATA + clr_Strings[i]);

		color[] tmpArray = new color[clr_Max[i]];
		clr_Colors[i] = tmpArray;

		for (int j = 0; j < clr_Max[i]; j++) {
			float tempPos = ((float)clr_PImage[i].width / clr_Max[i]) * j;
			clr_Colors[i][j] = clr_PImage[i].get( Math.round(tempPos), 1 );
		}

		for (int j = 0; j < clr_IntLen; j++) {
			clr_Int[i][j] = j%clr_Max[i];
		}
	}
}

void updateColorFlow() {
	for (int i = 0; i < clr_Len; ++i) {
		for (int j = 0; j < clr_IntLen; ++j) {
			int _tempNum = clr_Int[i][j];
			_tempNum++;
			if(_tempNum >= clr_Max[i]) _tempNum = 0;
			clr_Int[i][j] = _tempNum;
		}
	}
}

// ********************************************************************************************************************

void keyPressed() {
	switch (key) {
		case '1': whichClr = 0; break;
		case '2': whichClr = 1; break;
		case '3': whichClr = 2; break;
	}
}

// ********************************************************************************************************************


