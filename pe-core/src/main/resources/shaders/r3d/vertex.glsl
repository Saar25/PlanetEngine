// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec3 in_normal;
layout (location = 2) in vec3 in_colour;
layout (location = 3) in mat4 in_transformation;

// Uniforms
uniform mat4 mvpMatrix;

// Vertex outputs
flat out vec3 v_colour;
flat out vec3 v_normal;

void main(void) {
    v_colour = in_colour;
    v_normal = in_normal;

    vec4 world = in_transformation * vec4(in_position, 1.0);
    gl_Position = mvpMatrix * world;
}
