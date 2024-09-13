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

int           stageW           = 900;
int           stageH           = 900;
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
AudioPlayer   myAudio;
FFT           myAudioFFT;

int           myAudioRange     = sphereRows*2;
int           myAudioMax       = 100;

float         myAudioAmp       = 22.0f;
float         myAudioIndex     = 0.19f;
float         myAudioIndexAmp  = myAudioIndex;
float         myAudioIndexStep = 0.225f;

float[]      myAudioData       = new float[myAudioRange];

// ************************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	H.init(this);
	background(clrBg);

	minim   = new Minim(this);
	myAudio = minim.loadFile(pathDATA + "audio/HECQ_With_Angels_Trifonic_Remix.wav");
	myAudio.play();

	myAudioFFT = new FFT(myAudio.bufferSize(), myAudio.sampleRate());
	myAudioFFT.linAverages(myAudioRange);
	myAudioFFT.window(FFT.GAUSS);

	clr1 = loadImage(pathDATA + "colors/color_002.png");
	textureMode(NORMAL);

	layout = new HSphereLayout().radius(sphereRadius).loc(sphereX,sphereY,sphereZ);
	layout.useSpiral().numPoints(numItems);
	// layout.phiModifier(7.2);
	layout.phiModifier(1.2345f);
	// layout.phiModifier(3.0001);
	// layout.phiModifier(0.805);

	oscRX = new HOscillator().range(-180,180).speed(0.01f).freq(1).waveform(H.SINE);
	oscRY = new HOscillator().range(-180,180).speed(0.07f).freq(1).waveform(H.SINE);
	oscRZ = new HOscillator().range(-180,180).speed(0.05f).freq(1).waveform(H.SINE);

	buildLayout(); // lets fire the layout function
}

public void draw() {
	background(clrBg);

	myAudioFFT.forward(myAudio.mix);
	for (int i = 0; i < myAudioRange; ++i) {
		float tempIndexAvg = (myAudioFFT.getAvg(i) * myAudioAmp) * myAudioIndexAmp;
		float tempIndexCon = constrain(tempIndexAvg, 0, myAudioMax);
		myAudioData[i]     = tempIndexCon;
		myAudioIndexAmp+=myAudioIndexStep;
	}
	myAudioIndexAmp = myAudioIndex;

	if (mousePressed) {
		translate(stageW/2, stageH/2);
		rotateX(map(mouseY, 0, stageH, -(TWO_PI/2), TWO_PI/2));
		rotateY(map(mouseX, 0, stageW, -(TWO_PI/2), TWO_PI/2));
		translate(-stageW/2, -stageH/2);
	}



	pushMatrix();
		translate(stageW/2, stageH/2, 0);
		strokeWeight(0);
		noStroke();
		fill( 0xff999999 );
		sphereDetail(20);

		int sphereS = (int)map(myAudioData[0], 0, 100, 180, 200);
		sphere(sphereS);
	popMatrix();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		oscRX.nextRaw(); rotateX(radians( oscRX.curr() ));
		oscRY.nextRaw(); rotateY(radians( oscRY.curr() ));
		oscRZ.nextRaw(); rotateZ(radians( oscRZ.curr() ));

		for (int i = 0; i < numItems; ++i) {
			strokeWeight(0);
			noStroke();
			fill(  clr1.get( ((frameCount*5)+(i*10))%clr1.width, 10 ) );

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


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
