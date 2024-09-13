import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HSphereLayout; 
import hype.extended.behavior.HOscillator; 
import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {





// ****************************************************************************

int           stageW           = 1920;
int           stageH           = 1080;
int         clrBg            = 0xff111111;
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

float         cubeW            = 3.0f;
float         cubeH            = 3.0f;

float         cubeD            = 10.0f;
float         cubeDMin         = 3.0f;
float         cubeDMax         = 200.0f;

// ****************************************************************************

// AUDIO




Minim         minim;
AudioInput    myAudio;
FFT           myAudioFFT;

int           myAudioRange     = sphereRows*2;
int           myAudioMax       = 100;

float         myAudioAmp       = 22.0f;
float         myAudioIndex     = 0.19f;
float         myAudioIndexAmp  = myAudioIndex;
float         myAudioIndexStep = 0.225f;

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

public void settings() {
	size(stageW, stageH, P3D);
	fullScreen();
}

public void setup() {
	H.init(this);
	background(clrBg);

	minim   = new Minim(this);
	myAudio = minim.getLineIn(Minim.MONO);

	myAudioFFT = new FFT(myAudio.bufferSize(), myAudio.sampleRate());
	myAudioFFT.linAverages(myAudioRange);
	myAudioFFT.window(FFT.GAUSS);

	clr1 = loadImage(pathDATA + "colors/color_002.png");

	rings = loadImage(pathDATA + "textures/rings.png");
	planetTexture = loadImage(pathDATA + "textures/texture.png");
	planetW = planetH = 50;
	initializeSphere(planetW, planetH);

	layout = new HSphereLayout().radius(sphereRadius).loc(sphereX,sphereY,sphereZ);


	oscRX = new HOscillator().range(-360,360).speed(0.01f).freq(1).waveform(H.SINE);
	oscRY = new HOscillator().range(-360,360).speed(0.07f).freq(1).waveform(H.SINE);
	oscRZ = new HOscillator().range(-360,360).speed(0.05f).freq(1).waveform(H.SINE);

	buildLayout(); // lets fire the layout function
}

public void draw() {
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

		oscRX.speed( map(myAudioData[0], 0, 100, -0.001f, 0.03f) );
		oscRX.nextRaw();
		rotateX(radians( oscRX.curr() ));

		oscRY.speed( map(myAudioData[3], 0, 100, -0.007f, 0.09f) );
		oscRY.nextRaw();
		rotateY(radians( oscRY.curr() ));

		oscRZ.speed( map(myAudioData[6], 0, 100, -0.005f, 0.07f) );
		oscRZ.nextRaw();
		rotateZ(radians( oscRZ.curr() ));

		// hint(ENABLE_DEPTH_TEST);

		strokeWeight(0);
		noStroke();
		fill(255 );
		textureSphere(150, 150, 150, planetTexture);

		// hint(DISABLE_DEPTH_TEST);



		noLights();

		for (int i = 0; i < numItems; ++i) {
			strokeWeight(0);
			noStroke();
			fill(  clr1.get( PApplet.parseInt(((frameCount*10)+(i*1.1f))%clr1.width), 10 ) );

			pushMatrix();
				translate(pos[i].x, pos[i].y, pos[i].z);

				float[] numbers = layout.getRotations(pos[i]);
				rotateX(numbers[0]); // x rotation
				rotate(numbers[1], numbers[2], numbers[3], numbers[4]); // y, xyz

				cubeD = (int)map(myAudioData[i%myAudioRange], 0, 100, cubeDMin, cubeDMax);

				buildCube( cubeW, cubeH, cubeD );
			popMatrix();
		}

		strokeWeight(0);
		noStroke();
		tint(  clr1.get( PApplet.parseInt(((frameCount*1))%clr1.width), 10 ) );
		image(rings, -(700/2), -(700/2));

	popMatrix();
}

// ****************************************************************************

public void stop() {
	myAudio.close();
	minim.stop();  
	super.stop();
}

// ****************************************************************************

public void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}
}

// ****************************************************************************

public void buildCube(float _w, float _h, float _d) {

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

public void initializeSphere(int numPtsW, int numPtsH_2pi) {
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
		if (PApplet.parseInt(numPointsH_2pi/2) != (float)numPointsH_2pi/2 && i==numPointsH-1) {
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

public void textureSphere(float rx, float ry, float rz, PImage t) { 
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
