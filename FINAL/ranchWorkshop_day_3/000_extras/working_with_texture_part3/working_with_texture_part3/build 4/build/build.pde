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
int[]     texUV       = {200, 400, 600, 800};
int       texUVLen    = texUV.length;
PVector[] pickedUVXY  = new PVector[texUVLen];
int       whichUV     = 0;

// ********************************************************************************************************************

// COLOR FLOWS

String[]  clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]     clr_Max     = {               100,               100,               100 };
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
	setupUVXY(); // XY COORDS FOR UV MAPPING
	setupColorFlow();  // COLOR FLOWS
}
 
void draw(){
	background(clrBG);

	// VISUALIZE THE TEXTURE SOURCE

	pushMatrix();
		translate(40, 40, 0);

		image(texLoaded[0], 0, 0, texW, texH);

		strokeWeight(2);
		stroke(#FF0000);
		noFill();

		for (int i = 0; i < texUVLen; ++i) {
			rect( pickedUVXY[i].x, pickedUVXY[i].y, texUV[i], texUV[i] );
		}
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
			vertex( -(0.5), -(0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y );
			vertex(  (0.5), -(0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y );
			vertex(  (0.5),  (0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y + texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y + texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 002

	color c1 = clr_Colors[0][clr_Int[0][0]];
	tint(c1);

	pushMatrix();
		translate(1690, 230, 0);
		scale(380);
		whichUV = 1;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y );
			vertex(  (0.5), -(0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y );
			vertex(  (0.5),  (0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y + texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y + texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 003

	color c2 = clr_Colors[1][clr_Int[1][0]];
	tint(c2);

	pushMatrix();
		translate(1270, 650, 0);
		scale(380);
		whichUV = 2;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y );
			vertex(  (0.5), -(0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y );
			vertex(  (0.5),  (0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y + texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y + texUV[whichUV] );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 004

	color c3 = clr_Colors[2][clr_Int[2][0]];
	tint(c3);

	pushMatrix();
		translate(1690, 650, 0);
		scale(380);
		whichUV = 3;

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y );
			vertex(  (0.5), -(0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y );
			vertex(  (0.5),  (0.5), 0,   pickedUVXY[whichUV].x + texUV[whichUV], pickedUVXY[whichUV].y + texUV[whichUV] );
			vertex( -(0.5),  (0.5), 0,   pickedUVXY[whichUV].x,                  pickedUVXY[whichUV].y + texUV[whichUV] );
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

// SETUP TEXTURE XY COORDS FOR UV MAPPING

void setupUVXY() {
	for (int i = 0; i < texUVLen; ++i) {
		PVector _pt = new PVector();
		_pt.x = (int)random((texW-texUV[i]));
		_pt.y = (int)random((texH-texUV[i]));
		pickedUVXY[i] = _pt;
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
		case ' ': setupUVXY();  break;
	}
}

// ********************************************************************************************************************


