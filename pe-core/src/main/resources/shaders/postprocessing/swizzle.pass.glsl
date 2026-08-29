// Definitions
#ifndef R
#define R r
#endif
#ifndef G
#define G g
#endif
#ifndef B
#define B b
#endif
#ifndef A
#define A a
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform float u_contrast;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 color = texture(u_texture, v_position);
    f_color = vec4(color.R, color.G, color.B, color.A);
}
