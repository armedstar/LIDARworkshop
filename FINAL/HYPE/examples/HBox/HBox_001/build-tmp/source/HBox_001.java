import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.layout.HGridLayout; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class HBox_001 extends PApplet {




HDrawablePool pool;

public void setup() {
	
	H.init(this).background(0xff242424).use3D(true);
	lights();

	pool = new HDrawablePool(100);
	pool.autoAddToStage()
		.add(new HBox())
		.layout(new HGridLayout().startX(-125).startY(-125).spacing(100,100).cols(10))
		.onCreate(
			 new HCallback() {
				public void run(Object obj) {
					HBox d = (HBox) obj;
					d
						.depth(64) // depth is a 3D/HBox specific method, so put this first
						.width(64)
						.height(64)
						.z(-500)
						.rotationZ(33)
					;
				}
			}
		)
		.requestAll()
	;

	H.drawStage();
	noLoop();
}

public void draw() {

}

  public void settings() { 	size(640,640,P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "HBox_001" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
