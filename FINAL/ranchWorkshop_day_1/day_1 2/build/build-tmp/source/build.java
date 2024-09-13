import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int    stageW   = 900;
int    stageH   = 900;
int  clrBg    = 0xff000000;
String pathDATA = "../../data/";

// ****************************************************************************

// THIS IS COLORS

PImage clr1, clr2;

// ****************************************************************************

public void settings() {
	size(stageW, stageH, P3D);
}

public void setup() {
	background(clrBg);

	clr1 = loadImage(pathDATA + "colors/color_001.png");
	clr2 = loadImage(pathDATA + "colors/color_002.png");
}

public void draw() {
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
