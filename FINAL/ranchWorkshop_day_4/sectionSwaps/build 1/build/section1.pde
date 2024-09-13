boolean s1_firstRun = true;
boolean s1_fireOnce = true;

// ****************************************************************************

int     s1_x        = 100;
int     s1_y        = 100;
int     s1_w        = 400;
int     s1_h        = 400;
color   s1_c        = #FF3300;

HOscillator s1_osxR;

// ****************************************************************************

void s1_setup() {
	// runs once

	if(s1_fireOnce) s1_osxR = new HOscillator().range(-180,180).speed(1).freq(1);
	s1_fireOnce = false;

	s1_firstRun = false;
}

void s1_draw() {
	// loop

	s1_osxR.nextRaw();

	pushMatrix();
		translate(s1_x, s1_y, 0);

		rotateY(radians( s1_osxR.curr() ));

		strokeWeight(0);
		noStroke();
		fill(s1_c);
		rect(0, 0, s1_w, s1_h);

	popMatrix();
}