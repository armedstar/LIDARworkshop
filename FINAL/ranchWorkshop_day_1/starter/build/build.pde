import hype.*;

// ****************************************************************************

int    stageW   = 900;
int    stageH   = 900;
color  clrBg    = #FF3300;
String pathDATA = "../../data/"

// ****************************************************************************


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
}