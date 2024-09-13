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

PImage img1, img2;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
	// fullScreen(2);
}

public void setup() {
	H.init(this);
	background(clrBg);

	img1 = loadImage(pathDATA + "textures/photo_1.png");
	img2 = loadImage(pathDATA + "textures/photo_2.png");
}

public void draw() {
	background(clrBg);

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		scale(500);

		strokeWeight(0);
		noStroke();
		fill(0xffCCCCCC);

		beginShape(QUAD);
			texture(img1);
			vertex( -(0.5f), -(0.5f),       0,   0); // x, y, u, v
			vertex(  (0.5f), -(0.5f),     500,   0);
			vertex(  (0.5f),  (0.5f),     500, 500);
			vertex( -(0.5f),  (0.5f),       0, 500);
		endShape(CLOSE);


	popMatrix();


	strokeWeight(1);
	stroke(0xff00FF00);
	noFill();
	line(0, height/2, width, height/2);
	line(width/2, 0, width/2, height);


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
