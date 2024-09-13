import hype.*;
import hype.extended.behavior.HOscillator;

int         stageW      = 1920;
int         stageH      = 1080;

color       clrBG       = #CCCCCC;

String      pathDATA    = "../../data/";

// ********************************************************************************************************************

// TEXTURES

String[]    texNames    = { "texture1.png" };
int         texNamesLen = texNames.length;
PImage[]    texLoaded   = new PImage[texNamesLen];

int         texW        = 1000;
int         texH        = 1000;


//                         0    1    2    3
int[]       texUV       = {200, 400, 600, 800};
int         whichUV     = 0;
int         texUVmax    = 1000;

HOscillator texOSC;

// ********************************************************************************************************************

// COLOR FLOWS

String[]    clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]       clr_Max     = {               300,               500,               700 };
int         clr_Len     = clr_Strings.length;

color[][]   clr_Colors  = new color[clr_Len][];
int         clr_IntLen  = 1;
int[][]     clr_Int     = new int[clr_Len][clr_IntLen];

PImage[]    clr_PImage  = new PImage[clr_Len];

int         whichClr    = 0;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup(){
	H.init(this);
	setupTEX(); // TEXTURES
	setupColorFlow();  // COLOR FLOWS

	texOSC = new HOscillator().range(0.0, 1.0).speed(1).freq(1);
}
 
void draw(){
	background(clrBG);

	texOSC.nextRaw();
	float map1UV = map(texOSC.curr(), 0, 1, texUV[0], texUVmax);
	float map2UV = map(texOSC.curr(), 0, 1, texUV[1], texUVmax);
	float map3UV = map(texOSC.curr(), 0, 1, texUV[2], texUVmax);
	float map4UV = map(texOSC.curr(), 0, 1, texUV[3], texUVmax);

	color c = clr_Colors[whichClr][clr_Int[whichClr][0]];
	tint(c);

	// VISUALIZE THE TEXTURE SOURCE

	pushMatrix();
		translate(540, stageH/2, 0);

		image(texLoaded[0], -(texW/2), -(texH/2), texW, texH);

		strokeWeight(2);
		stroke(#000000);
		noFill();
		line( -(texW/2), 0, (texW/2), 0 );
		line( 0, -(texH/2), 0, (texH/2) );

		stroke(#FF0000);

		rect( -( map1UV/2 ), -( map1UV/2 ), map1UV, map1UV ); // UV array subitem 0
		rect( -( map2UV/2 ), -( map2UV/2 ), map2UV, map2UV ); // UV array subitem 1
		rect( -( map3UV/2 ), -( map3UV/2 ), map3UV, map3UV ); // UV array subitem 2
		rect( -( map4UV/2 ), -( map4UV/2 ), map4UV, map4UV ); // UV array subitem 3
	popMatrix();



	strokeWeight(0);
	noStroke();
	noFill();



	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 001

	pushMatrix();
		translate(1270, 230, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   (texW/2)-(map1UV/2), (texH/2)-(map1UV/2) );
			vertex(  (0.5), -(0.5), 0,   (texW/2)+(map1UV/2), (texH/2)-(map1UV/2) );
			vertex(  (0.5),  (0.5), 0,   (texW/2)+(map1UV/2), (texH/2)+(map1UV/2) );
			vertex( -(0.5),  (0.5), 0,   (texW/2)-(map1UV/2), (texH/2)+(map1UV/2) );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 002

	pushMatrix();
		translate(1690, 230, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   (texW/2)-(map2UV/2), (texH/2)-(map2UV/2) );
			vertex(  (0.5), -(0.5), 0,   (texW/2)+(map2UV/2), (texH/2)-(map2UV/2) );
			vertex(  (0.5),  (0.5), 0,   (texW/2)+(map2UV/2), (texH/2)+(map2UV/2) );
			vertex( -(0.5),  (0.5), 0,   (texW/2)-(map2UV/2), (texH/2)+(map2UV/2) );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 003

	pushMatrix();
		translate(1270, 650, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   (texW/2)-(map3UV/2), (texH/2)-(map3UV/2) );
			vertex(  (0.5), -(0.5), 0,   (texW/2)+(map3UV/2), (texH/2)-(map3UV/2) );
			vertex(  (0.5),  (0.5), 0,   (texW/2)+(map3UV/2), (texH/2)+(map3UV/2) );
			vertex( -(0.5),  (0.5), 0,   (texW/2)-(map3UV/2), (texH/2)+(map3UV/2) );
		endShape(CLOSE);
	popMatrix();

	// VISUALIZE THE ACTUAL TEXTURE / SPRITE / 004

	pushMatrix();
		translate(1690, 650, 0);
		scale(380);

		beginShape(QUADS);
			texture( texLoaded[0] );
			vertex( -(0.5), -(0.5), 0,   (texW/2)-(map4UV/2), (texH/2)-(map4UV/2) );
			vertex(  (0.5), -(0.5), 0,   (texW/2)+(map4UV/2), (texH/2)-(map4UV/2) );
			vertex(  (0.5),  (0.5), 0,   (texW/2)+(map4UV/2), (texH/2)+(map4UV/2) );
			vertex( -(0.5),  (0.5), 0,   (texW/2)-(map4UV/2), (texH/2)+(map4UV/2) );
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


