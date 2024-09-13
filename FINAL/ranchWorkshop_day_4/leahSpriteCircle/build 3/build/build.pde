import hype.*;
import hype.extended.layout.HCircleLayout;

// ****************************************************************************

int           stageW         = 900;
int           stageH         = 900;
color         clrBg          = #ECECEC;
String        pathDATA       = "../../data/";

// ****************************************************************************

// SPRITE SHEET

PImage        ss;
float         ssW            = 980.0;
float         ssH            = 980.0;
int           ssCols         = 5;
int           ssRows         = 5;
int           ssMax          = ssCols * ssRows;
float         ssCellW        = ssW/ssCols;
float         ssCellH        = ssH/ssRows;

int           ssCount        = 0;
float         ssX, ssY; // store/update x,y positions

int           speedMin       = 0;
int           speedMax       = 3;

// ********************************************************************************************************************

// CIRCLE LAYOUT

int          numItems        = 12;
int          circleRadius    = 300;
int          circleStartX    = 0;
int          circleStartY    = 0;
float        circleAngleStep = 360.0/numItems;

PVector[]     pos            = new PVector[numItems];

HCircleLayout layout;

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup() {
	H.init(this);
	background(clrBg);

	hint(DISABLE_TEXTURE_MIPMAPS);
	((PGraphicsOpenGL)g).textureSampling(2);

	ss = loadImage(pathDATA + "spritesheet.png");

	layout = new HCircleLayout().radius(circleRadius).startX(circleStartX).startY(circleStartY).angleStep(circleAngleStep);

	buildLayout(); // lets fire the layout function
}

void draw() {
	background(clrBg);

	if (++speedMin%speedMax==0) {
		ssX = (ssCount%ssCols) * ssCellW;
		ssY = floor(ssCount/ssCols) * ssCellH;
		ssCount = ++ssCount%ssMax;
	}

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		for (int i = 0; i < numItems; ++i) {
			pushMatrix();
				translate(pos[i].x, pos[i].y, pos[i].z);

				rotateZ( radians( 90 + (circleAngleStep*i)  ) );

				scale(ssCellW);

				strokeWeight(0);
				noStroke();
				fill(255);

				beginShape(QUADS);
					texture(ss);
					vertex( -(0.5), -(0.5), 0,   ssX,                 ssY);
					vertex(  (0.5), -(0.5), 0,   ssX+ssCellW,         ssY);
					vertex(  (0.5),  (0.5), 0,   ssX+ssCellW, ssY+ssCellH);
					vertex( -(0.5),  (0.5), 0,   ssX,         ssY+ssCellH);
				endShape(CLOSE);

			popMatrix();
		}
	popMatrix();

}

void buildLayout() {
	for (int i = 0; i < numItems; ++i) {
		pos[i] = layout.getNextPoint();
	}
}