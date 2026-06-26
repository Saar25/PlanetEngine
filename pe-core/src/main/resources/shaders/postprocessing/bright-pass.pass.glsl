// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform float u_threshold;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 color = texture(u_texture, v_position);
    float brightness = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
    f_color = color * step(u_threshold, brightness);
}
