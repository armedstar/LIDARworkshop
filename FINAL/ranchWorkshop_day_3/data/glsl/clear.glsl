#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 texOffset;
varying vec4 vertColor;
varying vec4 vertTexCoord;

uniform float fadeSpeed = 0.5f;
uniform float tick = 17.0f; //roughly 60fps

void main(void) {
	float fadeAmount = (1.0f / (fadeSpeed * 1000.0f)) * tick;

	vec4 color = texture2D(texture, vertTexCoord.xy);
	color.a = color.a - fadeAmount;

	gl_FragColor = vec4(color);
}


