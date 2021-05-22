// Vertex outputs
in vec2 v_uvCoord;
in vec3 v_normal;

// Uniforms
uniform sampler2D u_texture;
uniform float u_specular;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;
layout (location = 2) out vec4 f_specular;

void main(void) {
    f_colour = texture(u_texture, v_uvCoord);
    f_normal = vec4(v_normal, 1);
    f_specular = vec4(u_specular, 1, 1, 1);
}
