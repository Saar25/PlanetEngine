// Consts
const int TOP_DIR = 2;

// Uniforms
uniform sampler2D u_atlas;
uniform float u_transitionCross;

// Vertex outputs
flat in int v_id;
flat in int v_dir;
flat in int v_shw;
in vec2 v_uvCoords1;
in vec2 v_uvCoords2;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

const float[] lights = float[]( .75, .75, 1.0, .25, .50, .50 );

void main(void) {
    float transitionCross = v_dir == TOP_DIR ? u_transitionCross : 0;

    vec4 colour1 = texture(u_atlas, v_uvCoords1);
    vec4 colour2 = texture(u_atlas, v_uvCoords2);
    f_colour = mix(colour1, colour2, transitionCross);

    f_colour.xyz *= lights[v_dir];
    f_colour *= 1 - v_shw / 10.0;
    f_colour.a = .7;
}
