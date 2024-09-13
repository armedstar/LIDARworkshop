import hype.*;
import hype.extended.layout.HSphereLayout;
import hype.extended.behavior.HOscillator;

// ****************************************************************************

int           stageW           = 900;
int           stageH           = 900;
color         clrBg            = #202020;
String        pathDATA         = "../../data/";

// ****************************************************************************

// SPHERE LAYOUT

int           sphereCols       = 2;
int           sphereRows       = 32;
int           numItems         = 600;

int           sphereRadius     = 200;
int           sphereX          = 0;
int           sphereY          = 0;
int           sphereZ          = 0;

PVector[]     pos              = new PVector[numItems];

HSphereLayout layout;

// ****************************************************************************

HOscillator   oscRX, oscRY, oscRZ;

// ****************************************************************************

PImage        clr1, img1, img2;

// ****************************************************************************

// CUBE SETTINGS

float         cubeW            = 3.0;
float         cubeH            = 3.0;

float         cubeD            = 10.0;
float         cubeDMin         = 3.0;
float         cubeDMax         = 200.0;

// ****************************************************************************

// AUDIO

import ddf.minim.*;
import ddf.minim.analysis.*;

Minim         minim;
AudioPlayer   myAudio;
FFT           myAudioFFT;

int           myAudioRange     = sphereRows*2;
int           myAudioMax       = 100;

float         myAudioAmp       = 22.0;
float         myAudioIndex     = 0.19;
float         myAudioIndexAmp  = myAudioIndex;
float         myAudioIndexStep = 0.225;

float[]       myAudioData       = new float[myAudioRange];

// ************************************************************************************

PImage        planetTexture, rings;

int           planetW, planetH;

int           numPointsW;
int           numPointsH_2pi; 
int           numPointsH;
float[]       coorX;
float[]       coorY;
float[]       coorZ;
float[]       multXZ;

// ************************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	minim   = new Minim(this);
	myAudio = minim.loadFile(pathDATA + "audio/HECQ_With_Angels_Trifonic_Remix.wav");
	myAudio.play();

	myAudioFFT = new FFT(myAudio.bufferSize(), myAudio.sampleRate());
	myAudioFFT.linAverages(myAudioRange);
	myAudioFFT.window(FFT.GAUSS);

	clr1 = loadImage(pathDATA + "colors/color_002.png");

	planetTexture = loadImage(pathDATA + "textures/texture.png");
	planetW = planetH = 50;
	initializeSphere(planetW, planetH);

	layout = new HSphereLayout().radius(sphereRadius).loc(sphereX,sphereY,sphereZ);
	layout.useSpiral().numPoints(numItems);
	// layout.phiModifier(7.2);
	layout.phiModifier(1.2345);
	// layout.phiModifier(3.0001);
	// layout.phiModifier(0.805);

	oscRX = new HOscillator().range(-360,360).speed(0.01).freq(1).waveform(H.SINE);
	oscRY = new HOscillator().range(-360,360).speed(0.07).freq(1).waveform(H.SINE);
	oscRZ = new HOscillator().range(-360,360).speed(0.05).freq(1).waveform(H.SINE);

	buildLayout(); // lets fire the layout function
}

void draw() {
	noTint();
	background(clrBg);

	myAudioFFT.forward(myAudio.mix);
	for (int i = 0; i < myAudioRange; ++i) {
		float tempIndexAvg = (myAudioFFT.getAvg(i) * myAudioAmp) * myAudioIndexAmp;
		float tempIndexCon = constrain(tempIndexAvg, 0, myAudioMax);
		myAudioData[i]     = tempIndexCon;
		myAudioIndexAmp+=myAudioIndexStep;
	}
	myAudioIndexAmp = myAudioIndex;


	// lights();
	pointLight(255, 255, 255, stageW/2, -500, 200); // r,g,b, x,y,z
	pointLight(100, 100, 100, stageW/2, 1500, 0);   // r,g,b, x,y,z


	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		oscRX.speed( map(myAudioData[0], 0, 100, -0.001, 0.03) );
		oscRX.nextRaw();
		rotateX(radians( oscRX.curr() ));

		oscRY.speed( map(myAudioData[3], 0, 100, -0.007, 0.09) );
		oscRY.nextRaw();
		rotateY(radians( oscRY.curr() ));

		oscRZ.speed( map(myAudioData[6], 0, 100, -0.005, 0.07) );
		oscRZ.nextRaw();
		rotateZ(radians( oscRZ.curr() ));


		strokeWeight(0);
		noStroke();
		fill(255 );
		textureSphere(150, 150, 150, planetTexture);


		noLights();

		for (int i = 0; i < numItems; ++i) {
			strokeWeight(0);
			noStroke();
			fill(  clr1.get( int(((frameCount*10)+(i*1.1))%clr1.width), 10 ) );

			pushMatrix();
				translate(pos[i].x, pos[i].y, pos[i].z);

				float[] numbers = layout.getRotations(pos[i]);
				rotateX(numbers[0]); // x rotation
				rotate(numbers[1], numbers[2], numbers[3], numbers[4]); // y, xyz

				cubeD = (int)map(myAudioData[i%myAudioRange], 0, 100, cubeDMin, cubeDMax);

				buildCube( cubeW, cubeH, cubeD );
			popMatrix();
		}

	popMatrix();
}

// ****************************************************************************

void stop() {
	myAudio.close();
	minim.stop();  
	super.stop();
}

// ****************************************************************************

void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}
}

// ****************************************************************************

void buildCube(float _w, float _h, float _d) {

	// back
	beginShape(QUAD);
		// texture(img2);
		vertex( -(_w/2), -(_h/2), -(0),   0, 0);
		vertex(  (_w/2), -(_h/2), -(0),   1, 0);
		vertex(  (_w/2),  (_h/2), -(0),   1, 1);
		vertex( -(_w/2),  (_h/2), -(0),   0, 1);
	endShape(CLOSE);

	// top
	beginShape(QUAD);
		// texture(img2);
		vertex(-(_w/2), -(_h/2),  -(0),   0, 0);
		vertex( (_w/2), -(_h/2),  -(0),   1, 0);
		vertex( (_w/2), -(_h/2),  (_d),   1, 1);
		vertex(-(_w/2), -(_h/2),  (_d),   0, 1);
	endShape(CLOSE);

	// bottom
	beginShape(QUAD);
		// texture(img2);
		vertex(-(_w/2),  (_h/2), (_d),   0, 0);
		vertex( (_w/2),  (_h/2), (_d),   1, 0);
		vertex( (_w/2),  (_h/2), -(0),   1, 1);
		vertex(-(_w/2),  (_h/2), -(0),   0, 1);
	endShape(CLOSE);

	// left
	beginShape(QUAD);
		// texture(img2);
		vertex( -(_w/2), -(_h/2), -(0),   0, 0);
		vertex( -(_w/2), -(_h/2), (_d),   1, 0);
		vertex( -(_w/2),  (_h/2), (_d),   1, 1);
		vertex( -(_w/2),  (_h/2), -(0),   0, 1);
	endShape(CLOSE);

	// right
	beginShape(QUAD);
		// texture(img2);
		vertex( (_w/2), -(_h/2),  (_d),   0, 0);
		vertex( (_w/2), -(_h/2),  -(0),   1, 0);
		vertex( (_w/2),  (_h/2),  -(0),   1, 1);
		vertex( (_w/2),  (_h/2),  (_d),   0, 1);
	endShape(CLOSE);

	// // front
	beginShape(QUAD);
		// texture(img2);
		vertex( -(_w/2), -(_h/2), (_d),   0, 0);
		vertex(  (_w/2), -(_h/2), (_d),   1, 0);
		vertex(  (_w/2),  (_h/2), (_d),   1, 1);
		vertex( -(_w/2),  (_h/2), (_d),   0, 1);
	endShape(CLOSE);
}

// ****************************************************************************

// based on / https://processing.org/examples/texturesphere.html Texture Sphere by Gillian Ramsay

void initializeSphere(int numPtsW, int numPtsH_2pi) {
	numPointsW=numPtsW+1;
	numPointsH_2pi=numPtsH_2pi;
	numPointsH=ceil((float)numPointsH_2pi/2)+1;

	coorX  = new float[numPointsW];
	coorY  = new float[numPointsH];
	coorZ  = new float[numPointsW];
	multXZ = new float[numPointsH];

	for (int i=0; i<numPointsW ;i++) {
		float thetaW=i*2*PI/(numPointsW-1);
		coorX[i]=sin(thetaW);
		coorZ[i]=cos(thetaW);
	}
	
	for (int i=0; i<numPointsH; i++) {
		if (int(numPointsH_2pi/2) != (float)numPointsH_2pi/2 && i==numPointsH-1) {
			float thetaH=(i-1)*2*PI/(numPointsH_2pi);
			coorY[i]=cos(PI+thetaH); 
			multXZ[i]=0;
		} else {
			float thetaH=i*2*PI/(numPointsH_2pi);
			coorY[i]=cos(PI+thetaH); 
			multXZ[i]=sin(thetaH);
		}
	}
}

void textureSphere(float rx, float ry, float rz, PImage t) { 
	float changeU=t.width/(float)(numPointsW-1); 
	float changeV=t.height/(float)(numPointsH-1); 
	float u=0;
	float v=0;

	beginShape(TRIANGLE_STRIP);
		strokeWeight(0);
		noStroke();
		texture(t);
		for (int i=0; i<(numPointsH-1); i++) {
			float coory=coorY[i];
			float cooryPlus=coorY[i+1];

			float multxz=multXZ[i];
			float multxzPlus=multXZ[i+1];

			for (int j=0; j<numPointsW; j++) {
				normal(-coorX[j]*multxz, -coory, -coorZ[j]*multxz);
				vertex(coorX[j]*multxz*rx, coory*ry, coorZ[j]*multxz*rz, u, v);
				normal(-coorX[j]*multxzPlus, -cooryPlus, -coorZ[j]*multxzPlus);
				vertex(coorX[j]*multxzPlus*rx, cooryPlus*ry, coorZ[j]*multxzPlus*rz, u, v+changeV);
				u+=changeU;
			}
			v+=changeV;
			u=0;
		}
	endShape();
}