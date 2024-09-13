int    stageW   = 1000;
int    stageH   = 500;
color  clrBG    = #CCCCCC;
String pathDATA = "../../data/";

// ********************************************************************************************************************

PShape s;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH,P3D);
}

void setup(){
	background(clrBG);
	s = loadShape(pathDATA+"ai_001.svg");	
}
 
void draw(){
	background(clrBG);

	s.disableStyle();
	strokeWeight(10);
	stroke(#FF3300);
	fill(#999999);

	shape(s);
}

// ********************************************************************************************************************
