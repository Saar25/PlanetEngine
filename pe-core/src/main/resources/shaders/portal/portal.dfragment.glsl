// Vertex outputs
in vec4 v_clipSpace;

// Uniforms
uniform sampler2D u_texture;

// Fragment outputs
layout (location = 0) out vec4 f_color;
layout (location = 1) out vec4 f_normalSpecular;

void main(void) {
    vec2 ndc = (v_clipSpace.xy / v_clipSpace.w) * 0.5 + 0.5;
    vec4 color = texture(u_texture, ndc);

    f_color = color;
    f_normalSpecular = vec4(0, 1, 0, 0);
}
