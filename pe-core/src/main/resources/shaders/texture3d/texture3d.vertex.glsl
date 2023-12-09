// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uvCoord;

// Uniforms
uniform mat4 u_mvpMatrix;

// Vertex outputs
out vec2 v_uvCoord;

void main(void) {
    v_uvCoord = in_uvCoord;

    vec4 world_position = u_mvpMatrix * vec4(in_position, 1.0);

    gl_Position = world_position;
}
