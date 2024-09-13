import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import hype.*; 
import hype.extended.behavior.HNoiseLoop; 
import hype.extended.colorist.HColorPool; 
import hype.extended.layout.HGridLayout; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class HNoiseLoop_008 extends PApplet {






HDrawablePool pool, pool2;

public void setup() {
	
	// frameRate(24);
	H.init(this).background(0xff242424);

	pool = new HDrawablePool(90);
	pool.autoAddToStage()
		.add(new HRect(6).rounding(2).anchorAt(H.CENTER).noStroke())
		.colorist(new HColorPool(0xffFFFFFF, 0xffF7F7F7, 0xffECECEC, 0xff333333, 0xff0095A8, 0xff00616F, 0xffFF3300, 0xffFF6600).fillOnly())
		.layout(new HGridLayout().startLoc(9, height/2).spacing(7, 0).cols(90))
		.onCreate(
			new HCallback() {
				public void run(Object obj) {
					int i = pool.currentIndex();
					
					HDrawable d = (HDrawable) obj;

					new HNoiseLoop()
						.target(d)
						.property(H.Y)
						.relativeVal(320)
						.range(-200, 200)
						.radius(150)
						.currentStep(i)
						.clockwise(true)//sample noise in clockwise direction
					;
				}
			}
		)
		.requestAll()
	;

	pool2 = new HDrawablePool(90);
	pool2.autoAddToStage()
		.add(new HRect(6).rounding(2).anchorAt(H.CENTER).noStroke())
		.colorist(new HColorPool(0xffFFFFFF, 0xffF7F7F7, 0xffECECEC, 0xff333333, 0xff0095A8, 0xff00616F, 0xffFF3300, 0xffFF6600).fillOnly())
		.layout(new HGridLayout().startLoc(9, height/2).spacing(7, 0).cols(90))
		.onCreate(
			new HCallback() {
				public void run(Object obj) {
					int i = pool2.currentIndex();
					
					HDrawable d = (HDrawable) obj;

					new HNoiseLoop()
						.target(d)
						.property(H.Y)
						.relativeVal(320)
						.range(-200, 200)
						.radius(150)
						.currentStep(i)
						.clockwise(false)//sample noise in anti-clockwise direction
					;
				}
			}
		)
		.requestAll()
	;
}

public void draw() {
	H.drawStage();
}
  public void settings() { 	size(640,640); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "HNoiseLoop_008" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
