import hype.*;

// ****************************************************************************

int    stageW   = 900;
int    stageH   = 900;
color  clrBg    = #FF3300;
String pathDATA = "../../data/";

// ****************************************************************************

// load in photos, color and b&w

PImage img1, img2;

// ****************************************************************************

PImage        clr1;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
	// fullScreen(2);
}

void setup() {
	H.init(this);
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_002.png");

	img1 = loadImage(pathDATA + "textures/photo_1.png");
	img2 = loadImage(pathDATA + "textures/photo_2.png");

	textureMode(NORMAL);
}

void draw() {
	background(clrBg);

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		scale(500);

		strokeWeight(0);
		noStroke();
		fill(#FF0000);

		tint(  clr1.get( (frameCount*10)%clr1.width, 10 ) );

		beginShape(QUAD);
			texture(img2);
			vertex( -(0.5), -(0.5),     0, 0); // x, y, u, v
			vertex(  (0.5), -(0.5),     1, 0);
			vertex(  (0.5),  (0.5),     1, 1);
			vertex( -(0.5),  (0.5),     0, 1);
		endShape(CLOSE);


	popMatrix();


	// strokeWeight(1);
	// stroke(#00FF00);
	// noFill();
	// line(0, height/2, width, height/2);
	// line(width/2, 0, width/2, height);


}