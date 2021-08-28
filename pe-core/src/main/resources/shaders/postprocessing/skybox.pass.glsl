// Vertex outputs
in vec2 v_position;
in vec3 v_viewDirection;

// Uniforms
uniform samplerCube u_cubeMap;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = texture(u_cubeMap, v_viewDirection);
}
