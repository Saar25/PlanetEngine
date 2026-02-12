// Vertex outputs
flat in vec3 v_color;

// Uniforms
uniform sampler2D u_texture;
uniform ivec2 u_resolution;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 color = texture(u_texture, gl_FragCoord.xy / u_resolution);

    f_color = color;
}
