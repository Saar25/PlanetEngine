#version 400

// Vertex outputs
in vec2 v_uvCoord;
in vec3 v_normal;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_normalMap;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = texture(u_texture, v_uvCoord);

    vec3 normal = texture(u_normalMap, v_uvCoord).rgb;
    normal = normalize(v_TBN * (normal * 2.0 - 1.0));
}
