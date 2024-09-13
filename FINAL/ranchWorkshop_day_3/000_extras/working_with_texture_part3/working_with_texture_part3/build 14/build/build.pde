import hype.*;
import hype.extended.behavior.HOscillator;

int           stageW      = 1920;
int           stageH      = 1080;

color         clrBG       = #FFFFFF;

String        pathDATA    = "../../data/";

// ********************************************************************************************************************

int           numAssets   = 100;

// ********************************************************************************************************************

// TEXTURES

String[]      texNames    = { "dm_001.png", "dm_002.png", "dm_003.png" };
int           texNamesLen = texNames.length;
PImage[]      texLoaded   = new PImage[texNamesLen];

int           texUVmax    = 1920;

//                           0    1    2    3   4    5
int[]         texUV       = {50, 100, 200, 400, 800, 1000};
int           texUVLen    = texUV.length;
int[]         whichUV     = new int[numAssets];
HOscillator[] texOSC      = new HOscillator[numAssets];

PVector[]     pickedUVXY  = new PVector[numAssets];

boolean       showUVMaps  = false;
boolean       showUVTexs  = true;

// ********************************************************************************************************************

// COLOR FLOWS

String[]      clr_Strings = { "color_1_001.png", "color_1_002.png", "color_1_003.png" };
int[]         clr_Max     = {               300,               300,               300 };
int           clr_Len     = clr_Strings.length;

color[][]     clr_Colors  = new color[clr_Len][];
int           clr_IntLen  = numAssets;
int[][]       clr_Int     = new int[clr_Len][clr_IntLen];

PImage[]      clr_PImage  = new PImage[clr_Len];

int           whichClr    = 1;

// ********************************************************************************************************************

// XYZ screen positions

PVector[]     pickedXYZ   = new PVector[numAssets];
PVector[]     data1       = new PVector[numAssets]; // texture, scale, rotation
HOscillator[] texZ        = new HOscillator[numAssets];
HOscillator[] texR        = new HOscillator[numAssets];


// ********************************************************************************************************************

void settings() {
	size(stageW, stageH, P3D);
	// PJOGL.profile = 1;
}

void setup(){
	H.init(this);

	setupTEX(); // TEXTURES
	setupColorFlow();  // COLOR FLOWS
	setupUVXY(); // XY COORDS FOR UV MAPPING
	setupART(); // XYZ screen positions

}
 
void draw(){
	background(clrBG);

	if (showUVTexs) hint(DISABLE_DEPTH_TEST);
	else            hint(ENABLE_DEPTH_TEST);
	// hint(ENABLE_DEPTH_SORT);

	strokeWeight(0);
	noStroke();
	noFill();

	for (int i = 0; i < numAssets; ++i) {
		PVector pt = pickedXYZ[i];
		PVector _data1 = data1[i];

		HOscillator _osc = texOSC[i];
		_osc.nextRaw();
		float mapUV = map(_osc.curr(), 0, 1, texUV[whichUV[i]], texUVmax);

		HOscillator _z = texZ[i]; _z.nextRaw();
		HOscillator _r = texR[i]; _r.nextRaw();

		pushMatrix();
			translate(stageW/2, stageH/2, _z.curr());

			pushMatrix();
				translate(pt.x, pt.y, pt.z);
				scale( (int)_data1.y );
				rotate(radians( _r.curr() ));

				if(showUVTexs) {
					strokeWeight(0);
					noStroke();
					noFill();
				} else {
					float sw = (float) 1.0/(int)_data1.y;
					strokeWeight(sw);
					stroke(0);
					fill(255);
				}

				// color c = clr_Colors[whichClr][clr_Int[whichClr][i%clr_Max[whichClr]]];
				// tint(c, 225);


				beginShape(QUADS);
					if(showUVTexs) texture( texLoaded[ (int)_data1.x ] );
					vertex( -(0.5), -(0.5), 0,   (texUVmax/2)-(mapUV/2), (texUVmax/2)-(mapUV/2) );
					vertex(  (0.5), -(0.5), 0,   (texUVmax/2)+(mapUV/2), (texUVmax/2)-(mapUV/2) );
					vertex(  (0.5),  (0.5), 0,   (texUVmax/2)+(mapUV/2), (texUVmax/2)+(mapUV/2) );
					vertex( -(0.5),  (0.5), 0,   (texUVmax/2)-(mapUV/2), (texUVmax/2)+(mapUV/2) );
				endShape(CLOSE);

			popMatrix();
		popMatrix();

		if(showUVMaps) {
			strokeWeight(2);
			stroke(#FF3300);
			noFill();

			pushMatrix();
				translate(texUVmax/2, texUVmax/2, 2);
				rect( -( mapUV/2 ), -( mapUV/2 ), mapUV, mapUV );
			popMatrix();
		}
	}

	if(showUVMaps) {
		noTint();
		pushMatrix();
			translate(0, 0, 1);
			image(texLoaded[0], 0, 0, texUVmax, texUVmax);
		popMatrix();
	}

	noTint();
	updateColorFlow(); // COLOR FLOWS
	surface.setTitle( int(frameRate) + " FPS" );
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

// // XYZ screen positions

void setupART() {
	for (int i = 0; i < numAssets; ++i) {
		PVector pt = new PVector();
		pt.x = (int)random( -(stageW/2), (stageW/2) );
		pt.y = (int)random( -(stageH/2), (stageH/2) );
		pt.z = 0;
		pickedXYZ[i] = pt;

		PVector d1 = new PVector();
		d1.x = (int)random(texNamesLen); // which texture to use ?
		d1.y = (int)random( 250, 750 );  // which scale to use ?
		d1.z = (int)random( 0 );         // which rotation to use ?
		data1[i] = d1;

		texZ[i] = new HOscillator().range(-2500, 250).speed(0.75).freq(1.0).currentStep( (int)random(555) );
		texR[i] = new HOscillator().range(-180, 180).speed(0.1).freq(1.0).currentStep( (int)random(444) ).waveform(H.EASE);
	}
}

// ********************************************************************************************************************

// SETUP TEXTURE XY COORDS FOR UV MAPPING

void setupUVXY() {
	for (int i = 0; i < numAssets; ++i) {
		whichUV[i] = (int)random(texUVLen);
		texOSC[i] = new HOscillator().range(0.0, 1.0).speed(0.25).freq(5).currentStep( (int)random(333) );
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
		case ' ': setupUVXY(); setupART();  break;
		case '1': whichClr = 0; break;
		case '2': whichClr = 1; break;
		case '3': whichClr = 2; break;
		case 'm': showUVMaps = !showUVMaps; break;
		case 'd': showUVTexs = !showUVTexs; break;
	}
}

// ********************************************************************************************************************

