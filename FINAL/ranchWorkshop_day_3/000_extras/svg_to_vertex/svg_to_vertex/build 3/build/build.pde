int    stageW   = 1000;
int    stageH   = 500;
color  clrBG    = #CCCCCC;
String pathDATA = "../../data/";

// ********************************************************************************************************************

PShape s;
int    sNumShapes;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH,P3D);
}

void setup(){
	background(clrBG);
	s = loadShape(pathDATA+"ai_001.svg");
	sNumShapes = s.getChildCount();
}
 
void draw(){
	background(clrBG);
	
	for (int i=0; i<sNumShapes; i++) {
		buildVertex( s.getChild(i), i );
	}
}

// ********************************************************************************************************************

void buildVertex(PShape _curShape, int _i) {
	strokeWeight(1);
	stroke(#FF3300);
	fill(#999999);

	beginShape(TRIANGLES);
		for (int i=0; i<_curShape.getVertexCount(); i++) {
			PVector v = _curShape.getVertex(i);
			vertex(v.x, v.y);
		}
	endShape(CLOSE);
}

// ********************************************************************************************************************


