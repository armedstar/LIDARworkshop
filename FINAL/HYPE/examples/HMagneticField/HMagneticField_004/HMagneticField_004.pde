import hype.*;
import hype.extended.layout.HGridLayout;
import hype.extended.behavior.HMagneticField;

HDrawablePool  pool;
HMagneticField field;

void setup() {
	size(640,640);
	H.init(this).background(#242424);

	field = new HMagneticField()
		.addPole(width/2, height/2,  -1) // x, y, south polarity / strength = -1 / attract
	;

	pool = new HDrawablePool(930);
	pool.autoAddToStage()
		.add(new HShape("arrow.svg").enableStyle(false).anchorAt(H.CENTER))
		.layout(new HGridLayout().startX(5).startY(15).spacing(21,21).cols(31))
		.onCreate(
			new HCallback() {
				public void run(Object obj) {
					HDrawable d = (HDrawable) obj;
					d.noStroke().fill(255);
					field.addTarget(d);
				}
			}
		)
		.requestAll()
	;
}

void draw() {
	HMagneticField.HPole p = field.pole(0);
	p.x = mouseX;
	p.y = mouseY;

	H.drawStage();
}
