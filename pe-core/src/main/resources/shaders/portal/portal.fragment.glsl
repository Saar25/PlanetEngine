// Vertex outputs
flat in vec3 v_color;

// Uniforms
uniform sampler2D u_texture;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec2 ndc = (v_clipSpace.xy / v_clipSpace.w) * 0.5 + 0.5;
    vec4 color = texture(u_texture, ndc);

    f_color = color;
}
