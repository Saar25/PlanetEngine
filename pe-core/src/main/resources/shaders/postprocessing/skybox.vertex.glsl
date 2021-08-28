// Consts
const vec2[] vertices = vec2[](
vec2(+0, +0), vec2(+1, +0),
vec2(+0, +1), vec2(+1, +1)
);

// Uniforms
uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;

// Vertex outputs
out vec2 v_position;
out vec3 v_viewDirection;

void main(void) {
    v_position = vertices[gl_VertexID];

    v_viewDirection = mat3(u_viewMatrixInv) * (u_projectionMatrixInv * vec4(v_position * 2 - 1, 0, 1)).xyz;

    gl_Position = vec4(v_position * 2 - 1, 0, 1);
}
