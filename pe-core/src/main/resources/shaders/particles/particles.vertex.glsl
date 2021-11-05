// Consts
const vec2[] uvCoords = vec2[](
vec2(+0.0, +0.0), vec2(+0.0, +1.0),
vec2(+1.0, +0.0), vec2(+1.0, +1.0)
);

const vec2[] positions = vec2[](
vec2(0, 0), vec2(1, 0),
vec2(0, 1), vec2(1, 1)
);

const int MAX_INT = 2147483647;

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in int in_birth;

// Uniforms
uniform mat4 u_mvpMatrix;
uniform mat4 u_viewMatrixT;
uniform int u_textureAtlasSize;
uniform int u_currentTime;
uniform int u_maxAge;

// Vertex outputs
out vec2 v_uvCoords1;
out vec2 v_uvCoords2;
out float v_blend;

mat4 buildTransformation(void) {
    mat4 m = u_viewMatrixT;
    m[3] = vec4(in_position, 1);
    return m;
}

void main(void) {
    vec3 position = vec3(positions[gl_VertexID], 0.0);

    vec4 a = buildTransformation() * vec4(position, 1.0);
    vec4 world_position = u_mvpMatrix * a;

    float scale = 1.0 / u_textureAtlasSize;
    float age = u_currentTime > in_birth
        ? float(u_currentTime - in_birth) / u_maxAge
        : float(u_currentTime - in_birth + MAX_INT) / u_maxAge;
    float index_f = u_textureAtlasSize * u_textureAtlasSize * age;
    int index_i = int(index_f);

    vec2 offset1 = vec2(index_i / u_textureAtlasSize, index_i % u_textureAtlasSize) * scale;
    v_uvCoords1 = uvCoords[gl_VertexID] * scale + offset1;

    vec2 offset2 = vec2((index_i + 1) / 4, (index_i + 1) % 4) * scale;
    v_uvCoords2 = uvCoords[gl_VertexID] * scale + offset2;

    v_blend = index_f - index_i;

    gl_Position = world_position;
}
