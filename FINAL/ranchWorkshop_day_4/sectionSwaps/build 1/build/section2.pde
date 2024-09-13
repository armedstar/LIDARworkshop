boolean s2_firstRun = true;

int     s2_x        = 300;
int     s2_y        = 300;
int     s2_w        = 200;
int     s2_h        = 200;
color   s2_c        = #00FF00;

void s2_setup() {
	// runs once

	s2_firstRun = false;
}

void s2_draw() {
	// loop

	strokeWeight(0);
	noStroke();
	fill(s2_c);
	rect(s2_x, s2_y, s2_w, s2_h);
}