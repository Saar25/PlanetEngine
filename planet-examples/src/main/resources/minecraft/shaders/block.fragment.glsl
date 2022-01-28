// Uniforms
uniform sampler2D u_atlas;
uniform ivec4 u_rayCastedFace;
uniform float u_glowTransition;

// Vertex outputs
flat in int v_id;
flat in int v_dir;
flat in int v_shw;
flat in vec3 v_normal;
flat in ivec3 v_block;
in vec2 v_uvCoords;
in vec3 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normalSpecular;

const float[] lights = float[]( .75, .75, 1.0, .25, .50, .50 );

void main(void) {
//    f_colour = vec4(colours[v_id], 1);
    f_colour = texture(u_atlas, v_uvCoords);
    if (f_colour.a < .5) discard;
    f_colour *= lights[v_dir];
    f_colour *= 1 - v_shw / 10.0;

    if (v_block == u_rayCastedFace.xyz && v_dir == u_rayCastedFace.w) {
        f_colour.rgb += vec3(u_glowTransition / 2);
    }

    f_normalSpecular = vec4(v_normal, 1);
}
