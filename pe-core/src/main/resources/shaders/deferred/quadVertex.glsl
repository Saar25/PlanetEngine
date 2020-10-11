const vec2[] vertices = {
    vec2(+0.0, +0.0), vec2(+1.0, +0.0),
    vec2(+0.0, +1.0), vec2(+1.0, +1.0)
};

// Per Vertex attibutes
layout (location = 0) in vec2 in_position;

// Vertex outputs
out vec2 v_position;

void main(void) {
    vec2 vertex = vertices[gl_VertexID];

    v_position = vertex;

    gl_Position = vec4(vertex * 2 - 1, 0.0, 1.0);
}
