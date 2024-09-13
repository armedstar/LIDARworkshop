int     stageW   = 1920;
int     stageH   = 1080;
color   clrBG    = #CCCCCC;
String  pathDATA = "../../data/";

// ********************************************************************************************************************

PShape  s;
int     sNumShapes;

PImage  tex0, tex1, tex2, tex3;
int     whichTex = 0;
PVector uV;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH,P3D);
}

void setup(){
	background(clrBG);
	s = loadShape(pathDATA+"ai_003.svg");
	sNumShapes = s.getChildCount();

	textureMode(NORMAL);
	tex0 = loadImage(pathDATA+"tex0.png");
	tex1 = loadImage(pathDATA+"tex1.png");
	tex2 = loadImage(pathDATA+"tex2.png");
	tex3 = loadImage(pathDATA+"tex3.png");
}
 
void draw(){
	background(clrBG);
	for (int i=0; i<sNumShapes; i++) {
		buildVertex( s.getChild(i), i );
	}
}

// ********************************************************************************************************************

void buildVertex(PShape _curShape, int _i) {
	strokeWeight(0);
	noStroke();
	noFill();
	tint(#202020);

	beginShape(TRIANGLES);
		switch (whichTex) {
			case 0: texture(tex0); break;
			case 1: texture(tex1); break;
			case 2: texture(tex2); break;
			case 3: texture(tex3); break;
		}

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

void mousePressed() {
	whichTex = (whichTex+1)%4;
}


