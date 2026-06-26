// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform float u_threshold;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    vec4 colour = texture(u_texture, v_position);
    float brightness = dot(colour.rgb, vec3(0.2126, 0.7152, 0.0722));
    f_colour = colour * step(u_threshold, brightness);
}
