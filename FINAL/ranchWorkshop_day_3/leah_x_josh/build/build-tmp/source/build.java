import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HGridLayout; 
import hype.extended.behavior.HOscillator; 

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

// int    stageW   = 2560/2;
// int    stageH   = 1600/2;

int    stageW   = 1920;
int    stageH   = 1080;

int  clrBg    = 0xff111111;
String pathDATA = "../../data/";

// ****************************************************************************

//3D GRID LAYOUT 1

int    grid1W        = 2;
int    grid1H        = 2;
int    grid1D        = 2;
int    grid1Total    = grid1W * grid1H * grid1D;
int    grid1SpacingX = 200;
int    grid1SpacingY = 200;
int    grid1SpacingZ = 200;
int    grid1StartX   = -((grid1W-1)*(grid1SpacingX/2));
int    grid1StartY   = -((grid1H-1)*(grid1SpacingY/2));
int    grid1StartZ   = -((grid1D-1)*(grid1SpacingZ/2));

PVector[] grid1 = new PVector[grid1Total];

HGridLayout layout1;

HOscillator[] l1S = new HOscillator[grid1Total];
int           l1SMin   = 5;
int           l1SMax   = 80;
float         l1SSpeed = 1.0f;
float         l1SFreq  = 1.0f;
float         l1SStep  = 1.0f;

// ****************************************************************************

//3D GRID LAYOUT 2

int    grid2W        = 3;
int    grid2H        = 3;
int    grid2D        = 3;
int    grid2Total    = grid2W * grid2H * grid2D;
int    grid2SpacingX = 200;
int    grid2SpacingY = 200;
int    grid2SpacingZ = 200;
int    grid2StartX   = -((grid2W-1)*(grid2SpacingX/2));
int    grid2StartY   = -((grid2H-1)*(grid2SpacingY/2));
int    grid2StartZ   = -((grid2D-1)*(grid2SpacingZ/2));

PVector[] grid2 = new PVector[grid2Total];

HGridLayout layout2;

HOscillator[] l2S = new HOscillator[grid2Total];
int           l2SMin   = 5;
int           l2SMax   = 80;
float         l2SSpeed = 1.0f;
float         l2SFreq  = 10.0f;
float         l2SStep  = 5.0f;

// ****************************************************************************

HOscillator oscRX, oscRY, oscRZ;

// ****************************************************************************

PImage clr1;
int    clr1Speed    = 5; // how fast color is moving
int    clr1Offset   = 10; // difference in color from neighbor to neighbor
String whichColor1  = "colors/chrislee_001.png";

PImage clr2;
int    clr2Speed    = 5; // how fast color is moving
int    clr2Offset   = 10; // difference in color from neighbor to neighbor
String whichColor2  = "colors/chrislee_004.png";

// ****************************************************************************

PImage tex1, tex2, tex3, tex4, tex5, tex6;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
	fullScreen();
}

public void setup() {
	H.init(this);
	background(clrBg);

	tex1 = loadImage(pathDATA + "textures/particle_03.png");
	tex2 = loadImage(pathDATA + "textures/rings.png");
	tex3 = loadImage(pathDATA + "textures/rings.png");
	tex4 = loadImage(pathDATA + "textures/rings.png");
	tex5 = loadImage(pathDATA + "textures/rings.png");
	tex6 = loadImage(pathDATA + "textures/particle_03.png");

	textureMode(NORMAL);

	clr1 = loadImage(pathDATA + whichColor1);
	clr2 = loadImage(pathDATA + whichColor2);

	// oscRX = new HOscillator().range(-180,180).speed(0.1).freq(1).waveform(H.SINE);
	// oscRY = new HOscillator().range(-180,180).speed(0.07).freq(1).waveform(H.EASE);
	// oscRZ = new HOscillator().range(-180,180).speed(0.05).freq(1).waveform(H.SINE);

	oscRX = new HOscillator().range(-180,180).speed(0.01f).freq(1).waveform(H.SINE);
	oscRY = new HOscillator().range(-180,180).speed(0.007f).freq(1).waveform(H.EASE);
	oscRZ = new HOscillator().range(-180,180).speed(0.05f).freq(1).waveform(H.SINE);

	layout1 = new HGridLayout().startLoc(grid1StartX,grid1StartY,grid1StartZ).spacing(grid1SpacingX,grid1SpacingY,grid1SpacingZ).cols(grid1W).rows(grid1H);

	buildLayout(grid1, grid1Total, layout1, l1S);

	layout2 = new HGridLayout().startLoc(grid2StartX,grid2StartY,grid2StartZ).spacing(grid2SpacingX,grid2SpacingY,grid2SpacingZ).cols(grid2W).rows(grid2H);

	buildLayout(grid2, grid2Total, layout2, l2S);
}

public void draw() {
	background(clrBg);	




	// lights();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		oscRX.nextRaw();
		oscRY.nextRaw();
		oscRZ.nextRaw();
		rotateX(radians( oscRX.curr() ));
		rotateY(radians( oscRY.curr() ));
		rotateZ(radians( oscRZ.curr() ));


		for (int i = 0; i < grid1Total; ++i) {
			l1S[i].nextRaw();

			strokeWeight(0);
			noStroke();
			tint( clr1.get( ((frameCount*clr1Speed)+(i*clr1Offset))%clr1.width, 10), 90 );
			// tint( #FF3300 );

			pushMatrix();
				translate(grid1[i].x, grid1[i].y, grid1[i].z);
				scale( l1S[i].curr() );
				if (i != floor(grid1Total/2) ) buildCube1();
			popMatrix();
		}

		for (int i = 0; i < grid2Total; ++i) {
			l2S[i].nextRaw();

			strokeWeight(0);
			noStroke();
			tint( clr2.get( ((frameCount*clr2Speed)+(i*clr2Offset))%clr2.width, 10), 90 );
			// tint( #00FF00 );

			pushMatrix();
				translate(grid2[i].x, grid2[i].y, grid2[i].z);
				scale( l2S[i].curr() );
				if (i != floor(grid2Total/2) ) buildCube2();
			popMatrix();
		}

	popMatrix();

}

public void buildLayout( PVector[] _g, int _t, HGridLayout _l, HOscillator[] _oS  ) {
	PVector pt = new PVector();

	for (int i = 0; i < _t; ++i) {
		_g[i] = _l.getNextPoint();

		if( _oS == l1S ) l1S[i] = new HOscillator().range(l1SMin, l1SMax).speed(l1SSpeed).freq(l1SFreq).currentStep(i*l1SStep).waveform(H.SINE);
		if( _oS == l2S ) l2S[i] = new HOscillator().range(l2SMin, l2SMax).speed(l2SSpeed).freq(l2SFreq).currentStep(i*l2SStep).waveform(H.SINE);
	}
}


// ****************************************************************************

public void buildCube1() {

	// back
	beginShape(QUAD);
		texture(tex1);
		vertex( -(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex(  (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex(  (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex( -(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

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

	// // left
	// beginShape(QUAD);
	// 	texture(tex4);
	// 	vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
	// 	vertex(-(0.5f), -(0.5f),  (0.5f),   1, 0);
	// 	vertex(-(0.5f),  (0.5f),  (0.5f),   1, 1);
	// 	vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	// endShape(CLOSE);

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

public void buildCube2() {

	// back
	beginShape(QUAD);
		texture(tex1);
		vertex( -(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex(  (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex(  (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex( -(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// top
	beginShape(QUAD);
		texture(tex2);
		vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex( (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex( (0.5f), -(0.5f),  (0.5f),   1, 1);
		vertex(-(0.5f), -(0.5f),  (0.5f),   0, 1);
	endShape(CLOSE);

	// bottom
	beginShape(QUAD);
		texture(tex3);
		vertex(-(0.5f),  (0.5f),  (0.5f),   0, 0); // x, y, z,  u, v
		vertex( (0.5f),  (0.5f),  (0.5f),   1, 0);
		vertex( (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// left
	beginShape(QUAD);
		texture(tex4);
		vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex(-(0.5f), -(0.5f),  (0.5f),   1, 0);
		vertex(-(0.5f),  (0.5f),  (0.5f),   1, 1);
		vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// right
	beginShape(QUAD);
		texture(tex5);
		vertex( (0.5f), -(0.5f),  (0.5f),   0, 0); // x, y, z,  u, v
		vertex( (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex( (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex( (0.5f),  (0.5f),  (0.5f),   0, 1);
	endShape(CLOSE);

	// front
	beginShape(QUAD);
		texture(tex6);
		vertex( -(0.5f), -(0.5f), (0.5f),   0, 0); // x, y, z,  u, v
		vertex(  (0.5f), -(0.5f), (0.5f),   1, 0);
		vertex(  (0.5f),  (0.5f), (0.5f),   1, 1);
		vertex( -(0.5f),  (0.5f), (0.5f),   0, 1);
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
