int     stageW    = 1920;
int     stageH    = 981;
color   clrBG     = #999999;
String  pathDATA  = "../../data/";

// ********************************************************************************************************************

PImage  ss;
float   ssW       = 980.0;
float   ssH       = 980.0;
int     ssCols    = 5;
int     ssRows    = 5;
int     ssMax     = ssCols * ssRows;
float   ssCellW   = ssW/ssCols;
float   ssCellH   = ssH/ssRows;

int     ssCount   = 0;
float   ssX, ssY; // store/update x,y positions

int     speedMin  = 0;
int     speedMax  = 2;

int     mouseFlip = -1; // -1 = look left / 1 = look right

// ********************************************************************************************************************

void settings() {
	size(stageW, stageH, P3D);
}

void setup(){
	hint(DISABLE_TEXTURE_MIPMAPS);
	((PGraphicsOpenGL)g).textureSampling(2);

	ss = loadImage(pathDATA + "spritesheet.png");
}
 
void draw(){
	background(clrBG);

	// update the sprite sheet positions
	if (++speedMin%speedMax==0) {
		ssX = (ssCount%ssCols) * ssCellW;
		ssY = floor(ssCount/ssCols) * ssCellH;
		ssCount = ++ssCount%ssMax;
	}

	strokeWeight(1);
	stroke(#FF3300);
	noFill();
	line(stageW/2, 0, stageW/2, stageH);

	// check mouse position
	if(mouseX<=stageW/2) mouseFlip = -1; // look left
	else                 mouseFlip =  1; // look right

	strokeWeight(0);
	noStroke();
	noFill();
	noTint();

	pushMatrix();
		translate(stageW/2, stageH/2, 0);

		pushMatrix();
			translate(0,0,0);
			scale(ssCellW); // 196 (980/5)

			pushMatrix();
				scale(mouseFlip,1); // look left or right based on mouse position

				beginShape(QUADS);
					texture(ss);
					vertex( -(0.5), -(0.5), 0,   ssX,                 ssY);
					vertex(  (0.5), -(0.5), 0,   ssX+ssCellW,         ssY);
					vertex(  (0.5),  (0.5), 0,   ssX+ssCellW, ssY+ssCellH);
					vertex( -(0.5),  (0.5), 0,   ssX,         ssY+ssCellH);
				endShape(CLOSE);
			popMatrix();
		popMatrix();
	popMatrix();






	surface.setTitle(
		"FPS = " + int(frameRate)
		+ " - mouseFlip = " + mouseFlip
	);

	// surface.setTitle();
}

// ********************************************************************************************************************








