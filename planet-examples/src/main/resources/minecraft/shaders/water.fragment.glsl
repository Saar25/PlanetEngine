// Consts
const int TOP_DIR = 2;

// Uniforms
uniform sampler2D u_atlas;
uniform float u_transitionCross;

// Vertex outputs
flat in int v_dir;
flat in int v_shw;
flat in vec3 v_normal;
in vec2 v_uvCoords1;
in vec2 v_uvCoords2;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normalSpecular;

const float[] lights = float[](.75, .75, 1.0, .25, .50, .50);

void main(void) {
    if (v_dir == TOP_DIR) {
        vec4 colour1 = texture(u_atlas, v_uvCoords1);
        vec4 colour2 = texture(u_atlas, v_uvCoords2);
        f_colour = mix(colour1, colour2, u_transitionCross);
    } else {
        f_colour = texture(u_atlas, v_uvCoords1);
    }

    f_colour.xyz *= lights[v_dir];
    f_colour *= 1 - v_shw / 10.0;
    f_colour.a = .7;

    f_normalSpecular = vec4(v_normal, 1);
}
