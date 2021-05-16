// Vertex outputs
in vec2 v_uvCoord;
in mat3 v_TBN;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_normalMap;
uniform mat4 u_normalMatrix;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = texture(u_texture, v_uvCoord);

    vec3 normal = texture(u_normalMap, v_uvCoord).rgb;
    normal = normalize(v_TBN * (normal * 2.0 - 1.0));
    normal = (u_normalMatrix * vec4(normal, 0)).xyz;

    f_normal = vec4(normal, 1);
}
