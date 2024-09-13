import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HPackingLayout; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class HPackingLayout_001 extends PApplet {




HDrawablePool pool;
HPackingLayout layout;

public void setup() {
	
	H.init(this).background(0xff242424);

	layout = new HPackingLayout().maxSize(500.0f).minSize(2.0f).numTrys(1500).numItems(300);

	pool = new HDrawablePool(300);
	pool.autoAddToStage()
		.add(new HEllipse())

		.layout(
			layout
		)

		.onCreate(
			 new HCallback() {
				public void run(Object obj) {
					HEllipse d = (HEllipse) obj;
					d.fill(255).noStroke();
				}
			}
		)
		.requestAll()
	;
}

public void draw() {
	H.drawStage();
	surface.setTitle("FPS: " + frameRate);
}


public void mousePressed() {
	pool.drain();
	layout.reset();
	pool.shuffleRequestAll();
}
  public void settings() { 	size(640,640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "HPackingLayout_001" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
