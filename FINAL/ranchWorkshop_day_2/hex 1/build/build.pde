import hype.*;
import hype.extended.layout.HHexLayout;

// ****************************************************************************

int    stageW       = 900;
int    stageH       = 900;
color  clrBg        = #ECECEC;
String pathDATA     = "../../data/";

// ****************************************************************************

// HEX LAYOUT

 int      numItems        = 169;
 int      hexSpacing      = 25;
 int      hexStartX       = 0;
 int      hexStartY       = 0;

PVector[] pos = new PVector[numItems];

HHexLayout layout;

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	layout = new HHexLayout().spacing(hexSpacing).offsetX(hexStartX).offsetY(hexStartY);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);


	lights();

	for (int i = 0; i < numItems; ++i) {
		pushMatrix();
			translate(pos[i].x, pos[i].y, pos[i].z);
			box(20, 20, 20);
		popMatrix();
	}
}

void buildLayout() {
	PVector pt = new PVector();

	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}

	// println( pos );
}