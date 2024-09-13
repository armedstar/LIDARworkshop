import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 

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

int    stageW   = 900;
int    stageH   = 900;
int  clrBg    = 0xffFF3300;
String pathDATA = "../../data/";

// ****************************************************************************

// load in photos, color and b&w

PImage clr1, img1, img2;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
	// fullScreen(2);
}

public void setup() {
	H.init(this);
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_002.png");
	img1 = loadImage(pathDATA + "textures/photo_1.png");
	img2 = loadImage(pathDATA + "textures/photo_2.png");

	textureMode(NORMAL);
}

public void draw() {
	background(clrBg);

	// hint(DISABLE_DEPTH_TEST);
	// hint(ENABLE_DEPTH_SORT);

	if (mousePressed) {
		translate(stageW/2, stageH/2);
		rotateX(map(mouseY, 0, stageH, -(TWO_PI/2), TWO_PI/2));
		rotateY(map(mouseX, 0, stageW, -(TWO_PI/2), TWO_PI/2));
		translate(-stageW/2, -stageH/2);
	}

	pushMatrix();
		translate(stageW/2, stageH/2, 0);
		scale(300);

		strokeWeight(0);
		noStroke();
		fill(0xffCCCCCC);

		tint(  clr1.get( (frameCount*10)%clr1.width, 10 ) );

		buildCube();
	popMatrix();


	strokeWeight(1);
	stroke(0xff00FF00);
	noFill();
	line(0, height/2, width, height/2);
	line(width/2, 0, width/2, height);
}

// ****************************************************************************

public void buildCube() {

	// back
	beginShape(QUAD);
		texture(img2);
		vertex( -(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex(  (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex(  (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex( -(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// top
	beginShape(QUAD);
		texture(img2);
		vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex( (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex( (0.5f), -(0.5f),  (0.5f),   1, 1);
		vertex(-(0.5f), -(0.5f),  (0.5f),   0, 1);
	endShape(CLOSE);

	// bottom
	beginShape(QUAD);
		texture(img2);
		vertex(-(0.5f),  (0.5f),  (0.5f),   0, 0); // x, y, z,  u, v
		vertex( (0.5f),  (0.5f),  (0.5f),   1, 0);
		vertex( (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// left
	beginShape(QUAD);
		texture(img2);
		vertex(-(0.5f), -(0.5f), -(0.5f),   0, 0); // x, y, z,  u, v
		vertex(-(0.5f), -(0.5f),  (0.5f),   1, 0);
		vertex(-(0.5f),  (0.5f),  (0.5f),   1, 1);
		vertex(-(0.5f),  (0.5f), -(0.5f),   0, 1);
	endShape(CLOSE);

	// right
	beginShape(QUAD);
		texture(img2);
		vertex( (0.5f), -(0.5f),  (0.5f),   0, 0); // x, y, z,  u, v
		vertex( (0.5f), -(0.5f), -(0.5f),   1, 0);
		vertex( (0.5f),  (0.5f), -(0.5f),   1, 1);
		vertex( (0.5f),  (0.5f),  (0.5f),   0, 1);
	endShape(CLOSE);

	// front
	beginShape(QUAD);
		texture(img2);
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
