// Uniforms
uniform sampler2D u_atlas;
uniform ivec4 u_rayCastedFace;

// Vertex outputs
flat in int v_id;
flat in int v_dir;
in vec2 v_uvCoords;
in vec3 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

const float[] lights = { .75, .75, 1.0, .25, .50, .50 };

void main(void) {
//    f_colour = vec4(colours[v_id], 1);
    f_colour = texture(u_atlas, v_uvCoords);
    if (f_colour.a < .5) discard;
    f_colour *= lights[v_dir];

    if (u_rayCastedFace.w >= 0) {
        int glow = ((v_position.x >= u_rayCastedFace.x) && (v_position.x <= u_rayCastedFace.x + 1))
        && ((v_position.y >= u_rayCastedFace.y) && (v_position.y <= u_rayCastedFace.y + 1))
        && ((v_position.z >= u_rayCastedFace.z) && (v_position.z <= u_rayCastedFace.z + 1)) ? 1 : 0;

        f_colour *= glow + 1;
    }
}
