// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uvCoord;
layout (location = 2) in vec3 in_normal;

// Uniforms
uniform mat4 u_viewProjectionMatrix;
uniform mat4 u_transformationMatrix;
uniform mat4 u_normalMatrix;

// Vertex outputs
out vec2 v_uvCoord;
out vec3 v_normal;

vec4 calculatePosition() {
    return u_transformationMatrix * vec4(in_position, 1.0);
}

vec3 calculateNormal() {
    return (u_normalMatrix * u_transformationMatrix * vec4(in_normal, 0.0)).xyz;
}

void main(void) {
    v_uvCoord = in_uvCoord;

    v_normal = calculateNormal();

    vec4 world = calculatePosition();
    gl_Position = u_viewProjectionMatrix * world;
}
