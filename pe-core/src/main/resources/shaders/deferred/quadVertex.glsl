#version 400

const vec2[] vertices = {
    vec2(-1, -1), vec2(-1, +1),
    vec2(+1, -1), vec2(+1, +1)
};

// Vertex outputs
out vec2 v_position;

void main(void) {
    vec2 vertex = vertices(gl_VertexID);

    v_position = vertex;

    gl_Position = vec4(vertex, 0.0, 1.0);
}
