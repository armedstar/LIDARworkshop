int     stageW   = 1000;
int     stageH   = 500;
color   clrBG    = #CCCCCC;
String  pathDATA = "../../data/";

// ********************************************************************************************************************

PShape  s;
int     sNumShapes;

PImage  tex;
PVector uV;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH,P3D);
}

void setup(){
	background(clrBG);
	s = loadShape(pathDATA+"ai_001.svg");
	sNumShapes = s.getChildCount();

	textureMode(NORMAL);
	tex = loadImage(pathDATA+"photo.png");
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
	noFill();
	// tint(#FF3300);

	beginShape(TRIANGLES);
		texture(tex);
		for (int i=0; i<_curShape.getVertexCount(); i++) {
			PVector v = _curShape.getVertex(i);

			switch (i) {
				case 0: uV = new PVector(0,0); break;
				case 1: uV = new PVector(1,0); break;
				case 2: uV = new PVector(0,1); break;
			}

			vertex(v.x, v.y, uV.x, uV.y);
		}
	endShape(CLOSE);
}

// ********************************************************************************************************************


