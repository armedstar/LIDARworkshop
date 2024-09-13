int    stageW   = 900;
int    stageH   = 900;
color  clrBg    = #000000;
String pathDATA = "../../data/";

// ****************************************************************************

// THIS IS COLORS

PImage clr1, clr2;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_001.png");
	clr2 = loadImage(pathDATA + "colors/color_002.png");
}

void draw() {
	background(clrBg);

	image(clr1,0,0,stageW,20); // image,x,y,w,h
	image(clr2,0,21,stageW,20); // image,x,y,w,h

	noStroke();

	// first 3 rect using clr 1
	for (int i = 0; i < 3; ++i) {
		fill( clr1.get(  ((frameCount*10)+(i*150))%2000, 10  ) );
		rect(10+(i*161), 50, 150, 150); // x,y,w,h
	}

	// first 3 rect using clr 2
	for (int i = 0; i < 3; ++i) {
		fill( clr2.get(  ((frameCount*10)+(i*500))%2000, 10  ) );
		rect(10+(i*161), 210, 150, 150); // x,y,w,h
	}
}