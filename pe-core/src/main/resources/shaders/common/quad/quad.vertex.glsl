/*
*
* Simple full screen quad vertex shader
*
*/

// Consts
const vec2[] vertices = {
vec2(+0, +0), vec2(+1, +0),
vec2(+0, +1), vec2(+1, +1)
};

// Vertex outputs
out vec2 v_position;

void main(void) {
    v_position = vertices[gl_VertexID];

    gl_Position = vec4(v_position * 2 - 1, 0, 1);
}
