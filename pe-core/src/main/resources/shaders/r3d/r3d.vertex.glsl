// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec3 in_normal;
layout (location = 2) in vec3 in_colour;
layout (location = 3) in mat4 in_transformation;

// Uniforms
uniform mat4 u_modelMatrix;
uniform mat4 u_mvpMatrix;
uniform mat4 u_normalMatrix;
uniform vec4 u_clipPlane;

// Vertex outputs
flat out vec3 v_colour;
flat out vec3 v_normal;

void main(void) {
    v_colour = in_colour;

    v_normal = (u_normalMatrix * vec4(in_normal, 0.0)).xyz;

    vec4 localCoords = in_transformation * vec4(in_position, 1.0);
    vec4 screenCoords = u_mvpMatrix * localCoords;
    vec4 worldCoords = u_modelMatrix * localCoords;

    gl_ClipDistance[0] = dot(u_clipPlane, worldCoords);
    gl_Position = screenCoords;
}
