#version 400

// Vertex outputs
in vec2 v_uvCoord;
in mat3 v_TBN;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_normalMap;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;

void main(void) {
    f_colour = texture(u_texture, v_uvCoord);

    vec3 normal = texture(u_normalMap, v_uvCoord).rgb;
    normal = normalize(v_TBN * (normal * 2.0 - 1.0));

    f_normal = vec4(normal, 1);
}
