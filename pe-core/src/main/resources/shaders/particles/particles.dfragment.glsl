// Vertex outputs
in vec2 v_uvCoords1;
in vec2 v_uvCoords2;
in float v_blend;

// Uniforms
uniform sampler2D u_texture;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normalSpecular;

void main(void) {
    vec4 colour1 = texture(u_texture, v_uvCoords1);
    vec4 colour2 = texture(u_texture, v_uvCoords2);
    f_colour = mix(colour1, colour2, v_blend);
    f_normalSpecular = vec4(0, 0, 1, .1);
}
