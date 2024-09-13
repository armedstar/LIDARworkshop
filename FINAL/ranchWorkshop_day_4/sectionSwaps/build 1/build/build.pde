import hype.*;
import hype.extended.behavior.HOscillator;

// ****************************************************************************

int    stageW   = 900;
int    stageH   = 900;
color  clrBg    = #111111;
String pathDATA = "../../data/";

// ****************************************************************************

int sectionNum = 1; // where to start

// ****************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);
}

void draw() {
	background(clrBg);

	switch (sectionNum) {
		case 1 : if(s1_firstRun) { s1_setup(); } s1_draw(); break;
		case 2 : if(s2_firstRun) { s2_setup(); } s2_draw(); break;
	}
}

// ****************************************************************************

void keyPressed() {
	switch (key) {
		case '1' : sectionNum = 1; s1_firstRun = true; break;
		case '2' : sectionNum = 2; s2_firstRun = true; break;
	}
}
