int    stageW   = 900;
int    stageH   = 900;
color  clrBg    = #000000;
String pathDATA = "../../data/";

// ****************************************************************************

// THIS IS COLORS

PImage clr1, clr2;

int whichColor = 1; // = either color 1 or color 2.... 1 or 2

// ****************************************************************************

// lets randomly put 100 things on screen

int numItems = 100;
int minS = 50;
int maxS = 150;

PVector[] myPos; // x,y,z

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_25_a.png");
	clr2 = loadImage(pathDATA + "colors/chrislee_004.png");

	pickPos();
}

void draw() {
	background(  clr2.get((frameCount*10)%clr2.width, 2) );

	// image(clr1,0,0,stageW,20); // image,x,y,w,h
	// image(clr2,0,21,stageW,20); // image,x,y,w,h

	noStroke();

	for (int i = 0; i < numItems; ++i) {
		PVector pt = myPos[i];

		switch (whichColor) {
			case 1: fill( clr1.get(((frameCount*2)+(i*5))%clr1.width, 10) ); break;
			case 2: fill( clr2.get(((frameCount*10)+(i*50))%clr2.width, 10) ); break;
		}

		rect(pt.x, pt.y, 100, 100);
	}
}

// function that picks stuff

void pickPos() {
	myPos = new PVector[numItems];

	for (int i = 0; i < numItems; ++i) {
		PVector pt = new PVector();
		pt.x = (int)random(stageW);
		pt.y = (int)random(stageH);
		myPos[i] = pt;
	}

	// println( myPos );
}

void keyPressed() {
	switch (key) {
		case ' ': pickPos(); break;
		case '1': whichColor=1; break;
		case '2': whichColor=2; break;
	}
}