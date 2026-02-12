/**
*
* Portal vertex shader
*
**/
// Per Vertex attibutes
layout (location = 0) in vec3 in_position;

// Uniforms
uniform mat4 u_mvpMatrix;

// Vertex outputs
out vec4 v_clipSpace;

void main(void) {
    v_clipSpace = u_mvpMatrix * vec4(in_position, 1.0);

    gl_Position = v_clipSpace;
}
